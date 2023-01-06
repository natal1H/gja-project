<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
  <title>Song trainer Home Page</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
  <link rel="icon" href="/favicon.ico" />
  <script src="/js/main.js" />"></script>
</head>
<body>
<tag:header user="${user}"></tag:header>

<div class="wrapper">
  <div class="playlists-title">
    <h3>Ratings of ${song.title}</h3>
  </div>

  <table>
    <thead>
    <tr>
      <th>Feelings</th>
      <th>Accuracy</th>
      <th>Number of Mistakes</th>
      <th>Comment</th>
    </tr>
    </thead>
    <tbody>

    <!-- Loop over and print playlists -->
    <c:forEach var="tempRatings" items="${ratings}">

      <tr>
        <td>
          <input class="rating rating--nojs"
                        max="5"
                        step="1"
                        type="range"
                        style="--fill: #C8102E;--symbol:var(--heart);"
                        value="${tempRatings.feelings}"
                        readonly="true"
                        disabled
          />
        </td>
        <td>
          <input class="rating rating--nojs"
                        max="5"
                        step="1"
                        type="range"
                        value="${tempRatings.accuracy}"
                        readonly="true"
                        disabled
          />
        </td>
        <td>${tempRatings.numberMistakes}</td>
        <td>${tempRatings.comment}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>


</div>
</body>
</html>

<style>


  .playlists-title {
    display: flex;
    justify-content: space-between;
    align-items: baseline;
  }

  .playlists-title a {
    padding: 12px 20px;
    background-color: #13b992;
    color: #1a1d28;
    border-radius: 12px;
    text-decoration: none;
    transition: linear 0.3s;
  }
  .playlists-title a:hover {
    box-shadow: 0px 0px 5px 5px rgba(19, 185, 146, 0.24);
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
