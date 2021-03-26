const validate_quarterly_Day = function () {
  var StartDate = $("#quarterly_beginDay").val();
  var EndDate = $("#quarterly_endDay").val();

  var sDate = parseInt(StartDate);
  var eDate = parseInt(EndDate);
  if (sDate > eDate) {
    Swal.fire("The begin day must be ealier than the end day");
    $(this).val("");
  }
};

const validate_monthly_Day = function () {
  var StartDate = $("#monthly_beginDay").val();
  var EndDate = $("#monthly_endDay").val();
  var sDate = parseInt(StartDate);
  var eDate = parseInt(EndDate);
  if (sDate > eDate) {
    Swal.fire("The begin day must be ealier than the end day");
    $(this).val("");
  }
};

$().ready(function () {
  $("#quarterly_select,#monthly_select,#weekly_select").select2({
    placeholder: "Select..",
    maximumSelectionLength: 3,
  });

  $("#quarterly_closeAfterDays").on("change",function(){
    $("#quarterly_select").val("");
    $('#quarterly_select').trigger("change");
  })
  $("#monthly_closeAfterDays").on("change",function(){
    $("#monthly_select").val("");
    $('#monthly_select').trigger("change");
  })
  $("#weekly_closeAfterDays").on("change",function(){
    $("#weekly_select").val("");
    $('#weekly_select').trigger("change");
  })

  $("#quarterly_beginDay").on("change", validate_quarterly_Day);
  $("#quarterly_endDay").on("change", validate_quarterly_Day);
  $("#monthly_beginDay").on("change", validate_monthly_Day);
  $("#monthly_endDay").on("change", validate_monthly_Day);

  $("#quarterly_select,#monthly_select,#weekly_select").on(
    "select2:unselecting",
    function () {
      Swal.fire({
        text: "Are you sure to remove this schedule?",
        icon: "warning",
        confirmButtonText: "Yes",
      });
    }
  );
  

  $("#quarterly_select").on("select2:select", function (e) {
    var day = $("#quarterly_closeAfterDays").val();
    var limitDay = parseInt(day);
    var data = e.params.data.text;
    var selectedDay= parseInt(data);

    if (limitDay < selectedDay) {
      Swal.fire("The remind before day must be ealier than the close after day");
      var arr=$(this).val();
      arr=arr.filter(function(item){
        return item!==data
      });
      $("#quarterly_select").val(arr);
      $('#quarterly_select').trigger("change");
    }
  });

  $("#monthly_select").on("select2:select", function (e) {
    var day = $("#monthly_closeAfterDays").val();
    var limitDay = parseInt(day);
    var data = e.params.data.text;
    var selectedDay= parseInt(data);

    if (limitDay < selectedDay) {
      Swal.fire("The remind before day must be ealier than the close after day");
      var arr=$(this).val();
      arr=arr.filter(function(item){
        return item!==data
      });
      $("#monthly_select").val(arr);
      $('#monthly_select').trigger("change");
    }
  });

  $("#weekly_select").on("select2:select", function (e) {
    var day = $("#weekly_closeAfterDays").val();
    var limitDay = parseInt(day);
    var data = e.params.data.text;
    var selectedDay= parseInt(data);

    if (limitDay < selectedDay) {
      Swal.fire("The remind before day must be ealier than the close after day");
      var arr=$(this).val();
      arr=arr.filter(function(item){
        return item!==data
      });
      $("#weekly_select").val(arr);
      $('#weekly_select').trigger("change");
    }
  });

});
