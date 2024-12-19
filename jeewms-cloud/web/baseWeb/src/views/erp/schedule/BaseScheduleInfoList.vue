<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('质检人')">
              <a-input :placeholder="$t('请输入质检人')" v-model="queryParam.userNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('排班日期')">
              <a-input :placeholder="$t('请输入排班日期')" v-model="queryParam.planDate"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('安排时间')">
              <a-input :placeholder="$t('请输入安排时间')" v-model="queryParam.attr4"></a-input>
            </a-form-item>
          </a-col>
          
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'SampleMakingOrder:search'">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" v-has="'SampleMakingOrder:reset'">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <!-- <a-button type="primary" icon="download" @click="handleExportXls('base_schedule_info')">导出</a-button> -->
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>
      </a-dropdown>
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
        :scroll="{x:true}"
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
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a v-has="'BaseScheduleInfoList:handleEdit'" @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item v-has="'BaseScheduleInfoList:delete'">
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <base-schedule-info-modal ref="modalForm" @ok="modalFormOk"></base-schedule-info-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaseScheduleInfoModal from './modules/BaseScheduleInfoModal'

  export default {
    name: 'BaseScheduleInfoList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BaseScheduleInfoModal
    },
    data () {
      return {
        description: 'base_schedule_info管理页面',
        // 表头
        columns: [
          // {
          //   title:'whNo',
          //   align:"center",
          //   dataIndex: 'whNo'
          // },
          {
            title:this.$t('用户'),
            align:"center",
            dataIndex: 'userNo',
            sorter: true,
          },
          // {
          //   title:'userName',
          //   align:"center",
          //   dataIndex: 'userName'
          // },
          // {
          //   title:'userSkill',
          //   align:"center",
          //   dataIndex: 'userSkill'
          // },
          // {
          //   title:'orgCode',
          //   align:"center",
          //   dataIndex: 'orgCode'
          // },
          // {
          //   title:'priority',
          //   align:"center",
          //   dataIndex: 'priority'
          // },
          // {
          //   title:'groupType',
          //   align:"center",
          //   dataIndex: 'groupType'
          // },
          {
            title:this.$t('排班日期'),
            align:"center",
            dataIndex: 'planDate',
            sorter: true,
          },
          // {
          //   title:'planDateTime',
          //   align:"center",
          //   dataIndex: 'planDateTime'
          // },
          // {
          //   title:'occupyTime',
          //   align:"center",
          //   dataIndex: 'occupyTime'
          // },
          // {
          //   title:'freeTime',
          //   align:"center",
          //   dataIndex: 'freeTime'
          // },
          // {
          //   title:'isRest',
          //   align:"center",
          //   dataIndex: 'isRest'
          // },
          // {
          //   title:'linkId',
          //   align:"center",
          //   dataIndex: 'linkId'
          // },
          // {
          //   title:'lineItemNo',
          //   align:"center",
          //   dataIndex: 'lineItemNo'
          // },
          {
            title:this.$t('地点'),
            align:"center",
            dataIndex: 'attr1',
            sorter: true,
            customCell: () => {
            return {
              style: {
                width: '300px',
                overflow: 'hidden',
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                cursor: 'pointer',
              },
            }
          },
          customRender: (text, record) => (
            <a-tooltip placement="topLeft" title={text}>
              {text ? (text.length > 20 ? text.substring(0, 20) + '...' : text) : ''}
            </a-tooltip>
          )
          },
          {
            title:this.$t('工作内容'),
            align:"center",
            dataIndex: 'attr2',
            sorter: true,
          },
          {
            title:this.$t('安排人'),
            align:"center",
            dataIndex: 'attr3',
            sorter: true,
          },
          {
            title:this.$t('安排时间'),
            align:"center",
            dataIndex: 'attr4',
            sorter: true,
          },
          {
            title:this.$t('人员'),
            align:"center",
            dataIndex: 'attr5',
            sorter: true,
          },
          // {
          //   title:'workClass',
          //   align:"center",
          //   dataIndex: 'workClass'
          // },
          // {
          //   title:'orgCodeName',
          //   align:"center",
          //   dataIndex: 'orgCodeName'
          // },
          // {
          //   title:'planDay',
          //   align:"center",
          //   dataIndex: 'planDay'
          // },
          // {
          //   title:'tenantId',
          //   align:"center",
          //   dataIndex: 'tenantId'
          // },
          // {
          //   title:'pbType',
          //   align:"center",
          //   dataIndex: 'pbType'
          // },
          {
            title:this.$t( '操作'),
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/sys/baseScheduleInfo/list",
          delete: "/sys/baseScheduleInfo/delete",
          deleteBatch: "/sys/baseScheduleInfo/deleteBatch",
          exportXlsUrl: "/sys/baseScheduleInfo/exportXls",
          importExcelUrl: "sys/baseScheduleInfo/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'whNo',text:'whNo'})
        fieldList.push({type:'string',value:'userNo',text:'userNo'})
        fieldList.push({type:'string',value:'userName',text:'userName'})
        fieldList.push({type:'string',value:'userSkill',text:'userSkill'})
        fieldList.push({type:'string',value:'orgCode',text:'orgCode'})
        fieldList.push({type:'string',value:'priority',text:'priority'})
        fieldList.push({type:'string',value:'groupType',text:'groupType'})
        fieldList.push({type:'string',value:'planDate',text:'planDate'})
        fieldList.push({type:'string',value:'planDateTime',text:'planDateTime'})
        fieldList.push({type:'int',value:'occupyTime',text:'occupyTime'})
        fieldList.push({type:'int',value:'freeTime',text:'freeTime'})
        fieldList.push({type:'string',value:'isRest',text:'isRest'})
        fieldList.push({type:'string',value:'linkId',text:'linkId'})
        fieldList.push({type:'string',value:'lineItemNo',text:'lineItemNo'})
        fieldList.push({type:'string',value:'attr1',text:'attr1'})
        fieldList.push({type:'string',value:'attr2',text:'attr2'})
        fieldList.push({type:'string',value:'attr3',text:'attr3'})
        fieldList.push({type:'string',value:'attr4',text:'attr4'})
        fieldList.push({type:'string',value:'attr5',text:'attr5'})
        fieldList.push({type:'string',value:'workClass',text:'workClass'})
        fieldList.push({type:'string',value:'orgCodeName',text:'orgCodeName'})
        fieldList.push({type:'int',value:'planDay',text:'planDay'})
        fieldList.push({type:'string',value:'tenantId',text:'tenantId'})
        fieldList.push({type:'string',value:'pbType',text:'pbType'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>