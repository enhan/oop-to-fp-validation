package eu.enhan.validation;

/**
 *
 */
public class AlertPayload {

    private String metric;
    private String name;
    private int thresholdA;
    private int thresholdB;
    private int thresholdC;
    private String target;

    public AlertPayload() {
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThresholdA() {
        return thresholdA;
    }

    public void setThresholdA(int thresholdA) {
        this.thresholdA = thresholdA;
    }

    public int getThresholdB() {
        return thresholdB;
    }

    public void setThresholdB(int thresholdB) {
        this.thresholdB = thresholdB;
    }

    public int getThresholdC() {
        return thresholdC;
    }

    public void setThresholdC(int thresholdC) {
        this.thresholdC = thresholdC;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
