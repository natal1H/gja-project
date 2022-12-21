<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <link rel="icon" href="/favicon.ico" />
    <script src="/js/main.js" /></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <script>
        function submitSortForm() {
            document.getElementById("sortForm").submit();
        }
    </script>
</head>
<body>
<tag:header></tag:header>
<div class="wrapper">
    <h2>${profileUser.userName}'s profile</h2>

    First name: ${profileUser.firstName} <br>
    Last name: ${profileUser.lastName}<br>

    <!-- construct a "addStudent" link with lector & student id -->
    <c:url var="addStudentLink" value="/addStudent">
        <c:param name="studentId" value="${profileUser.id}" />
        <c:param name="lectorId" value="${user.id}" />
    </c:url>

    <!-- construct a "addLector" link with lector & student id -->
    <c:url var="addLectorLink" value="/addLector">
        <c:param name="studentId" value="${user.id}" />
        <c:param name="lectorId" value="${profileUser.id}" />
    </c:url>

    <!-- TODO: do not allow user to add themselves -->
    <security:authorize access="hasRole('LECTOR')">
        <a href="${addStudentLink}">
            <button class="plus-btn"><i class="fa fa-solid fa-plus"></i>Add as student</button>
        </a>
    </security:authorize>
    <!-- TODO: do not allow user to add themselves -->
    <c:if test="${isLector}">
        <a href="${addLectorLink}">
            <button class="plus-btn"><i class="fa fa-solid fa-plus"></i>Add as lector</button>
        </a>
    </c:if>


    <div>
        <img src="${pageContext.request.contextPath}/profilePicture">
    </div>


    Songs ${profileUser.userName} can play:<br>


    <table>
        <thead>
            <th>
                <span>
                    Title
                    <span class="carrets">
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=TitleDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=TitleASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                    </span>
                </span>
            </th>
            <th>
                <span>
                    Artist
                    <span class="carrets">
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=ArtistDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=ArtistASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                    </span>
                </span>
            </th>
            <th>
                <span>
                    Instrument
                    <div class="dropdown">
                          <button onclick="filterToggle()" class="dropbtn"><span class="dropbtn"><i class="fa fa-solid fa-filter dropbtn"></i></span></button>
                          <div id="filter" class="dropdown-content softer">
                              <a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=ALL&sort=${pageContext.request.getParameter("sort")}">All songs</a>
                              <a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=GUITAR&sort=${pageContext.request.getParameter("sort")}">Guitar songs</a>
                              <a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=BASS&sort=${pageContext.request.getParameter("sort")}">Bass songs</a>
                              <a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=DRUMS&sort=${pageContext.request.getParameter("sort")}">Drums songs</a>
                              <a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=PIANO&sort=${pageContext.request.getParameter("sort")}">Piano songs</a>
                          </div>
                    </div>

                </span>
            </th>
            <th>
                <span>
                    Tuning
                </span>
            </th>
            <th>
                <span>
                    Length
                    <span class="carrets">
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=LengthDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=LengthASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                    </span>
                </span>
            </th>
            <th>
                <span>
                    Times Played
                    <span class="carrets">
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=TimesPlayedDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=TimesPlayedASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                    </span>
                </span>
            </th>
            <th>
                <span>
                    Last Played
                    <span class="carrets">
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=LastPlayedDESC"><i class="fa fa-sharp fa-solid fa-caret-up"></i></a>
                        <a href="${pageContext.request.contextPath}/profile?id=${pageContext.request.getParameter("id")}&inst=${pageContext.request.getParameter("inst")}&sort=LastPlayedASC"><i class="fa fa-sharp fa-solid fa-caret-down"></i></a>
                    </span>
                </span>
            </th>
        </thead>

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

    <div>
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
        background-color:transparent !important;
    }
    th span .dropbtn:hover {
        background-color:transparent !important;
    }

</style>