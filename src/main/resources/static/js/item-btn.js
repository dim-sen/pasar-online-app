$(document).ready(function () {
    $('#editButton').on('click', function () {
        $('#editItemModal_itemName').prop('disabled', false);
        $('#editItemModal_itemPrice').prop('disabled', false);
        $('#editItemModal_itemWeight').prop('disabled', false);
        $('#editItemModal_itemStock').prop('disabled', false);
        $('#editItemModal_itemDescription').prop('disabled', false);
        $('#editItemModal_itemCategory').prop('disabled', false);
        $('#file').prop('disabled', false);
        $(this).hide();
    });

    $('#editItemModal_itemName,' +
        '#editItemModal_itemPrice,' +
        '#editItemModal_itemWeight,' +
        '#editItemModal_itemStock,' +
        '#editItemModal_itemDescription,' +
        '#editItemModal_itemCategory,' +
        '#file').on('input', function () {
        $('#updateItemButton').prop('disabled', false);
    });

    $('#updateItemModal').on('hidden.bs.modal', function () {
        $('#editItemModal_itemName').prop('disabled', true);
        $('#editItemModal_itemPrice').prop('disabled', true);
        $('#editItemModal_itemWeight').prop('disabled', true);
        $('#editItemModal_itemStock').prop('disabled', true);
        $('#editItemModal_itemDescription').prop('disabled', true);
        $('#editItemModal_itemCategory').prop('disabled', true);
        $('#file').prop('disabled', true);
        $('#updateItemButton').prop('disabled', true);
        $('#editButton').show();
    });
});
