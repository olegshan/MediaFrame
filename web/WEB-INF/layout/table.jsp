<%@ include file="../layout/taglib.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="<c:url value="/resources/js/clrbx.js"/>"></script>
<script src="<c:url value="/resources/js/pagination.js"/>"></script>
<%@ include file="../layout/remove-modal.jsp" %>

<center>
    <table class="table table-bordered table-hover table-striped">
        <c:choose>
            <c:when test="${fn:length(requestScope.userPics) gt 0}">
                <thead>
                <tr>
                    <th>Picture</th>
                    <th>Description</th>
                    <th>Tags</th>
                    <th>Source/Author</th>
                    <th>Date</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
            </c:when>
            <c:otherwise>
                <h1>
                    <small>You don't have any pictures yet.</small>
                </h1>
            </c:otherwise>
        </c:choose>
        <tbody id="tableBody">
        <c:forEach items="${requestScope.userPics}" var="photo">
            <tr>
                <td>
                    <a class="img-thumbnail" href="${photo.location}">
                        <img src="${photo.thumbnailPath}" alt="${photo.name}" width="200"></a>
                </td>
                <td>
                    <p><c:out value="${photo.name}"/></p>
                </td>
                <td>
                    <p><c:out value="${photo.tags}"/></p>
                </td>
                <td>
                    <p><c:out value="${photo.source}"/></p>
                </td>
                <td>
                    <p><c:out value="${photo.publishedDate}"/></p>
                </td>
                <td style="text-align: center; vertical-align:middle">
                    <a href="<spring:url value="/edit/${photo.id}"/>"><img src="/resources/edit.png"></a>
                </td>
                <td style="text-align: center; vertical-align:middle">
                    <a href="<spring:url value="/delete/${photo.id}"/>" class="triggerRemove"><img
                            src="/resources/delete.png"></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${fn:length(requestScope.userPics) gt 10}">
        <div class="col-md-12 text-center">
            <ul class="pagination pagination-lg pager" id="myPager"></ul>
        </div>
    </c:if>
</center>