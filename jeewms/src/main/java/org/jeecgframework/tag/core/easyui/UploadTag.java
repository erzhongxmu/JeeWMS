package org.jeecgframework.tag.core.easyui;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.jeecgframework.core.util.MutiLangUtil;

/**
 * 
 * 类描述：上传标签
 * 
 * admin
 * @date： 日期：2012-12-7 时间：上午10:17:45
 * @version 1.0
 */
public class UploadTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected String id;// ID
	protected String uploader;//url
	protected String name;//控件名称
	protected String formData;//参数名称
	protected String extend="";//上传文件的扩展名	
	protected String buttonText="浏览";//按钮文本
	protected boolean multi=true;//是否多文件
	protected String queueID="filediv";//文件容器ID
	protected boolean dialog=true;//是否是弹出窗口模式
	protected String callback;
	protected boolean auto=false;//是否自动上传
	protected String onUploadSuccess;//上传成功处理函数
	protected boolean view=false;//生成查看删除链接

	protected String formId;//参数名称
	
	
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}

	private String fileSizeLimit = "15MB";//上传文件大小设置
	public String getFileSizeLimit() {
		return fileSizeLimit;
	}
	public void setFileSizeLimit(String fileSizeLimit) {
		this.fileSizeLimit = fileSizeLimit;
	}



	public void setView(boolean view) {
		this.view = view;
	}
	public void setOnUploadSuccess(String onUploadSuccess) {
		this.onUploadSuccess = onUploadSuccess;
	}
	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}
	public void setQueueID(String queueID) {
		this.queueID = queueID;
	}
	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}
	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}
	@Override
	public int doEndTag() throws JspTagException {
		JspWriter out = null;
		try {
			out = this.pageContext.getOut();
			out.print(end().toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.clear();
				out.close();
			} catch (Exception e2) {
			}
		}
		return EVAL_PAGE;
	}
	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if("pic".equals(extend))
		{
			extend="*.jpg;*,jpeg;*.png;*.gif;*.bmp;*.ico;*.tif";
		}
		if(extend.equals("office"))
		{
			extend="*.doc;*.docx;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm";
		}
		sb.append("<link rel=\"stylesheet\" href=\"plug-in/uploadify/css/uploadify.css\" type=\"text/css\"></link>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/uploadify/jquery.uploadify-3.1.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/Map.js\"></script>");
		sb.append("<script type=\"text/javascript\">"
				+"var flag = false;"
				+"var fileitem=\"\";"
				+"var fileKey=\"\";"
				+"var serverMsg=\"\";"
				+"var m = new Map();"
				+ "$(function(){"
				+"$(\'#"+id+"\').uploadify({"
				+"buttonText:\'"+MutiLangUtil.getMutiLangInstance().getLang(buttonText)+"\',"
				+"auto:"+auto+","
				+"progressData:\'speed\'," 
				+"multi:"+multi+","
				+"height:25,"
				+"overrideEvents:[\'onDialogClose\']," 
				+"fileTypeDesc:\'文件格式:\'," 
				+"queueID:\'"+queueID+"\',"
				+"fileTypeExts:\'"+extend+"\',"
				+"fileSizeLimit:\'"+fileSizeLimit+"\',"
				+"swf:\'plug-in/uploadify/uploadify.swf\',	"
				+"uploader:\'"+getUploader()			
						+"onUploadStart : function(file) { ");	
				if (formData!=null) {
					String[] paramnames=formData.split(",");				
					for (int i = 0; i < paramnames.length; i++) {
						String paramname=paramnames[i];
						sb.append("var "+paramname+"=$(\'#"+paramname+"\').val();");
					}				 
			        sb.append("$(\'#"+id+"\').uploadify(\"settings\", \"formData\", {");
			        for (int i = 0; i < paramnames.length; i++) {
						String paramname=paramnames[i];
						if (i==paramnames.length-1) {
							sb.append("'"+paramname+"':"+paramname+"");	
						}else{
							sb.append("'"+paramname+"':"+paramname+",");
						}
					}
			        sb.append("});");

				}else if (formId!=null) {
					sb.append(" var o = {};");
            		sb.append("    var _array = $('#"+formId+"').serializeArray();");
            		sb.append("    $.each(_array, function() {");
            		sb.append("        if (o[this.name]) {");
            		sb.append("            if (!o[this.name].push) {");
            		sb.append("                o[this.name] = [ o[this.name] ];");
            		sb.append("            }");
            		sb.append("            o[this.name].push(this.value || '');");
            		sb.append("        } else {");
            		sb.append("            o[this.name] = this.value || '';");
            		sb.append("        }");
            		sb.append("    });");
            		sb.append("$(\'#"+id+"\').uploadify(\"settings\", \"formData\", o);");
				};

		       sb.append("} ," 	          
				+"onQueueComplete : function(queueData) { ");
				if(dialog)
				{
				sb.append("var win = frameElement.api.opener;"  	  
	            +"win.reloadTable();"
	            +"win.tip(serverMsg);"
	            +"frameElement.api.close();");
				}
				else
				{
				  if(callback!=null) {
                      sb.append(""+callback+"();");
                  }
				}
				if(view)
				{
				sb.append("$(\"#viewmsg\").html(m.toString());");
				sb.append("$(\"#fileKey\").val(fileKey);");
				}
				sb.append("},");
				//上传成功处理函数
				sb.append("onUploadSuccess : function(file, data, response) {");
				sb.append("var d=$.parseJSON(data);");
				if(view)
				{
				sb.append("var fileitem=\"<span id=\'\"+d.attributes.id+\"\'><a href=\'#\' onclick=openwindow(\'文件查看\',\'\"+d.attributes.viewhref+\"\',\'70%\',\'80%\') title=\'查看\'>\"+d.attributes.name+\"</a><img border=\'0\' onclick=confuploadify(\'\"+d.attributes.delurl+\"\',\'\"+d.attributes.id+\"\') title=\'删除\' src=\'plug-in/uploadify/img/uploadify-cancel.png\' widht=\'15\' height=\'15\'>&nbsp;&nbsp;</span>\";");

				sb.append(" m=new Map(); ");

				sb.append("m.put(d.attributes.id,fileitem);");
				sb.append("fileKey=d.attributes.fileKey;");
				}
				if(onUploadSuccess!=null)
				{
				sb.append(onUploadSuccess+"(d,file,response);");
				}
				sb.append("if(d.success){");
				sb.append("var win = frameElement.api.opener;");
