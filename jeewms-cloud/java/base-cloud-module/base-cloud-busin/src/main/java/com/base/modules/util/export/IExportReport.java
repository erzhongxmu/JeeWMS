package com.base.modules.util.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public abstract interface IExportReport
{
  public abstract void createReport(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, Map<String, Object> paramMap)
    throws Exception;
  public ByteArrayOutputStream getStream(Map<String, Object> reportInfo)
  	throws Exception;
  public enum ReportType
  {
    JPG, PDF, Excel, Word;
  }
}
