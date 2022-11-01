<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song trainer Home Page</title>
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js" />"></script>
</head>
<body>
    <h2>Song Trainer Home Page</h2>

    Welcome to Song Trainer home page!
    <hr>
    <a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=ALL&sort=ArtistASC">GO TO PROFILE</a> |
    <a href="${pageContext.request.contextPath}/settings">USER SETTINGS</a>
    <hr>

    <security:authorize access="hasRole('LECTOR')">
        <!-- Link to point to /lector ... only for lectors -->
        <p>
            <a href="${pageContext.request.contextPath}/lectors">Lector page</a> (Only for lectors)
        </p>
        <hr>
    </security:authorize>

    <a href="${pageContext.request.contextPath}/songs?inst=ALL&sort=ArtistASC">View all my songs</a>
    <hr>

    <h3>My playlists</h3>

    <table>
        <tr>
            <th>Name</th>
            <th>Instrument</th>
        </tr>

        <!-- Loop over and print playlists -->
        <c:forEach var="tempPlaylist" items="${playlists}">
            <tr>
                <td><a href="${pageContext.request.contextPath}/playlist?id=${tempPlaylist.id}&sort=ArtistASC">${tempPlaylist.name}</a></td>
                <td>${tempPlaylist.instrumentStr}</td>
            </tr>
        </c:forEach>
    </table>

    <hr>
    <a href="${pageContext.request.contextPath}/playlist/addPlaylist">Create new playlist</a>
    <hr>

    <!-- Logout button -->
    <form:form action="${pageContext.request.contextPath}/logout" method="POST">
        <input type="submit" value="Logout" />
    </form:form>
</body>
</html>
