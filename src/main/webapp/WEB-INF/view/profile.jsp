<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>

    <script>
        function submitSortForm() {
            document.getElementById("sortForm").submit();
        }

        async function removeProfilePicture() {
            const response = await fetch("${pageContext.request.contextPath}/profilePicture", {
                method: "DELETE"
            })
            console.log(response);
            location.reload()
        }

        document.addEventListener("DOMContentLoaded", function () {
            const pictureForm = document.getElementById("pictureForm");
            pictureForm.onsubmit = async (e) => {
                e.preventDefault();
                const form = e.currentTarget;
                const url = form.action;

                try {
                    const formData = new FormData(form);
                    const response = await fetch(url, {
                        method: 'POST',
                        body: formData
                    });
                    console.log(response);
                    location.reload()
                } catch (error) {
                    console.error(error);
                }
            }
        });
    </script>
</head>
<body>
<h2>${user.userName}'s profile</h2>

First name: ${user.firstName}, Last name: ${user.lastName}<br>

<div>
    <img src="${pageContext.request.contextPath}/profilePicture">
    <form id="pictureForm" action="${pageContext.request.contextPath}/profilePicture" method="post"
          enctype="multipart/form-data">
        <label for="picture">Change profile picture</label>
        <input id="picture" name="picture" type="file">
        <input type="submit">
    </form>
    <input type="button" onclick="removeProfilePicture()" value="Remove">
</div>


Songs ${user.userName} can play:<br>

SORT BY:
<form id="sortForm"
      action="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}"
      method="get">
    <input type="hidden" name="inst" value="${pageContext.request.getParameter("inst")}">
    <input type="hidden" name="id" value="${pageContext.request.getParameter("id")}">
    ArtistASC<input type="radio" name="sort" value="ArtistASC"
                    onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("ArtistASC") ? "checked=\"checked\"" : ""}>|
    ArtistDESC<input type="radio" name="sort" value="ArtistDESC"
                     onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("ArtistDESC") ? "checked=\"checked\"" : ""}>|
    TitleASC<input type="radio" name="sort" value="TitleASC"
                   onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TitleASC") ? "checked=\"checked\"" : ""}>|
    TitleDESC<input type="radio" name="sort" value="TitleDESC"
                    onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TitleDESC") ? "checked=\"checked\"" : ""}>|
    Tuning<input type="radio" name="sort" value="Tuning"
                 onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("Tuning") ? "checked=\"checked\"" : ""}>|
    LengthASC<input type="radio" name="sort" value="LengthASC"
                    onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LengthASC") ? "checked=\"checked\"" : ""}>|
    LengthDESC<input type="radio" name="sort" value="LengthDESC"
                     onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LengthDESC") ? "checked=\"checked\"" : ""}>|
    TimesPlayedASC<input type="radio" name="sort" value="TimesPlayedASC"
                         onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TimesPlayedASC") ? "checked=\"checked\"" : ""}>|
    TimesPlayedDESC<input type="radio" name="sort" value="TimesPlayedDESC"
                          onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("TimesPlayedDESC") ? "checked=\"checked\"" : ""}>|
    LastPlayedASC<input type="radio" name="sort" value="LastPlayedASC"
                        onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LastPlayedASC") ? "checked=\"checked\"" : ""}>|
    LastPlayedDESC<input type="radio" name="sort" value="LastPlayedDESC"
                         onclick="submitSortForm()" ${pageContext.request.getParameter("sort").equals("LastPlayedDESC") ? "checked=\"checked\"" : ""}>
</form>

<table>
    <tr>
        <th>Title</th>
        <th>Artist</th>
        <th>Instrument</th>
        <th>Tuning</th>
        <th>Length</th>
        <th>Times Played</th>
        <th>Last Played</th>
    </tr>

    <!-- Loop over and print songs -->
    <c:forEach var="tempSong" items="${songs}">
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
        </tr>
    </c:forEach>
</table>

<hr>
<a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=ALL&sort=${pageContext.request.getParameter("sort")}">All
    songs</a><br>
<a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=GUITAR&sort=${pageContext.request.getParameter("sort")}">Guitar
    songs</a><br>
<a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=BASS&sort=${pageContext.request.getParameter("sort")}">Bass
    songs</a><br>
<a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=DRUMS&sort=${pageContext.request.getParameter("sort")}">Drums
    songs</a><br>
<a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=PIANO&sort=${pageContext.request.getParameter("sort")}">Piano
    songs</a><br>
<hr>

<a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
