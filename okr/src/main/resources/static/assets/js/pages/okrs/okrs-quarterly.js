$(document).ready(function () {
    let quarter = $("#current_quarter").val();
    let okr_type = $("#okr_type").val();
    let okr_seq = $("#okr_seq").val();
    let $member_select = $("#member_select");
    $member_select.select2({
        placeholder: "Find members ...",
        allowClear: true
    });

    $member_select.on("change", function () {
        let quarter = $("#current_quarter").val();
        handleListItemSelected("company", "");
        if ($(this).val() === "") renderContent(generateUrl("company"), {quarter: quarter, seq: "", type: "company"});
        else renderContent(generateUrl("member"), {quarter: quarter, seq: $(this).val(), type: "member"});
    })

    let url = generateUrl(okr_type);

    renderContent(url, {quarter: quarter, seq: okr_seq, type: okr_type}, false);

    $(".main-menu-item").removeClass("active")
    $("#quarterly_tab").addClass("active")

    let $okr_list = $(".okr-list");
    $okr_list.on("click", function () {
        let type = ($(this).attr("data-okr"));
        let seq = ($(this).attr("data-seq"));
        handleListItemSelected(type, seq);
        redirectAction(type, seq);
    })

    let redirectAction = function (type, seq) {
        let quarter = $("#current_quarter").val();
        switch (type) {
            case "view_all_members":
                console.log("TO ALL MEMBERS");
                break;
            case "view_all_teams":
                console.log("TO ALL TEAMS");
                break;
            case "member":
                renderContent(generateUrl("member"), {quarter: quarter, seq: seq, type: "member"});
                break;
            case "team":
                renderContent(generateUrl("team"), {quarter: quarter, seq: seq, type: "team"});
                break;
        }
    }

    window.onpopstate = function (event) {
        if (event.state == null) {
            history.back();
            return;
        }
        renderContent(event.state.url, event.state, true);
        handleListItemSelected(event.state.type, event.state.seq);
    };

    let handleListItemSelected = function (type, seq) {
        $okr_list.css("background-color", "");
        $("#" + type + seq).css("background-color", "#e9eef1")
    }
});

let generateUrl = function (okr_type) {
    switch (okr_type) {
        case "company":
            return "/api/companies/okrs/quarterly"
        case "member":
            return "/api/members/okrs/quarterly"
        case "team":
            return "/api/teams/okrs/quarterly"
        case "objective":
            return "/api/objectives/okrs/quarterly"
        case "keyResult":
            return "/api/key-results/okrs/quarterly"
        default:
            return "/api/companies/okrs/quarterly/dashboard"
    }
}

function draggableInit(editable) {
    if (editable !== "true") {
        $(".draggable-zone").removeClass("draggable-zone");
        return;
    }
    let current_orders;
    let containers = document.querySelectorAll('.draggable-zone');
    let handleDrag = function (event) {
        if (event.newIndex === event.oldIndex) return;
        let movedElement = current_orders.splice(event.oldIndex, 1);
        current_orders.splice(event.newIndex, 0, movedElement[0])
        let data = [];
        for (let i = 0; i < current_orders.length; i++) {
            let item = current_orders[i];
            if (item.priority != i + 1) {
                item.priority = i + 1;
                data.push(item)
            }
        }
        ajaxCall('/api/objectives/update-priority', 'POST', data, function () {
            let items = $(".draggable-zone").find(".draggable");
            for (let i = 0; i < items.length; i++) {
                let $item = $(items[i]);
                $item.find(".card-label").html("Objective #" + (i + 1));
            }
            Swal.fire("Successfully!", "Objective priorities updated!", "success");
        })
    }

    let saveCurrentOrders = function () {
        current_orders = [];
        let items = $(".draggable-zone").find(".draggable");
        for (let i = 0; i < items.length; i++) {
            current_orders.push({
                objectiveSeq: $(items[i]).attr("data-seq"),
                priority: $(items[i]).attr("data-priority"),
            })
        }
    }
    saveCurrentOrders();

    if (containers.length !== 0) {
        let swappable = new Sortable.default(containers, {
            draggable: '.draggable',
            handle: '.draggable .draggable-handle',
            mirror: {
                appendTo: 'body',
                constrainDimensions: true
            }
        });

        swappable.on('sortable:stop', (event) => {
            handleDrag(event)
        });
    }
}

