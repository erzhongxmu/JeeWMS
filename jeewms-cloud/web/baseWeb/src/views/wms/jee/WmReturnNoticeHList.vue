<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="10" :lg="11" :md="12" :sm="24">
            <a-form-item label="预计到货时间">
              <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.imData_begin"></j-date>
              <span class="query-group-split-cust"></span>
              <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.imData_end"></j-date>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="订单号">
              <a-input placeholder="请输入订单号" v-model="queryParam.imCusCode"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="司机">
                <a-input placeholder="请输入司机" v-model="queryParam.imCarDri"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="司机电话">
                <a-input placeholder="请输入司机电话" v-model="queryParam.imCarMobile"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="车号">
                <a-input placeholder="请输入车号" v-model="queryParam.imCarNo"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="月台">
                <j-dict-select-tag placeholder="请选择月台" v-model="queryParam.platformCode" dictCode="ba_platform,platform_name,platform_code"/>
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'returnNotice:add'">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('退货预约')" v-has="'returnNotice:export'">{{$t('导出')}}</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
      <a-button key="1" @click="batchDel" v-has="'returnNotice:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
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
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :rowClassName="setRowClsaa">

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
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'returnNotice:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'returnNotice:check'"/>
          <a @click="handleEdit(record)" v-has="'returnNotice:update'">{{$t('编辑')}}</a>

          <a-divider type="vertical"  v-has="'returnNotice:update'"/>
           <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'returnNotice:delete'">
                  <a>{{$t('删除')}}</a>
           </a-popconfirm>
          <a-divider type="vertical"  v-has="'returnNotice:delete'"/>
          <a @click="exportBill('ys',record.id)" v-has="'returnNotice:exportAccept'">导出验收单</a>
          <a-divider type="vertical" />
          <a @click="exportBill('rk',record.id)" v-has="'returnNotice:exportIn'">导出入库</a>
          <a-divider type="vertical"  v-has="'returnNotice:exportIn'"/>
           <a @click="changeImSta(record.id)" v-has="'returnNotice:done'">{{$t('完成')}}</a>
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                -->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <j-modal
      title="入库单打印"
      width="1000px"
      :visible="visible"
      :footer = "null"
      @cancel="canclePrintPutIn">
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printPutIn'"><a-icon type="printer" style="font-family: normal"/>{{$t('打印')}}</i>
      <table id="printPutIn" style="margin: 0 auto">
        <tbody>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="9" class="xl68" width="168" style="width:126pt">公司地址：{{printData.sysCompanyCode?printData.sysCompanyCode:''}}</td>
        </tr>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="4" height="40" class="xl67" style="height:30.0pt">到货日期：{{printData.imData?printData.imData:''}}</td>
          <td colspan="3" class="xl68" width="168" style="width:126pt">预约单号：{{printData.noticeId?printData.noticeId:''}}</td>
          <td colspan="2" style="mso-ignore:colspan"></td>
        </tr>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="4" height="40" class="xl68" width="242" style="height:30.0pt;width:182pt">货主名称：{{printData.cusCode_dictText?printData.cusCode_dictText:''}}</td>
          <td colspan="3" height="40" class="xl68" width="242" style="height:30.0pt;width:182pt">供应商：{{printData.supName?printData.supName:''}}</td>
          <td></td>
        </tr>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="4" class="xl68" width="337" style="width:252pt">备注：{{printData.imBeizhu?printData.imBeizhu:''}}</td>
          <td colspan="3">货主采购单号：{{printData.imCusCode?printData.imCusCode:''}}</td>
          <td></td>
        </tr>

        <tr height="33" style="mso-height-source:userset;height:25.05pt">
          <td class="xl65" style="border:1.0pt solid black;text-align: center">序号</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">商品编码</td>
          <td height="33" class="xl65" style="height:25.05pt;border:1.0pt solid black;text-align: center">商品</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">生产日期</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">收货温度</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">单位</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">数量</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">重量</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">容积</td>
          <td></td>
        </tr>




        <tr height="33" style="mso-height-source:userset;height:50px" v-for="(item,key) in printDataList" :key="key">
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{key+1}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center;word-break:break-all;"><span style="word-break:break-all;width: auto;font-size: 14pt">{{item.goodsCode}}</span></td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsName}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsPrdData}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCode}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsUnit}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCount}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsWeight}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsFvol}}</td>
          <td></td>
        </tr>



        <tr height="20" style="height:25.0pt">
          <td height="20" class="xl66" colspan="8" style="height:15.0pt;mso-ignore:colspan;text-align: justify">制单人：<span style="mso-spacerun:yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span>合计重量：{{totalWeight}}<span style="mso-spacerun:yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span>合计数量：{{totalNum}}<span style="mso-spacerun:yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span>货主签字（盖章）</td>
        </tr>

        </tbody>
      </table>
    </j-modal>

    <j-modal
      title="入库通知单打印"
      width="1000px"
      :visible="visible1"
      :footer = "null"
      @cancel="canclePrintOrder">
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printOrder'"><a-icon type="printer" style="font-family: normal"/>{{$t('打印')}}</i>
      <div style="font-size: 18pt;text-align: center">仓储管理系统入库通知单</div>
      <table id="printOrder" style="margin: 0 auto">
        <tbody>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="4"></td>
          <td colspan="4"></td>
          <td rowspan="3" class="xl69">
            <img :src="'http://47.113.229.131:9999/jeewms/qrcode/get?q='+printData.id" style="width:80px;height:80px;vertical-align:right">
          </td>
        </tr>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="4" height="40" class="xl68" width="242" style="height:30.0pt;width:182pt">货主：{{printData.cusCode_dictText?printData.cusCode_dictText:''}}</td>
          <td colspan="4" height="40" class="xl68" width="242" style="height:30.0pt;width:182pt">供应商：{{printData.supName?printData.supName:''}}</td>
          <td></td>
        </tr>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="4" class="xl68" width="168" style="width:126pt">单号:{{printData.noticeId?printData.noticeId:''}}</td>
          <td colspan="2" style="mso-ignore:colspan"></td>
        </tr>
        <tr height="40" style="mso-height-source:userset;height:30.0pt">
          <td colspan="4" class="xl68" width="337" style="width:252pt">备注：{{printData.imBeizhu?printData.imBeizhu:''}}</td>
          <td colspan="4">WMS单号：{{printData.noticeId?printData.noticeId:''}}</td>
          <td></td>
        </tr>




        <tr height="33" style="mso-height-source:userset;height:25.05pt">
          <td class="xl65" style="border:1.0pt solid black;text-align: center">商品编码</td>

          <td height="33" class="xl65" style="height:25.05pt;border:1.0pt solid black;text-align: center">商品</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">规格</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">单位</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">生产日期</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">保质期</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">数量</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">体积</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">重量</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">库位</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">二维码</td>

          <td></td>
        </tr>




        <tr height="33" style="mso-height-source:userset;height:50px" v-for="(item,key) in printDataList" :key="key">
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCode}}</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center;word-break:break-all;"><span style="word-break:break-all;width: auto;font-size: 14pt">{{item.goodsName}}</span></td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.shpGuiGe}}</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsUnit}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsPrdData}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.bzhiQi}}</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsCount}}</td>
          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsFvol}}</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsWeight}}</td>

          <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.binPlan}}</td>

          <td class="xl65" align="center" valign="middle" style="border:1.0pt solid black"><img :src="'http://47.113.229.131:9999/jeewms/qrcode/get?q='+item.id" alt="S11-5402010" style="width:40px;height:40px;vertical-align:middle;">　</td>
          <td></td>
        </tr>



        <tr height="20" style="height:25.0pt">
          <td height="20" class="xl66" colspan="8" style="height:15.0pt;mso-ignore:colspan;text-align: justify">主管：<span style="mso-spacerun:yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span>制单： <span style="mso-spacerun:yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span>仓管：<span style="mso-spacerun:yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </span>叉车：<span style="mso-spacerun:yes"></span></td>
          <td></td>
        </tr>

        </tbody>
      </table>
    </j-modal>


    <WmReturnNoticeH-modal ref="modalForm" @ok="modalFormOk"></WmReturnNoticeH-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmReturnNoticeHModal from './modules/WmReturnNoticeHModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import JDate from '@/components/jeecg/JDate.vue'
  import { getAction } from '@/api/manage'
  import { postAction } from '@/api/manage'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import '@/assets/less/TableExpand.less'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'

  export default {
    name: "WmImNoticeHList",
    mixins:[JeecgListMixin,commonTableRowClass,listCheckMixin],
    components: {
      JDictSelectTag,
      JDate,
      WmReturnNoticeHModal
    },
    data () {
      return {
        description: 'wm_im_notice_h管理页面',
        visible: false,
        visible1: false,
        printData: {
          imData: '',
          noticeId: '',
          sysCompanyCode: '',
          cusCode_dictText: '',
          supName: '',
          imBeizhu: '',
          imCusCode: '',
          createTime: '',
        },
        printDataList: [],
        totalWeight: 0,
        totalNum: 0,
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            width: 400,
            scopedSlots: { customRender: 'action' },
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
          //{
          //   title:'创建人名称',
          //   align: 'left',
          //   dataIndex: 'createBy'
          // },
          // {
          //   title:'创建日期',
          //   align: 'left',
          //   dataIndex: 'createTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          {
            title:'预计到货时间',
            align: 'left',
            dataIndex: 'imData',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'订单号',
            align: 'left',
            dataIndex: 'imCusCode'
          },
          {
            title:'司机',
            align: 'left',
            dataIndex: 'imCarDri'
          },
          {
            title:'司机电话',
            align: 'left',
            dataIndex: 'imCarMobile'
          },
          // {
          //   title:'车号',
          //   align: 'left',
          //   dataIndex: 'imCarNo'
          // },
          // {
          //   title:'订单类型',
          //   align: 'left',
          //   dataIndex: 'orderTypeCode_dictText'
          // },
          // {
          //   title:'月台',
          //   align: 'left',
          //   dataIndex: 'platformCode_dictText'
          // },
          // {
          //   title:'备注',
          //   align: 'left',
          //   dataIndex: 'imBeizhu'
          // },
          // {
          //   title:'单据状态',
          //   align: 'left',
          //   dataIndex: 'imSta'
          // },
          {
            title:'进货通知单号',
            align: 'left',
            dataIndex: 'noticeId'
          }//,
          // {
          //   title:'附件',
          //   align: 'left',
          //   dataIndex: 'fuJian',
          //   scopedSlots: {customRender: 'fileSlot'}
          // },
          // {
          //   title:'货主编码',
          //   align: 'left',
          //   dataIndex: 'supCode_dictText'
          // },
          // {
          //   title:'货主名称',
          //   align: 'left',
          //   dataIndex: 'supName'
          // }
        ],
        url: {
          list: "/jeewms/wmImNoticeH/thList",
          delete: "/jeewms/wmImNoticeH/delete",
          deleteBatch: "/jeewms/wmImNoticeH/deleteBatch",
          exportXlsUrl: "/jeewms/wmImNoticeH/exportXlsTwo",
          importExcelUrl: "jeewms/wmImNoticeH/importExcel",
          complete: "/jeewms/wmImNoticeH/complete",
          listWmImNoticeIByMainId: "/jeewms/wmImNoticeH/listWmImNoticeIByMainId",
          doPrintRkd: "/jeewms/wmImNoticeH/doPrintRkd",//导出入库单
          doPrintYsd: "/jeewms/wmImNoticeH/doPrintYsd",//导出验收单
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      },
      loadData(arg) {
        if(!this.url.list){
          this.$message.error("请设置url.list属性!")
          return
        }
        //加载数据 若传入参数1则加载第一页的内容
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        this.onClearSelected()
        var params = this.getQueryParams();//查询条件
        this.loading = true;
        getAction(this.url.list, params).then((res) => {
          if (res.success) {
            this.dataSource = res.result.records;
            this.ipagination.total = res.result.total;
          }
          if(res.code===510){
            this.$message.warning(this.$t('操作失败'))
          }
          this.loading = false;
        })
      },
      changeImSta(id) {
        postAction(this.url.complete+'?id='+id).then((res) => {
          if (res.success) {
            this.loadData()
            this.$message.success('设置成功')
          }
          if(res.code===510){
            this.$message.warning(this.$t('操作失败'))
          }
          this.loading = false;
        })
      },
      //根据入库单id获取单据商品列表
      getListWmImNoticeIByMainId(id) {
        var params = {
          imNoticeId: id
        }
        getAction(this.url.listWmImNoticeIByMainId, params).then((res) => {
          if (res.success) {
            this.printDataList = res.result.records;
            this.printDataList.forEach(item => {
              if(item.goodsWeight) {
                this.totalWeight += parseInt(item.goodsWeight)
              }
              if(item.goodsCount) {
                this.totalNum += parseInt(item.goodsCount)
              }
            })
          }
        })
      },
      //打开打印入库单
      goPrintPutIn(record) {
        this.printData = record;
        this.getListWmImNoticeIByMainId(record.noticeId);
        this.visible = true;
      },
      //关闭打印入库单
      canclePrintPutIn() {
        this.printData = {
          imData: '',
          noticeId: '',
          sysCompanyCode: '',
          cusCode_dictText: '',
          supName: '',
          imBeizhu: '',
          imCusCode: '',
          createTime: '',
        };
        this.printDataList = [];
        this.totalWeight = 0;
        this.totalNum = 0;
        this.visible = false
      },

      //打开打印入库通知单
      goPrintOrder(record) {
        this.printData = record;
        this.getListWmImNoticeIByMainId(record.noticeId);
        console.log(this.printDataList)
        this.visible1 = true
      },

      //关闭打印入库通知单单
      canclePrintOrder() {
        this.printData = {
          imData: '',
          noticeId: '',
          sysCompanyCode: '',
          cusCode_dictText: '',
          supName: '',
          imBeizhu: '',
          imCusCode: '',
          createTime: '',
        };
        this.visible1 = false
      },
      exportBill(type,id) {
        if(type == 'rk') {
          //return `${window._CONFIG['domianURL']}/${this.url.downReceiveExcel+'?id='+id}`;
          window.open(window._CONFIG['domianURL']+this.url.doPrintRkd+'?id='+id);
        } else if (type == 'ys') {
          window.open(window._CONFIG['domianURL']+this.url.doPrintYsd+'?id='+id);
        } else {

        }
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>