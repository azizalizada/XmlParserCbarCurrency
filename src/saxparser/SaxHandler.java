package saxparser;

import objectclass.ValType;
import objectclass.Valute;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SaxHandler extends DefaultHandler {
    private List<ValType> valTypeList;
    private List<Valute> valuteList;
    private Valute tempValute;
    private ValType tempValType;
    private boolean isValType;
    private boolean isValute;
    private boolean isNominal;
    private boolean isName;
    private boolean isValue;


    @Override
    public void startDocument() throws SAXException {
        this.valTypeList = new ArrayList<>();
        this.valuteList = new ArrayList<>();
        boolean isValType = false;
        boolean isValute = false;
        this.isNominal = false;
        this.isName = false;
        this.isValue = false;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        System.out.println("start element" + qName);
        if (qName.equals("ValType")) {
            isValType = true;
            tempValType = new ValType();
            System.out.println("temp ValType  = " + tempValType);
        }else if(qName.equals("Valute")){
            isValute = true;
            tempValute = new Valute();
            System.out.println("temp Valute = " + tempValute);
        }else if(qName.equals("Nominal")){
            isNominal = true;
        }else if(qName.equals("Name")){
            isName = true;
        }else if(qName.equals("Value")){
            isValue = true;
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);
        System.out.println("data = " + data);

        if(isNominal){
            tempValute.setNominal(data);
        }else if(isName){
            tempValute.setName(data);
        }else if(isValue){
            tempValute.setValue(new BigDecimal(data));
        }

        System.out.println("temp valute = " + tempValute);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        System.out.println("end element " + qName);

        if(qName.equals("Valtype")){
            isValType = false;
            valTypeList.add(tempValType);
            tempValType = null;
            System.out.println("valtype list = "+ valTypeList);
        }else if(qName.equals("Valute")){
            isValute = false;
            valuteList.add(tempValute);
            tempValType.setValuteList(valuteList);
            tempValute = null;
        }else if(qName.equals("Nominal")){
            isNominal = false;
        }else if(qName.equals("Name")){
            isName = false;
        }else if(qName.equals("Value")){
            isValue = false;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End document ");
    }

    public List<ValType> getValTypeList() {
        return valTypeList;
    }
}
