function doAddcheck(name) {
	// tWzCkHeadController.do?doAddcheck;
	var flag = true;
	var msghe = '';
	$("#wmOmNoticeI_table tr").each(function(){
		fieldname = $(this).find("td:eq(1)>input").val();
		fieldnamevalue = $(this).find("td:eq(2)>input").val();
		// fieldnamelo = $(this).find("td:eq(6)>input").val();
		if (fieldname !== null &&  (typeof fieldname != 'undefined') && fieldname !== '') {
			// var url = "wmOmNoticeHController.do?doAddcheck&mat_code="+fieldname+"&mat_qty="+fieldnamevalue;
			// $.ajax({
			// 	async : false,
			// 	cache : false,
			// 	type : 'POST',
			// 	url : url,// 请求的action路径
			// 	error : function() {// 请求失败处理函数
			// 	},
			// 	success : function(data) {
			// 		var d = $.parseJSON(data);
			// 		if (d.success) {
			//
			// 		}else{
			// 			flag = false;
			// 			msghe = msghe +"/" + d.msg;
			// 		}
			// 	}
			// });
			var strs= new Array(); //定义一数组
			var goods_code;
			try{
				strs= fieldname.split('-');//    合计
				goods_code = strs[0];
			}catch(err){
				goods_code = fieldname;
			}

			var url = "wmOmNoticeHController.do?docheck&goodscode="+goods_code+"&goodsqua="+fieldnamevalue;
			$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				url : url,// 请求的action路径
				error : function() {// 请求失败处理函数
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {

					}else{
						flag=false;
						msghe = msghe +"/" + d.msg;
					}
				}
			});

			// tWzCkHeadController.do?doAddcheck;
		}

		// fieldnamevalue = $(this).find("td:eq(4)>input").val();
		// if (fieldnamevalue !== null || (typeof(fieldnamevalue != 'undefined') || fieldnamevalue !== '') {
		//     alert(fieldnamevalue);
		// }

	})
	if(!flag){
		tip(msghe);
		return false;


	}
}

//初始化下标
function resetTrNum(tableId) {
	$tbody = $("#"+tableId+"");
	$tbody.find('>tr').each(function(i){
		$(':input, select,button,a', this).each(function(){
			var $this = $(this), name = $this.attr('name'),id=$this.attr('id'),onclick_str=$this.attr('onclick'), val = $this.val();
			if(name!=null){
				if (name.indexOf("#index#") >= 0){
					$this.attr("name",name.replace('#index#',i));
				}else{
					var s = name.indexOf("[");
					var e = name.indexOf("]");
					var new_name = name.substring(s+1,e);
					$this.attr("name",name.replace(new_name,i));
				}
			}
			if(id!=null){
				if (id.indexOf("#index#") >= 0){
					$this.attr("id",id.replace('#index#',i));
				}else{
					var s = id.indexOf("[");
					var e = id.indexOf("]");
					var new_id = id.substring(s+1,e);
					$this.attr("id",id.replace(new_id,i));
				}
			}
			if(onclick_str!=null){
				if (onclick_str.indexOf("#index#") >= 0){
					$this.attr("onclick",onclick_str.replace(/#index#/g,i));
				}else{
				}
			}
		});
		$(this).find('div[name=\'xh\']').html(i+1);
	});
}
//通用弹出式文件上传
function commonUpload(callback,inputId){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback,inputId);
               return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       }
   });
}
//通用弹出式文件上传-回调
function commonUploadDefaultCallBack(url,name,inputId){
	$("#"+inputId+"_href").attr('href',url).html('下载');
	$("#"+inputId).val(url);
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}
