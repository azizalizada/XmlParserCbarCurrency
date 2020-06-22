package saxparser;

import objectclass.ValType;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class SaxParser {

    public static void main(String[] args) {
        try {
            SaxHandler saxHandler = new SaxHandler();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            String file = "https://www.cbar.az/currencies/19.06.2020.xml";
            parser.parse(file, saxHandler);

            List<ValType> valTypeList = saxHandler.getValTypeList();
            valTypeList.forEach(System.out::println);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
