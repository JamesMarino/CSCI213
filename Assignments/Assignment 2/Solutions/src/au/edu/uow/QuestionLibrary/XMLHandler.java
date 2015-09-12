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

/**
 * Reads XML and Processes it
 * @author Subject Code: CSCI213
 * @author Name: James Marino
 * @author Student Number: 4720994
 * @author Login: jm617
 */
public class XMLHandler
{
    private String FileName;
    private String FileContents;
    private List<Question> QuestionList;

    public XMLHandler(String fileName)
    {
        QuestionList = new ArrayList<Question>();
        FileName = fileName;
        FileContents = "";
    }

    /**
     * Getter - Question List
     * @return Question List
     */
    public List<Question> getLibrary()
    {
        return QuestionList;
    }

    /**
     * Reads file and parses XML
     * @return boolean Successful Read
     */
    public boolean readFile()
    {
        File file = new File(FileName);
        String temp;

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));

            while ((temp = in.readLine()) != null) {
                FileContents = FileContents.concat(temp);

                    if (temp.length() > 0) {
                        char c = temp.charAt(0);

                        if (c != '<') {
                            FileContents = FileContents.concat("\n");
                        }
                    }
            }

            in.close();

            return true;

        } catch (FileNotFoundException ioe) {
            return false;
        } catch (IOException ioe) {
            return false;
        }
    }

    /**
     * Creates List to be used in test
     * @return successful creation
     */
    public boolean createLibrary()
    {
        Document DOM;
        DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder DB = DBF.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(FileContents));

            // Parse
            DOM = DB.parse(is);

            // Get All True and False Questions
            NodeList allTrueFalse = DOM.getElementsByTagName("TFQuestion");

            for (int i = 0; i < allTrueFalse.getLength(); i++) {

                List<String> questionTF = new ArrayList<String>();
                String answerTF = "";


                NodeList currentQuestion = allTrueFalse.item(i).getChildNodes();

                Node [] questionList = {
                        currentQuestion.item(0),
                        currentQuestion.item(1)
                };

                // Add Questions and Set Answer
                for (int j = 0; j < 2; j++) {
                    if (questionList[j].getNodeName().equals("answer")) {
                        answerTF = questionList[j].getTextContent();
                    } else if (questionList[j].getNodeName().equals("question")) {
                        questionTF.add(questionList[j].getTextContent());
                    }
                }


                TrueAndFalseQuestion tfQuestion = new TrueAndFalseQuestion(questionTF, answerTF);
                QuestionList.add(tfQuestion);

            }

            // Get All Multiple choice
            NodeList allMultipleChoice = DOM.getElementsByTagName("MQuestion");

            for (int i = 0; i < allMultipleChoice.getLength(); i++) {

                List<String> questionMC = new ArrayList<String>();
                List<String> choicesMC = new ArrayList<String>();
                int answerMC = 0;



                NodeList currentQuestion = allMultipleChoice.item(i).getChildNodes();

                Node [] questionList = {
                        currentQuestion.item(0),
                        currentQuestion.item(1),
                        currentQuestion.item(2)
                };

                for (int j = 0; j < 3; j++) {
                    if (questionList[j].getNodeName().equals("answer")) {
                        answerMC = Integer.parseInt(questionList[j].getTextContent().replaceAll("[^\\d.]", ""));
                    } else if (questionList[j].getNodeName().equals("question")) {
                        questionMC.add(questionList[j].getTextContent());
                    } else if (questionList[j].getNodeName().equals("choices")) {

                        String choices[] = questionList[j].getTextContent().split("\n");

                        for (String s: choices) {
                            choicesMC.add(s);
                        }
                    }
                }

                MultipleChoiceQuestion mcQuestion  = new MultipleChoiceQuestion(questionMC, choicesMC, answerMC);
                QuestionList.add(mcQuestion);

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
