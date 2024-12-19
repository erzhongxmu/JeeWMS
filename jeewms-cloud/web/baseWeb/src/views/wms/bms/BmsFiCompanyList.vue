<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('公司编号')">
              <a-input :placeholder="$t('请输入公司编号')" v-model="queryParam.comfiNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('公司名称')">
              <a-input :placeholder="$t('请输入公司名称')" v-model="queryParam.comfiName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('营业执照号码')">
              <a-input :placeholder="$t('请输入营业执照号码')" v-model="queryParam.comfiZhucehao"></a-input>
            </a-form-item>
          </a-col>

          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
              <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开') }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('新增')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('账单主体')">{{$t('导出')}}</a-button>
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
          <a v-else  @click="downloadFile(text)" >{{$t('下载')}}</a>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>
          <a-divider type="vertical" />
          <a @click="handleDetail(record)">{{$t('详情')}}</a>

          <!-- <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
        </span>
      </a-table>
    </div>

    <bms-fi-company-modal ref="modalForm" @ok="modalFormOk"></bms-fi-company-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BmsFiCompanyModal from './modules/BmsFiCompanyModal'

export default {
  name: 'BmsFiCompanyList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    BmsFiCompanyModal
  },
  data() {
    return {
      description: '账单主体管理页面',
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
          title: this.$t('公司编号'),
          align: 'center',
          dataIndex: 'comfiNo'
        },
        {
          title: this.$t('公司地址'),
          align: 'center',
          dataIndex: 'comfiAddr'
        },
        {
          title: this.$t('公司名称'),
          align: 'center',
          dataIndex: 'comfiName'
        },
        {
          title: this.$t('营业执照号码'),
          align: 'center',
          dataIndex: 'comfiZhucehao'
        },
        {
          title: this.$t('银行账号'),
          align: 'center',
          dataIndex: 'comfiBankid'
        },
        {
          title: this.$t('电话'),
          align: 'center',
          dataIndex: 'comfiTel'
        },
        {
          title: this.$t('开户行'),
          align: 'center',
          dataIndex: 'comfiBankname'
        },
        {
          title: this.$t('logo'),
          align: 'center',
          dataIndex: 'logoFile',
          scopedSlots: { customRender: 'fileSlot' }
        },
        {
          title: this.$t('运营章'),
          align: 'center',
          dataIndex: 'comfiDzyz',
          scopedSlots: { customRender: 'fileSlot' }
        },
        {
          title: this.$t('备注1'),
          align: 'center',
          dataIndex: 'comfiBeizhu1'
        },
        {
          title: this.$t('备注2'),
          align: 'center',
          dataIndex: 'comfiBeizhu2'
        },
        {
          title: this.$t('备注3'),
          align: 'center',
          dataIndex: 'comfiBeizhu3'
        }
      ],
      url: {
        list: '/bms/bmsFiCompany/list',
        delete: '/bms/bmsFiCompany/delete',
        deleteBatch: '/bms/bmsFiCompany/deleteBatch',
        exportXlsUrl: '/bms/bmsFiCompany/exportXls',
        importExcelUrl: 'bms/bmsFiCompany/importExcel'
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
      fieldList.push({ type: 'string', value: 'comfiNo', text: '公司编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiAddr', text: '公司地址', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiName', text: '公司名字', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiZhucehao', text: '营业执照号码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiBankid', text: '银行账号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiTel', text: '电话', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiBankname', text: '开户行', dictCode: '' })
      fieldList.push({ type: 'string', value: 'logoFile', text: 'logo', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiDzyz', text: '运营章', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiBeizhu1', text: '备注1', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiBeizhu2', text: '备注2', dictCode: '' })
      fieldList.push({ type: 'string', value: 'comfiBeizhu3', text: '备注3', dictCode: '' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>