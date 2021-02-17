package com.epam.task.fourth.parser;

import com.epam.task.fourth.entity.Tariff;
import com.epam.task.fourth.entity.TariffPrices;
import com.epam.task.fourth.entity.TariffWithMonthlyFee;
import com.epam.task.fourth.entity.TarifficationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaxParser implements XmlParser {

    private static final Logger LOGGER = LogManager.getLogger(XmlParser.class);

    private final XMLInputFactory inputFactory = XMLInputFactory.newInstance();

    @Override
    public List<Tariff> parse(String filename) throws ParserException {
        FileInputStream inputStream = null;

        try {
            List<Tariff> tariffs = new ArrayList<>();

            inputStream = new FileInputStream(filename);
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                int type = reader.next();

                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();

                    if (isTariff(name)) {
                        Tariff tariff = buildTariff(reader);
                        tariffs.add(tariff);
                    }
                }
            }

            return tariffs;

        } catch (FileNotFoundException | XMLStreamException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ParserException(e);

        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private Tariff buildTariff(XMLStreamReader reader) throws XMLStreamException {
        Tariff tariff;

        String monthlyFeeTextContent = reader.getAttributeValue(null, "monthlyFee");

        if (monthlyFeeTextContent != null) {
            tariff = new TariffWithMonthlyFee();

            int monthlyFee = Integer.parseInt(monthlyFeeTextContent);
            ((TariffWithMonthlyFee) tariff).setMonthlyFee(monthlyFee);
        } else {
            tariff = new Tariff();
        }

        tariff.setName(reader.getAttributeValue(null, "name"));

        while (reader.hasNext()) {
            int type = reader.next();
            String name;

            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    String text;

                    switch (name) {
                        case "operatorName":
                            tariff.setOperatorName(getText(reader));
                            break;
                        case "prices":
                            tariff.setPrices(getPrices(reader));
                            break;
                        case "connectionFee":
                            text = getText(reader);
                            if (text != null) {
                                tariff.setConnectionFee(Integer.parseInt(text));
                            }
                            break;
                        case "tariffication":
                            text = getText(reader);
                            TarifficationType tarifficationType = TarifficationType.getByString(text);
                            tariff.setType(tarifficationType);
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (isTariff(name)) {
                        return tariff;
                    }
                    break;
            }
        }

        return tariff;
    }

    private boolean isTariff(String name) {
        return "tariff".equals(name) || "tariffWithMonthlyFee".equals(name);
    }

    private TariffPrices getPrices(XMLStreamReader reader) throws XMLStreamException {
        TariffPrices prices = new TariffPrices();

        while(reader.hasNext()) {
            int type = reader.next();
            String name;

            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    String text = getText(reader);

                    if (text != null) {
                        int cost = Integer.parseInt(text);

                        switch (name) {
                            case "callToThisOperator":
                                prices.setCallToThisOperator(cost);
                                break;
                            case "callToOtherOperators":
                                prices.setCallToOtherOperators(cost);
                                break;
                            case "callToStationary":
                                prices.setCallToStationary(cost);
                                break;
                            case "sms":
                                prices.setSms(cost);
                                break;
                        }
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if ("prices".equals(name)) {
                        return prices;
                    }
            }
        }

        throw new XMLStreamException("Unknown element in prices");
    }

    private String getText(XMLStreamReader reader) throws XMLStreamException {
        if (reader.hasNext()) {
            reader.next();
            return reader.getText();
        }

        return null;
    }
}
