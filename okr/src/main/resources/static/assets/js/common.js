function buildPageSizeSelection() {
  let baseUrl = document.getElementById("base-url").value;
  let pageSize = document.getElementById("page-size-selection");
  pageSize.addEventListener("change", () => {
    window.location.href = baseUrl + "?size=" + pageSize.value;
  });
}

function renderImagesOnList(data, field, path, prop) {
  let visibleImages = 5;
  let result = `<div class="d-flex align-items-center">
             <div class="symbol-group symbol-hover">`;

  let listData = data[field];
  for (let i = 0; i < Math.min(listData.length, visibleImages); i++) {
    result += `<a class="symbol symbol-30 symbol-circle" data-toggle="tooltip" title="${
      listData[i].name
    }" href="${prop !== undefined ? path + "=" + listData[i][prop] : "#"}">
                 <img class="" src="${getImagesUrl(
                   listData[i].image
                 )}" alt="photo">
             </a>`;
  }
  if (listData.length > visibleImages) {
    result += `<div class="symbol symbol-30 symbol-circle symbol-light">
                  <span class="symbol-label font-weight-bold">
                    ${listData.length - visibleImages}+
                  </span>
             </div>`;
  }
  result += `</div></div>`;
  return result;
}

function makeImageSymbol(data, size, shape) {
  let output = "";
  const { name, image } = data;

  let stateNo = KTUtil.getRandomInt(0, 7);
  let states = [
    "success",
    "primary",
    "danger",
    "success",
    "warning",
    "dark",
    "primary",
    "info",
  ];
  let state = states[stateNo];

  output =
    '<div class="symbol symbol-' +
    (size === "big" ? "40" : "30") +
    " " +
    (shape === "circle" && "symbol-circle") +
    " symbol-light-" +
    state +
    ' flex-shrink-0" data-toggle="tooltip" title="' +
    name +
    '">';

  if (image === null) {
    output +=
      '<span class="symbol-label font-size-' +
      (size === "big" && "h4") +
      '">' +
      name.substring(0, 1) +
      "</span>";
  } else {
    output += '<img class="" src="' + image + '" alt="photo">';
  }

  output += "</div>";
  return output;
}

function renderActiveStatusOnList(data) {
  let status = {
    1: { title: "Active", state: "success" },
    2: { title: "inActive", state: "danger" },
  };
  let statusNo = data.useFlag === "Y" ? 1 : 2;
  return `<span class="label label-${status[statusNo].state} label-dot mr-2"></span>
            <span class="font-weight-bold text-${status[statusNo].state}">${status[statusNo].title}</span>`;
}

function getImagesUrl(path, prefix) {
  if (path === null) return "/image/default.jpg";
  return path;
}

function applyRedTextToSpan(text) {
  return `<span style="color: red">${text}</span>`;
}

function successAlert(message) {
  $.bootstrapGrowl(message, {
    type: "alert alert-custom alert-light-primary show mb-5",
    align: "center",
    width: "auto",
    allow_dismiss: false,
  });
}

function failAlert(message) {
  $.bootstrapGrowl(message, {
    type: "alert alert-custom alert-light-danger show mb-5",
    align: "center",
    width: "auto",
    allow_dismiss: false,
  });
}

function disableButton(e) {
  $(e).attr("disabled", "disabled");
}

function enableButton(e, timeout) {
  setTimeout(() => {
    $(e).removeAttr("disabled");
  }, timeout);
}

// yyyy/MM/dd hh:mm
function formatInstant(instant) {
  if (instant === null) return "";
  let date = new Date(instant);
  let year = date.getFullYear();
  let month = addZeroToTime(date.getMonth() + 1);
  let day = addZeroToTime(date.getDate());
  let hour = addZeroToTime(date.getHours());
  let minute = addZeroToTime(date.getMinutes());
  return year + "/" + month + "/" + day + " " + hour + ":" + minute;
}

// yyyy/MM/dd hh:mm:dd
function formatInstantWithSecond(instant) {
  if (instant === null) return "";
  let date = new Date(instant);
  let result = formatInstant(instant);
  let second = addZeroToTime(date.getSeconds());
  return result + ":" + second;
}

function addZeroToTime(time) {
  if (time < 10) return "0" + time;
  return time;
}

function getCurrentDateInISOFormat() {
  let date = new Date();
  return new Date(date.getTime() - date.getTimezoneOffset() * 60000)
    .toISOString()
    .split("T")[0];
}

function getOffsetDateInISOFormat(dayOffset) {
  let today = new Date();
  let offsetDate = new Date(today.setDate(today.getDate() + dayOffset));
  return getDateInISOFormat(offsetDate);
}

function getDateInISOFormat(date) {
  return new Date(date.getTime() - date.getTimezoneOffset() * 60000)
    .toISOString()
    .split("T")[0];
}

function shortenString(content, length) {
  if (content === null) return "";
  if (content.length <= length) return content;
  return content.substring(0, length) + "...";
}

