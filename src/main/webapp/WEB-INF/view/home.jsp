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
    <script src="/js/main.js" />"></script>
</head>
<body>
    <tag:header></tag:header>

    <div class="wrapper">
        <h2>Home</h2>

        <div class="playlists-title">
            <h3>My playlists</h3>
            <a href="${pageContext.request.contextPath}/playlist/addPlaylist"><i class="fa fa-solid fa-plus"></i> Add</a>
        </div>


        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Instrument</th>
                    <th style="text-align: right;">Actions</th>
                </tr>
            </thead>
            <tbody>
            <!-- Loop over and print playlists -->
            <c:forEach var="tempPlaylist" items="${playlists}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/playlist?id=${tempPlaylist.id}&sort=ArtistASC">${tempPlaylist.name}</a></td>
                    <td>${tempPlaylist.instrumentStr}</td>
                    <td class="icons">
                        <button class="pencil"><i class="fa fa-solid fa-pencil"></i></button>
                        <button class="trash"><i class="fa fa-solid fa-trash"></i></button>
                    </td>
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
