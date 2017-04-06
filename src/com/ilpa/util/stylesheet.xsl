<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<xsl:value-of select="/report" />
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="section">
		<xsl:value-of select="." />
		<xsl:apply-templates select="bold" />
	</xsl:template>

	<xsl:template match="bold">
			<xsl:value-of select="." />
	</xsl:template>

</xsl:stylesheet>