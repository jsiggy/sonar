<?xml version='1.0'?>
<!--
/*
 * Copyright (c) 2003 Sabre Inc. All rights reserved.
 * This software is the confidential and proprietary product of Sabre Inc. 
 * Any unauthorized use, reproduction, or transfer of this software, in any 
 * medium, or incorporation of this software into any system or publication, 
 * is strictly prohibited. Sabre, the Sabre logo design, and AirServ are 
 * trademarks and/or service marks of an affiliate of Sabre Inc. All other 
 * trademarks, service marks and trade names are owned by their respective 
 * companies.  
 *
 */
-->


<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:redirect="org.apache.xalan.xslt.extensions.Redirect"
    extension-element-prefixes="redirect"
    xmlns:xlink="http://www.w3.org/1999/xlink"
    version="1.0">

    <!-- global variables -->

    <xsl:variable name="title1">JDepend</xsl:variable>
    <xsl:variable name="title2">Package Dependencies</xsl:variable>
    <xsl:variable name="labelx">Abstraction</xsl:variable>
    <xsl:variable name="labely">Instability</xsl:variable>
    <xsl:variable name="svg.dir">svg</xsl:variable>

<!--    Summary Variables-->
    <xsl:variable name="dx">   <!-- pixels between ticks on x axis = dx -->
        30
    </xsl:variable>
    <xsl:variable name="dy">   <!-- pixels between ticks on y axis = dy    -->
        30
    </xsl:variable>
    <xsl:variable name="x0">   <!-- x offset of origin -->
        460
    </xsl:variable>
    <xsl:variable name="y0">   <!-- x offset of origin -->
        3850
    </xsl:variable>

<!--    Individual Variables-->
    <xsl:variable name="iUnits">   <!-- pixels between ticks on either axis = iUnits -->
        4
    </xsl:variable>
    <xsl:variable name="iOffset">   <!-- offset from [0,0] of svg where graph should begin -->
        <xsl:value-of select="($iUnits)*10"/>
    </xsl:variable>


<!--    Template to redirect summary and individual jdepend svg   -->
    <xsl:template name="all.svg" match="JDepend">

        <redirect:write file="{$svg.dir}/jdepend-summary.svg">
            <xsl:call-template name="summary.svg">
                <xsl:with-param name="Packages" select="Packages"/>
            </xsl:call-template>
        </redirect:write>

        <xsl:for-each select="Packages/Package/Stats">

            <redirect:write file="{$svg.dir}/jdepend-{../@name}.svg">

                <xsl:call-template name="individual.svg">
                    <xsl:with-param name="Package" select=".."/>
                </xsl:call-template>

            </redirect:write>

        </xsl:for-each>

    </xsl:template>

<!--    Template to create summary jdepend svg   -->

    <xsl:template name="summary.svg">
        <xsl:param name="Packages"/>
        <svg width="406pt" height="436pt" viewBox="0 0 4060 4360"
            xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">

            <rect x="40" y="30" width="4020" height="4320" style="fill:silver;"/>
            <rect x="3" y="3" width="4014" height="4314" style="fill:white; stroke-width:6; stroke:black"/>
            <g style="fill:#FFFFFF; stroke-width:15; stroke:black">
                <rect x="40" y="40" width="3940" height="4240"/>
            </g>

<!--            Creating Titles -->
            <g style="fill:#000080; font-family:Times New Roman;font-style:italic; font-size:180; text-anchor:end;">
                <text id="Heading" x="3800" y="256">
                    <xsl:value-of select="$title1"/>
                </text>
                <text x="3800" y="456">
                    <xsl:value-of select="$title2"/>
                </text>
            </g>

<!--            Creating Averages for X and Y Labels-->
            <g style="fill:#008000; font-family:Arial;font-size:100;">
                <text id="Notes" x="2600" y="640">Average
                    <xsl:value-of select="$labelx"/> =
                    <xsl:value-of select="format-number(sum($Packages/Package/Stats/A) div count($Packages/Package/Stats/A),'0.00')"/>
                </text>
                <text x="2600" y="760">Average
                    <xsl:value-of select="$labely"/> =
                    <xsl:value-of select="format-number(sum($Packages/Package/Stats/I) div count($Packages/Package/Stats/I),'0.00')"/>
                </text>
            </g>

