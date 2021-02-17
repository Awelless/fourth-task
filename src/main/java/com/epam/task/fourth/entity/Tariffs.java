package com.epam.task.fourth.entity;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tariffs")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tariffs {
    @XmlElements({
            @XmlElement(name = "tariff", type = Tariff.class),
            @XmlElement(name = "tariffWithMonthlyFee", type = TariffWithMonthlyFee.class)
    })
    private List<Tariff> tariffs = new ArrayList<>();

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public void add(Tariff tariff) {
        tariffs.add(tariff);
    }

    @Override
    public int hashCode() {
        return tariffs.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Tariffs t = (Tariffs) o;

        return tariffs.equals(t.getTariffs());
    }
}
