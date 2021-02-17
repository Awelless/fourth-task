package com.epam.task.fourth.entity;

import com.epam.task.fourth.util.TarifficationTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlType(name = "tariff")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tariff {
    @XmlAttribute(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String operatorName;
    @XmlElement(required = true)
    protected TariffPrices prices;
    @XmlElement(required = true)
    protected int connectionFee;
    @XmlJavaTypeAdapter(TarifficationTypeAdapter.class)
    @XmlElement(required = true)
    protected TarifficationType type;

    public Tariff() {
    }

    public Tariff(String name, String operatorName, TariffPrices prices, int connectionFee, TarifficationType type) {
        this.name = name;
        this.operatorName = operatorName;
        this.prices = prices;
        this.connectionFee = connectionFee;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public int getConnectionFee() {
        return connectionFee;
    }

    public void setConnectionFee(int connectionFee) {
        this.connectionFee = connectionFee;
    }

    public TarifficationType getType() {
        return type;
    }

    public void setType(TarifficationType type) {
        this.type = type;
    }

    public TariffPrices getPrices() {
        return prices;
    }

    public void setPrices(TariffPrices prices) {
        this.prices = prices;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + operatorName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Tariff tariff = (Tariff) o;

        return name.equals(tariff.name) &&
               operatorName.equals(tariff.operatorName);
    }
}
