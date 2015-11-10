<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet" type="text/css"/>
    <script src="${pageContext.request.contextPath}/resources/js/jquery-2.1.4.js">
    </script>
    <script>
        function checkStatusChange() {
            $.ajax({
                url: '/home/ajax',
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    $.each(data, function() {
                        $('#' + this.id).text(this.status);
                    })
                }
            })
        }
        setInterval(checkStatusChange, 3000);

        function forcedTaskStart(id) {
            $.ajax({
                url: '/task/start/' + id,
                type: 'GET',
                success: function(data) {
                    $('#' + id).text(data);
                }
            })
        }
    </script>
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
            <table id="home_table">
                <caption><h4>Задачи:</h4></caption>
                <tr>
                    <th>Название</th>
                    <th>Тип</th>
                    <th>Дата создания</th>
                    <th>
                        Приоритет
                        <a href="${pageContext.request.contextPath}/home/sort/up"><img src="${pageContext.request.contextPath}/resources/img/arrow-up.png" title="Up"></a>
                        <a href="${pageContext.request.contextPath}/home/sort/down"><img src="${pageContext.request.contextPath}/resources/img/arrow-down.png" title="Down"></a>
                    </th>
                    <th>Исполнитель</th>
                    <th>Статус</th>
                </tr>
                <c:forEach var="task" items="${tasks}">
                    <tr>
                        <td>
                            ${task.name}
                        </td>
                        <td>
                            ${task.type}
                        </td>
                        <td>
                            ${task.createAt}
                        </td>
                        <td>
                            ${task.priority}
                        </td>
                        <td>
                            ${task.executor.name}
                        </td>
                        <td id="${task.id}">
                            ${task.status}
                        </td>
                        <td>
                            <a href="/task/update/${task.id}">
                                <img src="${pageContext.request.contextPath}/resources/img/edit.png" title="Edit"/>
                            </a>
                        </td>
                        <td>
                            <a href="/task/delete/${task.id}">
                                <img src="${pageContext.request.contextPath}/resources/img/delete.png" title="Delete"/>
                            </a>
                        </td>
                        <c:if test="${task.executor != null}">
                            <td>
                                <a onclick="forcedTaskStart('${task.id}')">
                                    <img src="${pageContext.request.contextPath}/resources/img/start.png" title="Start"/>
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <div id="ajax"></div>
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
