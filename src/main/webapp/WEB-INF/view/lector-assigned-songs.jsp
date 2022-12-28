<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Add Song For Student</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/favicon.ico"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js"/>
    </script>
</head>
<body>
<tag:header user="${user}"></tag:header>
<div class="wrapper">
    <h2>Assigned songs to: ${student.userName}</h2>

    <tag:songList songs="${songs}" showVisibleColumn="false" showPlayButton="true" editable="false" showRemoveFromPlaylist="false" showAddToPlaylist="false"/>

</div>

</body>
</html>
