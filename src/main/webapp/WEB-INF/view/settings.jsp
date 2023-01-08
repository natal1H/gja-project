<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <link rel="icon" href="/favicon.ico"/>
    <script src="/js/main.js"></script>
    <script>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<tag:header user="${user}"></tag:header>
<div class="wrapper">
    <h2>Settings</h2>

    <div class="rows">
        <div>
            <img src="${pageContext.request.contextPath}/profilePicture" style="max-width: 150px; max-height: 150px;" onerror="this.onerror=null;this.src='/img/img.png';">
            <c:if test="${not empty user.profilePicturePath}">
                <span>
                    <button onclick="removeProfilePicture()" class="red btn"><i
                            class="fa fa-trash"></i>Remove photo</button>
                </span>
            </c:if>
            <form id="pictureForm" action="${pageContext.request.contextPath}/profilePicture" method="post"
                  enctype="multipart/form-data">
                <label for="picture">Change profile picture:</label>
                <input id="picture" name="picture" type="file"
                       accept="${String.join(",",allowedProfilePictureFormats)}" required>
                <input type="submit" class="btn in">
            </form>

        </div>

        <div>
            <table>
                <tr>
                    <td>Username</td>
                    <td>${user.userName}</td>
                </tr>
                <tr>
                    <td>First name</td>
                    <td>${user.firstName}</td>
                </tr>
                <tr>
                    <td>Last name</td>
                    <td>${user.lastName}</td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <td>Roles</td>
                    <td><security:authentication property="principal.authorities"/></td>
                </tr>

            </table>
            <a href="${pageContext.request.contextPath}/settings/edit">
                <button class="btn"><i class="fa fa-pencil"></i>Edit this info</button>
            </a>
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/settings/changePassword">
                <button class="btn"><i class="fa fa-lock"></i>Change password</button>
            </a>
            <security:authorize access="!hasRole('LECTOR')">
                <form action="${pageContext.request.contextPath}/settings/becomeLector" method="post">
                    <input type="submit" name="becomeLector" class="btn" value="Become a lector"/>
                </form>
            </security:authorize>
            <security:authorize access="hasRole('LECTOR')">
                <form action="${pageContext.request.contextPath}/settings/stopBeingLector" method="post">
                    <input type="submit" name="stopBeingLector" class="btn" value="Stop being a lector"/>
                </form>
            </security:authorize>
        </div>
    </div>


</div>
</body>
</html>

<style>
    .rows {
        display: flex;
        justify-content: space-between;
    }

    .rows div {
        margin-right: 100px;
        display: flex;
        flex-direction: column;
    }

    .rows form {
        display: flex;
        flex-direction: column;
    }

    .rows img {
        max-width: 200px;
        max-height: 200px;
    }

    .btn {
        display: flex;
        justify-content: space-between;
        background-color: transparent;
        border-radius: 7px;
        border: 1px solid #13b992;
        padding: 7px 14px;
        cursor: pointer;
        color: #13b992;
        font-size: 15px;
        align-items: baseline;
        margin-top: 10px;
        max-width: 200px;
    }

    input {
        text-align: center
    }

    a {
        text-decoration: none;
        color: #13b992;
    }

    .red {
        color: rgba(255, 0, 7, 0.79);
        border: 1px solid rgba(255, 0, 7, 0.79);
    }

    button i {
        padding-right: 20px;
    }

    #pictureForm {
        margin-top: 20px;
    }

    form label {
        margin-bottom: 5px;
    }

    .in {
        width: 80px;
    }
</style>
