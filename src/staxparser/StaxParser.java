package staxparser;

import objectclass.ValType;
import objectclass.Valute;
import org.xml.sax.XMLReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {
    public static void main(String[] args) {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        try {
            XMLStreamReader reader = factory.
                    createXMLStreamReader(new URL("https://www.cbar.az/currencies/19.06.2020.xml").openStream());

             List<ValType> valTypeList = new ArrayList<>();
             List<Valute> valuteList = new ArrayList<>();
             Valute tempValute = null;
             ValType tempValType = null;
             boolean isValType = false;
             boolean isValute = false;
             boolean isNominal = false;
             boolean isName = false;
             boolean isValue = false;
            
            while (reader.hasNext()){
                int elementType = reader.next();

                if(elementType == XMLEvent.START_DOCUMENT){
                    System.out.println("Start document");
                } else if(elementType == XMLEvent.START_ELEMENT){
                    System.out.println("Start element " + reader.getName());
                    if (reader.getName().toString().equals("ValType")) {
                        isValType = true;
                        tempValType = new ValType();
                        String att = reader.getAttributeValue(null,"Type");
                        tempValType.setAtt(att);
                        System.out.println("temp ValType  = " + tempValType);
                    }else if(reader.getName().toString().equals("Valute")){
                        isValute = true;
                        tempValute = new Valute();
                        String code = reader.getAttributeValue(null,"Code");
                        tempValute.setCode(code);
                        System.out.println("temp Valute = " + tempValute);
                    }else if(reader.getName().toString().equals("Nominal")){
                        isNominal = true;
                    }else if(reader.getName().toString().equals("Name")){
                        isName = true;
                    }else if(reader.getName().toString().equals("Value")){
                        isValue = true;
                    }
                } else if(elementType == XMLEvent.CHARACTERS){
                    System.out.println(" data = " + reader.getText());
                    String data = reader.getText();
                    if(isNominal){
                        tempValute.setNominal(data);
                    }else if(isName){
                        tempValute.setName(data);
                    }else if(isValue){
                        tempValute.setValue(new BigDecimal(data));
                    }
                } else if(elementType == XMLEvent.END_ELEMENT){
                    System.out.println("End element " +reader.getName());
                    if(reader.getName().toString().equals("ValType")){
                        isValType = false;
                        valTypeList.add(tempValType);
                        tempValType = null;
                        System.out.println("valtype list = "+ valTypeList);
                    }else if(reader.getName().toString().equals("Valute")){
                        isValute = false;
                        valuteList.add(tempValute);
                        System.out.println("valute list  = " + valuteList);
                        tempValType.setValuteList(valuteList);
                        System.out.println( " temp valtype = " + tempValType);
                        tempValute = null;
                    }else if(reader.getName().toString().equals("Nominal")){
                        isNominal = false;
                    }else if(reader.getName().toString().equals("Name")){
                        isName = false;
                    }else if(reader.getName().toString().equals("Value")){
                        isValue = false;
                    }
                } else  if (elementType == XMLEvent.END_DOCUMENT){
                    System.out.println("End document");
                }

            }
            System.out.println("list hazirdir");
            System.out.println(valTypeList);
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
