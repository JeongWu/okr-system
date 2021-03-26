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
            sortable: false,
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
                    targets: [0,1,2,3,4,5,6,7,8],
                    sortable: false
                },
                {
                    targets: [6],
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
                    title: "MEMBER",
                    data: 'name'
                },
                {
                    target: 2,
                    title: "POSITION",
                    data: 'position'
                },
                {
                    target: 3,
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
                    target: 4,
                    title: "CAREER",
                    data: 'career',

                },
                {
                    target: 5,
                    title: "LEVEL",
                    data: 'level',
                },
                {
                    target:6,
                    title: "EDIT COMPANY OKR",
                    data: 'editCompanyOkrFlag',
                    render : function(data){
                        var output = '';

						if (data == 'Y'){
							output = '<div class="col-3 ">\
							<span class="switch switch-icon align-items-center">\
							 <label>\
							  <input type="checkbox" checked="checked"  disabled="disabled" />\
							  <span></span>\
							 </label>\
							</span>\
						   </div>\
						  </div>';
						}
						else{
							output = '<div class="col-3 align-items-center">\
							<span class="switch switch-icon">\
							 <label>\
							  <input type="checkbox"  disabled="disabled" />\
							  <span></span>\
							 </label>\
							</span>\
						   </div>\
						  </div>';
						}
						return output;	
					}
                },
                {
                    target:7,
                    title: "ACTIVE",
                    data: 'useFlag',
                    render: function(data){
                        var Active = {
							Y: {'title': 'Active'},
							N: {'title': 'Inactive'},
						};
						return '<span class="font-weight-bold">' +
							Active[data].title + '</span>';
                    }
                },
                {
                    target: 8,
                    title: "JUSTIFICATION",
                    data: 'justification',
                    render : function(data) {
                        var output = ''
                        output = '<span data-toggle="tooltip" data-placement="top" data-trigger="focus"\
                        title="'+data+'"><span class="d-inline-block text-truncate" style="max-width: 150px;">'+data+'</span></span>';
                        return output;
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

function formatStringtoDate(instant){
    if (instant === null) return "";
    let year = instant.subString(0,4);
    let month = instant.subString(4,6);
    let day = instant.subString(6,8);
    return year + "/" + month + "/" + day;
}