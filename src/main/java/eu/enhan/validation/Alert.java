package eu.enhan.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.net.HostAndPort;

/**
 *
 */
public class Alert {

    @NotNull
    private Metrics metric;

    @NotNull
    @NotBlank // What happen if we put this on an int field ?
    private String name;

    @Min(100)
    @NotNull
    private int thresholdA;

    // What to put in here ?
    private int thresholdB;

    @Max(1000)
    @Min(100)
    @NotNull
    private int thresholdC;


    @NotNull
    private HostAndPort target;

    public Metrics getMetric() {
        return metric;
    }

    public void setMetric(Metrics metric) {
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

    public HostAndPort getTarget() {
        return target;
    }

    public void setTarget(HostAndPort target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Alert{" + "metric=" + metric + ", name='" + name + '\'' + ", thresholdA=" + thresholdA + ", thresholdB=" + thresholdB + ", thresholdC=" + thresholdC + ", target=" + target + '}';
    }
}
