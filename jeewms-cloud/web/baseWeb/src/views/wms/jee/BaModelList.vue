<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品分类名称">
              <a-input placeholder="请输入商品分类名称" v-model="queryParam.modelName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品分类编码">
              <a-input placeholder="请输入商品分类编码" v-model="queryParam.modelCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
<!--              <a @click="handleToggleSearch" style="margin-left: 8px">-->
<!--                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}-->
<!--                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>-->
<!--              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'carType:add'">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('商品分类')" v-has="'carType:export'">{{$t('导出')}}</a-button>
      <a-upload name="file" v-has="'carType:import'" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
<!--      <a-button key="1" @click="batchDel" v-has="'carType:batchDelete'"><a-icon type="delete" />{{$t('批量删除')}}</a-button>-->
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

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'carType:update'">{{$t('编辑')}}</a>
          <a-divider type="vertical" v-has="'carType:delete'"/>
<!--          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)" v-has="'carType:delete'">-->
<!--            <a>{{$t('删除')}}</a>-->
<!--          </a-popconfirm>-->
          <span v-has="'carType:stop'" v-if="record.disableStatus != 'Y'">
            <a-popconfirm title="确定停用吗?" @confirm="() => switchStatus(record.id,'Y')">
              <a>停用</a>
            </a-popconfirm>
          </span>
          <span v-has="'carType:start'" v-if="record.disableStatus == 'Y'">
            <a-popconfirm title="确定启用吗?" @confirm="() => switchStatus(record.id,'N')">
              <a>启用</a>
            </a-popconfirm>
          </span>
<!--          <a-dropdown>-->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--                <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
<!--                  <a>{{$t('删除')}}</a>-->
<!--                </a-popconfirm>-->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <baModel-modal ref="modalForm" @ok="modalFormOk"></baModel-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { putAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaModelModal from './modules/BaModelModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'

  export default {
    name: "BaModelList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass],
    components: {
      BaModelModal
    },
    data () {
      return {
        description: '商品分类管理页面',
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
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            // fixed:"right",
            width:50,
            scopedSlots: { customRender: 'action' }
          },
          {
            title:'商品分类名称',
            align: 'left',
            dataIndex: 'modelName'
          },
          {
            title:'商品分类编码',
            align: 'left',
            dataIndex: 'modelCode'
          },
          {
            title: '状态',
            align: 'left',
            dataIndex: 'disableStatus',
            customRender:function (t,r,index) {
              return t!='Y'?'正常':'停用';
            }
          },
          {
            title:'备注',
            align: 'left',
            dataIndex: 'remarks'
          },
          // {
          //   title:'BOM数据（扩展）',
          //   align: 'left',
          //   dataIndex: 'bom'
          // }
        ],
        url: {
          list: "/jeewms/baModel/list",
          delete: "/jeewms/baModel/delete",
          edit: "/jeewms/baModel/edit",
          deleteBatch: "/jeewms/baModel/deleteBatch",
          exportXlsUrl: "/jeewms/baModel/exportXls",
          importExcelUrl: "jeewms/baModel/importExcel",
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
      },
      switchStatus(id,status) {
        let that = this
        var param = {
          id: id,
          disableStatus: status
        }
        putAction(this.url.edit,param).then((res)=>{
          if(res.success){
            that.$message.success(this.$t('操作成功'));
            that.loadData()
          }else{
            that.$message.warning(this.$t('操作失败'));
          }
        })
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>