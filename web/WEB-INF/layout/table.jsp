<%@ include file="../layout/taglib.jsp" %>
<script src="<c:url value="/resources/js/clrbx.js"/>"></script>

<center>
    <table class="table table-bordered table-hover table-striped">
        <thead>
        <tr>
            <th>Picture</th>
            <th>Name</th>
            <th>Tags</th>
            <th>Source</th>
            <th>Date</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.userPics}" var="photo">
            <tr>
                <td>
                    <a class="img-thumbnail" href="${photo.location}">
                        <img src="${photo.thumbnailPath}" alt="${photo.name}" width="200"></a>
                </td>
                <td>
                    <p>${photo.name}</p>
                </td>
                <td>
                    <p>${photo.tags}</p>
                </td>
                <td>
                    <p>${photo.source}</p>
                </td>
                <td>
                    <p>${photo.publishedDate}</p>
                </td>
                <td style="text-align: center; vertical-align:middle">
                    <a href="<spring:url value="/edit/${photo.id}"/>"><img src="/resources/edit.png"></a>
                </td>
                <td style="text-align: center; vertical-align:middle">
                    <a href="<spring:url value="/delete/${photo.id}"/>"><img src="/resources/delete.png"></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</center>
