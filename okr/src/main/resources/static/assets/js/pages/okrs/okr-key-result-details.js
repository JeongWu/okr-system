$(document).ready(function() {
    let shortenStringLength = 15;
    let url = new URL(window.location);
    let sourceSeq = url.searchParams.get("seq");

    let KTDatatablesDataSourceAjaxServer = function() {

        let initTable1 = function() {
            let table = $('#kt_datatable').DataTable({
                dom: `
            <'row'
                <'col-sm-12'tr>
            >
            <'row' 
                <'col-sm-12 col-md-7'p>
                <'col-sm-12 col-md-2 pl-15 pt-2'l>
                <'col-sm-12 col-md-3'i>
            >`,
                responsive: true,
                searchDelay: 500,
                processing: true,
                serverSide: true,
                language: {
                    lengthMenu: "_MENU_",
                    info: "Showing _START_ - _END_ of _TOTAL_",
                    infoFiltered: "",
                    loadingRecords: '&nbsp;',
                    processing: '<i class="fa fa-spinner fa-spin fa-3x fa-fw"></i><span class="sr-only">Loading...</span> '
                },
                ajax: {
                    url: '/api/key-result-histories/datatables',
                    type: 'POST',
                    contentType: 'application/json',
                    dataSrc: "data",
                    data: function(d) {
                        d.keyResultSeq = sourceSeq;
                        return JSON.stringify(d);
                    },
                },
                buttons: [
                    'print',
                    'copyHtml5',
                    'excelHtml5',
                    'csvHtml5',
                    'pdfHtml5',
                ],
                columnDefs: [
                    {
                        targets: [1, 2, 3, 4, 5, 6, 7, 8],
                        sortable: false
                    },
                    {
                        targets: '_all',
                        className: 'text-center',
                    }
                ],
                order: [[ 0, "desc" ]],
                columns: [
                    {
                        target: 0,
                        title: "#",
                        data: 'historySeq',
                    }, {
                        target: 1,
                        title: "KEY_RESULT",
                        data: 'keyResult'
                    },
                    {
                        target: 2,
                        title: "PROPORTION",
                        data: 'proportion',
                        render: function (data){
                            return data + "%";
                        }
                    },
                    {
                        target: 3,
                        title: "PROGRESS",
                        data: 'progress',
                        render: function (data){
                            return data + "%";
                        }
                    },
                    {
                        target: 4,
                        title: "LATEST UPDATE DATE",
                        data: 'latestUpdateDt',
                        render: function (data){
                            return formatInstant(data,"-");
                        }
                    },
                    {
                        target: 5,
                        title: "CLOSE DATE",
                        data: 'closeDate',
                        render: function (data){
                            return formatInstant(data,"-");
                        }
                    },
                    {
                        target: 6,
                        title: "CLOSE JUSTIFICATION",
                        data: 'closeJustification',
                        render: function (data){
                            return renderStringWithTooltip(data,shortenStringLength);
                        }
                    },
                    {
                        target: 7,
                        title: "JUSTIFICATION",
                        data: 'justification',
                        render: function (data){
                            return renderStringWithTooltip(data,shortenStringLength);
                        }
                    },
                    {
                        target: 8,
                        title: "MOD",
                        data: 'updatedDate',
                        render: function (data){
                            return formatInstant(data, "-");
                        }
                    },
                ],
            });
        };
        return {
            init: function() {
                initTable1();
            },
        };
    }();

    initPage();
    KTDatatablesDataSourceAjaxServer.init();

    let card = new KTCard("keyResult_card");
    let card2 = new KTCard("relation_card");
    let card3 = new KTCard("feedback_card");
    let card4 = new KTCard("history_card");

    let $like_btn = $(".like-btn");
    $like_btn.each(function (){
        let liked = $(this).attr("data-liked");
        renderLikedBtn(this, liked);
    });

});










