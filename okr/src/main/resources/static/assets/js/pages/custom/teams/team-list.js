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
            url: "/api/teams/datatables",
            map: function (raw) {
              var dataSet = raw;

              if (typeof raw.data !== "undefined") {
                dataSet = raw.data;
              }
              console.log(dataSet);
              var data = dataSet.map((i) => i.divisionName);
              var list = new Set(data);
              list.forEach(function (d) {
                $('.datatable-input[data-col-index="1"]').append(
                  '<option value="' + d + '">' + d + "</option>"
                );
              });
              $("#kt_datatable_search_division").selectpicker();
              $("#kt_subheader_total").append(dataSet.length + " Total");

              return dataSet;
            },
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
          field: "teamSeq",
          title: "#",
          sortable: "asc",
          width: 20,
          type: "number",
          selector: false,
          textAlign: "left",
          template: function (data) {
            return (
              '<span class="font-weight-bolder">' + data.teamSeq + "</span>"
            );
          },
        },
        {
          field: "name",
          title: "Team",
          width: 130,

          template: function (data) {
            var output = "";

            var teamImg = data.image;
            var teamName = data.localName;

            output = '<div class="d-flex align-items-center">';
            output += makeImageSymbol(teamName, teamImg, "big");

            output +=
              '<div class="ml-2">\
              <div class="text-dark-75 font-weight-bold line-height-sm">' +
              teamName +
              "</div>\
                </div>\
            </div>";

            return output;
          },
        },

        {
          field: "divisionName",
          title: "Division",

          template: function (data) {
            var output = "";

            output +=
              '<div class="font-weight-bolder font-size-lg mb-0">' +
              data.division.localName +
              "</div>";

            return output;
          },
        },
        {
          field: "teamType",
          title: "TEAM Type",
          width: 60,
          // autoHide: false,
          template: function (data) {
            var status = {
              1: {
                title: "TEAM",
                class: "label-light-primary",
              },
              2: {
                title: "SQUAD",
                class: " label-light-danger",
              },
              3: {
                title: "TFT",
                class: " label-light-success",
              },
            };

            var statusNo = 1;
            if (data.teamType === "SQUAD") statusNo = 2;
            else if (data.teamType === "TFT") statusNo = 3;

            return (
              '<span class="label font-weight-bold label-lg ' +
              status[statusNo].class +
              ' label-inline label-bold">' +
              status[statusNo].title +
              "</span>"
            );
          },
        },
        {
          field: "TEAM_MANAGER",
          title: "TEAM MANGER",
          width: 60,
          sortable: false,
          template: function (data) {
            var output = "";

            var managerInfo = data.leaderOrManager;

            if (managerInfo !== null) {
              output = '<div class="d-flex align-items-center">';
              output += makeImageSymbol(
                managerInfo.name,
                managerInfo.image,
                "big",
                "circle"
              );
              output += " </div>";
            }

            return output;
          },
        },
        {
          field: "MEMBERS",
          title: "MEMBERS",
          width: 130,
          sortable: false,
          template: function (data) {
            var output = "";

            output =
              '<div class="d-flex align-items-center">\
            <a href="/members">\
          <div class="symbol-group symbol-hover">';

            var memberList = data.members;

            if (memberList.length > 4) {
              memberList.forEach((member, index) => {
                if (index < 4) {
                  output += makeImageSymbol(
                    member.name,
                    member.image,
                    "small",
                    "circle"
                  );
                }
              });
              output +=
                ' <div class="symbol symbol-30 symbol-circle flex-shrink-0 symbol-light">\
              <span class="symbol-label font-weight-bold"> +' +
                (memberList.length - 4) +
                "</span>\
          </div>\
          </div>\
    </div>";
            } else {
              memberList.forEach((member) => {
                output += makeImageSymbol(
                  member.name,
                  member.image,
                  "small",
                  "circle"
                );
              });

              output += "</div>\
              </a>\
              </div>";
            }
            return output;
          },
        },
        {
          field: "useFlag",
          title: "Active",
          width: 70,
          template: function (data) {
            var status = {
              1: { title: "Active", state: "success" },
              2: {
                title: "inActive",
                state: "danger",
              },
            };
            var statusNo = data.useFlag === "Y" ? 1 : 2;
            return (
              '<span class="label label-' +
              status[statusNo].state +
              ' label-dot mr-2"></span><span class="font-weight-bold text-' +
              status[statusNo].state +
              '">' +
              status[statusNo].title +
              "</span>"
            );
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
            var id = data.teamSeq;
            return (
              '\
                    <div class="dropdown dropdown-inline">\
                    <a href="javascript:;" class="btn btn-sm btn-light btn-text-primary btn-icon mr-2" data-toggle="dropdown">\
                    <i class="flaticon-more-1"></i>\
                </a>\
                      <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">\
                        <ul class="nav nav-hoverable flex-column">\
                        <li class="nav-item"><a class="nav-link" href="edit/' +
              id +
              '"><i class="nav-icon la la-edit"></i><span class="nav-text">Edit Details</span></a></li>\
                            <li class="nav-item"><a class="nav-link" href="/change-members/' +
              id +
              '"><i class="nav-icon la la-refresh"></i><span class="nav-text">Change Members</span></a></li>\
                            <li class="nav-item"><a class="nav-link" href="#"><i class="nav-icon la la-cog"></i><span class="nav-text">Set Team Manager</span></a></li>\
                            <li class="nav-item"><a class="nav-link" href="/team-histories/' +
              id +
              '"><i class="nav-icon la la-eye"></i><span class="nav-text">View History</span></a></li>\
                        </ul>\
                      </div>\
                </div>\
                    '
            );
          },
        },
      ],
    });

    var makeImageSymbol = function (name, image, size, shape) {
      var output = "";

      var stateNo = KTUtil.getRandomInt(0, 7);
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
      var state = states[stateNo];

      output =
        '<div class="symbol symbol-' +
        (size === "big" ? "40" : "30") +
        " " +
        (shape === "circle" && "symbol-circle") +
        " symbol-light-" +
        state +
        ' flex-shrink-0" data-toggle="tooltip" title="' +
        name +
        '">';

      if (image === null) {
        output +=
          '<span class="symbol-label font-size-' +
          (size === "big" && "h4") +
          '">' +
          name.substring(0, 1) +
          "</span>";
      } else {
        output += '<img class="" src="' + image + '" alt="photo">';
      }

      output += "</div>";
      return output;
    };

    //realtime search in the view
    // $("#kt_datatable_search_name").on("propertychange change keyup paste input", function () {
    $("#kt_datatable_search_name").on("change keyup paste", function () {
      // console.log($(this).val());
      datatable.search($(this).val().toLowerCase(), "localName");
    });

    $("#kt_datatable_search_division").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "divisionName");
    });

    $("#kt_datatable_search_type").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "teamType");
    });
    $("#kt_datatable_search_active").on("change", function () {
      datatable.search($(this).val().toLowerCase(), "useFlag");
    });

    $("#kt_datatable_search_active,#kt_datatable_search_type").selectpicker();

    // $(
    //   "#kt_datatable_search_division, #kt_datatable_search_type",
    //   "#kt_datatable_search_active"
    // ).selectpicker();

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

    //excel download button
    $("#excelBtn").click(function () {
      $("#kt_datatable").table2excel({
        name: "Excel table",
        filename: "team table",
        fileext: ".xls",
        preserveColors: true,
        exclude_img: true,
        exclude_links: true,
        exclude_inputs: true,
      });
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
