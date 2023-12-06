$(document).ready(function () {
    $('.update-item-button').on('click', function () {
        var itemId = $(this).data('item-id');
        $('#itemId').val(itemId);

        var url = '/admin/find-item-by-id?id=' + itemId;

        $.get(url, function (itemDao) {
            if (itemDao === null || Object.keys(itemDao).length === 0) {
                $('#itemNotFoundAlert').removeClass('d-none');
                $('#updateItemModal').on('shown.bs.modal', function () {
                    $('#updateItemModal').modal('hide');
                });

            } else {
                $('#editItemModal_itemId').val(itemDao.id);
                $('#editItemModal_itemName').val(itemDao.itemName);
                $('#editItemModal_itemPrice').val(itemDao.itemPrice);
                $('#editItemModal_itemWeight').val(itemDao.itemWeight);
                $('#editItemModal_itemStock').val(itemDao.itemStock);
                $('#editItemModal_itemDescription').val(itemDao.itemDescription);
                $('#editItemModal_itemCategory').val(itemDao.categoryDao.id);

                if (itemDao.itemImage != null) {
                    $('#editItemModal_itemImage').attr('src', 'data:image/jpeg;base64,' + itemDao.itemImage);
                } else {
                    $('#editItemModal_itemImage').attr('src', '');
                }
                $('#itemNotFoundAlert').addClass('d-none');
                $('#updateItemModal').modal('show');
            }
        });
    });
});
