
var $homeWordAddForm = $("#homeWord-add-form");

$(function () {
    $("#myHomeWord-add .btn-save").click(function () {
        var name = $(this).attr("name");
        var validator = $homeWordAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "myHomeWord/add", $homeWordAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("homeWordTable");
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "myHomeWord/update", $homeWordAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("homeWordTable");
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#myHomeWord-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#homeWord-add-button").attr("name", "save");
    $("#homeWord-add-modal-title").html('新增作业');
    $MB.closeAndRestModal("myHomeWord-add");
}
