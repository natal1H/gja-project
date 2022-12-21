<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Add Song</title>
    <link rel="icon" href="/favicon.ico" />
</head>
<body>
    <h2>Save song</h2>

    <form:form action="saveSong" modelAttribute="song" method="POST">
        <form:hidden path="id" />

        <!-- Title -->
        <form:label path="title">Title</form:label>
        <form:input path="title"/><br>

        <!-- Artist -->
        <form:label path="artist">Artist</form:label>
        <form:input path="artist"/><br>

        <!-- Instrument -->
        <form:label path="instrument">Instrument</form:label>
        <form:radiobuttons path="instrument" items="${instruments}"/><br>

        <!-- Tuning -->
        <!-- TODO: disable base on selected instrument -->
        <form:label path="tuning">Tuning</form:label>
        <form:radiobuttons path="tuning" items="${tunings}"/><br>

        Is visible for friends: <form:checkbox path="visible" value="true"/><br>

        <!-- Submit Button -->
        <button type="submit" class="btn btn-primary">SAVE</button>

    </form:form>

    <hr>
    <a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
