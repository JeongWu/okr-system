let current_orders;
jQuery(document).ready(function () {
    let containers = document.querySelectorAll('.draggable-zone');
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
});

function handleDrag(event) {
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
    ajaxCall('/api/objectives/update-priority', 'POST', data, onSuccess)
}

function saveCurrentOrders() {
    current_orders = [];
    let items = $(".draggable-zone").find(".draggable");
    for (let i = 0; i < items.length; i++) {
        current_orders.push({
            objectiveSeq: $(items[i]).attr("data-seq"),
            priority: $(items[i]).attr("data-priority"),
        })
    }
}

let onSuccess = function (result) {
    let items = $(".draggable-zone").find(".draggable");
    for (let i = 0; i < items.length; i++) {
        let $item = $(items[i]);
        $item.find(".card-label").html("Objective #" + (i + 1));
    }
    Swal.fire("Successfully!", "Objective priorities updated!", "success");
}