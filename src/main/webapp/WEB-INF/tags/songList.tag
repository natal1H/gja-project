<%@ tag import="fit.gja.songtrainer.util.InstrumentEnum" %>
<%@ tag import="fit.gja.songtrainer.entity.Song" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="songs" type="java.util.List<fit.gja.songtrainer.entity.Song>" required="true" %>
<%@attribute name="showVisibleColumn" type="java.lang.Boolean" required="true" %>
<%@attribute name="showPlayButton" type="java.lang.Boolean" required="true" %>
<%@attribute name="showRemoveFromPlaylist" type="java.lang.Boolean" required="true" %>
<%@attribute name="showAddToPlaylist" type="java.lang.Boolean" required="true" %>

<%@attribute name="editable" type="java.lang.Boolean" required="true" %>


<c:set var="selectedInstrument" value='<%=request.getParameter("inst")%>'/>
<c:set var="instruments" value="<%=InstrumentEnum.values()%>"/>

<jsp:doBody var="body"/>

<script>
    function changeInstrumentFilter() {
        let selected = document.getElementById("instruments").value
        let url = new URL(document.location);
        let params = url.searchParams;
        params.set("inst", selected);
        window.location.replace(url.toString())
    }
</script>

<label for="instruments">Instrument:</label>
<select id="instruments" name="instruments" onchange="changeInstrumentFilter()">
    <option value="ALL">ALL</option>
    <c:forEach var="instrument" items="${instruments}">
        ${(selectedInstrument == instrument.toString())? "selected" : "no"}
        <option value="${instrument}" ${(selectedInstrument == instrument.toString())? "selected" : ""}>${instrument}</option>
    </c:forEach>
</select>
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
<table class="sortable">
    <thead>
    <th>Title</th>
    <th>Artist</th>
    <th>Instrument</th>
    <th>Tuning</th>
    <th>Length</th>
    <th>Times Played</th>
    <th>
        <span>Last Played</span>
    </th>
    <c:if test="${showVisibleColumn}">
        <th>Visible</th>
    </c:if>
    <c:if test="${showPlayButton}">
        <th class="sorttable_nosort">Play</th>
    </c:if>

    <c:if test="${editable}">
        <th style="text-align: right" class="sorttable_nosort">Actions</th>
    </c:if>
    </thead>

    <!-- Loop over and print songs -->
    <!--<c:if test="${songs != null}">-->
    <c:forEach var="tempSong"
               items="${
                   selectedInstrument != null && selectedInstrument != \"ALL\"?
                       songs.stream().filter((song) -> song.getInstrument().toString() == selectedInstrument).toList() :
                       songs
               }">


        <tr>
            <td>${tempSong.title}</td>
            <td>${tempSong.artist}</td>
            <td>${tempSong.instrumentStr}</td>
            <td>
                <c:choose>
                    <c:when test="${tempSong.tuning!=null}">
                        ${tempSong.tuningStr}
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>

            </td>
            <td>${tempSong.lengthStr}</td>
            <td>${tempSong.times_played}</td>
            <td><c:choose>
                <c:when test="${tempSong.last_played!=null}">
                    ${tempSong.last_played}
                </c:when>
                <c:otherwise>
                    Never
                </c:otherwise>
            </c:choose></td>
            <c:if test="${showVisibleColumn}">
                <td sorttable_customkey="${tempSong.visible ? 0 : 1}">
                    <c:choose>
                        <c:when test="${tempSong.visible}">
                            <i class="fa fa-solid fa-check" style="color: green"></i>
                        </c:when>
                        <c:otherwise>
                            <i class="fa fa-solid fa-xmark"></i>
                        </c:otherwise>
                    </c:choose>
                </td>
            </c:if>
            <c:if test="${showPlayButton}">
                <td>
                    <a href="/song?songId=${tempSong.id}">
                        <button class="play-btn"><i class="fa fa-solid fa-play"></i>Play</button>
                    </a>
                </td>
            </c:if>
            <c:if test="${editable}">
                <!-- construct an "update" link with song id -->
                <c:url var="updateLink" value="/songs/showUpdateForm">
                    <c:param name="songId" value="${tempSong.id}"/>
                </c:url>

                <!-- construct a "delete" link with song id -->
                <c:url var="deleteLink" value="/songs/deleteSong">
                    <c:param name="songId" value="${tempSong.id}"/>
                    <c:param name="playlistId" value="${playlist.id}"/>
                </c:url>


                <!-- construct a "removeFromPlaylist" link with song id -->
                <c:url var="removeFromPlaylistLink" value="/playlist/removeSongFromPlaylist">
                    <c:param name="songId" value="${tempSong.id}"/>
                    <c:param name="playlistId" value="${playlist.id}"/>
                </c:url>

                <!-- construct a "addToPlaylist" link with song id -->
                <c:url var="addToPlaylistLink" value="/songs/showAddToPlaylistForm">
                    <c:param name="songId" value="${tempSong.id}"/>
                </c:url>

                <!-- construct an "showRatings" link with song id -->
                <c:url var="showRatingsLink" value="/rating/showAll">
                    <c:param name="songId" value="${tempSong.id}"/>
                </c:url>

                <td class="icons">

                    <a href="${updateLink}" class="pencil"><i class="fa fa-solid fa-pencil"></i></a>
                    <a href="${deleteLink}" class="trash"
                       onclick="if (!(confirm('Are you sure you want to delete this song?'))) return false"><i
                            class="fa fa-solid fa-trash"></i></a>
                    <a href="${showRatingsLink}" class="chart"><i class="fa fa-solid fa-bar-chart"></i></a>
                    <c:if test="${showRemoveFromPlaylist}">
                        <a href="${removeFromPlaylistLink}" class="discard"><i class="fa fa-solid fa-close"></i></a>
                    </c:if>

                    <c:if test="${showAddToPlaylist}">
                        <a href="${addToPlaylistLink}" class="plus">
                            <i class="fa fa-solid fa-plus"></i>
                        </a>
                    </c:if>


                </td>
            </c:if>
        </tr>
    </c:forEach>
    <!--</c:if>-->


</table>
<style>
    .play-btn {
        color: #13b992;
        border: 1px solid #13b992;
        width: 70px;
        display: flex;
        justify-content: space-between;
    }

    .icons a {
        font-size: 14px;
        padding: 7px 15px;
        border: 1px solid #b0b0b0;
        border-radius: 7px;
        background-color: transparent;
        color: #b0b0b0;
        cursor: pointer;
        margin-right: 5px;
    }

    table .icons .plus {
        transition: linear 0.3s;
    }

    table .icons .plus:hover {
        color: #4de1dc;
        border: 1px solid #4de1dc;
    }

    table button {
        padding: 7px 10px !important;
    }

    .icons .pencil, .icons .trash, .icons .discard,.icons .chart{
        transition: linear 0.3s;
    }

    .icons .pencil:hover,.icons .chart:hover{
        color: #4de14d;
        border: 1px solid #4de14d;
    }

    .icons .trash:hover {
        color: #ee1d24;
        border: 1px solid #ee1d24;
    }

    .icons .discard:hover {
        color: #ee7b1d;
        border: 1px solid #ee7b1d;
    }

    label {
        padding-right: 15px;
    }

    select {
        color: white;
        background-color: transparent;
        border-radius: 7px;
        padding: 5px;
    }
    select option {
        color: white;
        background-color: #1F2230;
        text-indent: 5px;
    }

</style>