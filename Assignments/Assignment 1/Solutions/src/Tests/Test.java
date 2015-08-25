package Tests;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Test {
    public static void main(String[] args)
    {
        System.out.println();

        String XMLFromFile = "";

        Document DOM;
        DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder DB = DBF.newDocumentBuilder();

            // Parse
            DOM = DB.parse(XMLFromFile);

            Element test = DOM.getElementById("test");
            test.getTextContent();


        } catch (ParserConfigurationException pce) {

        } catch (SAXException se) {

        } catch (IOException ioe) {

        }
    }
}
