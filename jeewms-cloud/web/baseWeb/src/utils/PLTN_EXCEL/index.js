
//  WMS 导出模板
import LAY_EXCEL from 'lay-excel';
import { JYD, LLD, ZXD } from './dispose';
import { JYD_STYLE, LLD_STYLE, ZXD_STYLE } from './style';
import { JYD_HB, LLD_HB, ZXD_HB } from './merge';
import { reduceIterate } from 'xe-utils/methods';

/**
 *  检验单
 * @param data 数据
 */
export function INSPECTION_SHEET(data) {
  let arr = JSON.parse(JSON.stringify(data))
  // 处理数据
  let obj = {}
  arr.map((item,index)=>{
    let result = JYD(item,(index+1))
    JYD_STYLE.map((item) => {
      LAY_EXCEL.setExportCellStyle(result, item.title, item.data)
    })
    obj["第"+(index+1)+"页"] = result
  })
  var mergeConf = LAY_EXCEL.makeMergeConfig(JYD_HB); //从开头到结束
  var colConf = LAY_EXCEL.makeColConfig({ 'A': 5, 'B': 80, 'C': 180, 'D': 80, 'E': 70, 'F': 5, 'G': 70, 'H': 70, 'I': 70, 'J': 100, 'K': 70 });
  var rowConf = LAY_EXCEL.makeRowConfig({ 0: 5, 19: 5, 6: 5, 7: 21.75, 22: 30, 23: 21.75, 24: 21.75, 25: 21.75, 26: 21.75, 27: 12, 28: 12, 29: 12, 30: 12, 31: 12 }, 21.75);  //第0行
  LAY_EXCEL.exportExcel(
    obj,
    '检验单.xlsx',
    'xlsx',
    {
      extend: {
        '!merges': mergeConf,
        '!cols': colConf,
        '!rows': rowConf
      }
    }
  )
}


/**
 *  包装领料单
 * @param data 数据
 */
export function STOCK_REQUISITION(data) {
  let arr = JSON.parse(JSON.stringify(data))
  // 处理数据
  let result = LLD(arr)
  //设置样式的函数
  LLD_STYLE.map((item) => {
    LAY_EXCEL.setExportCellStyle(result, item.title, item.data)
  })
  var mergeConf = LAY_EXCEL.makeMergeConfig(LLD_HB); //从开头到结束
  var colConf = LAY_EXCEL.makeColConfig({ 'A': 15, 'B': 100, 'C': 100, 'D': 80, 'E': 60, 'F': 60, 'G': 60, 'H': 60, 'I': 60, 'J': 60, 'K': 20, 'L': 80, 'M': 80, 'N': 80, 'O': 30, 'P': 30, 'Q': 15 });
  var rowConf = LAY_EXCEL.makeRowConfig({ 0: 15, 17: 5, 6: 18, 24: 5, 10: 50, 19: 25, 20: 25, 21: 25, 22: 25, 23: 25, 30: 30, 31: 30, 32: 15 }, 20);  //第0行
  LAY_EXCEL.exportExcel(
    { '第一页': result, },
    '包装领料单.xlsx',
    'xlsx',
    {
      extend:
      {
        '第一页': {
          // 以下配置仅 sheet1 有效
          '!merges': mergeConf,
          '!cols': colConf,
          '!rows': rowConf
        }
      }
    }
  )
}

function sort(s,t){ // 排序
  var a = 'T00001'
  var b = 'T00001'
  if(s['xianghao']){
    a = s['xianghao'].toLowerCase();
  }
  if(t['xianghao']){
    b = t['xianghao'].toLowerCase();
  }
  if(a.length===2){
    a=a.slice(0, a.length - 1) + '0' + a.slice(-1)
  }
  if(b.length===2){
    b = b.slice(0, b.length - 1) + '0' + b.slice(-1)
  }
  if (a < b) return -1;
  if (a > b) return 1;
  return 0;
}

/**
 *  装箱单
 * @param data 数据
 */
