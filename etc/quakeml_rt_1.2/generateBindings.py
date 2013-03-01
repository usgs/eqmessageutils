#! /usr/bin/env python


#
# grep name etc/quakeml_rt_1.2rc3/QuakeML-RT-BED-1.2.xsd  | grep simpleType | grep -v qml | sed 's/.*name="\(.*\)".*/"\1",/'
#
simpleElements = (
	"ResourceIdentifier",
	"WhitespaceOrEmptyStringType",
	"ResourceReference_optional",
	"OriginUncertaintyDescription",
	"AmplitudeCategory",
	"OriginDepthType",
	"OriginType",
	"MTInversionType",
	"EvaluationMode",
	"EvaluationStatus",
	"PickOnset",
	"DataUsedWaveType",
	"AmplitudeUnit",
	"EventDescriptionType",
	"MomentTensorCategory",
	"EventType",
	"EventTypeCertainty",
	"SourceTimeFunctionType",
	"PickPolarity",
	"ResourceReference",
)

#
# grep name etc/quakeml_rt_1.2rc3/QuakeML-RT-BED-1.2.xsd  | grep complexType | grep -v qml | sed 's/.*name="\(.*\)".*/"\1",/'
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
	"Reading",
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



print """<bindings version="2.0" 
	xmlns="http://java.sun.com/xml/ns/jaxb"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
	extensionBindingPrefixes="xjc">

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

		<!-- tell JAXB this will not be embedded in another schema -->
		<xjc:simple/>
	</globalBindings>

	<!-- bindings for QuakeML -->
	<bindings schemaLocation="QuakeML-RT-1.2_jaxb.xsd" node="/xs:schema">
		<schemaBindings>
			<package name="org.quakeml_rt_1_2"/>
		</schemaBindings>
	</bindings>

	<!-- bindings for QuakeML-RT-BED -->
	<bindings schemaLocation="QuakeML-RT-BED-1.2_jaxb.xsd" node="/xs:schema">
		<schemaBindings>
			<package name="org.quakeml_rt_1_2"/>
		</schemaBindings>

		<!-- jaxb does okay with all the other enumerations -->
		<bindings node="//xs:simpleType[@name='AmplitudeUnit']">
			<typesafeEnumClass name="AmplitudeUnit">
				<typesafeEnumMember value="m" name="METERS"/>
				<typesafeEnumMember value="s" name="SECONDS"/>
				<typesafeEnumMember value="m/s" name="METERS_PER_SECOND"/>
				<typesafeEnumMember value="m/(s*s)" name="METERS_PER_SECOND_SQUARED"/>
				<typesafeEnumMember value="m*s" name="METER_SECONDS"/>
			</typesafeEnumClass>
		</bindings>
"""

#for el in simpleElements:
#	print """<bindings node="//xs:simpleType[@name='%s']"><class name="%s"/></bindings>""" % (el, el)

for el in complexElements:
	print """		<bindings node="//xs:complexType[@name='%s']"><class name="%s"/></bindings>""" % (el, el)

print """
	</bindings>

</bindings>
"""
