$(document).ready(function() {
    let chart;

    let $quarter_select = $("#quarter-select");
    $quarter_select.on('change', function () {
        let quarter = $quarter_select.val();
        $("#current_quarter").val(quarter);
        renderContent("/api/companies/okrs/quarterly/dashboard", {quarter: quarter, seq: "", type: "default"});
    });

    let $view_company_okr = $("#view_company_okr")
    $view_company_okr.on("click", function (){
        let quarter = $quarter_select.val();
        renderContent("/api/companies/okrs/quarterly", {quarter: quarter, seq: "", type: "company"});
    })

    let options = {
        series: [{
            name: 'Last week',
            data: []
        }, {
            name: 'Current Week',
            data: []
        }],
        chart: {
            type: 'bar',
            height: 350
        },
        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: '15%',
                endingShape: 'rounded'
            },
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            show: true,
            width: 2,
            colors: ['transparent']
        },
        xaxis: {
            categories: [],
        },
        yaxis: {
            title: {
                text: 'Percentage(%)'
            },
            max: 100
        },
        fill: {
            opacity: 1
        },
        tooltip: {
            y: {
                formatter: function (val) {
                    return `${val} %`
                }
            }
        }
    };
    let renderChart = function () {
        $.ajax({
            url: '/api/companies/okrs/quarterly/dashboard/chart',
            data: {
                quarter: $quarter_select.val()
            },
            success(result) {
                chart.destroy()
                options.xaxis.categories = result.companyObjectives.map(m => m.objective);
                options.series[0].data = result.companyObjectives.map(m => 10);
                options.series[1].data = result.companyObjectives.map(m => m.progress);
                chart = new ApexCharts(document.querySelector("#company-chart"), options);
                chart.render();
            },
            error(error){
                let message = error.responseJSON.message;
                if (message === null || message === undefined)
                    Swal.fire("Error!", "Some errors occur while loading chart" , "error");
                else Swal.fire("Error!", message , "error");
            }
        });
    }

    chart = new ApexCharts(document.querySelector("#company-chart"), options);
    chart.render();
    renderChart();
});






