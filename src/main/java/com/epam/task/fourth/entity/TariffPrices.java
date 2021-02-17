package com.epam.task.fourth.entity;

import javax.xml.bind.annotation.*;

@XmlType(name = "prices")
@XmlAccessorType(XmlAccessType.FIELD)
public class TariffPrices {
    @XmlElement(required = true)
    private int callToThisOperator;
    @XmlElement(required = true)
    private int callToOtherOperators;
    @XmlElement(required = true)
    private int callToStationary;
    @XmlElement(required = true)
    private int sms;

    public TariffPrices() {
    }

    public TariffPrices(int callToThisOperator, int callToOtherOperators, int callToStationary, int sms) {
        this.callToThisOperator = callToThisOperator;
        this.callToOtherOperators = callToOtherOperators;
        this.callToStationary = callToStationary;
        this.sms = sms;
    }

    public int getCallToThisOperator() {
        return callToThisOperator;
    }

    public void setCallToThisOperator(int callToThisOperator) {
        this.callToThisOperator = callToThisOperator;
    }

    public int getCallToOtherOperators() {
        return callToOtherOperators;
    }

    public void setCallToOtherOperators(int callToOtherOperators) {
        this.callToOtherOperators = callToOtherOperators;
    }

    public int getCallToStationary() {
        return callToStationary;
    }

    public void setCallToStationary(int callToStationary) {
        this.callToStationary = callToStationary;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }
}
