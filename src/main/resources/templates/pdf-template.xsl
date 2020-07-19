<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="xml" indent="yes"/>
<xsl:attribute-set name="tableBorder">
    <xsl:attribute name="border">solid 0.1mm black</xsl:attribute>
</xsl:attribute-set>
<xsl:attribute-set name="tableInline">
    <xsl:attribute name="width">50%</xsl:attribute>
    <xsl:attribute name="float">left</xsl:attribute>
</xsl:attribute-set>
<xsl:template match="Employee">
    <fo:root font-family="Calibri">
        <fo:layout-master-set>
            <fo:simple-page-master master-name="simpleA4"
                                   page-height="29.7cm" page-width="21.0cm" margin="1cm">
                <fo:region-body/>
            </fo:simple-page-master>
        </fo:layout-master-set>
        <fo:page-sequence master-reference="simpleA4">

            <fo:flow flow-name="xsl-region-body">

                <fo:table table-layout="fixed" width="100%">

                    <fo:table-column column-number="1" column-width="40%"/>
                    <fo:table-column column-number="2" column-width="60%"/>
                    <fo:table-body>
                    <fo:table-row>

                        <fo:table-cell>

                            <fo:block font-size="14pt" color="blue" font-weight="bold" space-after="5mm" text-align="center">
                                Employees
                            </fo:block>

                            <fo:block font-size="10pt" font-weight="normal">
                                <fo:table table-layout="fixed" border-collapse="separate" xsl:use-attribute-sets="tableInline">
                                    <fo:table-column column-number="1" column-width="1cm"/>
                                    <fo:table-column column-number="2" column-width="6.59cm"/>
                                    <fo:table-header>
                                        <fo:table-cell xsl:use-attribute-sets="tableBorder">
                                            <fo:block font-weight="bold">Id</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell xsl:use-attribute-sets="tableBorder" text-align="center">
                                            <fo:block font-weight="bold">Name</fo:block>
                                        </fo:table-cell>
                                    </fo:table-header>
                                    <fo:table-body height="10cm">
                                        <xsl:for-each select="./employees/employee">
                                            <fo:table-row border="solid 0.1mm black">

                                                <fo:table-cell text-align="left" xsl:use-attribute-sets="tableBorder">
                                                    <fo:block>
                                                        <xsl:value-of select="id"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell text-align="center" xsl:use-attribute-sets="tableBorder">
                                                    <fo:block>
                                                        <xsl:value-of select="name"/>
                                                    </fo:block>
                                                </fo:table-cell>

                                            </fo:table-row>
                                        </xsl:for-each>

                                    </fo:table-body>
                                </fo:table>

                            </fo:block>

                        </fo:table-cell>

                        <fo:table-cell>

                            <fo:block font-size="14pt" color="green" font-weight="bold" space-after="5mm" text-align="center">
                                Active Employees
                            </fo:block>

                            <fo:block font-size="10pt" font-weight="normal">
                                <fo:table table-layout="fixed" border-collapse="separate" xsl:use-attribute-sets="tableInline">
                                    <fo:table-column column-number="1" column-width="5.69cm"/>
                                    <fo:table-column column-number="2" column-width="5.69cm"/>
                                    <fo:table-header>
                                        <fo:table-cell xsl:use-attribute-sets="tableBorder" text-align="center">
                                            <fo:block font-weight="bold">Salary</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell xsl:use-attribute-sets="tableBorder" text-align="center">
                                            <fo:block font-weight="bold">Employment Date</fo:block>
                                        </fo:table-cell>
                                    </fo:table-header>
                                    <fo:table-body height="10cm">
                                        <xsl:for-each select="./activeEmployees/activeEmployee">
                                            <fo:table-row border="solid 0.1mm black">

                                                <fo:table-cell text-align="center" xsl:use-attribute-sets="tableBorder">
                                                    <fo:block>
                                                        <xsl:value-of select="salary"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell text-align="center" xsl:use-attribute-sets="tableBorder">
                                                    <fo:block>
                                                        <xsl:value-of select="employmentDate"/>
                                                    </fo:block>
                                                </fo:table-cell>

                                            </fo:table-row>
                                        </xsl:for-each>

                                    </fo:table-body>
                                </fo:table>

                            </fo:block>

                        </fo:table-cell>


                    </fo:table-row>
                    </fo:table-body>
                </fo:table>

            </fo:flow>

        </fo:page-sequence>

    </fo:root>

</xsl:template>

</xsl:stylesheet>
