//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.02.05 at 11:56:09 AM MST 
//


package gov.usgs.ansseqmsg;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for OriginCpxType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OriginCpxType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SourceKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Action" type="{http://www.usgs.gov/ansseqmsg}ActionSimType" minOccurs="0"/&gt;
 *         &lt;element name="Region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Time" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="LocalTime" type="{http://www.usgs.gov/ansseqmsg}LocalTimeCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="Depth" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="StdError" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="AzimGap" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="MinDist" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="Errh" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="Errz" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="OTError" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="LatError" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="LonError" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="DepthError" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="ErrorAxes" type="{http://www.usgs.gov/ansseqmsg}ErrorAxesCpxType" minOccurs="0"/&gt;
 *         &lt;element name="NumPhaUsed" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="NumPhaAssoc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="NumStaUsed" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="NumStaAssoc" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="DepthMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.usgs.gov/ansseqmsg}OriginStatusSimType" minOccurs="0"/&gt;
 *         &lt;element name="PreferredFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="Comment" type="{http://www.usgs.gov/ansseqmsg}CommentCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Magnitude" type="{http://www.usgs.gov/ansseqmsg}MagnitudeCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="MomentTensor" type="{http://www.usgs.gov/ansseqmsg}MomentTensorCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="FocalMech" type="{http://www.usgs.gov/ansseqmsg}FocalMechCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Phase" type="{http://www.usgs.gov/ansseqmsg}PhaseCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Method" type="{http://www.usgs.gov/ansseqmsg}MethodCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginCpxType", propOrder = {
    "sourceKey",
    "version",
    "action",
    "region",
    "time",
    "localTime",
    "latitude",
    "longitude",
    "depth",
    "stdError",
    "azimGap",
    "minDist",
    "errh",
    "errz",
    "otError",
    "latError",
    "lonError",
    "depthError",
    "errorAxes",
    "numPhaUsed",
    "numPhaAssoc",
    "numStaUsed",
    "numStaAssoc",
    "depthMethod",
    "status",
    "preferredFlag",
    "comment",
    "magnitude",
    "momentTensor",
    "focalMech",
    "phase",
    "method"
})
public class Origin {

