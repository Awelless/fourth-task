package com.epam.task.fourth.util;

import com.epam.task.fourth.entity.TarifficationType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class TarifficationTypeAdapter extends XmlAdapter<String, TarifficationType> {
    @Override
    public TarifficationType unmarshal(String text) {
        return TarifficationType.getByString(text);
    }

    @Override
    public String marshal(TarifficationType tarifficationType) {
        return tarifficationType.getText();
    }
}
