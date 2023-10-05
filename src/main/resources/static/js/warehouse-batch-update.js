$(document).ready(function () {
    $('.update-warehouse-batch-button').on('click', function () {
        var wbId = $(this).data('wb-id');
        $('#wbId').val(wbId);

        var url = '/admin/find-wb-by-id?id=' + wbId;

        $.get(url, function (warehouseBatchDto) {
            if (warehouseBatchDto === null || Object.keys(warehouseBatchDto).length === 0) {
                $('#warehouseBatchNotFoundAlert').removeClass('d-none');
                $('#updateWarehouseBatchModal').on('shown.bs.modal', function () {
                    $('#updateWarehouseBatchModal').modal('hide');
                });

            } else {
                $('#editWarehouseBatchModal_warehouseBatchId').val(warehouseBatchDto.id);
                $('#editWarehouseBatchModal_warehouseBatchWarehouse').val(warehouseBatchDto.warehouse.id);
                $('#editWarehouseBatchModal_warehouseBatchBatch').val(warehouseBatchDto.batch.id);
                $('#warehouseBatchNotFoundAlert').addClass('d-none');
                $('#updateWarehouseBatchModal').modal('show');
            }
        });
    });
});
