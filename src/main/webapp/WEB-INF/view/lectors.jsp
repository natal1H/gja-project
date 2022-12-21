<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Lector page</title>
    <link rel="icon" href="/favicon.ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js" /></script>
</head>
<body>
    <tag:header></tag:header>

    <div class="wrapper">
        <h2>Lectors Page</h2>

        This page is visible only to users with LECTOR role.<br>

        <form:form action="${pageContext.request.contextPath}/lectors/search"  method="post">
            Search Users By Name: <input type="text" name="keyword" />
            <input type ="submit" value="Search">
        </form:form>
        <c:forEach var="tempUser" items="${users}">
            <a href="${pageContext.request.contextPath}/profile?id=${tempUser.id}&inst=ALL&sort=ArtistASC">${tempUser.userName}</a><br>
        </c:forEach><br>

        <div class="students-title">
            <h3>My students</h3>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Username</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                    <th style="text-align: right;">Actions</th>
                </tr>
            </thead>
            <tbody>
            <!-- Loop over and print students -->
            <c:forEach var="tempStudent" items="${students}">
                <!-- construct a "delete" link with lector & student id -->
                <c:url var="removeStudentLink" value="/lectors/removeStudent">
                    <c:param name="studentId" value="${tempStudent.id}" />
                    <c:param name="lectorId" value="${user.id}" />
                </c:url>

                <!-- construct a "delete" link with lector & student id -->
                <c:url var="addSongToStudentLink" value="/lectors/addSongToStudent">
                    <c:param name="studentId" value="${tempStudent.id}" />
                    <c:param name="lectorId" value="${user.id}" />
                </c:url>

                <tr>
                    <td><a href="${pageContext.request.contextPath}/profile?id=${tempStudent.id}&inst=ALL&sort=ArtistASC">${tempStudent.userName}</a></td>
                    <td>${tempStudent.firstName}</td>
                    <td>${tempStudent.lastName}</td>
                    <td>
                        <a href="${addSongToStudentLink}" >
                            <button class="plus"><i class="fa fa-solid fa-plus"></i></button>
                        </a>
                        <td>
                            <a href="${removeStudentLink}" onclick="if (!(confirm('Are you sure you want to remove this student?'))) return false">
                                <button class="trash"><i class="fa fa-solid fa-trash"></i></button>
                            </a>
                        </td>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>

</body>
</html>

<style>

    .students-title {
        display: flex;
        justify-content: space-between;
        align-items: baseline;
    }

    .students-title a {
        padding: 12px 20px;
        background-color: #13b992;
        color: #1a1d28;
        border-radius: 12px;
        text-decoration: none;
        transition: linear 0.3s;
    }
    .students-title a:hover {
        box-shadow: 0px 0px 5px 5px rgba(19, 185, 146, 0.24);
    }

    table .icons .plus {
        transition: linear 0.3s;
    }
    table .icons .plus:hover {
        color: #4de1dc;
        border: 1px solid #4de1dc;
    }

</style>
