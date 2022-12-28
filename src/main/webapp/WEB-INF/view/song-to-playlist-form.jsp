<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
    <link rel="icon" href="/favicon.ico" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
</head>
<body>
<tag:header user="${user}"></tag:header>
    <div class="wrapper">
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
    </div>
</body>
</html>
