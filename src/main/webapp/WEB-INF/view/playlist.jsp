    <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Playlist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <link rel="icon" href="/favicon.ico" />
    <script src="/js/main.js" />"></script>

    <script>
        function submitSortForm() {
            document.getElementById("sortForm").submit();
        }
    </script>
</head>
<body>
    <tag:header></tag:header>
    <div class="wrapper">
        <div class="top">
            <h2>${playlist.name} (${playlist.instrumentStr})</h2>
            <span class="icons">
                <a href="${updatePlaylistLink}" class="pencil"><i class="fa fa-solid fa-pencil"></i></a>
                <a href="${deletePlaylistLink}" class="trash"
                    onclick="if (!(confirm('Are you sure you want to delete this playlist?'))) return false"><i class="fa fa-solid fa-trash"></i></a>
            </span>

        </div>

        <!-- construct an "update" link with playlist id -->
        <c:url var="updatePlaylistLink" value="/playlist/showUpdateForm">
            <c:param name="playlistId" value="${playlist.id}" />
        </c:url>

        <!-- construct a "delete" link with playlist id -->
        <c:url var="deletePlaylistLink" value="/playlist/deletePlaylist">
            <c:param name="playlistId" value="${playlist.id}" />
        </c:url>


        <table>
            <thead>
                <th>
                    <span>
                        Title
                    <span class="carrets">
                          <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=TitleDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                          <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=TitleASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                      </span>
                    </span>
                </th>
                <th>
                    <span>
                        Artist
                        <span class="carrets">
                      <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=ArtistDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                      <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=ArtistASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                  </span>
                    </span>
                </th>
                <th>Instrument</th>
                <th>Tuning</th>
                <th>
                    <span>
                        Length
                    <span class="carrets">
                      <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=LengthDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                      <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=LengthASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                  </span>
                    </span>
                </th>
                <th>
                    <span>
                         Times Played
                        <span class="carrets">
                          <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=TimesPlayedDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                          <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=TimesPlayedASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                      </span>
                    </span>

                </th>
                <th>
                    <span>
                        Last Played
                        <span class="carrets">
                          <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=LastPlayedDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                          <a href="${pageContext.request.contextPath}/playlist?id=${pageContext.request.getParameter("id")}&sort=LastPlayedASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                      </span>
                    </span>

                </th>
                <th>Visible</th>
                <th style="text-align: right">Actions</th>
            </thead>

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
                            <a href="${updateLink}" class="pencil"><i class="fa fa-solid fa-pencil"></i></a> <!-- todo: this redirects back to /songs after update -->
                            <a href="${deleteLink}" class="trash"
                               onclick="if (!(confirm('Are you sure you want to delete this song?'))) return false"><i class="fa fa-solid fa-trash"></i></a>
                            <a href="${removeFromPlaylistLink}" class="discard"><i class="fa fa-solid fa-close"></i></a>
                        </td>
                    </tr>
                </c:forEach>
            <!--</c:if>-->

        </table>

    </div>
</body>
</html>


<style>
    .top {
        display: flex;
        justify-content: space-between;
        align-items: baseline;
        margin-bottom: 20px;
    }

    .icons a {
        font-size: 14px;
        padding: 7px 15px;
        border: 1px solid #b0b0b0;
        border-radius: 7px;
        background-color: transparent;
        color: #b0b0b0;
        cursor: pointer;
        margin-right: 5px;
    }

    .icons .pencil, .icons .trash, .icons .discard  {
        transition: linear 0.3s;
    }
    .icons .pencil:hover {
        color: #4de14d;
        border: 1px solid #4de14d;
    }
    .icons .trash:hover {
        color: #ee1d24;
        border: 1px solid #ee1d24;
    }

    .icons .discard:hover {
        color: #ee7b1d;
        border: 1px solid #ee7b1d;
    }
    th span {
        display: flex;
        justify-content: space-between;
    }
    th span span {
        width: 20px;
    }

</style>
