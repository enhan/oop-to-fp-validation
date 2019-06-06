package eu.enhan.validation

/**
 * @author Emmanuel Nhan
 */
sealed class AlertCreationError {

    data class ThresholdATooLow(val incorrectValue: Int, val minAllowedValue: Int):
        AlertCreationError()
    data class ThresholdCTooHigh(val incorrectValue: Int, val maxAllowedValue: Int):
        AlertCreationError()

    object ThresholdsPartiallyValidated: AlertCreationError()

    data class InvalidThresholdOrder(val tA: Int, val tB: Int, val tC: Int):
        AlertCreationError()

    data class InvalidMetric(val incorrectValue: String?): AlertCreationError()

    object NameEmpty: AlertCreationError()

    data class InvalidHost(val incorrectValue: String?): AlertCreationError()

}