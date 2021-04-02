$(document).ready(function () {

    const answerGroupCodes = JSON.parse(localStorage.getItem('answerGroupCode'));
    const evalTemplateDetails = JSON.parse(localStorage.getItem('evalTemplateDetails'));
    const templateSeq = $("#templateSeq").val();

    // table initialization
    const tblTemplateDetail = $("#tblTemplateDetail").DataTable({
        ordering: false, info: false, filter: false, paging: false,
        data: evalTemplateDetails,
        "rowCallback": function (row, data, index) {
            $(row).find('input.question').attr('name', 'question-' + index);
            $(row).find('select.answer-group-code').attr('name', 'answer-group-code-' + index);
            $(row).find('button.btn-delete-row').on('click', deleteCurrentRow);
            $(row).data("detailSeq", data.detailSeq);
        },
        columns: [
            {data: 'question'},
            {data: 'answerGroupCode'},
            {data: 'useFlag'},
        ],
        columnDefs: [
            {
                targets: 0,
                title: 'QUESTION *',
                width: '50%',
                className: 'text-center',
                render: function (data) {
                    return '\
                    <input type="text" class="question form-control" required maxlength="255" value="' + data + '">\
                    '
                }
            },
            {
                targets: 1,
                title: 'ANSWER GROUP CODE *',
                width: '30%',
                className: 'text-center',
                render: function (data) {
                    let result = '\
                                <select class="custom-select answer-group-code" required>\
                                    <option value="">Select answer group code</option>\
                                ';
                    $.each(answerGroupCodes, function (index, answer) {
                        result += '<option value="' + answer.groupCode + '" ' + (answer.groupCode === data ? 'selected' : '') + '>' + answer.groupName + '</option>'
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
                render: function (data) {
                    return '\
                    <span class="switch switch-icon template-activation-switch">\
                        <label>'
                        +
                        '<input type="checkbox" class="template-detail-activation"' + (data === 'Y' ? 'checked' : '') + '>\
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
                render: function (data, type, row) {
                    let result = '';
                    if (row.detailSeq === undefined) {
                        result += '\
                                <button type="button" class="btn btn-icon btn-hover-light-danger btn-sm btn-delete-row">\
                                    <i class="flaticon2-delete"></i>\
                                </button>'
                    }
                    return result;
                }
            }
        ]
    })

    // init action listeners
    $("#btnAddTemplateDetail").on('click', function () {
        tblTemplateDetail.row.add({question: '', useFlag: 'Y'}).draw().node();
    });

    //function definition
    function deleteCurrentRow() {
        const row = $(this).parents('tr');
        tblTemplateDetail.row(row).remove().draw();
    }

    // init formvalidation
    $("#formEditTemplate").validate({
        submitHandler: function () {
            const evalType = $("#evaluationType").val();
            const templateName = $("#templateName").val();
            const useFlag = $("#templateActivation").is(":checked") ? 'Y' : 'N';

            let evalTemplateDto = {
                templateSeq: templateSeq,
                evaluationType: evalType,
                templateName: templateName,
                useFlag: useFlag,
            };

            $.ajax({
                url: '/evaluation-template/' + templateSeq + '/update-evaluation-template',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(evalTemplateDto),
                success: function (responseData) {
                    if (responseData.success) {
                        Swal.fire({
                            title: "Template updated successfully",
                            icon: "success",
                            showCancelButton: false,
                            showConfirmButtonText: 'OK'
                        }).then(function () {
                            window.location.reload();
                        })
                    }
                },
                error: function (jqXHR) {
                    Swal.fire({
                        title: jqXHR.statusText,
                        icon: "error",
                        text: jqXHR.responseJSON.error
                    })
                },

            })
        }
    });

    $("#formEditTemplateDetail").validate({
        submitHandler: function () {
            const evalType = $("#evaluationType").val();
            const templateName = $("#templateName").val();
            const useFlag = $("#templateAction").is(":checked") ? 'Y' : 'N';
            const evalTemplateDetails = [];
            $("#tblTemplateDetail tbody tr").each(function () {
                const row = $(this);
                const detailSeq = row.data("detailSeq") === undefined ? null : $(row).data("detailSeq");
                const evalTemplateItem = {
                    detailSeq: detailSeq,
                    templateSeq: templateSeq,
                    question: row.find('input.question').val(),
                    answerGroupCode: row.find('select.answer-group-code').val(),
                    useFlag: row.find('input.template-detail-activation').is(":checked") ? 'Y' : 'N',
                };
                evalTemplateDetails.push(evalTemplateItem)
            })
            let evalTemplateDto = {
                templateSeq: templateSeq,
                evaluationType: evalType,
                templateName: templateName,
                useFlag: useFlag,
                evaluationTemplateDetails: evalTemplateDetails
            };

            $.ajax({
                url: '/evaluation-template/' + templateSeq + '/update-evaluation-template-details',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(evalTemplateDto),
                success: function (responseData) {
                    if (responseData.success) {
                        Swal.fire({
                            title: "Template updated successfully",
                            icon: "success",
                            showCancelButton: false,
                            showConfirmButtonText: 'OK'
                        }).then(function () {
                            window.location.reload();
                        })
                    }
                },
                error: function (jqXHR) {
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