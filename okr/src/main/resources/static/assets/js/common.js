function buildPageSizeSelection(){
    let baseUrl = document.getElementById("base-url").value;
    let pageSize = document.getElementById("page-size-selection");
    pageSize.addEventListener("change", ()=>{
        window.location.href = baseUrl+"?size="+pageSize.value
    })
}

function renderImagesOnList(data, field, path, prop) {
    let visibleImages = 5;
    let result =
        `<div class="d-flex align-items-center">
             <div class="symbol-group symbol-hover">`;

    let listData = data[field];
    for (let i = 0; i < Math.min(listData.length, visibleImages); i++) {
        result +=
            `<a class="symbol symbol-30 symbol-circle" data-toggle="tooltip" title="${listData[i].name}" href="${path}=${listData[i][prop]}">
                 <img class="" src="${getImagesUrl(listData[i].image, "avatar")}" alt="photo">
             </a>`;
    }
    if (listData.length > visibleImages){
        result +=
            `<div class="symbol symbol-30 symbol-circle symbol-light">
                  <span class="symbol-label font-weight-bold">
                    ${listData.length - visibleImages}+
                  </span>
             </div>`
    }
    result += `</div></div>`;
    return result;
}

function renderActiveStatusOnList(data) {
    let status = {
        1: { title: "Active", state: "success" },
        2: {title: "inActive", state: "danger"},
    };
    let statusNo = data.useFlag === "Y" ? 1 : 2;
    return `<span class="label label-${status[statusNo].state} label-dot mr-2"></span>
            <span class="font-weight-bold text-${status[statusNo].state}">${status[statusNo].title}</span>`
}

function getImagesUrl(path, prefix){
    if (path === null) return "/image/default.jpg"
    return "/" + prefix + "/" + path;
}

function applyRedTextToSpan(text){
    return `<span style="color: red">${text}</span>`
}