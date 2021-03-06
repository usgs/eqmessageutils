//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.05 at 11:56:10 AM MST 
//


package org.quakeml_1_1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MomentTensorStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MomentTensorStatus"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="standard CMT solution"/&gt;
 *     &lt;enumeration value="quick CMT solution"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MomentTensorStatus")
@XmlEnum
public enum MomentTensorStatus {

    @XmlEnumValue("standard CMT solution")
    STANDARD_CMT_SOLUTION("standard CMT solution"),
    @XmlEnumValue("quick CMT solution")
    QUICK_CMT_SOLUTION("quick CMT solution");
    private final String value;

    MomentTensorStatus(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MomentTensorStatus fromValue(String v) {
        for (MomentTensorStatus c: MomentTensorStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
