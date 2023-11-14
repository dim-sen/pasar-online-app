$(document).ready(function() {

    var successMessage = [[${SUCCESS}]];
    var alreadyExistMessage = [[${ALREADY_EXIST}]];
    var failedMessage = [[${FAILED}]];

    if (successMessage) {
        $('#dynamicAlertModalLabel').text('Success');
        $('#dynamicAlertModal .modal-body p').text(successMessage);
        $('#dynamicAlertModal').modal('show');
    } else if (alreadyExistMessage) {
        $('#dynamicAlertModalLabel').text('Error');
        $('#dynamicAlertModal .modal-body p').text(alreadyExistMessage);
        $('#dynamicAlertModal').modal('show');
    } else if (failedMessage) {
        $('#dynamicAlertModalLabel').text('Error');
        $('#dynamicAlertModal .modal-body p').text(failedMessage);
        $('#dynamicAlertModal').modal('show');
    }
});