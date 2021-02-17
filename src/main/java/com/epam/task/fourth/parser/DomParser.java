package com.epam.task.fourth.parser;

import com.epam.task.fourth.entity.Tariff;
import com.epam.task.fourth.entity.TariffPrices;
import com.epam.task.fourth.entity.TariffWithMonthlyFee;
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

    public DomParser() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    }

    @Override
    public List<Tariff> parse(String filename) throws ParserException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        Document document;

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            List<Tariff> tariffs = new ArrayList<>();

            document = builder.parse(filename);
            Element root = document.getDocumentElement();

            NodeList tariffList = root.getElementsByTagName(XmlTagConstants.TARIFF);

            for (int i = 0; i < tariffList.getLength(); ++i) {
                Element tariffElement = (Element) tariffList.item(i);
                Tariff tariff = buildTariff(tariffElement);
                tariffs.add(tariff);
            }

            NodeList tariffWithMonthlyFeeList = root.getElementsByTagName(XmlTagConstants.TARIFF_WITH_MONTHLY_FEE);

            for (int i = 0; i < tariffWithMonthlyFeeList.getLength(); ++i) {
                Element tariffElement = (Element) tariffWithMonthlyFeeList.item(i);
                Tariff tariff = buildTariff(tariffElement);
                tariffs.add(tariff);
            }

            return tariffs;

        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ParserException(e);
        }
    }

    private Tariff buildTariff(Element element) {
        Tariff tariff;

        if (element.hasAttribute(XmlTagConstants.MONTHLY_FEE)) {
            tariff = new TariffWithMonthlyFee();

            String monthlyFeeTextContent = element.getAttribute(XmlTagConstants.MONTHLY_FEE);
            int monthlyFee = Integer.parseInt(monthlyFeeTextContent);
            ((TariffWithMonthlyFee) tariff).setMonthlyFee(monthlyFee);

        } else {
            tariff = new Tariff();
        }

        tariff.setName(element.getAttribute(XmlTagConstants.NAME));
        tariff.setOperatorName(getElementTextContent(element, XmlTagConstants.OPERATOR_NAME));

        tariff.setPrices(getPrices(element));

        tariff.setConnectionFee(getElementIntContent(element, XmlTagConstants.CONNECTION_FEE));

        return tariff;
    }

    private TariffPrices getPrices(Element element) {
        Element pricesElement = (Element) element.getElementsByTagName(XmlTagConstants.PRICES).item(0);

        TariffPrices prices = new TariffPrices();

        prices.setCallToThisOperator(getElementIntContent(pricesElement, XmlTagConstants.CALL_TO_THIS_OPERATOR));
        prices.setCallToOtherOperators(getElementIntContent(pricesElement, XmlTagConstants.CALL_TO_OTHER_OPERATORS));
        prices.setCallToStationary(getElementIntContent(pricesElement, XmlTagConstants.CALL_TO_STATIONARY));
        prices.setSms(getElementIntContent(pricesElement, XmlTagConstants.SMS));

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
