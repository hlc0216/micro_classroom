var validator;
var $courseAddForm = $("#course-add-form");

$(function () {
    validateRule();
    // createMenuTree();

    $("#course-add .btn-save").click(function () {
        var name = $(this).attr("name");
        // getMenu();
        var validator = $courseAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "course/add", $courseAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("courseTable");
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "course/update", $courseAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("courseTable");
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#course-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#course-add-button").attr("name", "save");
    $("#course-add-modal-title").html('新增课程');
    validator.resetForm();
    // $MB.resetJsTree("menuTree");
    $MB.closeAndRestModal("course-add");
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $courseAddForm.validate({
        rules: {
            courseName: {
                required: true,
                minlength: 3,
                maxlength: 160,
                // remote: {
                //     url: "course/checkcourseName",
                //     type: "get",
                //     dataType: "json",
                //     data: {
                //         courseName: function () {
                //             return $("input[name='courseName']").val().trim();
                //         },
                //         oldcourseName: function () {
                //             return $("input[name='oldcourseName']").val().trim();
                //         }
                //     }
                // }
            }
        },
        messages: {
            courseName: {
                required: icon + "请输入课程名称",
                minlength: icon + "课程名称长度3到160个字符",
                // remote: icon + "该课程名已经存在"
            }
        }
    });
}

// function createMenuTree() {
//     $.post(ctx + "menu/menuButtonTree", {}, function (r) {
//         if (r.code === 0) {
//             var data = r.msg;
//             $('#menuTree').jstree({
//                 "core": {
//                     'data': data.children
//                 },
//                 "state": {
//                     "disabled": true
//                 },
//                 "checkbox": {
//                     "three_state": false
//                 },
//                 "plugins": ["wholerow", "checkbox"]
//             });
//         } else {
//             $MB.n_danger(r.msg);
//         }
//     })
//
// }

// function getMenu() {
//     var $menuTree = $('#menuTree');
//     var ref = $menuTree.jstree(true);
//     var menuIds = ref.get_checked();
//     $menuTree.find(".jstree-undetermined").each(function (i, element) {
//         menuIds.push($(element).closest('.jstree-node').attr("id"));
//     });
//     $("[name='menuId']").val(menuIds);
// }