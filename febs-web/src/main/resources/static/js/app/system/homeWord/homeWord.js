$(function () {
    var settings = {
        url: ctx + "homeWord/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                homeWordName: $(".homeWord-table-form").find("input[name='homeWordName']").val().trim()
            };
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'pointName',
                title: '课题名称'
            },
            {
                field: 'homeWordName',
                title: '作业名称'
            }, {
                field: 're',
                title: '要求'
            }, {
                field: 'zi',
                title: '资料'
            },
            {
                field:'ti',
                title:'提交方式'
            },
            {
                field:'deptName',
                title:'参加班级'
            }
            ]
    };

    $MB.initTable('homeWordTable', settings);
});

function search() {
    $MB.refreshTable('homeWordTable');
}

function refresh() {
    $(".homeWord-table-form")[0].reset();
    search();
}

function deletehomeWords() {
    var selected = $("#homeWordTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的作业！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "删除选中作业，确定删除？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'homeWord/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exporthomeWordExcel() {
    $.post(ctx + "homeWord/excel", $(".homeWord-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exporthomeWordCsv() {
    $.post(ctx + "homeWord/csv", $(".homeWord-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}