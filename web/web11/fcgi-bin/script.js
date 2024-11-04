document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('dataForm');
    const inputY = document.getElementById('inputY');
    const inputR = document.getElementById('inputR');
    const resultsTableBody = document.querySelector('#results-table tbody');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const yValue = parseFloat(inputY.value);
        const rValue = parseFloat(inputR.value);

        if (isNaN(yValue) || yValue <= -5 || yValue >= 3 || !isFinite(rValue)) {
            Toastify({
                text: "Ошибка: Y должно быть числом в диапазоне (-5; 3)",
                duration: 3000,
                close: true,
                gravity: "top",
                position: "right",
                backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            }).showToast();
            return;
        }

        if (isNaN(rValue) || rValue <= 1 || rValue >= 4 || !isFinite(rValue)) {
            Toastify({
                text: "Ошибка: R должно быть числом в диапазоне (1; 4)",
                duration: 3000,
                close: true,
                gravity: "top",
                position: "right",
                backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
            }).showToast();
            return;
        }

        const formData = new URLSearchParams(new FormData(form));

        fetch('web11.jar', {
            method: 'POST',
            body: formData,
            headers: {
                'Content-Type': 'application /x-www-form-urlencoded'
            }
        })
            .then(response => {
                if (!response.ok) {
                    Toastify({
                        text: "ОШИБКА! Невалидные данные",
                        duration: 3000,
                        close: true,
                        gravity: "top",
                        position: "right",
                        backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                    }).showToast();
                } else {
                    response.json()
                        .then(data => {
                        const newRow = document.createElement('tr');
                        newRow.innerHTML = `
                        <td>${data.x}</td>
                        <td>${data.y}</td>
                        <td>${data.r}</td>
                        <td>${data.result}</td>
                        <td>${data.currentTime}</td>
                        <td>${data.executionTime} ns</td>
                        `;

                        resultsTableBody.appendChild(newRow);
                        Toastify({
                            text: "Успешно отправлено! Результат: " + data.result,
                            duration: 3000,
                            close: true,
                            gravity: "top",
                            position: "right",
                            backgroundColor: "linear-gradient(to right, #4caf50, #81c784)",
                        }).showToast();

                    });
                }
            })
            .catch(error => {
                Toastify({
                    text: "Ошибка соединения!",
                    duration: 3000,
                    close: true,
                    gravity: "top",
                    position: "right",
                    backgroundColor: "linear-gradient(to right, #ff5f6d, #ffc371)",
                }).showToast();
                console.error(error);
            });
    });

    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');

    const R = 150;

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
    ctx.fillRect(200, 200, R, R);

    // Кусок окружности
    ctx.beginPath();
    ctx.arc(200, 200, R / 2, 1.5 * Math.PI, 2 * Math.PI);
    ctx.lineTo(200, 200);
    ctx.fill();

    // Треугольник
    ctx.beginPath();
    ctx.moveTo(200, 200);
    ctx.lineTo(200 - R / 2, 200);
    ctx.lineTo(200, 200 - R / 2);
    ctx.fill();

    // Подписи осей
    ctx.fillStyle = "#000";
    ctx.font = "14px Arial";
    ctx.fillText("x", 390, 215);
    ctx.fillText("y", 215, 10);

    // Подписи R
    ctx.fillText("R", 350, 215);
    ctx.fillText("R/2", 280, 215);
    ctx.fillText("-R/2", 100, 215);
    ctx.fillText("-R", 50, 215);

    ctx.fillText("R", 210, 50);
    ctx.fillText("R/2", 210, 120);
    ctx.fillText("-R/2", 210, 280);
    ctx.fillText("-R", 210, 350);
});