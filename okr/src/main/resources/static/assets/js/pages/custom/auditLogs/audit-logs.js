"use strict";
let shortenStringLength = 15;
let KTDatatablesDataSourceAjaxServer = function() {

    let initTable1 = function() {
        let nameSearch = $("#member-search");
        let logTypeSearch = $("#log_type_search");
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
                url: '/api/audit-logs/datatables',
                type: 'POST',
                contentType: 'application/json',
                dataSrc: "data",
                data: function(d) {
                    d.beginDate = beginDate.val();
                    d.endDate = endDate.val();
                    return JSON.stringify(d);
                },
            },
            buttons: [
                'print',
                'copyHtml5',
                'excelHtml5',
                'csvHtml5',
                'pdfHtml5',
            ],
            columnDefs: [
                {
                    targets: [3, 4, 5],
                    sortable: false
                },
                {
                    targets: [7],
                    width: 80,
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
                    data: 'logSeq',
                }, {
                    target: 1,
                    title: "LOG TYPE",
                    data: 'logType',
                    render: function (data){
                        return renderLogType(data);
                    }
                },
                {
                    target: 2,
                    title: "MEMBER",
                    data: 'name'
                },
                {
                    target: 3,
                    title: "TARGET",
                    data: 'target',
                    render: function (data){
                        return renderStringWithTooltip(data,shortenStringLength);
                    }
                },
                {
                    target: 4,
                    title: "PARAMETER",
                    data: 'parameter',
                    render: function (data){
                        return renderStringWithTooltip(data,shortenStringLength);
                    }
                },
                {
                    target: 5,
                    title: "COMMENT",
                    data: 'comment',
                    render: function (data){
                        return renderStringWithTooltip(data,shortenStringLength);
                    }
                },
                {
                    target: 6,
                    title: "ACCESS IP",
                    data: 'accessIp',
                },
                {
                    target: 7,
                    title: "TIME",
                    data: 'createdDate',
                    render: function (data){
                        return formatInstantWithSecond(data);
                    }
                },
            ],
        });

        $("#search-button").on('click', function (){
            let nameSearchValue = nameSearch.val();
            table.columns(2).search(nameSearchValue);
            let logTypeSearchValue = logTypeSearch.val();
            table.columns(1).search(logTypeSearchValue);
            table.draw();
        });

        $("#reset-button").on('click', function (){
            nameSearch.val("");
            table.columns(2).search("");
            logTypeSearch.val("");
            table.columns(1).search("");
            endDate.val(getCurrentDateInISOFormat());
            beginDate.val(getOffsetDateInISOFormat(-6));
            table.ajax.reload();
        });

        $('#export_excel').on('click', function(e) {
            e.preventDefault();
            table.button(2).trigger();
        });
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

function renderLogType(logType){
    if (logType === "SIGNIN") return "SIGN IN";
    if (logType === "SIGNOUT") return "SIGN OUT";
    return logType;
}






