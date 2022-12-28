<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Song Trainer Add Song</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/favicon.ico"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" href="/css/stylesheet.css" rel="stylesheet">
    <script src="/js/main.js"/>
    </script>

    <script src="https://unpkg.com/wavesurfer.js"></script>
    <script src="https://unpkg.com/wavesurfer.js/dist/plugin/wavesurfer.cursor.min.js" type="text/javascript"></script>
</head>
<body>
<tag:header user="${user}"></tag:header>


<div class="wrapper">
    <h1>${song.artist} - ${song.title}</h1>

    <div id="loading">Loading...</div>
    <div id="loaded" style="visibility: hidden">
        <div id="waveform"></div>
        <button id="playButton" onclick="wavesurfer.play()">Play</button>
    </div>

</div>

<c:url var="backingTrack" value="/songs/backingTrack">
    <c:param name="songId" value="${song.id}"/>
</c:url>

<c:url var="addTimesPlayed" value="/songs/addTimesPlayed">
    <c:param name="songId" value="${song.id}"/>
</c:url>

<c:url var="addRating" value="/rating/addRating">
    <c:param name="songId" value="${song.id}"/>
</c:url>
<script>
    let wavesurfer = WaveSurfer.create({
        container: document.querySelector('#waveform'),
        waveColor: '#D9DCFF',
        progressColor: '#4353FF',
        cursorColor: '#4353FF',
        height: 100,
        barWidth: 3,
        barRadius: 3,
        cursorWidth: 1,
        height: 200,
        barGap: 3,
        plugins: [WaveSurfer.cursor.create({
            showTime: true,
            opacity: 1,
            color: "#4353FF",
            customShowTimeStyle: {
                'background-color': '#000',
                color: '#fff',
                padding: '2px',
                'font-size': '10px'
            }
        })]
    });
    console.log("${backingTrack}");
    wavesurfer.load("${backingTrack}");
    wavesurfer.on("ready", () => {
        document.getElementById("loading").style.visibility = "hidden"
        document.getElementById("loaded").style.visibility = "visible"
    })

    wavesurfer.on("play", () => {
        let button = document.getElementById("playButton")
        button.onclick = () => wavesurfer.pause()
        button.textContent = "Pause";
    })

    wavesurfer.on("pause", () => {
        let button = document.getElementById("playButton")
        button.onclick = () => wavesurfer.play()
        button.textContent = "Play";
    })
    wavesurfer.on("finish", () => {
        console.log("Finished")
        fetch('${addTimesPlayed}', {
            method: 'POST',
        })
            .then(() => window.location.href = "${addRating}")
    })
</script>
</body>
</html>
