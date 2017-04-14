<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" indent="no" encoding="UTF-8" />
	<!-- 
		<xsl:strip-space elements="section"/>
	 -->
	

	<xsl:variable name="bold">'''</xsl:variable>
	<xsl:variable name="italic">''</xsl:variable>
	<xsl:variable name="heading">==</xsl:variable>
	
	<xsl:variable name="mycounter">0</xsl:variable>
	

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="section">
			<xsl:variable name="mycounter">
		        <xsl:call-template name="countupdown">
		        	<xsl:with-param name="mycountervar" select="$mycounter" />
		        </xsl:call-template>
		    </xsl:variable>
			<xsl:copy-of select="$mycounter"/>
			<xsl:copy-of select="$heading" />
			<xsl:value-of select="@heading" />
			<xsl:copy-of select="$heading" />
			<xsl:apply-templates select="@*|node()" />
	</xsl:template>

	<xsl:template match="italic">
		<xsl:copy-of select="$italic" />
		<xsl:apply-templates select="@*|node()" />
		<xsl:copy-of select="$italic" />
	</xsl:template>

	<xsl:template match="bold">
		<xsl:copy-of select="$bold" />
		<xsl:apply-templates select="node()" />
		<xsl:copy-of select="$bold" />
	</xsl:template>

	<xsl:template name="countupdown">
		<xsl:param name="mycountervar" />
		<xsl:value-of select="$mycountervar+1" />
	</xsl:template>
	


	<!-- <xsl:template match="text()"> <xsl:value-of select="normalize-space(.)" 
		/> </xsl:template> -->


</xsl:stylesheet>