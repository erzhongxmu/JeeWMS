<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item  :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')"   v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item  :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')"  v-model="queryParam.goodsName"></a-input>
            </a-form-item>
          </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('货主编码')">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma" :placeholder="$t('请选择')" />
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('货主名称')">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" :placeholder="$t('请选择')"  />
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('源库位')">
                <a-input :placeholder="$t('请输入')"   v-model="queryParam.binFrom"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('到库位')">
                <a-input :placeholder="$t('请输入')"  v-model="queryParam.binTo"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('源托盘')">
                <a-input :placeholder="$t('请输入')"  v-model="queryParam.tinFrom"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('到托盘')">
                <a-input :placeholder="$t('请输入')"  v-model="queryParam.tinId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('状态')">
                <!-- <a-input :placeholder="$t('请输入')"   v-model="queryParam.moveSta"></a-input> -->
                <j-dict-select-tag :placeholder="$t('请输入')" v-model="queryParam.moveSta" dictCode="moveSta"/>
              </a-form-item>
            </a-col>
<!--            <a-col :xl="4" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="转移货主">-->
<!--                <j-dict-select-tag :placeholder="$t('请输入')" v-model="queryParam.toCusCode" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
            <!-- <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('执行状态')">
                <a-input :placeholder="$t('请输入')"  v-model="queryParam.runSta"></a-input>
              </a-form-item>
            </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button  @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
<!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
      <a-button icon="download" @click="handleExportXls($t('库存转移'))" v-has="'WmToMoveGoodsList:export'">{{$t('导出')}}</a-button>
