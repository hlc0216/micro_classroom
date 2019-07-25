$(function () {
    var settings = {
        url: ctx + "course/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                courseName: $(".course-table-form").find("input[name='courseName']").val().trim()
            };
        },
        columns: [{
            checkbox: true
        }, {
            field: 'courseName',
            title: '课程'
        }, {
            field: 'imgUrl',
            title: '图片'
        }, {
            field: 'xId',
            title: '学院'
        }]
    };

    $MB.initTable('courseTable', settings);
});

function search() {
    $MB.refreshTable('courseTable');
}

function refresh() {
    $(".course-table-form")[0].reset();
    search();
}

function deletecourses() {
    var selected = $("#courseTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要删除的课程！');
        return;
    }
    var ids = "";
    for (var i = 0; i < selected_length; i++) {
        ids += selected[i].id;
        if (i !== (selected_length - 1)) ids += ",";
    }

    $MB.confirm({
        text: "删除选中课程，确定删除？",
        confirmButtonText: "确定删除"
    }, function () {
        $.post(ctx + 'course/delete', {"ids": ids}, function (r) {
            if (r.code === 0) {
                $MB.n_success(r.msg);
                refresh();
            } else {
                $MB.n_danger(r.msg);
            }
        });
    });
}

function exportcourseExcel() {
    $.post(ctx + "course/excel", $(".course-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}

function exportcourseCsv() {
    $.post(ctx + "course/csv", $(".course-table-form").serialize(), function (r) {
        if (r.code === 0) {
            window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
        } else {
            $MB.n_warning(r.msg);
        }
    });
}