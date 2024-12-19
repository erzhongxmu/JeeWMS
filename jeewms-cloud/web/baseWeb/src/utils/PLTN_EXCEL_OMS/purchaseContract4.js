import saveAs from 'file-saver'
import Excel from 'exceljs'
import { myBase64Image } from '@/utils/PLTN_EXCEL_OMS/picture1.js'
import {numSub,numMulti,accDiv,numAdd} from '@/utils/util'

export function exportFile4(header, columns, dataList, expertName) {
  let topData = dataList[0]
  let data = dataList[0].busiPoItemList
  const EXCEL_TYPE =
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8'
  let workbook = new Excel.Workbook()
  workbook.created = new Date()
  workbook.modified = new Date()
  let worksheet = workbook.addWorksheet('sheet1', { views: [{ showGridLines: false }] })
  worksheet.properties.defaultColWidth = 14
  worksheet.columns = columns
  // 采购合同标题
  worksheet.mergeCells('A1:C1')
  worksheet.getCell('A1').value = '样品采购合同'
  worksheet.getCell('A1').font = {
    family: 4,
    size: 30,
    underline: false,
    bold: false
  };
  worksheet.getCell('A1').alignment = { vertical: 'middle', horizontal: 'left' }
  //销售单号
  worksheet.getCell('D1').value = 'Subject:' + topData.query12
  worksheet.getCell('D1').font = {
    family: 4,
    size: 18,
    underline: false,
    bold: false
  };
  worksheet.getCell('D1').alignment = { vertical: 'middle', horizontal: 'center' , wrapText: true }
  worksheet.mergeCells('D1:F1')
  // 图片
  worksheet.mergeCells('G1:J1')
  const imageId1 = workbook.addImage({
    base64: myBase64Image,
    extension: 'jpeg',
  });
  worksheet.addImage(imageId1, 'G1:J1');


  // 指定第一行行高
  worksheet.getRow(1).height = 120

  // 单据头
  worksheet.getCell('A2').value = '供应商Vendor：'
  worksheet.getCell('A3').value = '地址Address：'
  worksheet.getCell('A4').value = '制造商Manufacturer：'
  worksheet.getCell('A5').value = '联系人Contact：'
  worksheet.getCell('A6').value = '联系电话Tel：'
  worksheet.getCell('G3').value = '采购合同号Contract No：'
  worksheet.getCell('G4').value = '日期DATE：'
  worksheet.getCell('G5').value = '采购名Purchaser：'
  worksheet.getCell('G6').value = '跟单员Merchandiser：'
  worksheet.mergeCells('B2:D2')
  worksheet.mergeCells('B3:D3')
  worksheet.mergeCells('B4:D4')
  worksheet.mergeCells('B5:D5')
  worksheet.mergeCells('B6:D6')
  worksheet.mergeCells('I3:J3')
  worksheet.mergeCells('I4:J4')
  worksheet.mergeCells('I5:J5')
  worksheet.mergeCells('I6:J6')
  worksheet.mergeCells('G3:H3')
  worksheet.mergeCells('G4:H4')
  worksheet.mergeCells('G5:H5')
  worksheet.mergeCells('G6:H6')

  worksheet.getCell('B2').value = topData.query42
  worksheet.getCell('B3').value = topData.query07
  worksheet.getCell('I3').value = topData.query06
  worksheet.getCell('I4').value = topData.query08
  worksheet.getCell('B5').value = topData.query09
  worksheet.getCell('I5').value = topData.query10
  worksheet.getCell('B6').value = topData.query11
  worksheet.getCell('B4').value = topData.query05


  // 对齐、换行
  worksheet.getCell('A2').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('A3').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('A4').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('A5').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('A6').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('H3').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('H4').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('H5').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('H6').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('B3').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('I3').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('I4').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('B5').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('I5').alignment = { vertical: 'middle', horizontal: 'left' };
  worksheet.getCell('B6').alignment = { vertical: 'middle', horizontal: 'left' };

  // 设置单据头行高
  let headArr = ['3', '5', '6']
  headArr.map((item, index) => {
    worksheet.getRow(item).height = 22
  })


  // 表内容
  // 表头样式
  worksheet.addConditionalFormatting({
    ref: 'A11:J11',
    rules: [
      {
        type: 'expression',
        formulae: ['MOD(ROW()+COLUMN(),1)=0'],
        style: { fill: { type: 'pattern', pattern: 'solid', bgColor: { argb: 'FFD9D9D9' }, }, font: { size: 12, color: { argb: 'FF000000' }, vertAlign: "superscript" } },
      }
    ]
  })
  worksheet.getRow(11).height = 42.5

  let arr1 = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J']
  let arr2 = ["11"]

  // 生成26个字母
  const arr3 = Array.from(new Array(26), (ele, index) => { return String.fromCharCode(65 + index); })

  // 商品信息
  let goodsData = []
  // let goodsData = [[`工厂税点`, `产品名中文`,`123`,`123`,`123`,100,3.3,330,330,330]]
  data.map((item, index) => {
    let arr = []
    arr[0] = arr3[index]+ (item.query11?"+"+item.query11:'')
    arr[1] = item.query12
    arr[2] = item.query13
    arr[3] = item.query13
    arr[4] = item.query13
    arr[5] = item.query13
    arr[6] = item.query14
    arr[7] = item.query17
    let a  = numMulti(numMulti(Number(item.query15),Number(item.query14)),100)
    let b  = numMulti(numMulti(Number(item.query17),Number(item.query14)),100)
    item.query16 = accDiv(Math.floor(a) ,100)
    item.query18 = accDiv(Math.floor(b) ,100)
    arr[8] = item.query18
    arr[9] = item.query18
    goodsData.push(arr)
  })
  // 计算表1总价
  let total = 0
  goodsData.map((item, index) => {
    total += Number(item[9])
  })
  total = numAdd(total.toFixed(2),topData.query29)
  console.log(total, '--------------')

  // row:后续都要通过计算row进行 行赋值
  let row = 0
  // 计算商品信息占用多少行
  worksheet.getRow(7).height = 1
  worksheet.getRow(8).height = 1
  worksheet.getRow(9).height = 1
  worksheet.getRow(10).height = 1
  goodsData.map((item, index) => {
    arr2.push(11 + index + 1)
  })
  // 批量加细线、进行垂直居中
  arr1.map((item, index) => {
    arr2.map((v, index) => {
      worksheet.getCell(item + v).border = {
        top: { style: 'thin' },
        left: { style: 'thin' },
        bottom: { style: 'thin' },
        right: { style: 'thin' }
      }
      if (item == 'C' || item == 'D' || item == 'E') {
        worksheet.getCell(item + v).alignment = { vertical: 'middle', horizontal: 'left', wrapText: true };
      } else {
        worksheet.getCell(item + v).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true };
      }
    })
  })
  worksheet.getCell("D10").border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell("E10").border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell("F10").border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell("J10").border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell("K11").border = {
    left: { style: 'thin' },
  }
  // row 当前生成到了那一行  +1之后即是下一行的行数
  row = arr2[arr2.length - 1] + 1
  console.log(row)
  // 表格内行合并、设置表格1行高
  arr2.map((item, index) => {
    worksheet.mergeCells(`C${item}:F${item}`)
    worksheet.mergeCells(`I${item}:J${item}`)
    if (index == 0) {
      worksheet.getRow(item).height = 50
    } else {
      worksheet.getRow(item).height = 310
    }
  })
  worksheet.mergeCells(`I${row}:J${row}`)
  // 计算总金额
  worksheet.getCell('H' + row).value = 'TOTAL'
  worksheet.getCell('I' + row).value =  topData.num08
  worksheet.mergeCells(`A${row}:F${row}`)
  // 总金额加边框、设置高
  worksheet.getCell('H' + row).border = {
    top: { style: 'thin' },
    left: { style: 'thin' },
    bottom: { style: 'thin' },
    right: { style: 'thin' }
  }
  worksheet.getCell('I' + row).border = {
    top: { style: 'thin' },
    left: { style: 'thin' },
    bottom: { style: 'thin' },
    right: { style: 'thin' }
  }
  worksheet.getRow(row).height = 50
  worksheet.getCell(`H${row}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true }
  worksheet.getCell(`I${row}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true }
  // 生成商品信息表
  worksheet.addTable({
    name: 'MyTable1',
    ref: 'A11',
    headerRow: true,
    totalsRow: false,
    style: {},
    columns: [
      { name: '订单ID', filterButton: false },
      {
        name: `ITEM
品名`, filterButton: false
      },
      {
        name: `DESCRIPTION
产品描述`, filterButton: false
      },
      {
        name: `DESCRIPTION
产品描述`, filterButton: false
      },
      {
        name: `DESCRIPTION
产品描述`, filterButton: false,

      },
      {
        name: `DESCRIPTION
产品描述`, filterButton: false,

      },
      {
        name: `QUANTITY
数量`, filterButton: false
      },
      {
        name: `UNIT PRICE (${topData.query25})  
单价`,
        filterButton: false
      },
//       {
//         name: `TOTAL AMOUNT (${topData.query25})
// 总金额`,
//         filterButton: false
//       },
      {
        name: `TOTAL AMOUNT (${topData.query25})
总金额`,
        filterButton: false
      },
      {
        name: `TOTAL AMOUNT (${topData.query25})
总金额`,
        filterButton: false
      },
    ],
    rows: goodsData
  });

  worksheet.getColumn(3).width = 21
  worksheet.getColumn(4).width = 21
  worksheet.getColumn(5).width = 23
  worksheet.getColumn(6).width = 20
  // 开户行、账户、账号
  // row += 1
  // worksheet.getRow(row).height = 60

  // worksheet.getCell(`A${row}`).value = '开户行：'
  // worksheet.getRow(row).height = 22
  // row += 1
  // worksheet.getCell(`A${row}`).value = '账户：'
  // worksheet.getRow(row).height = 22
  // row += 1
  // worksheet.getCell(`A${row}`).value = '账号：'
  // worksheet.getRow(row).height = 22
  // row += 1
  // worksheet.mergeCells(`A${row}:F${row}`)
  // worksheet.getRow(row).height = 150
  worksheet.getCell(`A${row}`).alignment = { vertical: 'middle', horizontal: 'left', wrapText: true }
  worksheet.getCell(`A${row}`).value = topData.query19
  row += 1
  worksheet.getRow(row).height = 20
  // 说明
  row += 1 // 说明开始的行数
  worksheet.mergeCells(`A${row}:J${row}`)
  worksheet.getRow(row).height = 320
  worksheet.getCell(`A${row}`).value =  ``
  // 说明边框
  worksheet.getCell(`A${row}`).font = {size:9}
  arr1.map((item, index) => {
    worksheet.getCell(item + row).alignment = { vertical: 'top', horizontal: 'left', wrapText: true}
    worksheet.getCell(item + row).border = {
      top: { style: 'thin' },
      left: { style: 'thin' },
      bottom: {style:'thin'},
      right: { style: 'thin' }
    }
  })
  row += 1
  worksheet.getCell(`A${row}`).value = '以下无正文'
  // 签字(上)
  row += 4
  worksheet.getCell(`A${row}`).value = '（采购）Purchaser'
  worksheet.getCell(`G${row}`).value = '（供应商）Vendor'
  worksheet.getCell(`A${row}`).font = {
    family: 4,
    size: 10,
    // underline: true,
    bold: true
  };
  worksheet.getCell(`G${row}`).font = {
    family: 4,
    size: 10,
    // underline: true,
    bold: true
  };

  // 签名细线
  worksheet.getCell(`A${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`B${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`C${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`D${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`E${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`G${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`H${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`I${row}`).border = {
    bottom: { style: 'thin' },
  }
  worksheet.getCell(`J${row}`).border = {
    bottom: { style: 'thin' },
  }

  // 签字(下)
  row += 1
  worksheet.getCell(`A${row}`).value = '（签字）Authorized by'
  worksheet.getCell(`G${row}`).value = '（签字）Authorized by'

  // （日期）Date 
  worksheet.getCell(`D${row}`).value = '（日期）Date'
  worksheet.getCell(`I${row}`).value = '（日期）Date'

  // 页脚
  row += 2
  worksheet.mergeCells(`A${row}:J${row}`)
  worksheet.getRow(row).height = 80
  // 对齐、换行
  worksheet.getCell(`A${row}`).alignment = { vertical: 'middle', horizontal: 'center', wrapText: true };
  worksheet.getCell(`A${row}`).value = ``

  workbook.xlsx.writeBuffer().then((buffer) => {
    console.log(3212131111111111122222)
    const blob = new Blob([buffer], { type: EXCEL_TYPE })
    saveAs(blob, `${expertName}.xlsx`)
  })
}