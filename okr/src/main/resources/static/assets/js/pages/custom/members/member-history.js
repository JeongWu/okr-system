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
                url: '/api/memberhistory/datatables/' + $('#memberSeq').val(),
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
                    targets: [5, 6, 7],
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
                }, 
                {
                    target: 1,
                    title: "NAME",
                    data: 'name'
                },
                {
                    target: 2,
                    title: "POSITION",
                    data: 'position'
                },
                {
                    target: 3,
                    title: "CAREER",
                    data: 'career'
                },
                {
                    target: 4,
                    title: "JOININGDATE",
                    data: 'joiningDate',
                    render: function (data){
                    var output = '';
                    output =
                    '<div class="align-items-center">\
                            <h7> '+ data.substring(0,4) + '-'+ data.substring(4,6) + '-'+ data.substring(6,8) + '</h7> </div>\
                    </div>';
                        
                        return output;
                    }
                },
                {
                    target: 5,
                    title: "CAREER",
                    data: 'career',
                },
                {
                    target:6,
                    title: "LEVEL",
                    data: 'level',

                },
                {
                    target: 7,
                    title: "JUSTIFICATION",
                    data: 'justification',
                 },
            ],
        });

        $("#search-button").on('click', function (){
            let searchValue = searchBox.val();
            table.columns(7).search(searchValue).draw();
        });
        $("#reset-button").on('click', function (){
            searchBox.val("");
            table.columns(7).search("");
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

function formatStringtoDate(instant){
    if (instant === null) return "";
    let year = instant.subString(0,4);
    let month = instant.subString(4,6);
    let day = instant.subString(6,8);
    return year + "/" + month + "/" + day;
}