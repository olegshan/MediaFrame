<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="<c:url value="/resources/js/tab.js"/>"></script>

<style type="text/css">.thumb-image {
    margin-top: 10px;
    width: 150px;
}
</style>


<c:if test="${success eq false}">
    <div class="alert alert-danger">Upload failured!</div>
</c:if>
<c:if test="${photolist eq 'blank'}">
    <div class="alert alert-danger">Please select pictures!</div>
</c:if>
<c:if test="${maxsize eq 'exceeded'}">
    <div class="alert alert-danger">Some files are too large. Max size is 8 MB.</div>
</c:if>

<c:if test="${filetype eq 'wrong'}">
    <div class="alert alert-danger">Only .jpg, .jpeg, .png and .gif formats are supported.</div>
</c:if>


<center>
    <div id="wrapper" style="margin-top: 20px;">
        <form method="POST" action="upload" enctype="multipart/form-data">
            <input id="fileUpload" name="file" multiple="multiple" type="file"/>
            <br/>
            <input type="submit" value="Upload" class="btn btn-primary btn-lg">
            <br/>
            <div id="image-holder"/>
        </form>
    </div>
</center>