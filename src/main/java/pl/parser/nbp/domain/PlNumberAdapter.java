package pl.parser.nbp.domain;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Benek on 09.04.2017.
 */
class PlNumberAdapter extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        return v.replace(",",".");
    }

    @Override
    public String marshal(String v) throws Exception {
        return v.replace(".",",");
    }
}
