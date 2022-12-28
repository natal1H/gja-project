<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Song Trainer Add playlist</title>
  <link rel="icon" href="/favicon.ico" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="icon" href="/favicon.ico" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
  <script src="/js/main.js" /></script>
</head>
<body>
<tag:header user="${user}"></tag:header>

<div class="wrapper">
  <h2>New playlist</h2>

    <form:form action="savePlaylist" modelAttribute="playlist" method="POST">

      <form:hidden path="id" />
      <!-- Name -->
      <div class="input-group">
        <form:label path="name">Name</form:label>
        <form:input path="name" class="text-input"/>
      </div>


      <!-- Instrument -->
      <div class="input-group">
        <form:label path="instrument">Instrument</form:label>
        <form:radiobuttons path="instrument" items="${instruments}" class="options"/>
      </div>

      <form:checkbox path="public"></form:checkbox> Public

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
