var validator;
var $homeWordAddForm = $("#homeWord-add-form");

$(function () {
    validateRule();
    createPointTree();
    createDeptTree();
    $("#homeWord-add .btn-save").click(function () {
        var name = $(this).attr("name");
        getPoint();
        getDept();
        var validator = $homeWordAddForm.validate();
        var flag = validator.form();
        if (flag) {
            if (name === "save") {
                $.post(ctx + "homeWord/add", $homeWordAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("homeWordTable");
                    } else $MB.n_danger(r.msg);
                });
            }
            if (name === "update") {
                $.post(ctx + "homeWord/update", $homeWordAddForm.serialize(), function (r) {
                    if (r.code === 0) {
                        closeModal();
                        $MB.n_success(r.msg);
                        $MB.refreshTable("homeWordTable");
                    } else $MB.n_danger(r.msg);
                });
            }
        }
    });

    $("#homeWord-add .btn-close").click(function () {
        closeModal();
    });

});

function closeModal() {
    $("#homeWord-add-button").attr("name", "save");
    $("#homeWord-add-modal-title").html('新增作业');
    validator.resetForm();
    $MB.closeAndRestModal("homeWord-add");
    $MB.resetJsTree("deptTree");
    $MB.refreshJsTree("pointTree", createPointTree());
}

function validateRule() {
    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
    validator = $homeWordAddForm.validate({
        rules: {
            homeWordName: {
                required: true,
                minlength: 3,
                maxlength: 50,
                remote: {
                    url: "homeWord/checkhomeWordName",
                    type: "get",
                    dataType: "json",
                    data: {
                        homeWordName: function () {
                            return $("input[name='homeWordName']").val().trim();
                        },
                        oldhomeWordName: function () {
                            return $("input[name='oldhomeWordName']").val().trim();
                        }
                    }
                }
            }
        },
        messages: {
            homeWordName: {
                required: icon + "请输入作业名称",
                minlength: icon + "作业名称长度3到50个字符",
                remote: icon + "该作业名已经存在"
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
    $("[name='courseId']").val(ref.get_checked()[0]);
}

function createDeptTree() {
    $.post(ctx + "dept/tree", {}, function (r) {
        if (r.code === 0) {
            var data = r.msg;
            $('#deptTree').jstree({
                "core": {
                    'data': data.children,
                    'multiple': true // 取消多选
                },
                "state": {
                    "disabled": true
                },
                "checkbox": {
                    "three_state": true // 取消选择父节点后选中所有子节点
                },
                "plugins": ["wholerow", "checkbox"]
            });
        } else {
            $MB.n_danger(r.msg);
        }
    })
}

function getDept() {
    var ref = $('#deptTree').jstree(true).get_selected();
    // $("[name='deptId']").val(ref.get_selected()[0]);
    var deptList="";
    var len=ref.length;
    for(var i=0;i<len;i++)
    {

        if(i==len-1){
            deptList+=ref[i];
        }else{
            deptList+=ref[i]+",";
        }
    }
    console.log(deptList);
    console.log(ref);
    $("[name='deptId']").val(deptList);

}