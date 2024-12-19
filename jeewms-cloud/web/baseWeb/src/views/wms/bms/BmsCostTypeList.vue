<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('类型编号')">
              <a-input :placeholder="$t('请输入类型编号')" v-model="queryParam.costTypeNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('类型名称')">
              <a-input :placeholder="$t('请输入类型名称')" v-model="queryParam.costTypeName"></a-input>
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
      <a-button type="primary" icon="download" @click="handleExportXls('计费类型')">{{$t('导出')}}</a-button>
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

    <bms-cost-type-modal ref="modalForm" @ok="modalFormOk"></bms-cost-type-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BmsCostTypeModal from './modules/BmsCostTypeModal'

export default {
  name: 'BmsCostTypeList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    BmsCostTypeModal
  },
  data() {
    return {
      description: '计费类型管理页面',
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
          title: this.$t('类型编号'),
          align: 'center',
          dataIndex: 'costTypeNo'
        },
        {
          title: this.$t('类型名称'),
          align: 'center',
          dataIndex: 'costTypeName'
        },
        {
          title: this.$t('类型单位'),
          align: 'center',
          dataIndex: 'costTypeUnit'
        },
        {
          title: this.$t('备注'),
          align: 'center',
          dataIndex: 'remark'
        }
      ],
      url: {
        list: '/bms/bmsCostType/list',
        delete: '/bms/bmsCostType/delete',
        deleteBatch: '/bms/bmsCostType/deleteBatch',
        exportXlsUrl: '/bms/bmsCostType/exportXls',
        importExcelUrl: 'bms/bmsCostType/importExcel'
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
      fieldList.push({ type: 'string', value: 'costTypeNo', text: '类型编号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costTypeName', text: '类型名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'costTypeUnit', text: '类型单位', dictCode: '' })
      fieldList.push({ type: 'Text', value: 'remark', text: '备注', dictCode: '' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>