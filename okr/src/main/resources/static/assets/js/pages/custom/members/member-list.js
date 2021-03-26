"use strict";
// Class definition

var KTDefaultDatatableDemo = function() {
	// Private functions

	var demo = function() {

		var options = {
			
			data: {
                type: 'remote',
                source: {
                    read : {
                        url : 'get-member-data',
                    }
                },
                pageSize: 10,
            },
	

			// layout definition
			layout: {
				scroll: true, // enable/disable datatable scroll both horizontal and vertical when needed.
				height: 550, // datatable's body's fixed height
				footer: false, // display/hide footer
			},

			// column sorting
			sortable: true,

			pagination: true,

			search: {
				input: $('#kt_datatable_search_query'), 
				key: 'generalSearch'
			},

			// columns definition
			columns: [
				 {
					field: 'memberSeq',
					title: '#',
					width: 30,
				}, {
					field: 'name',
					title: 'Member',	
					width: 100,
					template: function (data) {
					  var output = "";
					  var stateNo = KTUtil.getRandomInt(0, 7);
					  var states = [
						"success",
						"primary",
						"danger",
						"success",
						"warning",
						"dark",
						"primary",
						"info",
					  ];
					  var state = states[stateNo];
		  
					  output =
						'<div class="d-flex align-items-center">\
								  <div class="symbol symbol-40 symbol-' +
						state +
						' flex-shrink-0">\
									  <div class="symbol-label">' +
						data.name.substring(0, 1) +
						'</div>\
								  </div>\
								  <div class="ml-2">\
									  <div class="text-dark-75 font-weight-bold line-height-sm">' +
						data.name +
						"</div>\
								  </div>\
							  </div>";
		  
					  return output;
					},

				},{
					field: 'position',
					title: 'Position',
				},
			
				 {
					field: 'teamName',
					title: 'Belong To',
					template: function(row){
						var number = 7;
						var user_img = '100_' + number + '.jpg';
						var output = '';
						var teamMember = row.teamMember;
						output =
							'<div class="d-flex align-items-center">\
						  <div class="symbol-group symbol-hover">';
	
							if (teamMember.length > 4) {
								teamMember.forEach((item, index) => {
								  if (index < 4) {

									output +=
									  '<div class="symbol symbol-30 symbol-circle" data-toggle="tooltip" title="' +
									  item.teamName +
									  '">\
									  <a href="/team?id='+item.teamName+'">\
								  <img class="" src="' +
									  item.teamImage +
									  '" alt="photo">\
									  </a><br\>\
									  <h9>'+item.teamName+'</h9>\
								  </div>';

								  }
								});
								output +=
								  ' <div class="symbol symbol-30 symbol-circle symbol-light">\
								<span class="symbol-label font-weight-bold"> +' +
								  (teamMember.length - 4) +
								  "</span>\
									</div>\
									</div>\
					  			</div>";}

							else {
							teamMember.forEach((item) => {
								output +=
								'<div class="symbol symbol-30 symbol-circle" data-toggle="tooltip" title="' +
								item.teamName +
								'">\
								<a href="/team?id='+item.teamName+'">\
								<img class="" src="' +
								item.teamImage +
								'" alt="photo">\
								</a><br\>\
								<h9>'+item.teamName+'</h9>\
								</div>';
							});
				
							output += "</div>\
							</div>";
							}


							return output;
						},
					},
						

			

				{
					field: 'joiningDate',
					title: 'Joining Date',
					
				},{
					field: 'career',
					title: 'Career (Month)',
				},{
					field: 'level',
					title: 'Level',
				},
				{
					field: 'editCompanyOkrFlag',
					title: 'Edit Company OKR',
					autoHide: false,
					// callback function support for column rendering
					template: function(row) {
						var output = '';

						if (row.editCompanyOkrFlag == 'Y'){
							output = '<div class="col-3">\
							<span class="switch switch-icon">\
							 <label>\
							  <input type="checkbox" checked="checked"  disabled="disabled" />\
							  <span></span>\
							 </label>\
							</span>\
						   </div>\
						  </div>';
						}
						else{
							output = '<div class="col-3">\
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
				
					},
				}, 
				
				
				
				{
					field: 'active',
					title: 'Active',
					autoHide: false,
					// callback function support for column rendering
					template: function(row) {
						var Active = {
							Y: {'title': 'Y', 'state': 'primary'},
							N: {'title': 'N', 'state': 'danger'},
						};
						return '<span class="label label-' + Active[row.useFlag].state + ' label-dot mr-2"></span><span class="font-weight-bold text-' + Active[row.useFlag].state + '">' +
							Active[row.useFlag].title + '</span>';
					},
				}, {
					field: 'Actions',
					title: 'Actions',
					sortable: false,
					width: 125,
					overflow: 'visible',
					autoHide: false,
					template: function() {
						return '\
						\
					\
							<div class="dropdown dropdown-inline">\
								<a href="javascript:;" class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
								<i class="flaticon-more-1"></i>\
	                            </a>\
							  	<div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
									<ul class="nav nav-hoverable flex-column">\
							    		<li class="nav-item"><a class="nav-link" href="/edit-details"><i class="nav-icon la la-edit"></i><span class="nav-text">Edit Details</span></a></li>\
							    		<li class="nav-item"><a class="nav-link" href="/view-history"><i class="nav-icon la la-leaf"></i><span class="nav-text">View History</span></a></li>\
									\
									</ul>\
							  	</div>\
							</div>\
						\
						';
					},
				}],

		};

		var datatable = $('#kt_datatable').KTDatatable(options);

		// both methods are supported
		// datatable.methodName(args); or $(datatable).KTDatatable(methodName, args);

		$('#kt_datatable_destroy').on('click', function() {
			// datatable.destroy();
			$('#kt_datatable').KTDatatable('destroy');
		});

		$('#kt_datatable_init').on('click', function() {
			datatable = $('#kt_datatable').KTDatatable(options);
		});

		$('#kt_datatable_reload').on('click', function() {
			// datatable.reload();
			$('#kt_datatable').KTDatatable('reload');
		});

		$('#kt_datatable_sort_asc').on('click', function() {
			datatable.sort('Career', 'asc');
		});

		$('#kt_datatable_sort_desc').on('click', function() {
			datatable.sort('Career', 'desc');
		});

		// get checked record and get value by column name
		$('#kt_datatable_get').on('click', function() {
			// select active rows
			datatable.rows('.datatable-row-active');
			// check selected nodes
			if (datatable.nodes().length > 0) {
				// get column by field name and get the column nodes
				var value = datatable.columns('CompanyName').nodes().text();
				console.log(value);
			}
		});

		// record selection
		$('#kt_datatable_check').on('click', function() {
			var input = $('#kt_datatable_check_input').val();
			datatable.setActive(input);
		});

		$('#kt_datatable_check_all').on('click', function() {
			// datatable.setActiveAll(true);
			$('#kt_datatable').KTDatatable('setActiveAll', true);
		});

		$('#kt_datatable_uncheck_all').on('click', function() {
			// datatable.setActiveAll(false);
			$('#kt_datatable').KTDatatable('setActiveAll', false);
		});

		$('#kt_datatable_hide_column').on('click', function() {
			datatable.columns('JoiningDate').visible(false);
		});

		$('#kt_datatable_show_column').on('click', function() {
			datatable.columns('JoiningDate').visible(true);
		});

		$('#kt_datatable_remove_row').on('click', function() {
			datatable.rows('.datatable-row-active').remove();
		});


		//Excel Dowload Function
		$("#btnExport").click(function () {
			$("#kt_datatable").table2excel({
				name: "Worksheet Name",
				filename: "member",
				fileext: ".xls"
			});
		});
		$(function() {
				$("btnExport").click(function(e){
					var table = $(this).prev('kt_datatable');
					if(table && table.length){
						var preserveColors = (table.hasClass('table2excel_with_colors') ? true : false);
						$(table).table2excel({
							exclude: ".noExl",
							name: "Excel Document Name",
							filename: "myFileName" + new Date().toISOString().replace(/[\-\:\.]/g, "") + ".xls",
							fileext: ".xls",
							exclude_img: true,
							exclude_links: true,
							exclude_inputs: true,
							preserveColors: preserveColors
						});
					}
				});
				
			});

		

		//Reset button
		$('#kt_reset').on('click', function(e) {
			e.preventDefault();
			$('.datatable-input').each(function() {
				$(this).val('');
			});
			datatable.setDataSourceParam('query', {});
			datatable.reload();
		});
	



		$('#kt_datatable_search_level').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'level');
		});
		
		$('#kt_datatable_search_company_okr').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'editCompanyOkrFlag');
		});
		

		$('#kt_datatable_search_active').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'useFlag');
		});

		$('#kt_datatable_search_team').on('change', function() {
			datatable.search($(this).val().toLowerCase(), 'teamMember.0.teamName', 'teamMember.1.teamName');
		});

		$('#kt_datatable_search_level, #kt_datatable_search_active','#kt_datatable_search_team','#kt_datatable_search_company_okr').selectpicker();

	};

	return {
		// public functions
		init: function() {
			demo();
			
		},
	};
}();







jQuery(document).ready(function() {
	KTDefaultDatatableDemo.init();

	
});
