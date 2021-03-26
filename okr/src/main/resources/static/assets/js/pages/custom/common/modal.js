var KTDatatableModalForKeyResult = (function () {
 
  var initSubDatatable = function () {
    var el = $("#kt_datatable_sub");
    var datatable = el.KTDatatable({
      data: {
        type: "remote",
        source: {
          read: {
            url: "/api/dictionary/keyResult/datatables",
            map: function (raw) {
              var dataSet = raw;

              if (typeof raw.data !== "undefined") {
                dataSet = raw.data;
              }
              console.log(dataSet);
              var data = dataSet.map((i) => i.jobTypeCodeName);
              var list = new Set(data);
              list.forEach(function (d) {
                $('#kt_datatable_search_jobType').append(
                  '<option value="' + d + '">' + d + "</option>"
                );
              });
              $("#kt_datatable_search_jobType").selectpicker();

              return dataSet;
            },
          },
        },
        pageSize: 10, // display 20 records per page
        serverPaging: true,
        // serverFiltering: true,
        serverSorting: true,
      },

      // layout definition
      layout: {
        theme: "default",
        scroll: false,
        height: 350,
        footer: false,
      },

      sortable: true,

      // columns definition
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
          template: function (data) {
            return (
              '<span class="font-weight-bolder">' +
              data.dictionarySeq +
              "</span>"
            );
          },
        },
        {
          field: "jobType",
          title: "JOB",
          textAlign: "center",
          width: 70,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.jobTypeCodeName +
              "</div>";

            return output;
          },
        },

        {
          field: "sentence",
          title: "KEY RESULT",
          textAlign: "center",
          width: 300,
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
          textAlign: "center",
          width: 70,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.taskTypeCodeName +
              "</div>";

            return output;
          },
        },
        {
          field: "taskMetric",
          title: "PROPERTIES",
          sortable: false,
          textAlign: "center",
          width: 90,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.taskMetricCodeName +
              "</div>";

            return output;
          },
        },
        {
          field: "taskIndicator",
          title: "",
          sortable: false,
          textAlign: "center",
          width: 70,
          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.taskIndicatorCodeName +
              "</div>";

            return output;
          },
        },
        {
          field: "Actions",
          title: "Actions",
          sortable: false,
          textAlign: "center",
          width: 70,

          template: function (data) {
            return (
              '\
                      <button class="selectButton btn btn-sm btn-light btn-text-primary btn-icon mr-2" data-sentence="' +
              data.sentence +
              '" data-taskType="' +
              data.taskType +
              '" data-taskMetric="' +
              data.taskMetric +
              '" data-taskIndicator="' +
              data.taskIndicator +
              '" data-toggle="modal"  data-target="#kt_datatable_modal_keyresult">\
                      <i class="la la-spell-check"></i>\
                  </button>\
                      '
            );
          },
        },
      ],
    });

    var modal = $("#kt_datatable_modal_keyresult");

    $("#kt_datatable_search_sentence").on("change keyup paste", function () {
      datatable.search($(this).val().toLowerCase(), "sentence");
    });

    $("#kt_datatable_search_jobType").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "jobTypeCodeName");
    });



    $("#kt_search").on("click", function (e) {
      e.preventDefault();
      console.log(datatable.getDataSourceQuery());

      datatable.setDataSourceParam("query", {
        sentence: $("#kt_datatable_search_sentence").val(),
        jobType: $("#kt_datatable_search_jobType").val(),
      });

      datatable.reload();
      console.log(datatable.getDataSourceQuery());
    });

    $("#kt_reset").on("click", function (e) {
      e.preventDefault();

      datatable.setDataSourceParam("query", {});
      $(".datatable-input").each(function () {
        $(this).val("").change();
      });
    });

   

    // fix datatable layout after modal shown
    datatable.hide();
    var alreadyReloaded = false;
    modal.on("shown.bs.modal", function () {
      if (!alreadyReloaded) {
        var modalContent = $(this).find(".modal-content");
        datatable.spinnerCallback(true, modalContent);

        datatable.reload();

        datatable.on("datatable-on-layout-updated", function () {
          console.log("update");

          $("#kt_datatable_sub tbody").on(
            "click",
            ".selectButton",
            function (e) {
              var index=localStorage.getItem("index");
              console.log("index")
              console.log(index)
              // console.log(e.currentTarget.dataset.sentence);
              // console.log(e.currentTarget.dataset.tasktype);
              // console.log(e.currentTarget.dataset.taskmetric);
              // console.log(e.currentTarget.dataset.taskindicator);
              $('.key-result:eq('+index+')').val(e.currentTarget.dataset.sentence);
              $('.task-type:eq('+index+')').val(e.currentTarget.dataset.tasktype);
              $('.task-metric:eq('+index+')').val(e.currentTarget.dataset.taskmetric);
              $('.task-indicator:eq('+index+')').val(e.currentTarget.dataset.taskindicator);
            
              // $('.key-result[data-index="'+index+'"]').val(e.currentTarget.dataset.sentence);
              // $('.task-type[data-index="'+index+'"]').val(e.currentTarget.dataset.tasktype);
              // $('.task-metric[data-index="'+index+'"]').val(e.currentTarget.dataset.taskmetric);
              // $('.task-indicator[data-index="'+index+'"]').val(e.currentTarget.dataset.taskindicator);
      

              
            }
          );

          datatable.show();
          datatable.spinnerCallback(false, modalContent);
          datatable.redraw();
        });

        alreadyReloaded = true;
      }
    });
  };

  return {
    init: function (index) {
      initSubDatatable(index);
    },
  };
})();
