$(document).ready(function () {
    $('.update-batch-button').on('click', function () {
        var batchId = $(this).data('batch-id');
        $('#batchId').val(batchId);

        var url = '/admin/find-batch-by-id?id=' + batchId;

        $.get(url, function (batchDto) {
            if (batchDto === null || Object.keys(batchDto).length === 0) {
                $('#batchNotFoundAlert').removeClass('d-none');
                $('#updateBatchModal').on('shown.bs.modal', function () {
                    $('#updateBatchModal').modal('hide');
                });

            } else {
                $('#editBatchModal_batchId').val(batchDto.id);
                $('#editBatchModal_batchTime').val(batchDto.batchTime);
                $('#batchNotFoundAlert').addClass('d-none');
                $('#updateBatchModal').modal('show');
            }
        });
    });
});
