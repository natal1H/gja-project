<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song trainer Home Page</title>
</head>
<body>
    <h2>Song Trainer Home Page</h2>

    Welcome to Song Trainer home page!
    <hr>

    <p>
        User: <security:authentication property="principal.username" /><br>
        Role(s): <security:authentication property="principal.authorities" /><br>
        First name: ${user.firstName}, Last name: ${user.lastName}, Email: ${user.email}
    </p>

    <hr>

    <security:authorize access="hasRole('LECTOR')">
        <!-- Link to point to /lector ... only for lectors -->
        <p>
            <a href="${pageContext.request.contextPath}/lectors">Lector page</a> (Only for lectors)
        </p>
        <hr>
    </security:authorize>

    <a href="${pageContext.request.contextPath}/songs">All my songs</a>
    <hr>

    <!-- Logout button -->
    <form:form action="${pageContext.request.contextPath}/logout" method="POST">
        <input type="submit" value="Logout" />
    </form:form>
</body>
</html>
