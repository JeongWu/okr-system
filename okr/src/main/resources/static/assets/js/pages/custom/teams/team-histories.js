"use strict";

let KTDatatablesDataSourceAjaxServer = function() {

    let initTable1 = function() {
		var myId = document.getElementById('param');
		console.log(myId.value);
//		var myDivision = document.getElemetById('divisionList');
//		console.log(myDivision.value);
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
                url: '/api/team-histories/datatables/'+myId.value,
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
                    targets: [7],
                    sortable: false
                },
//                {
//                    targets: [ 4, 5],
//                    width: 120,
//                },
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
                    title: "TEAM",
                    data: 'name'
                },
                {
                    target: 2,
                    title: "DIVISION",
                    data: 'division.name'
                },
                {
                    target: 3,
                    title: "TEAM TYPE",
                    data: 'teamType',
                },
                {
                    target: 4,
                    title: "ACTIVE",
                    data: 'useFlag'
                },
                {
                    target: 5,
                    title: "EDIT MEMBER",
                    data: 'createdBy',
                },
                {
                    target: 6,
                    title: "EDIT DATE",
                    data: 'createdDate',
					render: function (data){
                        return formatInstant(data);
                    }
                },
                {
                    target: 7,
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





