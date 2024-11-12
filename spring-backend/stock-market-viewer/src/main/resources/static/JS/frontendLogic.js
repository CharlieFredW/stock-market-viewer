const url = 'http://localhost:8080';

let selectedType = 'price'; // default select

const graphText = document.getElementById("graph-text");
const radio1 = document.getElementById("radio1");
const radio2 = document.getElementById("radio2");
const radio3 = document.getElementById("radio3");

const chart = document.getElementById("chart").getContext('2d');

const chartData = {
    labels: [],
    datasets: [{
        label: "",
        data: [],
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1
    }]
};

var newChart = new Chart(chart, {
    type: 'line',
    data: chartData,
});

const tickerValueParagraph = document.getElementById('tickerValue');
const tradeTimeParagraph = document.getElementById('tradeTime');
const highValueParagraph = document.getElementById('highValue');
const midValueParagraph = document.getElementById('midValue');
const lowValueParagraph = document.getElementById('lowValue');
const volumeValueParagraph = document.getElementById('volumeValue');
const tradesValueParagraph = document.getElementById('tradesValue');

function getCryptoObjects() {
    fetch(url + '/api/cryptoObjects')
        .then(response => response.json())
        .then(data => {
            const latestData = data[data.length - 1];

            tickerValueParagraph.textContent = latestData.ticker;
            tradeTimeParagraph.textContent = latestData.date;
            highValueParagraph.textContent = parseFloat(latestData.high).toFixed(2);
            midValueParagraph.textContent = parseFloat(latestData.mid).toFixed(2);
            lowValueParagraph.textContent = parseFloat(latestData.low).toFixed(2);
            volumeValueParagraph.textContent = parseFloat(latestData.volume).toFixed(2);
            tradesValueParagraph.textContent = latestData.tradesDone;
        })
        .catch(error => console.error('Error fetching crypto data:', error));
}

getCryptoObjects();
setInterval(getCryptoObjects, 10000);

function getCryptoGraph(type, timeInterval) {
    fetch(`${url}/api/cryptoGraph?type=${type}&timeInterval=${timeInterval}`)
        .then(response => response.json())
        .then(data => {
            const values = data.map(item => {
                switch(type) {
                    case 'price':
                        return item.mid;
                    case 'volume':
                        return item.volume;
                    case 'trades':
                        return item.trades;
                    default:
                        return null;
                }
            });
            const times = data.map(item => item.time);

            newChart.data.datasets[0].data = values;
            newChart.data.labels = times;
            newChart.update();
        })
        .catch(error => console.error('Error fetching crypto data:', error));
}

function updateGraph() {
    const selectedInterval = document.querySelector('input[name="option"]:checked').value;
    getCryptoGraph(selectedType, selectedInterval);
}

document.getElementById('price').addEventListener('click', function() {
    selectedType = 'price';
    graphText.textContent = "Price Development";
    newChart.data.datasets[0].label = "Price in USD";
    newChart.update();
    updateGraph();
});

document.getElementById('volume').addEventListener('click', function() {
    selectedType = 'volume';
    graphText.textContent = "Volume Development";
    newChart.data.datasets[0].label = "Volume";
    newChart.update();
    updateGraph();
});

document.getElementById('tradesDone').addEventListener('click', function() {
    selectedType = 'trades';
    graphText.textContent = "Trade Development";
    newChart.data.datasets[0].label = "Trades done";
    newChart.update();
    updateGraph();
});

document.querySelectorAll('input[name="option"]').forEach(radioButton => {
    radioButton.addEventListener('change', updateGraph);
});
