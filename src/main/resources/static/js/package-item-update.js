$(document).ready(function () {
    $('.update-package-item-button').on('click', function () {
        var piId = $(this).data('pi-id');
        $('#piId').val(piId);

        var url = '/admin/find-pi-by-id?id=' + piId;

        $.get(url, function (packageItemDto) {
            if (packageItemDto === null || Object.keys(packageItemDto).length === 0) {
                $('#packageItemNotFoundAlert').removeClass('d-none');
                $('#updatePackageItemModal').on('shown.bs.modal', function () {
                    $('#updatePackageItemModal').modal('hide');
                });

            } else {
                $('#editPackageItemModal_packageItemId').val(packageItemDto.id);
                $('#editPackageItemModal_packageItemPackages').val(packageItemDto.packages.id);
                $('#editPackageItemModal_packageItemItem').val(packageItemDto.item.id);
                $('#packageItemNotFoundAlert').addClass('d-none');
                $('#updatePackageItemModal').modal('show');
            }
        });
    });
});
