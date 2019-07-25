$(function() {
    initTreeTable();
    $("#user-add .btn-close").click(function () {
        $MB.closeAndRestModal("user-add");
    });
});

function initTreeTable() {
    var setting = {
        id: 'deptId',
        code: 'deptId',
        url: ctx + 'clazz/list',
        expandAll: false,
        expandColumn: "2",
        ajaxParams: {
            deptName: $(".dept-table-form").find("input[name='deptName']").val().trim()
        },
        columns: [{
                field: 'selectItem',
                checkbox: true
            },
            {
                title: '编号',
                field: 'deptId',
                width: '50px'
            },
            {
                title: '名称',
                field: 'deptName'
            },
            {
                title: '班级人数',
                field: 'orderNum',
                formatter: function (item, index) {

                    if (item.orderNum == 1) return '<span class="badge badge-success"><a onclick="Clazz('+item.deptId+')">点击查看</a></span>';
                    else return '<span class="badge badge-warning">无</span>';
                }
            }
        ]
    };

    $MB.initTreeTable('deptTable', setting);
}

function searchClazz() {
    initTreeTable();
}
function Clazz(num) {
    $("input[name='deptId']").val(num);
    $MB.refreshTable('userTable');
    $("#user-add").modal("show");
}
function refresh() {
    $(".dept-table-form")[0].reset();
    searchClazz();
    $MB.refreshJsTree("deptTree", createDeptTree());
}

function exportDeptExcel(){
	$.post(ctx+"dept/excel",$(".dept-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}

function exportDeptCsv(){
	$.post(ctx+"dept/csv",$(".dept-table-form").serialize(),function(r){
		if (r.code === 0) {
			window.location.href = "file/download?fileName=" + r.msg + "&delete=" + true;
		} else {
			$MB.n_warning(r.msg);
		}
	});
}