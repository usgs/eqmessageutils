//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.08.21 at 09:51:54 AM MDT 
//


package org.quakeml_1_2;

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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;


/**
 * <p>Java class for Event complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Event"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://quakeml.org/xmlns/bed/1.2}EventDescription" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="comment" type="{http://quakeml.org/xmlns/bed/1.2}Comment" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="focalMechanism" type="{http://quakeml.org/xmlns/bed/1.2}FocalMechanism" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="amplitude" type="{http://quakeml.org/xmlns/bed/1.2}Amplitude" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="magnitude" type="{http://quakeml.org/xmlns/bed/1.2}Magnitude" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="stationMagnitude" type="{http://quakeml.org/xmlns/bed/1.2}StationMagnitude" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="origin" type="{http://quakeml.org/xmlns/bed/1.2}Origin" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="pick" type="{http://quakeml.org/xmlns/bed/1.2}Pick" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="preferredOriginID" type="{http://quakeml.org/xmlns/bed/1.2}ResourceReference" minOccurs="0"/&gt;
 *         &lt;element name="preferredMagnitudeID" type="{http://quakeml.org/xmlns/bed/1.2}ResourceReference" minOccurs="0"/&gt;
 *         &lt;element name="preferredFocalMechanismID" type="{http://quakeml.org/xmlns/bed/1.2}ResourceReference" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{http://quakeml.org/xmlns/bed/1.2}EventType" minOccurs="0"/&gt;
 *         &lt;element name="typeCertainty" type="{http://quakeml.org/xmlns/bed/1.2}EventTypeCertainty" minOccurs="0"/&gt;
 *         &lt;element name="creationInfo" type="{http://quakeml.org/xmlns/bed/1.2}CreationInfo" minOccurs="0"/&gt;
 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://anss.org/xmlns/catalog/0.1}CatalogAttributes"/&gt;
 *       &lt;attribute name="publicID" use="required" type="{http://quakeml.org/xmlns/bed/1.2}ResourceReference" /&gt;
 *       &lt;anyAttribute processContents='lax' namespace='##other'/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Event", propOrder = {
    "descriptions",
    "comments",
    "focalMechanisms",
    "amplitudes",
    "magnitudes",
    "stationMagnitudes",
    "origins",
    "picks",
    "preferredOriginID",
    "preferredMagnitudeID",
    "preferredFocalMechanismID",
    "type",
    "typeCertainty",
    "creationInfo",
    "anies"
})
@XmlSeeAlso({
    ScenarioEvent.class,
    InternalEvent.class
})
public class Event {

    @XmlElement(name = "description")
    protected List<EventDescription> descriptions;
    @XmlElement(name = "comment")
    protected List<Comment> comments;
    @XmlElement(name = "focalMechanism")
    protected List<FocalMechanism> focalMechanisms;
    @XmlElement(name = "amplitude")
    protected List<Amplitude> amplitudes;
    @XmlElement(name = "magnitude")
    protected List<Magnitude> magnitudes;
    @XmlElement(name = "stationMagnitude")
    protected List<StationMagnitude> stationMagnitudes;
    @XmlElement(name = "origin")
    protected List<Origin> origins;
    @XmlElement(name = "pick")
    protected List<Pick> picks;
    @XmlSchemaType(name = "anyURI")
    protected String preferredOriginID;
    @XmlSchemaType(name = "anyURI")
    protected String preferredMagnitudeID;
    @XmlSchemaType(name = "anyURI")
    protected String preferredFocalMechanismID;
    @XmlSchemaType(name = "string")
    protected EventType type;
    @XmlSchemaType(name = "string")
    protected EventTypeCertainty typeCertainty;
    protected CreationInfo creationInfo;
    @XmlAnyElement(lax = true)
    protected List<Object> anies;
    @XmlAttribute(name = "publicID", required = true)
    protected String publicID;
    @XmlAttribute(name = "datasource", namespace = "http://anss.org/xmlns/catalog/0.1")
    protected String datasource;
    @XmlAttribute(name = "dataid", namespace = "http://anss.org/xmlns/catalog/0.1")
    protected String dataid;
    @XmlAttribute(name = "datatype", namespace = "http://anss.org/xmlns/catalog/0.1")
    protected String datatype;
    @XmlAttribute(name = "eventsource", namespace = "http://anss.org/xmlns/catalog/0.1")
    protected String eventsource;
    @XmlAttribute(name = "eventid", namespace = "http://anss.org/xmlns/catalog/0.1")
    protected String eventid;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the descriptions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the descriptions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDescriptions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EventDescription }
     * 
     * 
     */
    public List<EventDescription> getDescriptions() {
        if (descriptions == null) {
            descriptions = new ArrayList<EventDescription>();
        }
        return this.descriptions;
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
     * Gets the value of the focalMechanisms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the focalMechanisms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFocalMechanisms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FocalMechanism }
     * 
     * 
     */
    public List<FocalMechanism> getFocalMechanisms() {
        if (focalMechanisms == null) {
            focalMechanisms = new ArrayList<FocalMechanism>();
        }
        return this.focalMechanisms;
    }

    /**
     * Gets the value of the amplitudes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the amplitudes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAmplitudes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Amplitude }
     * 
     * 
     */
    public List<Amplitude> getAmplitudes() {
        if (amplitudes == null) {
            amplitudes = new ArrayList<Amplitude>();
        }
        return this.amplitudes;
    }

    /**
     * Gets the value of the magnitudes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the magnitudes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMagnitudes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Magnitude }
     * 
     * 
     */
    public List<Magnitude> getMagnitudes() {
        if (magnitudes == null) {
            magnitudes = new ArrayList<Magnitude>();
        }
        return this.magnitudes;
    }

    /**
     * Gets the value of the stationMagnitudes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stationMagnitudes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStationMagnitudes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StationMagnitude }
     * 
     * 
     */
    public List<StationMagnitude> getStationMagnitudes() {
        if (stationMagnitudes == null) {
            stationMagnitudes = new ArrayList<StationMagnitude>();
        }
        return this.stationMagnitudes;
    }

    /**
     * Gets the value of the origins property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the origins property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrigins().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Origin }
     * 
     * 
     */
    public List<Origin> getOrigins() {
        if (origins == null) {
            origins = new ArrayList<Origin>();
        }
        return this.origins;
    }

    /**
     * Gets the value of the picks property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the picks property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPicks().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Pick }
     * 
     * 
     */
    public List<Pick> getPicks() {
        if (picks == null) {
            picks = new ArrayList<Pick>();
        }
        return this.picks;
    }

    /**
     * Gets the value of the preferredOriginID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredOriginID() {
        return preferredOriginID;
    }

    /**
     * Sets the value of the preferredOriginID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredOriginID(String value) {
        this.preferredOriginID = value;
    }

    /**
     * Gets the value of the preferredMagnitudeID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredMagnitudeID() {
        return preferredMagnitudeID;
    }

    /**
     * Sets the value of the preferredMagnitudeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredMagnitudeID(String value) {
        this.preferredMagnitudeID = value;
    }

    /**
     * Gets the value of the preferredFocalMechanismID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreferredFocalMechanismID() {
        return preferredFocalMechanismID;
    }

    /**
     * Sets the value of the preferredFocalMechanismID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreferredFocalMechanismID(String value) {
        this.preferredFocalMechanismID = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link EventType }
     *     
     */
    public EventType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventType }
     *     
     */
    public void setType(EventType value) {
        this.type = value;
    }

    /**
     * Gets the value of the typeCertainty property.
     * 
     * @return
     *     possible object is
     *     {@link EventTypeCertainty }
     *     
     */
    public EventTypeCertainty getTypeCertainty() {
        return typeCertainty;
    }

    /**
     * Sets the value of the typeCertainty property.
     * 
     * @param value
     *     allowed object is
     *     {@link EventTypeCertainty }
     *     
     */
    public void setTypeCertainty(EventTypeCertainty value) {
        this.typeCertainty = value;
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
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAnies() {
        if (anies == null) {
            anies = new ArrayList<Object>();
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
     * Gets the value of the datasource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * Sets the value of the datasource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasource(String value) {
        this.datasource = value;
    }

    /**
     * Gets the value of the dataid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataid() {
        return dataid;
    }

    /**
     * Sets the value of the dataid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataid(String value) {
        this.dataid = value;
    }

    /**
     * Gets the value of the datatype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * Sets the value of the datatype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatatype(String value) {
        this.datatype = value;
    }

    /**
     * Gets the value of the eventsource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventsource() {
        return eventsource;
    }

    /**
     * Sets the value of the eventsource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventsource(String value) {
        this.eventsource = value;
    }

    /**
     * Gets the value of the eventid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventid() {
        return eventid;
    }

    /**
     * Sets the value of the eventid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventid(String value) {
        this.eventid = value;
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