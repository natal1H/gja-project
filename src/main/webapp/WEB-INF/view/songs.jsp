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
      </tr>

      <!-- Loop over and print songs -->
      <c:forEach var="tempSong" items="${songs}">
          <tr>
              <td>${tempSong.title}</td>
              <td>${tempSong.artist}</td>
          </tr>
      </c:forEach>
  </table>

  <hr>
  <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
