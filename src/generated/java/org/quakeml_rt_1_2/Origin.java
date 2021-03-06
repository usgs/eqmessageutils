//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.08.21 at 09:51:58 AM MDT 
//


package org.quakeml_rt_1_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for Origin complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Origin"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="compositeTime" type="{http://quakeml.org/xmlns/bed-rt/1.2}CompositeTime" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="comment" type="{http://quakeml.org/xmlns/bed-rt/1.2}Comment" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="originUncertainty" type="{http://quakeml.org/xmlns/bed-rt/1.2}OriginUncertainty" minOccurs="0"/&gt;
 *         &lt;element name="arrival" type="{http://quakeml.org/xmlns/bed-rt/1.2}Arrival" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="time" type="{http://quakeml.org/xmlns/bed-rt/1.2}TimeQuantity"/&gt;
 *         &lt;element name="longitude" type="{http://quakeml.org/xmlns/bed-rt/1.2}RealQuantity"/&gt;
 *         &lt;element name="latitude" type="{http://quakeml.org/xmlns/bed-rt/1.2}RealQuantity"/&gt;
 *         &lt;element name="depth" type="{http://quakeml.org/xmlns/bed-rt/1.2}RealQuantity" minOccurs="0"/&gt;
 *         &lt;element name="depthType" type="{http://quakeml.org/xmlns/bed-rt/1.2}OriginDepthType" minOccurs="0"/&gt;
 *         &lt;element name="timeFixed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="epicenterFixed" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="referenceSystemID" type="{http://quakeml.org/xmlns/bed-rt/1.2}ResourceReference" minOccurs="0"/&gt;
 *         &lt;element name="methodID" type="{http://quakeml.org/xmlns/bed-rt/1.2}ResourceReference" minOccurs="0"/&gt;
 *         &lt;element name="earthModelID" type="{http://quakeml.org/xmlns/bed-rt/1.2}ResourceReference" minOccurs="0"/&gt;
 *         &lt;element name="quality" type="{http://quakeml.org/xmlns/bed-rt/1.2}OriginQuality" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{http://quakeml.org/xmlns/bed-rt/1.2}OriginType" minOccurs="0"/&gt;
 *         &lt;element name="region" minOccurs="0"&gt;
 *           &lt;simpleType&gt;
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *               &lt;maxLength value="128"/&gt;
 *             &lt;/restriction&gt;
 *           &lt;/simpleType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="evaluationMode" type="{http://quakeml.org/xmlns/bed-rt/1.2}EvaluationMode" minOccurs="0"/&gt;
 *         &lt;element name="evaluationStatus" type="{http://quakeml.org/xmlns/bed-rt/1.2}EvaluationStatus" minOccurs="0"/&gt;
 *         &lt;element name="creationInfo" type="{http://quakeml.org/xmlns/bed-rt/1.2}CreationInfo" minOccurs="0"/&gt;
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="publicID" use="required" type="{http://quakeml.org/xmlns/bed-rt/1.2}ResourceReference" /&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Origin", propOrder = {
    "compositeTimes",
    "comments",
    "originUncertainty",
    "arrivals",
    "time",
    "longitude",
    "latitude",
    "depth",
    "depthType",
    "timeFixed",
    "epicenterFixed",
    "referenceSystemID",
    "methodID",
    "earthModelID",
    "quality",
    "type",
    "region",
    "evaluationMode",
    "evaluationStatus",
    "creationInfo",
    "anies"
})
public class Origin {

    @XmlElement(name = "compositeTime")
    protected List<CompositeTime> compositeTimes;
    @XmlElement(name = "comment")
    protected List<Comment> comments;
    protected OriginUncertainty originUncertainty;
    @XmlElement(name = "arrival")
    protected List<Arrival> arrivals;
    @XmlElement(required = true)
    protected TimeQuantity time;
    @XmlElement(required = true)
    protected RealQuantity longitude;
    @XmlElement(required = true)
    protected RealQuantity latitude;
    protected RealQuantity depth;
    @XmlSchemaType(name = "string")
    protected OriginDepthType depthType;
    protected Boolean timeFixed;
    protected Boolean epicenterFixed;
    @XmlSchemaType(name = "anyURI")
    protected String referenceSystemID;
    @XmlSchemaType(name = "anyURI")
    protected String methodID;
    @XmlSchemaType(name = "anyURI")
    protected String earthModelID;
    protected OriginQuality quality;
    @XmlSchemaType(name = "string")
    protected OriginType type;
    protected String region;
    @XmlSchemaType(name = "string")
    protected EvaluationMode evaluationMode;
    @XmlSchemaType(name = "string")
    protected EvaluationStatus evaluationStatus;
    protected CreationInfo creationInfo;
    @XmlAnyElement
    protected List<Element> anies;
    @XmlAttribute(name = "publicID", required = true)
    protected String publicID;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the compositeTimes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the compositeTimes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompositeTimes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CompositeTime }
     * 
     * 
     */
    public List<CompositeTime> getCompositeTimes() {
        if (compositeTimes == null) {
            compositeTimes = new ArrayList<CompositeTime>();
        }
        return this.compositeTimes;
    }

    /**
     * Gets the value of the comments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comment }
     * 
     * 
     */
    public List<Comment> getComments() {
        if (comments == null) {
            comments = new ArrayList<Comment>();
        }
        return this.comments;
    }

    /**
     * Gets the value of the originUncertainty property.
     * 
     * @return
     *     possible object is
     *     {@link OriginUncertainty }
     *     
     */
    public OriginUncertainty getOriginUncertainty() {
        return originUncertainty;
    }

    /**
     * Sets the value of the originUncertainty property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginUncertainty }
     *     
     */
    public void setOriginUncertainty(OriginUncertainty value) {
        this.originUncertainty = value;
    }

    /**
     * Gets the value of the arrivals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arrivals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArrivals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Arrival }
     * 
     * 
     */
    public List<Arrival> getArrivals() {
        if (arrivals == null) {
            arrivals = new ArrayList<Arrival>();
        }
        return this.arrivals;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link TimeQuantity }
     *     
     */
    public TimeQuantity getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeQuantity }
     *     
     */
    public void setTime(TimeQuantity value) {
        this.time = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link RealQuantity }
     *     
     */
    public RealQuantity getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link RealQuantity }
     *     
     */
    public void setLongitude(RealQuantity value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link RealQuantity }
     *     
     */
    public RealQuantity getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link RealQuantity }
     *     
     */
    public void setLatitude(RealQuantity value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the depth property.
     * 
     * @return
     *     possible object is
     *     {@link RealQuantity }
     *     
     */
    public RealQuantity getDepth() {
        return depth;
    }

    /**
     * Sets the value of the depth property.
     * 
     * @param value
     *     allowed object is
     *     {@link RealQuantity }
     *     
     */
    public void setDepth(RealQuantity value) {
        this.depth = value;
    }

    /**
     * Gets the value of the depthType property.
     * 
     * @return
     *     possible object is
     *     {@link OriginDepthType }
     *     
     */
    public OriginDepthType getDepthType() {
        return depthType;
    }

    /**
     * Sets the value of the depthType property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginDepthType }
     *     
     */
    public void setDepthType(OriginDepthType value) {
        this.depthType = value;
    }

    /**
     * Gets the value of the timeFixed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getTimeFixed() {
        return timeFixed;
    }

    /**
     * Sets the value of the timeFixed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTimeFixed(Boolean value) {
        this.timeFixed = value;
    }

    /**
     * Gets the value of the epicenterFixed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getEpicenterFixed() {
        return epicenterFixed;
    }

    /**
     * Sets the value of the epicenterFixed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEpicenterFixed(Boolean value) {
        this.epicenterFixed = value;
    }

    /**
     * Gets the value of the referenceSystemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceSystemID() {
        return referenceSystemID;
    }

    /**
     * Sets the value of the referenceSystemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceSystemID(String value) {
        this.referenceSystemID = value;
    }

    /**
     * Gets the value of the methodID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMethodID() {
        return methodID;
    }

    /**
     * Sets the value of the methodID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMethodID(String value) {
        this.methodID = value;
    }

    /**
     * Gets the value of the earthModelID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEarthModelID() {
        return earthModelID;
    }

    /**
     * Sets the value of the earthModelID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEarthModelID(String value) {
        this.earthModelID = value;
    }

    /**
     * Gets the value of the quality property.
     * 
     * @return
     *     possible object is
     *     {@link OriginQuality }
     *     
     */
    public OriginQuality getQuality() {
        return quality;
    }

    /**
     * Sets the value of the quality property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginQuality }
     *     
     */
    public void setQuality(OriginQuality value) {
        this.quality = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link OriginType }
     *     
     */
    public OriginType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginType }
     *     
     */
    public void setType(OriginType value) {
        this.type = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the evaluationMode property.
     * 
     * @return
     *     possible object is
     *     {@link EvaluationMode }
     *     
     */
    public EvaluationMode getEvaluationMode() {
        return evaluationMode;
    }

    /**
     * Sets the value of the evaluationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvaluationMode }
     *     
     */
    public void setEvaluationMode(EvaluationMode value) {
        this.evaluationMode = value;
    }

    /**
     * Gets the value of the evaluationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link EvaluationStatus }
     *     
     */
    public EvaluationStatus getEvaluationStatus() {
        return evaluationStatus;
    }

    /**
     * Sets the value of the evaluationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvaluationStatus }
     *     
     */
    public void setEvaluationStatus(EvaluationStatus value) {
        this.evaluationStatus = value;
    }

    /**
     * Gets the value of the creationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CreationInfo }
     *     
     */
    public CreationInfo getCreationInfo() {
        return creationInfo;
    }

    /**
     * Sets the value of the creationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreationInfo }
     *     
     */
    public void setCreationInfo(CreationInfo value) {
        this.creationInfo = value;
    }

    /**
     * Gets the value of the anies property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the anies property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnies().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Element }
     * 
     * 
     */
    public List<Element> getAnies() {
        if (anies == null) {
            anies = new ArrayList<Element>();
        }
        return this.anies;
    }

    /**
     * Gets the value of the publicID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicID() {
        return publicID;
    }

    /**
     * Sets the value of the publicID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicID(String value) {
        this.publicID = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
