"use strict";
let $year = $("#year-select");
let $week = $("#week-select");

let KTDatatablesDataSourceAjaxServer = (function () {
  let initSearch = function () {
    $year.val("");
    $week.val("");
  };

  let initTable = function () {
    let teamSearch = $("team-search");
    let memberSearch = $("#member-search");

    teamSearch.val("");
    memberSearch.val("");

    let table = $("#kt_datatable").DataTable({
      dom: `<'row'<'col-sm-12'tr>>
			<'row'<'col-sm-12 col-md-5 'p><'col-sm-12 col-md-7 dataTables_pager'li>>`,
      responsive: true,
      searchDelay: 500,
      processing: true,
      serverSide: true,
      autoWidth: false,
      language: {
        lengthMenu: "_MENU_",
        info: "Showing _START_ - _END_ of _TOTAL_",
        infoFiltered: "",
        loadingRecords: "&nbsp;",
        processing:
          '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> ',
      },
      ajax: {
        url: "/api/weekly-prs/datatables",
        type: "POST",
        contentType: "application/json",
        dataSrc: "data",
        data: function (d) {
          d.year = $year.val();
          d.week = $week.val();
          return JSON.stringify(d);
        },
      },
      columnDefs: [
        {
          targets: "_all",
          className: "text-center",
        },
      ],
      columns: [
        {
          target: 0,
          title: "NAME",
          data: "memberName",
        },
        {
          target: 1,
          title: "TEAM NAME",
          data: "teamName",
        },
        {
          target: 2,
          title: "PERIOD",
          data: "beginDate",
          render: function (data, type, full, meta) {
            return bindStartDateAndEndDate(
              full.beginDate,
              full.endDate
            );
          },
        },
        {
          target: 3,
          title: "END DATE",
          data: "weeklyEndDate",
        },
        {
          target: 4,
          title: "SELF REVIEW",
          data: "weeklyEndDate",
        },
        {
          target: 5,
          title: "ACTION PLAN / REVIEW",
          data: "weeklyEndDate",
          render: function (data, type, full, meta) {
            return full.actionPlan+"/"+full.review
          },
        },
        {
          target: 6,
          title: "CHALLENGE",
          data: "challenge",
        },
        {
          target: 7,
          title: "REG. DATE",
          data: "createdDate",
          render: function (data) {
            return formatInstant(data,"-");
          },
        },
        {
          target: 8,
          title: "ACTIONS",
          data: "weeklySeq",
          render: function (data) {
            return (
              '\
						\
					\
							<div class="dropdown dropdown-inline">\
								<button class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
								<i class="flaticon-more-1"></i>\
	                            </button>\
							  	<div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
									<ul class="nav nav-hoverable flex-column">\
							    		<li class="nav-item"><a class="nav-link" href="/evaluation-schedules/edit/' +
              data +
              '"><i class="nav-icon la la-edit"></i><span class="nav-text">Edit Schedule</span></a></li>\
									\
									</ul>\
							  	</div>\
							</div>\
						\
						'
            );
          },
        },
      ]
    });
  };
  return {
    init: function () {
      initSearch();
      // initTable();
    },
  };
})();

let getWeekList = function () {
  $.ajax({
    url: "/api/weekly-prs/year",
    type: "POST",
    data: {
      year: $year.val(),
    },
    success(result) {
      console.log(result);
      result.weeks.forEach(function (d) {
        $week.append('<option value="' + d + '">' + d + "</option>");
      });
    },
    error(error) {
      let message = error.responseJSON.message;
      if (message === null || message === undefined)
        Swal.fire("Error!", "Some errors occur while loading chart", "error");
      else Swal.fire("Error!", message, "error");
    },
  });
};

$(document).ready(function () {
  KTDatatablesDataSourceAjaxServer.init();
  $year.on("change", getWeekList);
});

// function bindStartDateAndEndDate(startDate, endDate) {
//   if (startDate !== null || endDate !== null)
//     return formatStringDate(startDate) + " ~ " + formatStringDate(endDate);
//   else return "";
// }
