//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.05 at 11:56:09 AM MST 
//


package gov.usgs.ansseqmsg;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EventScopeSimType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EventScopeSimType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Public"/&gt;
 *     &lt;enumeration value="Internal"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "EventScopeSimType")
@XmlEnum
public enum EventScope {

    @XmlEnumValue("Public")
    PUBLIC("Public"),
    @XmlEnumValue("Internal")
    INTERNAL("Internal");
    private final String value;

    EventScope(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EventScope fromValue(String v) {
        for (EventScope c: EventScope.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
