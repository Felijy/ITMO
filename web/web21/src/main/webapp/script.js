document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('dataForm');
    const inputY = document.getElementById('inputY');
    const inputX = document.getElementById('inputX');
    const resultsTableBody = document.querySelector('#results-table tbody');
    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');

    // Функция для отправки данных формы
    form.addEventListener('submit', function (event) {
        event.preventDefault();
        const yValue = parseFloat(inputY.value);
        const xValue = parseFloat(inputX.value);

        // Валидация входных данных
        if (isNaN(yValue) || yValue <= -5 || yValue >= 3 || !isFinite(yValue)) {
            showToast("Ошибка: Y должно быть числом в диапазоне (-5; 3)", "error");
            return;
        }

        if (isNaN(xValue) || xValue <= -3 || xValue >= 5 || !isFinite(xValue)) {
            showToast("Ошибка: X должно быть числом в диапазоне (-3; 5)", "error");
            return;
        }

        const formData = new URLSearchParams(new FormData(form));

        fetch('/web21/controller', {
            method: 'POST',
            body: formData,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = response.url;
                } else {
                    showToast("Ошибка проверки данных!", "error");
                }
            })
            .catch(error => {
                showToast("Ошибка соединения!", "error");
                console.error(error);
            });
    });

    canvas.addEventListener('click', function (event) {
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;

        const canvasCenterX = canvas.width / 2;
        const canvasCenterY = canvas.height / 2;

        const selectedR = getSelectedRValue();

        if (selectedR) {
            const normalizedX = ((x - canvasCenterX) * (selectedR / (canvas.width / 2)))*1.4;
            const normalizedY = ((canvasCenterY - y) * (selectedR / (canvas.height / 2)))*1.4;

            sendPointToServer(normalizedX, normalizedY, selectedR);
            plotPoint(ctx, x, y, 'red');
        } else {
            showToast("Ошибка: Выберите значение R.", "error");
        }
    });


    function getSelectedRValue() {
        const rButtons = document.querySelectorAll('input[name="r"]');
        for (const rButton of rButtons) {
            if (rButton.checked) {
                return parseFloat(rButton.value);
            }
        }
        return null;
    }

    function sendPointToServer(x, y, r) {
        const pointData = new URLSearchParams({
            x: x.toFixed(2),
            y: y.toFixed(2),
            r: r.toFixed(2)
        });

        fetch('/web21/controller', {
            method: 'POST',
            body: pointData,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = response.url;
                } else {
                    showToast("Ошибка проверки данных!", "error");
                }
            })
            .catch(error => {
                showToast("Ошибка соединения!", "error");
                console.error(error);
            });
    }

    function plotPoint(ctx, x, y, color) {
        ctx.beginPath();
        ctx.arc(x, y, 3, 0, 2 * Math.PI);
        ctx.fillStyle = color;
        ctx.fill();
    }

    function displayFilteredPoints(resultsTableBody, selectedR) {
        // Очищаем старые точки на графике
        drawGraph(ctx, 150);

        // Проходим по всем строкам таблицы и фильтруем по значению R
        Array.from(resultsTableBody.rows).forEach(row => {
            const rValue = parseFloat(row.cells[2].textContent);
            if (rValue === selectedR) {
                const xValue = parseFloat(row.cells[0].textContent);
                const yValue = parseFloat(row.cells[1].textContent);

                // Преобразуем координаты для отображения на canvas
                const canvasCenterX = canvas.width / 2;
                const canvasCenterY = canvas.height / 2;

                const xCanvas = (xValue / (selectedR / (canvas.width / 2) * 1.4)) + canvasCenterX;
                const yCanvas = -((yValue / (selectedR / (canvas.height / 2) * 1.4)) - canvasCenterY);

                plotPoint(ctx, xCanvas, yCanvas, 'red');
            }
        });
    }

    document.querySelectorAll('input[name="r"]').forEach(rButton => {
        rButton.addEventListener('change', () => {
            const selectedR = parseFloat(rButton.value);
            displayFilteredPoints(resultsTableBody, selectedR);
        });
    });

    function updateResultsTable(result) {
        const row = resultsTableBody.insertRow(0);

        const cellX = row.insertCell(0);
        const cellY = row.insertCell(1);
        const cellR = row.insertCell(2);
        const cellInside = row.insertCell(3);
        const cellTime = row.insertCell(4);
        const cellExecutionTime = row.insertCell(5);

        cellX.textContent = result.x;
        cellY.textContent = result.y;
        cellR.textContent = result.r;
        cellInside.textContent = result.isInside ? "Попадание" : "Промах";
        cellTime.textContent = result.time;
        cellExecutionTime.textContent = result.executionTime;
    }

    function showToast(message, type) {
        const backgroundColor = type === 'error'
            ? "linear-gradient(to right, #ff5f6d, #ffc371)"
            : "linear-gradient(to right, #00b09b, #96c93d)";
        Toastify({
            text: message,
            duration: 3000,
            close: true,
            gravity: "top",
            position: "right",
            backgroundColor: backgroundColor,
        }).showToast();
    }

    function drawGraph(ctx, R) {
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Оси
        ctx.beginPath();
        ctx.moveTo(200, 0);
        ctx.lineTo(200, 400);
        ctx.moveTo(0, 200);
        ctx.lineTo(400, 200);
        ctx.strokeStyle = "#000";
        ctx.stroke();

        ctx.fillStyle = "rgba(0, 0, 255, 0.5)";

        // Прямоугольник
        ctx.fillRect(200, 200, R / 2, R);

        // Кусок окружности
        ctx.beginPath();
        ctx.arc(200, 200, R / 2, -1.5 * Math.PI, -1 * Math.PI);
        ctx.lineTo(200, 200);
        ctx.fill();

        // Треугольник
        ctx.beginPath();
        ctx.moveTo(200, 200);
        ctx.lineTo(200 + R / 2, 200);
        ctx.lineTo(200, 200 - R / 2);
        ctx.fill();

        // Подписи осей и R
        ctx.fillStyle = "#000";
        ctx.font = "14px Arial";
        ctx.fillText("x", 390, 215);
        ctx.fillText("y", 215, 10);

        ctx.fillText("R", 350, 215);
        ctx.fillText("R/2", 280, 215);
        ctx.fillText("-R/2", 100, 215);
        ctx.fillText("-R", 50, 215);

        ctx.fillText("R", 210, 50);
        ctx.fillText("R/2", 210, 120);
        ctx.fillText("-R/2", 210, 280);
        ctx.fillText("-R", 210, 350);
    }
    drawGraph(ctx, 150); // Начальное значение R
    displayFilteredPoints(resultsTableBody, 150); // Отобразить точки с начальным R
});
