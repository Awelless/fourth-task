package com.epam.task.fourth.entity;

import javax.xml.bind.annotation.*;

@XmlType(name = "tariffWithMonthlyFee")
@XmlAccessorType(XmlAccessType.FIELD)
public class TariffWithMonthlyFee extends Tariff {
    @XmlAttribute(required = true)
    private int monthlyFee;

    public TariffWithMonthlyFee() {
        super();
    }

    public TariffWithMonthlyFee(String name, String operatorName, TariffPrices prices, int connectionFee, TarifficationType type, int monthlyFee) {
        super(name, operatorName, prices, connectionFee, type);
        this.monthlyFee = monthlyFee;
    }

    public int getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(int monthlyFee) {
        this.monthlyFee = monthlyFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        TariffWithMonthlyFee t = (TariffWithMonthlyFee) o;

        return super.equals(t) && monthlyFee == t.getMonthlyFee();
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + monthlyFee;
    }
}
