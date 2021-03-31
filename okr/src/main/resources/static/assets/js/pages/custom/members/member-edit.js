"use strict";

// Class definition
var KTWizard3 = function () {
	// Base elements
	var _wizardEl;
	var _formEl;
	var _wizard;
	var _validations = [];

	// Private functions
	var initWizard = function () {
		// Initialize form wizard
		_wizard = new KTWizard(_wizardEl, {
			startStep: 1, // initial active step number
			clickableSteps: true  // allow step clicking
		});

		// Validation before going to next page
		_wizard.on('beforeNext', function (wizard) {
			// Don't go to the next step yet
			_wizard.stop();

			// Validate form
			var validator = _validations[wizard.getStep() - 1]; // get validator for currnt step
			validator.validate().then(function (status) {
				if (status == 'Valid') {
					_wizard.goNext();
					KTUtil.scrollTop();
				} else {
					Swal.fire({
						text: "Sorry, looks like there are some errors detected, please try again.",
						icon: "error",
						buttonsStyling: false,
						confirmButtonText: "Ok, got it!",
						customClass: {
							confirmButton: "btn font-weight-bold btn-light"
						}
					}).then(function () {
						KTUtil.scrollTop();
					});
				}
			});
		});

		// Change event
		_wizard.on('change', function (wizard) {
			KTUtil.scrollTop();
		});
	}

	var initValidation = function () {
		// Init form validation rules. For more info check the FormValidation plugin's official documentation:https://formvalidation.io/
		// Step 1
		_validations.push(FormValidation.formValidation(
			_formEl,
			{
				fields: {
					name: {
						validators: {
							notEmpty: {
								message: 'Name is required'
							}
						}
					},
					localName: {
						validators: {
							notEmpty: {
								message: 'LocalName is required'
							}
						}
					},
					email: {
						validators: {
							notEmpty: {
								message: 'Email is required'
							},
							emailAddress: {
								message: 'The value is not a valid email address'
							   }
						}
					},
					contactPhone: {
						validators: {
							notEmpty: {
								message: 'Contact Phone is required'
							},
							phone: {
								country: 'KR',
								message: 'The value is not a valid US phone number'
							   }
						}
					},
					justification: {
						validators: {
							notEmpty: {
								message: 'justification is required'
							}
						}
					},

				},
				plugins: {
					trigger: new FormValidation.plugins.Trigger(),
					bootstrap: new FormValidation.plugins.Bootstrap()
				}
			}
		));

	}

	return {
		// public functions
		init: function () {
			_wizardEl = KTUtil.getById('kt_wizard_v3');
			_formEl = KTUtil.getById('kt_form');

			initWizard();
			initValidation();
			inputImage("kt_image_4");
		}
	};
}();

jQuery(document).ready(function () {
	KTWizard3.init();

	$(document).on('change', "[name='useFlag']", function(){
        var memberSeq = $('#memberSeq').val();
        var checkListSeq = $(this).attr('name');
		var checking = $(this);
         if (!$(this).is(':checked')){
             Swal.fire({
                title: "오늘 날짜로 해당 직원이 속한 조직에서 제외됩니다.",
                text: "진행하시겠습니까?",
                icon: "warning",
                showCancelButton: true,
                confirmButtonText: "네",
                cancelButtonText: "아니오",
            }).then(function(result) {
                if (result.value) {
					checking.prop("checked", false);
                } else {
					checking.prop("checked", true);
				}
            });
            }
    });

});

