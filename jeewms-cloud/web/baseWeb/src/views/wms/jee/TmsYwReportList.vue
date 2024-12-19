<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
      <a-button icon="download" @click="handleExportXls('tms_yw_dingdan')">{{$t('导出')}}</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
<!--      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
<!--      </a-dropdown>-->
<!--      <a-button v-if="selectedRowKeys.length > 0" @click="getCarList" type="primary" icon="plus">批量派车</a-button>-->
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
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
           <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
        </span>

      </a-table>
    </div>

<!--    <j-modal-->
<!--      title="派车司机"-->
<!--      width="800px"-->
<!--      :visible="showModal"-->
<!--      :confirmLoading="confirmLoading"-->
<!--      switchFullscreen-->
<!--      @ok="handleOk"-->
<!--      @cancel="handleCancel"-->
<!--      >-->
<!--      <a-spin :spinning="confirmLoading">-->
<!--        <a-form :form="form">-->
<!--          <a-form-item label="派车司机">-->
<!--            <a-select-->
<!--              labelInValue-->
<!--              v-decorator="[-->
<!--          'chehao',-->
<!--          { rules: [{ required: true, message: '请选择车辆' }] },-->
<!--        ]"-->
<!--              placeholder="请选择车辆"-->
<!--              @select="handleSelectChange"-->
<!--            >-->
<!--              <a-select-option v-for="(item,key) in carList" :value="item.username">-->
<!--                {{item.chepaihao}}-->
<!--              </a-select-option>-->

<!--            </a-select>-->
<!--          </a-form-item>-->
<!--        </a-form>-->
<!--      </a-spin>-->
<!--    </j-modal>-->

    <tmsYwReport-modal ref="modalForm" @ok="modalFormOk"></tmsYwReport-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { httpAction } from '@/api/manage'
  import { getAction } from '@/api/manage'
  import { postAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import TmsYwReportModal from './modules/TmsYwReportModal'

  export default {
    name: "TmsYwDingdanList",
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      TmsYwReportModal
    },
    data () {
      return {
        description: 'tms_yw_dingdan管理页面',
        form: this.$form.createForm(this),
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            // fixed:"right",
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
            title:'单号',
            align: 'left',
            dataIndex: 'fadh'
          },
          {
            title:'发货人',
            align: 'left',
            dataIndex: 'fahuoren'
          },
          {
            title:'发货人电话',
            align: 'left',
            dataIndex: 'fhrdh'
          },
          {
            title:'发货人地址',
            align: 'left',
            dataIndex: 'fhrdz'
          },
          {
            title:'收货人',
            align: 'left',
            dataIndex: 'shouhuoren'
          },
          {
            title:'收货人电话',
            align: 'left',
            dataIndex: 'shrsj'
          },
          {
            title:'收货人地址',
            align: 'left',
            dataIndex: 'shrdh'
          },
          {
            title:'货物',
            align: 'left',
            dataIndex: 'huowu'
          },
          {
            title:'长米',
            align: 'left',
            dataIndex: 'chang'
          },
          {
            title:'宽米',
            align: 'left',
            dataIndex: 'kuan'
          },
          {
            title:'高米',
            align: 'left',
            dataIndex: 'gao'
          },
          {
            title:'立方米',
            align: 'left',
            dataIndex: 'tiji'
          },
          {
            title:'重量',
            align: 'left',
            dataIndex: 'zhongl'
          },
          {
            title:'代收款金额',
            align: 'left',
            dataIndex: 'daishouk'
          },
          {
            title:'是否等通知',
            align: 'left',
            dataIndex: 'dengtongzhi_dictText'
          },
          {
            title:'状态',
            align: 'left',
            dataIndex: 'zhuangtai_dictText'
          },
          {
            title:'货主单号',
            align: 'left',
            dataIndex: 'ywkhdh'
          },
          {
            title:'送货方式',
            align: 'left',
            dataIndex: 'hwshfs_dictText'
          }

        ],
        url: {
          list: "/jeewms/tmsYwDingdan/bbList",
          delete: "/jeewms/tmsYwDingdan/delete",
          deleteBatch: "/jeewms/tmsYwDingdan/deleteBatch",
          exportXlsUrl: "/jeewms/tmsYwDingdan/exportXls",
          importExcelUrl: "jeewms/tmsYwDingdan/importExcel",
          getCarList: "/jeewms/tmsYwDingdan/getCarList",
          batchSendCar: "/jeewms/tmsYwDingdan/batchSendCar",
        },
        dictOptions:{},
        //是否显示派车下拉模态框
        showModal: false,
        //车辆下拉数据
        carList: [],
        confirmLoading: false,
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        carInfo: {},
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      //确认派车司机
      // handleOk() {
      //   var param = {
      //     chehao: this.carInfo.label.replace(/^\s*|\s*$/g,""),
      //     id: this.selectedRowKeys.join(","),
      //     sj: this.carInfo.key
      //   }
      //   postAction(this.url.batchSendCar,param).then((res) => {
      //     if (res.success) {
      //       this.showModal = false;
      //       this.$nextTick(() => {
      //         this.carList = [];
      //         this.carInfo = {};
      //         this.loadData();
      //       })
      //     } else {
      //       this.$message.warning(this.$t('操作失败'))
      //     }
      //   })
      // },
      // //获取车辆列表
      // getCarList() {
      //   getAction(this.url.getCarList).then((res) => {
      //     if (res.success) {
      //       this.carList = res.result;
      //       this.showModal = true;
      //     } else {
      //       this.$message.warning(this.$t('操作失败'))
      //     }
      //   })
      //
      // },
      // //选择车辆
      // handleSelectChange(value) {
      //   console.log(value,this.selectedRowKeys)
      //   this.carInfo =value
      // },
      // //关闭车辆选择模态框
      // handleCancel() {
      //   this.showModal = false;
      //   this.$nextTick(() => {
      //     this.carList = [];
      //     this.carInfo = {};
      //   })
      // }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>