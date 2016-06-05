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
        <c:set var="userPics" value="${user.photos}" scope="request"/>
        <%@ include file="../layout/table.jsp" %>

    </center>
</security:authorize>

<security:authorize access="!isAuthenticated()">
    // Here will be some usefull information for not authorized users about this app. By now here is a cat
    <center>
        <img src="/resources/index.png">
    </center>
</security:authorize>