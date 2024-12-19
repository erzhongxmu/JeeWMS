<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="货主名称">
              <a-input placeholder="请输入货主名称" v-model="queryParam.cusName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="波次号">
              <a-input placeholder="请输入波次号" v-model="queryParam.waveId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品编码">
                <a-input placeholder="请输入商品编码" v-model="queryParam.goodsId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="仓位">
                <a-input placeholder="请输入仓位" v-model="queryParam.binId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="一次下架容器">
                <a-input placeholder="请输入一次下架容器" v-model="queryParam.firstRq"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
      <a-button type="primary" icon="download" @click="handleExportXls('wave_to_down')">{{$t('导出')}}</a-button>
      <a-button type="primary" @click="printData" >{{$t('打印')}}</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
      <!-- 高级查询区域 -->
<!--      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
<!--          <a @click="handleEdit(record)">{{$t('编辑')}}</a>-->

<!--          <a-divider type="vertical" />-->
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
<!--              <a-menu-item>-->
<!--                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>{{$t('删除')}}</a>-->
<!--                </a-popconfirm>-->
<!--              </a-menu-item>-->
            </a-menu>
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>
     <j-modal title="数据" :visible="orderShow" @ok="orderOk" :width="900" @cancel="orderCancel">
      <a-table
        ref="table2"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        :columns="columns2"
        :dataSource="dataSource2"
        :pagination="ipagination2"
        class="j-table-force-nowrap"
      ></a-table>
      <template slot="footer">
        <a-button type="primary" v-print="'#printContent'" @click="orderOk()">{{$t('打印')}}</a-button>
      </template>
    </j-modal> 
    <div v-show="false">
      <div class="print" v-if="dataSource2[0]"   id="printContent">
      <div class="title">
        <div class="title_left">
           <barcode v-if="selectionRows[0] && selectionRows[0].waveId" :value="selectionRows[0].waveId"  :options="{ width:'2px',height:40,displayValue:false}"> </barcode>
        </div>
        <div class="title_right">
           <p>打印日期:  {{new Date().Format("yyyy-MM-dd")}}</p> 
           <p>波次单号:  {{selectionRows[0] && selectionRows[0].waveId}}</p> 
        </div>
      </div>
      <div class="collect">
        <p>订单总数: {{dataSource2[0].by1}}</p>
        <p>总拣数量:  {{num}}</p>
      </div>
      <table :border="'1px'" style="width: 100%;margin:0;">
              <thead>
                <tr style="height: 35px;">
                    <th style="height: 35px;">序号</th>
                    <th style="height: 35px;">仓位</th>
                    <th style="height: 35px;">商品条码</th>
                    <th style="height: 35px;">应检数量</th>
                    <th style="height: 35px;">生产日期</th>
                    <th style="height: 35px;">品名</th>
                </tr>
              </thead>
              <tbody>
                <tr  style="height: 35px;" v-for="(item,index) in dataSource2" :key="index">
                  <td style="height: 35px;">{{index+1}}</td>
                  <td style="height: 35px;">{{item.binId}}</td>
                  <td style="height: 35px;">{{item.shpTiaoMa}}</td>
                  <td style="height: 35px;">{{item.baseGoodscount}}</td>
                  <td style="height: 35px;">{{item.proData}}</td>
                  <td style="height: 35px;">{{item.goodsName}}</td>
                </tr>
            </tbody>
          </table> 
    </div>
    </div>
    
    <wave-to-down-modal ref="modalForm" @ok="modalFormOk"></wave-to-down-modal>
  </a-card>
</template>

