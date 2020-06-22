package objectclass;

import java.util.ArrayList;
import java.util.List;

public class ValType {
    private List<Valute> valuteList;
   private String att;

    public String getAtt() {
        return att;
    }

    public void setAtt(String att) {
        this.att = att;
    }

    public List<Valute> getValuteList() {
        return valuteList;
    }

    public void setValuteList(List<Valute> valuteList) {
        this.valuteList = valuteList;
    }

    @Override
    public String toString() {
        return "ValType = {" +
                " att = " + att +
                " valuteList = " + valuteList + " " +
                '}';
    }
}
