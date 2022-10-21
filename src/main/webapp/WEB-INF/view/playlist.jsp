<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Playlist</title>
</head>
<body>
    <h2>Playlist</h2>
    Name: ${playlist.name}

    <table>
        <tr>
            <th>Title</th>
            <th>Artist</th>
            <th>Instrument</th>
            <th>Tuning</th>
            <th>Length</th>
            <th>Action</th>
        </tr>

        <!-- Loop over and print songs -->
        <!--<c:if test="${songs != null}">-->
            <c:forEach var="tempSong" items="${songs}">

                <!-- construct a "delete" link with song id -->
                <c:url var="deleteLink" value="/playlist/deleteSong">
                    <c:param name="songId" value="${tempSong.id}" />
                </c:url>

                <tr>
                    <td>${tempSong.title}</td>
                    <td>${tempSong.artist}</td>
                    <td>${tempSong.instrumentStr}</td>
                    <td>
                        <c:choose>
                            <c:when test="${tempSong.tuning!=null}">
                                ${tempSong.tuningStr}
                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>

                    </td>
                    <td>${tempSong.lengthStr}</td>
                    <td>
                        UPDATE <!-- TODO -->
                        |
                        <a href="${deleteLink}"
                           onclick="if (!(confirm('Are you sure you want to delete this song?'))) return false">DELETE</a>
                        |
                        REMOVE FROM PLAYLIST <!-- TODO -->
                    </td>
                </tr>
            </c:forEach>
        <!--</c:if>-->

    </table>


    <hr>
    <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
