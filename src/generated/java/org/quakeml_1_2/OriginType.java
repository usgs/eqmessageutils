//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.08.21 at 09:51:54 AM MDT 
//


package org.quakeml_1_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OriginType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="OriginType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="hypocenter"/&gt;
 *     &lt;enumeration value="centroid"/&gt;
 *     &lt;enumeration value="amplitude"/&gt;
 *     &lt;enumeration value="macroseismic"/&gt;
 *     &lt;enumeration value="rupture start"/&gt;
 *     &lt;enumeration value="rupture end"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "OriginType")
@XmlEnum
public enum OriginType {

    @XmlEnumValue("hypocenter")
    HYPOCENTER("hypocenter"),
    @XmlEnumValue("centroid")
    CENTROID("centroid"),
    @XmlEnumValue("amplitude")
    AMPLITUDE("amplitude"),
    @XmlEnumValue("macroseismic")
    MACROSEISMIC("macroseismic"),
    @XmlEnumValue("rupture start")
    RUPTURE_START("rupture start"),
    @XmlEnumValue("rupture end")
    RUPTURE_END("rupture end");
    private final String value;

    OriginType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static OriginType fromValue(String v) {
        for (OriginType c: OriginType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}