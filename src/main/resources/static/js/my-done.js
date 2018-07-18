$(function(){
	
	$('#query').on('click',function(){
		let param = {
			'processKey' : $('#processKey').val(),
			'processInstanceId' : $('#processInstanceId').val(),
			'processName' : $('#processName').val(),
		};
		$('#my-done-data').datagrid('reload',param);
	});
	
	var columns = [[
		{title:'流水号',field:'processInstanceId',width:80,align:'center',sortable:true},
	    {title:'工作名称 ',field:'title',width:200,align:'center',sortable:true},
	    {title:'所属流程',field:'processName',width:200,align:'center'},
	    {title:'流程发起人',field:'createBy',width:100,align:'center'},
	    {title:'办结时间',field:'completedTime',width:150,align:'center'},
	    {title:'步骤与流程图',field:'taskName',width:150,align:'center'},
	    {title:'状态',field:'status',width:100,align:'center'},
	    {title:'公共附件',field:'attachment',width:100,align:'center'},
	    {title:'操作',field:'operation',width:150,align:'center'},
	]];
	var $myDoneData = $('#my-done-data');
	$myDoneData.datagrid({
		url: '/workflow/myDoneList',
		method: 'post',
		fit: true,
		fitColumns: false,
		pagination: true,
		rownumbers: true,
		striped: true,
		pageSize: 20,
		queryParams: {},
		columns: columns,
	});
	
	$myDoneData.datagrid('enableFilter');
});