$(document).ready(function () {
    $('.update-package-button').on('click', function () {
        var packageId = $(this).data('package-id');
        $('#packageId').val(packageId);

        var url = '/admin/find-package-by-id?id=' + packageId;

        $.get(url, function (packageDto) {
            if (packageDto === null || Object.keys(packageDto).length === 0) {
                $('#packageNotFoundAlert').removeClass('d-none');
                $('#updatePackageModal').on('shown.bs.modal', function () {
                    $('#updatePackageModal').modal('hide');
                });

            } else {
                $('#editPackageModal_packageId').val(packageDto.id);
                $('#editPackageModal_packageName').val(packageDto.packageName);
                $('#editPackageModal_packagePrice').val(packageDto.packagePrice);
                $('#editPackageModal_packageWeight').val(packageDto.packageWeight);
                $('#editPackageModal_packageDescription').val(packageDto.packageDescription);

                if (packageDto.packageImage != null) {
                    $('#editPackageModal_packageImage').attr('src', 'data:image/jpeg;base64,' + packageDto.packageImage);
                } else {
                    $('#editPackageModal_packageImage').attr('src', '');
                }
                $('#packageNotFoundAlert').addClass('d-none');
                $('#updatePackageModal').modal('show');
            }
        });
    });
});
