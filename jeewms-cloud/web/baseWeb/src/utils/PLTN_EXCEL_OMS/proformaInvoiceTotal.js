import saveAs from 'file-saver'
import Excel from 'exceljs'

export function exportProformaInvoiceTotal(header, columns, dataList, expertName) {
    const EXCEL_TYPE =
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8'
    let workbook = new Excel.Workbook()
    workbook.created = new Date()
    workbook.modified = new Date()
    let worksheet = workbook.addWorksheet('sheet1',{views: [{showGridLines: false}]})
    worksheet.properties.defaultColWidth = 12
    worksheet.columns = columns
    
    // 开始
    let totalPrice = 0
    dataList.map((item,index) => {
      totalPrice += Number(item.query10)
    })
    worksheet.mergeCells(`A1:P1`)
    worksheet.getCell(`A1`).fill = {
        type: 'gradient',
        gradient: 'angle',
        degree: 0,
        stops: [
          {position:1, color:{argb:'FF4c1130'}}
        ]
    }
    worksheet.getCell(`A1`).font = {
        name: 'Lato',
        size: 10,
        underline: false,
        bold: true,
        color: { argb: 'FFFFFFFF' }
    }
    worksheet.getCell(`A1`).alignment = { vertical: 'middle', horizontal: 'center' }
    worksheet.getCell(`A1`).value  = totalPrice
    worksheet.getRow(1).height = 25
    
    // 第二行
    let tableHeader = ['RECEIVED DATE','PAYMENT LIST','SELECT','INV REF','','MEMO','CLIENT','REGION','CUR','INV AMOUNT','PAYMENT AMOUNT','STATUS','SELLSY INV','BALANCE AFTER THIS PAYMENT','PO NO.','PO AMOUNT']
    let tableCol = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P']
    worksheet.getRow(2).height = 40
    tableCol.map((item,index) => {
        tableHeader.map((v,i) => {
            if(index == i) {
                worksheet.getCell(`${item}2`).value  = v
                worksheet.getCell(`${item}2`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true }
                worksheet.getCell(`${item}2`).font = {
                    name: 'Lato',
                    size: 10,
                    underline: false,
                    bold: true,
                    color: { argb: 'FFFFFFFF' }
                }
                worksheet.getCell(`${item}2`).fill = {
                    type: 'gradient',
                    gradient: 'angle',
                    degree: 0,
                    stops: [
                      {position:1, color:{argb:'FFa64d79'}}
                    ]
                }

            }
           
        })
      
    })
    worksheet.getCell(`K2`).fill = {
        type: 'gradient',
        gradient: 'angle',
        degree: 0,
        stops: [
          {position:1, color:{argb:'FF4c1130'}}
        ]
    }

    // 表内容
//     let tableValue = [
//         {
//             Date:'2022/10/18',
//             Payment:'P2A#1033 EUR PRO',
//             Select:'',
//             Ref:'DASTHK-02471',
//             attr:'',
//             Memo:`ADARE MANOR HOTEL
// IRELAND - 22M10 - 40K
// SHOPPING BAG DPEU-12053`,
//             Client:'ADARE MANOR HOTEL IRELAND',
//             Region:'P-EUR',
//             Cur:'EUR',
//             IAmount:'27935',
//             PAmount:'8380.5',
//             Status:'',
//             Sellsy:'DPEU-12053',
//             Balance:'19554.5',
//             PoNum:'',
//             PoMoney:''
//         },
//         {
//             Date:'2022/10/18',
//             Payment:'P2A#1033 EUR PRO',
//             Select:'',
//             Ref:'DASTHK-02471',
//             attr:'',
//             Memo:`ADARE MANOR HOTEL
// IRELAND - 22M10 - 40K
// SHOPPING BAG DPEU-12053`,
//             Client:'ADARE MANOR HOTEL IRELAND',
//             Region:'P-EUR',
//             Cur:'EUR',
//             IAmount:'27935',
//             PAmount:'8380.5',
//             Status:'',
//             Sellsy:'DPEU-12053',
//             Balance:'19554.5',
//             PoNum:'',
//             PoMoney:''
//         },
//     ]

    


    let row = 3
    let tableRow = []
    let tableColRow = []
    dataList.map((item,index) => {
        tableRow.push(row+index)
    })
    tableRow.map((item,index) => {
        let arr = []
        worksheet.getRow(item).height = 30
        tableCol.map((v,i) => {
          worksheet.getCell(`${v}${item}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true }
          worksheet.getCell(`F${item}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true }
          worksheet.getCell(`G${item}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true }
          worksheet.getCell(`${v}${item}`).font = {
            name: 'Lato',
            size: 8,
            underline: false,
            bold: false,
            color: { argb: 'FF8b1b47' }
          }
          worksheet.getCell(`${v}${item}`).border = {
            top: {style:'thin',color: {argb:'FFd5a6bd'}},
            left: {style:'thin',color: {argb:'FFd5a6bd'}}, 
            bottom: {style:'thin',color: {argb:'FFd5a6bd'}},
            right: {style:'thin',color: {argb:'FFd5a6bd'}}
          }
          worksheet.getCell(`A${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFffff00'}}
            ]
          }
          worksheet.getCell(`B${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFffff00'}}
            ]
          }
          worksheet.getCell(`C${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFead1dc'}}
            ]
          }
          worksheet.getCell(`D${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFead1dc'}}
            ]
          }
          worksheet.getCell(`K${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF741b47'}}
            ]
          }
          worksheet.getCell(`N${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFf3f3f3'}}
            ]
          }
          worksheet.getCell(`O${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFead1dc'}}
            ]
          }
          worksheet.getCell(`P${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFead1dc'}}
            ]
          }
          worksheet.getCell(`K${item}`).font = {
            name: 'Lato',
            size: 8,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          }
          worksheet.getCell(`D${item}`).font = {
            name: 'Lato',
            size: 8,
            underline: false,
            bold: true,
            color: { argb: 'FF741b79' }
          }
          worksheet.getCell(`F${item}`).font = {
            name: 'Lato',
            size: 8,
            underline: false,
            bold: true,
            color: { argb: 'FF741b79' }
          }
          worksheet.getCell(`M${item}`).font = {
            name: 'Lato',
            size: 8,
            underline: false,
            bold: true,
            color: { argb: 'FFa21b47' }
          }
          arr.push(`${v}${item}`)
        })
        tableColRow.push(arr)
    })

    tableColRow.map((item,index) => {
      dataList.map((v,i) => {
          if(index == i) {
            worksheet.getCell(`${item[0]}`).value  = v['query16']
            worksheet.getCell(`${item[1]}`).value  = v['query17']
            worksheet.getCell(`${item[2]}`).value  = v['query18']
            worksheet.getCell(`${item[3]}`).value  = v['query19']
            worksheet.getCell(`${item[4]}`).value  = v['attr']
            worksheet.getCell(`${item[5]}`).value  = v['query05']
            worksheet.getCell(`${item[6]}`).value  = v['query06']
            worksheet.getCell(`${item[7]}`).value  = v['query07']
            worksheet.getCell(`${item[8]}`).value  = v['query08']
            worksheet.getCell(`${item[9]}`).value  = v['query09']
            worksheet.getCell(`${item[10]}`).value  = v['query10']
            worksheet.getCell(`${item[11]}`).value  = v['query11']
            worksheet.getCell(`${item[12]}`).value  = v['query12']
            worksheet.getCell(`${item[13]}`).value  = v['query13']
            worksheet.getCell(`${item[14]}`).value  = v['query14']
            worksheet.getCell(`${item[15]}`).value  = v['query15']
          }
      })
    })


    workbook.xlsx.writeBuffer().then((buffer) => {
    const blob = new Blob([buffer], { type: EXCEL_TYPE })
    saveAs(blob, `${expertName}.xlsx`)
    })
}