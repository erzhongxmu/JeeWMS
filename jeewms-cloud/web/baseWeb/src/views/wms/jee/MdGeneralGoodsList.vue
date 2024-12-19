<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="所属部门">
              <j-dict-select-tag placeholder="请选择所属部门" v-model="queryParam.sysOrgCode" dictCode="sys_depart,depart_name,id"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="所属公司">
              <j-dict-select-tag placeholder="请选择所属公司" v-model="queryParam.sysCompanyCode" dictCode="ba_com,com_name,com_code"/>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="所属货主">
                <j-dict-select-tag placeholder="请选择所属货主" v-model="queryParam.suoShuKeHu" dictCode="md_cus,zhong_wen_qch,ke_hu_bian_ma"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品名称">
                <a-input placeholder="请输入商品名称" v-model="queryParam.shpMingCheng"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="单位">
                <j-dict-select-tag placeholder="请选择单位" v-model="queryParam.shlDanWei" dictCode="ba_unit,unit_name,unit_code"/>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="货主名称">
                <a-input placeholder="请输入货主名称" v-model="queryParam.cusName"></a-input>
              </a-form-item>
            </a-col>
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
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('商品')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
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
          <a @click="handleCheck(record)">{{$t('查看')}}</a>
          <a-divider type="vertical"/>
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
<!--          <a-divider type="vertical" />-->
<!--                <a @click="openPrint(record)">{{$t('打印')}}</a>-->
<!--          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <mdGeneralGoods-modal ref="modalForm" @ok="modalFormOk"></mdGeneralGoods-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MdGeneralGoodsModal from './modules/MdGeneralGoodsModal'
  import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { listCheckMixin } from '@/mixins/listCheckMixin'

  export default {
    name: "MdGoodsList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass,listCheckMixin],
    components: {
      JDictSelectTag,
      MdGeneralGoodsModal
    },
    data () {
      return {
        description: '商品管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            width:200,
            // fixed:"right",
            scopedSlots: { customRender: 'action' }
          },
          {
            title:'商品名称',
            align: 'left',
            dataIndex: 'shpMingCheng'
          },
          // {
          //   title:'商品编码',
          //   align: 'left',
          //   dataIndex: 'shpBianMa'
          // },
          {
            title:'商品型号',
            align: 'left',
            dataIndex: 'shpXingHao'
          },
          {
            title:'商品条码',
            align: 'left',
            dataIndex: 'shpTiaoMa'
          },
          {
            title:'保质期',
            align: 'left',
            dataIndex: 'bzhiQi'
          },
          {
            title:'单位',
            align: 'left',
            dataIndex: 'shlDanWei_dictText'
          },
          // {
          //   title:'体积',
          //   align: 'left',
          //   dataIndex: 'tiJiCm'
          // },
          // {
          //   title:'净重',
          //   align: 'left',
          //   dataIndex: 'zhlKg'
          // },
          {
            title:'商品描述',
            align: 'left',
            dataIndex: 'shpMiaoShu'
          },
          {
            title:'停用',
            align: 'left',
            dataIndex: 'zhuangTai_dictText'
          },
          {
            title:'货主名称',
            align: 'left',
            dataIndex: 'cusName'
          },
        ],
        url: {
          list: "/jeewms/mdGoods/list",
          delete: "/jeewms/mdGoods/delete",
          deleteBatch: "/jeewms/mdGoods/deleteBatch",
          exportXlsUrl: "/jeewms/mdGoods/exportXls",
          importExcelUrl: "jeewms/mdGoods/importExcel",
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
      initDictConfig(){
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>