<template>
  <div class="AutoPrint">
    <!-- 入库标签 -->
    <!-- <a-button type="primary" @click="btnClickPrint" >{{$t('自动入库打印')}}</a-button>
    <a-button type="primary" @click="btnClickPrint2" >{{$t('自动箱唛打印')}}</a-button>
    <a-button type="primary" @click="btnClickPrint3" >{{$t('自动入库打印')}}</a-button> -->
    <!-- 入库 -->
    <!-- <div id="printContent" class="printContent_cl">
        <div class="printitem">
            <p>- SKU:RCMD#0186</p>
            <p>- MO MILANO(ASIA)MO MILANO(ASIA)MO MILANO</p>
            <p>- SLIPPERS KIDSMO MILANO(ASIA)MO</p>
            <p>- QTY: 20</p>
            <p>- QB: A82211</p>
            <p>- 2022-06-09</p>
            <img id="barcode"/>
        </div>
    </div> -->
    <!-- 箱唛 -->
    <!-- <div id="printContent2" class="printContent_cl2">
      <p>INTERCONTINENTAL KAOHSIUNG</p>
      <p>SKU:OBC#0260</p>
      <p>COAT HANGER</p>
      <p>- QTY: 20</p>
      <p>BA238213</p>
      <p>WMS</p>
    </div> -->
    <!-- 出库 -->
    <!-- <div id="printContent3" class="printContent_cl3">
      <p>SHIP#1234</p>
      <p>T00001</p>
      <p>1/10</p>
    </div> -->
  </div>
</template>

<script>
// import { getLodop } from '@/utils/LodopFuncs' //导入模块
// export default {
//   name: 'AutoPrint',
//   components: {},
//   data() {return {}},
//   mounted(){
//       JsBarcode("#barcode","123456789012",{
//            width:'2px',height:50,displayValue:true,fontSize:12
//       })
//   },
//   methods: {
//     btnClickPrint: function() {
//       console.log(document.getElementById('printContent').innerHTML)
//       var self = this
//       let LODOP = getLodop() //主要代码
//       LODOP.PRINT_INIT()
//       LODOP.SET_PRINT_PAGESIZE(1, '2000mm', '200mm')
//       LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1)
//       var strBodyStyle =
//         `<style>p{
//         margin: 0;
//         line-height: 20px;
//     }
//     .printitem{
//         width: 500px;
//         height: 500px;}
//         .printitem p{
//             font-size: 45px;
//             font-weight: 600;
//             line-height: 65px;
//         }
//         .printitem img{
//             width: 300px;
//             height: 80px;
//         }</style>`
//       var strFormHtml = strBodyStyle + '<body>' + document.getElementById('printContent').innerHTML + '</body>'
//       LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', strFormHtml) //以上为声明打印参数，具体请参照官网示例 //以下是成功加入打印队列之后的回调，如果不用刻意不看，直接执行LODOP.PRINT()
//       LODOP.SET_PRINT_MODE('CATCH_PRINT_STATUS', true)

//       if (LODOP.CVERSION) {
//         //判断c_lodop是否存在，安装了c-lodop就会存在
//         LODOP.On_Return = function(TaskID, Value) {
//           // console.log('TaskID:' + TaskID)
//           console.log('Value:' + Value) //job代码
//           self.jobCode = Value
//           if (!!Value) {
//             //如果成功，改变打印次数
//             ChangePrintCount(self.newDataAuto).then(res => {
//               if (res.status !== 1) {
//                 self.$notify.error({
//                   title: '错误',
//                   message: res.message
//                 })
//               }
//             })
//             return
//           }
//         }
//         LODOP.PRINT() //这是执行打印（无预览）
//         //        LODOP.PREVIEW()   // 这是有预览打印
//         self.dialogVisible = false
//         return
//       } else {
//         console.log('c-lodop出错了')
//       }
//     },
//     btnClickPrint2: function() {
//       var self = this
//       let LODOP = getLodop() //主要代码
//       LODOP.PRINT_INIT()
//       LODOP.SET_PRINT_PAGESIZE(1, '94mm', '120mm')
//       LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1)
//       var strBodyStyle =
//         '<style>table, td,th {border: 1 solid #000000;border-collapse: collapse};td,th {height: 40px;line-height: 40px}</style>'
//       var strFormHtml = strBodyStyle + '<body>' + document.getElementById('printContent').innerHTML + '</body>'
//       LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', strFormHtml) //以上为声明打印参数，具体请参照官网示例 //以下是成功加入打印队列之后的回调，如果不用刻意不看，直接执行LODOP.PRINT()
//       LODOP.SET_PRINT_MODE('CATCH_PRINT_STATUS', true)

