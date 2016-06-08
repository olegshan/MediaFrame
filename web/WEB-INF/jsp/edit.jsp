<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../layout/taglib.jsp" %>
<script src="<c:url value="/resources/js/clrbx.js"/>"></script>

<c:if test="${edited eq 'success'}">
    <div class="alert alert-success">Photo updated successfully</div>
</c:if>

<center>
    <form method="POST" action="update/${photoEdit.id}">
        <table class="table table-bordered table-hover table-striped">
            <tr>
                <td rowspan="3" style="text-align: center; vertical-align:middle; width:300px">
                    <a class="img-thumbnail" href="${photoEdit.location}">
                        <img src="${photoEdit.thumbnailPath}" width="200"></a>
                </td>
                <td width="50">Name:</td>
                <td>
                    <input type="text" class="form-control" value="${photoEdit.name}" name="name">
                </td>
            </tr>
            <tr>
                <td>Tags:</td>
                <td>
                    <input type="text" class="form-control" value="${photoEdit.tags}" name="tags">
                </td>
            </tr>
            <tr>
                <td>Source:</td>
                <td>
                    <input type="text" class="form-control" value="${photoEdit.source}" name="source">
                </td>
            </tr>
        </table>
        <input type="submit" value="Save" class="btn btn-primary">
        <a href="<spring:url value="delete/${photoEdit.id}"/>">
            <button type="button" class="btn btn-danger">Delete</button>
        </a>
        <a href="<spring:url value="cancel"/>">
            <button type="button" class="btn btn-default">Cancel</button>
        </a>
    </form>
</center>