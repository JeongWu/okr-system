"use strict";

let KTDatatablesDataSourceAjaxServer = (function () {
  let initTable1 = function () {
    let evaluationTypeSearch = $("#evaluation_type_search");
    let beginDate = $("#begin-date");
    let endDate = $("#end-date");
    endDate.val(getCurrentDateInISOFormat());
    beginDate.val(getOffsetDateInISOFormat(-6));

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
        url: "/api/schedules/datatables",
        type: "POST",
        contentType: "application/json",
        dataSrc: "data",
        data: function (d) {
          d.beginDate = beginDate.val();
          d.endDate = endDate.val();
          return JSON.stringify(d);
        },
      },
      columnDefs: [
        {
          targets: [1, 2, 3, 4, 5, 6, 7, 8],
          sortable: false,
          className: "text-center",
        },
        {
          targets: [3, 4, 5, 6, 7],
          width: 120,
        },
      ],
      columns: [
        {
          target: 0,
          title: "#",
          data: "scheduleSeq",
        },
        {
          target: 1,
          title: "EVALUATION TYPE",
          data: "evaluationType",
        },
        {
          target: 2,
          title: "EVALUATION STEP",
          data: "evaluationStep",
          render: function (data) {
            return data === 99 ? "Completion" : data;
          },
        },
        {
          target: 3,
          title: "SELF REVIEW",
          data: "selfReviewBeginDate",
          render: function (data, type, full, meta) {
            return bindStartDateAndEndDate(
              full.selfReviewBeginDate,
              full.selfReviewEndDate
            );
          },
        },
        {
          target: 4,
          title: "1ST EVALUATION",
          data: "firstReviewBeginDate",
          render: function (data, type, full, meta) {
            return bindStartDateAndEndDate(
              full.firstReviewBeginDate,
              full.firstReviewEndDate
            );
          },
        },
        {
          target: 5,
          title: "2ND EVALUATION",
          data: "secondReviewBeginDate",
          render: function (data, type, full, meta) {
            return bindStartDateAndEndDate(
              full.secondReviewBeginDate,
              full.secondReviewEndDate
            );
          },
        },
        {
          target: 6,
          title: "HR REVIEW",
          data: "reviewBeginDate",
          render: function (data, type, full, meta) {
            return bindStartDateAndEndDate(
              full.reviewBeginDate,
              full.reviewEndDate
            );
          },
        },
        {
          target: 7,
          title: "REG DATE",
          data: "createdDate",
          render: function (data) {
            return formatInstantDate(data);
          },
        },
        {
          target: 8,
          title: "ACTIONS",
          data: "scheduleSeq",
          render: function (data, type, full, meta) {
            if (full.evaluationStep === 99) return '<button class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
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
							    		<li class="nav-item"><a class="nav-link" href="/schedule/edit/' +
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
      ],
    });

    $("#search-button").on("click", function () {
      let evaluationTypeSearchValue = evaluationTypeSearch.val();
      table.columns(1).search(evaluationTypeSearchValue);
      table.draw();
    });

    $("#reset-button").on("click", function () {
      evaluationTypeSearch.val("").change();
      table.columns(1).search("");
      endDate.val(getCurrentDateInISOFormat());
      beginDate.val(getOffsetDateInISOFormat(-6));
      table.ajax.reload();
    });
  };
  return {
    init: function () {
      initTable1();
    },
  };
})();

$(document).ready(function () {
  KTDatatablesDataSourceAjaxServer.init();
  $("#begin-date, #end-date").datepicker({
    rtl: KTUtil.isRTL(),
    todayBtn: "linked",
    clearBtn: true,
    todayHighlight: true,
    templates: "arrows",
  });
  $("#evaluation_type_search").selectpicker();
});

function formatStringDate(data) {
  if (data === null) return "";
  let year = data.substring(0, 4);
  let month = data.substring(4, 6);
  let day = data.substring(6, 8);
  return year + "-" + month + "-" + day;
}

function formatInstantDate(instant) {
  if (instant === null) return "";
  let date = new Date(instant);
  let year = date.getFullYear();
  let month = addZeroToTime(date.getMonth() + 1);
  let day = addZeroToTime(date.getDate());
  let hour = addZeroToTime(date.getHours());
  let minute = addZeroToTime(date.getMinutes());
  return year + "-" + month + "-" + day + " " + hour + ":" + minute;
}

function bindStartDateAndEndDate(startDate, endDate) {
  return startDate + " ~ " + endDate;
}
