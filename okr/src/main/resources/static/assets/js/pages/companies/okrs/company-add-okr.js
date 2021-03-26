$(document).ready(function () {
    let action = '';
    const objectiveLevels = JSON.parse(localStorage.getItem('objLevels'));
    const taskTypes = JSON.parse(localStorage.getItem('taskTypes'));
    const taskMetrics = JSON.parse(localStorage.getItem('taskMetrics'));
    const taskIndicators = JSON.parse(localStorage.getItem('taskIndicators'));
    const initialData = [{}, {}, {}]; // init 3 blank object
    const table = $('#keyResultTable').DataTable({paging: false, info: false, filter: false, ordering: false,
        data: initialData,
        "rowCallback": function(row) {
            $(row).addClass('row-key-result');
            $(row).find('button.search-key-result').on('click', saveRowIndexAndShowDictionary);
        },
        columnDefs: [
            {
                targets: 0,
                title: 'LEVEL*',
                className: 'w3-24 text-center px-0',
                render: function () {
                    let result =
                        '<select class="custom-select objective-level" name="keyresultlevel[]">\
                            <option value="">Level</option>\
                        ';
                    $.each(objectiveLevels, function (index, level) {
                        result += '<option value="' + level.code + '">' + level.codeName + '</option>'
                    })
                    result += '</select>'
                    return result;
                }
            },
            {
                targets: 1,
                title: 'KEY RESULT*',
                className: 'w6-12 text-center',
                render: function () {
                    return '<input type="text" class="key-result form-control w-100" name="keyresult[]">'
                }
            },
            {
                targets: 2,
                title: 'PROPERTIES',
                className: 'w4-12 text-center',
                render: function () {
                    let result = '<div class="row justify-content-center">';
                    let taskTypeSelect = '<select class="custom-select col mr-2 task-type" style="width: 33%">\
                                            <option value="">Task Type</option>';
                    $.each(taskTypes, function (index, taskType) {
                        taskTypeSelect += '<option value="' + taskType.code + '">' + taskType.codeName + '</option>';
                    })
                    taskTypeSelect += '</select>';
                    let taskMetricSelect = '<select class="custom-select col mr-2 task-metric" style="width: 33%">\
                                            <option value="">Task Metric</option>';

                    $.each(taskMetrics, function (index, taskMetric) {
                        taskMetricSelect += '<option value="' + taskMetric.code + '">' + taskMetric.codeName + '</option>';
                    })
                    taskMetricSelect += '</select>';

                    let taskIndicatorSelect = '<select class="custom-select col mr-2 task-indicator" style="width: 33%">\
                                            <option value="">Task Indicator</option>';

                    $.each(taskIndicators, function (index, taskIndicator) {
                        taskIndicatorSelect += '<option value="' + taskIndicator.code + '">' + taskIndicator.codeName + '</option>';
                    })
                    taskIndicatorSelect += '</select>';
                    result += taskTypeSelect + taskMetricSelect + taskIndicatorSelect;
                    result += '</div>';
                    return result;
                }
            },
            {
                targets: 3,
                title: 'ACTION',
                className: 'w1-24 text-center',
                render: function () {
                    return '<button type="button" class="btn btn-clean btn-hover-light-primary btn-sm btn-icon search-key-result" data-toggle="modal" data-target="#kt_datatable_modal_keyresult">\
                                    <i class="flaticon2-search"></i>\
                                </button>'
                }
            }
        ]

    });

    let fv = initializeFormValidation();

    $('button#btnAddKeyResult').on('click', function () {
        table.row.add({}).draw(true);// add a blank object
        fv.resetForm(false);
        $('#formOKRs div.fv-plugins-message-container').remove();
        fv = initializeFormValidation();//re-initialize form validation to apply to newly added row
    });

    $('.submit-option').on('click', function () {
        action = $(this).val();
    })

    function initializeFormValidation() {
        return FormValidation.formValidation(
            document.getElementById('formOKRs'),
            {
                fields: {
                    objective: {
                        validators: {
                            notEmpty: {
                                message: 'Please type in objective'
                            }
                        }
                    },
                    'keyresultlevel[]': {
                        validators: {
                            notEmpty: {
                                message: 'Please select level'
                            }
                        }
                    },
                    'keyresult[]': {
                        validators: {
                            notEmpty: {
                                message: 'Key Result is required'
                            }
                        }
                    }
                },
                plugins: {
                    trigger: new FormValidation.plugins.Trigger(),
                    bootstrap: new FormValidation.plugins.Bootstrap({
                        rowSelector: function (field) {
                            if (field === 'objective') {
                                return 'div.form-group';
                            }
                            if (field === 'keyresultlevel[]' || field === 'keyresult[]') {
                                return 'td';
                            }
                        }
                    }),
                    submitButton: new FormValidation.plugins.SubmitButton(),
                }
            }
        ).on('core.form.valid', function () {
            const year = $('#year').val();
            const quarter = $('#quarter').val();
            const objective = $('#objective').val();
            var keyResults = [];
            $('table#keyResultTable tr.row-key-result').each(function () {
                const level = $(this).find('select.objective-level').val();
                const kresult = $(this).find('input.key-result').val();
                const taskType = $(this).find('select.task-type').val();
                const taskMetric = $(this).find('select.task-metric').val();
                const taskIndicator = $(this).find('select.task-indicator').val();
                var keyResult = {
                    objectiveLevel: level,
                    keyResult: kresult,
                    taskType: taskType,
                    taskMetric: taskMetric,
                    taskIndicator: taskIndicator
                };
                keyResults.push(keyResult);
            })

            $.ajax({
                url: '/company/okrs/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    year: year,
                    quarter: quarter,
                    objective: objective,
                    key_results: keyResults
                }),
                success: function(responseData) {
                    console.log(responseData)
                    Swal.fire({
                        title: 'Submit successfully',
                        icon: 'success',
                        showCancelButton: false,
                        confirmButtonText: 'OK'
                    }).then(() => {
                        if (action === 'saveAndAddNew') {
                            location.reload();
                        } else if (action === 'saveAndRelation') {
                            //TODO: change url for save & relation
                            window.location.href = '/';
                        } else if (action === 'saveAndExit') {
                            window.location.href = '/';
                        }
                    });
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log(jqXHR)
                    console.log(textStatus)
                    console.log(errorThrown)
                    Swal.fire({
                        title: textStatus,
                        text: errorThrown,
                        icon: 'error',
                        showCancelButton: false,
                        confirmButtonText: 'OK'
                    })
                }
            })
        });
    }

    KTDatatableModalForKeyResult.init();

    function saveRowIndexAndShowDictionary() {
        fv.resetForm(false);
        const rowIndex = $(this).closest('tr').index();
        localStorage.setItem("index", rowIndex)
    }
})
