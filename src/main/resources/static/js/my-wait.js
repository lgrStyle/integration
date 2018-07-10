$(function(){
	var columns = [[
	    {title:'流水号',field:'List Price',width:80,align:'center',sortable:true},
	    {title:'工作名称 ',field:'Unit Cost',width:200,align:'center',sortable:true},
	    {title:'所属流程',field:'Attribute',width:200,align:'center'},
	    {title:'流程发起人',field:'Status1',width:100,align:'center'},
	    {title:'接收时间',field:'Status',width:150,align:'center'},
	    {title:'步骤与流程图',field:'Status6',width:150,align:'center'},
	    {title:'状态',field:'Status2',width:100,align:'center'},
	    {title:'公共附件',field:'Status3',width:100,align:'center'},
	    {title:'正文',field:'Status4',width:100,align:'center'},
	    {title:'操作',field:'Status5',width:150,align:'center'},
	]];
	var $myWaitData = $('#my-wait-data');
	$myWaitData.datagrid({
		url: '',
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