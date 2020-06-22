package jdbc;

import objectclass.ValType;
import objectclass.Valute;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Jdbc {

    public static void main(String[] args) {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        List<ValType> valTypeList = new ArrayList<>();
        List<Valute> valuteList = new ArrayList<>();
        Valute tempValute = null;
        ValType tempValType = null;
        boolean isValType = false;
        boolean isValute = false;
        boolean isNominal = false;
        boolean isName = false;
        boolean isValue = false;
        try {
            XMLStreamReader reader = factory.
                    createXMLStreamReader(new URL("https://www.cbar.az/currencies/19.06.2020.xml").openStream());


            while (reader.hasNext()) {
                int elementType = reader.next();

                if (elementType == XMLEvent.START_DOCUMENT) {
                    System.out.println("Start document");
                } else if (elementType == XMLEvent.START_ELEMENT) {
                    System.out.println("Start element " + reader.getName());
                    if (reader.getName().toString().equals("ValType")) {
                        isValType = true;
                        tempValType = new ValType();
                        String att = reader.getAttributeValue(null, "Type");
                        tempValType.setAtt(att);
                        System.out.println("temp ValType  = " + tempValType);
                    } else if (reader.getName().toString().equals("Valute")) {
                        isValute = true;
                        tempValute = new Valute();
                        String code = reader.getAttributeValue(null, "Code");
                        tempValute.setCode(code);
                        System.out.println("temp Valute = " + tempValute);
                    } else if (reader.getName().toString().equals("Nominal")) {
                        isNominal = true;
                    } else if (reader.getName().toString().equals("Name")) {
                        isName = true;
                    } else if (reader.getName().toString().equals("Value")) {
                        isValue = true;
                    }
                } else if (elementType == XMLEvent.CHARACTERS) {
                    System.out.println(" data = " + reader.getText());
                    String data = reader.getText();
                    if (isNominal) {
                        tempValute.setNominal(data);
                    } else if (isName) {
                        tempValute.setName(data);
                    } else if (isValue) {
                        tempValute.setValue(new BigDecimal(data));
                    }
                } else if (elementType == XMLEvent.END_ELEMENT) {
                    System.out.println("End element " + reader.getName());
                    if (reader.getName().toString().equals("ValType")) {
                        isValType = false;
                        valTypeList.add(tempValType);
                        tempValType = null;
                        System.out.println("valtype list = " + valTypeList);
                    } else if (reader.getName().toString().equals("Valute")) {
                        isValute = false;
                        valuteList.add(tempValute);
                        System.out.println("valute list  = " + valuteList);
                        tempValType.setValuteList(valuteList);
                        System.out.println(" temp valtype = " + tempValType);
                        tempValute = null;
                    } else if (reader.getName().toString().equals("Nominal")) {
                        isNominal = false;
                    } else if (reader.getName().toString().equals("Name")) {
                        isName = false;
                    } else if (reader.getName().toString().equals("Value")) {
                        isValue = false;
                    }
                } else if (elementType == XMLEvent.END_DOCUMENT) {
                    System.out.println("End document");
                }

            }
            System.out.println("list hazirdir");
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String url = "jdbc:postgresql://localhost:5432/currency";
        Properties parameters = new Properties();
        parameters.put("user", "postgres");
        parameters.put("password", "eziz12eziz");
        try {
            Connection con = DriverManager.getConnection(url, parameters);
            if (con != null) {
                System.out.println("successuful ");
            }

            List<Valute> valuteList1 = new ArrayList<>();
            for (int i = 0; i < valTypeList.size(); i++) {
                ValType valType = valTypeList.get(i);
                valuteList1 = valType.getValuteList();
                }

            con.setAutoCommit(false);
            int count = 0;
            String sql = "insert into valute \n" +
                    "values (?,?,?);";

            for (int i = 0; i < valuteList1.size(); i++) {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1,valuteList1.get(i).getNominal());
                ps.setString(2,valuteList1.get(i).getName());
                ps.setBigDecimal(3, valuteList1.get(i).getValue());
                count += ps.executeUpdate();
            }

            System.out.println(count + " line inserted");
            con.commit();
            con.close();
        } catch (SQLException e) {
            System.out.println("Qosulmadi ,duz yaz");
            e.printStackTrace();
        }
    }

}
