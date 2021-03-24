<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp"%>

<body>

<jsp:include page="navigation.jsp">
    <jsp:param name="title" value="Category"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/category" var="categorySubmitUrl"/>
            <form action="${categorySubmitUrl}" method="post">
                <input value="${requestScope.category.id}" type="hidden" id="id" name="id">

                <div class="form-group">
                    <label>Name</label>
                    <input value="${requestScope.category.name}" type="text" class="form-control" id="name" name="name" placeholder="Enter name">
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="scripts.jsp"%>

</body>
