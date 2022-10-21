<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Songs</title>
</head>
<body>

  <h2>All songs I can play</h2>

  <table>
      <tr>
          <th>Title</th>
          <th>Artist</th>
          <th>Instrument</th>
          <th>Tuning</th>
          <th>Length</th>
      </tr>

      <!-- Loop over and print songs -->
      <c:forEach var="tempSong" items="${songs}">
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
          </tr>
      </c:forEach>
  </table>

  <hr>
  <a href="${pageContext.request.contextPath}/songs?inst=all">All songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=guitar">Guitar songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=bass">Bass songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=drums">Drums songs</a><br>
  <a href="${pageContext.request.contextPath}/songs?inst=piano">Piano songs</a><br>
  <hr>

  <a href="${pageContext.request.contextPath}/addSong">Add song</a>

  <hr>

  <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
