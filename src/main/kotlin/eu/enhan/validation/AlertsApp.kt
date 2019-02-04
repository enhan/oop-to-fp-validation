package eu.enhan.validation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *
 */

@SpringBootApplication
open class AlertsApp

fun main(args: Array<String>) {
    runApplication<AlertsApp>(*args)
}