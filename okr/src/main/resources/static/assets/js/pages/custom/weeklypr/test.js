"use strict";

let KTDatatablesDataSourceAjaxServer = (function () {
  let $year = $("#year-select");
  let $week = $("#week-select");
  let teamSearch = $("#team-search");
  let memberSearch = $("#member-search");

  let initSearch = function () {
    $year.val("");
    $week.html('<option value="">Week</option>');

    teamSearch.val("");
    memberSearch.val("");
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
    let table = $("#kt_datatable").KTDatatable({
      data: {
        type: "remote",
        source: {
          read: {
            url: "/api/weekly-prs/test",
            // params: {
            //     query: {
            //         year: $year.val(),
            //         week: $week.val()
            //     },
            // },
          },
        },
        serverPaging: true,
        serverFiltering: false,
        serverSorting: true,
      },
      // layout definition
      layout: {
        scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
        footer: false, // display/hide footer
      },

      // column sorting
      sortable: false,

      pagination: false,

      columns: [
        {
          //   target: 0,
          title: "NAME",
          field: "member.localName",
          width: 80,
        },
        // {
        //   //   target: 1,
        //   title: "TEAM NAME",
        //   field: "year",
        //   textAlign: "center",
        //   width: 80,

        // },
        {
          //   target: 2,
          title: "PERIOD",
          field: "beginDate",
          textAlign: "center",
          width: 100,
          template: function (data) {
            return bindStartDateAndEndDate(data.beginDate, data.endDate);
          },
        },
        {
          //   target: 3,
          title: "END DATE",
          field: "weekEndDate",
          textAlign: "center",
          template: function (data) {
            return formatStringDate(data.weekEndDate);
          },
        },
        {
          //   target: 4,
          title: "ACTION PLAN / REVIEW",
          field: "sumActionPlan",
          textAlign: "center",
          template: function (data) {
            return data.sumActionPlan + " / " + data.sumReview;
          },
        },
        {
          //   target: 5,
          title: "CHALLENGE",
          field: "challenge",
          width: 250,
          textAlign: "center",
        },
        {
          //   target: 6,
          title: "REG DATE",
          field: "createdDate",
          textAlign: "center",
          template: function (data) {
            return formatInstant(data.createdDate, "-");
          },
        },
        {
          //   target: 7,
          title: "ACTIONS",
          field: "weeklySeq",
          textAlign: "center",
          template: function (data) {
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
              data.weeklySeq +
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
      datatable.setDataSourceParam("query", {});
    };

    //reset button
    $("#kt_reset").on("click", function (e) {
      initSearch();
    });

    // $("#kt_search").on("click", function (e) {
    //   table.setDataSourceParam("query", {
    //     week: $week.val(),
    //     year: $year.val(),
    //   });
    //   table.search(memberSearch.val().toLowerCase(), "member.localName");
    //   table.reload();
    //   console.log(table.getDataSourceParam("query"));
    // });
    memberSearch.on("change", function () {
      table.search($(this).val().toLowerCase(), "member.localName");
    });

    memberSearch.on("change keyup paste", function () {
      $("#searchFun").val($(this).val().toLowerCase()).keyup();
    });

    $week.on("change", function () {
      if ($year.val() && $week.val()) {
        table.setDataSourceParam("query", {
          week: $week.val(),
          year: $year.val(),
        });
        table.reload();
        console.log(table.getDataSourceParam("query"));
      } else {
        resetTable();
      }
    });

    $year.on("change", function () {
      if (!$year.val()) initSearch();
      getWeekList();
    });

    // $("#search-button").on("click", function () {
    //   let memberSearchValue = memberSearch.val();
    //   // let teamSearchValue = teamSearch.val();
    //   table.columns(0).search(memberSearchValue);
    //   // table.columns(1).search(teamSearchValue);
    //   table.draw();
    // });
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
