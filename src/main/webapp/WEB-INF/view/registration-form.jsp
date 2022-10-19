<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Register</title>
</head>
<body>
    <h2>Register new user</h2>

    <!-- Registration Form -->
    <form:form action="${pageContext.request.contextPath}/register/processRegistrationForm" modelAttribute="crmUser">

        <!-- Place for messages: error, alert etc ... -->
        <!-- Check for registration error -->
        <c:if test="${registrationError != null}">
                    ${registrationError}
        </c:if>


        <!-- User name -->
        <form:errors path="userName" />
        <form:input path="userName" placeholder="username (*)" /><br>

        <!-- Password -->
        <form:errors path="password" />
        <form:password path="password" placeholder="password (*)" /><br>

        <!-- Confirm Password -->
        <form:errors path="matchingPassword" />
        <form:password path="matchingPassword" placeholder="confirm password (*)" /><br>

        <!-- First name -->
        <form:errors path="firstName" />
        <form:input path="firstName" placeholder="first name (*)" /><br>

        <!-- Last name -->
        <form:errors path="lastName" />
        <form:input path="lastName" placeholder="last name (*)" /><br>

        <!-- Email -->
        <form:errors path="email" />
        <form:input path="email" placeholder="email (*)" /><br>

        <!-- Register Button -->
        <button type="submit" class="btn btn-primary">Register</button>

    </form:form>
</body>
</html>
