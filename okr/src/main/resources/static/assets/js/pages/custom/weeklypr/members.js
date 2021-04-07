"use strict";

let KTDatatablesDataSourceAjaxServer = (function () {
  let $year = $("#year-select");
  let $week = $("#week-select");
  let teamSearch = $("team-search");
  let memberSearch = $("#member-search");

  let initSearch = function () {
    $year.val("");
    // $week.val("");
    $week.html('<option value="">Week</option>');

    teamSearch.val("").change();
    memberSearch.val("").change();
  };

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
      // error(error) {
      // },
    });
  };

  let initTable = function () {
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
          sortable: false,
        },
        {
          targets: [4],
          width: 120,
        },
        {
          targets: [5],
          width: 200,
        },
      ],
      columns: [
        {
          target: 0,
          title: "NAME",
          data: "member.localName",
        },
        {
          target: 1,
          title: "TEAM NAME",
          data: "year",
        },
        {
          target: 2,
          title: "PERIOD",
          data: "beginDate",
          render: function (data, type, full, meta) {
            console.log(full);
            return bindStartDateAndEndDate(full.beginDate, full.endDate);
          },
        },
        {
          target: 3,
          title: "END DATE",
          data: "weekEndDate",
          render: function (data) {
            return formatStringDate(data);
          },
        },
        {
          target: 4,
          title: "ACTION PLAN / REVIEW",
          data: "week",
          // render: function (data, type, full, meta) {
          //   return full.sumActionPlan+"/"+full.sumReview
          // },
        },
        {
          target: 5,
          title: "CHALLENGE",
          data: "challenge",
        },
        {
          target: 6,
          title: "REG DATE",
          data: "createdDate",
          render: function (data) {
            return formatInstant(data, "-");
          },
        },
        {
          target: 7,
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
							    		<li class="nav-item"><a class="nav-link" href="/weekly-prs/details/' +
              data +
              '"><i class="nav-icon la la-eye"></i><span class="nav-text">View PR Details</span></a></li>\
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
    });

    let resetTable = function () {
      initSearch();
      table.columns(0).search("");
      table.columns(1).search("");
      table.ajax.reload();
    };

    $week.on("change", function () {
      if ($year.val() && $week.val()) table.draw();
      else {
        resetTable();
      }
    });

    $year.on("change", function () {
      if (!$year.val()) initSearch();
      getWeekList();
    });

    $("#search-button").on("click", function () {
      let memberSearchValue = memberSearch.val();
      // let teamSearchValue = teamSearch.val();
      table.columns(0).search(memberSearchValue);
      // table.columns(1).search(teamSearchValue);
      table.draw();
    });

    $("#reset-button").on("click", function () {
      resetTable();
    });
  };
  return {
    init: function () {
      initSearch();
      initTable();
    },
  };
})();

$(document).ready(function () {
  KTDatatablesDataSourceAjaxServer.init();
});

function bindStartDateAndEndDate(startDate, endDate) {
  if (startDate !== null || endDate !== null)
    return formatStringDate(startDate) + " ~ " + formatStringDate(endDate);
  else return "";
}
