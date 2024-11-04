<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Результаты проверки</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Результаты проверки точек</h1>

<form action="index.jsp" method="get">
    <button type="submit">Вернуться к форме</button>
</form>

<section class="form-result">
    <table border="1">
        <thead>
        <tr>
            <th>X</th>
            <th>Y</th>
            <th>R</th>
            <th>Результат</th>
            <th>Время проверки</th>
            <th>Время выполнения (нс)</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<web.lab.web21.AreaCheckServlet.Result> results = (List<web.lab.web21.AreaCheckServlet.Result>) session.getAttribute("results");

            if (results != null) {
                for (int i = results.size() - 1; i >= 0; i--) {
                    web.lab.web21.AreaCheckServlet.Result result = results.get(i);
                    if (i == results.size() - 1) {
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
                    } else {
        %>
        <tr style="background-color: lightgrey;">
            <td><%= result.getX() %></td>
            <td><%= result.getY() %></td>
            <td><%= result.getR() %></td>
            <td><%= result.isResult() ? "Попадание" : "Промах" %></td>
            <td><%= result.getTime() %></td>
            <td><%= result.getExecutionTime() %></td>
        </tr>
        <%
                }
            }
        } else {
        %>
        <tr>
            <td colspan="6">Нет сохраненных результатов</td>
        </tr>
        <%
            }
        %>

        </tbody>
    </table>
</section>
</body>
</html>
