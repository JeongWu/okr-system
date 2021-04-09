"use strict";

let KTDatatablesDataSource= (function () {
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
      url: "/api/weekly-pr/year",
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
    });
  };

  let initTable = function () {
    let table = $("#kt_datatable").KTDatatable({
      data: {
        type: "remote",
        source: {
          read: {
            url: "/api/weekly-pr/datatables",
          },
        },
        serverPaging: true,
        serverFiltering: false,
        serverSorting: true,
      },
      // layout definition
      layout: {
        scroll: false, 
        footer: false, 
      },

      sortable: false,

      pagination: false,

      search: {
        input: $("#kt_datatable_search_query"),
        key: "generalSearch",
      },

      columns: [
        {
          title: "NAME",
          field: "member.localName",
          width: 80,
        },
        {
          title: "PERIOD",
          field: "beginDate",
          textAlign: "center",
          width: 100,
          template: function (data) {
            if(!data.weeklyPrCard)
            return ""
            return bindStartDateAndEndDate(data.weeklyPrCard.beginDate, data.weeklyPrCard.endDate);
          },
        },
        {
          title: "END DATE",
          field: "weekEndDate",
          textAlign: "center",
          template: function (data) {
            if(!data.weeklyPrCard)
            return ""
            return formatStringDate(data.weeklyPrCard.weekEndDate);
          },
        },
        {
          title: "ACTION PLAN / REVIEW",
          field: "weeklyPrCard.sumActionPlan",
          textAlign: "center",
          template: function (data) {
            if(!data.weeklyPrCard)
            return ""
            return data.weeklyPrCard.sumActionPlan + " / " + data.weeklyPrCard.sumReview;
          },
        },
        {
          title: "CHALLENGE",
          field: "weeklyPrCard.challenge",
          width: 250,
          textAlign: "center",
        },
        {
          title: "REG DATE",
          field: "weeklyPrCard.createdDate",
          textAlign: "center",
          template: function (data) {
            if(!data.weeklyPrCard)
            return ""
            return formatInstant(data.weeklyPrCard.createdDate, "-");
          },
        },
        {
          title: "ACTIONS",
          field: "weeklyPrCard.weeklySeq",
          textAlign: "center",
          template: function (data) {
            if (!data.weeklySeq) return '<button class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
            <i class="flaticon-more-1"></i>\
                          </button>';
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


     memberSearch.on("change keyup paste", function () {
      table.search($(this).val().toLowerCase(), "member.localName");
    });

    teamSearch.on("change", function () {
      $("#kt_datatable_search_query").val($(this).val().toLowerCase()).keyup();
    });

    $week.on("change", function () {
      table.search($year.val().toLowerCase(), "weeklyPrCard.year");
      table.search($week.val().toLowerCase(), "weeklyPrCard.week");
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
  KTDatatablesDataSource.init();
});

function bindStartDateAndEndDate(startDate, endDate) {
  if (startDate !== null || endDate !== null)
    return formatStringDate(startDate) + " ~ " + formatStringDate(endDate);
  else return "";
}
