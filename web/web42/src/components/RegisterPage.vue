<script setup>
import axios from "axios";
import Swal from "sweetalert2";
import {ref} from "vue";
import CryptoJS from "crypto-js";

const username = ref('');
const password = ref('');

const handleRegister = async () => {
  try {
    const hashedPassword = CryptoJS.SHA256(password.value).toString(CryptoJS.enc.Base64);

    const response = await axios.post('/api/register', {
      username: username.value,
      password: hashedPassword,
    });

    if (response.status === 200) {
      Swal.fire({
        title: 'Регистрация успешна!',
        text: 'Добро пожаловать!',
        icon: 'success',
        confirmButtonText: 'Перейти к странице входа',
      }).then(() => {
        window.location.href = '/login';
      });
    } else {
      Swal.fire({
        title: 'Ошибка регистрации!',
        text: 'Введены неправильные или повторяющиеся данные',
        icon: 'error',
        confirmButtonText: 'Попробовать снова',
      });
    }
  } catch (error) {
    if (error.response.status === 409) {
      Swal.fire({
        title: 'Ошибка регистрации!',
        text: 'Такой пользователь уже зарегистрирован',
        icon: 'error',
        confirmButtonText: 'Попробовать снова',
      });
    } else {
      console.error('Registration error:', error);
      Swal.fire({
        title: 'Ошибка!',
        text: 'Во время выполнения возникла ошибка на сервере. Попробуйте позже',
        icon: 'error',
        confirmButtonText: 'OK',
      });
    }
  }
}
</script>

<template>
  <div>
    <h1>Регистрация</h1>
    <form @submit.prevent="handleRegister">
      <div>
        <label for="username">Имя пользователя:</label>
        <input id="username" v-model="username" type="text" placeholder="Введите логин" required/>
      </div>
      <div>
        <label for="password">Пароль:</label>
        <input id="password" v-model="password" type="password" placeholder="Введите пароль" required/>
      </div>
      <button type="submit">Зарегистрироваться</button>
    </form>
    <p>Уже есть аккаунт?
      <router-link to="/login">Войти</router-link>
    </p>
  </div>
</template>

<style scoped>
.registration-container {
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

form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.form-group {
  margin-bottom: 20px;
  width: 100%;
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
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
}

button:hover {
  background-color: #218838;
}

router-link {
  color: #007bff;
  text-decoration: underline;
}

router-link:hover {
  text-decoration: none;
}

@media (min-width: 1121px) {
  .registration-container {
    width: 500px;
  }
}

@media (max-width: 1120px) and (min-width: 643px) {
  .registration-container {
    width: 90%;
  }
}

@media (max-width: 642px) {
  .registration-container {
    width: 90%;
    padding: 15px;
  }

  h1 {
    font-size: 1.5rem;
  }

  button {
    padding: 10px;
  }

  input {
    padding: 8px;
  }
}
</style>
