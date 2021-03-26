"use strict";
// Class definition

var KTDefaultDatatableDemo = function() {
	// Private functions

	var checklists = function() {

		var options = {			
			data: {
                type: 'remote',
                source: {
                    read : {
                        url : '/api/checklists/datatables',
                    }
                },
                pageSize: 10,
            },
	
			// layout definition
			layout: {
				scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed. // datatable's body's fixed height
				footer: false, // display/hide footer
			},

			pagination: true,

			search: {
				input: $('#kt_datatable_search_query'), 
				key: 'generalSearch'
			},

			// columns definition
			columns: [
				 {
					field: 'checkListSeq',
					title: '#',
					width: 30,


				}, {
					field: 'type',
					title: 'TYPE',	
					width: 100,
					template: function(row) {
						var TYPE = {
							OBJECTIVE: {'title': 'OBJECTIVE'},
							KEYRESULT: {'title': 'KEY RESULT'}
						};
						return '<span class="font-weight-bold">' +
                        TYPE[row.type].title + '</span>';
					},

				},
                {
					field: 'question',
					title: 'QUESTION',
					width: 500,
					
				},
                {
					field: 'useFlag',
					title: 'ACTIVE',
                    textAlign : 'right',
                    width: 100,
					// callback function support for column rendering
					template: function(row) {
						var output = '';
                        var useFlag = row.useFlag;
						if (row.useFlag == 'Y'){
							return '<div class="col-3 ml-10">\
							<span class="switch switch-icon">\
							 <label>\
							  <input type="checkbox" name="'+row.checkListSeq+'" checked="checked" id="useFlag'+row.checkListSeq+'"/>\
							  <span ></span>\
							 </label>\
							</span>\
						   </div>\
						  </div>';
						}
						else{
							return '<div class="col-3 ml-10">\
							<span class="switch switch-icon">\
							 <label>\
							  <input type="checkbox" name="'+row.checkListSeq+'" id="useFlag'+row.checkListSeq+'"/>\
							  <span ></span>\
							 </label >\
							</span>\
						   </div>\
						  </div>';
						}				
					},
				},],

		};
	
		var datatable = $('#kt_checklist').KTDatatable(options);

		//Reset button
		$('#kt_reset').on('click', function(e) {
			e.preventDefault();
			$('.datatable-input').each(function() {
				$(this).val('');
			});
			datatable.setDataSourceParam('query', {});
			datatable.reload();
		});

		//Search function
		$('#kt_datatable_search_okr_active').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'useFlag');
		});
		
		$('#kt_datatable_search_okr_type').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'type');			
		});

		$(document).on('change', "[id^='useFlag']", function(){
            var useFlag = $(this).val(); // Y, N
            var checkListSeq = $(this).attr('name'); // checkListSeq
             if ($(this).is(':checked')){
                useFlag = 'Y';
            } else {
                useFlag = 'N';
            }
            var dto = {
                'checkListSeq' : checkListSeq,
                'useFlag' : useFlag
            };
            $.ajax({
                url: "/checklists/update",
                type: "POST",
                cache: false,
                dataType: "json",
                data: dto,
                success: function(data) {
                    swal({
						text: "변경되었습니다!",
						icon: "success",
						button: "확인",
					  }).then(function(){
						$('#kt_checklist').reload();
						// location.href="/checklists";    
					  });
                        
                },
                error: function(err) {
					swal({
						text: "실패하였습니다!",
						icon: "warning",
						button: "확인",
					  })
                    console.log(err);
                }                    
    });
});

	};

	return {
		// public functions
		init: function() {
			checklists();			
		},
	};
}();


jQuery(document).ready(function() {
	KTDefaultDatatableDemo.init();
});