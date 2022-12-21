<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
    <link rel="icon" href="/favicon.ico" />
    <!-- TODO!!! post sends passwords as plaintext, fix -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js" />"></script>
</head>
<body>
    <tag:header></tag:header>
    <div class="wrapper">
        <h2>Change password</h2>

        <!-- TODO: front end check if new password matches confirm -->
        <form method="post" action="/settings/updatePassword">
            <div class="input-group">
                <label for="oldpass">Old Password: </label>
                <input id="oldpass" name="oldpassword" type="password" class="text-input"/>
            </div>

            <div class="input-group">
                <label for="pass">New Password: </label>
                <input id="pass" name="password" type="password" class="text-input"/>
            </div>

            <div class="input-group">
                <label for="passConfirm">Confirm new password: </label>
                <input id="passConfirm" name="passwordConfirm" type="password" class="text-input"/>
            </div>


            <button type="submit"  class="btn btn-primary"><i class="fa fa-check-circle-o"></i>Change Password</button>
        </form>

        <a href="${pageContext.request.contextPath}/settings"/><button class="btn red"><i class="fa fa-close"></i> Cancel</button></a>
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
        width: 200px;
        justify-content: space-between;
        cursor: pointer;
        align-items: baseline;
        font-size: 16px;
    }

    .btn i {
        font-size: 20px;
    }

    .red {
        width: 130px;
        background-color: transparent;
        border: 1px solid #ee1d24;
        color: #ee1d24;
        margin-top: 15px;
    }
    a {
        text-decoration: none;
    }
</style>