$(document).ready(function() {
    let quarter = $("#current_quarter").val();
    let okr_type = $("#okr_type").val();
    let okr_seq = $("#okr_seq").val();
    let $member_select = $("#member_select");
    $member_select.select2({
        placeholder: "Find members ...",
        allowClear: true
    });

    $member_select.on("change", function (){
        let quarter = $("#current_quarter").val();
        handleListItemSelected("company", "");
        if ($(this).val() === "") renderContent(generateUrl("company"), {quarter: quarter, seq: "", type: "company"});
        else renderContent(generateUrl("member"), {quarter: quarter, seq: $(this).val(), type: "member"});
    })

    let generateUrl = function (okr_type){
        switch (okr_type){
            case "company": return "/api/companies/okrs/quarterly"
            case "member": return "/api/members/okrs/quarterly"
            case "team": return "/api/teams/okrs/quarterly"
            default: return "/api/companies/okrs/quarterly/dashboard"
        }
    }

    let url = generateUrl(okr_type);

    renderContent(url, {quarter: quarter, seq: okr_seq, type: okr_type});

    $(".main-menu-item").removeClass("active")
    $("#quarterly_tab").addClass("active")

    let $okr_list = $(".okr-list");
    $okr_list.on("click", function (){
        let type = ($(this).attr("data-okr"));
        let seq = ($(this).attr("data-seq"));
        handleListItemSelected(type, seq);
        redirectAction(type, seq);
    })

    let redirectAction = function (type, seq){
        let quarter = $("#current_quarter").val();
        switch (type){
            case "view_all_members":
                console.log("TO ALL MEMBERS");
                break;
            case "view_all_teams":
                console.log("TO ALL TEAMS");
                break;
            case "member":
                renderContent(generateUrl("member"), {quarter: quarter , seq: seq, type: "member"});
                break;
            case "team":
                renderContent(generateUrl("team"), {quarter: quarter, seq: seq, type: "team"});
                break;
        }
    }

    window.onpopstate = function(event) {
        renderContent(event.state.url, event.state, true);
        handleListItemSelected(event.state.type, event.state.seq);
    };

    let handleListItemSelected = function (type, seq){
        $okr_list.css("background-color", "");
        $("#" + type + seq).css("background-color", "#e9eef1")
    }
});