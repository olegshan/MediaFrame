<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ include file="../layout/taglib.jsp" %>
<script src="<c:url value="/resources/js/clrbx.js"/>"></script>

<security:authorize access="isAuthenticated()">
    <center>

        <a href="<spring:url value="/picture-upload"/>" class="btn btn-default btn-lg">Upload pictures</a>
        <br/>
        <br/>
        <%@ include file="../layout/searchline.jsp" %>
        <br/>
    </center>
    <c:set var="userPics" value="${user.photos}" scope="request"/>
    <%@ include file="../layout/table.jsp" %>


</security:authorize>

<security:authorize access="!isAuthenticated()">
    <%--Here will be some usefull information for not authorized users about this app. By now here is a cat--%>
    <center>
        <br/>
        <h1>
            <small>Mediaframe is a simple and powerful photobank.</small>
        </h1>
        <h2>
            <small><a href="<spring:url value="/register" />">Register</a> or
                <a href="<spring:url value="/login" />">login</a> right now and you will be able to store all your
                pictures for free.
            </small>
        </h2>
        <h3>
            <small>The cat below is nice, huh?</small>
        </h3>

        <img src="/resources/cat.jpg">
    </center>
</security:authorize>