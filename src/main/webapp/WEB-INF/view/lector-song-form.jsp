<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Add Song For Student</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/favicon.ico" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js" /></script>
</head>
<body>
    <tag:header user="${user}"></tag:header>
    <div class="wrapper">
        <h2>Add song to student</h2>

        Fill out song details for student ${student.userName}:<br>

        <form:form action="saveStudentSong" modelAttribute="studentSongForm" method="POST" enctype="multipart/form-data">
            <!-- Student info -->
            <form:hidden path="student.id" value="${student.id}" />
            <form:hidden path="student.userName" value="${student.userName}" />

            <form:hidden path="song.id" />
            <form:hidden path="song.visible" value="true" />

            <!-- Title -->
            <div class="input-group">
                <form:label path="song.title">Title</form:label>
                <form:input path="song.title" class="text-input" required="required"/>
            </div>

            <!-- Artist -->
            <div class="input-group">
                <form:label path="song.artist">Artist</form:label>
                <form:input path="song.artist" class="text-input" required="required"/>
            </div>

            <div class="input-group">
                <label for="backing_track">Backing track</label>
                <input id="backing_track" type="file" name="backing_track" required>
            </div>

            <!-- Instrument -->
            <div class="input-group">
                <form:label path="song.instrument">Instrument</form:label>
                <form:radiobuttons path="song.instrument" items="${instruments}"/>
            </div>

            <!-- Tuning -->
            <div class="input-group">
                <form:label path="song.tuning">Tuning</form:label>
                <form:radiobuttons path="song.tuning" items="${tunings}"/>
            </div>

            <!-- Submit Button -->
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle-o"></i> <span>SAVE</span></button>

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
