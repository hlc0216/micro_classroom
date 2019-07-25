function updatehomeWord() {
    var selected = $("#homeWordTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的作业！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个作业！');
        return;
    }
    var id = selected[0].id;
    $.post(ctx + "homeWord/getHomeWord", {"id": id}, function (r) {
        if (r.code === 0) {
            var $form = $('#homeWord-add');
            var $pointTree = $('#pointTree');
            var $deptTree = $('#deptTree');
            $form.modal();
            var homeWord = r.msg;
            $("#homeWord-add-modal-title").html('修改作业');
            $form.find("input[name='homeWordName']").val(homeWord.homeWordName);
            $form.find("input[name='oldhomeWordName']").val(homeWord.homeWordName);
            $form.find("input[name='id']").val(homeWord.id);
            $form.find("input[name='re']").val(homeWord.re);
            $form.find("input[name='ti']").val(homeWord.ti);
            $form.find("input[name='zi']").val(homeWord.zi);
            $pointTree.jstree('select_node', homeWord.courseId, true);
            $pointTree.jstree('disable_node', homeWord.courseId);

            var chars=homeWord.deptId.split(",");
            var len=chars.length;
            for (var i=0;i<len;i++) {
                $deptTree.jstree('select_node', chars[i], true);
                $pointTree.jstree('disable_node', chars[i]);
            }


            $("#homeWord-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}