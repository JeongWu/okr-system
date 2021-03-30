"use strict";
// Class definition

let KTDatatableRemoteAjaxDemo = function () {
    let initTable = function () {

        let datatable = $('#kt_datatable').KTDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        url: '/api/divisions/datatables',
                        map: function (raw) {
                            let dataSet = raw;
                            $(".subheader-title").html('| ' + dataSet.length+" Total");
                            return dataSet;
                        },
                    },
                },
            },

            layout: {
                scroll: false,
                footer: false,
            },

            sortable: true,
            pagination: true,

            columns: [{
                field: 'divisionSeq',
                title: '#',
                sortable: 'asc',
                width: 50,
                type: 'number',
                selector: false,
                textAlign: 'center',
            }, {
                field: 'localName',
                title: 'DIVISION',
                sortable: 'asc',
                width: 150,
            }, {
                field: "teams",
                title: "TEAMS",
                width: 150,
                sortable: false,
                textAlign: 'center',
                template: function (data) {
                    return renderImagesOnList(data, "teams", "/teams/list?teamSeq","teamSeq")
                },
            }, {
                field: "members",
                title: "MEMBERS",
                width: 150,
                sortable: false,
                template: function (data) {
                    return renderImagesOnList(data, "members", "/members?memberSeq","memberSeq")
                },
            },{
                field: "useFlags",
                title: "ACTIVE",
                width: 80,
                sortable: false,
                template: function (data) {
                    return renderActiveStatusOnList(data)
                },
            }, {
                field: "Actions",
                title: "Actions",
                sortable: false,
                width: 70,
                textAlign: 'center',
                template: function (data) {
                    return `<div class="dropdown dropdown-inline">
                    <a href="javascript:;" class="btn btn-sm btn-light btn-text-primary btn-icon mr-2" data-toggle="dropdown">
                        <i class="flaticon-more-1"></i>
                    </a>
                      <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">
                        <ul class="nav nav-hoverable flex-column">
                            <li class="nav-item"><a class="nav-link" href="/divisions/edit/${data.divisionSeq}"><i class="nav-icon la la-edit"></i><span class="nav-text">Edit Details</span></a></li>
                            <li class="nav-item"><a class="nav-link" href="/divisions/change-members/${data.divisionSeq}"><i class="nav-icon la la-refresh"></i><span class="nav-text">Change Members</span></a></li>
                        </ul>
                      </div>
                </div>`
                },
            }
            ],
        });
    };
    return {
        init: function () {
            initTable();
        },
    };
}();

jQuery(document).ready(function () {
    KTDatatableRemoteAjaxDemo.init();
});
