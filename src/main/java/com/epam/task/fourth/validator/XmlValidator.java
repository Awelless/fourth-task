package com.epam.task.fourth.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

public class XmlValidator {

    private static final Logger LOGGER = LogManager.getLogger(XmlValidator.class);

    private final SchemaFactory    schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private final SAXParserFactory parserFactory = SAXParserFactory.newInstance();

    public boolean isValid(String filename, String schemaFilename) {
        try {
            Schema schema = schemaFactory.newSchema(new File(schemaFilename));
            parserFactory.setSchema(schema);
            parserFactory.setNamespaceAware(true);

            SAXParser parser = parserFactory.newSAXParser();

            LOGGER.info("Started validating file: " + filename);

            parser.parse(filename, new TariffErrorHandler());

            return true;

        } catch (SAXException | ParserConfigurationException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            LOGGER.info("Finished validating file: " + filename);
        }
    }
}