renderContent = function (url, data, isBack) {
    handleHistory(url, data, isBack)

    let $content_div = $("#content");
    $content_div.html(loadingPage())
    $.ajax({
        url: url,
        data: data,
        success(data) {
            $content_div.html(data);
        },
        error(error) {
            if (error.responseJSON != null && error.responseJSON.message != null)
                Swal.fire("Error!", error.responseJSON.message, "error");
            else Swal.fire("Error!", "Some errors occur while loading page", "error");
        }
    });
};

initPage = function () {
    $(".back-btn").on("click", function () {
        history.back();
    })
}

handleHistory = function (url, oldState, isBack) {
    let data = {...oldState};
    if (!isBack) {
        const currentUrl = new URL(window.location)
        currentUrl.searchParams.set("type", data.type)
        currentUrl.searchParams.set("seq", data.seq)
        currentUrl.searchParams.set("quarter", data.quarter)
        if (["objective", "keyResult"].includes(data.type))
            currentUrl.searchParams.delete("quarter")
        data.url = url;
        history.pushState(data, null, currentUrl.toString())
    }
}

showOkrDetails = function () {
    let that = $(event.target);
    let type = that.attr("data-okr");
    let seq = that.attr("data-seq");
    renderContent(generateUrl(type), {seq: seq, type: type});
}

renderLikedBtn = function (e, liked) {
    if (liked === "true") {
        $(e).removeClass("btn-light-secondary")
        $(e).addClass("btn-light-primary")
        $(e).text("Unlike")
    } else {
        $(e).removeClass("btn-light-primary")
        $(e).addClass("btn-light-secondary")
        $(e).text("Like")
    }
}

updateLikeContent = function (sourceTable, sourceSeq, liked) {
    let $content = $("#like-content-"+ sourceTable+ "-" + sourceSeq);
    let currentText = $content.text();
    if (liked !== "true") {
        currentText = currentText.replace("You, ", "")
        currentText = currentText.replace("You and ", "")
        currentText = currentText.replace("You like this", "")
        currentText = currentText.replace("You", "")
    } else {
        if (currentText === "") currentText = "You like this"
        else if (currentText.includes("and")) currentText = "You, " + currentText;
        else currentText = "You and " + currentText;
    }
    $content.text(currentText);

}

handleLikeClick = function () {
    let that = $(event.target);
    let liked = that.attr("data-liked");
    let sourceTable = that.attr("data-sourceTable");
    let sourceSeq = that.attr("data-sourceSeq");

    that.attr("disabled", "disabled");
    let data = {sourceTable: sourceTable, sourceSeq: sourceSeq, action: liked === "true" ? "unlike" : "like"}
    ajaxPost('/api/likes', data,
        function () {
            if (liked === "true") {
                liked = "false";
                that.attr("data-liked", "false");
            } else {
                liked = "true";
                that.attr("data-liked", "true")
            }
            renderLikedBtn(that, liked);
            updateLikeContent(sourceTable, sourceSeq, liked);
            that.removeAttr("disabled");
        }, onErrorDefault)
}

handleDeleteFeedback = function () {
    let that = $(event.target);
    let feedbackSeq = that.attr("data-seq");
    let $feedback_div = that.parents(".feedback-div");

    Swal.fire({
        title: "Are you sure?",
        text: 'Do you want to delete this feedback?',
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true
    }).then(function(result) {
        if (result.value) {
            loadingUI("feedback_card");
            let data = {feedbackSeq : feedbackSeq}
            ajaxDelete("/api/feedbacks", data, function () {
                Swal.fire("Deleted!", "The feedback has been deleted.", "success");
                deleteFeedbackContent($feedback_div)
            }, onErrorDefault, ()=>{releaseUI("feedback_card")})
        } else if (result.dismiss === "cancel") {
            Swal.fire("Cancelled", "The feedback is available", "error")
        }
    });
}

deleteFeedbackContent = function (e){
    e.html("X Deleted Feedback");
    e.css("background-color", "#f7f7f7");
    e.css("height", "30px");
}

openReplyBox = function () {
    let that = event.target;
    let feedbackSeq = $(that).attr("data-seq");
    let $feedback_reply = $("#feedback-reply" + feedbackSeq);
    let image = $("#member-image").val();
    let data = {member: {image: image}, feedbackSeq: feedbackSeq}
    $feedback_reply.html(renderReplyBox(data))
}

