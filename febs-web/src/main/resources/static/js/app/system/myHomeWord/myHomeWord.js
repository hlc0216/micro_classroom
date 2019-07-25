$(function () {
    var settings = {
        url: ctx + "myHomeWord/list",
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
                field: 'homeWordName',
                title: '作业名称'
            },
            {
                field: 'pointName',
                title: '课题'
            },
            {
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
                field:'id',
                title:'状态',
                formatter: function (item, index) {
                    return '<span class="badge badge-success"><a onclick="Clazz('+item+')">点击提交</a></span>';

                }
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
function Clazz(num) {
    $("input[name='homeWordId']").val(num);
    $("#myHomeWord-add").modal("show");
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