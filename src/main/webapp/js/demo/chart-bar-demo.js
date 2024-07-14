

// chart-bar-demo.js
document.addEventListener('DOMContentLoaded', (event) => {
    fetch('/GestorMetas/Index')
            .then(response => response.json())
            .then(data => {
                data.forEach(usuario => {
                    var usuarioNombre = usuario.usuario;
                    var metas = usuario.nombresMetas;
                    var porcentajes = usuario.porcentajesCumplimiento;

                    // Crear un nuevo canvas para el gráfico
                    var container = document.createElement('div');
                    container.className = 'chart-container';
                    container.innerHTML = `
                            <div class="card shadow mb-4">
                                <div class="card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">KPI's - ${usuarioNombre}</h6>
                                </div>
                                <div class="card-body">
                                    <div class="chart-bar">
                                        <canvas id="chart-${usuarioNombre}"></canvas>
                                    </div>
                                </div>
                            </div>
                        `;
                    document.getElementById('chartArea').appendChild(container);

                    var ctx = document.getElementById(`chart-${usuarioNombre}`).getContext('2d');
                    var myBarChart = new Chart(ctx, {
                        type: 'bar',
                        data: {
                            labels: metas,
                            datasets: [{
                                    label: "Porcentaje de Cumplimiento",
                                    backgroundColor: "#4e73df",
                                    hoverBackgroundColor: "#2e59d9",
                                    borderColor: "#4e73df",
                                    data: porcentajes,
                                }],
                        },
                        options: {
                            maintainAspectRatio: false,
                            layout: {
                                padding: {
                                    left: 10,
                                    right: 25,
                                    top: 25,
                                    bottom: 0
                                }
                            },
                            scales: {
                                xAxes: [{
                                        time: {
                                            unit: 'meta'
                                        },
                                        gridLines: {
                                            display: false,
                                            drawBorder: false
                                        },
                                        ticks: {
                                            maxTicksLimit: 6
                                        },
                                        maxBarThickness: 25,
                                    }],
                                yAxes: [{
                                        ticks: {
                                            min: 0,
                                            max: 100,
                                            maxTicksLimit: 5,
                                            padding: 10,
                                            callback: function (value, index, values) {
                                                return value + '%';
                                            }
                                        },
                                        gridLines: {
                                            color: "rgb(234, 236, 244)",
                                            zeroLineColor: "rgb(234, 236, 244)",
                                            drawBorder: false,
                                            borderDash: [2],
                                            zeroLineBorderDash: [2]
                                        }
                                    }],
                            },
                            legend: {
                                display: false
                            },
                            tooltips: {
                                titleMarginBottom: 10,
                                titleFontColor: '#6e707e',
                                titleFontSize: 14,
                                backgroundColor: "rgb(255,255,255)",
                                bodyFontColor: "#858796",
                                borderColor: '#dddfeb',
                                borderWidth: 1,
                                xPadding: 15,
                                yPadding: 15,
                                displayColors: false,
                                caretPadding: 10,
                                callbacks: {
                                    label: function (tooltipItem, chart) {
                                        var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                                        return datasetLabel + ': ' + tooltipItem.yLabel + '%';
                                    }
                                }
                            },
                        }
                    });
                });
            })
            .catch(error => console.error('Error fetching data:', error));
});