$(document).ready(function () {
    $('#editButton').on('click', function () {
        $('#editCategoryModal_categoryName').prop('disabled', false);
        $(this).hide();
    });

    $('#editCategoryModal_categoryName').on('input', function () {
        $('#updateCategoryButton').prop('disabled', false);
    });

    $('#updateCategoryModal').on('hidden.bs.modal', function () {
        $('#editCategoryModal_categoryName').prop('disabled', true);
        $('#updateCategoryButton').prop('disabled', true);
        $('#editButton').show();
    });
});