<!--            Creating X and Y Axis Labels-->
            <g transform="translate(190 840) rotate(-90)" style="fill:#008080; font-family:Times New Roman;font-size:100; text-anchor:end; ">
                <text>
                    <xsl:value-of select="$labely"/>
                </text>
            </g>
            <g style="fill:#008080; font-family:Times New Roman;font-size:100; text-anchor:end; ">
                <text x="3500" y="4100" style="fill:#008080; font-family:Times New Roman;font-size:100; text-anchor:end;">
                    <xsl:value-of select="$labelx"/>
                </text>
            </g>

<!--            X-axis with ticks -->
            <path style="stroke:#000000; stroke-width:6" d="M {($x0)+0},{$y0} L {($x0)+0},{($y0)+39} z M {($x0)+10*$dx},{$y0} L {($x0)+10*$dx},{($y0)+39} z M {($x0)+20*$dx},{$y0} L {$x0+20*$dx},{($y0)+39} z M {$x0+30*$dx},{$y0} L {$x0+30*$dx},{($y0)+39} z M {$x0+40*$dx},{$y0} L {$x0+40*$dx},{($y0)+39} z M {$x0+50*$dx},{$y0} L {$x0+50*$dx},{($y0)+39} z M {$x0+60*$dx},{$y0} L {$x0+60*$dx},{($y0)+39} z M {$x0+70*$dx},{$y0} L {$x0+70*$dx},{($y0)+39} z M {$x0+80*$dx},{$y0} L {$x0+80*$dx},{($y0)+39} z M {$x0+90*$dx},{$y0} L {$x0+90*$dx},{($y0)+39} z M {$x0+100*$dx},{$y0} L {$x0+100*$dx},{($y0)+39} z  M {$x0+100*$dx},{$y0} L {$x0+0*$dx},{$y0} z"/>

<!--            Y-axis with ticks -->
            <path style="stroke:#000000; stroke-width:6" d="M {$x0} {$y0} L {($x0)-33} {$y0} z M {$x0} {($y0)-10*$dy} L {($x0)-33} {($y0)-10*$dy} z M {$x0} {($y0)-20*$dy} L {($x0)-33} {($y0)-20*$dy} z M {$x0} {($y0)-30*$dy} L {($x0)-33} {($y0)-30*$dy} z M {$x0} {($y0)-40*$dy} L {($x0)-33} {($y0)-40*$dy} z M {$x0} {($y0)-50*$dy} L {($x0)-33} {($y0)-50*$dy} z M {$x0} {($y0)-60*$dy} L {($x0)-33} {($y0)-60*$dy} z M {$x0} {($y0)-70*$dy} L {($x0)-33} {($y0)-70*$dy} z M {$x0} {($y0)-80*$dy} L {($x0)-33} {($y0)-80*$dy} z M {$x0} {($y0)-90*$dy} L {($x0)-33} {($y0)-90*$dy} z M {$x0} {($y0)-100*$dy} L {($x0)-33} {($y0)-100*$dy} z M {$x0} {($y0)-100*$dy} L {$x0} {$y0} z"/>

<!--            Diagonal Line from Most Instable to Most Abstract -->
            <path style="stroke:#000000; stroke-width:6" d="M {$x0} {($y0)-100*$dy} L {($x0)+100*$dx} {$y0} z"/>

            <g style="font-family:Times New Roman;font-size:80; text-anchor:middle;">
                <text x="{($x0)+0}" y="{($y0)+110}"> 0</text>
                <text x="{$x0+10*$dx}" y="{($y0)+110}">.1</text>
                <text x="{$x0+20*$dx}" y="{($y0)+110}">.2</text>
                <text x="{$x0+30*$dx}" y="{($y0)+110}">.3</text>
                <text x="{$x0+40*$dx}" y="{($y0)+110}">.4</text>
                <text x="{$x0+50*$dx}" y="{($y0)+110}">.5</text>
                <text x="{$x0+60*$dx}" y="{($y0)+110}">.6</text>
                <text x="{$x0+70*$dx}" y="{($y0)+110}">.7</text>
                <text x="{$x0+80*$dx}" y="{($y0)+110}">.8</text>
                <text x="{$x0+90*$dx}" y="{($y0)+110}">.9</text>
                <text x="{$x0+100*$dx}" y="{($y0)+110}">1</text>
            </g>

            <g style="font-family:Times New Roman;font-size:80; text-anchor:middle;">
                <text x="{($x0)-60}" y="{($y0)-100*$dy}"> 1</text>
                <text x="{($x0)-60}" y="{($y0)-90*$dy}">.9</text>
                <text x="{($x0)-60}" y="{($y0)-80*$dy}">.8</text>
                <text x="{($x0)-60}" y="{($y0)-70*$dy}">.7</text>
                <text x="{($x0)-60}" y="{($y0)-60*$dy}">.6</text>
                <text x="{($x0)-60}" y="{($y0)-50*$dy}">.5</text>
                <text x="{($x0)-60}" y="{($y0)-40*$dy}">.4</text>
                <text x="{($x0)-60}" y="{($y0)-30*$dy}">.3</text>
                <text x="{($x0)-60}" y="{($y0)-20*$dy}">.2</text>
                <text x="{($x0)-60}" y="{($y0)-10*$dy}">.1</text>
                <text x="{($x0)-60}" y="{($y0)+0}">0</text>
            </g>

            <xsl:for-each select="$Packages/Package/Stats">
                <xsl:choose>
                    <xsl:when test="D &lt; .5">
                        <xsl:call-template name="summary.circle.svg">
                            <xsl:with-param name="Package" select=".."/>
                            <xsl:with-param name="red" select="(2 * D)*255"/>
                            <xsl:with-param name="green" select="(.5 + D)*255"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="summary.circle.svg">
                            <xsl:with-param name="Package" select=".."/>
                            <xsl:with-param name="red" select="(1) * 255"/>
                            <xsl:with-param name="green" select="(1 - D) * 2 * 255"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>

        </svg>
    </xsl:template>

