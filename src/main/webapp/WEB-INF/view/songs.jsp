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
  <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
