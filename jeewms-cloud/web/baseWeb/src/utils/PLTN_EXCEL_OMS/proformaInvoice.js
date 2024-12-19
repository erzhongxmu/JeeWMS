import saveAs from 'file-saver'
import Excel from 'exceljs'
import { myBase64Image } from '@/utils/PLTN_EXCEL_OMS/picture2.js'

export function exportProformaInvoice(header, columns, dataList, expertName) {
    const EXCEL_TYPE =
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8'
    console.log(dataList,'---------------')
    let workbook = new Excel.Workbook()
    workbook.created = new Date()
    workbook.modified = new Date()
    let worksheet = workbook.addWorksheet('sheet1',{views: [{showGridLines: false}]})// 创建一个第一行和列冻结的工作表
    worksheet.properties.defaultColWidth = 10//默认列宽
    worksheet.columns = columns//添加列标题并定义列键和宽度
    

    // 开始
    worksheet.mergeCells(`A1:J1`)//合并一系列单元格
    worksheet.getCell(`A1`).alignment = { vertical: 'top', horizontal: 'left' }//对齐方式
    worksheet.getCell(`A1`).value  = `ASTREA TRADING LIMITED - Estimate ${dataList.query05}`//设置A1的值
    worksheet.getCell(`A1`).font = {//字体设置
        name: 'Arial',
        size: 7,
        underline: false,
        bold: false,
        color: { argb: 'FF000000' }
    }
    worksheet.getCell(`A1`).border = {//在A1周围设置单个细边框
        top: {style:'thin',color: {argb:'FFc3cbdd'}},
        left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
        bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
        right: {style:'thin',color: {argb:'FFc3cbdd'}}
    }
    worksheet.getRow(1).height = 30// 设置特定的行高

    // 第二行
    let borderCol5 = ['A','E']
    worksheet.mergeCells(`A2:D2`)
    worksheet.mergeCells(`E2:J2`)
    borderCol5.map((item,index) => {
        worksheet.getCell(`${item}2`).border = {
            top: {style:'thin',color: {argb:'FFc3cbdd'}},
            left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
            bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
            right: {style:'thin',color: {argb:'FFc3cbdd'}}
        }
        worksheet.getCell(`${item}2`).alignment = { vertical: 'top', horizontal: 'left', wrapText: true };//对齐方式
        worksheet.getCell(`${item}2`).font = {
            name: 'Arial',
            size: 9,
            underline: false,
            bold: false,
            color: { argb: 'FF000000' }
        }
    })
    worksheet.getCell(`E2`).value  = `
Estimate ${dataList.query05}
Date : ${dataList.query06}
No. client : ${dataList.query07} `
    worksheet.getRow(2).height = 80

    // 第三行
    let borderCol4 = ['A','D','E']
    worksheet.mergeCells(`A3:C3`)
    worksheet.mergeCells(`E3:J3`)
    borderCol4.map((item,index) => {
        worksheet.getCell(`${item}3`).border = {
            top: {style:'thin',color: {argb:'FFc3cbdd'}},
            left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
            bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
            right: {style:'thin',color: {argb:'FFc3cbdd'}}
        }
        worksheet.getCell(`${item}3`).alignment = { vertical: 'middle', horizontal: 'left', wrapText: true };
        worksheet.getCell(`${item}3`).font = {
            name: 'Arial',
            size: 9,
            underline: false,
            bold: false,
            color: { argb: 'FF000000' }
        }
    })
    worksheet.getCell(`A3`).value  = ``
    worksheet.getCell(`E3`).value  = `
${dataList.query10}`
    worksheet.getRow(3).height = 160

    // 第四行
    worksheet.mergeCells(`A4:J4`)
    worksheet.getCell(`A4`).alignment = { vertical: 'middle', horizontal: 'left' }
    worksheet.getCell(`A4`).value  = 'Object : '
    worksheet.getCell(`A4`).border = {
        top: {style:'thin',color: {argb:'FFc3cbdd'}},
        left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
        bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
        right: {style:'thin',color: {argb:'FFc3cbdd'}}
    }
    worksheet.getCell(`A4`).font = {
        name: 'Arial',
        size: 9,
        underline: false,
        bold: false,
        color: { argb: 'FF000000' }
    }
    worksheet.getRow(4).height = 20

    // 第五行
    let tableHeader = ['Name / Code','Description','Qty / Unit','price','Sales tax','Line total']
    let tableRow = []//用来存取dataList.busiPoItemList有多少行[6,7,8,9,...]
    let tableValue = dataList.busiPoItemList
//     let tableValue = [
//         {
//           Name:'01-0043',
//           Description:`ROSEWOOD MADRID
// RWMA#0070 - SLIPPER MEN
// RWMA#0071 - SLIPPER MEN BAG`,
//           Qty:'6000',
//           Unit:'unité',
//           Price:'1440',
//           Tax:`0,000 %
// (0,000)`, 
//           Total:'8640'
//         },
//         {
//             Name:'01-0043',
//             Description:`ROSEWOOD MADRID
// RWMA#0070 - SLIPPER MEN
// RWMA#0071 - SLIPPER MEN BAG`,
//             Qty:'5000',
//             Unit:'unité',
//             Price:'1409',
//             Tax:`0,000 %
// (0,000)`, 
//             Total:'7045'
//           }
//     ]
    worksheet.mergeCells(`B5:D5`)
    worksheet.mergeCells(`G5:H5`)
    worksheet.mergeCells(`I5:J5`)
    let tableCol = ['A','B','E','F','G','I','J']
    tableCol.map((item,index) => {
        worksheet.getCell(`${item}5`).border = {
            top: {style:'thin',color: {argb:'FFc3cbdd'}},
            left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
            bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
            right: {style:'thin',color: {argb:'FFc3cbdd'}}
        }
        tableHeader.map((v,i) => {
            if(index == i) {
                worksheet.getCell(`${item}5`).value  = v
                worksheet.getCell(`${item}5`).font = {
                    name: 'Arial',
                    size: 9,
                    underline: false,
                    bold: true,
                    color: { argb: 'FF000000' }
                }
                worksheet.getCell(`${item}5`).alignment = { vertical: 'middle', horizontal: 'center' }
            } 
        })
    })
    worksheet.getRow(5).height = 20

    // 表内容
    let row = 6//第六行开始
    let tableColRow = []
    tableValue.map((item,index) => {
        tableRow.push(row+index)
    })
    tableRow.map((item,index) => {//对行进行循环
        worksheet.mergeCells(`B${item}:D${item}`)
        worksheet.mergeCells(`G${item}:H${item}`)
        worksheet.mergeCells(`I${item}:J${item}`)
        worksheet.getRow(item).height = 50
        let arr = []
        tableCol.map((v,i) => {//对列进行循环，具体到某个单元格getCell，单元格样式、合并
            worksheet.getCell(`${v}${item}`).border = {
                top: {style:'thin'},
                left: {style:'thin'}, 
                bottom: {style:'thin'},
                right: {style:'thin'}
            }
            worksheet.getCell(`${v}${item}`).font = {
                name: 'Arial',
                size: 10,
                underline: false,
                bold: false,
                color: { argb: 'FF000000' }
            }
            worksheet.getCell(`${v}${item}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true };
            arr.push(`${v}${item}`)//将每个单元格添加进arr中Array:['A6', 'B6', 'E6', 'F6', 'G6', 'I6', 'J6']
        })
        tableColRow.push(arr)//[Array,Array,Array]
    })
    console.log(tableColRow,'tableColRow');
    tableColRow.map((item,index) => {//循环赋值
        tableValue.map((v,i) => {
            if(index == i) {
                worksheet.getCell(`${item[0]}`).value  = v['query12']
                worksheet.getCell(`${item[1]}`).value  = v['query13']
                worksheet.getCell(`${item[2]}`).value  = v['query14']
                worksheet.getCell(`${item[3]}`).value  = v['query15']
                worksheet.getCell(`${item[4]}`).value  = v['query16']
                worksheet.getCell(`${item[5]}`).value  = v['query17']
            }      
        })
    })

    // Total
    row += tableValue.length
    let borderCol = ['A','H','J']
    worksheet.mergeCells(`A${row}:F${row}`)
    worksheet.mergeCells(`G${row}:H${row}`)
    worksheet.mergeCells(`I${row}:J${row}`)
    borderCol.map((item,index) => {
        worksheet.getCell(`${item}${row}`).border = {
            top: {style:'thin',color: {argb:'FFc3cbdd'}},
            left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
            bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
            right: {style:'thin',color: {argb:'FFc3cbdd'}}
        }
        worksheet.getCell(`${item}${row}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true };
        worksheet.getCell(`${item}${row}`).font = {
            name: 'Arial',
            size: 10,
            underline: false,
            bold: true,
            color: { argb: 'FF000000' }
        }
    })
    worksheet.getRow(row).height = 40
    worksheet.getCell(`G${row}`).value  = `Total
Total due`
    // 计算总价 
    let total = 0
    tableValue.map((item,index) => {
      total += Number(item.query17)
    })
    worksheet.getCell(`I${row}`).value  = total
    
    // 客户签名
    row += 1
    let borderCol1 = ['A','E']
    worksheet.mergeCells(`A${row}:D${row}`)
    worksheet.mergeCells(`E${row}:J${row}`)
    worksheet.getCell(`E${row}`).value  = `Client signature:`
    borderCol1.map((item,index) => {
        worksheet.getCell(`${item}${row}`).border = {
            top: {style:'thin',color: {argb:'FFc3cbdd'}},
            left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
            bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
            right: {style:'thin',color: {argb:'FFc3cbdd'}}
        }
        worksheet.getCell(`${item}${row}`).alignment = { vertical: 'middle', horizontal: 'left'};
        worksheet.getCell(`${item}${row}`).font = {
            name: 'Arial',
            size: 10,
            underline: false,
            bold: true,
            color: { argb: 'FF000000' }
        }
    })
    worksheet.getRow(row).height = 20

    // 备注
    row += 1
    let borderCol2 = ['A','C']
    worksheet.mergeCells(`A${row}:B${row}`)
    worksheet.mergeCells(`C${row}:J${row}`)
    worksheet.getCell(`A${row}`).value  = `Valid until :
Payment means :
Terms of payment :`
    worksheet.getCell(`C${row}`).value  = ` ${dataList.query18}
virement bancaire
à la commande`
    borderCol2.map((item,index) => {
        worksheet.getCell(`${item}${row}`).border = {
            top: {style:'thin',color: {argb:'FFc3cbdd'}},
            left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
            bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
            right: {style:'thin',color: {argb:'FFc3cbdd'}}
        }
        worksheet.getCell(`${item}${row}`).alignment = { vertical: 'middle', horizontal: 'left',wrapText: true};
        worksheet.getCell(`${item}${row}`).font = {
            name: 'Arial',
            size: 10,
            underline: false,
            bold: false,
            color: { argb: 'FF000000' }
        }
    })
    worksheet.getRow(row).height = 60

    // 标注
    row += 1
    worksheet.mergeCells(`A${row}:J${row}`)
    worksheet.getCell(`A${row}`).value  = ``
    worksheet.getCell(`A${row}`).alignment = { vertical: 'middle', horizontal: 'center'};
    worksheet.getCell(`A${row}`).font = {
        name: 'Arial',
        size: 8,
        underline: false,
        bold: false,
        color: { argb: 'FF000000' }
    }
    worksheet.getCell(`A${row}`).border = {
        top: {style:'thin',color: {argb:'FFc3cbdd'}},
        left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
        bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
        right: {style:'thin',color: {argb:'FFc3cbdd'}}
    }
    worksheet.getRow(row).height = 20

    // 页脚
    row += 1
    let borderCol3 = ['A','H','I','J']
    worksheet.mergeCells(`A${row}:G${row}`)
    borderCol3.map((item,index) => {
        worksheet.getCell(`${item}${row}`).border = {
            top: {style:'thin',color: {argb:'FFc3cbdd'}},
            left: {style:'thin',color: {argb:'FFc3cbdd'}}, 
            bottom: {style:'thin',color: {argb:'FFc3cbdd'}},
            right: {style:'thin',color: {argb:'FFc3cbdd'}}
        }
        worksheet.getCell(`${item}${row}`).alignment = { vertical: 'middle', horizontal: 'center',wrapText: true};
        worksheet.getCell(`${item}${row}`).font = {
            name: 'Arial',
            size: 8,
            underline: false,
            bold: false,
            color: { argb: 'FF000000' }
        }
    })
    worksheet.getCell(`A${row}`).value  = ``
worksheet.getCell(`J${row}`).value  = `Page 1/1`
    worksheet.getRow(row).height = 30

    // 图片
    const imageId1 = workbook.addImage({
        base64:myBase64Image,
        extension: 'jpeg',
    });
    worksheet.addImage(imageId1, {tl: { col: 0, row: 1 }, br: { col: 1.5, row: 2 }});
    









    workbook.xlsx.writeBuffer().then((buffer) => {
    const blob = new Blob([buffer], { type: EXCEL_TYPE })
    saveAs(blob, `${expertName}.xlsx`)
    })
}