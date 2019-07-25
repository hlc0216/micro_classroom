var validator;
var $pointAddForm = $("#point-add-form");
var $pointName = $pointAddForm.find("input[name='pointName']");
$(function () {
    validateRule();
    initRole();
    createPointTree();
    $("#point-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getPoint();
        validator = $pointAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "point/add", $pointAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        refresh();
                        closeModal();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "point/update", $pointAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        refresh();
                        closeModal();
                        $MB.n_success(r.msg);
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#point-add .btn-close").click(function () {
        $("input:radio[value='0']").trigger("click");
        closeModal();
    });

});
var $rolesSelect = $pointAddForm.find("select[name='level']");
function initRole() {
    $.post(ctx + "course/list", {}, function (r) {
        var data = r.rows;
        var option = "";
        for (var i = 0; i < data.length; i++) {
            option += "<option value='" + data[i].id + "'>" + data[i].courseName + "</option>"
        }
        $rolesSelect.html("").append(option);

    });
}

function closeModal() {
    $pointName.parent().prev().text("课题名称");
    $("#point-add-modal-title").html('新增课题');
    $("#point-add-button").attr("name", "save");
    validator.resetForm();
    $MB.closeAndRestModal("point-add");
    $MB.refreshJsTree("pointTree", createPointTree());

}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $pointAddForm.validate({
        rules: {
            pointName: {
                required: true,
                minlength: 2,
                maxlength: 50,
                remote: {
                    url: "point/checkPointName",
                    type: "get",
                    dataType: "json",
                    data: {
                        pointName: function () {
                            return $("input[name='pointName']").val().trim();
                        },
                        oldPointName: function () {
                            return $("input[name='oldPointName']").val().trim();
                        },
                        type: function () {
                            return $("input[name='type']").val();
                        }
                    }
                }
            }
        },
        messages: {
            pointName: {
                required: icon + "请输入名称",
                minlength: icon + "名称长度2到50个字符",
                remote: icon + "该名称已经存在"
            }
        }
    });
}

function createPointTree() {
    $.post(ctx + "point/tree", {}, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            $('#pointTree').jstree({
                "core": {
                    'data': data.children,
                    'multiple': false
                },
                "state": {
                    "disabled": true
                },
                "checkbox": {
                    "three_state": false
                },
                "plugins": ["wholerow", "checkbox"]
            });
        } else {
            $MB.n_danger(r.msg);
        }
    })

}

function getPoint() {
    var ref = $('#pointTree').jstree(true);
    $("[name='parentId']").val(ref.get_checked()[0]);
}