function updatePoint() {
    var selected = $("#pointTable").bootstrapTreeTable("getSelections");
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的课题或按钮！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个课题或按钮！');
        return;
    }
    var pointId = selected[0].id;
    $.post(ctx + "point/getPoint", {"pointId": pointId}, function (r) {
        if (r.code === 0) {
            var $form = $('#point-add');
            var $pointTree = $('#pointTree');
            $form.modal();
            var point = r.msg;
            $("#point-add-modal-title").html('修改课题/按钮');
            $("input:radio[value='" + point.type + "']").trigger("click");
            $form.find("input[name='pointName']").val(point.pointName);
            $form.find("input[name='oldPointName']").val(point.pointName);
            $form.find("input[name='pointId']").val(point.pointId);
            // $form.find("input[name='level']").val(point.level);
            $form.find("input[name='remark']").val(point.remark);
            $form.find("input[name=jizai]:eq("+point.status+")").attr("checked",'checked');
            $form.find("input[name='remark']").val(point.remark);

            $form.find("select[name='level']").find("option:contains("+point.level+")").attr("checked","checked");

            $pointTree.jstree('select_node', point.parentId, true);
            $pointTree.jstree('disable_node', point.pointId);
            $("#point-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}