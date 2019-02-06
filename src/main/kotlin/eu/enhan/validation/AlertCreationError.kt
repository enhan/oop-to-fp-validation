package eu.enhan.validation

/**
 * @author Emmanuel Nhan
 */
sealed class AlertCreationError {

    data class ThresholdATooLow(val incorrectValue: Int, val minAllowedValue: Int):
        AlertCreationError()
    data class ThresholdCTooHigh(val incorrectValue: Int, val maxAllowedValue: Int):
        AlertCreationError()
    data class ThresholdBNotInBetween(val incorrectValue: Int, val suppliedA: Int, val suppliedC: Int):
        AlertCreationError()

    object ThresholdBNotValidated: AlertCreationError()

    data class InvalidMetric(val incorrectValue: String?): AlertCreationError()

    object NameEmpty: AlertCreationError()

    data class InvalidHost(val incorrectValue: String?): AlertCreationError()

}