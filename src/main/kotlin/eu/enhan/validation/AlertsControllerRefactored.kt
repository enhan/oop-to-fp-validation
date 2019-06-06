package eu.enhan.validation

import arrow.core.*
import arrow.core.extensions.option.applicative.applicative
import arrow.data.*
import arrow.data.extensions.nonemptylist.semigroup.semigroup
import arrow.data.extensions.validated.applicative.applicative
import com.google.common.net.HostAndPort
import eu.enhan.validation.AlertCreationError.*
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


data class AlertRefactored(
    val name: String,
    val metric: Metrics,
    val thresholdA: Int,
    val thresholdB: Int,
    val thresholdC: Int,
    val target: HostAndPort
)



@RestController
@RequestMapping("/alerts/v3")
open class AlertsControllerRefactored {

    val log = LoggerFactory.getLogger(AlertsControllerRefactored::class.java)

    private fun callService(a: AlertRefactored) {
        log.info("Calling service (actually, doing nothing).")
    }

    private fun format(err: NonEmptyList<AlertCreationError>): Array<String> =err.map{ when (it) {
        is ThresholdATooLow -> "ThresholdA '${it.incorrectValue}' is too low. Min allowed value is ${it.minAllowedValue}"
        is ThresholdCTooHigh -> "ThresholdC '${it.incorrectValue}' is too high. Max allowed value is ${it.maxAllowedValue}"
        is InvalidMetric -> "'${it.incorrectValue}' is not a valid metric"
        NameEmpty -> "Name must not be empty"
        is InvalidHost -> "'${it.incorrectValue}' is not a valid host"
        ThresholdsPartiallyValidated -> "ThresholdB was not validated because ThresholdA or ThresholdC contains error"
        is InvalidThresholdOrder -> "Thresholds must respect thresholdA < thresholdB < thresholdC"
        HostEmpty -> "Target must not be empty"
    }}.all.toTypedArray()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createAlert(@RequestBody payload: AlertPayload): ResponseEntity<Array<String>> = run {


        val validationResult: ValidatedNel<AlertCreationError, AlertRefactored> = validate(payload)

        validationResult.fold({ errors ->
            // In case of error, we just send a bad request
            ResponseEntity.badRequest().body(format(errors))
        }) { validAlert ->
            callService(validAlert)
            ResponseEntity.ok().build()
        }

    }

    private fun validate(payload: AlertPayload): ValidatedNel<AlertCreationError, AlertRefactored> = run {
        val validateName: Validated<NameEmpty, String> = validateName(payload.name)
        val validateMetric: Validated<InvalidMetric, Metrics> = validateMetric(payload.metric)
        val validateHost: Validated<AlertCreationError, HostAndPort> = validateHost(payload.target)
        val validateThresholdA: Validated<ThresholdATooLow, Int> = validateTA(payload.thresholdA)
        val validateThresholdC: Validated<ThresholdCTooHigh, Int> = validateTC(payload.thresholdC)
        // Validation of ThresholdB depends on A and C
        val validateThresholdB: Validated<AlertCreationError, Int> =
            validateTB(validateThresholdA.toOption(),  payload.thresholdB.some(), validateThresholdC.toOption())

        Validated.applicative(NonEmptyList.semigroup<AlertCreationError>()).map(
            validateName.toValidatedNel(),
            validateMetric.toValidatedNel(),
            validateThresholdA.toValidatedNel(),
            validateThresholdB.toValidatedNel(),
            validateThresholdC.toValidatedNel(),
            validateHost.toValidatedNel()
        ) {
            val (name, metric, tA, tB, tC, host) = it
            AlertRefactored(name, metric, tA, tB, tC, host)
        }.fix()

    }

    private fun validateName(name: String?): Validated<NameEmpty, String> =
        if (name.isNullOrBlank()) NameEmpty.invalid() else name.valid()

    private fun validateMetric(metric: String?): Validated<InvalidMetric, Metrics> = Validated.fromTry(Try {
        Metrics.valueOf(metric ?: "") // Making it concise...
    }).leftMap { InvalidMetric(metric) }

    private fun validateHost(rawHost: String?): Validated<AlertCreationError, HostAndPort> =
        if (rawHost.isNullOrBlank()) HostEmpty.invalid()
         else Validated.fromTry(Try {
            HostAndPort.fromString(rawHost)
        }).leftMap { InvalidHost(rawHost) }

    private fun validateTA(thresholdA: Int): Validated<ThresholdATooLow, Int> = if (thresholdA < Constants.MIN_T_A)
        ThresholdATooLow(thresholdA, Constants.MIN_T_A).invalid()
    else
        thresholdA.valid()

    private fun validateTC(thresholdC: Int): Validated<ThresholdCTooHigh, Int> = if (thresholdC > Constants.MAX_T_C)
        ThresholdCTooHigh(thresholdC, Constants.MAX_T_C).invalid()
    else
        thresholdC.valid()

    private fun validateTB(tA: Option<Int>, tB: Option<Int>, tC: Option<Int>): Validated<AlertCreationError, Int> = Option.applicative().map(tA, tB, tC){
        val (a, b, c) = it
        if (a < b && b < c)
            b.valid()
        else {
            InvalidThresholdOrder(a, b, c).invalid()
        }
    }.fix().getOrElse { ThresholdsPartiallyValidated.invalid() }

}


