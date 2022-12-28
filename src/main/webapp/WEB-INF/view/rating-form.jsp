<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
  <title>Song Trainer Add Rating</title>
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
  <h2>New rating</h2>

  <form:form action="saveRating" modelAttribute="rating" method="POST">

    <form:hidden path="id" />

    <!-- Name -->
    <input type="hidden" name="songId" value="${song.id}">
    <div class="input-group">
      <form:label path="feelings">How did you feel about playing this song?</form:label>
      <form:input path="feelings" class="text-input"/>
    </div>
    <div class="input-group">
      <form:label path="accuracy">With what precision did you play this song?</form:label>
      <form:input path="accuracy" class="text-input"/>
    </div>
    <div class="input-group">
      <form:label path="numberMistakes">How many mistakes did you make?</form:label>
      <form:input path="numberMistakes" class="text-input"/>
    </div>
    <div class="input-group">
      <form:label path="comment">Note:</form:label>
      <form:input path="comment" class="text-input"/>
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
