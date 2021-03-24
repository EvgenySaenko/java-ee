<%@ page import="ru.geekbrains.persist.ProductRepository" %>
<%@ page import="ru.geekbrains.persist.Product" %>
<%@ page import="java.util.List" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%--подтягиваем head из файла head.jsp--%>
<%--выполняется еще до трансляции в java код то есть до того как компонент Джаспер--%>
<%--начинает переводить страничку в сервлет--%>
<%@include file="head.jsp" %>

<body>
<%--подключаем код навигейшон меню и инжектим в него параметр title со значением Product--%>
<jsp:include page="navigation.jsp">
    <jsp:param name="title" value="Customer"/>
</jsp:include>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/customer/new" var="newCustomer"/>
            <a class="btn btn-primary" href="${newCustomer}">Add Customer</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Username</th>
                    <th scope="col">Password</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${requestScope.customers.isEmpty()}">
                        <tr>
                            <td colspan="4">
                                No data
                            </td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <%--                <% for(Product product :(List<Product>) request.getAttribute("products")) { %>--%>
                        <c:forEach var="customer" items="${requestScope.customers}">
                            <tr>
                                <th scope="row">
                                    <c:out value="${customer.id}"/>
                                </th>
                                <td>
                                    <c:out value="${customer.username}"/>
                                </td>
                                <td>
                                    <c:out value="${customer.password}"/>
                                </td>

                                <td>
                                    <c:url value="/customer/${customer.id}" var="customerUrl"/>
                                    <a class="btn btn-success" href="${customerUrl}"><i class="fas fa-edit"></i></a>
                                    <a class="btn btn-danger" href="#"><i class="far fa-trash-alt"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>

                </c:choose>


                </tbody>
            </table>
        </div>
    </div>
</div>
<%--подтягиваем скрипты из файла scripts.jsp--%>
<%@include file="scripts.jsp"%>
</body>
</html>
