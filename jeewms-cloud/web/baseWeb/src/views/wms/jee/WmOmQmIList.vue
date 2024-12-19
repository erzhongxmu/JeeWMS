<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('出库单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.omNoticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('货主编码')">
              <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma"
                :placeholder="$t('请选择')" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('货主名称')">
              <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma"
                :placeholder="$t('请选择')" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('库位')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.binId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('托盘')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.tinId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsBatch"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('装箱单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.imCusCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{ $t('查询') }}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{ $t('重置') }}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
      <a-button icon="download" @click="handleExportXls($t('下架'))" v-has="'WmOmQmIList:export'">{{ $t('导出') }}</a-button>
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
      <!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
      <!--      </a-upload>-->
      <!--      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->
      <a-button @click="batchRemove" v-has="'WmOmQmIList:batchRemove'" :loading="isLoading">{{ $t('批量下架') }}</a-button>
      <a-button @click="batchEdit" v-has="'WmOmQmIList:batchUpdate'">{{ $t('批量修改') }}</a-button>
      <a-button @click="batchSave" v-has="'WmOmQmIList:batchSave'">{{ $t('批量保存') }}</a-button>
      <a-button @click="batchCancel" v-has="'WmOmQmIList:cancelBatchUpdate'">{{ $t('批量取消修改') }}</a-button>
      <a-button @click="handlePrint('ck')" v-has="'WmOmQmIList:batchPrint'">{{ $t('批量打印标签') }}</a-button>
      <!--      <a-dropdown>-->
      <!--        <a-menu slot="overlay">-->
      <!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
      <!--          <a-menu-item key="2" @click="batchRemove"><a-icon type="fall"/>下架</a-menu-item>-->
      <!--          <a-menu-item key="3" @click="batchEdit"><a-icon type="edit"/>修改</a-menu-item>-->
      <!--          <a-menu-item key="4" @click="batchSave"><a-icon type="save"/>保存</a-menu-item>-->
      <!--          <a-menu-item key="5" @click="batchCancel"><a-icon type="stop"/>取消修改</a-menu-item>-->
      <!--        </a-menu>-->
      <!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
      <!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{ $t('已选择') }}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{ $t('项目') }}
        <a style="margin-left: 24px" @click="onClearSelected">{{ $t('清空') }}</a>
      </div>

      <a-table ref="table" size="middle" bordered rowKey="id" :columns="columns" :dataSource="dataSource"
        :pagination="ipagination" :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" class="j-table-force-nowrap"
        @change="handleTableChange" :rowClassName="setRowClsaa">
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{ $t('无图片') }}</span>
          <img v-else :src="getImgView(text)" height="25px" alt
            style="max-width:80px;font-size: 12px;font-style: italic;" />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{ $t('无文件') }}</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small"
            @click="uploadFile(text)">下载</a-button>
        </template>
        <template v-for="col in columnsEdit" :slot="col" slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, col, record)" />
          </div>
          <div v-else>{{ text }}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'WmOmQmIList:check'">{{ $t('查看') }}</a>
          <a-divider type="vertical" v-has="'WmOmQmIList:update'" />
          <a @click="handleEdit(record)" v-has="'WmOmQmIList:update'">{{ $t('编辑') }}</a>
          <!-- <a-divider type="vertical" v-has="'WmOmQmIList:batchRemove'"/>
          <a @click="downGoods(record)" v-has="'WmOmQmIList:batchRemove'">{{$t('下架')}}</a>-->
          <!--          <a-divider type="vertical" />-->
          <!--          <a @click="handleRemove(record.id)">下架</a>-->
          <a-divider type="vertical" v-has="'WmOmQmIList:delete'" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'WmOmQmIList:delete'">
            <a>{{ $t('删除') }}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <!--    <a-tabs defaultActiveKey="1">-->
    <!--      <a-tab-pane tab="下架明细" key="1" >-->
    <!--        <WmOmQmSecondList :mainId="selectedMainId" />-->
    <!--      </a-tab-pane>-->
    <!--    </a-tabs>-->

    <wmOmQmI-modal ref="modalForm" @ok="modalFormOk"></wmOmQmI-modal>
    <export-spin v-if="exportOk"></export-spin>

    <ShippingMark ref="ShippingMark" />
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmOmQmIModal from './modules/WmOmQmIModal'
import WmOmQmSecondList from './WmOmQmSecondList'
import { putAction, getAction } from '@/api/manage'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import ExportSpin from '@/components/ExportSpin/ExportSpin'
import ShippingMark from '@/views/wms/jee/pltn/ShippingMark'

