<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('来源编号')">
              <a-input :placeholder="$t('请输入来源编号')" v-model="queryParam.costSoNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('来源名称')">
              <a-input :placeholder="$t('请输入来源名称')" v-model="queryParam.costSoName"></a-input>
            </a-form-item>
          </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('来源类型编号')">
                <!-- <j-popup
                  :placeholder="$t('请选择来源类型编号')"
                  v-model="queryParam.costSNo"
                  code="bms_cs_type"
                  org-fields="cost_s_no,cost_s_name,cost_s_desc"
                  dest-fields="costSNo,costSName,costSDesc"
                  :field="getPopupField('costSNo,costSName,costSDesc')"
                  :multi="true"
                /> -->
                <a-input :placeholder="$t('请输入来源类型编号')" v-model="queryParam.costSNo"></a-input>
              </a-form-item>
            </a-col>
            <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('来源类型名称')">
                <a-input :placeholder="$t('请输入来源类型名称')" v-model="queryParam.costSName"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
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
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('计费来源')">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query
        :fieldList="superFieldList"
        ref="superQueryModal"
        @handleSuperQuery="handleSuperQuery"
      ></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete" />{{$t('删除')}}
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          {{$t('批量操作')}}
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
      <a-button type="primary" icon="mail" @click="costCalculate">{{$t('费用计算')}}</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项')}}
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
        @change="handleTableChange"
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
          <a v-else  @click="downloadFileList(text)" >{{$t('下载')}}</a>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <!-- <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
          <a @click="handleDetail(record)">{{$t('详情')}}</a>
          <a-divider type="vertical" />
          <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>

    <bms-cost-source-modal ref="modalForm" @ok="modalFormOk"></bms-cost-source-modal>
    <bms-cost-source-modal2 ref="modalForm2" @ok="modalFormOk"></bms-cost-source-modal2>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BmsCostSourceModal from './modules/BmsCostSourceModal'
import BmsCostSourceModal2 from './modules/BmsCostSourceModal2'

export default {
  name: 'BmsCostSourceList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    BmsCostSourceModal,
    BmsCostSourceModal2
  },
  data() {
    return {
      description: '计费来源管理页面',
      // 表头
      columns: [
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key:'rowIndex',
        //   width:60,
        //   align:"center",
        //   customRender:function (t,r,index) {
        //     return parseInt(index)+1;
        //   }
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'center',
          fixed: 'left',
          width: 147,
          scopedSlots: { customRender: 'action' }
        },
        {
          title: this.$t('来源编号'),
          align: 'center',
          dataIndex: 'costSoNo'
        },
        {
          title: this.$t('来源名称'),
          align: 'center',
          dataIndex: 'costSoName'
        },
        {
          title: this.$t('来源类型编号'),
          align: 'center',
          dataIndex: 'costSNo'
        },
        {
          title: this.$t('来源类型名称'),
          align: 'center',
          dataIndex: 'costSName'
        },
        {
          title: this.$t('来源类型描述'),
          align: 'center',
          dataIndex: 'costSDesc'
        },
        {
          title: this.$t('计费对象类型'),
          align: 'center',
          dataIndex: 'costObjType'
        },
        {
          title: this.$t('计费对象编号'),
          align: 'center',
          dataIndex: 'costObjNo'
        },
        {
          title: this.$t('计费对象名称'),
          align: 'center',
          dataIndex: 'costObjName'
        },
        {
          title: this.$t('来源数量'),
          align: 'center',
          dataIndex: 'costSoSum'
        },
        {
          title: this.$t('来源单位'),
          align: 'center',
          dataIndex: 'costSoUnit'
        },
        {
          title: this.$t('来源日期'),
          align: 'center',
          dataIndex: 'costSoDate',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: this.$t('附件单号'),
          align: 'center',
          dataIndex: 'accessoryNo',
          scopedSlots: { customRender: 'fileSlot' },
        },
        {
          title: this.$t('状态'),
          align: 'center',
          dataIndex: 'status'
        }
      ],
      url: {
        list: '/bms/bmsCostSource/list',
        delete: '/bms/bmsCostSource/delete',
        deleteBatch: '/bms/bmsCostSource/deleteBatch',
        exportXlsUrl: '/bms/bmsCostSource/exportXls',
        importExcelUrl: 'bms/bmsCostSource/importExcel'
      },
      dictOptions: {},
      superFieldList: []
    }
  },
  created() {
    this.getSuperFieldList()
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'costSoNo', text: '来源编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costSoName', text: '来源名称', dictCode: '' })
      fieldList.push({
        type: 'popup',
        value: 'costSNo',
        text: '来源类型编号',
        popup: { code: 'bms_cs_type', field: 'cost_s_no', orgFields: 'cost_s_no', destFields: 'costSNo' }
      })
      fieldList.push({ type: 'string', value: 'costSName', text: '来源类型名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costSDesc', text: '来源类型描述', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costObjType', text: '计费对象类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costObjNo', text: '计费对象编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costObjName', text: '计费对象名称', dictCode: '' })
      fieldList.push({ type: 'BigDecimal', value: 'costSoSum', text: '来源数量', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costSoUnit', text: '来源单位', dictCode: '' })
      fieldList.push({ type: 'date', value: 'costSoDate', text: '来源日期' })
      fieldList.push({ type: 'string', value: 'status', text: '状态', dictCode: '' })
      this.superFieldList = fieldList
    },
    costCalculate() {
      this.$refs.modalForm2.list()
      this.$refs.modalForm2.title = '计费规则'
      this.$refs.modalForm2.disableSubmit = false
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>