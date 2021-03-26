"use strict";
// Class definition

	
var KTDatatableJsonRemoteDemo = function() {
	
    // basic demo 
    var belong = function() {
        var datatable = $('#member_belong').KTDatatable({
            // datasource definition
            data: {
                type: 'remote',
                source: {
                    read: {
                        url: '/member/get-member-data1',
						params: {
						}
                    },
                },
                pageSize: 10,
            },

            // layout definition
            layout: {
                scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
                footer: false // display/hide footer
            },

            // column sorting
            sortable: false,

            pagination: true,

            search: {
                input: $('#kt_datatable_search_query'),
                key: 'generalSearch'
            },

            // columns definition
            columns: [{
                field: 'team.teamSeq',
                title: '#',
                width: 20,
                textAlign: 'left',
            }, {
                field: 'team.localName',
                title: 'TEAM',
				textAlign: 'center',
            },  {
                field: 'applyBeginDate',
                title: 'START',
 				type: 'String',
				textAlign: 'right',
            },  {
                field: 'applyBeginDate',
				title: 'END',
				type: 'String',
				textAlign: 'left',
            }, 
			{
                field: 'teamManagerFlag',
                title: 'TEAM MANAGER',
				textAlign: 'center',
				template: function(row) {
					var status = {
					'Y': {'title': 'YES', 'class': 'label-light-primary'},
					'N': {'title': 'NO', 'class': ' label-light-danger'},
				};
				return '<span class="label label-inline font-weight-bold label-lg">' + status[row.teamManagerFlag].title + '</span>';
			},
				
				
            },
			{
                field: 'teamLeadFlag',
                title: ' TEAM LEAD ',
				textAlign: 'center',
				template: function(row) {
					var status = {
					'Y': {'title': 'YES', 'class': 'label-light-primary'},
					'N': {'title': 'NO', 'class': ' label-light-danger'},
				};
				return '<span class="label label-inline font-weight-bold label-lg">' + status[row.teamLeadFlag].title + '</span>';
			},
            },
			{
                field: 'applyBeginDate',
                title: 'EDIT DATE',
                type: 'date',
                format: 'MM  /DD / YYYY',
				textAlign: 'center',
            }, 
			{
                field: 'team.justification',
                title: 'Justification',
				textAlign: 'center',
            }

			]

        });

        $('#kt_datatable_search_teamManager').on('change', function() {
            datatable.search($(this).val().toLowerCase(), 'teamManagerFlag');

			datatable.table().draw();
        });

        $('#kt_datatable_search_teamLead').on('change', function() {
            datatable.search($(this).val().toLowerCase(), 'teamLeadFlag');
			datatable.table().draw();
        });

        $('#kt_datatable_search_teamManager, #kt_datatable_search_teamLead').selectpicker();


	$('#reset').on('click', function(e) {
			e.preventDefault();
			$('.datatable-input').each(function() {
				$(this).val('');
				$(this).prop("checked", false);
			});
			location.href = "/member/belong/" + $("#memberSeq").val();
		});
		
    };

	    var history = function() {
		
        var datatable = $('#member_history').KTDatatable({
            // datasource definition
            data: {
                type: 'remote',
                source: {
                    read: {
                        url: '/memberhistory/datatable/' + $("#history_name").val(),
						params: {
						}
                    },
                },
                pageSize: 10,
            },

            // layout definition
            layout: {
                scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
                footer: false // display/hide footer
            },

            // column sorting
            sortable: true,

            pagination: true,

            search: {
                input: $('#kt_datatable_search_query'),
                key: 'generalSearch'
            },

            // columns definition
            columns: [{
                field: 'historySeq',
                title: '#',
                width: 20,
                textAlign: 'left',
            }, {
                field: 'name',
                title: 'NAME',
				width: 20,
				textAlign: 'left',
            },  {
                field: 'position',
                title: 'POSITION',
				textAlign: 'center',
            }, {
                field: 'joiningDate',
                title: 'JOININGDATE',
                type: 'date',
                format: 'MM  /DD / YYYY',
				textAlign: 'center',
            }, 
			{
                field: 'career',
                title: 'CAREER',
				textAlign: 'center',
            },
			{
                field: 'level',
                title: 'LEVEL',
				textAlign: 'center',
            },
			{
                field: 'useFlag',
                title: 'EDIT COMAPNY OKR',
				textAlign: 'center',
            },
			{
                field: 'useFlag',
                title: 'ACTIVE',
				textAlign: 'center',
				template: function(row) {
					var status = {
					'Y': {'title': 'Active', 'class': 'label-light-primary'},
					'N': {'title': 'Inactive', 'class': ' label-light-danger'},
				};
				return '<span class="label ' + status[row.useFlag].class + ' label-inline font-weight-bold label-lg">' + status[row.useFlag].title + '</span>';
			},
            },
			{
                field: 'justification',
                title: 'Justification',
				textAlign: 'center',
            }

			]

        });
    };

    return {
        // public functions
        init: function() {
            history();
			belong();

        }
    };
}();



jQuery(document).ready(function() {
    KTDatatableJsonRemoteDemo.init();

	if ($('input[name=kt_datatable_search_teamManager]').is(":checked")) {
    $('input[name=kt_datatable_search_teamManager]').val('Y');
	} else {
    $('input[name=kt_datatable_search_teamManager]').val('N');
	}


	/*
	$('#hisotry_date').click(function(){
	var from = $("input[name=from]").val();
	var to = $("input[name=to]").val();
	var justification = $("input[name=justification]").val();
	var dateDto = {
		'from' : from,
		'to' : to,
		'justification' : justification
	};
	alert("from : "+dateDto.from+" to : "+ dateDto.to + " js : " + dateDto.justification);


	$.ajax({
	url: '/member/date',
	type: "GET",
	dataType: "json",
	data: dateDto,
	success: function(data) {
		console.log(data);
	}, error: function(request,status,error) {
		alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	}
	});

});*/	

});
