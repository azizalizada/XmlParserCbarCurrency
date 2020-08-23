package jdbc;

import objectclass.ValType;
import objectclass.Valute;
import staxparser.StaxParser;

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

        try {
            URL url = new URL("https://www.cbar.az/currencies/19.06.2020.xml");
            List<ValType> valTypeList2;
            StaxParser parser = new StaxParser();
            valTypeList2 = parser.StaxParse(url);

            String ur = "jdbc:postgresql://localhost:5432/cbardb";
            Properties parameters = new Properties();
            parameters.put("user", "*");
            parameters.put("password", "*");
            Connection con = DriverManager.getConnection(ur, parameters);
            if (con != null) {
                System.out.println("successuful ");
            }


            List<Valute> valuteList1 = new ArrayList<>();
            for (int i = 0; i < valTypeList2.size(); i++) {
                ValType valType = valTypeList2.get(i);
                valuteList1 = valType.getValuteList();
                }

            con.setAutoCommit(false);
            int count = 0;
            String sql = "insert into valute (nominal, name, value1)\n" +
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
