<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Register</title>
    <link rel="icon" href="/favicon.ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
</head>
<body>

<%--    <div class="wrapper">--%>
<%--        <h2>Register new user</h2>--%>

<%--        <!-- Registration Form -->--%>
<%--        <form:form action="${pageContext.request.contextPath}/register/processRegistrationForm" modelAttribute="crmUser">--%>

<%--            <!-- Place for messages: error, alert etc ... -->--%>
<%--            <!-- Check for registration error -->--%>
<%--            <c:if test="${registrationError != null}">--%>
<%--                        ${registrationError}--%>
<%--            </c:if>--%>


<%--            <!-- User name -->--%>
<%--            <form:errors path="userName" />--%>
<%--            <form:input path="userName" placeholder="username (*)" /><br>--%>

<%--            <!-- Password -->--%>
<%--            <form:errors path="password" />--%>
<%--            <form:password path="password" placeholder="password (*)" /><br>--%>

<%--            <!-- Confirm Password -->--%>
<%--            <form:errors path="matchingPassword" />--%>
<%--            <form:password path="matchingPassword" placeholder="confirm password (*)" /><br>--%>

<%--            <!-- First name -->--%>
<%--            <form:errors path="firstName" />--%>
<%--            <form:input path="firstName" placeholder="first name (*)" /><br>--%>

<%--            <!-- Last name -->--%>
<%--            <form:errors path="lastName" />--%>
<%--            <form:input path="lastName" placeholder="last name (*)" /><br>--%>

<%--            <!-- Email -->--%>
<%--            <form:errors path="email" />--%>
<%--            <form:input path="email" placeholder="email (*)" /><br>--%>

<%--            <!-- Register Button -->--%>
<%--            <button type="submit" class="btn btn-primary">Register</button>--%>

<%--        </form:form>--%>
<%--    </div>--%>
    <div class="login-wrapper register">
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
            <h2>Register to SongTrainer</h2>

            <!-- Registration Form -->
            <form:form action="${pageContext.request.contextPath}/register/processRegistrationForm" modelAttribute="crmUser">
                <!-- Place for messages: error, alert etc ... -->
                <!-- Check for registration error -->
                <c:if test="${registrationError != null}">
                    ${registrationError}
                </c:if>

                <p>
                    <!-- User name -->
                    <form:errors path="userName" />
                    <form:input path="userName" placeholder="User name"  class="input"/>
                </p>
                <p>
                    <form:errors path="password" />
                    <form:password path="password" placeholder="Password" class="input"/>
                </p>
                <p>
                    <form:errors path="matchingPassword" />
                    <form:password path="matchingPassword" placeholder="Confirm password" class="input"/>
                </p>
                <p>
                    <form:errors path="firstName" />
                    <form:input path="firstName" placeholder="First name" class="input"/>
                </p>
                <p>
                    <form:errors path="lastName" />
                    <form:input path="lastName" placeholder="Last Name" class="input"/>
                </p>
                <p>
                    <form:errors path="email" />
                    <form:input path="email" placeholder="Email" class="input" />
                </p>

                <input type="submit" class="btn" value="Register">
            </form:form>

            <p>Already have an account? <a href="${pageContext.request.contextPath}/showLoginPage">Login here</a></p>
        </div>
    </div>

</body>
</html>

<style>
    .register {
        margin-top: 50px;
    }
</style>
