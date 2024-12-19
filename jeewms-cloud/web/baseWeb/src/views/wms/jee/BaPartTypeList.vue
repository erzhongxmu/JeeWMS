<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('分类名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.typeName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('分类名称英文')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.attr3"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('分类编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.attr4"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('海关编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.attr5"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('用途')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.attr6"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('所属部门')">
              <j-search-select-tag
                type="list"
                v-model="queryParam.sysOrgCode"
                :trigger-change="true"
                dict="department"
                :placeholder="$t('请选择')"
              />
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'partClassify:add'">{{$t('添加')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('商品分类')" v-has="'partClassify:export'">{{$t('导出')}}</a-button>
      <a-upload name="file" v-has="'partClassify:import'" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button type="primary" icon="download" @click="handleExportModal('商品分类')" v-has="'partClassify:exportModal'">{{$t('模板下载')}}</a-button>
      <a-button key="1" @click="batchDel" v-has="'partClassify:batchDelete'">
        <a-icon type="delete"/>
        {{$t('批量删除')}}
      </a-button>
<!--      <a-button key="1" @click="batchDel" v-has="''"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>-->

      <!-- <a-button @click="binduser" type="primary" icon="plus">绑定用户</a-button> -->
<!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
<!--        <a-menu slot="overlay">-->
<!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
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
          <a @click="handleEdit(record)" v-has="'partClassify:update'">{{$t('编辑')}}</a>
          
          <a-divider type="vertical"  v-has="'partClassify:update'"/>
          <span v-has="'partClassify:stop'" v-if="record.attr1 != 'Y'">
            <a-popconfirm :title="$t('分类名称')" @confirm="() => switchStatus(record.id,'Y')">
              <a>{{$t('停用')}}</a>
            </a-popconfirm>
          </span>
          <span v-has="'partClassify:start'" v-if="record.attr1 == 'Y'">
            <a-popconfirm :title="$t('分类名称')" @confirm="() => switchStatus(record.id,'N')">
              <a>{{$t('启用')}}</a>
            </a-popconfirm>
          </span>
          <a-divider type="vertical" v-has="'partClassify:delete'"/>
          <a-popconfirm v-has="'partClassify:delete'" :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
            <a >{{$t('删除')}}</a>
          </a-popconfirm>
         <!-- <a-popconfirm :title="$t('你确定吗?')" v-has="'partClassify:delete'" @confirm="() => handleDelete(record.id)">
           <a>{{$t('删除')}}</a>
         </a-popconfirm> -->
         <!-- <a-dropdown> -->
<!--            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>-->
<!--            <a-menu slot="overlay">-->
<!--              <a-menu-item>-->
<!--              </a-menu-item>-->
<!--            </a-menu>-->
<!--          </a-dropdown>-->
        </span>

      </a-table>
    </div>

    <baPartType-modal ref="modalForm" @ok="modalFormOk"></baPartType-modal>

    <j-modal :visible="visible1" title="选择用户" @ok="handleBinduser" :width='800' @cancel="handleCancel">
      <div style='display: flex;align-items: center;margin-bottom: 20px'>
        <span>请选择用户：</span>
        <span><j-select-multi-user v-model="users" style="width:300px;"></j-select-multi-user></span>
      </div>
    </j-modal>
    <export-spin v-if="exportOk"></export-spin>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { putAction,downFile } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaPartTypeModal from './modules/BaPartTypeModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import JSelectMultiUser from '@/components/jeecgbiz/JSelectMultiUser'
  import ExportSpin from '@/components/ExportSpin/ExportSpin'

  export default {
    name: "BaPartTypeList",
    mixins:[JeecgListMixin, mixinDevice,commonTableRowClass],
    components: {
      BaPartTypeModal,
     JSelectMultiUser,
      ExportSpin
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
            title:this.$t('分类名称'),
            align: 'left',
            dataIndex: 'typeName',
            sorter: true
          },
          {
            title:this.$t('分类名称英文'),
            align: 'left',
            dataIndex: 'attr3',
            sorter: true
          },
          {
            title:this.$t('分类编码'),
            align: 'left',
            dataIndex: 'attr4',
            sorter: true
          },
          {
            title:this.$t('海关编码'),
            align: 'left',
            dataIndex: 'attr5',
            sorter: true
          },
          {
            title:this.$t('用途'),
            align: 'left',
            dataIndex: 'attr6',
            sorter: true
          },
          {
            title:this.$t('所属部门'),
            align: 'left',
            dataIndex: 'sysOrgCode',
            sorter: true
          },
          // {
          //   title:'堆码极限',
          //   align: 'left',
          //   dataIndex: 'stackLimit'
          // },
          // {
          //   title:'最大库存量(件)',
          //   align: 'left',
          //   dataIndex: 'maxStock'
          // },
          {
            title: this.$t('状态'),
            align: 'left',
            dataIndex: 'attr1',
            customRender: (t,r,index) => {
              return t=='Y'?this.$t('停用'):this.$t('启用');
            },
            sorter: true
          },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            // fixed:"right",
            width:100,
            scopedSlots: { customRender: 'action' }
          }
          // {
          //   title:'已绑定用户',
          //   align: 'left',
          //   dataIndex: 'attr2'
          // },
        ],
        url: {
          list: "/jeewms/baPartType/list",
          delete: "/jeewms/baPartType/delete",
          edit: "/jeewms/baPartType/edit",
          deleteBatch: "/jeewms/baPartType/deleteBatch",
          exportXlsUrl: "/jeewms/baPartType/exportXls",
          importExcelUrl: "jeewms/baPartType/importExcel",
          downloadXls: "/jeewms/baPartType/downloadXls",
        },
        dictOptions:{},
        visible1:false,
        users:''
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      handleCancel(){
        this.visible1=false;
      },
      initDictConfig(){
      },
      binduser(){
        if(this.selectedRowKeys.length == 0){
          this.$message.error("请选择需要绑定的商品类型")
          return
        }
        this.users='';
        this.visible1=true;
      },
      handleBinduser(){
        if(this.selectedRowKeys.length == 0){
          this.$message.error("请选择需要绑定的商品类型")
          return
        }
        if(!this.users){
          this.$message.error("请选择需要绑定的用户")
          return
        }
        let that = this;
        this.$http.post('/jeewms/wmsUserPartType/add',{
            userName:that.users,
            partTypeId:that.selectedRowKeys.join()
        }).then(res=>{
          if (res.success){
            that.$message.success("保存成功")
            that.handleCancel();
          }else{
            that.$message.error(this.$t('操作失败'))
          }
        })
      },
      switchStatus(id,status) {
        let that = this
        var param = {
          id: id,
          attr1: status
        }
        putAction(this.url.edit,param).then((res)=>{
          if(res.success){
            that.$message.success(this.$t('操作成功'));
            that.loadData()
          }else{
            that.$message.warning(this.$t('操作失败'));
          }
        })
      },
      handleExportModal(fileName){
        this.exportOk = true
        console.log(this.exportOk)
        if(!fileName || typeof fileName != "string"){
          fileName = "导出文件"
        }
        let myparam = {};
        // Object.assign(myparam,this.queryParam);
        // for(var mkey in myparam){
        //   if(myparam[mkey]!=null && myparam[mkey]!='' && myparam[mkey].indexOf("*") == -1){
        //     myparam[mkey] = '*'+myparam[mkey]+'*';
        //   }
        // }
        //
        let param = {...myparam};
        console.log("导出参数",param)
        try {
          downFile(this.url.downloadXls,param).then((data)=>{
            if (!data) {
              this.$message.warning("文件下载失败")
              this.exportOk = false
              return
            }
            if (typeof window.navigator.msSaveBlob !== 'undefined') {
              window.navigator.msSaveBlob(new Blob([data],{type: 'application/vnd.ms-excel'}), fileName+'.xls')
            }else{
              let url = window.URL.createObjectURL(new Blob([data],{type: 'application/vnd.ms-excel'}))
              let link = document.createElement('a')
              link.style.display = 'none'
              link.href = url
              link.setAttribute('download', fileName+'.xls')
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link); //下载完成移除元素
              window.URL.revokeObjectURL(url); //释放掉blob对象
            }
            this.exportOk = false
            console.log(this.exportOk)
          }).finally(() =>{
            this.exportOk = false
          })
        } catch (e) {
          this.exportOk = false
        }
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>