<script setup>
import { ref } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2';
import CryptoJS from "crypto-js";

const username = ref('');
const password = ref('');

const handleLogin = async () => {
  try {
    const hashedPassword = CryptoJS.SHA256(password.value).toString(CryptoJS.enc.Base64);

    const response = await axios.post('/api/login', {
      username: username.value,
      password: hashedPassword,
    });

    if (response.status === 200) {
      Swal.fire({
        title: 'Вход успешен!',
        text: 'Добро пожаловать!',
        icon: 'success',
        confirmButtonText: 'Перейти к главной странице',
      }).then(() => {
        localStorage.setItem('username', username.value);
        localStorage.setItem('password', hashedPassword);
        window.location.href = '/main';
      });
    } else {
      Swal.fire({
        title: 'Ошибка входа!',
        text: 'Неправильное имя пользователя или пароль',
        icon: 'error',
        confirmButtonText: 'Попробовать снова',
      });
    }
  } catch (error) {
    if (error.response.status === 401) {
      Swal.fire({
        title: 'Ошибка входа!',
        text: 'Неправильное имя пользователя или пароль',
        icon: 'error',
        confirmButtonText: 'Попробовать снова',
      });
    } else {
      console.error('Login error:', error);
      Swal.fire({
        title: 'Ошибка!',
        text: 'Во время выполнения возникла ошибка на сервере. Попробуйте позже',
        icon: 'error',
        confirmButtonText: 'OK',
      });
    }
  }
};
</script>

<template>
  <div>
    <h1>Lab Web #4</h1>
    <h3>ФИО: Кулагин Вячеслав Дмитриевич</h3>
    <p>Группа: P3209</p>
    <p>Вариант: 928775</p>

    <h2>Пожалуйста, войдите:</h2>

    <form @submit.prevent="handleLogin">
      <div>
        <label for="username">Имя пользователя:</label>
        <input id="username" v-model="username" type="text" placeholder="Введите логин" required/>
      </div>
      <div>
        <label for="password">Пароль:</label>
        <input id="password" v-model="password" type="password" placeholder="Введите пароль" required/>
      </div>
      <button type="submit">Войти</button>
    </form>

    <p>Нет аккаунта?
      <router-link to="/register">Зарегистрироваться</router-link>
    </p>
  </div>
</template>

<style scoped>
.login-container {
  width: 100%;
  max-width: 600px;
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

h2 {
  font-size: 1.5rem;
  color: #333;
  text-align: center;
}

h3, p {
  text-align: center;
  color: #555;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-size: 1rem;
  color: #555;
  margin-bottom: 5px;
}

input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
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
}

button:hover {
  background-color: #0056b3;
}

router-link {
  color: #007bff;
  text-decoration: underline;
}

router-link:hover {
  text-decoration: none;
}

@media (min-width: 1121px) {
  .login-container {
    width: 500px;
  }
}

@media (max-width: 1120px) and (min-width: 643px) {
  .login-container {
    width: 90%;
  }
}

@media (max-width: 642px) {
  .login-container {
    width: 90%;
    padding: 15px;
  }

  h1 {
    font-size: 1.5rem;
  }

  h2 {
    font-size: 1.2rem;
  }

  button {
    padding: 10px;
  }

  input {
    padding: 8px;
  }
}
</style>
