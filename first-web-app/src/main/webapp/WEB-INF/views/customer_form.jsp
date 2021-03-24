<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp"%>

<body>

<jsp:include page="navigation.jsp">
    <jsp:param name="title" value="Customer"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/customer" var="customerSubmitUrl"/>
            <form action="${customerSubmitUrl}" method="post">
                <input value="${requestScope.customer.id}" type="hidden" id="id" name="id">

                <div class="form-group">
                    <label>Name</label>
                    <input value="${requestScope.customer.username}" type="text" class="form-control" id="username" name="username" placeholder="Enter username">
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <input value="${requestScope.customer.password}" type="text" class="form-control" id="password" name="password" placeholder="Enter password">
                </div>

                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="scripts.jsp"%>

</body>
