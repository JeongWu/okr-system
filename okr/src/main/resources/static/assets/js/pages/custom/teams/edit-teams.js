"use strict";

// Class definition
var KTUserEdit = (function () {
  // Base elements
  var avatar;

  var initUserForm = function () {
    avatar = new KTImageInput("kt_user_edit_avatar");
  };


$("#submitBtn").click(function (e) {
    e.preventDefault();
    console.log("submit event!!")
    $("#kt_form").submit();
});

  return {
    // public functions
    init: function () {
      initUserForm();
    },
  };
})();

jQuery(document).ready(function () {
  KTUserEdit.init();
});
