<script>
export default {
  name: 'Lab1View',
  data() {
    return {
      sampleSize: 1000,     // Начальное значение для размера выборки
      intervals: 11,        // Начальное значение для числа интервалов
      expectation: 2.5,    // Начальное значение для матожидания
    };
  },
  methods: {
    startCalculation() {
      console.log("Размер выборки:", this.sampleSize);
      console.log("Число интервалов:", this.intervals);
      console.log("Математическое ожидание:", this.expectation);
      // Здесь можно добавить логику для выполнения вычислений или отправки данных
    },
  },
};
</script>


<template>
  <main>
    <h2>Лабораторная работа №1</h2>

    <div>
      <h3>Параметры моделирования</h3>
      <div class="input-group"> <label for="sampleSize">Размер выборки (100 - 10000):</label> <input type="number"
          id="sampleSize" v-model.number="sampleSize" :min="100" :max="10000" required /> </div>
      <div class="input-group"> <label for="intervals">Число интервалов (5 - 20):</label> <input type="number"
          id="intervals" v-model.number="intervals" :min="5" :max="20" required /> </div>
      <div class="input-group"> <label for="expectation">Математическое ожидание (0.1 - 3):</label> <input type="number"
          id="expectation" v-model.number="expectation" :min="0.1" :max="3" step="0.1" required /> </div>
    </div>



    <button type="submit">Start</button>
  </main>
</template>

<style scoped>
main {
  margin-bottom: 20px;
}

aside {
  display: flex;
  margin: 0 20px;
  flex-flow: row wrap;
  justify-content: center;
}

.icon {
  width: 140px;
  margin: 10px 30px;
  align-self: end;
}

.icon img {
  width: 100%;
}

.icon figcaption {
  text-align: center;
}


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
</style>
