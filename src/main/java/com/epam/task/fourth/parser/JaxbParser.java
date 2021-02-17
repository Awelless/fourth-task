package com.epam.task.fourth.parser;

import com.epam.task.fourth.entity.Tariff;
import com.epam.task.fourth.entity.Tariffs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JaxbParser implements XmlParser {

    private static final Logger LOGGER = LogManager.getLogger(XmlParser.class);

    @Override
    public List<Tariff> parse(String filename) throws ParserException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Tariffs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            Tariffs tariffs = (Tariffs) unmarshaller.unmarshal(new File(filename));

            return tariffs.getTariffs();

        } catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ParserException(e);
        }
    }
}
