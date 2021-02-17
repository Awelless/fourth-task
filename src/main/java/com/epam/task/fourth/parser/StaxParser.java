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

    @Override
    public List<Tariff> parse(String filename) throws ParserException {

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

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

        String monthlyFeeTextContent = reader.getAttributeValue(null, XmlTagConstants.MONTHLY_FEE);

        if (monthlyFeeTextContent != null) {
            tariff = new TariffWithMonthlyFee();

            int monthlyFee = Integer.parseInt(monthlyFeeTextContent);
            ((TariffWithMonthlyFee) tariff).setMonthlyFee(monthlyFee);
        } else {
            tariff = new Tariff();
        }

        tariff.setName(reader.getAttributeValue(null, XmlTagConstants.NAME));

        while (reader.hasNext()) {
            int type = reader.next();
            String name;

            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    processStartElement(reader, tariff);
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

    private void processStartElement(XMLStreamReader reader, Tariff tariff) throws XMLStreamException {
        String name = reader.getLocalName();
        String text;

        switch (name) {
            case XmlTagConstants.OPERATOR_NAME:
                tariff.setOperatorName(getText(reader));
                break;
            case XmlTagConstants.PRICES:
                tariff.setPrices(getPrices(reader));
                break;
            case XmlTagConstants.CONNECTION_FEE:
                text = getText(reader);
                if (text != null) {
                    tariff.setConnectionFee(Integer.parseInt(text));
                }
                break;
            case XmlTagConstants.TARIFFICATION:
                text = getText(reader);
                TarifficationType tarifficationType = TarifficationType.getByString(text);
                tariff.setType(tarifficationType);
        }
    }

    private boolean isTariff(String name) {
        return XmlTagConstants.TARIFF.equals(name) ||
               XmlTagConstants.TARIFF_WITH_MONTHLY_FEE.equals(name);
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
                            case XmlTagConstants.CALL_TO_THIS_OPERATOR:
                                prices.setCallToThisOperator(cost);
                                break;
                            case XmlTagConstants.CALL_TO_OTHER_OPERATORS:
                                prices.setCallToOtherOperators(cost);
                                break;
                            case XmlTagConstants.CALL_TO_STATIONARY:
                                prices.setCallToStationary(cost);
                                break;
                            case XmlTagConstants.SMS:
                                prices.setSms(cost);
                                break;
                        }
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (XmlTagConstants.PRICES.equals(name)) {
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
