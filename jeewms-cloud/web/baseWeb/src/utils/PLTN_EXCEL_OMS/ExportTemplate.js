import saveAs from 'file-saver'
import Excel from 'exceljs'

export function ExportTemplate(header, columns, dataList, expertName) {
    const EXCEL_TYPE =
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8'
    let workbook = new Excel.Workbook()
    workbook.created = new Date()
    workbook.modified = new Date()
    let worksheet = workbook.addWorksheet('sheet1',{views: [{showGridLines: true}]})
    worksheet.properties.defaultRowHeight = 25;
    let columns2 = []
    columns.map((item,index)=>{
        item.style = {alignment:{vertical: 'middle', horizontal: 'center'}}
        item.width = 25
        columns2.push(item)
    })
    worksheet.columns = columns2
    dataList.map((v,i)=>{
        worksheet.addRow(v);
    })
    workbook.xlsx.writeBuffer ().then((buffer) => {
    const blob = new Blob([buffer], { type: EXCEL_TYPE })
    saveAs(blob, `${expertName}.xlsx`)
    })
}