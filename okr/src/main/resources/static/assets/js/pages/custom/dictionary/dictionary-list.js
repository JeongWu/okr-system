"use strict";
// Class definition

var KTDefaultDatatableDemo = (function () {
  // Private functions

  // basic demo
  var demo = function () {
    var options = {
      data: {
        type: "remote",
        source: {
          read: {
            url: "/api/dictionary/datatables",
          },
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
        input: $("#kt_datatable_search_query"),
        key: "generalSearch",
      },

      // columns definition
      columns: [
        {
          field: "dictionarySeq",
          title: "#",
          width: 30,
        },
        {
          field: "dictionaryTypeCodeName",
          title: "Type",
          textAlign: "center",
          width: 70,
        },
        {
          field: "jobTypeCodeName",
          title: "Job",
          textAlign: "center",
          width: 50,
        },

        {
          field: "categoryGroupCodeName",
          title: "Category Group",
          textAlign: "center",
          width: 100,
        },

        {
          field: "categoryCodeName",
          title: "Category",
          textAlign: "center",
          width: 100,
        },
        {
          field: "sentence",
          title: "Sentence",
          width: 250,
        },
        {
          field: "active",
          title: "Active",
          autoHide: false,
          width: 70,
          // callback function support for column rendering
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
            var dictionarySeq = row.dictionarySeq;

            return (
              '\
							<div class="dropdown">\
								<button   class="btn btn-sm btn-clean btn-icon" data-toggle="dropdown">\
								<i class="flaticon-more-1"></i>\
	                            </button>\
							  	<div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
									<ul class="nav nav-hoverable flex-column">\
							    		<li class="nav-item"><a class="nav-link" href="/dictionary/edit/' +
              dictionarySeq +
              '"><i class="nav-icon la la-edit"></i><span class="nav-text">Edit Dictionary</span></a></li>\
									\
									</ul>\
							  	</div>\
							</div>'
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
        filename: "Dictionary",
        fileext: ".xls",
      });
    });
    $(function () {
      $("btnExport").click(function (e) {
        var table = $(this).prev("kt_datatable");
        if (table && table.length) {
          var preserveColors = table.hasClass("table2excel_with_colors")
            ? true
            : false;
          $(table).table2excel({
            exclude: ".noExl",
            name: "Excel Document Name",
            filename:
              "myFileName" +
              new Date().toISOString().replace(/[\-\:\.]/g, "") +
              ".xls",
            fileext: ".xls",
            exclude_img: true,
            exclude_links: true,
            exclude_inputs: true,
            preserveColors: preserveColors,
          });
        }
      });
    });

    // //Reset button
    // $('#kt_reset').on('click', function(e) {
    // 	e.preventDefault();
    // 	$('.datatable-input').each(function() {
    // 		$(this).val('');
    // 	});
    // 	datatable.setDataSourceParam('query', {});
    // 	datatable.reload();
    // });

    //reset button
    $("#kt_reset").on("click", function (e) {
      e.preventDefault();

      datatable.setDataSourceParam("query", {});
      $(".datatable-input").each(function () {
        $(this).val("").change();
      });
    });

    $("#kt_datatable_search_type").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "dictionaryType");
    });
    $("#kt_datatable_search_job").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "jobType");
    });

    $("#kt_datatable_search_categorygroup").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "categoryGroup");
    });

    $("#kt_datatable_search_category").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "category");
    });
    $("#kt_datatable_search_active").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "useFlag");
    });

    $(
      "#kt_datatable_search_type,#kt_datatable_search_job,#kt_datatable_search_categorygroup,#kt_datatable_search_category,#kt_datatable_search_active"
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
