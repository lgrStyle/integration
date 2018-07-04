$(function(){
	var columns = [[
	    {title:'流水号',field:'List Price',width:80,align:'center',sortable:true},
	    {title:'工作名称 ',field:'Unit Cost',width:200,align:'center',sortable:true},
	    {title:'所属流程',field:'Attribute',width:200,align:'center'},
	    {title:'开始时间',field:'Status',width:150,align:'center'},
	    {title:'流程发起人',field:'Status1',width:100,align:'center'},
	    {title:'状态',field:'Status2',width:100,align:'center'},
	    {title:'当前步骤',field:'Status3',width:100,align:'center'},
	    {title:'操作',field:'Status4',width:150,align:'center'},
	]];
	var $historyData = $('#history-data');
	$historyData.datagrid({
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
	
	$historyData.datagrid('enableFilter');
})

