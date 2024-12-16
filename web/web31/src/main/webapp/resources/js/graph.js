function drawGraph(ctx, R, points) {
    // Масштабируем размер области в зависимости от R
    const scale = 50;  // Коэффициент масштабирования для элементов графика
    const offsetX = 200;
    const offsetY = 200;

    ctx.clearRect(0, 0, ctx.canvas.width, ctx.canvas.height);  // Очистить холст

    // Оси
    ctx.beginPath();
    ctx.moveTo(offsetX, 0);   // Ось X
    ctx.lineTo(offsetX, ctx.canvas.height); // Ось Y
    ctx.moveTo(0, offsetY);   // Ось Y
    ctx.lineTo(ctx.canvas.width, offsetY); // Ось X
    ctx.strokeStyle = "#000";
    ctx.stroke();

    ctx.fillStyle = "rgba(0, 0, 255, 0.5)";

    // Прямоугольник (верхняя правая часть)
    ctx.fillRect(offsetX, offsetY, -R * scale, R * scale / 2);

    // Кусок окружности
    ctx.beginPath();
    ctx.arc(offsetX, offsetY, R * scale, Math.PI, -0.5 * Math.PI);
    ctx.lineTo(offsetX, offsetY);
    ctx.fill();

    // Треугольник (нижняя левая часть)
    ctx.beginPath();
    ctx.moveTo(offsetX, offsetY);
    ctx.lineTo(offsetX + R * scale / 2, offsetY);
    ctx.lineTo(offsetX, offsetY - R * scale / 2);
    ctx.fill();

    // Подписи осей и R
    ctx.fillStyle = "#000";
    ctx.font = "14px Arial";

    // Подписи оси X
    ctx.fillText("3", offsetX + scale * 3, offsetY + 15);
    ctx.fillText("1.5", offsetX + scale * 1.5, offsetY + 15);
    ctx.fillText("-1.5", offsetX - scale * 1.5, offsetY + 15);
    ctx.fillText("-3", offsetX - scale * 3, offsetY + 15);

    // Подписи оси Y
    ctx.fillText("3", offsetX - 15, offsetY - scale * 3);
    ctx.fillText("1.5", offsetX - 15, offsetY - scale * 1.5);
    ctx.fillText("-1.5", offsetX - 15, offsetY + scale * 1.5);
    ctx.fillText("-3", offsetX - 15, offsetY + scale * 3);

    const canvas = document.getElementById("graphCanvas");

    const range = 7; // Диапазон графика: от -5 до 5 по обеим осям
    const pixelsPerUnit = canvas.width / range; // Масштаб: пикселей на единицу

    points.forEach(point => {
        if (point.r === R) {
            // Центр канваса (0, 0) графика
            const canvasCenterX = canvas.width / 2;
            const canvasCenterY = canvas.height / 2;

            // Преобразование координат графика в пиксели
            const xCanvas = canvasCenterX + point.x * pixelsPerUnit;
            const yCanvas = canvasCenterY - point.y * pixelsPerUnit; // Инверсия оси Y

            // Рисование точки
            ctx.beginPath();
            ctx.arc(xCanvas, yCanvas, 5, 0, 2 * Math.PI);
            ctx.fillStyle = point.hit ? "green" : "red"; // Цвет в зависимости от попадания
            ctx.fill();
        }
    });

}

function updateGraph() {
    const canvas = document.getElementById("graphCanvas");
    const ctx = canvas.getContext("2d");
    const R = parseFloat(document.getElementById("formId:rValue").innerText);

    // Получаем JSON-данные из скрытого элемента
    const pointsData = document.getElementById("formId:graphData").innerText;
    let points = JSON.parse(pointsData);

    drawGraph(ctx, R, points);
}


function initializeGraph() {
    const radiusInput = document.getElementById("formId:rValue");
    if (radiusInput) {
        radiusInput.addEventListener("change", updateGraph);
    }
    updateGraph();
}

function handleCanvasClick(event) {
    const canvas = document.getElementById("graphCanvas");
    const rect = canvas.getBoundingClientRect();
    const xPixel = event.clientX - rect.left;
    const yPixel = event.clientY - rect.top;

    const R = parseFloat(document.getElementById("formId:rValue").innerText);

    const range = 7; // Диапазон графика: от -5 до 5 по обеим осям
    const pixelsPerUnit = canvas.width / range; // Масштаб: пикселей на единицу

    // Центр канваса
    const canvasCenterX = canvas.width / 2;
    const canvasCenterY = canvas.height / 2;

    // Преобразование пикселей в координаты графика
    const x = (xPixel - canvasCenterX) / pixelsPerUnit; // Сдвиг и масштаб
    const y = (canvasCenterY - yPixel) / pixelsPerUnit; // Инверсия и масштаб

    // Установка скрытых полей формы для передачи значений на сервер
    document.getElementById("formId:hiddenX").value = x.toFixed(2);
    document.getElementById("formId:hiddenY").value = y.toFixed(2);

    // Отправка формы
    document.getElementById("formId:submitPoint").click();
}


document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById("graphCanvas");
    if (canvas) {
        canvas.addEventListener("click", handleCanvasClick);
    }
    initializeGraph();
});
