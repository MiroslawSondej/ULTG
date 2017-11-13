package com.mygdx.ultg;

import com.badlogic.gdx.Gdx;
import com.mygdx.ultg.entities.Settings;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class SettingsManager {

    public static Settings load(String fileName) {
        Settings settings = new Settings();

        try {
            File settingsFile = new File(fileName);
            if (settingsFile.exists() && settingsFile.canRead()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(settingsFile);

                NodeList nodeList = doc.getElementsByTagName("section");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if(nodeList.item(i).getAttributes().getNamedItem("id").getTextContent() == "audio") {
                        Element element = (Element) nodeList;
                        int volumeValue = Integer.parseInt(element.getElementsByTagName("volume").item(0).getTextContent());
                        settings.setVolume(volumeValue);
                    } else if(nodeList.item(i).getAttributes().getNamedItem("id").getTextContent() == "game") {
                        Element element = (Element) nodeList;
                        String languageValue = element.getElementsByTagName("language").item(0).getTextContent();
                        if(languageValue.length() < 1)
                            languageValue = "english";
                        settings.setLanguage(languageValue);
                    }
                }
            }
            else {
                throw(new Exception("Cannot read settings file!"));
            }
        } catch (Exception e) {
            Gdx.app.log("EXPCETION", e.getMessage());
        }

        return settings;
    }

    public static void save(String fileName, Settings settings) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("settings");
            doc.appendChild(rootElement);

            // Audio
            Element audioSection = doc.createElement("section");
            rootElement.appendChild(audioSection);

            Attr audioSectionAttrId = doc.createAttribute("id");
            audioSectionAttrId.setValue("audio");
            audioSection.setAttributeNode(audioSectionAttrId);

            Element volumeElement = doc.createElement("volume");
            volumeElement.appendChild(doc.createTextNode("100"));
            audioSection.appendChild(volumeElement);

            // Video

            // Game
            Element gameSection = doc.createElement("section");
            rootElement.appendChild(gameSection);

            Attr gameSectionAttrId = doc.createAttribute("id");
            gameSectionAttrId.setValue("game");
            gameSection.setAttributeNode(gameSectionAttrId);

            Element languageElement = doc.createElement("language");
            languageElement.appendChild(doc.createTextNode("english"));
            gameSection.appendChild(languageElement);

            // Save to file

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fileName));
            transformer.transform(source, result);

        } catch (Exception e) {
            Gdx.app.log("EXPCETION", e.getMessage());
        }
    }

}
