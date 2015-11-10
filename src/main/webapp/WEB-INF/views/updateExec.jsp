<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Edit executor</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container">

    <div class="content">

        <div class="sidebar">
            <table>
                <caption><h4>Исполнители: </h4></caption>
                <c:forEach var="executor" items="${executors}">
                    <tr>
                        <td>
                            <a href="/home/${executor.name}">${executor.name}</a>
                        </td>
                        <td>
                            <a href="/exec/update/${executor.name}">
                                <img src="${pageContext.request.contextPath}/resources/img/edit.png" title="Edit">
                            </a>
                        </td>
                        <td>
                            <a href="/exec/delete/${executor.name}">
                                <img src="${pageContext.request.contextPath}/resources/img/delete.png" title="Delete">
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="cont_body">
            <c:url value="/exec/update/" var="userActionUrl"/>
            <sf:form modelAttribute="executorModel" method="post" action="${userActionUrl}">
                <table>
                    <caption><h4>Изменить исполнителя:</h4></caption>
                    <tr>
                        <td>
                            Имя:
                        </td>
                        <td>
                            <sf:input path="name" required="true" readonly="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Тип:
                        </td>
                        <td>
                            <sf:radiobutton path="type" value="reader"/> Читатель
                            <sf:radiobutton path="type" value="writer"/> Писатель
                        </td>
                    </tr>
                    <tr align="center">
                        <td colspan="2">
                            <input type="submit" value="Обновить"/>
                        </td>
                    </tr>
                </table>
            </sf:form>
            </table>
        </div>
    </div>
    <div class="cont_footer">
        <div class="left">
            <button><a href="${pageContext.request.contextPath}/exec/create/new">Создать исполнителя</a></button>
        </div>
        <div class="right">
            <button><a href="${pageContext.request.contextPath}/home">Показать все задачи</a></button>
            <button><a href="${pageContext.request.contextPath}/task/create/new">Создать задачу</a></button>
        </div>

    </div>
</div>
</body>
</html>

