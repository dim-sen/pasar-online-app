$(document).ready(function () {
    $('.update-warehouse-button').on('click', function () {
        var warehouseId = $(this).data('warehouse-id');
        $('#warehouseId').val(warehouseId);

        var url = '/admin/find-warehouse-by-id?id=' + warehouseId;
        console.log("warehouseId: ", warehouseId);

        $.get(url, function (warehouseDto) {
            if (warehouseDto === null || Object.keys(warehouseDto).length === 0) {
                $('#warehouseNotFoundAlert').removeClass('d-none');
                $('#updateWarehouseModal').on('shown.bs.modal', function () {
                    $('#updateWarehouseModal').modal('hide');
                });

            } else {
                $('#editWarehouseModal_warehouseId').val(warehouseDto.id);
                $('#editWarehouseModal_warehouseName').val(warehouseDto.warehouseName);
                console.log("warehouseName: ", warehouseDto.warehouseName);
                $('#editWarehouseModal_warehouseAddress').val(warehouseDto.warehouseAddress);
                $('#warehouseNotFoundAlert').addClass('d-none');
                $('#updateWarehouseModal').modal('show');
            }
        });
    });
});
