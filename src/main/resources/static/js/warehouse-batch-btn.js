$(document).ready(function () {
    $('#editButton').on('click', function () {
        $('#editWarehouseBatchModal_warehouseBatchWarehouse').prop('disabled', false);
        $('#editWarehouseBatchModal_warehouseBatchBatch').prop('disabled', false);
        $(this).hide();
    });

    $('#editWarehouseBatchModal_warehouseBatchWarehouse,' +
        '#editWarehouseBatchModal_warehouseBatchBatch').on('input', function () {
        $('#updateWarehouseBatchButton').prop('disabled', false);
    });

    $('#updateWarehouseBatchModal').on('hidden.bs.modal', function () {
        $('#editWarehouseBatchModal_warehouseBatchWarehouse').prop('disabled', true);
        $('#editWarehouseBatchModal_warehouseBatchBatch').prop('disabled', true);
        $('#updateWarehouseBatchButton').prop('disabled', true);
        $('#editButton').show();
    });
});
