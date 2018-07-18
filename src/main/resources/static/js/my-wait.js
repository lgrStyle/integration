$(function(){
	
	function getRowIndex(target){
		var tr = $(target).closest('tr.datagrid-row');
		return parseInt(tr.attr('datagrid-row-index'));	
	}
	
	$('#query').on('click',function(){
		let param = {
			'processKey' : $('#processKey').val(),
			'processInstanceId' : $('#processInstanceId').val(),
			'processName' : $('#processName').val(),
		};
		$('#my-wait-data').datagrid('reload',param);
	});
	
	$(document).on('click','#easyui-handle',function(){
		let data = $('#my-wait-data').datagrid('getSelected');
		console.log(data);
	});
	
	$(document).on('click','#easyui-export',function(){
		let data = $('#my-wait-data').datagrid('getSelected');
		console.log(data);
		
	});
	
	$(document).on('click','#easyui-delete',function(){
		let data = $('#my-wait-data').datagrid('getSelected');
		console.log(data);
	});
	
	let columns = [[
	    {title:'流水号',field:'processInstanceId',width:80,align:'center'},
	    {title:'工作名称 ',field:'title',width:200,align:'center',
	    	styler:function(value,row,index){
	    		return {'class' : 'cell-style'};
	    	}
	    },
	    {title:'所属流程',field:'processName',width:200,align:'center'},
	    {title:'流程发起人',field:'createBy',width:100,align:'center'},
	    {title:'接收时间',field:'receiptTime',width:150,align:'center'},
	    {title:'步骤与流程图',field:'taskName',width:150,align:'center',
	    	styler:function(value,row,index){
	    		return {'class' : 'cell-style'};
	    	}
	    },
	    {title:'状态',field:'status',width:100,align:'center'},
	    {title:'公共附件',field:'attachment',width:100,align:'center'},
	    {title:'操作',field:'operation',width:150,align:'center',
	    	formatter: function(value,row,index){
	    		let h = '<span class="cell-style" id="easyui-handle" >主办</span>';
	    		let e = '<span class="cell-style" id="easyui-export" >导出</span>';
	    		let d = '<span class="cell-style" id="easyui-delete" >删除</span>'
	    		return h+"&nbsp;&nbsp;&nbsp;"+e+"&nbsp;&nbsp;&nbsp;"+d;
	    	}
	    },
	]];
	let $myWaitData = $('#my-wait-data');
	$myWaitData.datagrid({
		url: '/workflow/myWaitList',
		method: 'post',
		fit: true,
		fitColumns: false,
		pagination: true,
		rownumbers: true,
		striped: true,
		singleSelect: true,
		pageSize: 20,
		queryParams: {},
		columns: columns,
		onClickCell: function(index,field,value){
			if(field == 'title'){
				let data = $myWaitData.datagrid('getSelected');
			}else if(field == 'taskName'){
				$myWaitData.datagrid('selectRow',index);
				let data = $myWaitData.datagrid('getSelected');
				let url = `/workflow//processImage?taskId=${data.taskId}`;
				openFullWindow(url);
			}
		}
	});
	
	$myWaitData.datagrid('enableFilter');
});


