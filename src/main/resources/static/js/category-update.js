$(document).ready(function () {
    $('.update-category-button').on('click', function () {
        var categoryId = $(this).data('category-id');
        $('#categoryId').val(categoryId);

        var url = '/admin/find-category-by-id?id=' + categoryId;

        $.get(url, function (categoryDto) {
            if (categoryDto === null || Object.keys(categoryDto).length === 0) {
                $('#categoryNotFoundAlert').removeClass('d-none');
                $('#updateCategoryModal').on('shown.bs.modal', function () {
                    $('#updateCategoryModal').modal('hide');
                });

            } else {
                $('#editCategoryModal_categoryId').val(categoryDto.id);
                $('#editCategoryModal_categoryName').val(categoryDto.categoryName);
                $('#categoryNotFoundAlert').addClass('d-none');
                $('#updateCategoryModal').modal('show');
            }
        });
    });
});
