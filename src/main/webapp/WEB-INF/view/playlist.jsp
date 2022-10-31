<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Playlist</title>

    <script>
        function submitSortForm() {
            document.getElementById("sortForm").submit();
        }
    </script>
</head>
<body>
    <h2>Playlist</h2>
    Name: ${playlist.name} (${playlist.instrumentStr})<br>

    SORT SONGS BY:

    <form id="sortForm" action="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}" method="get">
        <input type="hidden" name="id" value="${pageContext.request.getParameter("id")}">
        ArtistASC<input type="radio" name="sort" value="ArtistASC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("ArtistASC") ? "checked=\"checked\"" : ""}>|
        ArtistDESC<input type="radio" name="sort" value="ArtistDESC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("ArtistDESC") ? "checked=\"checked\"" : ""}>|
        TitleASC<input type="radio" name="sort" value="TitleASC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TitleASC") ? "checked=\"checked\"" : ""}>|
        TitleDESC<input type="radio" name="sort" value="TitleDESC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TitleDESC") ? "checked=\"checked\"" : ""}>|
        Tuning<input type="radio" name="sort" value="Tuning" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("Tuning") ? "checked=\"checked\"" : ""}>|
        LengthASC<input type="radio" name="sort" value="LengthASC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LengthASC") ? "checked=\"checked\"" : ""}>|
        LengthDESC<input type="radio" name="sort" value="LengthDESC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LengthDESC") ? "checked=\"checked\"" : ""}>|
        TimesPlayedASC<input type="radio" name="sort" value="TimesPlayedASC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TimesPlayedASC") ? "checked=\"checked\"" : ""}>|
        TimesPlayedDESC<input type="radio" name="sort" value="TimesPlayedDESC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TimesPlayedDESC") ? "checked=\"checked\"" : ""}>|
        LastPlayedASC<input type="radio" name="sort" value="LastPlayedASC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LastPlayedASC") ? "checked=\"checked\"" : ""}>|
        LastPlayedDESC<input type="radio" name="sort" value="LastPlayedDESC" onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LastPlayedDESC") ? "checked=\"checked\"" : ""}>
    </form>


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
            <th>Times Played</th>
            <th>Last Played</th>
            <th>Visible</th>
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
                    <td>${tempSong.times_played}</td>
                    <td><c:choose>
                        <c:when test="${tempSong.last_played!=null}">
                            ${tempSong.last_played}
                        </c:when>
                        <c:otherwise>
                            Never
                        </c:otherwise>
                    </c:choose></td>
                    <td>${tempSong.visible}</td>
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
