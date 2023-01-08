<%@ page import="fit.gja.songtrainer.util.InstrumentEnum" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Playlist</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <link rel="icon" href="/favicon.ico"/>
    <script src="/js/main.js"/>
    </script>
</head>
<body>
<tag:header user="${user}"/>
<div class="wrapper">
    <!-- construct an "update" link with playlist id -->
    <c:url var="updatePlaylistLink" value="/playlist/showUpdateForm">
        <c:param name="playlistId" value="${playlist.id}"/>
    </c:url>

    <!-- construct a "delete" link with playlist id -->
    <c:url var="deletePlaylistLink" value="/playlist/deletePlaylist">
        <c:param name="playlistId" value="${playlist.id}"/>
    </c:url>
    <c:set var="privilegedUser" value="${user.id == playlist.user.id}"/>
    <div class="top">
        <h2>${playlist.name} (${playlist.instrumentStr})</h2>
        <span class="icons">
            <c:if test="${privilegedUser}">
                <a href="${updatePlaylistLink}" class="pencil"><i class="fa fa-solid fa-pencil"></i></a>
                <a href="${deletePlaylistLink}" class="trash"
                   onclick="if (!(confirm('Are you sure you want to delete this playlist?'))) return false"><i
                        class="fa fa-solid fa-trash"></i></a>
            </c:if>

            <c:if test="${not empty playlist.songs}">
                <c:url var="playPlaylist" value="/song">
                    <c:param name="songId" value="${playlist.songs[0].id}"/>
                    <c:param name="playlistId" value="${playlist.id}"/>
                </c:url>
                <a href="${playPlaylist}">
                    Play playlist
                </a>
            </c:if>
        </span>
    </div>


    <tag:songList songs="${playlist.songs}" showVisibleColumn="false" showPlayButton="true" editable="${privilegedUser}" showRemoveFromPlaylist="${privilegedUser}" showAddToPlaylist="false"/>
</div>


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

    .icons .pencil, .icons .trash, .icons .discard {
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

</style>
</body>
</html>

