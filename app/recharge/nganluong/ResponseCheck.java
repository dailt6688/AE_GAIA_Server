package recharge.nganluong;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class ResponseCheck {

    private String errorCode;
    private String token;
    private String description;
    private String transactionStatus;
    private String order_code;
    private String transaction_id;

    public ResponseCheck(String xmlResult) {
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

        errorCode = document.getElementsByTagName("error_code").item(0).getTextContent();
        token = document.getElementsByTagName("token").item(0).getTextContent();
        description = document.getElementsByTagName("description").item(0).getTextContent();
        transactionStatus = document.getElementsByTagName("transaction_status").item(0).getTextContent();
        order_code = document.getElementsByTagName("order_code").item(0).getTextContent();
        transaction_id = document.getElementsByTagName("transaction_id").item(0).getTextContent();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getToken() {
        return token;
    }

    public String getDescription() {
        return description;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getOrder_code() {
        return order_code;
    }

    public String getTransactionId() {
        return transaction_id;
    }
}
