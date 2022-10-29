<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Playlist</title>
</head>
<body>
    <h2>Playlist</h2>
    Name: ${playlist.name} (${playlist.instrumentStr})<br>

    <!-- construct an "update" link with playlist id -->
    <c:url var="updatePlaylistLink" value="/playlist/showUpdateForm">
        <c:param name="playlistId" value="${playlist.id}" />
    </c:url>

    <!-- construct a "delete" link with playlist id -->
    <c:url var="deletePlaylistLink" value="/playlist/deletePlaylist">
        <c:param name="playlistId" value="${playlist.id}" />
    </c:url>

    <a href="${updatePlaylistLink}">UPDATE PLAYLIST</a>
    |
    <a href="${deletePlaylistLink}"
       onclick="if (!(confirm('Are you sure you want to delete this playlist?'))) return false">DELETE PLAYLIST</a>

    <hr>

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

                <!-- construct an "update" link with song id -->
                <c:url var="updateLink" value="/songs/showUpdateForm">
                    <c:param name="songId" value="${tempSong.id}" />
                </c:url>

                <!-- construct a "delete" link with song id -->
                <c:url var="deleteLink" value="/playlist/deleteSong">
                    <c:param name="songId" value="${tempSong.id}" />
                    <c:param name="playlistId" value="${playlist.id}" />
                </c:url>

                <!-- construct a "removeFromPlaylist" link with song id -->
                <c:url var="removeFromPlaylistLink" value="/playlist/removeSongFromPlaylist">
                    <c:param name="songId" value="${tempSong.id}" />
                    <c:param name="playlistId" value="${playlist.id}" />
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
                        <a href="${updateLink}">UPDATE</a> <!-- todo: this redirects back to /songs after update -->
                        |
                        <a href="${deleteLink}"
                           onclick="if (!(confirm('Are you sure you want to delete this song?'))) return false">DELETE</a>
                        |
                        <a href="${removeFromPlaylistLink}">REMOVE FROM PLAYLIST</a>
                    </td>
                </tr>
            </c:forEach>
        <!--</c:if>-->

    </table>


    <hr>
    <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
