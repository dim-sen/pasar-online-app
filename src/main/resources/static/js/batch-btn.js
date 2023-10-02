$(document).ready(function () {
    $('#editButton').on('click', function () {
        $('#editBatchModal_batchTime').prop('disabled', false);
        $(this).hide();
    });

    $('#editBatchModal_batchTime').on('input', function () {
        $('#updateBatchButton').prop('disabled', false);
    });

    $('#updateBatchModal').on('hidden.bs.modal', function () {
        $('#editBatchModal_batchTime').prop('disabled', true);
        $('#updateBatchButton').prop('disabled', true);
        $('#editButton').show();
    });
});