    @XmlElement(name = "SourceKey")
    protected String sourceKey;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "Action")
    @XmlSchemaType(name = "string")
    protected Action action;
    @XmlElement(name = "Region")
    protected String region;
    @XmlElement(name = "Time", type = String.class)
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date time;
    @XmlElement(name = "LocalTime")
    protected List<LocalTime> localTime;
    @XmlElement(name = "Latitude", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal latitude;
    @XmlElement(name = "Longitude", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal longitude;
    @XmlElement(name = "Depth", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal depth;
    @XmlElement(name = "StdError", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal stdError;
    @XmlElement(name = "AzimGap", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal azimGap;
    @XmlElement(name = "MinDist", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal minDist;
    @XmlElement(name = "Errh", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal errh;
    @XmlElement(name = "Errz", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal errz;
    @XmlElement(name = "OTError", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal otError;
    @XmlElement(name = "LatError", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal latError;
    @XmlElement(name = "LonError", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal lonError;
    @XmlElement(name = "DepthError", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal depthError;
    @XmlElement(name = "ErrorAxes")
    protected ErrorAxes errorAxes;
    @XmlElement(name = "NumPhaUsed")
    protected BigInteger numPhaUsed;
    @XmlElement(name = "NumPhaAssoc")
    protected BigInteger numPhaAssoc;
    @XmlElement(name = "NumStaUsed")
    protected BigInteger numStaUsed;
    @XmlElement(name = "NumStaAssoc")
    protected BigInteger numStaAssoc;
    @XmlElement(name = "DepthMethod")
    protected String depthMethod;
    @XmlElement(name = "Status")
    @XmlSchemaType(name = "string")
    protected OriginStatus status;
    @XmlElement(name = "PreferredFlag")
    protected Boolean preferredFlag;
    @XmlElement(name = "Comment")
    protected List<Comment> comment;
    @XmlElement(name = "Magnitude")
    protected List<Magnitude> magnitude;
    @XmlElement(name = "MomentTensor")
    protected List<MomentTensor> momentTensor;
    @XmlElement(name = "FocalMech")
    protected List<FocalMech> focalMech;
    @XmlElement(name = "Phase")
    protected List<Phase> phase;
    @XmlElement(name = "Method")
    protected List<Method> method;

    /**
     * Gets the value of the sourceKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceKey() {
        return sourceKey;
    }

    /**
     * Sets the value of the sourceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceKey(String value) {
        this.sourceKey = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link Action }
     *     
     */
    public Action getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link Action }
     *     
     */
    public void setAction(Action value) {
        this.action = value;
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
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTime(Date value) {
        this.time = value;
    }

    /**
     * Gets the value of the localTime property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the localTime property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocalTime().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocalTime }
     * 
     * 
     */
    public List<LocalTime> getLocalTime() {
        if (localTime == null) {
            localTime = new ArrayList<LocalTime>();
        }
        return this.localTime;
    }

    /**
     * Gets the value of the latitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatitude(BigDecimal value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLongitude(BigDecimal value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the depth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getDepth() {
        return depth;
    }

    /**
     * Sets the value of the depth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepth(BigDecimal value) {
        this.depth = value;
    }

    /**
     * Gets the value of the stdError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getStdError() {
        return stdError;
    }

    /**
     * Sets the value of the stdError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStdError(BigDecimal value) {
        this.stdError = value;
    }

    /**
     * Gets the value of the azimGap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getAzimGap() {
        return azimGap;
    }

    /**
     * Sets the value of the azimGap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAzimGap(BigDecimal value) {
        this.azimGap = value;
    }

    /**
     * Gets the value of the minDist property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getMinDist() {
        return minDist;
    }

    /**
     * Sets the value of the minDist property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinDist(BigDecimal value) {
        this.minDist = value;
    }

    /**
     * Gets the value of the errh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getErrh() {
        return errh;
    }

    /**
     * Sets the value of the errh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrh(BigDecimal value) {
        this.errh = value;
    }

    /**
     * Gets the value of the errz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getErrz() {
        return errz;
    }

    /**
     * Sets the value of the errz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrz(BigDecimal value) {
        this.errz = value;
    }

    /**
     * Gets the value of the otError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getOTError() {
        return otError;
    }

    /**
     * Sets the value of the otError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOTError(BigDecimal value) {
        this.otError = value;
    }

    /**
     * Gets the value of the latError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getLatError() {
        return latError;
    }

    /**
     * Sets the value of the latError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLatError(BigDecimal value) {
        this.latError = value;
    }

    /**
     * Gets the value of the lonError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getLonError() {
        return lonError;
    }

    /**
     * Sets the value of the lonError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLonError(BigDecimal value) {
        this.lonError = value;
    }

    /**
     * Gets the value of the depthError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getDepthError() {
        return depthError;
    }

    /**
     * Sets the value of the depthError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepthError(BigDecimal value) {
        this.depthError = value;
    }

    /**
     * Gets the value of the errorAxes property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorAxes }
     *     
     */
    public ErrorAxes getErrorAxes() {
        return errorAxes;
    }

    /**
     * Sets the value of the errorAxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorAxes }
     *     
     */
    public void setErrorAxes(ErrorAxes value) {
        this.errorAxes = value;
    }

    /**
     * Gets the value of the numPhaUsed property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumPhaUsed() {
        return numPhaUsed;
    }

    /**
     * Sets the value of the numPhaUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumPhaUsed(BigInteger value) {
        this.numPhaUsed = value;
    }

    /**
     * Gets the value of the numPhaAssoc property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumPhaAssoc() {
        return numPhaAssoc;
    }

    /**
     * Sets the value of the numPhaAssoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumPhaAssoc(BigInteger value) {
        this.numPhaAssoc = value;
    }

    /**
     * Gets the value of the numStaUsed property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumStaUsed() {
        return numStaUsed;
    }

    /**
     * Sets the value of the numStaUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumStaUsed(BigInteger value) {
        this.numStaUsed = value;
    }

    /**
     * Gets the value of the numStaAssoc property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumStaAssoc() {
        return numStaAssoc;
    }

    /**
     * Sets the value of the numStaAssoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumStaAssoc(BigInteger value) {
        this.numStaAssoc = value;
    }

    /**
     * Gets the value of the depthMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepthMethod() {
        return depthMethod;
    }

    /**
     * Sets the value of the depthMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepthMethod(String value) {
        this.depthMethod = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link OriginStatus }
     *     
     */
    public OriginStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginStatus }
     *     
     */
    public void setStatus(OriginStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the preferredFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getPreferredFlag() {
        return preferredFlag;
    }

    /**
     * Sets the value of the preferredFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreferredFlag(Boolean value) {
        this.preferredFlag = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the comment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Comment }
     * 
     * 
     */
    public List<Comment> getComment() {
        if (comment == null) {
            comment = new ArrayList<Comment>();
        }
        return this.comment;
    }

    /**
     * Gets the value of the magnitude property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the magnitude property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMagnitude().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Magnitude }
     * 
     * 
     */
    public List<Magnitude> getMagnitude() {
        if (magnitude == null) {
            magnitude = new ArrayList<Magnitude>();
        }
        return this.magnitude;
    }

    /**
     * Gets the value of the momentTensor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the momentTensor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMomentTensor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MomentTensor }
     * 
     * 
     */
    public List<MomentTensor> getMomentTensor() {
        if (momentTensor == null) {
            momentTensor = new ArrayList<MomentTensor>();
        }
        return this.momentTensor;
    }

    /**
     * Gets the value of the focalMech property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the focalMech property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFocalMech().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FocalMech }
     * 
     * 
     */
    public List<FocalMech> getFocalMech() {
        if (focalMech == null) {
            focalMech = new ArrayList<FocalMech>();
        }
        return this.focalMech;
    }

    /**
     * Gets the value of the phase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Phase }
     * 
     * 
     */
    public List<Phase> getPhase() {
        if (phase == null) {
            phase = new ArrayList<Phase>();
        }
        return this.phase;
    }

    /**
     * Gets the value of the method property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the method property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMethod().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Method }
     * 
     * 
     */
    public List<Method> getMethod() {
        if (method == null) {
            method = new ArrayList<Method>();
        }
        return this.method;
    }

}
