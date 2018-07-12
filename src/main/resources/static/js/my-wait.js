$(function(){
	var columns = [[
	    {title:'流水号',field:'id',width:80,align:'center',sortable:true},
	    {title:'工作名称 ',field:'title',width:200,align:'center',sortable:true},
	    {title:'所属流程',field:'processType',width:200,align:'center'},
	    {title:'流程发起人',field:'originator',width:100,align:'center'},
	    {title:'接收时间',field:'receiptTime',width:150,align:'center'},
	    {title:'步骤与流程图',field:'taskName',width:150,align:'center'},
	    {title:'状态',field:'status',width:100,align:'center'},
	    {title:'公共附件',field:'attachment',width:100,align:'center'},
	    {title:'操作',field:'operation',width:150,align:'center'},
	]];
	var $myWaitData = $('#my-wait-data');
	$myWaitData.datagrid({
		url: '/workflow/myWaitList',
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
	
	$myWaitData.datagrid('enableFilter');
});