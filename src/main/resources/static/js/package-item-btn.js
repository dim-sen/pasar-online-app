$(document).ready(function () {
    $('#editButton').on('click', function () {
        $('#editPackageItemModal_packageItemPackages').prop('disabled', false);
        $('#editPackageItemModal_packageItemItem').prop('disabled', false);
        $(this).hide();
    });

    $('#editPackageItemModal_packageItemPackages,' +
        '#editPackageItemModal_packageItemItem').on('input', function () {
        $('#updatePackageItemButton').prop('disabled', false);
    });

    $('#updateWarehouseBatchModal').on('hidden.bs.modal', function () {
        $('#editPackageItemModal_packageItemPackages').prop('disabled', true);
        $('#editPackageItemModal_packageItemItem').prop('disabled', true);
        $('#updatePackageItemButton').prop('disabled', true);
        $('#editButton').show();
    });
});
