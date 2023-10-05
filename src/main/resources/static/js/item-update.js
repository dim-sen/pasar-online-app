$(document).ready(function () {
    $('.update-item-button').on('click', function () {
        var itemId = $(this).data('item-id');
        $('#itemId').val(itemId);

        var url = '/admin/find-item-by-id?id=' + itemId;

        $.get(url, function (itemDto) {
            if (itemDto === null || Object.keys(itemDto).length === 0) {
                $('#itemNotFoundAlert').removeClass('d-none');
                $('#updateItemModal').on('shown.bs.modal', function () {
                    $('#updateItemModal').modal('hide');
                });

            } else {
                console.log("itemDto: " + itemDto)
                console.log("itemDto.categoryDao: " + itemDto.categoryDao)
                console.log("itemDto.categoryDao.id: " + itemDto.categoryDao.id)
                console.log("itemDto.categoryDao.categoryName: " + itemDto.categoryDao.categoryName)
                $('#editItemModal_itemId').val(itemDto.id);
                $('#editItemModal_itemName').val(itemDto.itemName);
                $('#editItemModal_itemPrice').val(itemDto.itemPrice);
                $('#editItemModal_itemWeight').val(itemDto.itemWeight);
                $('#editItemModal_itemStock').val(itemDto.itemStock);
                $('#editItemModal_itemDescription').val(itemDto.itemDescription);
                $('#editItemModal_itemCategory').val(itemDto.categoryDao.id);

                if (itemDto.itemImage != null) {
                    $('#editItemModal_itemImage').attr('src', 'data:image/jpeg;base64,' + itemDto.itemImage);
                } else {
                    $('#editItemModal_itemImage').attr('src', '');
                }
                $('#itemNotFoundAlert').addClass('d-none');
                $('#updateItemModal').modal('show');
            }
        });
    });
});
