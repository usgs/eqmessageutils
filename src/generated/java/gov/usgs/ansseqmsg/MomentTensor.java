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
 * <p>Java class for MomentTensorCpxType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MomentTensorCpxType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SourceKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TypeKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Action" type="{http://www.usgs.gov/ansseqmsg}ActionSimType" minOccurs="0"/&gt;
 *         &lt;element name="MagMw" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="M0" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="Tensor" type="{http://www.usgs.gov/ansseqmsg}TensorCpxType" minOccurs="0"/&gt;
 *         &lt;element name="PrincAxes" type="{http://www.usgs.gov/ansseqmsg}PrincAxesCpxType" minOccurs="0"/&gt;
 *         &lt;element name="NodalPlanes" type="{http://www.usgs.gov/ansseqmsg}NodalPlanesCpxType" minOccurs="0"/&gt;
 *         &lt;element name="DerivedOriginTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="DerivedLatitude" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="DerivedLongitude" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="DerivedDepth" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="DepthDetermination" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VarReduc" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="PerDblCpl" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/&gt;
 *         &lt;element name="ErrorTensor" type="{http://www.usgs.gov/ansseqmsg}ErrorTensorCpxType" minOccurs="0"/&gt;
 *         &lt;element name="NumObs" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/&gt;
 *         &lt;element name="PreferredFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="SrcTimeFn" type="{http://www.usgs.gov/ansseqmsg}SrcTimeFnCpxType" minOccurs="0"/&gt;
 *         &lt;element name="Comment" type="{http://www.usgs.gov/ansseqmsg}CommentCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Method" type="{http://www.usgs.gov/ansseqmsg}MethodCpxType" minOccurs="0"/&gt;
 *         &lt;element name="DataUsed" type="{http://www.usgs.gov/ansseqmsg}DataUsedCpxType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MomentTensorCpxType", propOrder = {
    "sourceKey",
    "typeKey",
    "version",
    "action",
    "magMw",
    "m0",
    "tensor",
    "princAxes",
    "nodalPlanes",
    "derivedOriginTime",
    "derivedLatitude",
    "derivedLongitude",
    "derivedDepth",
    "depthDetermination",
    "varReduc",
    "perDblCpl",
    "errorTensor",
    "numObs",
    "preferredFlag",
    "srcTimeFn",
    "comment",
    "method",
    "dataUsed"
})
public class MomentTensor {

    @XmlElement(name = "SourceKey")
    protected String sourceKey;
    @XmlElement(name = "TypeKey")
    protected String typeKey;
    @XmlElement(name = "Version")
    protected String version;
    @XmlElement(name = "Action")
    @XmlSchemaType(name = "string")
    protected Action action;
    @XmlElement(name = "MagMw", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal magMw;
    @XmlElement(name = "M0", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal m0;
    @XmlElement(name = "Tensor")
    protected Tensor tensor;
    @XmlElement(name = "PrincAxes")
    protected PrincAxes princAxes;
    @XmlElement(name = "NodalPlanes")
    protected NodalPlanes nodalPlanes;
    @XmlElement(name = "DerivedOriginTime", type = String.class)
    @XmlJavaTypeAdapter(Adapter2 .class)
    @XmlSchemaType(name = "dateTime")
    protected Date derivedOriginTime;
    @XmlElement(name = "DerivedLatitude", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal derivedLatitude;
    @XmlElement(name = "DerivedLongitude", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal derivedLongitude;
    @XmlElement(name = "DerivedDepth", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal derivedDepth;
    @XmlElement(name = "DepthDetermination")
    protected String depthDetermination;
    @XmlElement(name = "VarReduc", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal varReduc;
    @XmlElement(name = "PerDblCpl", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "float")
    protected BigDecimal perDblCpl;
    @XmlElement(name = "ErrorTensor")
    protected ErrorTensor errorTensor;
    @XmlElement(name = "NumObs")
    protected BigInteger numObs;
    @XmlElement(name = "PreferredFlag")
    protected Boolean preferredFlag;
    @XmlElement(name = "SrcTimeFn")
    protected SrcTimeFn srcTimeFn;
    @XmlElement(name = "Comment")
    protected List<Comment> comment;
    @XmlElement(name = "Method")
    protected Method method;
    @XmlElement(name = "DataUsed")
    protected List<DataUsed> dataUsed;

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
     * Gets the value of the typeKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeKey() {
        return typeKey;
    }

    /**
     * Sets the value of the typeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeKey(String value) {
        this.typeKey = value;
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
     * Gets the value of the magMw property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getMagMw() {
        return magMw;
    }

    /**
     * Sets the value of the magMw property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMagMw(BigDecimal value) {
        this.magMw = value;
    }

    /**
     * Gets the value of the m0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getM0() {
        return m0;
    }

    /**
     * Sets the value of the m0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setM0(BigDecimal value) {
        this.m0 = value;
    }

    /**
     * Gets the value of the tensor property.
     * 
     * @return
     *     possible object is
     *     {@link Tensor }
     *     
     */
    public Tensor getTensor() {
        return tensor;
    }

    /**
     * Sets the value of the tensor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Tensor }
     *     
     */
    public void setTensor(Tensor value) {
        this.tensor = value;
    }

    /**
     * Gets the value of the princAxes property.
     * 
     * @return
     *     possible object is
     *     {@link PrincAxes }
     *     
     */
    public PrincAxes getPrincAxes() {
        return princAxes;
    }

    /**
     * Sets the value of the princAxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrincAxes }
     *     
     */
    public void setPrincAxes(PrincAxes value) {
        this.princAxes = value;
    }

    /**
     * Gets the value of the nodalPlanes property.
     * 
     * @return
     *     possible object is
     *     {@link NodalPlanes }
     *     
     */
    public NodalPlanes getNodalPlanes() {
        return nodalPlanes;
    }

    /**
     * Sets the value of the nodalPlanes property.
     * 
     * @param value
     *     allowed object is
     *     {@link NodalPlanes }
     *     
     */
    public void setNodalPlanes(NodalPlanes value) {
        this.nodalPlanes = value;
    }

    /**
     * Gets the value of the derivedOriginTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Date getDerivedOriginTime() {
        return derivedOriginTime;
    }

    /**
     * Sets the value of the derivedOriginTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivedOriginTime(Date value) {
        this.derivedOriginTime = value;
    }

    /**
     * Gets the value of the derivedLatitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getDerivedLatitude() {
        return derivedLatitude;
    }

    /**
     * Sets the value of the derivedLatitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivedLatitude(BigDecimal value) {
        this.derivedLatitude = value;
    }

    /**
     * Gets the value of the derivedLongitude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getDerivedLongitude() {
        return derivedLongitude;
    }

    /**
     * Sets the value of the derivedLongitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivedLongitude(BigDecimal value) {
        this.derivedLongitude = value;
    }

    /**
     * Gets the value of the derivedDepth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getDerivedDepth() {
        return derivedDepth;
    }

    /**
     * Sets the value of the derivedDepth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivedDepth(BigDecimal value) {
        this.derivedDepth = value;
    }

    /**
     * Gets the value of the depthDetermination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepthDetermination() {
        return depthDetermination;
    }

    /**
     * Sets the value of the depthDetermination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepthDetermination(String value) {
        this.depthDetermination = value;
    }

    /**
     * Gets the value of the varReduc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getVarReduc() {
        return varReduc;
    }

    /**
     * Sets the value of the varReduc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVarReduc(BigDecimal value) {
        this.varReduc = value;
    }

    /**
     * Gets the value of the perDblCpl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public BigDecimal getPerDblCpl() {
        return perDblCpl;
    }

    /**
     * Sets the value of the perDblCpl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPerDblCpl(BigDecimal value) {
        this.perDblCpl = value;
    }

    /**
     * Gets the value of the errorTensor property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorTensor }
     *     
     */
    public ErrorTensor getErrorTensor() {
        return errorTensor;
    }

    /**
     * Sets the value of the errorTensor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorTensor }
     *     
     */
    public void setErrorTensor(ErrorTensor value) {
        this.errorTensor = value;
    }

    /**
     * Gets the value of the numObs property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumObs() {
        return numObs;
    }

    /**
     * Sets the value of the numObs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumObs(BigInteger value) {
        this.numObs = value;
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
     * Gets the value of the srcTimeFn property.
     * 
     * @return
     *     possible object is
     *     {@link SrcTimeFn }
     *     
     */
    public SrcTimeFn getSrcTimeFn() {
        return srcTimeFn;
    }

    /**
     * Sets the value of the srcTimeFn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SrcTimeFn }
     *     
     */
    public void setSrcTimeFn(SrcTimeFn value) {
        this.srcTimeFn = value;
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
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link Method }
     *     
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link Method }
     *     
     */
    public void setMethod(Method value) {
        this.method = value;
    }

    /**
     * Gets the value of the dataUsed property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataUsed property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataUsed().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataUsed }
     * 
     * 
     */
    public List<DataUsed> getDataUsed() {
        if (dataUsed == null) {
            dataUsed = new ArrayList<DataUsed>();
        }
        return this.dataUsed;
    }

}