export function PACKING_LIST(data,u8Djcode1) {
  let arr1 = JSON.parse(JSON.stringify(data)) // 深拷贝原数据
  let arr2 = []
  arr1.map((item,index)=>{  // 处理箱号
    let firstRq =  item.firstRq?item.firstRq.split(',') : ['暂无'] // 将返回的箱号字符串处理成数组
    firstRq.sort()
    let str =  ''
    if(firstRq.length == 1){  // 如果只有一个箱号，只显示一个
      str = u8Djcode1 + ' ' + firstRq[0]
    }else{ // 如果有多个箱号，取首尾箱号
      str = u8Djcode1 + ' ' + firstRq[0] + '~' + firstRq[firstRq.length - 1]
    }
    item.xianghao = str // 将处理好的箱号数据赋值给存储箱号的字段
  })
  arr1.sort(sort)
  let obj = { // 存储要计算总数的字段
		baseGoodscount:0,
		totalqtypercarton:0,
		totalvol:0,
		totalweight:0,
  }
  let arr = JSON.parse(JSON.stringify(arr1)) 
  arr.map(item =>{ // 给数据对象添加特定字段，处理计算总数逻辑
    item.sell = 'P-ASIA'
    item.state = '未贴箱号'
    item.totalqtypercarton = (Number(item.baseGoodscount) || 0)/(Number(item.qtypercarton) || 0)
    obj.baseGoodscount += (Number(item.baseGoodscount)  || 0)
    obj.totalqtypercarton += (Number(item.totalqtypercarton) || 0)
    obj.totalvol += (Number(item.totalvol) || 0)
    obj.totalweight += (Number(item.totalweight) || 0)
  })
  arr.push(obj) // 在原数据数组后面追加计算了总数的数据对象
  arr.unshift({ // 在原数据数组第一个元素添加一份表头数据（每个表头字段要对应显示的中文）
    omBeiZhu: 'NOTE\r\n备注',
    state: 'STICKER STATUS\r\n箱号状态',
		xianghao:'CARTON NO.\r\n箱号',
		sell:'COMPANY SELLER\r\n销售公司',
		cusName:'Customer\r\n客户名',
		// shipdepcode:'跟单员',
		shipdepname:'业务员',
		goodsId:'SKU',
    goodsName:'ITEM NAME EN\r\n英文品名',
		u8ReturnCode:'Sales inv\r\n销售发票号',
		shippingmark:'SHIPPING MARK\r\n箱唛',
		baseGoodscount:'TOTAL SHIP QTY\r\n出货数量',
		qtypercarton:'Qty per carton\r\n每箱数量',
		totalqtypercarton:'Total qty of carton\r\n总箱数',
		createBy:'BUYER\r\n采购名',
		createName:'跟单',
		goodsUnitPrice:'Unit price (RMB)\r\n单价',
		contractlno:'QB#\r\n采购单号',
		astreanum:'ASTRE  INV#\r\n内部发票号',
		goodsjianchen:'ITEM NAME\r\n品名简称',
		goodsguige:'Product Dimension\r\n产品尺寸',
		goodsTypeName:'Material CN\r\n中文材质',
		goodsTypeEnname:'Material EN\r\n英文材质',
		shpYanEnse:'Color EN\r\n颜色英文',
		ctnsizel:'CTN size L\r\n纸箱长',
		ctnsizew:'CTN size W\r\n纸箱宽',
		ctnsizeh:'CTN size H\r\n纸箱高',
		weightctn:'Weight/Ctn KGS\r\n重量/箱',
		wolctn:'Vol/Ctn\r\n体积/箱',
		totalvol:'Total VOL\r\n总体积',
		totalweight:'Total  Weight\r\n总重量',
		taxrefund:'是否退税',
		billingproductname:'开票品名 （财务需复核）',
		billingproductunit:'开票单位 （财务需复核）',
		// shiporgcode:'税点',
		salespricewvat:'SALES PRICE W/ VAT\r\n卖价-报关价格',
		salespricecur:'SALES PRICE CURRENCY\r\n报关价格币种',
		totalamount:'TOTAL AMOUNT\r\n总金额',
		ladingname:'NAME ON BILL OF LADING\r\n提单品名',
		logo:'品牌LOGO',
		hscode:'HS CODE\r\n海关商品编码',
		usagess:'USAGE\r\n用途',
		declarationelements:'申报要素',
  })
  console.log(arr,888888888888888888)
  //设置样式的函数
  let STYLE = ZXD_STYLE(arr.length)
  STYLE.map((item) => {
    LAY_EXCEL.setExportCellStyle(arr, item.title, item.data)
  })
  let result = LAY_EXCEL.filterExportData(arr, ZXD(arr))
  var mergeConf = LAY_EXCEL.makeMergeConfig(ZXD_HB(arr)); 
  var colConf = LAY_EXCEL.makeColConfig({
    'A': 80, 'B': 200, 'C': 80, 'D': 80, 'E': 80, 'F': 180, 'G': 80, 'H': 80, 'I': 80, 'J': 80, 'K': 80, 'L': 80, 'M': 80, 'N': 80, 'O': 80, 'P': 80, 'Q': 80, 'R': 80, 'S': 80, 'T': 80, 'U': 80, 'V': 80, 'W': 80, 'X': 80, 'Y': 80, 'Z': 80, 'AA': 80, 'AA': 80, 'AB': 80, 'AC': 80, 'AD': 80, 'AE': 80, 'AF': 80, 'AG': 80, 'AH': 80, 'AI': 80, 'AJ': 80, 'AK': 80, 'AL': 80, 'AM': 80,'AN': 80, 'AO': 80, 'AP': 200,
  });
  var rowConf = LAY_EXCEL.makeRowConfig({ 0: 80,1: 40,2: 40,3: 40,4: 40,5: 40,6: 40,7: 40,8: 40,9: 40,10: 40,11: 40,12: 40,13: 40,14: 40,15: 40,16: 40,17: 40,18: 40,19: 40,20: 40,21: 40,22: 40,23: 40,24: 40,25: 40,26: 40,27: 40,28: 40,29: 40,30: 40 }, 40);  //第0行
  LAY_EXCEL.exportExcel(
    { '第一页': result, },
    '装箱单.xlsx',
    'xlsx',
    {
      extend:
      {
        '第一页': {
          // 以下配置仅 sheet1 有效
          '!merges': mergeConf,
          '!cols': colConf,
          '!rows': rowConf
        }
      }
    }
  )
}



