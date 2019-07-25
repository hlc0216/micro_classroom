$(function () {
    var settings = {
        url: ctx + "studentDetail/list",
        pageSize: 10,
        queryParams: function (params) {
            return {
                pageSize: params.limit,
                pageNum: params.offset / params.limit + 1,
                userId:$("input[name='OlduserId']").val()
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
                field: 'homeWordUrl',
                title: '作业url'
            },
            {
                field: 'homeWordMark',
                title: '备注'
            },
            {
                field: 'homeWordImg',
                title: '图片url'
            },
            {
                field: 'homeWordFeng',
                title: '分数'
            },
            {
                field: 'homeWordPing',
                title: '评论'
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
function updatehomeWord() {
    var selected = $("#homeWordTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要评论的作业！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能评论一个作业！');
        return;
    }
    var id = selected[0].id;
    $.post(ctx + "studentDetail/getOne", {"id": id}, function (r) {
        if (r.code === 0) {
            var $form = $('#studentDetail-add');
            $form.modal();
            var homeWord = r.msg;
            $("#homeWord-add-modal-title").html('评论作业');
            $form.find("input[name='homeWordId']").val(homeWord.homeWordId);
            $form.find("input[name='id']").val(homeWord.id);

            $form.find("input[name='userId']").val(homeWord.userId);
            $form.find("input[name='homeWordStatus']").val(homeWord.homeWordStatus);
            $form.find("input[name='homeWordUrl']").val(homeWord.homeWordUrl);
            $form.find("input[name='homeWordImg']").val(homeWord.homeWordImg);
            $form.find("input[name='homeWordMark']").val(homeWord.homeWordMark);
            $form.find("input[name='homeWordFeng']").val(homeWord.homeWordFeng);
            $form.find("input[name='homeWordPing']").val(homeWord.homeWordPing);

            $("#homeWord-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}