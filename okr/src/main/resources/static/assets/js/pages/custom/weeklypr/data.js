"use strict";

let KTDatatablesDataSourceAjaxServer = (function () {
  let $year = $("#year-select");
  let $week = $("#week-select");
  let teamSearch = $("#team-search");
  let memberSearch = $("#member-search");
  let searchInput = $("#kt_datatable_search_query");

  let initSearch = function () {
    $year.val("");
    $week.html('<option value="">Week</option>');

    teamSearch.val("");
    memberSearch.val("");
    searchInput.val("")
    
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
            url: "/api/weekly-prs/last",
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

      search: {
        input: $("#kt_datatable_search_query"),
        key: "generalSearch",
      },

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
            if(!data.weeklyPRCard)
            return ""
            return bindStartDateAndEndDate(data.weeklyPRCard.beginDate, data.weeklyPRCard.endDate);
          },
        },
        {
          //   target: 3,
          title: "END DATE",
          field: "weekEndDate",
          textAlign: "center",
          template: function (data) {
            if(!data.weeklyPRCard)
            return ""
            return formatStringDate(data.weeklyPRCard.weekEndDate);
          },
        },
        {
          //   target: 4,
          title: "ACTION PLAN / REVIEW",
          field: "weeklyPRCard.sumActionPlan",
          textAlign: "center",
          template: function (data) {
            if(!data.weeklyPRCard)
            return ""
            return data.weeklyPRCard.sumActionPlan + " / " + data.weeklyPRCard.sumReview;
          },
        },
        {
          //   target: 5,
          title: "CHALLENGE",
          field: "weeklyPRCard.challenge",
          width: 250,
          textAlign: "center",
        },
        {
          //   target: 6,
          title: "REG DATE",
          field: "weeklyPRCard.createdDate",
          textAlign: "center",
          // template: function (data) {
          //   return formatInstant(data.createdDate, "-");
          // },
        },
        {
          //   target: 7,
          title: "ACTIONS",
          field: "weeklyPRCard.weeklySeq",
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
      table.setDataSourceParam("query", {});
      table.reload();
    };

    //reset button
    $("#kt_reset").on("click", function (e) {
      e.preventDefault();
      resetTable();
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

     memberSearch.on("change keyup paste", function () {
      table.search($(this).val().toLowerCase(), "member.localName");
    });

    teamSearch.on("change", function () {
      // datatable.search($(this).val().toLowerCase(), 'teamMembers.teamMemberId.team.name');
      $("#kt_datatable_search_query").val($(this).val().toLowerCase()).keyup();
    });

    $week.on("change", function () {
      table.search($year.val().toLowerCase(), "weeklyprcard.year");
      table.search($week.val().toLowerCase(), "weeklyPRCard.week");
    });

    $year.on("change", function () {
      if (!$year.val()) resetTable();
      getWeekList();
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
