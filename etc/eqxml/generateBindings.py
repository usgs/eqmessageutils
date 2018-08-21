#! /usr/bin/env python

simpleElements = (
	"Action",
	"AxisLengthUnit",
	"EventAction",
	"EventScope",
	"EventType",
	"EventUsage",
	"OriginStatus",
)

elements = (
	"Event",
	"Origin",
	"ProductLink",
	"Comment",
	"LocalTime",
	"ErrorAxes",
	"Magnitude",
	"MomentTensor",
	"FocalMech",
	"Phase",
	"Pick",
	"Amplitude",
	"Duration",
	"Axis",
	"Tensor",
	"Fault",
	"SrcTimeFn",
	"Method",
	"PrincAxes",
	"NodalPlanes",
	"ErrorTensor",
	"ErrorFaults",
	"DataUsed"
)



print """<bindings version="1.0" 
	xmlns="http://java.sun.com/xml/ns/jaxb"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:eqxml="http://www.usgs.gov/ansseqmsg">

<globalBindings
		fixedAttributeAsConstantProperty="true"
		generateIsSetMethod="false">

	<!-- override default Float mapping for floats -->
	<javaType xmlType="xs:float" name="java.math.BigDecimal" 
			parseMethod="javax.xml.bind.DatatypeConverter.parseDecimal"
			printMethod="javax.xml.bind.DatatypeConverter.printDecimal" />
	
	<!-- override default XMLGregorianCalendar mapping for dates. -->
	<javaType xmlType="xs:dateTime" name="java.util.Date"
			parseMethod="gov.usgs.earthquake.util.ISO8601.parse"
			printMethod="gov.usgs.earthquake.util.ISO8601.format" />

</globalBindings>

<bindings schemaLocation="ANSS_EQ_XML_doc.xsd" node="/xs:schema">
<package name="gov.usgs.ansseqmsg"/>
"""

for el in simpleElements:
	print """<bindings node="//xs:simpleType[@name='%sSimType']"><typesafeEnumClass name="%s"/></bindings>""" % (el, el)

for el in elements:
	print """<bindings node="//xs:complexType[@name='%sCpxType']"><class name="%s"/></bindings>""" % (el, el)

print """
</bindings>
</bindings>
"""
