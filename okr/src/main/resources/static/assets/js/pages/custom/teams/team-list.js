"use strict";
// Class definition

let KTAppsProjectsListDatatable = (function () {
  // Private functions

  let makeTeamListTable = function () {
    let datatable = $("#kt_datatable").KTDatatable({
      // datasource definition
      data: {
        type: "remote",
        source: {
          read: {
            url: "/api/teams/datatables",
            map: function (raw) {
              let dataSet = raw;

              if (typeof raw.data !== "undefined") {
                dataSet = raw.data;
              }
              console.log(dataSet);
              let data = dataSet.map((i) => i.divisionName);
              let list = new Set(data);
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
            let output = "";

            let teamImg = data.image;
            let teamName = data.localName;

            output = '<div class="d-flex align-items-center">';
            output += makeImageSymbol(data, "big");

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
          width: 100,
          template: function (data) {
            let output = "";

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
          width: 80,
          // autoHide: false,
          template: function (data) {
            let status = {
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

            let statusNo = 1;
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
            let output = "";

            let managerInfo = data.leaderOrManager;

            if (managerInfo !== null) {
              output = '<div class="d-flex align-items-center">\
              <a href="/members/list">';
              output += makeImageSymbol(
                managerInfo,
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
          width: 150,
          sortable: false,
          //   template: function (data) {
          //     console.log(data);
          //     return renderImagesOnList(data, "members", "/members/list?memberSeq","memberSeq")
          // },
          template: function (data) {
            let output = "";

            output =
              '<div class="d-flex align-items-center">\
            <a href="/members/list">\
          <div class="symbol-group symbol-hover">';

            let memberList = data.leaderOrManager
              ? data.members.filter(
                  (x) => x.memberSeq !== data.leaderOrManager.memberSeq
                )
              : data.members;

            if (memberList.length > 5) {
              memberList.forEach((member, index) => {
                if (index < 5) {
                  output += makeImageSymbol(
                    member,
                    "small",
                    "circle"
                  );
                }
              });
              output +=
                ' <div class="symbol symbol-30 symbol-circle flex-shrink-0 symbol-light">\
              <span class="symbol-label font-weight-bold"> +' +
                (memberList.length - 5) +
                "</span>\
          </div>\
          </div>\
    </div>";
            } else {
              memberList.forEach((member) => {
                output += makeImageSymbol(
                  member,
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
            return renderActiveStatusOnList(data);
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
            let id = data.teamSeq;
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

    

    //realtime search in the view
    // $("#kt_datatable_search_name").on("propertychange change keyup paste input", function () {
    $("#kt_datatable_search_name").on("change keyup paste", function () {
      // console.log($(this).val());
      // datatable.search($(this).val().toLowerCase(), "localName");
      $("#searchFun").val($(this).val().toLowerCase()).keyup();
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
