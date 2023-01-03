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

        <form:form action="saveSongToPlaylist" method="post">
        <table>
            <tr>
                <td>Song</td>
                <td>${song.artist} - ${song.title} (${song.instrumentStr})</td>
            </tr>
            <tr>
                <td>Playlist</td>
                <td>
                    <input type="hidden" name="songId" value="${song.id}">
                    <select name="playlist">
                        <c:forEach var="tempPlaylist" items="${playlists}">
                            <option value="${tempPlaylist.id}">${tempPlaylist.name}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>



          <!-- Submit Button -->
          <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle-o"></i> <span>SAVE</span></button>
      </form:form>
    </div>
</body>
</html>

<style>
    .btn {
        padding: 12px 20px;
        background-color: #13b992;
        color: #1a1d28;
        border-radius: 12px;
        text-decoration: none;
        transition: linear 0.3s;
        display: flex;
        width: 120px;
        justify-content: space-between;
        cursor: pointer;
        align-items: baseline;
        font-size: 16px;
    }

    select {
        background-color: transparent;
        color: white;
        padding: 10px;
        border: 1px solid white;
        border-radius: 10px;
    }
</style>
