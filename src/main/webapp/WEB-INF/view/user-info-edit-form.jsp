<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer</title>
    <link rel="icon" href="/favicon.ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js" />"></script>
</head>
<body>
<tag:header user="${user}"></tag:header>
    <div class="wrapper">
        <h2>Edit user's info</h2>

        <form:form action="saveEditedInfo" modelAttribute="user" method="POST">
            <form:hidden path="id" />

            <!-- Username -->
            <div class="input-group">
                <form:label path="userName">Username</form:label>
                <form:input path="userName" class="text-input"/>
            </div>

            <!-- Firstname -->
            <div class="input-group">
                <form:label path="firstName">First name</form:label>
                <form:input path="firstName" class="text-input"/>
            </div>

            <!-- Lastname -->
            <div class="input-group">
                <form:label path="lastName">Last name</form:label>
                <form:input path="lastName" class="text-input"/>
            </div>

            <!-- Email -->
            <div class="input-group">
                <form:label path="email">E-mail</form:label>
                <form:input path="email" class="text-input"/>
            </div>
            <!-- Submit Button -->
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle-o"></i> SAVE</button>
        </form:form>

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
        width: 120px;
        justify-content: space-between;
        cursor: pointer;
        align-items: baseline;
        font-size: 16px;
    }

    .btn i {
        font-size: 20px;
    }
</style>