renderReplyBox = function (feedback) {
    return `
        <div style="width: 15%; min-width: 40px; max-width: 50px; " class="pr-5 pt-4">
            <img src="${feedback.member.image ? feedback.member.image : '/image/default.jpg'}"
                 class="symbol align-self-end max-h-100px" style="max-width: 100%" alt=""/>
        </div>
        <div style="width: 82%">
            <textarea rows="2" class="form-control mb-2" id="feedback-input"></textarea>
            <div class="ml-5 mb-5">
                <span class="mr-3"><button class="btn btn-light-primary btn-sm" data-seq="${feedback.feedbackSeq}" onclick="handleAddFeedback()">Save</button></span>
                <span><button class="btn btn-light-secondary btn-sm" style="color: black" onclick="hideReplyBox()">Cancel</button></span>
            </div>
        </div>`
}

hideReplyBox = function () {
    let $reply_box = $(event.target).parents(".feedback-reply");
    $reply_box.html("")
}

handleAddFeedback = function () {
    let url = new URL(window.location);
    let $reply_box = $(event.target).parents(".feedback-reply");

    let feedback = $("#feedback-input").val();
    let sourceTable = url.searchParams.get("type").toUpperCase();
    let sourceSeq = url.searchParams.get("seq");
    let parentFeedbackSeq = $(event.target).attr("data-seq");
    let data = {sourceTable: sourceTable, sourceSeq: sourceSeq, feedback: feedback, parentFeedbackSeq: parentFeedbackSeq}
    $reply_box.html("");
    loadingUI("feedback_card");
    ajaxPost("/api/feedbacks", data, function (response){
        let $sub_feedbacks = $reply_box.parent();
        $sub_feedbacks.append(renderFeedbackContent(response));
    }, onErrorDefault, ()=>{releaseUI("feedback_card")})
}

renderFeedbackContent = function (feedback) {
    return `<div>
        <div class="feedback-div d-flex align-items-center pt-0 px-5 mb-4 offset-${feedback.depth}">
            <div style="width: 15%; min-width: 40px; max-width: 50px; text-align: center" class="pr-5">
                <img src="${feedback.member.image}"
                 class="symbol align-self-end max-h-100px" style="max-width: 100%" alt=""/>
            </div>
            <div style="width: 82%">
                <span class="text-dark font-weight-bolder text-hover-primary mb-1 font-size-lg">${feedback.member.name}</span>
                <span class="text-muted font-weight-bold d-block">${feedback.feedback}</span>
                <span class="row mt-1 ml-0">
                    <span class="mr-5">
                        <button class="mr-5 reply-btn pl-0"
                            data-seq="${feedback.feedbackSeq}"
                            style="border: none; background: none" 
                            onclick="openReplyBox()">Reply</button>
                    </span>
                <span class="mr-5">
                    <button class="mr-5"
                            data-seq="${feedback.feedbackSeq}"
                            style="border: none; background: none"
                            onclick="handleDeleteFeedback()">Delete</button>
                </span>
                <span>
                    <button class="mr-5 like-btn" style="border: none; background: none"
                            data-liked="false"
                            data-sourceTable="FEEDBACK"
                            data-sourceSeq="${feedback.feedbackSeq}"
                            onclick="handleLikeClick()">Like</button>
                </span>
                <span class="mr-5"
                      id="like-content-FEEDBACK-${feedback.feedbackSeq}"></span>
                <span class="">${formatInstantWithSecond(feedback.createdDate, "-")}</span>
                </span>
            </div>
        </div>
        <div id="feedback-reply${feedback.feedbackSeq}"
            class="feedback-reply d-flex pt-0 px-5 mb-1 offset-${feedback.depth + 1}"
            data-depth="${feedback.depth}"></div>
    </div>
</div>`
}

handleAddFeedbackForObjective = function (seq){

    let $input = $("#objective-feedback-input");
    let feedback = $input.val();
    let sourceTable = $(event.target).attr("data-sourceTable");
    let data = {sourceTable: sourceTable, sourceSeq: seq, feedback: feedback}
    loadingUI("feedback_card");
    ajaxPost("/api/feedbacks", data, function (response){
        $("#objective-input-div").before(renderFeedbackContent(response));
    }, onErrorDefault, ()=>{releaseUI("feedback_card"); $input.val("")})
}

clearInput = function (){
    let $input = $("#objective-feedback-input");
    $input.val("");
}

loadingUI = function (id){
    let UI = "#" + id;
    KTApp.block(UI);
}

releaseUI = function (id){
    let UI = "#" + id;
    KTApp.unblock(UI);
}