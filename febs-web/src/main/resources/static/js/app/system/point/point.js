$(function () {
    initTreeTable();
});

function initTreeTable() {
    var $pointTableForm = $(".point-table-form");
    var setting = {
        id: 'pointId',
        code: 'pointId',
        url: ctx + 'point/list',
        expandAll: false,
        expandColumn: "2",
        ajaxParams: {
            pointName: $pointTableForm.find("input[name='pointName']").val().trim(),
            status: $pointTableForm.find("select[name='status']").val()
        },
        columns: [
            {
                field: 'selectItem',
                checkbox: true
            },
            {
                title: '编号',
                field: 'pointId',
                width: '50px'
            },
            {
                title: '名称',
                field: 'pointName'
            },
            {
                title:'课程',
                field:'course',
            },
            {
                title:'备注',
                field:'remark',
            },
            {
                title:'状态',
                field:'status',
                formatter: function (value, row, index) {
                    if (value.status==0) return '<span class="badge badge-success">有效</span>';
                    if (value.status==1) return '<span class="badge badge-warning">锁定</span>';
                }
            }
        ]
    };

    $MB.initTreeTable('pointTable', setting);
}

function search() {
    initTreeTable();
}

function refresh() {
    $(".point-table-form")[0].reset();
    initTreeTable();
    $MB.refreshJsTree("pointTree", createPointTree());
}

function deletePoints() {
    var ids = $("#pointTable").bootstrapTreeTable("getSelections");
    var ids_arr = "";
    if (!ids.length) {
        $MB.n_warning("请勾选需要删除的菜单或按钮！");
        return;
    }
    for (var i = 0; i < ids.length; i++) {
        ids_arr += ids[i].id;
        if (i !== (ids.length - 1)) ids_arr += ",";
    }
    $MB.confirm({
        text: "确定删除选中？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'point/delete', {"ids": ids_arr}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportPointExcel() {
    $.post(ctx + "point/excel", $(".point-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportPointCsv() {
    $.post(ctx + "point/csv", $(".point-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}