<script>
  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WaveToDownModal from './modules/WaveToDownModal'
  import { getAction} from '@/api/manage'
  Date.prototype.Format = function (fmt) { // author: meizz
      var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds() // 毫秒
      };
      if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
      for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
          return fmt;
    }
  export default {
    name: 'WaveToDownList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      WaveToDownModal
    },
    data () {
      return {
        num:0,
        columns2: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'left',
          customRender: function(t, r, index) {
            return parseInt(index) + 1
          }
        },
          {
            title:'商品条码',
            align: 'left',
            dataIndex: 'shpTiaoMa'
          },
          {
            title:'商品编码',
            align: 'left',
            dataIndex: 'goodsId'
          },
          {
            title:'商品名称',
            align: 'left',
            dataIndex: 'goodsName'
          },
          {
            title:'数量',
            align: 'left',
            dataIndex: 'baseGoodscount'
          },
          {
            title:'仓位',
            align: 'left',
            dataIndex: 'binId'
          },
          {
            title:'托盘',
            align: 'left',
            dataIndex: 'tinId'
          },
          {
            title:'生产日期',
            align: 'left',
            dataIndex: 'proData'
          },
      ],
      dataSource2: [],
      ipagination2: {
        current: 1,
        pageSize: 5,
        pageSizeOptions: ['5', '10', '20', '30'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      orderShow: false,
        description: 'wave_to_down管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            //fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          },
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align: 'left',
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title:'创建人登录名称',
            align: 'left',
            dataIndex: 'createBy'
          },
          {
            title:'创建人名称',
            align: 'left',
            dataIndex: 'createName'
          },
          {
            title:'货主',
            align: 'left',
            dataIndex: 'cusCode'
          },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusName'
          },
          {
            title:'波次号',
            align: 'left',
            dataIndex: 'waveId'
          },
          {
            title:'商品编码',
            align: 'left',
            dataIndex: 'goodsId'
          },
          {
            title:'商品名称',
            align: 'left',
            dataIndex: 'goodsName'
          },
          {
            title:'单号',
            align: 'left',
            dataIndex: 'imCusCode'
          },
          {
            title:'仓位',
            align: 'left',
            dataIndex: 'binId'
          },
          {
            title:'托盘',
            align: 'left',
            dataIndex: 'tinId'
          },
          {
            title:'生产日期',
            align: 'left',
            dataIndex: 'proData'
          },
          {
            title:'数量',
            align: 'left',
            dataIndex: 'baseGoodscount'
          },
          {
            title:'出库备注',
            align: 'left',
            dataIndex: 'omBeiZhu'
          },
          {
            title:'基本单位',
            align: 'left',
            dataIndex: 'baseUnit'
          },
          {
            title:'一次下架容器',
            align: 'left',
            dataIndex: 'firstRq'
          },
          {
            title:'分拣容器',
            align: 'left',
            dataIndex: 'secondRq'
          },
          {
            title:'批次',
            align: 'left',
            dataIndex: 'goodsBatch'
          },
          // {
          //   title:'by1',
          //   align: 'left',
          //   dataIndex: 'by1'
          // },
          // {
          //   title:'by2',
          //   align: 'left',
          //   dataIndex: 'by2'
          // },
          // {
          //   title:'by3',
          //   align: 'left',
          //   dataIndex: 'by3'
          // },
          // {
          //   title:'by4',
          //   align: 'left',
          //   dataIndex: 'by4'
          // },
          // {
          //   title:'by5',
          //   align: 'left',
          //   dataIndex: 'by5'
          // },
          {
            title:'商品条码',
            align: 'left',
            dataIndex: 'shpTiaoMa'
          },

        ],
        url: {
          list: "/jeewms/waveToDown/list",
          delete: "/jeewms/waveToDown/delete",
          deleteBatch: "/jeewms/waveToDown/deleteBatch",
          exportXlsUrl: "/jeewms/waveToDown/exportXls",
          importExcelUrl: "jeewms/waveToDown/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      printData(){
        this.orderShow = true
        getAction('/jeewms/waveToDown/queryWaveList', {waveId:this.selectionRows[0]?this.selectionRows[0].waveId:'xx'}).then(res=>{
          let num = 0
          res.result.map(item=>{
            num += item.baseGoodscount
          })
          this.num = num
          this.dataSource2 = res.result
        })
      },
      orderOk() {
        console.log(this.selectedRowKeys, this.selectionRows,  this.selectedMainId,)
        // getAction('/jeewms/waveToDown/queryWaveList', ){

        // }
        
      },
      orderCancel(){
       
        this.orderShow = false
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        this.superFieldList = fieldList
        fieldList.push({type:'string',value:'createBy',text:'创建人登录名称',dictCode:''})
        fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
        fieldList.push({type:'string',value:'cusCode',text:'货主',dictCode:''})
        fieldList.push({type:'string',value:'cusName',text:'货主名称',dictCode:''})
        fieldList.push({type:'string',value:'waveId',text:'波次号',dictCode:''})
        fieldList.push({type:'string',value:'goodsId',text:'商品编码',dictCode:''})
        fieldList.push({type:'string',value:'goodsName',text:'商品名称',dictCode:''})
        fieldList.push({type:'string',value:'imCusCode',text:'单号',dictCode:''})
        fieldList.push({type:'string',value:'binId',text:'仓位',dictCode:''})
        fieldList.push({type:'string',value:'tinId',text:'托盘',dictCode:''})
        fieldList.push({type:'string',value:'proData',text:'生产日期',dictCode:''})
        fieldList.push({type:'double',value:'baseGoodscount',text:'数量',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'omBeiZhu',text:'出库备注',dictCode:''})
        fieldList.push({type:'string',value:'baseUnit',text:'基本单位',dictCode:''})
        fieldList.push({type:'string',value:'firstRq',text:'一次下架容器',dictCode:''})
        fieldList.push({type:'string',value:'secondRq',text:'分拣容器',dictCode:''})
        fieldList.push({type:'string',value:'by1',text:'by1',dictCode:''})
        fieldList.push({type:'string',value:'by2',text:'by2',dictCode:''})
        fieldList.push({type:'string',value:'by3',text:'by3',dictCode:''})
        fieldList.push({type:'string',value:'by4',text:'by4',dictCode:''})
        fieldList.push({type:'string',value:'by5',text:'by5',dictCode:''})
        fieldList.push({type:'string',value:'shpTiaoMa',text:'商品条码',dictCode:''})

      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
  .print{
    width: 700px;
    /* background: #00ff00; */
    margin: 0 auto;
    padding: 30px;
    box-sizing: content-box;
  }
  .print .title{
    width: 100%;
    height: 70px;
    display: flex;
  }
  .print .title .title_left{
    flex: 1;
  }
  .print .title .title_right{
    width: 40%;
    display: flex;
    justify-content: center;
    flex-direction: column;
    height: 100%;
  }
  .print .title .title_right p{
    text-align: left;
    line-height: 24px;
    margin: 0;
  }
  .print .collect{
    width: 100%;
    display: flex;
    line-height:50px;
  }
  .print .collect p{
    padding: 0 10px;
    margin: 0;
  }
  th,td{
    text-align: center;
  }
</style>