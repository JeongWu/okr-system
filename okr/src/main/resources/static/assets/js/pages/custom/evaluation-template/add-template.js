$(document).ready(function () {

    const answerGroupCodes = JSON.parse(localStorage.getItem('answerGroupCode'));
    const initialData = [{}, {}, {}];
    // table initialization
    const tblTemplateDetail = $("#tblTemplateDetail").DataTable({
        ordering: false, info: false, filter: false, paging: false,
        data: initialData,
        "rowCallback": function (row, data, index) {
            $(row).find('input.question').attr('name', 'question-' + index);
            $(row).find('select.answer-group-code').attr('name', 'answer-group-code-' + index);
            $(row).find('button.btn-delete-row').on('click', deleteCurrentRow);
        },
        columnDefs: [
            {
                targets: 0,
                title: 'QUESTION *',
                width: '50%',
                className: 'text-center',
                render: function () {
                    return '\
                    <input type="text" class="question form-control" required maxlength="255">\
                    '
                }
            },
            {
                targets: 1,
                title: 'ANSWER GROUP CODE *',
                width: '30%',
                className: 'text-center',
                render: function () {
                    let result = '\
                                <select class="custom-select answer-group-code" required>\
                                    <option value="">Select answer group code</option>\
                                ';
                    $.each(answerGroupCodes, function (index, answer) {
                        result += '<option value="' + answer.groupCode + '">' + answer.groupName + '</option>'
                    })
                    result += '</select>'
                    return result;
                }
            },
            {
                targets: 2,
                title: 'ACTIVE *',
                width: '10%',
                className: 'text-center',
                render: function () {
                    return '\
                    <span class="switch switch-icon template-activation-switch">\
                        <label>\
                            <input type="checkbox" checked class="template-detail-activation">\
                            <span></span>\
                        </label>\
                    </span>\
                        ';
                }
            },
            {
                targets: 3,
                title: 'ACTION',
                width: '10%',
                className: 'text-center',
                render: function () {
                    return '\
                    <button type="button" class="btn btn-icon btn-hover-light-danger btn-sm btn-delete-row">\
                        <i class="flaticon2-delete"></i>\
                    </button>'
                }
            }
        ]
    })

    // init action listeners
    $("#btnAddTemplateDetail").on('click', function () {
        tblTemplateDetail.row.add({}).draw().node();
    });

    //function definition
    function deleteCurrentRow() {
        const row = $(this).parents('tr');
        tblTemplateDetail.row(row).remove().draw();
    }

    function addBlankRow() {
        tblTemplateDetail.data().add({})
    }

    // init formvalidation
    $("#formAddTemplate").validate({
        submitHandler: function () {
            const evalType = $("#evaluationType").val();
            const templateName = $("#templateName").val();
            const useFlag = $("#templateActivation").is(":checked") ? 'Y' : 'N';
            const evalTemplateDetails = [];
            $("#tblTemplateDetail tbody tr").each(function () {
                const row = $(this);
                const evalTemplateItem = {
                    question: row.find('input.question').val(),
                    answerGroupCode: row.find('select.answer-group-code').val(),
                    useFlag: row.find('input.template-detail-activation').is(":checked") ? 'Y' : 'N',
                };
                evalTemplateDetails.push(evalTemplateItem)
            })
            let evalTemplateDto = {
                evaluationType: evalType,
                templateName: templateName,
                useFlag: useFlag,
                evaluationTemplateDetails: evalTemplateDetails
            };

            $.ajax({
                url: '/evaluation-template/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(evalTemplateDto),
                success: function (responseData) {
                    if (responseData.success) {
                        Swal.fire({
                            title: "Template created successfully",
                            icon: "success",
                            showCancelButton: false,
                            showConfirmButtonText: 'OK'
                        }).then(function () {
                            window.location.href = "/templates"
                        })
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    Swal.fire({
                        title: jqXHR.statusText,
                        icon: "error",
                        text: jqXHR.responseJSON.error
                    })
                },

            })
        }
    })
})