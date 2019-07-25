function updatecourse() {
    var selected = $("#courseTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的课程！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个课程！');
        return;
    }
    var id = selected[0].id;
    $.post(ctx + "course/getCourse", {"id": id}, function (r) {
        if (r.code === 0) {
            var $form = $('#course-add');
            // var $menuTree = $('#menuTree');
            $form.modal();
            var course = r.msg;
            $("#course-add-modal-title").html('修改课程');
            $form.find("input[name='courseName']").val(course.courseName);
            $form.find("input[name='oldcourseName']").val(course.courseName);
            $form.find("input[name='id']").val(course.id);
            $form.find("input[name='imgUrl']").val(course.imgUrl);
            $("#course-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}