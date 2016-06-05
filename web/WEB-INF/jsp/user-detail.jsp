<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp" %>
<h2>${user.name}</h2>
<c:set var="userPics" value="${user.photos}" scope="request"/>
<%@ include file="../layout/table.jsp" %>
