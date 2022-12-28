<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
  <title>Song trainer Home Page</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
  <link rel="icon" href="/favicon.ico" />
  <script src="/js/main.js" />"></script>
</head>
<body>
<tag:header user="${user}"></tag:header>

<div class="wrapper">
  <div class="playlists-title">
    <h3>Ratings of ${song.title}</h3>
  </div>

  <table>
    <thead>
    <tr>
      <th>Feelings</th>
      <th>Accuracy</th>
      <th>Number of Mistakes</th>
      <th>Comment</th>
    </tr>
    </thead>
    <tbody>

    <!-- Loop over and print playlists -->
    <c:forEach var="tempRatings" items="${ratings}">

      <tr>
        <td>${tempRatings.feelings}</td>
        <td>${tempRatings.accuracy}</td>
        <td>${tempRatings.numberMistakes}</td>
        <td>${tempRatings.comment}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>


</div>
</body>
</html>

<style>


  .playlists-title {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
  }

  .playlists-title a {
    padding: 12px 20px;
    background-color: #13b992;
    color: #1a1d28;
    border-radius: 12px;
    text-decoration: none;
    transition: linear 0.3s;
  }
  .playlists-title a:hover {
    box-shadow: 0px 0px 5px 5px rgba(19, 185, 146, 0.24);
  }

</style>
