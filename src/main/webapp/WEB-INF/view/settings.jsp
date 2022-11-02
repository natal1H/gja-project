<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
</head>
<body>
    <h2>User settings</h2>

    Username: ${user.userName}<br>
    First name: ${user.firstName}<br>
    Last name: ${user.lastName}<br>
    Email: ${user.email}<br>
    <a href="${pageContext.request.contextPath}/settings/edit">EDIT INFO</a>
    <hr>
    Role(s): <security:authentication property="principal.authorities" /><br>
    <hr>
    <a href="${pageContext.request.contextPath}/settings/changePassword">Change password</a><br>
    <hr>
    <security:authorize access="!hasRole('LECTOR')">
        Become a lector<!-- TODO -->
    </security:authorize>
    <security:authorize access="hasRole('LECTOR')">
        Stop being a lector<!-- TODO -->
    </security:authorize>

    <hr>
    <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
