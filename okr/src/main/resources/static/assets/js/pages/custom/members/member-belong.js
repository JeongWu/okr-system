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
                        url: '/api/members/belong/datatables/'+ $('#memberSeq').val(),
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
                field: 'teamMemberId.team.teamSeq',
                title: '#',
                width: 20,
                textAlign: 'left',
            }, {
                field: 'teamMemberId.team.localName',
                title: 'TEAM',
				textAlign: 'center',
            },  {
                field: 'applyBeginDate',
                title: 'PERIOD',
 				type: 'String',
				textAlign: 'center',
                width: 200,
                template: function(row) {
                var output = '';
                var applyBeginDate = row.teamMemberId.applyBeginDate;
                var applyEndDate = row.applyEndDate;
                output =
                '<div class="align-items-center">\
                        <h7> '+ applyBeginDate.substring(0,4) + '-'+ applyBeginDate.substring(4,6) + '-'+ applyBeginDate.substring(6,8) + ' ~ ' + applyEndDate.substring(0,4) + '-'+ applyEndDate.substring(4,6) + '-'+ applyEndDate.substring(6,8)+'</h7> </div>\
                </div>';
                return output;
			},	
            },
			{
                field: 'teamManagerFlag',
                title: 'TEAM MANAGER / LEAD',
				textAlign: 'center',
				template: function(row) {
					var status = {
					'Y': {'title': 'YES',},
					'N': {'title': 'NO',},
				};
				return '<span>' + status[row.teamManagerFlag].title 
                + ' / ' + status[row.teamLeadFlag].title +'</span>';
			},				
            },			{
                
                field: 'teamMemberId.team.updatedDate',
                title: 'Edit',
				textAlign: 'center',
            },
			{
                field: 'justification',
                title: 'Justification',
				textAlign: 'center',
                template: function(row) {
				return '<span >' + row.justification +'</span>';
			},
            }
			]

        });

        $('#kt_datatable_search_teamManager').on('change', function() {
            var n = $(this).val();
             if ($(this).is(':checked')){
                n = 'Y';
            } else {
                n = 'N';
            }
            datatable.search(n.toLowerCase(), 'teamManagerFlag');
        });

        $('#kt_datatable_search_teamLead').on('change', function() {
            var n = $(this).val();
            if ($(this).is(':checked')){
               n = 'Y';
           } else {
               n = 'N';
           }
            datatable.search(n.toLowerCase(), 'teamLeadFlag');
        });

        $('#kt_datatable_search_teamManager, #kt_datatable_search_teamLead').selectpicker();	
        
        $('#reset').on('click', function(e) {
            e.preventDefault();
            $('.datatable-input').each(function() {
                $(this).val('');
                $(this).prop("checked", false);
                datatable.setDataSourceParam('query', {});
                datatable.reload();
            });
        });
    };

    return {
        // public functions
        init: function() {
			belong();
        }
    };
}();

jQuery(document).ready(function() {
    KTDatatableJsonRemoteDemo.init();
});;
