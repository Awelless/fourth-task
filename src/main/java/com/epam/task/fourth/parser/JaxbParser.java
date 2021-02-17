package com.epam.task.fourth.parser;

import com.epam.task.fourth.entity.Tariff;
import com.epam.task.fourth.entity.Tariffs;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JaxbParser implements XmlParser {

    @Override
    public List<Tariff> parse(String filename) throws ParserException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Tariffs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Tariffs tariffs = (Tariffs) unmarshaller.unmarshal(new File(filename));

            return tariffs.getTariffs();

        } catch (JAXBException e) {
            throw new ParserException(e);
        }
    }
}
