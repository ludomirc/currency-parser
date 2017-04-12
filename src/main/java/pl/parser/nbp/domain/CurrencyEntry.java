package pl.parser.nbp.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Benek on 09.04.2017.
 */
@XmlRootElement(name = "pozycja")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyEntry {

    @XmlElement(name = "nazwa_waluty")
    private String currencyName;

    @XmlElement(name = "przelicznik")
    private String converter;

    @XmlElement(name = "kod_waluty")
    private String code;

    @XmlElement(name = "kurs_kupna")
    @XmlJavaTypeAdapter(PlNumberAdapter.class)
    private String buyingRate;

    @XmlElement(name = "kurs_sprzedazy")
    @XmlJavaTypeAdapter(PlNumberAdapter.class)
    private String salaryRate;

    public CurrencyEntry() {
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getConverter() {
        return converter;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(String buyingRate) {
        this.buyingRate = buyingRate;
    }

    public String getSalaryRate() {
        return salaryRate;
    }

    public void setSalaryRate(String salaryRate) {
        this.salaryRate = salaryRate;
    }

    @Override
    public String toString() {
        return "CurrencyEntry{" +

                "currencyName='" + currencyName + '\'' +
                ", converter=" + converter +
                ", code='" + code + '\'' +
                ", buyingRate=" + buyingRate +
                ", salaryRate=" + salaryRate +
                '}';
    }
}