//				sb.append("win.tip(d.msg);");
				sb.append("serverMsg = d.msg;");
				sb.append("}");
				sb.append("},");
				sb.append("onFallback : function(){tip(\"您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试\")},");
				sb.append("onSelectError : function(file, errorCode, errorMsg){");
				sb.append("switch(errorCode) {");
				sb.append("case -100:");
				sb.append("tip(\"上传的文件数量已经超出系统限制的\"+$(\'#"+id+"\').uploadify(\'settings\',\'queueSizeLimit\')+\"个文件！\");");
				sb.append("break;");
				sb.append("case -110:"
				+"tip(\"文件 [\"+file.name+\"] 大小超出系统限制的\"+$(\'#"+id+"\').uploadify(\'settings\',\'fileSizeLimit\')+\"大小！\");"
				+"break;"
				+"case -120:"
				+"tip(\"文件 [\"+file.name+\"] 大小异常！\");"
				+"break;"
				+"case -130:"
				+"tip(\"文件 [\"+file.name+\"] 类型不正确！\");"
				+"break;"
				+"}");
		       sb.append("},"
				+"onUploadProgress : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) { "
				//+"tip('<span>文件上传成功:'+totalBytesUploaded/1024 + ' KB 已上传 ,总数' + totalBytesTotal/1024 + ' KB.</span>');"  	  	             
				+"}"
	   			+"});"
				+"});"
				+"function upload() {"
				+"	$(\'#"+id+"\').uploadify('upload', '*');"
				+"		return flag;"
				+"}"
				+"function cancel() {"
				+"$(\'#"+id+"\').uploadify('cancel', '*');"				
				+"}"				
				+"</script>");	
		       sb.append("<span id=\""+id+"span\"><input type=\"file\" name=\""+name+"\" id=\""+id+"\" /></span>");
		       if(view)
		       {
		       sb.append("<span id=\"viewmsg\"></span>");
		       sb.append("<input type=\"hidden\" name=\"fileKey\" id=\"fileKey\" />");
		       }
		        
		return sb;
	}
	
	/**
	 * 获取上传路径,修改jsessionid传不到后台的bug jueyue --- 20130916
	 * @return
	 */
	private String getUploader() {
		return uploader+"&sessionId="+pageContext.getSession().getId()+"',";
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	public void setFormData(String formData) {
		this.formData = formData;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}

	 
	
}
