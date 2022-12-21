<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Songs</title>

    <script>
        function submitSortForm() {
            document.getElementById("sortForm").submit();
        }
    </script>
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <link rel="icon" href="/favicon.ico" />
    <script src="/js/main.js" /></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <tag:header></tag:header>
    <div class="wrapper">
        <div class="songs-title">
            <h3>My Songs</h3>
            <a href="${pageContext.request.contextPath}/songs/addSong"><i class="fa fa-solid fa-plus"></i> Add</a>
        </div>

      <table>
          <thead>
              <tr>
                  <th>
                      <span>
                        Title
                          <span class="carrets">
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=TitleDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=TitleASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                          </span>
                      </span>
                  </th>
                  <th>
                      <span>
                        Artist
                          <span class="carrets">
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=ArtistDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=ArtistASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                          </span>
                      </span>
                  </th>
                  <th>
                      <span>
                        Instrument
                          <!--<span class="carrets">
                              <i class="fa fa-solid fa-filter"></i>
                          </span>-->
                      </span>
                  </th>
                  <th>
                      <span>
                        Tuning
                          <span class="carrets">
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=Tuning">
                              <i class="fa fa-solid fa-filter"></i>
                              </a>
                          </span>
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
                          <span class="carrets">
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=LengthDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=LengthASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                          </span>
                      </span>
                  </th>
                  <th>
                      <span>
                        Played
                          <span class="carrets">
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=TimesPlayedDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=TimesPlayedASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                          </span>
                      </span>
                  </th>
                  <th>
                      <span>
                        Last Played
                          <span class="carrets">
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=LastPlayedDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                              <a href="${pageContext.request.contextPath}/songs?inst=${pageContext.request.getParameter("inst")}&sort=LastPlayedASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                          </span>
                      </span>
                  </th>
                  <th>
                      <span>
                        Visible
                      </span>
                  </th>
                  <th>
                      <span>
                        Action
                      </span>
                  </th>
              </tr>
          </thead>

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
                      <button onclick="new Audio('/songs/backingTrack/?songId=${tempSong.id}').play()" class="play-btn"><i class="fa fa-solid fa-play"></i>Play</button>
                  </td>
                  <td>${tempSong.lengthStr}</td>
                  <td>${tempSong.times_played}x</td>
                  <td><c:choose>
                      <c:when test="${tempSong.last_played!=null}">
                          ${tempSong.last_played}
                      </c:when>
                      <c:otherwise>
                          Never
                      </c:otherwise>
                  </c:choose></td>
                  <td>
                      <c:choose>
                          <c:when test="${tempSong.visible}">
                              <i class="fa fa-solid fa-check" style="color: green"></i>
                          </c:when>
                          <c:otherwise>
                              <i class="fa fa-solid fa-xmark"></i>
                          </c:otherwise>
                      </c:choose>
                  </td>
                  <td class="icons">
                      <a href="${updateLink}">
                          <button class="pencil"><i class="fa fa-solid fa-pencil"></i></button>
                      </a>
                      <a href="${deleteLink}"
                         onclick="if (!(confirm('Are you sure you want to delete this song?'))) return false">
                          <button class="trash"><i class="fa fa-solid fa-trash"></i></button>
                          </a>
                      <a href="${addToPlaylistLink}">
                          <button class="plus"><i class="fa fa-solid fa-plus"></i></button>
                      </a>
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

    </div>
</body>
</html>

<style>
    .play-btn {
        color: #13b992;
        border: 1px solid #13b992;
        width: 70px;
        display: flex;
        justify-content: space-between;
    }

    th span {
        display: flex;
        justify-content: space-between;
    }
    th span span {
        width: 20px;
    }
    table .icons .plus {
        transition: linear 0.3s;
    }
    table .icons .plus:hover {
        color: #4de1dc;
        border: 1px solid #4de1dc;
    }

    table button {
        padding: 7px 10px !important;
    }
    .songs-title {
        display: flex;
        justify-content: space-between;
        align-items: baseline;
    }
    .songs-title a {
        padding: 12px 20px;
        background-color: #13b992;
        color: #1a1d28;
        border-radius: 12px;
        text-decoration: none;
        transition: linear 0.3s;
    }
    .songs-title a:hover {
        box-shadow: 0px 0px 5px 5px rgba(19, 185, 146, 0.24);
    }




</style>
