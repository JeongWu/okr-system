jQuery(document).ready(function () {
  $(document).on("change", "[name='useFlag']", function () {
    var el = $(this);
    if (!$(this).is(":checked")) {
      Swal.fire({
        title: "오늘 날짜로 해당 직원이 속한 조직에서 제외됩니다.",
        text: "진행하시겠습니까?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "네",
        cancelButtonText: "아니오",
      }).then(function (result) {
        if (result.value) {
          el.prop("checked", false);
        } else if (result.dismiss === "cancel") {
          el.prop("checked", true);
        }
      });
    }
  });
});
