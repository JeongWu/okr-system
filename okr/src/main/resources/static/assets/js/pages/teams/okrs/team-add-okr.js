$(document).ready(function () {
    let action = '';
    const objectiveLevels = JSON.parse(localStorage.getItem('objLevels'));
    const taskTypes = JSON.parse(localStorage.getItem('taskTypes'));
    const taskMetrics = JSON.parse(localStorage.getItem('taskMetrics'));
    const taskIndicators = JSON.parse(localStorage.getItem('taskIndicators'));
    const teamId = $('#teamId').val();
    const sumOfOtherObjectivesProportions = parseInt($('#sumOfOtherObjectiveProportion').val());
    const initialData = [{}, {}, {}]; // init 3 blank object
    const table = $('#keyResultTable').DataTable({
        paging: false, info: false, filter: false, ordering: false,
        data: initialData,
        "rowCallback": function (row, data, index) {
            // initializing different name so that jquery validation plugins can work
            $(row).find('select.objective-level').attr('name', 'keyresult-level-' + index);
            $(row).find('input.key-result').attr('name', 'keyresult-' + index);
            $(row).find('input.proportion').attr('name', 'proportion-' + index).on('change', validateTotalProportion);
            $(row).addClass('row-key-result');
            $(row).find('button.search-key-result').on('click', saveRowIndexAndShowDictionary);
            $(row).find('button.remove-row').on('click', removeCurrentRow);
        },
        "initComplete": function () {
            // because of using ajax data source. adding rules in rowCallback won't work for data coming from server
            $('input.proportion').each(function () {
                $(this).rules('add', {integer: true})
            })
        },
        columnDefs: [
            {
                targets: 0,
                title: 'LEVEL*',
                className: 'w1-12 text-center px-0',
                render: function () {
                    let result =
                        '<select class="custom-select objective-level" required>\
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
                className: 'w11-24 text-center',
                render: function () {
                    return '<input type="text" class="key-result form-control w-100" required maxlength="255">'
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
                title: 'PROPORTION*',
                className: 'w1-24 text-center',
                render: function () {
                    return '<span class="input-symbol-percent">\
                                <input class="form-control proportion" type="number" min="1" max="100" required>\
                            </span>'
                }

            },
            {
                targets: 4,
                title: 'ACTION',
                className: 'w1-12 text-center px-0',
                render: function () {
                    return '<button type="button" class="btn btn-clean btn-hover-light-primary btn-sm btn-icon search-key-result" data-toggle="modal" data-target="#kt_datatable_modal_keyresult">\
                                    <i class="flaticon2-search"></i>\
                             </button>\
                             <button type="button" class="btn btn-clean btn-hover-light-danger btn-sm btn-icon remove-row">\
                                <i class="flaticon2-delete"></i>\
                             </button> \
                             '
                }
            }
        ]

    });

    $('button#btnAddKeyResult').on('click', function () {
        const rowNode = table.row.add({}).draw(true).node();// add a blank object
        $(rowNode).find('input.proportion').rules('add', {integer: true})
    });

    $('.submit-option').on('click', function () {
        action = $(this).val();
    })

    KTDatatableModalForKeyResult.init();

    function saveRowIndexAndShowDictionary() {
        const rowIndex = $(this).closest('tr').index();
        localStorage.setItem("index", rowIndex)
    }

    const formValidator = $('#formOKRs').validate({
        submitHandler: function () {
            const year = $('#year').val();
            const quarter = $('#quarter').val();
            const objective = $('#objective').val();
            const objectiveProportion = $('#objectiveProportion').val();
            var keyResults = [];
            $('table#keyResultTable tr.row-key-result').each(function () {
                const level = $(this).find('select.objective-level').val();
                const kresult = $(this).find('input.key-result').val();
                const taskType = $(this).find('select.task-type').val();
                const taskMetric = $(this).find('select.task-metric').val();
                const taskIndicator = $(this).find('select.task-indicator').val();
                const proportion = $(this).find('input.proportion').val();
                var keyResult = {
                    objectiveLevel: level,
                    keyResult: kresult,
                    taskType: taskType,
                    taskMetric: taskMetric,
                    taskIndicator: taskIndicator,
                    proportion: proportion
                };
                keyResults.push(keyResult);
            })

            $.ajax({
                url: '/team/' + teamId + '/okrs/add',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    year: year,
                    quarter: quarter,
                    objective: objective,
                    proportion: objectiveProportion,
                    keyResults: keyResults
                }),
                success: function (responseData) {
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
                error: function (jqXHR, textStatus, errorThrown) {
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
        }
    });

    $.validator.addMethod("sumProportion", function (value, element, params) {
            var sumOfVals = 0;
            $('table#keyResultTable').find('input.proportion').each(function () {
                const aValue = parseInt($(this).val(), 10);
                sumOfVals += isNaN(aValue) ? 0 : aValue;
            });
            return sumOfVals === params;
        },
        jQuery.validator.format("Sum of portion must be {0}")
    );
    $('input#proportionSum').rules('add', {sumProportion: 100});

    function validateTotalProportion() {
        formValidator.element('#proportionSum');
    }

    function removeCurrentRow() {
        const row = $(this).parents('tr.row-key-result');
        table.row(row).remove().draw();
        validateTotalProportion();
    }

    const objectiveProp = $('#objectiveProportion');
    objectiveProp.rules('add', {integer: true});
    objectiveProp.on('change', function () {
        const newValue = parseInt($(this).val());
        const newTotal = isNaN(newValue) ? sumOfOtherObjectivesProportions : (sumOfOtherObjectivesProportions + newValue);
        $('#spanSumObjectivesProportions').text(newTotal);
        $('#totalObjectiveProportionHidden').val(newTotal);
        console.log(newTotal)
        formValidator.element('#totalObjectiveProportionHidden');
    })
})
