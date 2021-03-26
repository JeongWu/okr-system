let quarter_select, quarter, chart;
jQuery(document).ready(function () {
    chart = new ApexCharts(document.querySelector("#company-chart"), options);
    chart.render();
    quarter_select = $("#quarter-select");
    quarter = quarter_select.val();
    callAjaxRender();
    quarter_select.on('change', function (){
        quarter = quarter_select.val();
        window.location.href = '/companies/dashboard?quarter='+quarter;
    })

});

let callAjaxRender = function (){
    $.ajax({
        url: '/api/companies/dashboard',
        data: {
            quarter: quarter
        },
        success(result){
            chart.destroy()
            options.xaxis.categories = result.companyObjectives.map(m=> m.objective);
            options.series[0].data = result.companyObjectives.map(m=> 10);
            options.series[1].data = result.companyObjectives.map(m=> m.progress);
            chart = new ApexCharts(document.querySelector("#company-chart"), options);
            chart.render();
        },
    });
}

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
