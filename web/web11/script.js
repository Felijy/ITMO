document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('dataForm');
    const inputY = document.getElementById('inputY');
    const inputR = document.getElementById('inputR');

    form.addEventListener('submit', function (event) {
        event.preventDefault(); // Останавливаем отправку формы

        const yValue = parseFloat(inputY.value);
        const rValue = parseFloat(inputR.value);

        if (isNaN(yValue) || yValue <= 0) {
            alert("Ошибка: Y должно быть числом больше 0.");
            return;
        }

        if (isNaN(rValue) || rValue <= 0) {
            alert("Ошибка: R должно быть числом больше 0.");
            return;
        }

        const formData = new FormData(form);


        fetch('/fcgi-bin/web11.jar', {
            method: 'POST',
            body: formData
        })
            .then(response => response.text())
            .then(data => {
                // Обработка ответа от FastCGI сервера
                console.log(data);
            })
            .catch(error => {
                console.error('Ошибка:', error);
            });
    });

    const canvas = document.getElementById('graphCanvas');
    const ctx = canvas.getContext('2d');
    const R = 150; // Радиус

    // Настройка осей координат
    ctx.beginPath();
    ctx.moveTo(200, 0);
    ctx.lineTo(200, 400);
    ctx.moveTo(0, 200);
    ctx.lineTo(400, 200);
    ctx.strokeStyle = "#000";
    ctx.stroke();

    // Рисуем фигуру
    ctx.fillStyle = "rgba(0, 0, 255, 0.5)";

    // 1. Прямоугольник
    ctx.fillRect(200, 200, R, R);

    // 2. Четверть окружности
    ctx.beginPath();
    ctx.arc(200, 200, R / 2, 1.5 * Math.PI, 2 * Math.PI);
    ctx.lineTo(200, 200);
    ctx.fill();

    // 3. Треугольник
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
