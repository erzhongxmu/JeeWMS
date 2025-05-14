package org.jeecgframework.core.extend.swftools;

import org.jeecgframework.core.util.FileUtils;
/**
 * 文件转换调用接口
 * @author admin
 *
 */
public class SwfToolsUtil {
	public static void convert2SWF(String inputFile) {
		
		String extend=FileUtils.getExtend(inputFile);
		PDFConverter pdfConverter = new OpenOfficePDFConverter();
		SWFConverter swfConverter = new SWFToolsSWFConverter();
		if("pdf".equals(extend))
		{
			swfConverter.convert2SWF(inputFile,extend);
		}
		if("doc".equals(extend)|| "docx".equals(extend)|| "xls".equals(extend)|| "pptx".equals(extend)|| "xlsx".equals(extend)|| "ppt".equals(extend)|| "txt".equals(extend)|| "odt".equals(extend))
		{
			DocConverter converter = new DocConverter(pdfConverter,swfConverter);
			converter.convert(inputFile,extend);
		}
		
	}
}
