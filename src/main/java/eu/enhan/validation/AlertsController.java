package eu.enhan.validation;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HostAndPort;

/**
 *
 */
@RestController
@RequestMapping("/alerts")
public class AlertsController {


    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String createAlert(@RequestBody AlertPayload alert) {
        // Validation
        String name;
        if (alert.getName().isEmpty())
            throw new BadRequestException("name", alert.getName());
        else
            name = alert.getName();

        Metrics m = Metrics.valueOf(alert.getMetric());

        int tA;
        if (alert.getThresholdA() < 100)
            throw new BadRequestException("thresholdA must be > 100" );
        else
            tA = alert.getThresholdA();

        int tC;
        if (alert.getThresholdC() > 1000)
            throw new BadRequestException("thresholdC must be < 1000");
        else
            tC = alert.getThresholdB();

        int tB;
        if (alert.getThresholdB() < tA || alert.getThresholdB() > tC)
            throw new BadRequestException("thresholdB must be between thresholdA and thresholdC");
        else
            tB = alert.getThresholdB();

        HostAndPort host = HostAndPort.fromString(alert.getTarget());

        callService(name, m, tA, tB, tC, host);

        return "created";
    }

    @ExceptionHandler
    public ResponseEntity<BadRequestPayload> handleBadRequest(BadRequestException ex) {
        return ResponseEntity.badRequest().body(new BadRequestPayload(ex.getMessage()));
    }


    @ExceptionHandler
    public ResponseEntity<BadRequestPayload> handleIAE(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new BadRequestPayload(ex.getMessage()));
    }


    private void callService(String name, Metrics m, int tA, int tB, int tC, HostAndPort host) {
        // Just to make a point
    }

}
