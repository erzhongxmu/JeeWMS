<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div  style="padding:0px;border:0px">
 			<div style="margin-left:100px">                                 桃红色标识此储位有货，淡绿色标识此储位为空  </div>	    
    <div name="searchColums" style="float: left; padding-left: 0px;padding-top: 5px;">
          <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;" title="仓库">仓库: </span>
              <input type="text" name="cangku" style="width: 100px; height: 30px;">
         </span>
      <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;" title="储位">储位: </span>
              <input type="text" name="chuwei" style="width: 100px; height: 30px;">
         </span>
               <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;" title="其他">其他: </span>
              <input type="text" name="des" style="width: 100px; height: 30px;">
         </span>
        <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;"  >
          <button onclick="chaxun()" >查询</button>  </span>
         </div>
			
       
  </div>
<div id="bottom" style="margin-top:60px;border:2px">

</div>
 </div>
<style type="text/css">
a.a01:link, a.a01:visited{font-size:12px; font-family:verdana; width:90px; margin:1px; color:#1A1A1A; display:inline-table;  background-color:#FF4040;}
a.a01:active, a.a01:hover{font-size:12px; font-family:verdana; width:90px; margin:1px; color:#1A1A1A; display:inline-table; }
a.a02:link, a.a01:visited{font-size:12px; font-family:verdana; width:90px; margin:1px; color:#1A1A1A; display:inline-table;  background-color:#66CD00;}
a.a02:active, a.a01:hover{font-size:12px; font-family:verdana; width:90px; margin:1px; color:#1A1A1A; display:inline-table; }

</style>
 <script type="text/javascript">
 function addtab(name){
	 var str = name.replace('|','\n');
	 
 	alert(str);
 }
 $(document).ready(function(){
	 chaxun();
// 	 $("#mvCusCostListtb").find("input[name='outtime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
// 	 $("#mvCusCostListtb").find("input[name='outtime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

 });

	function chaxun(){
	 
						 var cangku;
						 var chuwei;
						 var des;
						 cangku = $('input[name="cangku"]').attr("value");
						 chuwei = $('input[name="chuwei"]').attr("value");
						 des = $('input[name="des"]').attr("value");
							//加载消息
							var url = "mdBinController.do?getbinall&binstore="+cangku+"&binid="+chuwei+"&des="+des;
							$.ajax({
					    		url:url,
					    		type:"GET",
					    		dataType:"JSON",
					    		async: false,
					    		success:function(data){
					    			if(data.success){
					    				var messageList = data.attributes.messageList;
					    				var messageCount = data.obj;
						    				
					    				var messageContent = "";
					    				var tincount = 0;
					    				if(messageList.length > 0){
					    					for(var i=0;i<messageList.length;i++){
					    						tincount  = messageList[i].tincount + 0;
					    						if(tincount>0){
					    							  messageContent +=" <a class='a01' href='javascript:void(0);' onclick='javascript:addtab(\""+messageList[i].des+"\")';return false;'>";
							    						messageContent +=messageList[i].binid+" </a> ";
							        		
					    						}else{
					    							  messageContent +=" <a class='a02' href='javascript:void(0);' >";
							    						messageContent +=messageList[i].binid+" </a> ";
							        		
					    						}
			    						      		}
					    				}

					    				$("#bottom").html(messageContent);
					    				console.dir(messageContent);
					    			}
					    		}
					    	});
			 
	}
 
 

 
 function print(id){

	 if(begindate==""||enddate==""){
		 alert("开始或者结束日期不能为空");
	 }else{
			var url = "mvCusCostController.do?doPrint&id="+id+"&begindate="+begindate+"&enddate="+enddate;
			window.open(url);
	 }

	}

 </script>