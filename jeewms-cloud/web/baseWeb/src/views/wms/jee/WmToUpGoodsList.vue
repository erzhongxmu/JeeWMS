<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('入库单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.orderId"></a-input>
            </a-form-item>
          </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('库位编码')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.kuWeiBianMa"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('托盘')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.binId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('客户')">
                <j-popup
                  v-model="queryParam.cusCode"
                  code="cus_jiancheng"
                  org-fields="zhong_wen_qch,ke_hu_bian_ma"
                  dest-fields="cusName,cusCode"
                  field="cusCode"
                />
              </a-form-item>
            </a-col>
             <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('子PO号')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsBatch"></a-input>
              </a-form-item>
            </a-col>
            <!--            <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="货主">-->
            <!--                <j-dict-select-tag placeholder="请选择货主" v-model="queryParam.cusCode" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button
                type="primary"
                @click="searchQuery"
                icon="search"
                v-has="'toUpGoods:search'"
              >{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开') }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button
        @click="printBqCode"
        type="primary"
        icon="plus"
        v-has="'toUpGoods:printSampleLabel'"
      >{{$t('样品标签打印')}}</a-button>
      <!--      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'toUpGoods:add'">{{$t('添加')}}</a-button>-->
      <a-button
        icon="download"
        @click="handleExportXls($t('上架调整'))"
        v-has="'toUpGoods:export'"
      >{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        v-has="'toUpGoods:init'"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button icon="import">{{$t('库存初始化')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'toUpGoods:batchDelete'">
        <a-icon type="delete" />
        {{$t('批量删除')}}
      </a-button>
      <a-button @click="batchEdit" v-has="'toUpGoods:update'">{{$t('批量修改')}}</a-button>
      <a-button @click="batchSave" v-has="'toUpGoods:save'">{{$t('批量保存')}}</a-button>
      <a-button @click="batchCancel" v-has="'toUpGoods:cancel'">{{$t('批量取消修改')}}</a-button>
      <a-button @click="handlePrint('rk')" v-has="'toUpGoods:printLabel'">{{$t('批量打印标签')}}</a-button>
      <a-button @click="handlePrint('xiangmai')" v-has="'toUpGoods:printBox'">{{$t('批量打印箱唛')}}</a-button>
      <!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
      <!--        <a-menu slot="overlay">-->
      <!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
      <!--          <a-menu-item key="6" @click="batchEdit"><a-icon type="edit"/>修改</a-menu-item>-->
      <!--          <a-menu-item key="7" @click="batchSave"><a-icon type="save"/>保存</a-menu-item>-->
      <!--          <a-menu-item key="8" @click="batchCancel"><a-icon type="stop"/>取消修改</a-menu-item>-->
      <!--        </a-menu>-->
      <!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
      <!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{$t('项目')}}
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
        :rowClassName="setRowClsaa"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >{{$t('下载')}}</a-button>
        </template>
        <template slot="goodsQua" slot-scope="text, record">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'goodsQua')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsProData" slot-scope="text, record">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-date-picker @change="e => handleBatchChange(e, record.id, 'goodsProData')" />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="kuWeiBianMa" slot-scope="text, record">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'kuWeiBianMa')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="binId" slot-scope="text, record">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'binId')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="baseGoodscount" slot-scope="text, record">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, 'baseGoodscount')"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <span v-has="'toUpGoods:check'">
            <a @click="handleCheck(record)">{{$t('查看')}}</a>
            <a-divider type="vertical" />
          </span>
          <span v-has="'toUpGoods:edit'">
            <a @click="handleEdit(record)">{{$t('编辑')}}</a>
            <a-divider type="vertical" />
          </span>
          <a-popconfirm
            :title="$t('你确定吗?')"
            @confirm="() => handleDelete(record.id)"
            v-has="'toUpGoods:delete'"
          >
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>

     <j-modal :visible="visibledy1" :title="$t('打印标签')" :width="700" @cancel="visibledy1=false">
      <!-- 自定义弹窗确定按钮，实现打印功能 -->
      <template slot="footer">
        <a-button key="back" @click="visibledy1=false">{{$t('取消')}}</a-button>
        <a-button key="submit" type="primary" :loading="loading" v-print="'#printbq'">{{$t('打印')}}</a-button>
      </template>

      <!-- 打印模板 -->
      <div style="margin-bottom: 20px">
        <div class="printTitle">
          <p>样品类型：</p>
          <j-search-select-tag v-model="bqText" dict="sample_type" :placeholder="$t('请选择')" />
        </div>
        <!-- <div class="printTitle" style="margin-top: 10px">
          <p>份额：</p>
          <a-input v-model="bqNum" />
        </div>-->
        <!-- <a-button  v-print="'#printbq'" style="margin-bottom: 10px">{{$t('打印')}}</a-button> -->
        <div class="printBady" id="printbq" ref="print">
          <div
            class="printItem"
            v-for="(item,index) in selectionRows"
            :key="index"
            style="page-break-after:always"
          >
            <div class="printItem3">
              <div class="printItem_1">
                <p class="printItem_title">{{bqText}}</p>
                <p>- {{item.cusCode_dictText}}</p>
                <p>- {{item.goodsName}}</p>
                <p>Date：</p>
              </div>
              <div class="printItem_2">
                <p class="printItem_title">{{bqText}}</p>
                <p>- {{item.cusCode_dictText}}</p>
                <p>- {{item.goodsName}}</p>
                <p>Date：</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </j-modal>

    <wmToUpGoods-modal ref="modalForm" @ok="modalFormOk"></wmToUpGoods-modal>
    <export-spin v-if="exportOk"></export-spin>

    <ShippingMark  ref="ShippingMark" />
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { postAction, getAction, putAction } from '@/api/manage'
import WmToUpGoodsModal from './modules/WmToUpGoodsModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import moment from 'moment'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import ExportSpin from '@/components/ExportSpin/ExportSpin'
import JSearchSelectTag from '@comp/dict/JSearchSelectTag'
import ShippingMark from '@/views/wms/jee/pltn/ShippingMark'

export default {
  name: 'WmToUpGoodsList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    JDictSelectTag,
    WmToUpGoodsModal,
    ExportSpin,
    JSearchSelectTag,
    ShippingMark
  },
  data() {
    return {
      description: 'wm_to_up_goods管理页面',
      visibledy1: false,
      bqText: 'GROLET LONDON',
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
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'goodsName',
          sorter: true
        },
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsId',
          sorter: true
        },
        {
          title: this.$t('数量'),
          align: 'left',
          dataIndex: 'goodsQua',
          sorter: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          sorter: true
        },
        // {
        //   title:'原始单据编码',
        //   align: 'left',
        //   dataIndex: 'orderId'
        // },
        {
          title: this.$t('生产日期'),
          align: 'left',
          dataIndex: 'goodsProData',
          scopedSlots: { customRender: 'goodsProData' },
          sorter: true
        },
        {
          title: this.$t('子PO号'),
          align: 'left',
          dataIndex: 'goodsBatch',
          sorter: true
        },
        // {
        //   title:'库位编码',
        //   align: 'left',
        //   dataIndex: 'kuWeiBianMa',
        //   scopedSlots: { customRender: 'kuWeiBianMa' },
        // },
        {
          title: this.$t('托盘'),
          align: 'left',
          dataIndex: 'binId',
          scopedSlots: { customRender: 'binId' },
          sorter: true
        },
        {
          title: this.$t('货主名称'),
          align: 'left',
          dataIndex: 'cusCode_dictText',
          sorter: true
        },
        // {
        //   title:this.$t('基本单位'),
        //   align: 'left',
        //   dataIndex: 'baseUnit'
        // },
        {
          title: this.$t('收货数量'),
          align: 'left',
          dataIndex: 'baseGoodscount',
          scopedSlots: { customRender: 'baseGoodscount' },
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
      ],
      url: {
        list: '/jeewms/wmToUpGoods/list',
        delete: '/jeewms/wmToUpGoods/delete',
        deleteBatch: '/jeewms/wmToUpGoods/deleteBatch',
        exportXlsUrl: '/jeewms/wmToUpGoods/exportXls',
        importExcelUrl: 'jeewms/wmToUpGoods/importExcel',
        updateBatch: '/jeewms/wmToUpGoods/updateBatch'
      },
      dictOptions: {},
      //批量修改标记
      batchEditFlag: false,
      //批量修改时新值数据
      newColData: [],
      //批量修改时数据修改行的id集合
      newColIds: [],
      printb:[], // 打印的内容
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    printBqCode() { // 样品标签打印
      if (this.selectedRowKeys.length > 0) {
        this.visibledy1 = true
        // this.countArr(this.bqNum)
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    // handlePrint(e) {
    //   if (this.selectedRowKeys.length < 1) {
    //     this.$message.warning(this.$t('请选择一条记录!'))
    //     return
    //   }
    //   let ids = this.selectedRowKeys.join(',')
    //   let url = '/jeewms/wmImNoticeH/labelPrints2' // 默认打印箱唛
    //   if (e == 1) {
    //     url = '/jeewms/wmImNoticeH/labelPrinting2' // 标签
    //   }
    //   getAction(url, { ids }).then(res => {})
    // },
    handlePrint(e) {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      let ids = this.selectedRowKeys.join(',')
      this.selectionRows.map((item,index)=>{
          item.xianghao = item.binId
          item.eachBox = item.baseGoodscount
          item.goodsCode = item.goodsId
      })
      let data = {
        list: this.selectionRows,
        param:{
          id:ids,
          type:e,
          pageType:'SJTZ'
        }
      }
      this.$refs.ShippingMark.onShow(data)
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
      if (newValue && newValue._isAMomentObject) {
        newValue = moment(newValue).format('YYYY-MM-DD')
      }
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
        postAction(that.url.updateBatch, that.newColData).then(res => {
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
<style scoped lang="less">
@import '~@assets/less/common.less';
.printTitle {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  p {
    width: 30%;
    margin: 0;
  }
}
.printBady {
  background: #eee;
  width: 330px;
  .printItem {
    font-size: 10px;
    width: 330px;
    .printItem3{
      width: 330px;
      height: 106px;
      display: flex;
    }
    .printItem_2 {
      width: 50%;
      height: 100%;
      margin-left: 20px;
      display: inline-block;
    }
    .printItem_1 {
      width: 50%;
      height: 100%;
      display: inline-block;
    }
    .printItem_title {
      font-size: 17px;
    }
    p {
      font-weight: 600;
      color: #000;
      margin: 0;
      line-height: 16px;
    }
  }
}
</style>