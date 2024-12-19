import saveAs from 'file-saver'
import Excel from 'exceljs'

export function exportSalesContract(header, columns, dataList, expertName) {
    console.log(dataList,'==============111111');
    const EXCEL_TYPE =
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8'
    let workbook = new Excel.Workbook()
    workbook.created = new Date()
    workbook.modified = new Date()
    let worksheet = workbook.addWorksheet('sheet1',{views: [{showGridLines: false}]})
    worksheet.properties.defaultColWidth = 14
    worksheet.columns = columns
    let selectCloumns =['A','D','E','F']//设置列宽
    selectCloumns.map((item,index) => {
      const nameCol = worksheet.getColumn(item);
      nameCol.width = 25;
    })
    // 表1
    let table1ColA = ['CUSTOMER','PRODUCTS','PO','SUPPLIER','GOOGLE DRIVE','COMPANY']
    let table1ColE = ['PO#','ASTREA INV#','SALES#','PURCHASER']
    let valueArr1 = []
    let valueArr2 = []
    let table2Valueleft = []
    // let valueArr1 = ['RELAIS & CHATEAUX','RECH#0390 - SHOPPING BAG SMALL','2022/10/19','FUMING - 富明','RELAIS & CHATEAUX - 22M10 - RECH#0390 - SHOPPING BAG SMALL','P-EUR']
    // let valueArr2 = ['QB22015','DASTHK-02647','STOCK','Berry']
    let table1ColRow = []
    let currencyType = dataList[0].query17 
    let address = dataList[0].query19

    dataList.map((item,index) => {
      valueArr1[0] = item.query05
      valueArr1[1] = item.query07
      valueArr1[2] = item.query09
      valueArr1[3] = item.query11
      valueArr1[4] = item.query13
      valueArr1[5] = item.query14
      valueArr2[0] = item.query06
      valueArr2[1] = item.query08
      valueArr2[2] = item.query10
      valueArr2[3] = item.query12
      table2Valueleft[0] = item.query15
      table2Valueleft[1] = item.query16
      table2Valueleft[2] = item.query18 && item.query18.indexOf("否") != -1?item.query18.split(',')[1]:''
      table2Valueleft[3] = item.num16 ? item.num16.toString() : ''
      // table2Valueleft[3] = item.query18 && item.query18.indexOf("是") != -1?item.query18.split(',')[1]:''
      table2Valueleft[4] = item.query20 - item.num18 + ''

    })

    table1ColA.map((item,index) => {
      table1ColRow.push(index+1)
    })

    table1ColRow.map((item,index) => {
      worksheet.getRow(item).height = 30

      table1ColA.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`A${item}`).alignment = { vertical: 'middle', horizontal: 'center' }
          worksheet.getCell(`A${item}`).fill = {
            type: 'pattern',
            pattern:'none',
            bgColor:{rgb:'#38761d'}
          };
          worksheet.getCell(`A${item}`).value  = v
          worksheet.getCell(`A${item}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`A${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF38761d'}}
            ]
          }
          worksheet.getCell(`A${item}`).font = {
            name: 'Arial',
            size: 12,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          }
        }
      })
      worksheet.getCell(`D${2}`).alignment = { vertical: 'middle', horizontal: 'center' }
      worksheet.getCell(`D${2}`).fill = { type: 'pattern', pattern:'none', bgColor:{rgb:'#38761d'} };
      worksheet.getCell(`D${2}`).value  = dataList[0].query22
      table1ColE.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`E${item}`).alignment = { vertical: 'middle', horizontal: 'center' }
          worksheet.getCell(`E${item}`).fill = {
            type: 'pattern',
            pattern:'none',
            bgColor:{rgb:'#38761d'}
          };
          worksheet.getCell(`E${item}`).value  = v
          worksheet.getCell(`E${item}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`E${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF38761d'}}
            ]
          }
          worksheet.getCell(`E${item}`).font = {
            name: 'Arial',
            size: 12,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          }
        }
      })

      // 第二行字体样式
      worksheet.getCell(`B2`).font = {
        name: 'Arial',
        size: 12,
        underline: false,
        bold: false,
        color: { argb: 'FF000000' }
      }
      worksheet.getCell(`B2`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true }
      worksheet.getCell(`D2`).font = {
        name: 'Arial',
        size: 12,
        underline: false,
        bold: false,
        color: { argb: 'FF000000' }
      }
      worksheet.getCell(`D2`).fill = {
        type: 'gradient',
        gradient: 'angle',
        degree: 0,
        stops: [
          {position:1, color:{argb:'FFb6d7a8'}}
        ]
      }

      valueArr1.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`B${item}`).alignment = { vertical: 'middle', horizontal: 'center' }
          worksheet.getCell(`B${item}`).value  = v
          worksheet.getCell(`B${item}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`B${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFb6d7a8'}}
            ]
          }
          worksheet.getCell(`B${item}`).font = {
            name: 'Arial',
            size: 13,
            underline: false,
            bold: true,
            color: { argb: 'FF000000' }
          }
        }
      })

      valueArr2.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`F${item}`).alignment = { vertical: 'middle', horizontal: 'center' }
          worksheet.getCell(`F${item}`).value  = v
          worksheet.getCell(`F${item}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`F${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FFb6d7a8'}}
            ]
          }
          worksheet.getCell(`F${item}`).font = {
            name: 'Arial',
            size: 13,
            underline: false,
            bold: true,
            color: { argb: 'FF000000' }
          }
        }
      })

    })

    // 填充颜色
    worksheet.getCell(`F1`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FFFFFFFF'}}
      ]
    }
    worksheet.getCell(`H1`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FFb6d7a8'}}
      ]
    }

    // 合并单元格
    let mergeArr1 = ['1','3','4']
    let mergeArr2 = ['1','2','3','4']
    let mergeArr3 = ['5','6']
    mergeArr1.map((item,index) => {
      worksheet.mergeCells(`B${item}:D${item}`)
    })
    mergeArr2.map((item,index) => {
      worksheet.mergeCells(`F${item}:G${item}`)
    })
    worksheet.mergeCells(`B2:C2`)
    mergeArr3.map((item,index) => {
      worksheet.mergeCells(`B${item}:G${item}`)
    })
    worksheet.mergeCells(`A7:G7`)
    worksheet.getRow(7).height = 70
    worksheet.getCell(`A7`).border = {
      top: {style:'thin'},
      left: {style:'thin'},
      bottom: {style:'thin'},
      right: {style:'thin'}
    }
    worksheet.mergeCells(`H1:H7`)
    worksheet.getCell(`H1`).border = {
      top: {style:'thin'},
      left: {style:'thin'},
      bottom: {style:'thin'},
      right: {style:'thin'}
    }

    // 表2
    let row = 7
    let table2ColA = ['QTY','UNIT PRICE','SPL/FILM/MOULD NON REFUNDABLE','SPL/FILM/MOULD REFUNDABLE','TOTAL (1)']
    let table2ColRow = []
    table2ColA.map((item,index) => {
      table2ColRow.push(row+index+1)
    })
    table2ColRow.map((item,index) => {
      worksheet.getRow(item).height = 30
      worksheet.getCell(`A${item}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true };
      table2ColA.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`A${item}`).value  = v
          worksheet.getCell(`A${item}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`A${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF38761d'}}
            ]
          }
          worksheet.getCell(`A${item}`).font = {
            name: 'Arial',
            size: 12,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          }
        }
      })
    })

    worksheet.getCell(`B12`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FFb6d7a8'}}
      ]
    }

    // 合并单元格
    // 左
    table2ColRow.map((item,index) => {
      worksheet.mergeCells(`B${item}:C${item}`)
      worksheet.getCell(`B${item}`).border = {
        top: {style:'thin'},
        left: {style:'thin'},
        bottom: {style:'thin'},
        right: {style:'thin'}
      }


      worksheet.getCell(`D${item}`).border = {
        top: {style:'thin'},
        left: {style:'thin'},
        bottom: {style:'thin'},
        right: {style:'thin'}
      }

      table2Valueleft.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`B${item}`).value  = v
        }
      })

    })

    // 右
    row += 1
    worksheet.mergeCells(`D${row}:G${row}`)
    worksheet.getCell(`D${row}`).value  = 'BANKING INFORMATION'
    worksheet.getCell(`D${row}`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FF38761d'}}
      ]
    }
    worksheet.getCell(`D${row}`).font = {
      name: 'Arial',
      size: 12,
      underline: false,
      bold: false,
      color: { argb: 'FFFFFFFF' }
    }
    worksheet.getCell(`D${row}`).alignment = { vertical: 'middle', horizontal: 'center' };
    row += 1
    worksheet.getCell(`D${row}`).value  = 'CURRENCY'
    worksheet.getCell(`D${row}`).alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell(`D${row}`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FF6aa84f'}}
      ]
    }
    worksheet.getCell(`D${row}`).font = {
      name: 'Arial',
      size: 12,
      underline: false,
      bold: false,
      color: { argb: 'FFFFFFFF' }
    }
    worksheet.mergeCells(`E${row}:G${row}`)
    worksheet.getCell(`E${row}`).value  = currencyType
    worksheet.getCell(`E${row}`).font = {
      name: 'Arial',
      size: 12,
      underline: false,
      bold: false,
      color: { argb: 'FF000000' }
    }
    worksheet.getCell(`E${row}`).alignment = { vertical: 'middle', horizontal: 'center' };
    row += 1
    worksheet.mergeCells(`D${row}:G${row+2}`)
    worksheet.getCell(`D${row}`).alignment = { vertical: 'middle', horizontal: 'left', wrapText: true };
//     worksheet.getCell(`D${row}`).value  = `户名:钟福明
// 账号:6212 2620 1004 3694 485
// 开户行:中国工商银行东莞分行新楼桥分理处
// 地址:东莞市运河东一路 157 号`
    worksheet.getCell(`D${row}`).value  = address
    worksheet.getCell(`D${row}`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FF93c47d'}}
      ]
    }
    worksheet.getCell(`D${row}`).font = {
      name: 'Arial',
      size: 12,
      underline: false,
      bold: false,
      color: { argb: 'FF000000' }
    }

    // 下
    row += 3
    worksheet.mergeCells(`A${row}:A${row+1}`)
    worksheet.getCell(`A${row+1}`).value  = 'Others costs'
    worksheet.getCell(`A${row+1}`).font = {
      name: 'Arial',
      size: 10,
      underline: false,
      bold: false,
      color: { argb: 'FF000000' }
    }
    worksheet.getCell(`A${row+1}`).alignment = { vertical: 'bottom', horizontal: 'center' };
    worksheet.mergeCells(`B${row}:C${row+1}`)
    worksheet.mergeCells(`D${row}:F${row+1}`)
    worksheet.getCell(`D${row+1}`).value  = 'Note'
    worksheet.getCell(`D${row+1}`).font = {
      name: 'Arial',
      size: 10,
      underline: false,
      bold: false,
      color: { argb: 'FF000000' }
    }
    worksheet.getCell(`D${row+1}`).alignment = { vertical: 'bottom', horizontal: 'center' };
    worksheet.mergeCells(`G${row}:G${row+1}`)

    // 其它费用
    row += 2
    let mergeTable2Row = [`${row}`,`${row+1}`,`${row+2}`]
    worksheet.getCell('B15').value  = dataList[0].num18//其他费用
    mergeTable2Row.map((item,index) => {
      worksheet.getRow(item).height = 30

      worksheet.mergeCells(`B${item}:C${item}`)
      worksheet.mergeCells(`D${item}:G${item}`)
      worksheet.getCell(`A${item}`).border = {
        top: {style:'thin'},
        left: {style:'thin'},
        bottom: {style:'thin'},
        right: {style:'thin'}
      }
      worksheet.getCell(`B${item}`).border = {
        top: {style:'thin'},
        left: {style:'thin'},
        bottom: {style:'thin'},
        right: {style:'thin'}
      }
      worksheet.getCell(`E${item}`).border = {
        top: {style:'thin'},
        left: {style:'thin'},
        bottom: {style:'thin'},
        right: {style:'thin'}
      }
    })
    // 总费用
    row += 3
    worksheet.getRow(row).height = 30
    worksheet.getCell(`A${row}`).border = {
      top: {style:'thin'},
      left: {style:'thin'},
      bottom: {style:'thin'},
      right: {style:'thin'}
    }
    worksheet.getCell(`A${row}`).value  = 'TOTAL (2)'
    worksheet.getCell(`A${row}`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FF38761d'}}
      ]
    }
    worksheet.getCell(`A${row}`).font = {
      name: 'Arial',
      size: 12,
      underline: false,
      bold: false,
      color: { argb: 'FFFFFFFF' }
    }
    worksheet.getCell(`A${row}`).alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.mergeCells(`B${row}:C${row}`)
    worksheet.getCell(`B${row}`).border = {
      top: {style:'thin'},
      left: {style:'thin'},
      bottom: {style:'thin'},
      right: {style:'thin'}
    }
    worksheet.getCell(`B${row}`).value  = dataList[0].num18
    worksheet.getCell(`B${row}`).alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell(`B${row}`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FF6aa84f'}}
      ]
    }
    worksheet.getCell(`B${row}`).font = {
      name: 'Arial',
      size: 12,
      underline: false,
      bold: false,
      color: { argb: 'FF000000' }
    }
    
    // 费率
    row += 2
    let table2Arr1 = ['TOTAL IN PO#','VAT%']
    // let table2Value1= ['43043','0%']
    var vat = ''
    if (dataList[0].num10) {
      vat = dataList[0].num10+'%'
    }else{
      vat = dataList[0].num10?dataList[0].num10:0+'%'
    }
    let table2Value1= [dataList[0].query20,vat]
    let table2Row = []

    worksheet.mergeCells(`D${row}:G${row}`)
    worksheet.getCell(`D${row}`).value  = 'TOTAL (1+2) MATCH WITH PO'
    worksheet.getCell(`D${row}`).alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell(`D${row}`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FF4a86e8'}}
      ]
    }
    worksheet.getCell(`D${row}`).font = {
      name: 'Arial',
      size: 12,
      underline: false,
      bold: false,
      color: { argb: 'FFFFFFFF' }
    }
    worksheet.getCell(`D${row}`).border = {
      top: {style:'thin'},
      left: {style:'thin'},
      bottom: {style:'thin'},
      right: {style:'thin'}
    }

    table2Arr1.map((item,index) => {
      table2Row.push(row+index)
    })
    table2Row.map((item,index) => {
      worksheet.getRow(item).height = 30
      worksheet.mergeCells(`B${item}:C${item}`)

      table2Arr1.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`A${item}`).alignment = { vertical: 'middle', horizontal: 'center' }
          worksheet.getCell(`A${item}`).value  = v
          worksheet.getCell(`A${item}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`A${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF38761d'}}
            ]
          }
          worksheet.getCell(`A${item}`).font = {
            name: 'Arial',
            size: 12,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          }
        }
      })

      table2Value1.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`B${item}`).alignment = { vertical: 'middle', horizontal: 'center' }
          worksheet.getCell(`B${item}`).value  = v
          worksheet.getCell(`B${item}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`B${item}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF6aa84f'}}
            ]
          }
          worksheet.getCell(`B${item}`).font = {
            name: 'Arial',
            size: 12,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          }
        }
      })
    })

    // Payments
    row += table2Arr1.length
    worksheet.getRow(row).height = 50
    worksheet.mergeCells(`A${row}:G${row}`)
    worksheet.getCell(`A${row}`).alignment = { vertical: 'left', horizontal: 'bottom' }
    worksheet.getCell(`A${row}`).value  = 'Payments'
    worksheet.getCell(`A${row}`).font = {
      name: 'Arial',
      size: 16,
      underline: false,
      bold: true,
      color: { argb: 'FF000000' }
    }
    worksheet.getCell(`A${row}`).border = {
      bottom: {style:'thin'},
    }

    row += 1
    let table3Arr1 = ['PR01 - MERCHANDISE (DEPOSIT)','PR01 - MERCHANDISE (BALANCE)','PR02 - SAMPLE REFUNDABLE','PR03 - SAMPLE NON REFUNDABLE']
    let table3Arr2 = ['PR07 - FILM/MOULD NON REFUNDABLE','PR08 - CERTIFICATION','PR09 - OTHER SERVICES (PRINTING…)']
    let table3Row = []

    table3Arr1.map((item,index) => {
      table3Row.push(row + index)
    })
    table3Row.map((item,index) => {
      worksheet.getRow(item).height = 15
      worksheet.mergeCells(`A${item}:B${item}`)
      worksheet.mergeCells(`C${item}:D${item}`)
      worksheet.mergeCells(`E${item}:G${item}`)
      worksheet.getCell(`A${item}`).fill = {
        type: 'gradient',
        gradient: 'angle',
        degree: 0,
        stops: [
          {position:1, color:{argb:'FFb6d7a8'}}
        ]
      }
      worksheet.getCell(`C${item}`).fill = {
        type: 'gradient',
        gradient: 'angle',
        degree: 0,
        stops: [
          {position:1, color:{argb:'FFb6d7a8'}}
        ]
      }
      worksheet.getCell(`E${item}`).fill = {
        type: 'gradient',
        gradient: 'angle',
        degree: 0,
        stops: [
          {position:1, color:{argb:'FFb6d7a8'}}
        ]
      }


      table3Arr1.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`A${item}`).value  = v
          worksheet.getCell(`A${item}`).font = {
            name: 'Arial',
            size: 7,
            underline: false,
            bold: false,
            color: { argb: 'FF000000' }
          };

        }
      })

      table3Arr2.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`C${item}`).value  = v
          worksheet.getCell(`C${item}`).font = {
            name: 'Arial',
            size: 7,
            underline: false,
            bold: false,
            color: { argb: 'FF000000' }
          };
        }
      })
    })

    row += table3Arr1.length
    let table3Arr3 = ['VAT#','CODE','TOTAL','PAYMENT LIST','PAYMENT DATE','REFERENCE','VALIDATION']
    let table3ArrCol = ['A','B','C','D','E','F','G']
    let table3ArrVal = [['','PR01','12912.9','','','',''],['','PR01','12912.9','','','',''],['','PR01','12912.9','','','',''],['','PR01','12912.9','','','',''],['','PR01','12912.9','','','','']]
    let table3Arr3Row = []
    let table3RowCol = []
    table3ArrVal.map((item,index) => {
      table3Arr3Row.push(row+index+1)
    })

    console.log(table3Arr3Row,'888888888')

    table3ArrCol.map((item,index) => {
      worksheet.getRow(row).height = 30

      table3Arr3.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`${item}${row}`).value  = v
          worksheet.getCell(`${item}${row}`).alignment = { vertical: 'middle', horizontal: 'center' }
          worksheet.getCell(`${item}${row}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
          worksheet.getCell(`${item}${row}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF38761d'}}
            ]
          };
          worksheet.getCell(`${item}${row}`).font = {
            name: 'Arial',
            size: 12,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          };

        }
      })
    })

    table3Arr3Row.map((item,index) => {
      let arr = []
      table3ArrCol.map((v,i) => {
        arr.push(`${v}${item}`)
      })
      table3RowCol.push(arr)

    })

    // payment赋值
    table3RowCol.map((item,index) => {
      // worksheet.getCell(item[1]).value  = dataList[0].busiPoItemList[index]?dataList[0].busiPoItemList[index].query23:''
      // worksheet.getCell(item[2]).value  = dataList[0].busiPoItemList[index]?dataList[0].busiPoItemList[index].query24:''
    })

    // 批量加细线
    table3Arr3Row.map((item,index) => {
      worksheet.getRow(item).height = 30

      table3ArrCol.map((v,i) => {
        table3RowCol.push(`${v}${item}`)
        worksheet.getCell(`${v}${item}`).border = {
          top: {style:'thin'},
          left: {style:'thin'},
          bottom: {style:'thin'},
          right: {style:'thin'}
        }
      })
    })

    // 右PAYPALA#22015
    worksheet.mergeCells(`H8:H31`)
    worksheet.getCell(`H8`).value  = dataList[0].query23
    worksheet.getCell(`H8`).font = {
      name: 'Arial',
      size: 36,
      underline: false,
      bold: true,
      color: { argb: 'FF000000' }
    }
    worksheet.getCell(`H8`).alignment = { vertical: 'middle', horizontal: 'center', textRotation: -90 }
    worksheet.getCell(`H8`).border = {
      top: {style:'thin'},
      left: {style:'thin'},
      bottom: {style:'thin'},
      right: {style:'thin'}
    }


    // 表4
    row = row+table3ArrVal.length + 2
    worksheet.getRow(row).height = 50
    worksheet.mergeCells(`A${row}:G${row}`)
    worksheet.getCell(`A${row}`).alignment = { vertical: 'left', horizontal: 'bottom' }
    worksheet.getCell(`A${row}`).value  = 'Deliveries & Stock'
    worksheet.getCell(`A${row}`).font = {
      name: 'Arial',
      size: 16,
      underline: false,
      bold: true,
      color: { argb: 'FF000000' }
    }
    worksheet.getCell(`A${row}`).border = {
      bottom: {style:'thin'},
    }

    row += 1
    let table4Arr1 = ['DATE','SHIP#','QTY SHIPPED','SALES FAPIAO/INV#','PAYMENT DATE','VALIDATION DATE']
    worksheet.getRow(row).height = 30
    let table4Value = [[],[],[],[]]
    let table4Row = []
    let table4ArrCol = ['A','B','C','D','F','G']
    worksheet.mergeCells(`D${row}:E${row}`)

    table4Value.map((item,index) => {
      table4Row.push(row+index+1)
    })

    // 表头赋值
    table4ArrCol.map((item,index) => {
      table4Arr1.map((v,i) => {
        if(index == i) {
          worksheet.getCell(`${item}${row}`).value  = v
          worksheet.getCell(`${item}${row}`).fill = {
            type: 'gradient',
            gradient: 'angle',
            degree: 0,
            stops: [
              {position:1, color:{argb:'FF38761d'}}
            ]
          };
          worksheet.getCell(`${item}${row}`).font = {
            name: 'Arial',
            size: 12,
            underline: false,
            bold: false,
            color: { argb: 'FFFFFFFF' }
          };
          worksheet.getCell(`${item}${row}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true };
          worksheet.getCell(`${item}${row}`).border = {
            top: {style:'thin'},
            left: {style:'thin'},
            bottom: {style:'thin'},
            right: {style:'thin'}
          }
        }
      })

    })

    // 批量加细线
    table4Row.map((item,index) => {
      worksheet.getRow(item).height = 30
      worksheet.mergeCells(`D${item}:E${item}`)

      table4ArrCol.map((v,i) => {
        worksheet.getCell(`${v}${item}`).border = {
          top: {style:'thin'},
          left: {style:'thin'},
          bottom: {style:'thin'},
          right: {style:'thin'}
        }

      })
    })


    worksheet.mergeCells(`H32:H39`)
    worksheet.getCell(`H32`).border = {
      top: {style:'thin'},
      left: {style:'thin'},
      bottom: {style:'thin'},
      right: {style:'thin'}
    }
    worksheet.getCell(`H32`).fill = {
      type: 'gradient',
      gradient: 'angle',
      degree: 0,
      stops: [
        {position:1, color:{argb:'FFb6d7a8'}}
      ]
    }

    workbook.xlsx.writeBuffer().then((buffer) => {
    const blob = new Blob([buffer], { type: EXCEL_TYPE })
    saveAs(blob, `${expertName}.xlsx`)
    })
}