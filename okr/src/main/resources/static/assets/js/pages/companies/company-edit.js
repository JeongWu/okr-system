jQuery(document).ready(function () {
    FormValidation
        .formValidation(
            document.getElementById('companyEditForm'),{
                fields: {
                    name: {
                        validators: {
                            notEmpty: {
                                message: 'The name is required'
                            }
                        }
                    },
                    localName: {
                        validators: {
                            notEmpty: {
                                message: 'The localName is required'
                            }
                        }
                    },
                    imageFile: {
                        validators: {
                            file: {
                                extension: 'jpeg,jpg,png',
                                type: 'image/jpeg,image/png',
                                maxSize: 512000,
                                message: ' &nbsp&nbsp&nbsp&nbsp  The selected file is not valid'
                            }
                        }
                    }
                },
                plugins: {
                    trigger: new FormValidation.plugins.Trigger(),
                    bootstrap: new FormValidation.plugins.Bootstrap(),
                    submitButton: new FormValidation.plugins.SubmitButton({
                        buttons: function(form) {
                            return [].slice.call(document.getElementById('submit'));
                        },
                    }),
                }
            }
        )
        .on('core.form.valid', function() {
            let formData = new FormData();
            let companyEditForm = document.getElementById('companyEditForm');
            // Append the text fields
            formData.append('name', companyEditForm.querySelector('[name="name"]').value);
            formData.append('localName', companyEditForm.querySelector('[name="localName"]').value);
            formData.append('domain', companyEditForm.querySelector('[name="domain"]').value);
            formData.append('googleSignIn', companyEditForm.querySelector('[name="googleSignIn"]').checked);

            let avatarFiles = companyEditForm.querySelector('[name="imageFile"]').files;
            if (avatarFiles.length > 0) {
                formData.append('imageFile', avatarFiles[0]);
            }

            disableButton($("#button-submit"));
            axios.post('/api/companies', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            }).then(function(response) {
                Swal.fire("Successfully", "Company detail is updated" , "success")
                    .then(function (){
                        window.location.href = "/"
                    })
            }).catch(error => {
                if (error.response.data.message === null || error.response.data.message === undefined)
                    Swal.fire("Error!", "Some errors occur" , "error");
                else Swal.fire("Error!", error.response.data.message , "error");

                enableButton($("#button-submit"), 2000);
            });
        });

    let avatar1 = new KTImageInput('kt_image_1');
})