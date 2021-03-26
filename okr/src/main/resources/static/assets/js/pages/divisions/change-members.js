$().ready(function() {
    let divisionName = $("#division-name").val();
    let divisionId = $("#division-id").val();
    function addCurrentTimeTo(element){
        let date = new Date();
        let dateString = new Date(date.getTime() - (date.getTimezoneOffset() * 60000 ))
            .toISOString()
            .split("T")[0];

        $(element).val(dateString);
    }
    $("#add-form").find(".add-form-division-id").val(divisionId);
    $(".remove-action").on("click", function (){
        addCurrentTimeTo($("#kt_datepicker_2"));
        $("#justification-content").val("");
        let memberName = $(this).find(".member-name").val();
        let memberId = $(this).find(".member-id").val();
        let applyBeginDate = $(this).find(".division-member-applyBeginDate").val();
        $("#modal-member-id").val(memberId);
        $("#modal-division-id").val(divisionId);
        $("#modal-applyBeginDate").val(applyBeginDate);
        $("#removeModelLabel").html("Do you confirm to remove "+memberName+" from "+ divisionName+"?")
        $("#modal-active").click();
    });

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
})



