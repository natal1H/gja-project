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
    Next song ${nextSong}
    <h1>${song.artist} - ${song.title}</h1>

    <div id="loading">
        <div class="center">
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
            <div class="wave"></div>
        </div>
    </div>
    <div id="loaded" style="visibility: hidden">
        <div id="waveform"></div>
        <div class="controls">
            <button onclick="wavesurfer.skip(-10)" class="circle"><i class="fa fa-solid fa-backward"></i></button>
            <button id="playButton" onclick="wavesurfer.play()" class="circle offset"><i class="fa fa-solid fa-play"></i></button>
            <button onclick="wavesurfer.skip(10)" class="circle offset"><i class="fa fa-solid fa-forward"></i></button>
            <button id="muteButton" onclick="wavesurfer.toggleMute()" class="circle offset"><i class="fa fa-solid fa-volume-up"></i></button>
        </div>


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
    <c:param name="nextSongId" value="${nextSongId}"/>
    <c:param name="playlistId" value="${playlistId}"/>
</c:url>

<script>
    let wavesurfer = WaveSurfer.create({
        container: document.querySelector('#waveform'),
        waveColor: '#D9DCFF',
        progressColor: '#13b992',
        cursorColor: '#13b992',
        height: 100,
        barWidth: 3,
        barRadius: 3,
        cursorWidth: 1,
        height: 200,
        barGap: 3,
        plugins: [WaveSurfer.cursor.create({
            showTime: true,
            opacity: 1,
            color: "#13b992",
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
        button.innerHTML = '<i class="fa fa-solid fa-pause"></i>';
    })

    wavesurfer.on("pause", () => {
        let button = document.getElementById("playButton")
        button.onclick = () => wavesurfer.play()
        button.innerHTML = '<i class="fa fa-solid fa-play"></i>';
    })
    wavesurfer.on("mute", () => {
        let button = document.getElementById("muteButton");
        if (button.innerHTML == '<i class="fa fa-solid fa-volume-up off"></i>') {
            button.innerHTML = '<i class="fa fa-solid fa-volume-up"></i>';
            return;
        }
        button.innerHTML = '<i class="fa fa-solid fa-volume-up off"></i>';
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

<style>
    .wrapper {
        position: relative;
        height: 350px;
    }
    .circle {
        box-sizing: border-box;
        display:block;
        width:50px;
        height:50px;
        padding-top: 3px;
        line-height: 20px;
        border: 3px solid #13b992;
        border-radius: 50%;
        color: #13b992;
        text-align:center;
        text-decoration:none;
        background-color: transparent;
        font-size:20px;
        font-weight:bold;
        transition: all 0.3s ease;
        margin: 5px;
    }
    .offset {
        padding-left: 7px;
    }

    .controls {
        display: flex;
        justify-content: center;
    }

    .off {
        color: rgba(19, 185, 146, 0.3);
    }

    #loading {
        position: absolute;
        top: -180px;
        left: 0;
        right: 0;
        margin-left: auto;
        margin-right: auto;
        width: 400px;
    }
    .center {
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background: rgba(0, 0, 0, 0);
    }
    .wave {
        width: 5px;
        height: 100px;
        background: #13b992;
        margin: 10px;
        animation: wave 1s linear infinite;
        border-radius: 20px;
    }
    .wave:nth-child(2) {
        animation-delay: 0.1s;
    }
    .wave:nth-child(3) {
        animation-delay: 0.2s;
    }
    .wave:nth-child(4) {
        animation-delay: 0.3s;
    }
    .wave:nth-child(5) {
        animation-delay: 0.4s;
    }
    .wave:nth-child(6) {
        animation-delay: 0.5s;
    }
    .wave:nth-child(7) {
        animation-delay: 0.6s;
    }
    .wave:nth-child(8) {
        animation-delay: 0.7s;
    }
    .wave:nth-child(9) {
        animation-delay: 0.8s;
    }
    .wave:nth-child(10) {
        animation-delay: 0.9s;
    }

    @keyframes wave {
        0% {
            transform: scale(0);
        }
        50% {
            transform: scale(1);
        }
        100% {
            transform: scale(0);
        }
    }


</style>
