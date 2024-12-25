<script setup>
import {ref, onMounted, onBeforeMount} from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import { watch } from 'vue';

const xCoordinate = ref('0');
const yCoordinate = ref('0');
const radius = ref('1');
const results = ref([]);
const canvas = ref(null);

const validateInput = () => {
  if (
      isNaN(parseFloat(xCoordinate.value)) ||
      isNaN(parseFloat(yCoordinate.value)) ||
      parseFloat(radius.value) <= 0 ||
      (-5 >= yCoordinate.value) ||
      (yCoordinate.value >= 5)
  ) {
    Swal.fire({
      title: 'Некорректные данные!',
      text: 'Пожалуйста, введите корректные данные',
      icon: 'error',
      confirmButtonText: 'OK',
    });
    return false;
  }
  return true;
};


const sendCoordinates = async (x, y, r) => {
  if (!validateInput()) return;

  try {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');
    if (!username) {
      Swal.fire({
        title: 'Ошибка!',
        text: 'Имя пользователя не найдено. Пожалуйста, войдите снова.',
        icon: 'error',
        confirmButtonText: 'OK',
      });
      return;
    }

    const response = await axios.post(
        '/api/check',
        { x, y, r, username, password }
    );

    const success = response.data.hit;
    const radius = response.data.r;

    results.value.push({ x, y, radius, result: success ? 'Попадание' : 'Промах' });

    Swal.fire({
      title: success ? 'Попадание' : 'Промах',
      icon: success ? 'success' : 'error',
      confirmButtonText: 'OK',
    });

    drawPoint(x, y, success ? 'green' : 'red');
  } catch (error) {
    Swal.fire({
      title: 'Ошибка!',
      text: 'Не удалось отправить данные на сервер:\n' + error,
      icon: 'error',
      confirmButtonText: 'OK',
    });
  }
};


const handleFormSubmit = () => {
  const x = parseFloat(xCoordinate.value);
  const y = parseFloat(yCoordinate.value);
  const r = parseFloat(radius.value);
  sendCoordinates(x, y, r);
};

const handleCanvasClick = (event) => {
  const rect = canvas.value.getBoundingClientRect();
  const x = ((event.clientX - rect.left) / rect.width - 0.5) * 8;
  const y = -((event.clientY - rect.top) / rect.height - 0.5) * 8;
  const r = parseFloat(radius.value);

  sendCoordinates(x.toFixed(2), y.toFixed(2), r);
};

const loadPoints = async () => {
  try {
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');
    if (!username) {
      Swal.fire({
        title: 'Ошибка!',
        text: 'Имя пользователя не найдено. Пожалуйста, войдите снова.',
        icon: 'error',
        confirmButtonText: 'OK',
      });
      return;
    }

    const response = await axios.get(
        '/api/getPoints',
        {
          params:
    {
      username, password
    },
  }
    );

    response.data.forEach(point => {
      results.value.push({
        x: point.x,
        y: point.y,
        radius: point.r,
        result: point.hit ? 'Попадание' : 'Промах',
      });
      drawPoint(point.x, point.y, point.hit ? 'green' : 'red');
    });
  } catch (error) {
    Swal.fire({
      title: 'Ошибка!',
      text: 'Не удалось получить данные с сервера:\n' + error,
      icon: 'error',
      confirmButtonText: 'OK',
    });
  }
}

const logout = () => {
  Swal.fire({
    title: 'Вы действительно хотите выйти?',
    text: 'Ваши данные будут удалены из системы',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Да, выйти',
    cancelButtonText: 'Отмена',
    reverseButtons: true,
  }).then((result) => {
    if (result.isConfirmed) {
      localStorage.removeItem('username');
      localStorage.removeItem('password');
      window.location.href = '/';
    }
  });
};

const drawGraph = () => {
  const ctx = canvas.value.getContext('2d');
  const width = canvas.value.width;
  const height = canvas.value.height;
  const r = parseFloat(radius.value);

  ctx.clearRect(0, 0, width, height);

  const centerX = width / 2;
  const centerY = height / 2;

  const scale = width / 8;

  ctx.fillStyle = 'rgba(0, 0, 255, 0.5)';
  ctx.beginPath();
  ctx.moveTo(centerX, centerY);
  ctx.arc(centerX, centerY, scale * r / 2, Math.PI, Math.PI * 1.5); // Четверть круга
  ctx.fillRect(centerX, centerY, r * scale, -r * scale / 2); // Прямоугольник

  ctx.closePath();
  ctx.fill();
  ctx.beginPath();
  ctx.moveTo(centerX, centerY);
  ctx.lineTo(centerX - r * scale / 2, centerY);
  ctx.lineTo(centerX, centerY + r * scale);   // Треугольник
  ctx.fill();

  // Отрисовка координатных осей
  ctx.strokeStyle = 'black';
  ctx.beginPath();
  ctx.moveTo(centerX, 0);
  ctx.lineTo(centerX, height);
  ctx.moveTo(0, centerY);
  ctx.lineTo(width, centerY);
  ctx.stroke();

  // Отметки на осях
  ctx.fillStyle = 'black';
  ctx.font = '12px Arial';
  for (let i = -4; i <= 4; i++) {
    const offsetX = centerX + i * scale;
    const offsetY = centerY - i * scale;
    if (i !== 0) {
      ctx.fillText(i, offsetX - 5, centerY + 15); // Ось X
      ctx.fillText(i, centerX + 5, offsetY + 5); // Ось Y
    }
  }

  results.value.forEach(result => {
    if (result.radius == radius.value) {
      drawPoint(result.x, result.y, result.result === 'Попадание' ? 'green' : 'red');
    }
  });
};

