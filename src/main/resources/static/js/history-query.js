$(function(){
	
	$('#query').on('click',function(){
		let param = {
			'processKey' : $('#processKey').val(),
			'processInstanceId' : $('#processInstanceId').val(),
			'title' : $('#title').val(),
			'createTime' : $('#createTime').val(),
			'completedTime' : $('#completedTime').val(),
			'scope' : $('#scope').val(),
			'status' : $('#status').val(),
		};
		$('#history-data').datagrid('reload',param);
	});
	
	let param = {
		'processKey' : $('#processKey').val(),
		'processInstanceId' : $('#processInstanceId').val(),
		'title' : $('#title').val(),
		'createTime' : $('#createTime').val(),
		'completedTime' : $('#completedTime').val(),
		'scope' : $('#scope').val(),
		'status' : $('#status').val(),
	};
	let columns = [[
		{title:'流水号',field:'processInstanceId',width:80,align:'center',sortable:true},
	    {title:'工作名称 ',field:'title',width:200,align:'center',sortable:true},
	    {title:'所属流程',field:'processName',width:200,align:'center'},
	    {title:'流程发起人',field:'createBy',width:100,align:'center'},
	    {title:'开始时间',field:'createTime',width:150,align:'center'},
	    {title:'当前步骤',field:'taskName',width:150,align:'center'},
	    {title:'状态',field:'status',width:100,align:'center'},
	    {title:'公共附件',field:'attachment',width:100,align:'center'},
	    {title:'操作',field:'operation',width:150,align:'center'},
	]];
	let $historyData = $('#history-data');
	$historyData.datagrid({
		url: '/workflow/historyList',
		method: 'post',
		fit: true,
		fitColumns: false,
		pagination: true,
		rownumbers: true,
		striped: true,
		pageSize: 20,
		queryParams: param,
		columns: columns,
	});
	
	$historyData.datagrid('enableFilter');
})

