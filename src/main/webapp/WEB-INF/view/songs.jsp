<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Songs</title>

    <script>
        function submitSortForm() {
            document.getElementById("sortForm").submit();
        }
    </script>
</head>
<body>

  <h2>All songs I can play</h2>

  SORT BY:

  <form id="sortForm" action="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}" method="get">
      <input type="hidden" name="inst" value="${pageContext.request.getParameter("inst")}">
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

  <table>
      <tr>
          <th>Title</th>
          <th>Artist</th>
          <th>Instrument</th>
          <th>Tuning</th>
          <th>Play</th>
          <th>Length</th>
          <th>Times Played</th>
          <th>Last Played</th>
          <th>Visible</th>
          <th>Action</th>
      </tr>

      <!-- Loop over and print songs -->
      <c:forEach var="tempSong" items="${songs}">

          <!-- construct an "update" link with song id -->
          <c:url var="updateLink" value="/songs/showUpdateForm">
              <c:param name="songId" value="${tempSong.id}" />
          </c:url>
          <!-- construct a "delete" link with song id -->
          <c:url var="deleteLink" value="/songs/delete">
              <c:param name="songId" value="${tempSong.id}" />
          </c:url>
          <!-- construct a "addToPlaylist" link with song id -->
          <c:url var="addToPlaylistLink" value="/songs/showAddToPlaylistForm">
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
              <td>
                  <button onclick="new Audio('/songs/backingTrack/?songId=${tempSong.id}').play()">Play</button>
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
                  <a href="${updateLink}">UPDATE</a>
                  |
                  <a href="${deleteLink}"
                     onclick="if (!(confirm('Are you sure you want to delete this song?'))) return false">DELETE</a>
                  |
                  <a href="${addToPlaylistLink}">ADD TO PLAYLIST</a>
              </td>
          </tr>
      </c:forEach>
  </table>

  <hr>
  <a href="${pageContext.request.contextPath}/songs?inst=ALL&sort=${pageContext.request.getParameter("sort")}">All songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=GUITAR&sort=${pageContext.request.getParameter("sort")}">Guitar songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=BASS&sort=${pageContext.request.getParameter("sort")}">Bass songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=DRUMS&sort=${pageContext.request.getParameter("sort")}">Drums songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=PIANO&sort=${pageContext.request.getParameter("sort")}">Piano songs</a><br>
  <hr>

  <a href="${pageContext.request.contextPath}/songs/addSong">Add song</a><br>

  <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