<!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
<!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
<!--      </a-upload>-->
      <a-button v-if="queryParam.moveSta == '计划中'" key="1" @click="batchDel" v-has="'WmToMoveGoodsList:batchDelete'"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
      <a-button v-if="queryParam.moveSta == '计划中'"  key="6" @click="batchEdit" v-has="'WmToMoveGoodsList:batchMove'" ><a-icon type="edit"/>{{$t('批量转移')}}</a-button>
      <a-button v-if="queryParam.moveSta == '计划中'"  key="7" @click="batchSave" v-has="'WmToMoveGoodsList:batchSave'" :loading="isloading"><a-icon type="save"/>{{$t('批量保存')}}</a-button>
      <a-button v-if="queryParam.moveSta == '计划中'"  key="8" @click="batchCancel" v-has="'WmToMoveGoodsList:cancelBatchMove'"><a-icon type="stop"/>{{$t('取消批量转移')}}</a-button>
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          -->
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
        <template slot="goodsQua"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'goodsQua',index)"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="tinId"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'tinId',index)"/>
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="binTo"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input  v-model="record['binTo']"/>
          </div>
          <div v-else>{{text}}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'WmToMoveGoodsList:check'">{{$t('查看')}}</a>
          <!-- <a-divider type="vertical" v-has="'WmToMoveGoodsList:update'"/>
          <a @click="handleEdit(record)" v-has="'WmToMoveGoodsList:update'">{{$t('编辑')}}</a> -->

          <a-divider type="vertical"  v-has="'WmToMoveGoodsList:delete'"/>
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'WmToMoveGoodsList:delete'">
                  <a  v-if="queryParam.moveSta == '计划中'" >{{$t('删除')}}</a>
          </a-popconfirm>
        </span>

      </a-table>
    </div>

    <wmToMoveGoods-modal ref="modalForm" @ok="modalFormOk"></wmToMoveGoods-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WmToMoveGoodsModal from './modules/WmToMoveGoodsModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import { putAction } from '@/api/manage'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import moment from 'moment'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import JSearchSelectTag from "@/components/dict/JSearchSelectTag"
  import ExportSpin from '@/components/ExportSpin/ExportSpin'

  export default {
    name: "WmToMoveGoodsList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      JDictSelectTag,
      WmToMoveGoodsModal,
      JSearchSelectTag,
      ExportSpin
    },
    data () {
      return {
        queryParam:{
          moveSta:'计划中'
        },
        isloading: false,
        description: '库存转移管理页面',
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
          // {
          //   title:'创建人名称',
          //   align: 'left',
          //   dataIndex: 'createName'
          // },
          // {
          //   title:'创建日期',
          //   align: 'left',
          //   dataIndex: 'createTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'更新人名称',
          //   align: 'left',
          //   dataIndex: 'updateName'
          // },
          // {
          //   title:'原始单据类型',
          //   align: 'left',
          //   dataIndex: 'orderTypeCode'
          // },
          // {
          //   title:'原始单据编码',
          //   align: 'left',
          //   dataIndex: 'orderId'
          // },
          // {
          //   title:'原始单据行项目',
          //   align: 'left',
          //   dataIndex: 'orderIdI'
          // },
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
            title:this.$t('货主编码'),
            align: 'left',
            dataIndex: 'cusCode',
          sorter: true
          },
          {
            title:this.$t('货主名称'),
            align: 'left',
            dataIndex: 'cusCode_dictText',
          sorter: true
          },
          {
            title:this.$t('到库位'),
            align: 'left',
            dataIndex: 'binTo',
            scopedSlots: { customRender: 'binTo' },
          sorter: true
          },
          {
            title:this.$t('到托盘'),
            align: 'left',
            dataIndex: 'tinId',
            // scopedSlots: { customRender: 'tinId' },
          sorter: true
          },
          {
            title:this.$t('数量'),
            align: 'left',
            dataIndex: 'goodsQua',
            // scopedSlots: { customRender: 'goodsQua' },
          sorter: true
          },
          {
            title:this.$t('生产日期'),
            align: 'left',
            dataIndex: 'goodsProData',
          sorter: true
          },
          // {
          //   title:this.$t('单位'),
          //   align: 'left',
          //   dataIndex: 'goodsUnit'
          // },
          {
            title:this.$t('源托盘'),
            align: 'left',
            dataIndex: 'tinFrom',
          sorter: true
          },
          {
            title:this.$t('源库位'),
            align: 'left',
            dataIndex: 'binFrom',
          sorter: true
          },
          {
            title:this.$t('状态'),
            align: 'left',
            dataIndex: 'moveSta',
            sorter: true
          },
          {
            title:this.$t('操作时间'),
            align: 'left',
            dataIndex: 'updateTime',
            sorter: true
          },
          {
            title:this.$t('操作人'),
            align: 'left',
            dataIndex: 'updateBy',
            sorter: true
          },
          // {
          //   title:this.$t('转移客户'),
          //   align: 'left',
          //   dataIndex: 'toCusCode_dictText'
          // },
          // {
          //   title:this.$t('基本单位'),
          //   align: 'left',
          //   dataIndex: 'baseUnit'
          // },
          // {
          //   title:this.$t('基本单位数量'),
          //   align: 'left',
          //   dataIndex: 'baseGoodscount'
          // },
          // {
          //   title:this.$t('批次'),
          //   align: 'left',
          //   dataIndex: 'goodsbatch'
          // },
          // {
          //   title:this.$t('到生产日期'),
          //   align: 'left',
          //   dataIndex: 'toGoodsProData'
          // },
          // {
          //   title:this.$t('执行状态'),
          //   align: 'left',
          //   dataIndex: 'runSta',
          // sorter: true
          // },
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
          list: "/jeewms/wmToMoveGoods/list",
          delete: "/jeewms/wmToMoveGoods/delete",
          deleteBatch: "/jeewms/wmToMoveGoods/deleteBatch",
          exportXlsUrl: "/jeewms/wmToMoveGoods/exportXls",
          importExcelUrl: "/jeewms/wmToMoveGoods/importExcel",
          updateBatch: "/jeewms/wmToDownGoods/updateBatch",
          editBatch: "/jeewms/wmToMoveGoods/editBatch",
        },
        dictOptions:{},
        //批量修改标记
        batchEditFlag: false,
        //批量修改时新值数据
        newColData: [],
        //批量修改时数据修改行的id集合
        newColIds: [],
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
      batchEdit() {
        console.log(this.newColData)
        if(this.selectedRowKeys.length > 0) {
          this.batchEditFlag = true
          let arr = []
          this.newColIds = []
          this.selectionRows.map(((item)=>{
            arr.push({
              binTo: item.binTo,
              goodsQua: item.goodsQua,
              id: item.id,
              tinId: item.tinId,
            })
            this.newColIds.push(item.id)
          }))
          this.newColData = arr
          
        } else {
          this.$message.warning(this.$t('请选择一条记录!'));
        }
      },
      //可编辑行改变时调用
      handleBatchChange(newValue, id, col) {
        if(newValue && newValue._isAMomentObject) {
          newValue = moment(newValue).format('YYYY-MM-DD');
        }
        var newSingleData = {};
        if(this.newColIds.indexOf(id) != -1) {
          this.newColData.forEach((item,index) => {
            if(item.id == id) {
              this.newColData[index][col] = newValue
            }
          })
        } else {
          this.newColIds.push(id)
          newSingleData[col] = newValue;
          newSingleData.id = id;
          this.newColData.push(newSingleData)
        }
      },
      //批量保存
      batchSave() {
        var that = this
        if(this.batchEditFlag) {
          let arr = []
          this.selectionRows.map(((item)=>{
            arr.push({
              binTo: item.binTo,
              goodsQua: item.goodsQua,
              id: item.id,
              tinId: item.tinId,
            })
          }))
          this.isloading = true
          putAction(that.url.editBatch, arr).then((res) => {
            if (res.success) {
              that.$message.success('批量修改成功')
              that.newColData = [];
              that.newColIds = [];
              that.loadData();
              that.batchEditFlag = false;
            } else {
              that.$message.warning(this.$t('操作失败'))
            }
            this.isloading = false
          })
        } else {
          this.$message.warning('无可保存内容!');
        }
      },
      //取消批量修改
      batchCancel() {
        if(this.batchEditFlag) {
          this.newColData = [];
          this.newColIds = [];
          this.batchEditFlag = false;
          this.selectedRowKeys = [];
        } else {
          this.$message.warning('无修改内容可取消!');
        }
      },
      //批量确认
      batchConfirm() {
        var that = this;
        var param = that.selectedRowKeys;
        putAction(that.url.doassignbatch,param).then((res) => {
          if (res.success) {
            that.$message.success('批量确认成功')
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