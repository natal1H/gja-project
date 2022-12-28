<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Add Song</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/favicon.ico" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js" /></script>
</head>
<body>
    <tag:header></tag:header>
    <div class="wrapper">
        <h2>Edit song</h2>

        <form:form action="saveSong" modelAttribute="song" method="POST" enctype="multipart/form-data">
            <form:hidden path="id" />

            <!-- Title -->
            <div class="input-group">
                <form:label path="title">Title</form:label>
                <form:input path="title" class="text-input"/>
            </div>

            <!-- Artist -->
            <div class="input-group">
                <form:label path="artist">Artist</form:label>
                <form:input path="artist" class="text-input"/>
            </div>

            <div class="input-group">
                <label for="backing_track">Backing track</label>
                <input id="backing_track" type="file" name="backing_track" accept="${String.join(",",allowedAudioExtensions)}">
            </div>
            <!-- Instrument -->
            <div class="input-group">
                <form:label path="instrument">Instrument</form:label>
                <form:radiobuttons path="instrument" items="${instruments}"/>
            </div>

            <!-- Tuning -->
            <!-- TODO: disable base on selected instrument -->
            <div class="input-group">
                <form:label path="tuning">Tuning</form:label>
                <form:radiobuttons path="tuning" items="${tunings}"/>
            </div>

            <div class="input-group">
                Is visible for others: <form:checkbox path="visible" value="true"/>
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
