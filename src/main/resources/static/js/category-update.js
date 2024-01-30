$(document).ready(function () {
    $('.update-category-button').on('click', function () {
        var categoryId = $(this).data('category-id');
        $('#categoryId').val(categoryId);

        var url = '/admin/find-category-by-id?id=' + categoryId;
        console.log("categoryId: ", categoryId);

        $.get(url, function (categoryDao) {
            if (categoryDao === null || Object.keys(categoryDao).length === 0) {
                $('#categoryNotFoundAlert').removeClass('d-none');
                $('#updateCategoryModal').on('shown.bs.modal', function () {
                    $('#updateCategoryModal').modal('hide');
                });

            } else {
                $('#editCategoryModal_categoryId').val(categoryDao.id);
                var categoryName = categoryDao.categoryName;
                var capitalizedCategoryName = categoryName.replace(/\b\w/g, function(match) {
                    return match.toUpperCase();
                });
                $('#editCategoryModal_categoryName').val(capitalizedCategoryName);
                $('#editCategoryModal_status').val(categoryDao.isActive.toString());
                console.log("isActive: ", categoryDao.isActive)
                $('#categoryNotFoundAlert').addClass('d-none');
                $('#updateCategoryModal').modal('show');
            }
        });
    });
});
