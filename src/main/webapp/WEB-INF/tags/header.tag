<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>

<%@attribute name="user" type="fit.gja.songtrainer.entity.User" required="true"%>
<div class="header">
    <div class="header-left">
        <span><a href="${pageContext.request.contextPath}/">SongTrainer</a></span>
        <a href="${pageContext.request.contextPath}/songs?inst=ALL">My Songs</a>
        <a href="${pageContext.request.contextPath}/playlists">My Playlists</a>
        <a href="${pageContext.request.contextPath}/students">Student page</a>
        <security:authorize access="hasRole('LECTOR')">
            <!-- Link to point to /lector ... only for lectors -->
            <a href="${pageContext.request.contextPath}/lectors">Lector page</a>
        </security:authorize>
    </div>
    <div class="header-btns">
        <div class="dropdown">
            <button onclick="headerToggle()" class="dropbtn">${user.userName}</button>
            <div id="myDropdown" class="dropdown-content">
                <a href="${pageContext.request.contextPath}/profile?id=${user.id}&inst=ALL"><span>My profile</span></a>
                <a href="${pageContext.request.contextPath}/settings"><span>Settings</span></a>
                <a>
                    <form:form action="${pageContext.request.contextPath}/logout" method="POST">
                        <input type="submit" value="Logout" />
                    </form:form>
                </a>
            </div>
        </div>
    </div>
</div>