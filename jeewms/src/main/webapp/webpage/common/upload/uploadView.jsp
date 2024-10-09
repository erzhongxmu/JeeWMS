<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html >
<html>
 <head>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">

        function uploadSuccess(d,file,response){
                $("#fileUrl").val(d.attributes.url);
                $("#fileName").val(d.attributes.name);
                var url = $("#fileUrl").val();
                var html="";
                if(url.indexOf(".gif")!=-1 || 
                                url.indexOf(".jpg")!=-1        ||
                                url.indexOf(".png")!=-1 ||
                                url.indexOf(".bmp")!=-1){
                        html += "<img src='"+url+"' width =400 height=300 />";
                }else{
                        html += "<a href='"+url+"' target=_blank >下载:"+d.attributes.name+"</a>";
                }
                $("#fileShow").html(html);
        }
        function uploadCallback(callback,inputId){
                var url = $("#fileUrl").val();
                var name= $("#fileName").val();
                callback(url,name,inputId);
                
        }
</script>
</head>
 <body style="overflow-y: hidden" scroll="no">
  <table cellpadding="0" cellspacing="1" class="formtable">
  <input id="documentTitle" type="hidden" name="documentTitle" value="blank"/>
  <input id="fileUrl" type="hidden"/>
  <input id="fileName" type="hidden"/>
   <tbody>
    <tr>
     <td align="right">
       <label class="Validform_label"></label>
     </td>
     <td class="value">
      <t:upload name="instruction" dialog="false" multi="false" extend=".jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif;*.xls;*.doc;*.rar;*.txt;*.zip" queueID="instructionfile" view="false" auto="true" uploader="systemController.do?saveFiles" onUploadSuccess="uploadSuccess"  id="instruction" formData="documentTitle"></t:upload>
     </td>
    </tr>
    <tr>
     <td colspan="2" id="instructionfile" class="value">
     </td>
    </tr>
   </tbody>
  </table>
   <div id="fileShow" >
  </div>
 </body>
 </html>
