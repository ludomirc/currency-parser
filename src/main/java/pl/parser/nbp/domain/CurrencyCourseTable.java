package pl.parser.nbp.domain;

import javax.xml.bind.annotation.*;
import java.util.Collection;

/**
 * Created by Benek on 09.04.2017.
 */
@XmlRootElement(name = "tabela_kursow")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyCourseTable {

    @XmlAttribute(name = "typ")
    String type;

    @XmlElement(name = "numer_tabeli")
    String id;

    @XmlElement(name = "data_notowania")
    String quotationDate;

    @XmlElement(name = "data_publikacji")
    String publicationDate;

    @XmlElement(name = "pozycja")
    Collection<CurrencyEntry> currencyEntry;

    public CurrencyCourseTable() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(String quotationDate) {
        this.quotationDate = quotationDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Collection<CurrencyEntry> getCurrencyEntry() {
        return currencyEntry;
    }

    public void setCurrencyEntry(Collection<CurrencyEntry> currencyEntry) {
        this.currencyEntry = currencyEntry;
    }

    @Override
    public String toString() {
        return "CurrencyCourseTable{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", quotationDate='" + quotationDate + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", currencyEntry=" + currencyEntry +
                '}';
    }
}
