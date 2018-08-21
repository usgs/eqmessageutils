#! /usr/bin/env python


#
# grep name etc/quakeml/QuakeML-BED-1.1.xsd  | grep simpleType | grep -v qml | sed 's/.*name="\(.*\)".*/"\1",/'
#
simpleElements = (
	"ResourceIdentifier",
	"ResourceReference",
	"OriginUncertaintyDescription",
	"MomentTensorStatus",
	"OriginDepthType",
	"OriginType",
	"EvaluationMode",
	"EvaluationStatus",
	"PickOnset",
	"MomentTensorMethod",
	"DataUsedWaveType",
	"EventDescriptionType",
	"EventType",
	"EventTypeCertainty",
	"SourceTimeFunctionType",
	"PickPolarity",
)

#
# grep name etc/quakeml/QuakeML-BED-1.1.xsd  | grep complexType | grep -v qml | sed 's/.*name="\(.*\)".*/"\1",/'
#
complexElements = (
	"TimeQuantity",
	"CreationInfo",
	"EventDescription",
	"Phase",
	"Comment",
	"Axis",
	"PrincipalAxes",
	"DataUsed",
	"CompositeTime",
	"Tensor",
	"OriginQuality",
	"RealQuantity",
	"NodalPlane",
	"TimeWindow",
	"WaveformStreamID",
	"IntegerQuantity",
	"SourceTimeFunction",
	"NodalPlanes",
	"ConfidenceEllipsoid",
	"MomentTensor",
	"FocalMechanism",
	"Amplitude",
	"StationMagnitudeContribution",
	"Magnitude",
	"StationMagnitude",
	"Pick",
	"Event",
	"OriginUncertainty",
	"Arrival",
	"Origin",
	"EventParameters",
)



print """<bindings version="1.0"
	xmlns="http://java.sun.com/xml/ns/jaxb"
	xmlns:xs="http://www.w3.org/2001/XMLSchema">

<globalBindings
		fixedAttributeAsConstantProperty="true"
		generateIsSetMethod="false">

	<!-- override default Float mapping for floats -->
	<javaType xmlType="xs:float" name="java.math.BigDecimal" 
			parseMethod="javax.xml.bind.DatatypeConverter.parseDecimal"
			printMethod="javax.xml.bind.DatatypeConverter.printDecimal" />

	<!-- override default Double mapping for doubles -->
	<javaType xmlType="xs:double" name="java.math.BigDecimal" 
			parseMethod="javax.xml.bind.DatatypeConverter.parseDecimal"
			printMethod="javax.xml.bind.DatatypeConverter.printDecimal" />

	<!-- override default XMLGregorianCalendar mapping for dates. -->
	<javaType xmlType="xs:dateTime" name="java.util.Date"
			parseMethod="gov.usgs.earthquake.quakeml.QuakemlDateParser.getDate"
			printMethod="gov.usgs.earthquake.quakeml.QuakemlDateParser.formatDate" />

</globalBindings>

<bindings schemaLocation="QuakeML-BED-1.1_jaxb.xsd" node="/xs:schema">
<schemaBindings>
	<package name="org.quakeml_1_1"/>
</schemaBindings>

<bindings node="//xs:element[@name='quakeml']"><class name="Quakeml"/></bindings>

"""

#for el in simpleElements:
#	print """<bindings node="//xs:simpleType[@name='%s']"><class name="%s"/></bindings>""" % (el, el)

for el in complexElements:
	print """<bindings node="//xs:complexType[@name='%s']"><class name="%s"/></bindings>""" % (el, el)

print """
</bindings>
</bindings>
"""
