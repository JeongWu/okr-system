"use strict";
// Class definition

let KTDatatableRemoteAjaxDemo = function () {
    let initTable = function () {
        let divisionId = $("#division-id").val();
        let divisionName = $("#division-name").val();

        let datatable = $('#kt_datatable').KTDatatable({
            data: {
                type: 'remote',
                source: {
                    read: {
                        url: '/api/divisions/changeMembers/datatables/' + divisionId,
                        map: function (raw) {
                            let dataSet = raw;
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
            pagination: false,

            columns: [{
                field: 'memberSeq',
                title: '#',
                sortable: 'asc',
                width: 50,
                type: 'number',
                selector: false,
                textAlign: 'center',
            }, {
                field: 'localName',
                title: 'MEMBER',
                sortable: 'asc',
                width: 150,
            },{
                field: 'position',
                title: 'POSITION',
                sortable: 'asc',
                width: 150,
            }, {
                field: "useFlags",
                title: "ACTIVE",
                width: 80,
                sortable: false,
                template: function (data) {
                    return renderActiveStatusOnList(data)
                },
            },
                {
                    field: "teams",
                    title: "BELONG TO",
                    width: 150,
                    sortable: false,
                    template: function (data) {
                        return renderImagesOnList(data, "teams")
                    },
                }, {
                    field: "Actions",
                    title: "Actions",
                    sortable: false,
                    width: 70,
                    textAlign: 'center',
                    overflow: "visible",
                    template: function (data) {
                        return `<div class="dropdown dropdown-inline">
                    <a href="javascript:;" class="btn btn-sm btn-light btn-text-primary btn-icon mr-2" data-toggle="dropdown">
                        <i class="flaticon-more-1"></i>
                    </a>
                      <div class="dropdown-menu dropdown-menu-sm dropdown-menu-right">
                        <ul class="nav nav-hoverable flex-column">
                            <li class="nav-item">
                            <a class="nav-link" id="remove-action${data.memberSeq}" data-memberName="${data.localName}" data-applyBeginDate="${data.applyBeginDate}" onclick="bindingModal(${data.memberSeq})" href="javascript:;">
                                <input class="member-name" th:value="${data.localName}" hidden />
                                <input class="division-member-applyBeginDate" th:value="${data.applyBeginDate}" hidden />
                                <i class="nav-icon la la-edit"></i>
                                <span class="nav-text">Remove</span>
                            </a></li>
                        </ul>
                      </div>
                </div>`
                    },
                },
            ],
        });
    };
    return {
        init: function () {
            initTable();
        },
    };
}();

$(document).ready(function () {
    KTDatatableRemoteAjaxDemo.init();
    let divisionId = $("#division-id").val();
    $("#add-form").find(".add-form-division-id").val(divisionId);

    $("#kt_datepicker_2,#kt_datepicker_3").on("blur", function (){
        if ($(this).val() === "" || $(this).val() === null ){
            addCurrentTimeTo($(this));
        }
    })

    $("#kt_select2_20").on("change", function (){
        addCurrentTimeTo($("#kt_datepicker_3"))
    })

    $("#add-form").validate();
    $("#remove-form").validate();

    $("#remove-action1").on("click", function (){
        console.log("AAA")
    })
});

function addCurrentTimeTo(element){
    let date = new Date();
    let dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 ))
        .toISOString()
        .split("T")[0];

    $(element).val(dateString);
}

function bindingModal(memberId){
    let selectedItem = $("#remove-action" + memberId);
    let divisionId = $("#division-id").val();
    let divisionName = $("#division-name").val();
    addCurrentTimeTo($("#kt_datepicker_2"));
    $("#justification-content").val("");
    $("#modal-member-id").val(memberId);
    $("#modal-division-id").val(divisionId);
    $("#modal-applyBeginDate").val(selectedItem.attr("data-applyBeginDate"));
    $("#removeModelLabel").html("Do you confirm to remove " +applyRedTextToSpan(selectedItem.attr("data-memberName"))+" from "+ applyRedTextToSpan(divisionName)+"?")
    $("#modal-active").click();
}
