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

<c:set var="playlistId" value='<%=request.getParameter("playlistId")%>'/>
<c:set var="nextSongId" value='<%=request.getParameter("nextSongId")%>'/>

<div class="wrapper">
  <h2>New rating</h2>

  <form:form action="saveRating" modelAttribute="rating" method="POST">

    <form:hidden path="id" />

    <!-- Name -->
    <input type="hidden" name="songId" value="${song.id}">
    <div class="input-group">
      <form:label path="feelings">How did you feel about playing this song?</form:label>
      <form:input path="feelings" class="rating rating--nojs"
                  max="5"
                  step="1"
                  type="range"
                  style="--fill: #C8102E;--symbol:var(--heart);"
                  value="3"/>
    </div>
    <div class="input-group">
      <form:label path="accuracy">With what precision did you play this song?</form:label>
      <form:input path="accuracy" class="rating rating--nojs"
                  max="5"
                  step="1"
                  type="range"
                  value="3"/>
    </div>
    <div class="input-group">
      <form:label path="numberMistakes">How many mistakes did you make?</form:label>
      <form:input path="numberMistakes" class="text-input num" type="number" value="0"/>
    </div>
    <div class="input-group">
      <form:label path="comment">Note:</form:label>
      <form:textarea path="comment" class="text-input big" placeholder="Some notes about playing..."/>
    </div>

    <!-- Submit Button -->
    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle-o"></i> <span>SAVE</span></button>

    <input type="hidden" name="playlistId" value="${playlistId}">
    <input type="hidden" name="nextSongId" value="${nextSongId}">

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

  input {
    background-color: transparent;
  }

  .btn i {
    font-size: 20px;
  }
  .rating {
    --dir: right;
    --fill: gold;
    --fillbg: rgba(248, 246, 246, 0.32);
    --heart: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 21.328l-1.453-1.313q-2.484-2.25-3.609-3.328t-2.508-2.672-1.898-2.883-0.516-2.648q0-2.297 1.57-3.891t3.914-1.594q2.719 0 4.5 2.109 1.781-2.109 4.5-2.109 2.344 0 3.914 1.594t1.57 3.891q0 1.828-1.219 3.797t-2.648 3.422-4.664 4.359z"/></svg>');
    --star: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M12 17.25l-6.188 3.75 1.641-7.031-5.438-4.734 7.172-0.609 2.813-6.609 2.813 6.609 7.172 0.609-5.438 4.734 1.641 7.031z"/></svg>');
    --stars: 5;
    --starsize: 3rem;
    --symbol: var(--star);
    --value: 1;
    --w: calc(var(--stars) * var(--starsize));
    --x: calc(100% * (var(--value) / var(--stars)));
    block-size: var(--starsize);
    inline-size: var(--w);
    position: relative;
    touch-action: manipulation;
    -webkit-appearance: none;
  }
  [dir="rtl"] .rating {
    --dir: left;
  }
  .rating::-moz-range-track {
    background: linear-gradient(to var(--dir), var(--fill) 0 var(--x), var(--fillbg) 0 var(--x));
    block-size: 100%;
    mask: repeat left center/var(--starsize) var(--symbol);
  }
  .rating::-webkit-slider-runnable-track {
    background: linear-gradient(to var(--dir), var(--fill) 0 var(--x), var(--fillbg) 0 var(--x));
    block-size: 100%;
    mask: repeat left center/var(--starsize) var(--symbol);
    -webkit-mask: repeat left center/var(--starsize) var(--symbol);
  }
  .rating::-moz-range-thumb {
    height: var(--starsize);
    opacity: 0;
    width: var(--starsize);
  }
  .rating::-webkit-slider-thumb {
    height: var(--starsize);
    opacity: 0;
    width: var(--starsize);
    -webkit-appearance: none;
  }
  .rating, .rating-label {
    display: block;
    font-family: ui-sans-serif, system-ui, sans-serif;
  }
  .rating-label {
    margin-block-end: 1rem;
  }

  /* NO JS */
  .rating--nojs::-moz-range-track {
    background: var(--fillbg);
  }
  .rating--nojs::-moz-range-progress {
    background: var(--fill);
    block-size: 100%;
    mask: repeat left center/var(--starsize) var(--star);
  }
  .rating--nojs::-webkit-slider-runnable-track {
    background: var(--fillbg);
  }
  .rating--nojs::-webkit-slider-thumb {
    background-color: var(--fill);
    box-shadow: calc(0rem - var(--w)) 0 0 var(--w) var(--fill);
    opacity: 1;
    width: 1px;
  }
  [dir="rtl"] .rating--nojs::-webkit-slider-thumb {
    box-shadow: var(--w) 0 0 var(--w) var(--fill);
  }

  .num {
    width: 50px;
    text-align: center;
  }

  .big {
    width: 50%;
    height: 70px;
    padding: 10px;
    font-family: 'Inter', sans-serif;
  }



</style>
