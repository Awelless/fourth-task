package com.epam.task.fourth.parser;

import com.epam.task.fourth.entity.Tariff;
import com.epam.task.fourth.entity.TariffPrices;
import com.epam.task.fourth.entity.TariffWithMonthlyFee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DomParser implements XmlParser {

    private static final Logger LOGGER = LogManager.getLogger(XmlParser.class);

    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public DomParser() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    }

    @Override
    public List<Tariff> parse(String filename) throws ParserException {
        Document document;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            List<Tariff> tariffs = new ArrayList<>();

            document = builder.parse(filename);
            Element root = document.getDocumentElement();

            NodeList tariffList = root.getElementsByTagName("tariff");

            for (int i = 0; i < tariffList.getLength(); ++i) {
                Element tariffElement = (Element) tariffList.item(i);
                Tariff tariff = buildTariff(tariffElement);
                tariffs.add(tariff);
            }

            NodeList tariffWithMonthlyFeeList = root.getElementsByTagName("tariffWithMonthlyFee");

            for (int i = 0; i < tariffWithMonthlyFeeList.getLength(); ++i) {
                Element tariffElement = (Element) tariffWithMonthlyFeeList.item(i);
                Tariff tariff = buildTariff(tariffElement);
                tariffs.add(tariff);
            }

            return tariffs;

        } catch (SAXException | IOException | ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ParserException(e);
        }
    }

    private Tariff buildTariff(Element element) {
        Tariff tariff;

        if (element.hasAttribute("monthlyFee")) {
            tariff = new TariffWithMonthlyFee();

            String monthlyFeeTextContent = element.getAttribute("monthlyFee");
            int monthlyFee = Integer.parseInt(monthlyFeeTextContent);
            ((TariffWithMonthlyFee) tariff).setMonthlyFee(monthlyFee);

        } else {
            tariff = new Tariff();
        }

        tariff.setName(element.getAttribute("name"));
        tariff.setOperatorName(getElementTextContent(element, "operatorName"));

        tariff.setPrices(getPrices(element));

        tariff.setConnectionFee(getElementIntContent(element, "connectionFee"));

        return tariff;
    }

    private TariffPrices getPrices(Element element) {
        Element pricesElement = (Element) element.getElementsByTagName("prices").item(0);

        TariffPrices prices = new TariffPrices();

        prices.setCallToThisOperator(getElementIntContent(pricesElement, "callToThisOperator"));
        prices.setCallToOtherOperators(getElementIntContent(pricesElement, "callToOtherOperators"));
        prices.setCallToStationary(getElementIntContent(pricesElement, "callToStationary"));
        prices.setSms(getElementIntContent(pricesElement, "sms"));

        return prices;
    }

    private String getElementTextContent(Element element, String name) {
        NodeList nodes = element.getElementsByTagName(name);
        Node node = nodes.item(0);
        return node.getTextContent();
    }

    private int getElementIntContent(Element element, String name) {
        String textContent = getElementTextContent(element, name);
        return Integer.parseInt(textContent);
    }
}
