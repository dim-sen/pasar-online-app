$(document).ready(function () {
    $('#editButton').on('click', function () {
        $('#editWarehouseModal_warehouseName').prop('disabled', false);
        $('#editWarehouseModal_warehouseAddress').prop('disabled', false);
        $(this).hide();
    });

    $('#editWarehouseModal_warehouseName,' +
        '#editWarehouseModal_warehouseAddress').on('input', function () {
        $('#updateWarehouseButton').prop('disabled', false);
    });

    $('#updateWarehouseModal').on('hidden.bs.modal', function () {
        $('#editWarehouseModal_warehouseName').prop('disabled', true);
        $('#editWarehouseModal_warehouseAddress').prop('disabled', true);
        $('#updateWarehouseButton').prop('disabled', true);
        $('#editButton').show();
    });
});
