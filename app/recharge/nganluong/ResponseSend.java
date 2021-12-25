package recharge.nganluong;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class ResponseSend {

    private String error_code;
    private String token;
    private String description;
    private String time_limit;
    private String checkout_url;

    public ResponseSend(String xmlResult) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        InputSource is = new InputSource(new StringReader(xmlResult));
        Document document = null;
        try {
            document = documentBuilder.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        error_code = document.getElementsByTagName("error_code").item(0).getTextContent();
        checkout_url = document.getElementsByTagName("checkout_url").item(0).getTextContent();
        token = document.getElementsByTagName("token").item(0).getTextContent();
        description = document.getElementsByTagName("description").item(0).getTextContent();
        time_limit = document.getElementsByTagName("time_limit").item(0).getTextContent();
    }

    public String getError_code() {
        return error_code;
    }

    public String getToken() {
        return token;
    }

    public String getDescription() {
        return description;
    }

    public String getTime_limit() {
        return time_limit;
    }

    public String getCheckout_url() {
        return checkout_url;
    }
}
