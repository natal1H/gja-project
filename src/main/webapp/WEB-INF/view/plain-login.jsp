<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Login</title>
</head>
<body>

  <h2>Login with Username and Password</h2>

  <form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="POST">
    <!-- Check for login error -->
    <c:if test="${param.error != null}">
      <i>You entered invalid username/password.</i>
    </c:if>

    <!-- Check if user logged out -->
    <c:if test="${param.logout != null}">
      <i>You have logged out.</i>
    </c:if>

    <p>
      User name: <input type="text" name="username" />
    </p>
    <p>
      Password: <input type="password" name="password" />
    </p>

    <input type="submit" value="Login">
  </form:form>

</body>
</html>
