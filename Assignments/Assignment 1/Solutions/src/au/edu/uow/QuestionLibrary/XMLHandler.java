package au.edu.uow.QuestionLibrary;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.*;

public class XMLHandler
{
    private String FileName;
    private String FileContents;
    private List<Question> QuestionList;

    public XMLHandler(String fileName)
    {
        QuestionList = new ArrayList<>();
        FileName = fileName;
        FileContents = "";
    }

    public List<Question> getLibrary()
    {
        return QuestionList;
    }

    public boolean readFile()
    {
        File file = new File(FileName);
        String temp;

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));

            while ((temp = in.readLine()) != null) {
                FileContents = FileContents.concat(temp);
                //FileContents = FileContents.concat("\n");
            }

            System.out.println(FileContents);
            in.close();

            return true;

        } catch (FileNotFoundException ioe) {
            return false;
        } catch (IOException ioe) {
            return false;
        }
    }

    public boolean createLibrary()
    {
        Document DOM;
        DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder DB = DBF.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(FileContents));

            // Parse
            DOM = DB.parse(is);

            // Get All Multiple choice
            NodeList allTrueFalse = DOM.getElementsByTagName("TFQuestion");

            for (int i = 0; i < allTrueFalse.getLength(); i++) {

                TrueAndFalseQuestion tfQuestion  = new TrueAndFalseQuestion();
                NodeList currentQuestion = allTrueFalse.item(i).getChildNodes();

                Node [] questionList = {
                        currentQuestion.item(0),
                        currentQuestion.item(1)
                };

                for (int j = 0; j < 2; j++) {
                    if (questionList[j].getNodeName().equals("answer")) {
                        tfQuestion.setAnswer(questionList[j].getTextContent());
                    } else if (questionList[j].getNodeName().equals("question")) {
                        tfQuestion.setQuestion(questionList[j].getTextContent());
                    }
                }

                QuestionList.add(tfQuestion);

            }

            return true;
        } catch (ParserConfigurationException pce) {
            return false;
        } catch (SAXException se) {
            return false;
        } catch (IOException ioe) {
            return false;
        }
    }
}
