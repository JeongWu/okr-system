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
                   listData[i].image,
                   "avatar"
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
  return "/" + prefix + "/" + path;
}

function applyRedTextToSpan(text) {
  return `<span style="color: red">${text}</span>`;
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

function inputImage(field) {
  return new KTImageInput(field);
}