const drawPoint = (x, y, color) => {
  const ctx = canvas.value.getContext('2d');
  const width = canvas.value.width;
  const height = canvas.value.height;
  const r = parseFloat(radius.value);
  const scale = width / 8;

  const centerX = width / 2;
  const centerY = height / 2;

  ctx.fillStyle = color;
  ctx.beginPath();
  ctx.arc(centerX + x * scale, centerY - y * scale, 3, 0, 2 * Math.PI);
  ctx.fill();
};

watch(radius, drawGraph);
onBeforeMount( () => {

})

onMounted(() => {
  const username = localStorage.getItem('username');
  const password = localStorage.getItem('password');

  if (!username || !password) {
    Swal.fire({
      title: 'Вы не авторизованы!',
      text: 'Пожалуйста, войдите в систему.',
      icon: 'warning',
      confirmButtonText: 'OK',
    }).then(() => {
      window.location.href = '/';
    });
  } else {
    try {
      const response =  axios.post('/api/login', {
        username: username,
        password: password,
      });
      loadPoints();
      drawGraph();
    } catch (error) {
      Swal.fire({
        title: 'Ошибка входа!',
        text: 'Используемый пароль неправильный. Выполните вход заново',
        icon: 'error',
        confirmButtonText: 'OK',
      }).then(() => {
        localStorage.removeItem('username');
        localStorage.removeItem('password');
        window.location.href = '/';
      });
    }
  }
});
</script>

<script>
export default {
  data() {
    return {
      username: localStorage.getItem('username') || 'Гость',
    };
  },
};
</script>


<template>
  <div>
    <h1>График и точки</h1>

    <p>Вы вошли как: {{ username }}</p>

    <div>
      <label for="x-coordinate">Координата X:</label>
      <select id="x-coordinate" v-model="xCoordinate">
        <option value="-5">-5</option>
        <option value="-4">-4</option>
        <option value="-3">-3</option>
        <option value="-2">-2</option>
        <option value="-1">-1</option>
        <option value="0">0</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
      </select>
    </div>

    <div>
      <label for="y-coordinate">Координата Y:</label>
      <input id="y-coordinate" v-model="yCoordinate" type="number" step="0.1" placeholder="Введите Y (-5 ... 5)" />
    </div>

    <div>
      <label for="radius">Радиус:</label>
      <select id="radius" v-model="radius">
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
      </select>
    </div>

    <button @click="handleFormSubmit">Отправить данные</button>

    <canvas ref="canvas" id="plane" width="500" height="500" @click="handleCanvasClick"></canvas>

    <table border="1">
      <thead>
      <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Результат</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(result, index) in results" :key="index">
        <td>{{ result.x }}</td>
        <td>{{ result.y }}</td>
        <td>{{ result.radius }}</td>
        <td>{{ result.result }}</td>
      </tr>
      </tbody>
    </table>
    <button @click="logout">Выйти</button>
  </div>
</template>

<style scoped>
.container {
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  background: #f4f7fa;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 2rem;
  color: #333;
  text-align: center;
}

p {
  text-align: center;
  font-size: 1rem;
  color: #555;
}

label {
  display: block;
  font-size: 1rem;
  color: #555;
  margin-bottom: 5px;
}

select,
input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
  margin-bottom: 15px;
}

button {
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  margin-bottom: 15px;
}

button:hover {
  background-color: #0056b3;
}

canvas {
  width: 100%;
  max-width: 500px;
  height: auto;
  border: 1px solid #000;
  margin-top: 20px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

table {
  width: 100%;
  margin-top: 20px;
  border-collapse: collapse;
}

table, th, td {
  border: 1px solid #ccc;
}

th, td {
  padding: 10px;
  text-align: center;
}

button.logout {
  background-color: #dc3545;
  color: white;
  margin-top: 20px;
}

button.logout:hover {
  background-color: #c82333;
}

@media (min-width: 1121px) {
  .container {
    width: 600px;
  }
}

@media (max-width: 1120px) and (min-width: 643px) {
  .container {
    width: 90%;
  }

  canvas {
    height: 400px;
  }
}

@media (max-width: 642px) {
  .container {
    width: 90%;
    padding: 15px;
  }

  h1 {
    font-size: 1.5rem;
  }

  button {
    padding: 10px;
  }

  canvas {
    height: 350px;
  }

  table {
    font-size: 0.9rem;
  }
}

</style>
