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


        function follow(userId) {
            fetch('/profile/follow?userId=' + userId, {
                method: 'POST',
            }).then(_ => document.location.reload())
        }

        function unfollow(userId) {
            fetch('/profile/unfollow?userId=' + userId, {
                method: 'POST',
            }).then(_ => document.location.reload())
        }

        let followForm = document.getElementById("followForm");
        followForm.onsubmit = function (event) {
            event.preventDefault();
            console.log(followForm.serialize())
        }

        let unfollowForm = document.getElementById("unfollowForm");
        unfollowForm.onsubmit = function (event) {
            event.preventDefault();
            console.log(unfollowForm.serialize())
        }
        // }
    </script>
</head>
<body>
<tag:header user="${user}"></tag:header>
<div class="wrapper">
    <div class="rows">
        <div>
            <h2>${profileUser.userName}'s profile</h2>

            <div style="display: flex; align-items: center">
                <div>
                    <img src="${pageContext.request.contextPath}/profilePicture?userId=${profileUser.id}" style="max-width: 150px; max-height: 150px;" onerror="this.onerror=null;this.src='/img/img.png';">

                </div>
                <div>
                    <table>
                        <tr>
                            <td>First name</td>
                            <td>${profileUser.firstName}</td>
                        </tr>
                        <tr>
                            <td>Last name</td>
                            <td>${profileUser.lastName}</td>
                        </tr>
                    </table>
                </div>

            </div>




        </div>
        <div class="follow">
            <c:choose>
                <c:when test="${isCurrent}">
                    <a href="/settings"><button>My Profile Settings</button></a>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${isFollowed}">
                            <button onclick="unfollow(${profileUser.id})">Unfollow</button>
                        </c:when>
                        <c:otherwise>
                            <button onclick="follow(${profileUser.id})" class="btn">Follow</button>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
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
                    <button class="plus-btn"><i class="fa fa-solid fa-plus"></i> Add as student</button>
                </a>
            </security:authorize>
            <!-- TODO: do not allow user to add themselves -->
            <c:if test="${isLector}">
                <a href="${addLectorLink}">
                    <button class="plus-btn"><i class="fa fa-solid fa-plus"></i> Add as lector</button>
                </a>
            </c:if>
        </div>
    </div>





    <h3>${profileUser.userName}'s songs</h3>

    <tag:songList songs="${songs}" showVisibleColumn="false" showPlayButton="false" editable="false" showRemoveFromPlaylist="false" showAddToPlaylist="false"/>
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

    .rows {
        display: flex;
        justify-content: space-between;
    }

    .follow {
    text-align: right;
    }

    .follow button {
        padding: 12px 20px;
        background-color: #13b992;
        color: #1a1d28;
        border-radius: 12px;
        text-decoration: none;
        cursor: pointer;
    }

</style>