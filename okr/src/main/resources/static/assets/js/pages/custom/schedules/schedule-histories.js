"use strict";

let KTDatatablesDataSourceAjaxServer = function() {

    let initTable1 = function() {
        let searchBox = $("#justification-search");
        let beginDate = $("#begin-date");
        let endDate = $("#end-date");
        endDate.val(getCurrentDateInISOFormat());
        beginDate.val(getOffsetDateInISOFormat(-6));
        let table = $('#kt_datatable').DataTable({
            dom: `
            <'row'
                <'col-sm-12'tr>
            >
            <'row' 
                <'col-sm-12 col-md-7'p>
                <'col-sm-12 col-md-2 pl-15 pt-2'l>
                <'col-sm-12 col-md-3'i>
            >`,
            responsive: true,
            searchDelay: 500,
            processing: true,
            serverSide: true,
            autoWidth: false,
            language: {
                lengthMenu: "_MENU_",
                info: "Showing _START_ - _END_ of _TOTAL_",
                infoFiltered: "",
                loadingRecords: '&nbsp;',
                processing: '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> '
            },
            ajax: {
                url: '/api/okr-schedule-histories/datatables',
                type: 'POST',
                contentType: 'application/json',
                dataSrc: "data",
                data: function(d) {
                    d.beginDate = beginDate.val();
                    d.endDate = endDate.val();
                    return JSON.stringify(d);
                },
            },
            columnDefs: [
                {
                    targets: [5, 6, 8],
                    sortable: false
                },
                {
                    targets: [ 4, 5],
                    width: 120,
                },
                {
                    targets: '_all',
                    className: 'text-center',
                }
            ],
            columns: [
                {
                    target: 0,
                    title: "#",
                    data: 'historySeq',
                }, {
                    target: 1,
                    title: "SCHEDULE",
                    data: 'scheduleType'
                },
                {
                    target: 2,
                    title: "BEGIN DAY",
                    data: 'beginDay'
                },
                {
                    target: 3,
                    title: "END DAY",
                    data: 'endDay',
                    render: function (data, type, row, meta){
                        return renderEndDay(row);
                    }
                },
                {
                    target: 4,
                    title: "CLOSE AFTER DAYS",
                    data: 'closeAfterDays'
                },
                {
                    target: 5,
                    title: "REMIND BEFORE DAYS",
                    data: 'remindBeforeDays',
                },
                {
                    target: 6,
                    title: "REMIND TIME",
                    data: 'remindTime',
                },
                {
                    target: 7,
                    title: "EDIT DATE",
                    data: 'createdDate',
                    render: function (data){
                        return formatInstant(data);
                    }
                },
                {
                    target: 8,
                    title: "JUSTIFICATION",
                    data: 'justification',
                    render: function (data){
                        return renderStringWithTooltip(data, 10);
                    }
                },
            ],
        });
        $("#search-button").on('click', function (){
            let searchValue = searchBox.val();
            table.columns(8).search(searchValue).draw();
        });
        $("#reset-button").on('click', function (){
            searchBox.val("");
            table.columns(8).search("");
            endDate.val(getCurrentDateInISOFormat());
            beginDate.val(getOffsetDateInISOFormat(-6));
            table.ajax.reload();
        })
    };
    return {
        init: function() {
            initTable1();
        },
    };
}();

$(document).ready(function() {
    KTDatatablesDataSourceAjaxServer.init();
    $("#begin-date, #end-date").datepicker({
        rtl: KTUtil.isRTL(),
        todayBtn: "linked",
        clearBtn: true,
        todayHighlight: true,
        templates: "arrows",
    });
});

function renderEndDay(row){
    if (row.endDay !== 99) return row.endDay;
    if (row.scheduleType === 'QUARTERLY') return "The end of the quarter";
    if (row.scheduleType === 'MONTHLY') return "The end of the month";
    return row.endDay;
}



