var DivisionFormControls = {
    init: function() {
        FormValidation.formValidation(document.getElementById("division_form"), {
            fields: {
                name: {
                    validators: {
                        notEmpty: {
                            message: "Division Name (English) is required"
                        }
                    }
                },
                localName: {
                    validators: {
                        notEmpty: {
                            message: "Division Name (Korean) is required"
                        }
                    }
                }
            },
            plugins: {
                // trigger: new FormValidation.plugins.Trigger,
                // bootstrap: new FormValidation.plugins.Bootstrap,
                // submitButton: new FormValidation.plugins.SubmitButton,
                // defaultSubmit: new FormValidation.plugins.DefaultSubmit
            }
        })
    }
};
jQuery(document).ready((function() {
    DivisionFormControls.init()
}));