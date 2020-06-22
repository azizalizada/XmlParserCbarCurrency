package objectclass;

import java.math.BigDecimal;

public class Valute {
    private String nominal;
    private String name;
    private BigDecimal value;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Valute() {
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Valute = { " +
                "Code = " + code +
                " , nominal = " + nominal + '\'' +
                " , name = " + name + '\'' +
                " , value = " + value +
                " }";
    }
}
