package eu.enhan.validation

/**
 * @author Emmanuel Nhan
 */
data class AlertPayload(val metric: String?,
                        val name: String?,
                        val thresholdA:Int = 0,
                        val thresholdB: Int = 0,
                        val thresholdC: Int = 0,
                        val target: String? = null)