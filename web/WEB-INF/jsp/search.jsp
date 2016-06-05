<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp" %>
<script src="<c:url value="/resources/js/clrbx.js"/>"></script>

<center>

    <a href="<spring:url value="/picture-upload"/>" class="btn btn-default btn-lg">Upload pictures</a>
    <br/>
    <br/>
    <%@ include file="../layout/searchline.jsp" %>
    <br/>
    <c:set var="userPics" value="${searchResult}" scope="request"/>
    <%@ include file="../layout/table.jsp" %>

</center>
