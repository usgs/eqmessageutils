#! /usr/bin/env python


#
# grep name etc/quakeml_1.2rc3/QuakeML-BED-1.2.xsd | grep complexType | grep -v qml | sed 's/.*name="\(.*\)".*/"\1",/'
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
	"OriginUncertainty",
	"Arrival",
	"Origin",
	"Pick",
	"Event",
	"EventParameters",
)



print """<bindings version="2.0" 
	xmlns="http://java.sun.com/xml/ns/jaxb"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xjc="http://java.sun.com/xml/ns/jaxb/xjc"
	xmlns:wildcard="http://jaxb2-commons.dev.java.net/basic/wildcard"
	extensionBindingPrefixes="xjc wildcard">

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
	<bindings schemaLocation="QuakeML-1.2_jaxb.xsd" node="/xs:schema">
		<schemaBindings>
			<package name="org.quakeml_1_2"/>
		</schemaBindings>
	</bindings>

	<!-- bindings for QuakeML-BED -->
	<bindings schemaLocation="QuakeML-BED-1.2_jaxb.xsd" node="/xs:schema">
		<wildcard:lax/>

		<schemaBindings map="true">
			<package name="org.quakeml_1_2"/>
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

for el in complexElements:
	print """		<bindings node="//xs:complexType[@name='%s']"><class name="%s"/></bindings>""" % (el, el)

print """
	</bindings>

	<!-- bindings for AnssEvent -->
	<bindings schemaLocation="AnssEvent-0.1.xsd" node="/xs:schema">
		<schemaBindings>
			<package name="org.quakeml_1_2"/>
		</schemaBindings>
	</bindings>

</bindings>
"""
