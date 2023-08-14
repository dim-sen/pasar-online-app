$(document).ready(function () {
    $('#editButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');

        $.get(href, function (categoryDto, status) {
            if (status === 'success') {
                $('#editCategoryId').val(categoryDto.id);
                $('#editCategoryName').val(categoryDto.categoryName);
                $('#editModal').modal('show');
            }
        });
    });
});
