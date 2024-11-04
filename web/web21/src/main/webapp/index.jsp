<%@ page import="web.lab.web21.AreaCheckServlet" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Web 2</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/toastify/toastify.css">
    <script defer src="${pageContext.request.contextPath}/script.js"></script>
    <script src="${pageContext.request.contextPath}/toastify/toastify.js"></script>
</head>
<body>
<header>
    <h1 id="header">Кулагин Вячеслав Дмитриевич, P3209, вариант: 285321</h1>
</header>

<main>
    <section class="form-graph-container">
        <div class="form-section">
            <h2>Входящие данные</h2>
            <form id="dataForm" action="${pageContext.request.contextPath}/process" method="POST">
                <label for="inputX">Введите X:</label>
                <input type="text" id="inputX" name="x" placeholder="Введите значение X (-3; 5)" maxlength="16">

                <label for="inputY">Введите Y:</label>
                <input type="text" id="inputY" name="y" placeholder="Введите значение Y (-5; 3)" maxlength="16">

                <label>Выберите R:</label>
                <div class="radio-group">
                    <label for="r1">
                        <input type="radio" id="r1" name="r" value="1"> 1
                    </label>
                    <label for="r2">
                        <input type="radio" id="r2" name="r" value="2"> 2
                    </label>
                    <label for="r3">
                        <input type="radio" id="r3" name="r" value="3"> 3
                    </label>
                    <label for="r4">
                        <input type="radio" id="r4" name="r" value="4"> 4
                    </label>
                    <label for="r5">
                        <input type="radio" id="r5" name="r" value="5"> 5
                    </label>
                </div>

                <button type="submit">Отправить</button>
            </form>
        </div>

        <div class="graph-section">
            <h2>График</h2>
            <canvas id="graphCanvas" width="400" height="400"></canvas>
        </div>
    </section>

    <h2>Результаты</h2>
    <table id="results-table">
        <thead>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат</th>
            <th>Время</th>
            <th>Время выполнения</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<AreaCheckServlet.Result> results = (List<web.lab.web21.AreaCheckServlet.Result>) session.getAttribute("results");

            if (results != null) {
                for (int i = results.size() - 1; i >= 0; i--) {
                    web.lab.web21.AreaCheckServlet.Result result = results.get(i);
        %>
        <tr>
            <td><%= result.getX() %></td>
            <td><%= result.getY() %></td>
            <td><%= result.getR() %></td>
            <td><%= result.isResult() ? "Попадание" : "Промах" %></td>
            <td><%= result.getTime() %></td>
            <td><%= result.getExecutionTime() %></td>
        </tr>
        <%
            }
        } else {
            }
        %>
        </tbody>
    </table>
</main>
</body>
</html>
