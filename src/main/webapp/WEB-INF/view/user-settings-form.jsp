<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<html>
<head>
    <title>Song Trainer</title>
</head>
<body>
    <h2>Edit user's info</h2>

    <form:form action="saveEditedInfo" modelAttribute="user" method="POST">
        <form:hidden path="id" />

        <!-- Username -->
        <form:label path="userName">Username</form:label>
        <form:input path="userName"/><br>

        <!-- Firstname -->
        <form:label path="firstName">First name</form:label>
        <form:input path="firstName"/><br>

        <!-- Lastname -->
        <form:label path="lastName">Last name</form:label>
        <form:input path="lastName"/><br>

        <!-- Email -->
        <form:label path="email">E-mail</form:label>
        <form:input path="email"/><br>
        <!-- Submit Button -->
        <button type="submit" class="btn btn-primary">SAVE</button>
    </form:form>

    <hr>
    <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