export default {
  name: 'WmOmQmIList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    JDictSelectTag,
    WmOmQmIModal,
    WmOmQmSecondList,
    JSearchSelectTag,
    ExportSpin,
    ShippingMark
  },
  data() {
    return {
      isLoading: false,
      description: '下架任务管理页面',
      selectedMainId: '',
      //可编辑行
      columnsEdit: ['tinId', 'baseGoodscount'],
      // 表头
      columns: [
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
          title: this.$t('装箱单号'),
          align: 'left',
          dataIndex: 'imCusCode',
          sorter: true
        },

        {
          title: this.$t('子PO号'),
          align: 'left',
          dataIndex: 'goodsBatch',
          scopedSlots: { customRender: 'goodsBatch' },
          sorter: true
        },
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsId',
          scopedSlots: { customRender: 'goodsId' },
          sorter: true
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'goodsName',
          scopedSlots: { customRender: 'goodsName' },
          sorter: true
        },
        {
          title: this.$t('货主编码'),
          align: 'left',
          dataIndex: 'cusCode',
          scopedSlots: { customRender: 'cusCode' },
          sorter: true
        },
        {
          title: this.$t('货主名称'),
          align: 'left',
          dataIndex: 'cusCode_dictText',
          scopedSlots: { customRender: 'cusCode_dictText' },
          sorter: true
        },
        {
          title: this.$t('库位'),
          align: 'left',
          dataIndex: 'binId',
          scopedSlots: { customRender: 'binId' },
          sorter: true
        },
        {
          title: this.$t('托盘'),
          align: 'left',
          dataIndex: 'tinId',
          scopedSlots: { customRender: 'tinId' },
          sorter: true
        },
        // {
        //   title:this.$t('出货数量'),
        //   align: 'left',
        //   dataIndex: 'qmOkQuat',
        //   scopedSlots: { customRender: 'qmOkQuat' },
        // sorter: true
        // },
        {
          title: this.$t('出货数量'),
          align: 'left',
          dataIndex: 'baseGoodscount',
          scopedSlots: { customRender: 'baseGoodscount' },
          sorter: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          scopedSlots: { customRender: 'goodsUnit' },
          sorter: true
        },
        // {
        //   title:this.$t('是否下架'),
        //   align: 'left',
        //   dataIndex: 'binSta',
        //   customRender: (text)=> {
        //     return text!="Y"?this.$t('否'):this.$t('是')
        //   },
        // sorter: true
        // },
        {
          title: this.$t('创建人'),
          align: 'left',
          dataIndex: 'createBy',
          sorter: true
        },
        {
          title: this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          customRender: function (text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
          sorter: true
        },
        // {
        //   title:this.$t('备注'),
        //   align: 'left',
        //   dataIndex: 'itemText',
        //   scopedSlots: { customRender: 'itemText' },
        // sorter: true
        // },
        // {
        //   title:this.$t('数量'),
        //   align: 'left',
        //   dataIndex: 'qmOkQuat',
        //   scopedSlots: { customRender: 'qmOkQuat' },
        // },
        {
          title: this.$t('生产日期'),
          align: 'left',
          dataIndex: 'proData',
          scopedSlots: { customRender: 'proData' },
          sorter: true
        },
        // {
        //   title:this.$t('体积'),
        //   align: 'left',
        //   dataIndex: 'tinTj',
        //   scopedSlots: { customRender: 'tinTj' },
        // },
        // {
        //   title:this.$t('重量'),
        //   align: 'left',
        //   dataIndex: 'tinZhl',
        //   scopedSlots: { customRender: 'tinZhl' },
        // },
        // {
        //   title:this.$t('温度'),
        //   align: 'left',
        //   dataIndex: 'recDeg',
        //   scopedSlots: { customRender: 'recDeg' },
        // },
        // {
        //   title:this.$t('任务接收人'),
        //   align: 'left',
        //   dataIndex: 'assignTo',
        //   scopedSlots: { customRender: 'assignTo' },
        // },
        // {
        //   title:this.$t('基本单位'),
        //   align: 'left',
        //   dataIndex: 'baseUnit',
        //   scopedSlots: { customRender: 'baseUnit' },
        // },
        // {
        //   title:this.$t('出货数量'),
        //   align: 'left',
        //   dataIndex: 'baseGoodscount',
        //   scopedSlots: { customRender: 'baseGoodscount' },
        //   sorter: true
        // },
        {
          title: this.$t('出库单号'),
          align: 'left',
          dataIndex: 'omNoticeId',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
        // {
        //   title:this.$t('货主名称'),
        //   align: 'left',
        //   dataIndex: 'cusName',
        //   scopedSlots: { customRender: 'cusName' },
        // },
        // {
        //   title:this.$t('波次编号'),
        //   align: 'left',
        //   dataIndex: 'waveId',
        //   scopedSlots: { customRender: 'waveId' },
        // },
      ],
      url: {
        list: '/jeewms/wmOmQmI/list',
        delete: '/jeewms/wmOmQmI/delete',
        deleteBatch: '/jeewms/wmOmQmI/deleteBatch',
        exportXlsUrl: '/jeewms/wmOmQmI/exportXls',
        importExcelUrl: 'jeewms/wmOmQmI/importExcel',
        // dotowavedown: "/jeewms/wmOmQmI/dotowavedown",
        dotowavedown: '/jeewms/wmOmQmI/dotodown1',
        editBatch: '/jeewms/wmOmQmI/editBatch'
      },
      dictOptions: {},
      //批量修改标记
      batchEditFlag: false,
      //批量修改时新值数据
      newColData: [],
      //批量修改时数据修改行的id集合
      newColIds: []
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    // handlePrint(){
    //   if(this.selectedRowKeys.length < 1) {
    //     this.$message.warning(this.$t('请选择一条记录!'));
    //     return;
    //   }
    //   var ids = this.selectedRowKeys.join(',')
    //   getAction('/jeewms/wmOmQmI/labelPrinting',{ids}).then(res=>{})
    // },

    handlePrint(e) {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      let ids = this.selectedRowKeys.join(',')
      this.selectionRows.map((item, index) => {
        item.goodsCode = item.goodsId
      })
      let data = {
        list: this.selectionRows,
        param: {
          id: ids,
          type: e,
          pageType: 'XJ'
        }
      }
      this.$refs.ShippingMark.onShow(data)
    },
    initDictConfig() { },
    downGoods(record) {
      let that = this
      this.$confirm({
        title: '确定下架',
        content: '是否确认下架选定数据',
        onOk() {
          let param = [record.id]
          putAction(that.url.dotowavedown, param).then(res => {
            if (res.success) {
              that.$message.success('下架成功')
              that.loadData()
            } else {
              that.$message.warning(this.$t('操作失败'))
            }
          })
        },
        onCancel() { }
      })
    },
    //批量下架
    batchRemove() {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      var that = this
      that.isLoading = true
      this.$confirm({
        title: '确定下架',
        content: '是否确认下架选定数据',
        onOk() {
          var param = that.selectedRowKeys
          putAction(that.url.dotowavedown, param).then(res => {
            if (res.success) {
              that.$message.success('下架成功')
              that.isLoading = false
              that.selectedRowKeys = []
              that.loadData()
            } else {
              that.$message.warning(that.$t(res.message))
              that.isLoading = false
              that.loadData()
            }
          })
        },
        onCancel() {
          that.isLoading = false
        }
      })
    },
    //单条下架
    handleRemove(id) {
      var ids = []
      ids.push(id)
      putAction(this.url.dotowavedown, ids).then(res => {
        if (res.success) {
          this.loadData()
          this.$message.success('下架成功')
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    //查看
    searchDetail(id) {
      console.log(id)
      this.selectedMainId = id
    },
    batchEdit() {
      if (this.selectedRowKeys.length > 0) {
        this.batchEditFlag = true
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    //可编辑行改变时调用
    handleBatchChange(newValue, id, col) {
      var newSingleData = {}
      if (this.newColIds.indexOf(id) != -1) {
        this.newColData.forEach((item, index) => {
          if (item.id == id) {
            this.newColData[index][col] = newValue
          }
        })
      } else {
        this.newColIds.push(id)
        newSingleData[col] = newValue
        newSingleData.id = id
        this.newColData.push(newSingleData)
      }
      console.log(this.newColData, this.newColIds)
    },
    //批量保存
    batchSave() {
      var that = this
      if (this.batchEditFlag) {
        putAction(that.url.editBatch, that.newColData).then(res => {
          if (res.success) {
            that.$message.success('批量修改成功')
            that.newColData = []
            that.newColIds = []
            that.loadData()
            that.batchEditFlag = false
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
      } else {
        this.$message.warning('无可保存内容!')
      }
    },
    //取消批量修改
    batchCancel() {
      if (this.batchEditFlag) {
        this.newColData = []
        this.newColIds = []
        this.batchEditFlag = false
        this.selectedRowKeys = []
      } else {
        this.$message.warning('无修改内容可取消!')
      }
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>