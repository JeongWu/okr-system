"use strict";

let KTDatatablesDataSourceAjaxServer = function() {

    let initTable1 = function() {
        let searchBox = $("#member-search");
        let searchBox2 = $("#team-search");
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
                url: '/api/okr-checklist-groups/datatables',
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
                    targets: [1,2,3,4,5,6,7,8,9,10],
                    sortable: false
                }
            ],
            columns: [
                {
                    target: 0,
                    title: "#",
                    data: 'groupSeq',
                },
                {
                    target: 1,
                    title: "TEAM ",
                    data: 'objective.team.name',
                },
                {
                    target: 2,
                    title: "MEMBER",
                    data: 'objective.member.name',
                },
                {
                    target: 3,
                    title: "OBJECTIVE SCORE",
                    data: 'objectiveScore',
                },
                
                {
                    target: 4,
                    title: "KR1 SCORE",
                    data: 'keyResult1Score'
                },
                {
                    target: 5,
                    title: "KR2 SCORE",
                    data: 'keyResult2Score',
                },
                {
                    target: 6,
                    title: "KR3 SCORE",
                    data: 'keyResult3Score',
                },
                {
                    target: 7,
                    title: "KR4 SCORE",
                    data: 'keyResult4Score',
                },
                {
                    target: 8,
                    title: "KR5 SCORE",
                    data: 'keyResult5Score',
                },
                {
                    target: 9,
                    title: "REG DATE",
                    data: 'createdDate',
                    render: function (data){
                        return formatInstant(data);
                    }
                },
                {
                    target: 10,
                    title: "ACTIONS",
                    data: 'groupSeq',
                    render: function (data){
                        return '\
						\
					\
							<div class="dropdown dropdown-inline">\
								<a href="javascript:;" class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
								<i class="flaticon-more-1"></i>\
	                            </a>\
							  	<div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
									<ul class="nav nav-hoverable flex-column">\
							    		<li class="nav-item"><a class="nav-link" href="/checklist-details/'+data+'"><i class="nav-icon la la-eye"></i><span class="nav-text">Checklist Details</span></a></li>\
									\
									</ul>\
							  	</div>\
							</div>\
						\
						';
                    }
                },
            ],
        });
        $("#search-button").on('click', function (){
            let searchValue = searchBox.val();
            let searchValue2 = searchBox2.val();
            table.columns(2).search(searchValue).draw();
            table.columns(1).search(searchValue2).draw();
         
        });
        $("#reset-button").on('click', function (){
            searchBox.val("");
            searchBox2.val("");
            table.columns(2).search("");
            table.columns(1).search("");
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



