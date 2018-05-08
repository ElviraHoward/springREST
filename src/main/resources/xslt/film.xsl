<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" indent="yes"/>
    <xsl:strip-space elements="*"/>

    <xsl:template match="/">

        <html>
            <head>
                <title>Films</title>
            </head>
            <body>

                <p>
                    <a href="../DirectorController/director">Режиссеры</a>
                </p>
                <p>
                    <a href="../FilmController/film">Фильмы</a>
                </p>

                <table border="1">

                    <tr>
                        <th>Name</th>
                        <th>Release Date</th>
                        <th>Rating</th>
                        <th>Director</th>
                    </tr>

                    <xsl:for-each select="List/item">
                        <xsl:sort/>
                        <tr>
                            <td><xsl:value-of select="name"/></td>
                            <td><xsl:value-of select="releaseDate"/></td>
                            <td><xsl:value-of select="rating"/></td>
                            <td><xsl:value-of select="directorByFilm"/></td>
                        </tr>
                    </xsl:for-each>

                </table>
            </body>
        </html>

    </xsl:template>

</xsl:stylesheet>