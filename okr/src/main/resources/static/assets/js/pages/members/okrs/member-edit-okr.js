$(document).ready(function () {
    const objectiveLevels = JSON.parse(localStorage.getItem('objLevels'));
    const taskTypes = JSON.parse(localStorage.getItem('taskTypes'));
    const taskMetrics = JSON.parse(localStorage.getItem('taskMetrics'));
    const taskIndicators = JSON.parse(localStorage.getItem('taskIndicators'));
    const objectiveSeq = $('#formEditObjective input#objectiveSeq').val();
    const sumOfOtherObjectivesProportions = parseInt($('#sumOfOtherObjectiveProportion').val());
    const table = $('#keyResultTable').DataTable(
        {
            paging: false, info: false, filter: false, ordering: false,
            ajax: {
                url: '/api/okrs/objective/' + objectiveSeq + '/active-key-results',
                type: 'GET',
                dataSrc: ''
            },
            "rowCallback": function (row, data, index) {
                $(row).find('select.objective-level').attr('name', 'keyresult-level-' + index);
                $(row).find('input.key-result').attr('name', 'keyresult-' + index);
                $(row).find('input.proportion').attr('name', 'proportion-' + index).on('change', validateTotalActiveProportion);
                $(row).find('input.key-result-activation').attr('name', 'key-result-activation-' + index).on('click', validateTotalActiveProportion).on('click', validateInactiveJustification);
                $(row).find('input.key-result-justification').attr('name', 'key-result-justification-' + index);
                $(row).data('keyResultSeq', data.keyResultSeq);
                $(row).addClass('row-key-result');
                $(row).find('button.search-key-result').on('click', saveIndexAndShowDictionary);
                $(row).find('button.remove-row').on('click', removeCurrentRow);
            },
            "initComplete": function () {
                // because of using ajax data source. adding rules in rowCallback won't work for data coming from server
                $('input.proportion').each(function () {
                    $(this).rules('add', {integer: true})
                })
                $('table#keyResultTable tbody tr input.key-result-justification').each(function() {
                    const krActivation = $(this).parent('td').prev('td').find('input.key-result-activation');
                    $(this).rules('add', {requiredIfDeactivate: krActivation})
                })
            },
            columns: [
                {data: 'objectiveLevel'},
                {data: 'keyResult'},
                {data: null},//properties
                {data: 'proportion'},
                {data: 'closeFlag'},
                {data: null},//justification
                {data: null} // action
            ],
            columnDefs: [
                {
                    targets: 0,
                    title: 'LEVEL*',
                    className: 'text-center px-0',
                    width: '8.33%',
                    render: function (data) {
                        let result =
                            '<select class="custom-select objective-level" required>\
                                <option value="">Level</option>\
                            ';
                        $.each(objectiveLevels, function (index, level) {
                            result += '<option value="' + level.code + '"' + (data === level.code ? ' selected' : '') + '>' + level.codeName + '</option>'
                        })
                        result += '</select>'
                        return result;
                    }
                },
                {
                    targets: 1,
                    title: 'KEY RESULT*',
                    className: 'text-center',
                    width: '29.17%',
                    render: function (data) {
                        return '<input type="text" class="key-result form-control w-100" value="' + (data === undefined ? '' : data) + '" required maxlength="255">'
                    }
                },
                {
                    targets: 2,
                    title: 'PROPERTIES',
                    className: 'text-center',
                    width: '25%',
                    render: function (data, type, row) {
                        let result = '<div class="row justify-content-center">';
                        let taskTypeSelect = '<select class="custom-select col mr-2 task-type" style="width: 33%">\
                                            <option value="">Task Type</option>';
                        $.each(taskTypes, function (index, taskType) {
                            taskTypeSelect += '<option value="' + taskType.code + '"' + (taskType.code === row.taskType ? 'selected' : '') + '>' + taskType.codeName + '</option>';
                        })
                        taskTypeSelect += '</select>';
                        let taskMetricSelect = '<select class="custom-select col mr-2 task-metric" style="width: 33%">\
                                            <option value="">Task Metric</option>';

                        $.each(taskMetrics, function (index, taskMetric) {
                            taskMetricSelect += '<option value="' + taskMetric.code + '"' + (taskMetric.code === row.taskMetric ? 'selected' : '') + '>' + taskMetric.codeName + '</option>';
                        })
                        taskMetricSelect += '</select>';

                        let taskIndicatorSelect = '<select class="custom-select col mr-2 task-indicator" style="width: 33%">\
                                            <option value="">Task Indicator</option>';

                        $.each(taskIndicators, function (index, taskIndicator) {
                            taskIndicatorSelect += '<option value="' + taskIndicator.code + '"' + (taskIndicator.code === row.taskIndicator ? 'selected' : '') + '>' + taskIndicator.codeName + '</option>';
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
                    className: 'text-center px-0 col-prop',
                    width: '2.08%',
                    render: function (data) {
                        return '<span class="input-symbol-percent">\
                                    <input class="form-control proportion" type="number" min="1" max="100" required value="' + (data === undefined ? '' : data) + '">\
                                </span>'
                    }
                },
                {
                    targets: 4,
                    title: 'ACTIVE',
                    className: 'text-center px-0',
                    width: '2.08%',
                    render: function(data) {
                        return '<span class="switch switch-icon col-1">\n' +
                            '           <label>\n' +
                            '               <input type="checkbox" ' + (data === 'N' ? 'checked' : '') + ' class="key-result-activation">\n' +
                            '               <span></span>\n' +
                            '           </label>\n' +
                            '      </span>';
                    }
                },
                {
                    targets: 5,
                    title: 'JUSTIFICATION',
                    className: 'text-center col-px-5',
                    width: '25%',
                    render: function() {
                        return '<input class="form-control key-result-justification">';
                    }
                },
                {
                    targets: 6,
                    title: 'ACTION',
                    className: 'text-center px-0',
                    width: '8.33%',
                    render: function (data, type, row) {
                        let result = '<button type="button" class="btn btn-clean btn-hover-light-primary btn-sm btn-icon search-key-result" data-toggle="modal" data-target="#kt_datatable_modal_keyresult">\
                                            <i class="flaticon2-search"></i>\
                                       </button>';
                        if (row.keyResultSeq === undefined) {
                            result += '<button type="button" class="btn btn-clean btn-hover-light-danger btn-sm btn-icon remove-row">\
                                            <i class="flaticon2-delete"></i>\
                                        </button>';
                        } else {
                            // create a "like an icon btn" for vertical alignment
                            result += '<span class="btn btn-clean btn-hover-light-danger btn-sm btn-icon invisible">\
                                        </span>';
                        }
                        return result;
                    }
                }
            ]
        });

    $('#btnAddKeyResult').on('click', function () {
        const rowNode = table.row.add({closeFlag: 'N'}).draw(true).node();// add new row with blank data
        $(rowNode).addClass('new-key-result');
        $(rowNode).find('input.proportion').rules('add', {integer: true});
        const krActivation = $(rowNode).find('input.key-result-activation');
        $(rowNode).find('input.key-result-justification').rules('add', {requiredIfDeactivate: krActivation})
    });

    // initObjectiveFormValidation();
    $('#objectiveActivation').on('change', function () {
        if (!$(this).is(':checked')) {
            $('#objInactiveJustification').prop('disabled', false);
        } else {
            $('#objInactiveJustification').prop('disabled', true);
        }
    })

    KTDatatableModalForKeyResult.init();

    function saveIndexAndShowDictionary() {
        const rowIndex = $(this).closest('tr').index();
        localStorage.setItem("index", rowIndex)
    }

    const objectiveValidator = $('#formEditObjective').validate({
        submitHandler: function () {
            const objectiveText = $('#formEditObjective input#objective').val();
            const closeFlag = $('#objectiveActivation').is(":checked") ? 'N' : 'Y';
            const inactiveJustification = $('#objInactiveJustification').val();
            const justification = $('#objectiveJustification').val();
            const proportion = $('#objectiveProportion').val();

            const data = {
                objectiveSeq: objectiveSeq,
                objective: objectiveText,
                closeFlag: closeFlag,
                closeJustification: inactiveJustification,
                justification: justification,
                proportion: proportion
            }
            $.ajax({
                url: '/api/okrs/objective/' + objectiveSeq,
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (data) {
                    Swal.fire({
                        title: 'Update successfully',
                        text: 'Click OK to reload this page',
                        icon: 'success',
                        showCancelButton: false,
                        confirmButtonText: 'OK'
                    }).then(() => {
                        location.reload();
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
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
    })

    const keyResultFormValidator = $('#formChangeKeyResult').validate({
        submitHandler: function () {
            // there will be 2 kinds of data here: one created before, now just updated
            // the other, just added
            const keyResults = [];
            $('#keyResultTable tbody tr.row-key-result').each(function() {
                const row = $(this);
                const keyResultSeq = row.hasClass('new-key-result') ? null : row.data('keyResultSeq');
                const objectiveLevel = row.find('select.objective-level').val();
                const keyResult = row.find('input.key-result').val();
                const taskType = row.find('select.task-type').val();
                const taskMetric = row.find('select.task-metric').val();
                const taskIndicator = row.find('select.task-indicator').val();
                const proportion = row.find('input.proportion').val();
                const closeFlag = row.find('input.key-result-activation').is(':checked') ? 'N' : 'Y';
                const justification = row.find('input.key-result-justification').val();
                keyResults.push({
                    keyResultSeq: keyResultSeq,
                    objectiveSeq: objectiveSeq,
                    objectiveLevel: objectiveLevel,
                    keyResult: keyResult,
                    taskType: taskType,
                    taskMetric: taskMetric,
                    taskIndicator: taskIndicator,
                    proportion: proportion,
                    justification: justification,
                    closeFlag: closeFlag,
                    closeJustification: closeFlag === 'Y' ? justification : null
                })
            })

            $.ajax({
                url: '/api/okrs/objective/{objectiveSeq}/keyresults',
                type: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify(keyResults),
                success: function (responseData) {
                    Swal.fire({
                        title: 'Update successfully',
                        text: 'Click OK to reload this page',
                        icon: 'success',
                        showCancelButton: false,
                        confirmButtonText: 'OK'
                    }).then(() => {
                        location.reload();
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
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
    })

    $.validator.addMethod("sumActiveProportion", function (value, element, params) {
            let sumOfVals = 0;
            $('table#keyResultTable').find('input.key-result-activation').filter(':checked').each(function () {
                const aValue = parseInt($(this).parents('td').prev('td').find('input.proportion').val(), 10);
                sumOfVals += isNaN(aValue) ? 0 : aValue;
            });
            return sumOfVals === params;
        },
        jQuery.validator.format("Sum portion of active key results must be {0}")
    );

    $('input#proportionSum').rules('add', {sumActiveProportion: 100});

    // define a custom validator and apply to 2 fields: objective inactive activation & key result inactive justification
    $.validator.addMethod("requiredIfDeactivate", function (value, element, eleOrSelector) {
            const activated = $(eleOrSelector).is(':checked');
            return activated || value.trim().length;
        },
        jQuery.validator.format("Inactive justification is required")
    );
    $('input#objInactiveJustification').rules('add', {requiredIfDeactivate: '#objectiveActivation'});
    $('#objectiveActivation').on('change', function() {
        objectiveValidator.element('#objInactiveJustification');
    })
    const objectiveProp = $('#objectiveProportion');
    objectiveProp.rules('add', {integer: true});
    objectiveProp.on('change', function() {
        const newValue = parseInt($(this).val());
        const newTotal = isNaN(newValue) ? sumOfOtherObjectivesProportions : (sumOfOtherObjectivesProportions + newValue) ;
        $('#spanSumObjectivesProportions').text(newTotal);
        $('#totalObjectiveProportionHidden').val(newTotal);
        objectiveValidator.element('#totalObjectiveProportionHidden');
    })
    function validateTotalActiveProportion() {
        keyResultFormValidator.element('#proportionSum');
    }

    function validateInactiveJustification() {
        const inputJustification = $(this).parents('td').next('td').find('input.key-result-justification');
        keyResultFormValidator.element(inputJustification);
    }

    function removeCurrentRow() {
        const row = $(this).parents('tr.row-key-result');
        table.row(row).remove().draw();
        validateTotalActiveProportion();
    }
})
