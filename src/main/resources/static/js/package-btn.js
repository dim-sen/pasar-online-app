$(document).ready(function () {
    $('#editButton').on('click', function () {
        $('#editPackageModal_packageName').prop('disabled', false);
        $('#editPackageModal_packagePrice').prop('disabled', false);
        $('#editPackageModal_packageWeight').prop('disabled', false);
        $('#editPackageModal_packageDescription').prop('disabled', false);
        $('#file').prop('disabled', false);
        $(this).hide();
    });

    $('#editPackageModal_packageName,' +
        '#editPackageModal_packagePrice,' +
        '#editPackageModal_packageWeight,' +
        '#editPackageModal_packageDescription,' +
        '#file').on('input', function () {
        $('#updatePackageButton').prop('disabled', false);
    });

    $('#updatePackageModal').on('hidden.bs.modal', function () {
        $('#editPackageModal_packageName').prop('disabled', true);
        $('#editPackageModal_packagePrice').prop('disabled', true);
        $('#editPackageModal_packageWeight').prop('disabled', true);
        $('#editPackageModal_packageDescription').prop('disabled', true);
        $('#file').prop('disabled', true);
        $('#updatePackageButton').prop('disabled', true);
        $('#editButton').show();
    });
});
