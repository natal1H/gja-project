<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Add Song For Student</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/favicon.ico"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js"/>
    </script>
</head>
<body>
<tag:header></tag:header>
<div class="wrapper">
    <h2>Assigned songs to: ${student.userName}</h2>

    <table>
   <thead>
      <tr>
         <th>
            <span>
            Title
            </span>
         </th>
         <th>
            <span>
            Artist
            <span/>
         </th>
         <th>
            <span>
               Instrument
            </span>
         </th>
         <th>
            <span>
            Tuning
            </span>
         </th>
         <th>
            <span>
            Play
            </span>
         </th>
         <th>
            <span>
            Length
            </span>
         </th>
         <th>
            <span>
            Played
            </span>
         </th>
         <th>
            <span>
            Last Played
            </span>
         </th>
      </tr>
   </thead>
        <c:forEach var="song" items="${songs}">
            <tr>
                <td>${song.title}</td>
                <td>${song.artist}</td>
                <td>${song.instrumentStr}</td>
                <td>
                    <c:choose>
                        <c:when test="${song.tuning!=null}">
                            ${song.tuningStr}
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>

                </td>
                <td>
                    <button onclick="new Audio('/songs/backingTrack/?songId=${song.id}').play()" class="play-btn"><i class="fa fa-solid fa-play"></i>Play</button>
                </td>
                <td>${song.lengthStr}</td>
                <td>${song.times_played}x</td>
                <td><c:choose>
                    <c:when test="${song.last_played!=null}">
                        ${song.last_played}
                    </c:when>
                    <c:otherwise>
                        Never
                    </c:otherwise>
                </c:choose></td>
            </tr>
        </c:forEach>
</table>
</div>

</body>
</html>
