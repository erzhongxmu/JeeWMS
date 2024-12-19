<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.noticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('库位编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.binId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('托盘')">
              <a-input :placeholder="$t('请输入')"  v-model="queryParam.tinId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('商品编码')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('商品名称')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="创建日期">-->
<!--                <j-date placeholder="请选择创建日期" v-model="queryParam.createTime"/>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button  @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
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
      <a-button icon="download" @click="handleExportXls($t('盘点'))" v-has="'sttInGoods:export'">{{$t('导出')}}</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
      <a-button key="1" @click="batchDel" v-has="'sttInGoods:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
      <!-- <a-button type="primary" icon="upload" @click="syncDbd()">提交盘点单</a-button> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>
      </a-dropdown> -->
      <!-- <a-button @click="handleDiff" type="primary" icon="plus">差异过账</a-button> -->
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
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
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
          <a @click="handleCheck(record)" v-has="'sttInGoods:check'">{{$t('查看')}}</a>
          
          <a-divider type="vertical"  v-has="'sttInGoods:update'"/>
          <a @click="handleEdit(record)" v-has="'sttInGoods:update'">{{$t('编辑')}}</a>

          <a-divider type="vertical"  v-has="'sttInGoods:Diff'"  v-if="record.sttSta=='已完成'"/>
          <a @click="handleDiff(record)" v-has="'sttInGoods:Diff'" v-if="record.sttSta=='已完成'">{{$t('差异过账')}}</a>

          <a-divider type="vertical" />
          <a @click="goPrintOrder(record)">{{$t('打印盘点单')}}</a>

          <a-divider type="vertical"  v-has="'sttInGoods:update'"/>
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'sttInGoods:delete'">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>
    <a-modal
      :title="$t('打印盘点单')"
      width="1000px"
      :visible="visible1"
      :footer="null"
      @cancel="canclePrintOrder"
      >
      <i class="l-btn-left" style="cursor: pointer" v-print="'#printOrder'">
        <a-icon type="printer" style="font-family: normal" />{{$t('打印')}} 
      </i>
      <div style="font-size: 18pt;text-align: center">{{$t('仓储管理系统盘点单')}} </div>
      <table id="printOrder" style="margin: 0 auto">
        <tbody>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td colspan="5"></td>
            <td colspan="5"></td>
            <td rowspan="3" class="xl69">
              <vue-qr :text="printData.noticeId" :size="80" :margin="0"></vue-qr>
            </td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              height="40"
              class="xl68"
              width="242"
              style="height:30.0pt;width:182pt"
            >{{$t('创建日期')}} ：{{ printDataList[0] && printDataList[0].createTime?printDataList[0].createTime:''}}</td>
            <td></td>
          </tr>
          <tr height="40" style="mso-height-source:userset;height:30.0pt">
            <td
              colspan="4"
              class="xl68"
              width="337"
              style="width:252pt"
            >{{$t('创建人')}} ：{{printData.createBy?printData.createBy:''}}</td>
            <td colspan="4">{{$t('单号')}} ：{{printData.noticeId?printData.noticeId:''}}</td>
            <td></td>
          </tr>

          <tr height="33" style="mso-height-source:userset;height:25.05pt">
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('商品编码')}} </td>
            <td
              class="xl65"
              height="33"
              style="height:25.05pt;border:1.0pt solid black;text-align: center"
            >{{$t('商品')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('单位')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('库位编码')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('托盘')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('数量')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('盘点数量')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('差异数量')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('生产日期')}} </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('批次')}} </td>
            <!-- <td class="xl65" style="border:1.0pt solid black;text-align: center">{{$t('二维码')}} </td> -->
            <td></td>
          </tr>

          <tr
            height="33"
            style="mso-height-source:userset;height:50px"
            v-for="(item,key) in printDataList"
            :key="key"
          >
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsId}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center;word-break:break-all;"
            >
              <span style="word-break:break-all;width: auto;font-size: 14pt">{{item.goodsName}}</span>
            </td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsUnit}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.binId}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.tinId}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsQua}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.sttQua}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.diffQua}}</td>
            <td
              class="xl65"
              style="border:1.0pt solid black;text-align: center"
            >{{item.goodsProData}}</td>
            <td class="xl65" style="border:1.0pt solid black;text-align: center">{{item.goodsBatch}}</td>
            <!-- <td class="xl65" align="center" valign="middle" style="border:1.0pt solid black">
              <vue-qr
                :text="item.tinId"
                :margin="0"
                colorDark="#000"
                colorLight="#fff"
                :size="40"
              ></vue-qr>
            </td> -->
            <td></td>
          </tr>
        </tbody>
      </table>
    </a-modal>
    <wmSttInGoods-modal ref="modalForm" @ok="modalFormOk"></wmSttInGoods-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmSttInGoodsModal from './modules/WmSttInGoodsModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import ExportSpin from '@/components/ExportSpin/ExportSpin'
  import JDate from '@comp/jeecg/JDate'
  import { getAction } from '@/api/manage'
  import vueQr from 'vue-qr'

  export default {
    name: "WmSttInGoodsList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      vueQr,
      JDate,
      WmSttInGoodsModal,
      ExportSpin
    },
    data () {
      return {
        visible1:false,
        description: 'wm_stt_in_goods管理页面',
        printData:{},
        goodsQua:0,
        sttQua:0,
        printDataList:[],
        // 表头
        columns: [
          {
            title:this.$t('商品编码'),
            align: 'left',
            dataIndex: 'goodsId',
          sorter: true
          },
          {
            title:this.$t('商品名称'),
            align: 'left',
            dataIndex: 'goodsName',
          sorter: true
          },
          {
            title: this.$t('货主名称'),
            align: 'left',
            dataIndex: 'cusName',
          sorter: true
          },
          {
            title:this.$t('数量'),
            align: 'left',
            dataIndex: 'goodsQua',
          sorter: true
          },
          {
            title:this.$t('单位'),
            align: 'left',
            dataIndex: 'goodsUnit',
          sorter: true
          },
          {
            title:this.$t('盘点数量'),
            align: 'left',
            dataIndex: 'sttQua',
          sorter: true
          },
          {
            title:this.$t('差异数量'),
            align: 'left',
            dataIndex: 'diffQua',
          sorter: true
          },
          {
            title:this.$t('库位编码'),
            align: 'left',
            dataIndex: 'binId',
          sorter: true
          },
          {
            title:this.$t('托盘'),
            align: 'left',
            dataIndex: 'tinId',
          sorter: true
          },
          {
            title:this.$t('单号'),
            align: 'left',
            dataIndex: 'noticeId',
          sorter: true
          },
          {
            title:this.$t('生产日期'),
            align: 'left',
            dataIndex: 'goodsProData',
          sorter: true
          },
          {
            title:this.$t('子PO号'),
            align: 'left',
            dataIndex: 'goodsBatch',
          sorter: true
          },
          {
            title:this.$t('盘点类型'),
            align: 'left',
            dataIndex: 'sttType',
            customRender: (text)=> {
              return text=="1"?this.$t('明盘'):this.$t('暗盘')
            },
          sorter: true
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
          // {
          //   title:'盘点数量',
          //   align: 'left',
          //   dataIndex: 'sttQua'
          // },
          // {
          //   title:'货主',
          //   align: 'left',
          //   dataIndex: 'cusCode'
          // },
          {
            title:this.$t('盘点状态'),
            align: 'left',
            dataIndex: 'sttSta',
          sorter: true
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            // fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }

        ],
        url: {
          list: "/jeewms/wmSttInGoods/list",
          delete: "/jeewms/wmSttInGoods/delete",
          deleteBatch: "/jeewms/wmSttInGoods/deleteBatch",
          exportXlsUrl: "/jeewms/wmSttInGoods/exportXls",
          importExcelUrl: "/jeewms/wmSttInGoods/importExcel",
          subU8url: "/jeewms/wmsPda/getU8Pandiandan",
          listWmSttInGoodByMainId: '/jeewms/wmSttInGoods/queryWmSttInGoodListByMainId'
        },
        dictOptions:{},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
    canclePrintOrder() {
      this.printData = {
        createTime: '',
        noticeId: '',
        imBeizhu: ''
      }
      this.visible1 = false
    },
      //打开打印盘点单
    goPrintOrder(record) {
      this.printData = record
      this.getWmSttInGoodsList(record.noticeId)
      this.visible1 = true
    },
    //根据盘点单号获取单据商品列表
    getWmSttInGoodsList(id) {
      var params = {
        id: id
      }
      getAction(this.url.listWmSttInGoodByMainId, params).then(res => {
        if (res.success) {
          res.result.sort((a,b)=>{
             return a.binId.localeCompare(b.binId)
          })
          this.printDataList = res.result
          this.printDataList.forEach(item => {
            if (item.goodsQua) {
              this.goodsQua += parseInt(item.goodsQua)
            }
            if (item.sttQua) {
              this.sttQua += parseInt(item.sttQua)
            }
          })
        }
      })
    },
      handleDiff(record){
//        if(this.selectedRowKeys.length != 1){
//          this.$message.error("请选择一项盘点记录进行过账");
//          return;
//        }
        this.$http.post('/jeewms/wmSttInGoods/doDifferent',{
          id:record.id
        }).then(res=>{
          if (res.success){
//            this.$message.success("保存成功")
//            this.printIds = res.result;
            this.loadData();
            this.$message.success("生成差异成功")
          }else{
            this.$message.error(this.$t('操作失败'))
          }
        })
      },
      //提交
      syncDbd() {
        if (this.selectedRowKeys.length != 1) {
          this.$message.warning(this.$t('请选择一条记录!'))
          return
        }
        var that = this
        let id = this.selectedRowKeys[0];
        getAction(that.url.subU8url, {id: id}).then(res => {
          if (res.success) {
            that.$message.success('操作成功！')
            that.loadData()
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>