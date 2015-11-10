<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Edit task</title>
    <script>
        var mass = ${executors};

        function changePrior() {
            var priority = document.getElementById("priority").value;
            var span = document.getElementById("range");
            span.innerHTML = priority;
        }

        function getRightExecutors(value) {
            var select = document.getElementById("executorId");
            select.innerHTML = "";
            var noneOption = document.createElement('option');
            noneOption.setAttribute('value','none');
            noneOption.innerHTML = 'none';
            noneOption.setAttribute('selected', '');
            select.appendChild(noneOption);
            if(value == 'read') {
                for (var i = 0; i < mass.length; i++) {
                    if (mass[i].split(',')[1] == 'reader') {
                        var option = document.createElement('option');
                        option.setAttribute('value', mass[i].split(',')[0]);
                        option.innerHTML = mass[i].split(',')[0];
                        select.appendChild(option);
                    }
                }
            } else if (value == 'write') {
                for (var i = 0; i < mass.length; i++) {
                    if (mass[i].split(',')[1] == 'writer') {
                        var option = document.createElement('option');
                        option.setAttribute('value', mass[i].split(',')[0]);
                        option.innerHTML = mass[i].split(',')[0];
                        select.appendChild(option);
                    }
                }
            }
        }
    </script>
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

        <c:url value="/task/update" var="userActionUrl"/>
        <div class="cont_body">
            <sf:form modelAttribute="taskModel" action="${userActionUrl}" method="post">
                <table>
                    <caption><h4>Изменить задание:</h4></caption>
                    <tr>
                        <td>
                            Имя:
                        </td>
                        <td>
                            <sf:input path="name" required="true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Тип:
                        </td>
                        <td align="right">
                            <sf:radiobutton path="type" value="read" id="read" onclick="getRightExecutors('read')"/> Прочитать
                            <sf:radiobutton path="type" value="write" id="write" onclick="getRightExecutors('write')"/> Записать
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Приоритет:
                        </td>
                        <td>
                            <sf:input path="priority" type="range" min="1" max="10" oninput="changePrior()"/>
                            <span id="range">${taskModel.priority}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Исполнитель:
                        </td>
                        <td>
                            <select size="1" id="executorId" name="executorId">
                                <c:if test="${taskModel.executor != null}">

                                    <option value="none">none</option>

                                    <c:forEach var="executor" items="${executors}">

                                        <c:if test="${taskModel.executor.type eq executor.type}">

                                            <c:choose>
                                                <c:when test="${taskModel.executor.name eq executor.name}">
                                                    <option value="${executor.name}" selected>${executor.name}</option>
                                                </c:when>
                                                <c:when test="${taskModel.executor.name ne executor.name}">
                                                    <option value="${executor.name}">${executor.name}</option>
                                                </c:when>
                                            </c:choose>

                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${taskModel.executor == null}">
                                    <script>
                                        getRightExecutors('${taskModel.type}');
                                    </script>
                                </c:if>
                            </select>
                        </td>
                    </tr>
                    <tr align="center">
                        <td colspan="2">
                            <sf:hidden path="id"/>
                            <sf:hidden path="createAt"/>
                            <input type="submit" value="Обновить"/>
                        </td>
                    </tr>
                </table>
            </sf:form>
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