function renderStringWithTooltip(content, length) {
  let shorten = shortenString(content, length);
  return `<span data-toggle="tooltip" title="${content}">${shorten}</span>`;
}

function ajaxCall(url, method, data, onSuccess, onError) {
  $.ajax({
    url: url,
    method: method,
    contentType: "application/json",
    data: JSON.stringify({
      data: data,
    }),
    success: onSuccess ? onSuccess : onSuccessDefault,
    error: onError ? onError : onErrorDefault,
  });
}

let onSuccessDefault = function (result) {
  Swal.fire("Successfully!", "Updated!", "success");
};

let onErrorDefault = function (xhr, status, error) {
  let response = JSON.parse(xhr.responseText);
  Swal.fire(
    "Error!",
    response.message !== "No message available" ? response.message : "Error!",
    "error"
  );
};

function loadingIcon() {
  return `<div class="text-center">
                <div class="spinner-border" role="status">
                    <span class="sr-only">Loading...</span>
                </div>
            </div>`;
}

function inputImage(field) {
  var avatar = new KTImageInput(field);
  avatar.on("cancel", function () {
    swal.fire({
      title: "Image successfully canceled !",
      type: "success",
      buttonsStyling: false,
      confirmButtonText: "Awesome!",
      confirmButtonClass: "btn btn-primary font-weight-bold",
    });
  });

  avatar.on("change", function () {
    swal.fire({
      title: "Image successfully changed !",
      type: "success",
      buttonsStyling: false,
      confirmButtonText: "Awesome!",
      confirmButtonClass: "btn btn-primary font-weight-bold",
    });
  });

  avatar.on("remove", function () {
    swal.fire({
      title: "Image successfully removed !",
      type: "error",
      buttonsStyling: false,
      confirmButtonText: "Got it!",
      confirmButtonClass: "btn btn-primary font-weight-bold",
    });
  });
}

function loadingPage() {
  let result = ` <div class="text-center">
        <div class="d-flex flex-column justify-content-center text-center pt-lg-40 pt-md-5 pt-sm-5 px-lg-0 pt-5 px-7">
            <h3 class="display4 font-weight-bolder my-7 text-dark" style="color: #986923;">Page Loading</h3>
            <p class="font-weight-bolder font-size-h2-md font-size-lg text-dark opacity-70">
                Please wait a minute<br/><br/>
            </p>
        </div>`;
  for (let i = 0; i < 6; i++) {
    result =
      result +
      `<div class="spinner-grow text-secondary mr-3" role="status"></div>`;
  }
  return result + "</div>";
}

function draggableInit(editable) {
  if (editable !== "true") {
    $(".draggable-zone").removeClass("draggable-zone");
    return;
  }
  let current_orders;
  let containers = document.querySelectorAll(".draggable-zone");
  let handleDrag = function (event) {
    if (event.newIndex === event.oldIndex) return;
    let movedElement = current_orders.splice(event.oldIndex, 1);
    current_orders.splice(event.newIndex, 0, movedElement[0]);
    let data = [];
    for (let i = 0; i < current_orders.length; i++) {
      let item = current_orders[i];
      if (item.priority != i + 1) {
        item.priority = i + 1;
        data.push(item);
      }
    }
    ajaxCall("/api/objectives/update-priority", "POST", data, function () {
      let items = $(".draggable-zone").find(".draggable");
      for (let i = 0; i < items.length; i++) {
        let $item = $(items[i]);
        $item.find(".card-label").html("Objective #" + (i + 1));
      }
      Swal.fire("Successfully!", "Objective priorities updated!", "success");
    });
  };

  let saveCurrentOrders = function () {
    current_orders = [];
    let items = $(".draggable-zone").find(".draggable");
    for (let i = 0; i < items.length; i++) {
      current_orders.push({
        objectiveSeq: $(items[i]).attr("data-seq"),
        priority: $(items[i]).attr("data-priority"),
      });
    }
  };
  saveCurrentOrders();

  if (containers.length !== 0) {
    let swappable = new Sortable.default(containers, {
      draggable: ".draggable",
      handle: ".draggable .draggable-handle",
      mirror: {
        appendTo: "body",
        constrainDimensions: true,
      },
    });

    swappable.on("sortable:stop", (event) => {
      handleDrag(event);
    });
  }
}

renderContent = function (url, data, isBack) {
  if (!isBack) {
    const currentUrl = new URL(window.location);
    currentUrl.searchParams.set("type", data.type);
    currentUrl.searchParams.set("seq", data.seq);
    currentUrl.searchParams.set("quarter", data.quarter);
    data.url = url;
    history.pushState(data, null, currentUrl.toString());
  }

  let $content_div = $("#content");
  $content_div.html(loadingPage());
  $.ajax({
    url: url,
    data: data,
    success(data) {
      $content_div.html(data);
    },
    error(error) {
      Swal.fire("Error!", "Some errors occur while loading page", "error");
    },
  });
};

initPage = function () {
  $(".back-btn").on("click", function () {
    history.back();
  });
};
