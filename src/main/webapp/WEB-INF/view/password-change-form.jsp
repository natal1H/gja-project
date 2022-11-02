<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
    <!-- TODO!!! post sends passwords as plaintext, fix -->
</head>
<a>
    <h2>Change password</h2>

    <!-- TODO: front end check if new password matches confirm -->
    <form method="post" action="/settings/updatePassword">
        <label for="oldpass">Old Password: </label><input id="oldpass" name="oldpassword" type="password" /><br>
        <label for="pass">New Password: </label><input id="pass" name="password" type="password" /><br>
        <label for="passConfirm">Confirm new password: </label><input id="passConfirm" name="passwordConfirm" type="password" /><br>

        <button type="submit">Change Password</button>
    </form>

    <hr>
    <a href="${pageContext.request.contextPath}/settings"/>BACK TO SETTINGS</a>

</body>
</html>
