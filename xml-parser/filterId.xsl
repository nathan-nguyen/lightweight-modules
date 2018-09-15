<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:dataml="http://www.fpml.org/FpML-5/legal">
	<xsl:output omit-xml-declaration="yes" indent="yes"/>

	<xsl:template match="dataml:object" >
		<xsl:if test="./dataml:detail/dataml:id = '1'" >
			<xsl:copy-of select="."/>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>

