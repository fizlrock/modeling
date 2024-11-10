<template>
  <div>
    <h1>Лабораторная работа №2</h1>
    <h2> Задание </h2>
    <div>
      <h3>Вариант № 22</h3>
      <p>Получить последовательность из <i>N = 1000</i> событий, образующих нестационарный информационный поток
        Пуассона, в котором интенсивность <i>λ</i> прямо пропорциональна времени: <i>λ = λ(t) = at</i>. Определить
        среднюю продолжительность функционирования системы по <i>K=10</i> статистическим испытаниям.</p>
      <table border="1">
        <tr>
          <th>N варианта</th>
          <th>1</th>
          <th>2</th>
          <th>3</th>
          <th>4</th>
          <th>5</th>
        </tr>
        <tr>
          <td>Значение a</td>
          <td>0.5</td>
          <td>2</td>
          <td>5</td>
          <td>7.5</td>
          <td>10</td>
        </tr>
      </table>
      <p>Вывести значения интервалов времени между наступлениями событий и моменты времени наступления событий.</p>
    </div>

    <h2> Выполнение </h2>


    <div>
      <h3>Параметры моделирования</h3>


      <div class="input-group">
        <label for="expectation">Параметр a(0.1 - 3):</label>
        <input type="number" id="expectation" v-model.number="alpha" :min="0.1" step="0.1" required />
      </div>
      <button @click="fetchData()">Запуск моделирования</button>

    </div>




    <h3> Отчет о моделировании</h3>
    <div>
      <p>Среднее время работы модели {{ data.simTime }} tu </p>
    </div>

    <h4> Количество суммарно случившихся событий к моменту времени</h4>
    <Line :data="chartData" :options="chartOptions" />

    
    <h4>Время работы моделей</h4>
    <table border="1">
      <thead>
        <tr>
          <th>Номер модели</th>
          <th>Время (tu)</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(time, index) in data.simulationTimes" :key="index">
          <td>{{ index + 1 }}</td>
          <td>{{ time }}</td>
        </tr>
      </tbody>
    </table>


  </div>
</template>

<script>

import axios from 'axios';
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement } from 'chart.js';
ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale, PointElement);


export default {
  name: 'Lab2View',
  components: { Line },

  methods: {
    async fetchData() {
      try {
        console.log("Делаем запрос");
        const response = await axios.get(` http://107.172.142.23:8080/api/lab2/22\?alpha=${this.alpha}`);

        this.data.simTime = response.data.averageSimulationTime;
        this.data.simulationTimes = response.data.simulationTimes;


        this.chartData = {
          labels: Array.from({ length: response.data.eventChart.length + 1 }, (_, i) => i),
          datasets: [{
            label: '10-ая модель',
            data: response.data.eventChart,
            fill: false,
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
          }]
        }

      } catch (error) {
        console.error('Ошибка запроса:', error);
      }
    },
  },
  data() {
    return {
      alpha: 1.5,
      data: {
        simulationTimes: [],
        simTime: 0,
        eventChart: [],
      },

      chartData: {
        labels: [],
        datasets: []

      },
      chartOptions: {
        responsive: true,

        plugins: {
          legend: { display: true },
        },
        scales: {
          x: { title: { display: true, text: 'Время' } },
          y: { title: { display: true, text: 'Число событий' } },
        },

      }


    }
  }
}
</script>

<style scoped>
.input-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

input[type="number"] {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  box-sizing: border-box;
}

input[type="number"]:focus {
  border-color: #007BFF;
  outline: none;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

h3 {
  color: #007BFF;
  font-family: 'Arial', sans-serif;
  margin-bottom: 20px;
}


table {
  width: 100%;
  border-collapse: collapse;
}

table th,
table td {
  padding: 8px;
  text-align: center;
}

h2,
h3 {
  margin-top: 20px;
}
</style>
