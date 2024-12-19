package com.base.modules.util.export;



public class ExportReportFactory
{
  private static IExportReport exportReport;

  public static IExportReport newInstance(IExportReport.ReportType reportType)
  {
    switch (reportType) {
    case Excel:
      exportReport = new ExportReportForExcel();
      break;

    }

    return exportReport;
  }
  public enum Font_Type
  {
    TITLE, HEADER, CONTENT;
  }
}
