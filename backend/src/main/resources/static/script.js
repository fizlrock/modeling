
var ctx = document.getElementById('myChart').getContext('2d');

fetch('http://localhost:8080/api/report?valueCount=1000&rangeCount=11&alpha=5')
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }
      // Examine the text in the response  
      response.json().then(function(data) {
        console.log(data)

        var myChart = new Chart(ctx, {
          type: 'line',
          data: {
            labels: data.teorDensityDots.map(dot => dot.x),
            datasets: [{
              type: 'line',
              label: 'Теоретическая плотность',
              data: data.teorDensityDots.map(dot => dot.y),
              fill: false,
              borderColor: 'rgb(75, 192, 192)',
              tension: 0.1
            }, {
              type: 'bar',
              label: 'Наблюдаемая плотность',
              backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
              ], data: data.realDensityDots.map(dot => dot.y),
              fill: false,
              tension: 0.1
            }]
          },
          options: {
            plugins: {
              title: {
                display: true,
                text: 'Функция плотности распределения вероятностей'
              }
            },
            scales: {
              y: {
                title: {
                  display: true,
                  text: 'f(x)'
                },
                beginAtZero: true,
              },
              x: {
                title: {
                  display: true,
                  text: 'x'
                },
                beginAtZero: true,
              }
            }
          }
        });
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });
