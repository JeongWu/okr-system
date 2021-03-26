"use strict";
// Class definition

var KTAppsProjectsListDatatable = (function () {
  // Private functions

  var makeTeamListTable = function () {
    var datatable = $("#kt_datatable").KTDatatable({
      // datasource definition
      data: {
        type: "remote",
        source: {
          read: {
            url: "/api/dictionary/keyResult/datatables",
            // map: function (raw) {
            //   var dataSet = raw;

            //   if (typeof raw.data !== "undefined") {
            //     dataSet = raw.data;
            //   }
            //   console.log(dataSet);
            //   var data = dataSet.map((i) => i.divisionName);
            //   var list = new Set(data);
            //   list.forEach(function (d) {
            //     $('.datatable-input[data-col-index="1"]').append(
            //       '<option value="' + d + '">' + d + "</option>"
            //     );
            //   });
            //   $("#kt_subheader_total").append(dataSet.length + " Total");

            //   return dataSet;
            // },
            // params: {},
          },
        },
        //not using server-side
        // pageSize: 10,
        // serverPaging: false,
        // serverFiltering: false,
        // serverSorting: false,
      },

      // layout definition
      layout: {
        scroll: false, // enable/disable datatable scroll both horizontal and vertical when needed.
        footer: false, // display/hide footer
      },

      // column sorting
      sortable: true,

      pagination: true,

      // columns definition
      columns: [
        {
          field: "dictionarySeq",
          title: "#",
          sortable: "asc",
          width: 20,
          type: "number",
          selector: false,
          textAlign: "left",
          template: function (data) {
            return (
              '<span class="font-weight-bolder">' + data.dictionarySeq + "</span>"
            );
          },
        },
        {
          field: "jobType",
          title: "JOB",
          width: 90,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.jobType +
              "</div>";

            return output;
          },
        },

        {
          field: "sentence",
          title: "KEY RESULT",
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.sentence +
              "</div>";

            return output;
          },
        },
       
        {
          field: "taskType",
          title: "",
          sortable: false,
          width: 70,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.taskType +
              "</div>";

            return output;
          },
        },
        {
          field: "taskMetric",
          title: "PROPERTIES",
          sortable: false,
          width: 90,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.taskMetric +
              "</div>";

            return output;
          },
        },
        {
          field: "taskIndicator",
          title: "",
          sortable: false,
          width: 70,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.taskIndicator +
              "</div>";

            return output;
          },
        },
      
        {
          field: "Actions",
          title: "Actions",
          sortable: false,
          width: 70,
          overflow: "visible",
          // autoHide: false,
          template: function (data) {
            // var id = data.teamSeq;
            return (
              '\
                    <div class="dropdown dropdown-inline">\
                    <a href="javascript:;" class="btn btn-sm btn-light btn-text-primary btn-icon mr-2" data-toggle="dropdown">\
                    <i class="flaticon-more-1"></i>\
                </a>\
                      </div>\
                    '
            );
          },
        },
      ],
    });


    //realtime search in the view
    // $("#kt_datatable_search_name").on("propertychange change keyup paste input", function () {
    $("#kt_datatable_search_sentence").on("change keyup paste", function () {
      // console.log($(this).val());
      datatable.search($(this).val().toLowerCase(), "sentence");
    });

    $("#kt_datatable_search_jobType").on("change", function () {

      datatable.search($(this).val().toLowerCase(), "jobType");
    });

    // $("#kt_datatable_search_type").on("change", function () {
    //   datatable.search($(this).val().toLowerCase(), "teamType");
    // });
    // $("#kt_datatable_search_active").on("change", function () {
    //   datatable.search($(this).val().toLowerCase(), "useFlag");
    // });

    $(
      "#kt_datatable_search_division, #kt_datatable_search_type",
      "#kt_datatable_search_active"
    ).selectpicker();

    //search button-not neccessary(search directly in the view)
    $("#kt_search").on("click", function (e) {
      // $(".datatable-input").each(function () {
      //   datatable
      //     .column($(this).data("col-index"))
      //     .search($(this).val().toLowerCase(), $(this).attr("name"));
      // });
    });

    //reset button
    $("#kt_reset").on("click", function (e) {
      e.preventDefault();

      datatable.setDataSourceParam("query", {});
      $(".datatable-input").each(function () {
        $(this).val("").change();
      });
      // datatable.reload(); //not reload for server-side
    });

  };

  return {
    // public functions
    init: function () {
      makeTeamListTable();
    },
  };
})();

jQuery(document).ready(function () {
  KTAppsProjectsListDatatable.init();
});
