const validate_quarterly_Day = function () {
  var StartDate = $("#quarterly_beginDay").val();
  var EndDate = $("#quarterly_endDay").val();

  var sDate = parseInt(StartDate);
  var eDate = parseInt(EndDate);
  if (sDate >= eDate) {
    Swal.fire("The begin day must be ealier than the end day");
    $(this).val("");
  }
};

const validate_monthly_Day = function () {
  var StartDate = $("#monthly_beginDay").val();
  var EndDate = $("#monthly_endDay").val();
  var sDate = parseInt(StartDate);
  var eDate = parseInt(EndDate);
  if (sDate >= eDate) {
    Swal.fire("The begin day must be ealier than the end day");
    $(this).val("");
  }
};

const ajaxSubmit = function (field) {
  var formData = new FormData($(field)[0]);
  axios
    .post("/okr-schedule-edit/save", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then(function (response) {
      Swal.fire("Successfully!", "Updated!", "success").then(function () {
        window.location.href = "/okr-schedule-edit";
      });
    })
    .catch((error) => {
      if (
        error.response.data.message === null ||
        error.response.data.message === undefined
      )
        Swal.fire("Error!", "Some errors occur", "error");
      else Swal.fire("Error!", error.response.data.message, "error");
    });
};

$().ready(function () {
  $("#quarterly_select,#monthly_select,#weekly_select").select2({
    placeholder: "Select..",
    maximumSelectionLength: 3,
  });

  $("#quarterly_closeAfterDays").on("change", function () {
    $("#quarterly_select").val("");
    $("#quarterly_select").trigger("change");
  });
  $("#monthly_closeAfterDays").on("change", function () {
    $("#monthly_select").val("");
    $("#monthly_select").trigger("change");
  });
  $("#weekly_closeAfterDays").on("change", function () {
    $("#weekly_select").val("");
    $("#weekly_select").trigger("change");
  });

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

  const compareValidate = function (field, day, data) {
    var el = $(field);
    var limitDay = parseInt(day);
    var selectedDay = parseInt(data);
    if (limitDay <= selectedDay) {
      Swal.fire(
        "The remind before day must be ealier than the close after day"
      );

      var arr = el.val();
      arr = arr.filter(function (item) {
        return item !== data;
      });
      el.val(arr);
      el.trigger("change");
    }
  };

  $("#quarterly_select").on("select2:select", function (e) {
    compareValidate(
      "#quarterly_select",
      $("#quarterly_closeAfterDays").val(),
      e.params.data.text
    );
  });

  $("#monthly_select").on("select2:select", function (e) {
    compareValidate(
      "#monthly_select",
      $("#monthly_closeAfterDays").val(),
      e.params.data.text
    );
  });

  $("#weekly_select").on("select2:select", function (e) {
    compareValidate(
      "#weekly_select",
      $("#weekly_closeAfterDays").val(),
      e.params.data.text
    );
  });

  const submitValidate = function (data,field) {
    const arr = [...data];
    const isNull = arr.some((e) => !e); //value is null-> return false
    if (isNull) {
      Swal.fire("Error!", "Please check your select", "error");
    } else {
      ajaxSubmit(field);
    }
  };

  $("#quarterly_form").validate({
    submitHandler: function () {
      var data = [
        $("#quarterly_beginDay").val(),
        $("#quarterly_endDay").val(),
        $("#quarterly_closeAfterDays").val(),
      ];
      submitValidate(data,"#quarterly_form");
    },
  });
  $("#monthly_form").validate({
    submitHandler: function () {
      var data = [
        $("#monthly_beginDay").val(),
        $("#monthly_endDay").val(),
        $("#monthly_closeAfterDays").val(),
      ];
      submitValidate(data,"#monthly_form");
    },
  });
  $("#weekly_form").validate({
    submitHandler: function () {
      var data = [
        $("#weekly_beginDay").val(),
        $("#weekly_endDay").val(),
        $("#weekly_closeAfterDays").val(),
      ];
      submitValidate(data,"#weekly_form");
    },
  });
});
