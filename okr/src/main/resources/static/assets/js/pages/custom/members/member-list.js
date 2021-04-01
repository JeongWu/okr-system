"use strict";
// Class definition

var KTDefaultDatatableDemo = (function () {
  // Private functions

  var demo = function () {
    var options = {
      data: {
        type: "remote",
        source: {
          read: {
            url: "/api/members/datatables",
          },
        },
        pageSize: 10,
      },

      // // layout definition
      // layout: {
      // 	scroll: true, // enable/disable datatable scroll both horizontal and vertical when needed.
      // 	height: 550, // datatable's body's fixed height
      // 	footer: false, // display/hide footer
      // },

      // column sorting
      sortable: true,

      pagination: true,

      search: {
        input: $("#kt_datatable_search_query"),
        key: "generalSearch",
      },

      // columns definition
      columns: [
        {
          field: "memberSeq",
          title: "#",
          width: 30,
        },
        {
          field: "name",
          title: "Member",
          width: 100,
          template: function (row) {
            var output = "";

            var memberImg = row.image;
            var memberName = row.localName;

            output = '<div class="d-flex align-items-center">';
            output += makeImageSymbol(row, "big");

            output +=
              '<div class="ml-2">\
						  <div class="text-dark-75 font-weight-bold line-height-sm">' +
              memberName +
              "</div>\
							</div>\
						</div>";

            return output;
          },
        },
        {
          field: "position",
          title: "Position",
          width: 70,
          textAlign: "center",
          template: function (row) {
            var output = "";
            var memberPosition = row.position;
            output =
              '<div class="align-items-center">\
								<h7>' +
              memberPosition +
              "</h7> </div>\
						</div>";
            return output;
          },
        },

        {
          field: "teamMembers.teamMemberId.team.name",
          title: "Belong To",
          width: 200,
          textAlign: "center",
          template: function (row) {
            var output = "";
            // var temp= '';
            let visibleImages = 4;
            var arrName = [];
            var arrImage = [];
            var arrSeq = [];
            var columnTotal = row.teamMembers.length;
            console.log(row.teamMembers);
            // console.log(row.teamMembers.teamMemberId)

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

            for (let i = 0; i < columnTotal; i++) {
              var teamMembers = row.teamMembers[i].teamMemberId.team;
              // temp = teamMembers.name;
              arrName.push(teamMembers.name);
              arrImage.push(teamMembers.image);
              arrSeq.push(teamMembers.teamSeq);
            }

            // for (let i = 0; i<columnTotal; i++){
            // 	var teamMembers = row.teamMembers[i].teamMemberId.team
            // 		temp = teamMembers.image;
            // 		arrImage.push(temp);
            // }

            output =
              '<div class="d-flex align-items-center">\
							<div class="symbol-group symbol-hover">';

            if (columnTotal <= visibleImages) {
              for (let i = 0; i < columnTotal; i++) {
                let data = { name: arrName[i], image: arrImage[i] };
                output += makeImageSymbol(
                  data,
                  "big",
                  "circle",
                  "/teams/list?teamSeq",
                  arrSeq[i]
                );
              }
              output += "</div>\
							</div>";
              // for (let i = 0; i < columnTotal; i++){
              // 	if(arrImage[i] == null){
              // 		var stateNo = KTUtil.getRandomInt(0, 7);
              // 		var state = states[stateNo];
              // 		output +=
              // 			'<div class="symbol symbol-40 symbol-circle symbol-light-'+state+' data-toggle="tooltip" title="'+arrName[i] +'" >\
              // 			<span class="symbol-label font-size-h4">' + arrName[i].substring(0, 1) + '</span>\
              // 			</a>\
              // 			</div>';
              // 	}
              // 	else{

              // 		output +='<div class="symbol symbol-40 symbol-circle" data-toggle="tooltip" title="' +arrName[i] +'">\
              // 						<img class="" src="/assets/media/image/' + arrImage[i] +'.jpg" alt="photo">\
              // 				</div>';
              // 	}
              // }
            } else {
              for (let i = 0; i < visibleImages; i++) {
                let data = { name: arrName[i], image: arrImage[i] };
                output += makeImageSymbol(
                  data,
                  "big",
                  "circle",
                  "/teams/list?teamSeq",
                  arrSeq[i]
                );
              }

            //   output += makeNumberSymbol(
            //     "big",
            //     "circle",
            //     columnTotal - visibleImages
            //   );

			
            //   output += "</div>\
			// 				</div>";

              output +='<button class="symbol symbol-40 symbol-circle symbol-light btn btn-sm p-0" data-toggle="dropdown">\
              	<span class="symbol-label font-weight-bold"> +' +(columnTotal - visibleImages)+'</span>\
              </button>\
              <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
              					<ul class="nav nav-hoverable flex-column">';

              for (let i = visibleImages; i <columnTotal; i++){
              	if(arrImage[i] == null){
              		var stateNo = KTUtil.getRandomInt(0, 7);
              		var state = states[stateNo];
              		output += '<li class="nav-item">\
					  <a class="nav-link" href="/teams/list?teamSeq='+arrSeq[i]+'">\
              							<div class="symbol symbol-40 symbol-circle symbol-light-'+state+' data-toggle="tooltip" title="'+arrName[i] +'" >\
              							<span class="symbol-label font-size-h4">'+ arrName[i].substring(0, 1) +'</span>\
              							</div>\
              							<span class="nav-text">'+arrName[i]+'</span>\
										  </a>\
              					</li>';
              	}
              	else{
					output +='<li class="nav-link">\
					<div class="symbol symbol-40 symbol-circle" >\
						<img src="/assets/media/image/' + arrImage[i] +'.jpg" alt="photo">\
					</div> &nbsp &nbsp\
					<a class="" href="/teams/list?teamSeq='+arrSeq[i]+'">\
							<span class="nav-text">'+arrName[i]+'</span>\
					</a>\
			</li>';
              	}

              }
              output+='</ul>\
              	</div>\
              	</div>\
              	</div>';
            }
            return output;
          },
        },
        {
          field: "joiningDate",
          title: "Joining Date",
          textAlign: "center",
          template: function (row) {
            var output = "";
            var memberJoiningDate = row.joiningDate;
            output =
              '<div class="align-items-center">\
								<h7> ' +
              memberJoiningDate.substring(0, 4) +
              "년 " +
              memberJoiningDate.substring(4, 6) +
              "월 " +
              memberJoiningDate.substring(6, 8) +
              "일</h7> </div>\
						</div>";
            return output;
          },
        },
        {
          field: "career",
          title: "Career (Month)",
          textAlign: "center",
          template: function (row) {
            var output = "";
            var memberCareer = row.career;
            output =
              '<div class="align-items-center">\
								<h7>' +
              memberCareer +
              "</h7> </div>\
						</div>";
            return output;
          },
        },
        {
          field: "level",
          title: "Level",
          textAlign: "center",
          width: 40,
          template: function (row) {
            var output = "";
            var memberLevel = row.level;
            output =
              '<div class="align-items-center">\
								<h7>' +
              memberLevel +
              "</h7> </div>\
						</div>";
            return output;
          },
        },
        {
          field: "editCompanyOkrFlag",
          title: "Edit Company OKR",
          autoHide: false,
          // callback function support for column rendering
          template: function (row) {
            var output = "";

            if (row.editCompanyOkrFlag == "Y") {
              output =
                '<div class="col-3">\
							<span class="switch switch-icon">\
							 <label>\
							  <input type="checkbox" checked="checked"  disabled="disabled" />\
							  <span></span>\
							 </label>\
							</span>\
						   </div>\
						  </div>';
            } else {
              output =
                '<div class="col-3">\
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
          field: "active",
          title: "Active",
          autoHide: false,
          template: function (row) {
            var Active = {
              Y: { title: "Active", state: "primary" },
              N: { title: "Inactive", state: "danger" },
            };
            return (
              '<span class="label label-' +
              Active[row.useFlag].state +
              ' label-dot mr-2"></span><span class="font-weight-bold text-' +
              Active[row.useFlag].state +
              '">' +
              Active[row.useFlag].title +
              "</span>"
            );
          },
        },
        {
          field: "Actions",
          title: "Actions",
          textAlign: "center",
          width: 75,
          autoHide: false,
          template: function (row) {
            return (
              '\
						\
					\
							<div class="dropdown dropdown-inline">\
								<a href="javascript:;" class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
								<i class="flaticon-more-1"></i>\
	                            </a>\
							  	<div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
									<ul class="nav nav-hoverable flex-column">\
							    		<li class="nav-item"><a class="nav-link" href="/members/edit/' +
              row.memberSeq +
              '"><i class="nav-icon la la-edit"></i><span class="nav-text">Edit Details</span></a></li>\
							    		<li class="nav-item"><a class="nav-link" href="/member-histories/' +
              row.memberSeq +
              '"><i class="nav-icon la la-eye"></i><span class="nav-text">View History</span></a></li>\
									\
									</ul>\
							  	</div>\
							</div>\
						\
						'
            );
          },
        },
      ],
    };

    var datatable = $("#kt_datatable").KTDatatable(options);

    $("#kt_datatable_destroy").on("click", function () {
      // datatable.destroy();
      $("#kt_datatable").KTDatatable("destroy");
    });

    $("#kt_datatable_init").on("click", function () {
      datatable = $("#kt_datatable").KTDatatable(options);
    });

    $("#kt_datatable_reload").on("click", function () {
      // datatable.reload();
      $("#kt_datatable").KTDatatable("reload");
    });

    //Excel Dowload Function
    $("#btnExport").click(function () {
      $("#kt_datatable").table2excel({
        name: "Worksheet Name",
        filename: "Member",
        fileext: ".xls",
      });
    });

    //Reset button
    $("#kt_reset").on("click", function (e) {
      e.preventDefault();
      $(".datatable-input").each(function () {
        $(this).val("");
      });
      datatable.setDataSourceParam("query", {});
      datatable.reload();
    });

    $("#kt_datatable_search_name").on("change keyup paste", function () {
      datatable.search($(this).val().toLowerCase(), "name");
    });

    //Search function
    $("#kt_datatable_search_level").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "level");
    });

    $("#kt_datatable_search_company_okr").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "editCompanyOkrFlag");
      console.log($(this).val().toLowerCase());
    });

    $("#kt_datatable_search_active").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "useFlag");
    });

    $("#kt_datatable_search_team").on("change", function () {
      // datatable.search($(this).val().toLowerCase(), 'teamMembers.teamMemberId.team.name');
      $("#kt_datatable_search_query").val($(this).val().toLowerCase()).keyup();
    });

    $(
      "#kt_datatable_search_level, #kt_datatable_search_active",
      "#kt_datatable_search_team",
      "#kt_datatable_search_company_okr"
    ).selectpicker();
  };

  return {
    // public functions
    init: function () {
      demo();
    },
  };
})();

jQuery(document).ready(function () {
  KTDefaultDatatableDemo.init();
});
