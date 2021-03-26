$().ready(function () {
    $("#submitBtn").click(function () {
        var formData = new FormData(document.getElementById('kt_form'));
        axios.post('/profile/save', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then(function (response) {
            Swal.fire("Successfully!", "Updated!", "success")
                .then(function () {
                    window.location.href = "/"
                })
        }).catch(error => {
            if (error.response.data.message === null || error.response.data.message === undefined)
                Swal.fire("Error!", "Some errors occur", "error");
            else Swal.fire("Error!", error.response.data.message, "error");
        });
    });
    inputImage("kt_user_edit_avatar");
})