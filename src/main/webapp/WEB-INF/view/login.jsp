<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Song Trainer - Login</title>
  <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
  <script src="/js/main.js" />"></script>
</head>
<body>

<div class="login-wrapper">
  <div class="left">
    <div>
      <h2>SongTrainer</h2>
      <p>Your personal musical instrument training instructor</p>
      <p>Join us in making music better every day!</p>
    </div>
    <div>
      <img src="/img/guitar.png">
    </div>

  </div>
  <div class="right">
    <h2>Login to SongTrainer</h2>

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
        <input type="text" class="input" name="username" placeholder="User name" required/>
      </p>
      <p>
        <input type="password" class="input" name="password" placeholder="Password" required/>
      </p>

      <input type="submit" class="btn" value="Login">
    </form:form>

    <p>Don't have an account yet? <a href="${pageContext.request.contextPath}/register/showRegistrationForm">Register here</a></p>
  </div>
</div>


</body>
</html>