<!--    Template to create summary circle for each package   -->

    <xsl:template name="summary.circle.svg" xmlns="http://www.w3.org/2000/svg">
        <xsl:param name="Package"/>
        <xsl:param name="red"/>
        <xsl:param name="green"/>

        <a onclick="goToPackage('{$Package/@name}')">
            <circle onmouseover="changePackageName('{$Package/@name}');" onmouseout="changePackageName('');" cx="{($x0)+((A)*100*$dx)}" cy="{($y0)-((I)*100*$dy)}" r="32" fill="rgb({$red},{$green},0)"/>
        </a>

    </xsl:template>

<!--    Template to create individual jdepend svg   -->

    <xsl:template name="individual.svg">
        <xsl:param name="Package"/>
        <svg width="{($iOffset)+($iUnits)*10}pt" height="{($iOffset)+($iUnits)*10}pt" viewBox="0 0 {($iOffset)+($iUnits)*110} {($iOffset)+($iUnits)*110}" style="fill:#000000"
            xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">

            <xsl:choose>
                <xsl:when test="$Package/Stats/D &lt; .5">
                    <xsl:call-template name="individual.circle.svg">
                        <xsl:with-param name="Package" select="$Package"/>
                        <xsl:with-param name="red" select="(2 * $Package/Stats/D)*255"/>
                        <xsl:with-param name="green" select="(.5 + $Package/Stats/D)*255"/>
                        <xsl:with-param name="bgColor" select="0"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="individual.circle.svg">
                        <xsl:with-param name="Package" select="$Package"/>
                        <xsl:with-param name="red" select="(1) * 255"/>
                        <xsl:with-param name="green" select="(1 - $Package/Stats/D) * 2 * 255"/>
                        <xsl:with-param name="bgColor" select="0"/>
                    </xsl:call-template>
                </xsl:otherwise>
            </xsl:choose>
        </svg>

    </xsl:template>

<!--    Template to create individual circle   -->

    <xsl:template name="individual.circle.svg" xmlns="http://www.w3.org/2000/svg">
        <xsl:param name="Package"/>
        <xsl:param name="red"/>
        <xsl:param name="green"/>
        <xsl:param name="bgColor"/>

        <a onclick="goToSummary()">

            <!-- Rectangle encompasing the individual graph -->
            <rect width="{($iUnits)*100}" height="{($iUnits)*100}" x="{$iOffset}" y="{$iOffset}" style="fill:rgb({$red},{$green},0); stroke:rgb({$bgColor},{$bgColor},{$bgColor}); stroke-width:6"/>

            <!-- Diagonal Line from Most Instable to Most Abstract -->
            <path style="stroke:rgb(($bgColor),($bgColor),($bgColor)); stroke-width:6" d="M {$iOffset} {$iOffset} L {($iOffset)+($iUnits)*100} {($iOffset)+($iUnits)*100} z"/>

            <circle cx="{($iOffset)+(($Package/Stats/A)*100*$iUnits)}" cy="{($iOffset)+((1-($Package/Stats/I))*100*$iUnits)}" r="{($iUnits)*6}" fill="rgb({$bgColor},{$bgColor},{$bgColor})"/>
        </a>

    </xsl:template>

</xsl:stylesheet>
