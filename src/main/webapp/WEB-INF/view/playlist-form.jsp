<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>Song Trainer Add playlist</title>
</head>
<body>
<h2>Save playlist</h2>

  <form:form action="savePlaylist" modelAttribute="playlist" method="POST">

    <form:hidden path="id" />
    <!-- Name -->
    <form:label path="name">Name</form:label>
    <form:input path="name"/><br>

    <!-- Instrument -->
    <form:label path="instrument">Instrument</form:label>
    <form:radiobuttons path="instrument" items="${instruments}"/><br>

    <!-- Submit Button -->
    <button type="submit" class="btn btn-primary">SAVE</button>

  </form:form>

<hr>
<a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>
