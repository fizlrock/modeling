<template>
  <div>
    <line-chart :chart-data="datacollection" />
  </div>
</template>

<script>
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, CategoryScale);

export default {
  name: 'LineChart',
  components: {
    Line,
  },
  data() {
    return {
      datacollection: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
          {
            label: 'Data One',
            backgroundColor: '#f87979',
            data: [40, 39, 10, 40, 39, 80, 40],
          },
        ],
      },
    };
  },
};
</script>
