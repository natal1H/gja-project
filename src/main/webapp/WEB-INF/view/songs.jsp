<%@ page import="fit.gja.songtrainer.util.InstrumentEnum" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="icon" href="/favicon.ico"/>
    <script src="/js/main.js"/>
    </script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>

<tag:header user="${user}"></tag:header>
<div class="wrapper">
    <div class="songs-title">
        <h3>My Songs</h3>
        <a href="${pageContext.request.contextPath}/songs/addSong"><i class="fa fa-solid fa-plus"></i> Add</a>
    </div>


    <tag:songList songs="${songs}" showVisibleColumn="true" showPlayButton="true" editable="true" showRemoveFromPlaylist="false" showAddToPlaylist="true"/>

</div>
</body>
</html>

<style>
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


    .softer a {
        font-weight: normal;
        text-align: left;
    }

    .softer {
        background-color: #474956;
    }

    th span .dropbtn {
        border: none;
        min-width: 0px !important;
        padding: 0px !important;
        background-color: transparent !important;
    }

    th span .dropbtn:hover {
        background-color: transparent !important;
    }


</style>
