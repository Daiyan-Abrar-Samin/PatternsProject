package org.patterns.smartexpensetracker.config;

import org.w3c.dom.*;
import org.w3c.dom.Node;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DatabaseConfig {
    private static String url;
    private static String user;
    private static String password;

    // static initializer block
    static {
        loadConfig();
    }

    // Load database credentials from config.xml file
    private static void loadConfig() {

        try {
            String fileName = "src/main/resources/config.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            document.getDocumentElement().normalize();

//            Node dbNode = document.getElementsByTagName("database").item(0);
//            Element dbElement = (Element) dbNode;

            url = document.getElementsByTagName("url").item(0).getTextContent().trim(); // or using element to get url, user and password from config.xml
            user = document.getElementsByTagName("user").item(0).getTextContent().trim();
            password = document.getElementsByTagName("password").item(0).getTextContent().trim();

        } catch (Exception e) {
            throw new RuntimeException("Failed to load DB config: " + e.getMessage(), e);
        }
    }

    // Getters for database url, user and password
    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }
}
