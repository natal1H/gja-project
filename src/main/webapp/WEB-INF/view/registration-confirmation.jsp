<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer - Registration Confirmation</title>
    <link rel="icon" href="/favicon.ico" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
</head>
<body>
    <tag:header></tag:header>

    <div class="wrapper">
        <h2 style="text-align: center">User registered successfully!</h2>


        <a href="${pageContext.request.contextPath}/showLoginPage">Login now</a>
    </div>
</body>
</html>