//       if (LODOP.CVERSION) {
//         //判断c_lodop是否存在，安装了c-lodop就会存在
//         LODOP.On_Return = function(TaskID, Value) {
//           // console.log('TaskID:' + TaskID)
//           console.log('Value:' + Value) //job代码
//           self.jobCode = Value
//           if (!!Value) {
//             //如果成功，改变打印次数
//             ChangePrintCount(self.newDataAuto).then(res => {
//               if (res.status !== 1) {
//                 self.$notify.error({
//                   title: '错误',
//                   message: res.message
//                 })
//               }
//             })
//             return
//           }
//         }
//         LODOP.PRINT() //这是执行打印（无预览）
//         //        LODOP.PREVIEW()   // 这是有预览打印
//         self.dialogVisible = false
//         return
//       } else {
//         console.log('c-lodop出错了')
//       }
//     },
//     btnClickPrint3: function() {
//       var self = this
//       let LODOP = getLodop() //主要代码
//       LODOP.PRINT_INIT()
//       LODOP.SET_PRINT_PAGESIZE(1, '94mm', '120mm')
//       LODOP.SET_PRINT_STYLEA(0, 'ItemType', 1)
//       var strBodyStyle =
//         '<style>table, td,th {border: 1 solid #000000;border-collapse: collapse};td,th {height: 40px;line-height: 40px}</style>'
//       var strFormHtml = strBodyStyle + '<body>' + document.getElementById('printContent').innerHTML + '</body>'
//       LODOP.ADD_PRINT_HTM(0, 0, '100%', '100%', strFormHtml) //以上为声明打印参数，具体请参照官网示例 //以下是成功加入打印队列之后的回调，如果不用刻意不看，直接执行LODOP.PRINT()
//       LODOP.SET_PRINT_MODE('CATCH_PRINT_STATUS', true)

//       if (LODOP.CVERSION) {
//         //判断c_lodop是否存在，安装了c-lodop就会存在
//         LODOP.On_Return = function(TaskID, Value) {
//           // console.log('TaskID:' + TaskID)
//           console.log('Value:' + Value) //job代码
//           self.jobCode = Value
//           if (!!Value) {
//             //如果成功，改变打印次数
//             ChangePrintCount(self.newDataAuto).then(res => {
//               if (res.status !== 1) {
//                 self.$notify.error({
//                   title: '错误',
//                   message: res.message
//                 })
//               }
//             })
//             return
//           }
//         }
//         LODOP.PRINT() //这是执行打印（无预览）
//         //        LODOP.PREVIEW()   // 这是有预览打印
//         self.dialogVisible = false
//         return
//       } else {
//         console.log('c-lodop出错了')
//       }
//     }
//   }
// }
</script>

<style lang="less">
.AutoPrint{
    color: #000!important;
    background: turquoise;
    .printContent_cl2{
        margin: 5px 0;
    }
    p{
        margin: 0;
        line-height: 20px;
    }
    .printitem{
        width: 500px;
        height: 500px;
        p{
            font-size: 30px;
            font-weight: 600;
            line-height: 50px;
        }
        img{
            width: 200px;
            height: 80px;
        }
    }
}
</style>