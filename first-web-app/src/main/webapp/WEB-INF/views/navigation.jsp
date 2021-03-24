<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="#">${param.title}</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <c:url value="/product" var="productUrl"/>
                <a class="nav-link" href="${productUrl}">Product</a>
            </li>
            <li class="nav-item active">
                <c:url value="/category" var="categoryUrl"/>
                <a class="nav-link" href="${categoryUrl}">Category</a>
            </li>
            <li class="nav-item active">
                <c:url value="/customer" var="customerUrl"/>
                <a class="nav-link" href="${customerUrl}">Customer</a>
            </li>
        </ul>
    </div>
</nav>
