<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="订单号">
              <a-input placeholder="请输入订单号" v-model="queryParam.orderId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品编码">
              <a-input placeholder="请输入商品编码" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品名称">
                <a-input placeholder="请输入商品名称" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主编码">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,ke_hu_bian_ma,ke_hu_bian_ma" placeholder="请选择货主编码"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主名称">
                <j-search-select-tag v-model="queryParam.cusCode" dict="md_cus,zhong_wen_qch,ke_hu_bian_ma" placeholder="请选择货主名称"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="源托盘">
                <a-input placeholder="请输入源托盘" v-model="queryParam.binIdFrom"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="目标托盘码">
                <a-input placeholder="请输入目标托盘码" v-model="queryParam.binIdTo"></a-input>
              </a-form-item>
            </a-col>
<!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
<!--              <a-form-item label="货主">-->
<!--                <j-dict-select-tag placeholder="请选择货主" v-model="queryParam.cusCode" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>-->
<!--              </a-form-item>-->
<!--            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
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
<!--      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>  v-has="'truckReCheck:onceBatchReCheck'"-->
      <a-button  @click="batchRecheck"><a-icon type="minus"/>一键批量复核</a-button>
      <a-button @click="chooseRecheck"><a-icon type="edit"/>选择批量复核</a-button>
      <a-button @click="chooseSave"><a-icon type="save"/>批量保存</a-button>
      <a-button @click="chooseCancel"><a-icon type="stop"/>取消批量复核</a-button>
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
        <template slot="goodsQuaok"
                  slot-scope="text, record, index">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input :value="text" @blur="e => handleBatchChange(e.target.value, record.id, 'goodsQuaok',record)"/>
          </div>
          <div v-else>{{text}}</div>
        </template>

<!--        <span slot="action" slot-scope="text, record">-->
<!--          <a @click="handleCheck(record)">{{$t('查看')}}</a>-->
<!--          <a-divider type="vertical"/>-->
<!--          <a @click="handleEdit(record)">{{$t('编辑')}}</a>-->

<!--          <a-divider type="vertical" />-->
<!--          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>{{$t('删除')}}</a>-->
<!--                </a-popconfirm>-->
<!--        </span>-->

      </a-table>
    </div>

    <wmTruckReCheck-modal ref="modalForm" @ok="modalFormOk"></wmTruckReCheck-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import { putAction } from '@/api/manage'
  import WmTruckReCheckModal from './modules/WmTruckReCheckModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'
  import JSearchSelectTag from "@/components/dict/JSearchSelectTag"

  export default {
    name: "WmToDownGoodsList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      JDictSelectTag,
      WmTruckReCheckModal,
      JSearchSelectTag
    },
    data () {
      return {
        description: '下架明细管理页面',
        // 表头
        columns: [
          // {
          //   title: this.$t('操作'),
          //   dataIndex: 'action',
          //   align: 'left',
          //   // fixed:"right",
          //   width:147,
          //   scopedSlots: { customRender: 'action' }
          // },
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
            title:'订单号',
            align: 'left',
            dataIndex: 'orderId'
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
            title:'货主编码',
            align: 'left',
            dataIndex: 'cusCode'
          },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusCode_dictText'
          },
          {
            title:'拣货数量',
            align: 'left',
            dataIndex: 'goodsQua'
          },
          {
            title:'复核数量',
            align: 'left',
            dataIndex: 'goodsQuaok',
            scopedSlots: { customRender: 'goodsQuaok' },
          },
          {
            title:'库位',
            align: 'left',
            dataIndex: 'kuWeiBianMa'
          },
          {
            title:'源托盘',
            align: 'left',
            dataIndex: 'binIdFrom'
          },
          {
            title:'目标托盘码',
            align: 'left',
            dataIndex: 'binIdTo'
          },
          {
            title:'状态',
            align: 'left',
            dataIndex: 'downSta'
          },
          {
            title:'创建人',
            align: 'left',
            dataIndex: 'createName'
          },
          {
            title:'创建日期',
            align: 'left',
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          // {
          //   title:'下架人',
          //   align: 'left',
          //   dataIndex: 'createName'
          // },
          // {
          //   title:'创建人登录名称',
          //   align: 'left',
          //   dataIndex: 'createBy'
          // },
          // {
          //   title:'下架时间',
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
          //   title:'更新人登录名称',
          //   align: 'left',
          //   dataIndex: 'updateBy'
          // },
          // {
          //   title:'更新日期',
          //   align: 'left',
          //   dataIndex: 'updateTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'所属部门',
          //   align: 'left',
          //   dataIndex: 'sysOrgCode'
          // },
          // {
          //   title:'所属公司',
          //   align: 'left',
          //   dataIndex: 'sysCompanyCode'
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
          // {
          //   title:'原始单据类型',
          //   align: 'left',
          //   dataIndex: 'orderType'
          // },
          // {
          //   title:'单位',
          //   align: 'left',
          //   dataIndex: 'goodsUnit'
          // },
          // {
          //   title:'生产日期',
          //   align: 'left',
          //   dataIndex: 'goodsProData'
          // },
          // {
          //   title:'批次',
          //   align: 'left',
          //   dataIndex: 'goodsBatch'
          // },
          // {
          //   title:'作业类型',
          //   align: 'left',
          //   dataIndex: 'actTypeCode'
          // },
          // {
          //   title:'基本单位',
          //   align: 'left',
          //   dataIndex: 'baseUnit'
          // },
          // {
          //   title:'基本单位数量',
          //   align: 'left',
          //   dataIndex: 'baseGoodscount'
          // },

        ],
        url: {
          list: "/jeewms/wmToDownGoods/datagridzzfh",
          delete: "/jeewms/wmToDownGoods/delete",
          deleteBatch: "/jeewms/wmToDownGoods/deleteBatch",
          exportXlsUrl: "/jeewms/wmToDownGoods/exportXls",
          importExcelUrl: "jeewms/wmToDownGoods/importExcel",
          dofubatch: "/jeewms/wmToDownGoods/dofubatch",
          editBatch: "/jeewms/wmToDownGoods/editBatch",
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
      //可编辑行改变时调用
      handleBatchChange(newValue, id, col) {
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
        console.log(this.newColData,this.newColIds)
      },
      chooseRecheck() {
        if(this.selectedRowKeys.length > 0) {
          this.batchEditFlag = true
        } else {
          this.$message.warning(this.$t('请选择一条记录!'));
        }
      },
      //批量保存
      chooseSave() {
        var that = this
        if(this.batchEditFlag) {
          putAction(that.url.editBatch, that.newColData).then((res) => {
            if (res.success) {
              that.$message.success('批量修改成功')
              that.newColData = [];
              that.newColIds = [];
              that.loadData();
              that.batchEditFlag = false;
            } else {
              that.$message.warning(this.$t('操作失败'))
            }
          })
        } else {
          this.$message.warning('无可保存内容!');
        }
      },
      //取消批量修改
      chooseCancel() {
        if(this.batchEditFlag) {
          this.newColData = [];
          this.newColIds = [];
          this.batchEditFlag = false;
          this.selectedRowKeys = [];
        } else {
          this.$message.warning('无修改内容可取消!');
        }
      },
      //一键复核
      batchRecheck() {
        if(this.selectedRowKeys.length < 1) {
          this.$message.warning(this.$t('请选择一条记录!'));
          return;
        }
        var ids = this.selectedRowKeys;
        putAction(this.url.dofubatch,ids).then((res) => {
          if (res.success) {
            this.$message.success('批量复核成功')
            this.loadData();
          } else {
            this.$message.warning(this.$t('操作失败'))
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>