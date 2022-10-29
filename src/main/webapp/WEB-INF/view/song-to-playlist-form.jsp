<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
</head>
<body>
  <h2>Add song to playlist</h2>

  Song: ${song.artist} - ${song.title} (${song.instrumentStr})
  <hr>

  <form:form action="saveSongToPlaylist" method="post">
      <input type="hidden" name="songId" value="${song.id}">
      <select name="playlist">
          <c:forEach var="tempPlaylist" items="${playlists}">
              <option value="${tempPlaylist.id}">${tempPlaylist.name}</option>
          </c:forEach>
      </select>
      <br>

      <!-- Submit Button -->
      <button type="submit" class="btn btn-primary">SAVE</button>
  </form:form>

</body>
</html>
