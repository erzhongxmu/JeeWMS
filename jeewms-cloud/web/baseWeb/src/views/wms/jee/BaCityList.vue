<template>
  <a-card :bordered="false">

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('地区信息')">{{$t('导出')}}</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button icon="import">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
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
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{
        selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        rowKey="id"
        bordered
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :expandedRowKeys="expandedRowKeys"
        @change="handleTableChange"
        @expand="handleExpand"
        v-bind="tableProps"
        :rowClassName="setRowClsaa">

        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt=""
               style="max-width:80px;font-size: 12px;font-style: italic;"/>
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
          <a @click="handleEdit(record)">{{$t('编辑')}}</a>
          <a-divider type="vertical"/>
          <a @click="handleAddChild(record)">添加下级</a>
           <a-divider type="vertical"/>
           <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDeleteNode(record)">
                  <a>{{$t('删除')}}</a>
           </a-popconfirm>
        </span>

      </a-table>
    </div>

    <baCity-modal ref="modalForm" @ok="modalFormOk"></baCity-modal>
  </a-card>
</template>

<script>

  import { deleteAction, getAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BaCityModal from './modules/BaCityModal'
  import { commonTableRowClass } from '@/mixins/commonTableRowClass'
  import { mixinDevice } from '@/utils/mixin'

  export default {
    name: 'BaCityList',
    mixins: [JeecgListMixin,commonTableRowClass],
    components: {
      BaCityModal
    },
    data() {
      return {
        description: '地区信息管理页面',
        // 表头
        columns: [
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align: 'left',
            width:200,
            scopedSlots: { customRender: 'action' }
          },
          {
            title: '地区代码',
            align: 'left',
            dataIndex: 'cityCode'
          },
          {
            title: '地区名称',
            align: 'left',
            dataIndex: 'cityName'
          },
          {
            title: '地区助记码',
            align: 'left',
            dataIndex: 'citySerc'
          },
          {
            title: '城市类型',
            align: 'left',
            dataIndex: 'cityTypeCode_dictText'
          },
          {
            title: '片区信息',
            align: 'left',
            dataIndex: 'bareaCode_dictText'
          },
          {
            title: '大区信息',
            align: 'left',
            dataIndex: 'dareaCode_dictText'
          },
          {
            title: '停用',
            align: 'left',
            dataIndex: 'cityDel_dictText'
          }

        ],
        url: {
          list: '/jeewms/baCity/rootList',
          childList: '/jeewms/baCity/childList',
          delete: '/jeewms/baCity/delete',
          deleteBatch: '/jeewms/baCity/deleteBatch',
          exportXlsUrl: '/jeewms/baCity/exportXls',
          importExcelUrl: 'jeewms/baCity/importExcel'
        },
        expandedRowKeys: [],
        hasChildrenField: 'hasChild',
        pidField: 'pid',
        dictOptions: {},
        loadParent: false
      }
    },
    computed: {
      importExcelUrl() {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      },
      tableProps() {
        let _this = this
        return {
          // 列表项是否可选择
          rowSelection: {
            selectedRowKeys: _this.selectedRowKeys,
            onChange: (selectedRowKeys) => _this.selectedRowKeys = selectedRowKeys
          }
        }
      }
    },
    methods: {
      loadData(arg) {
        if (arg == 1) {
          this.ipagination.current = 1
        }
        this.loading = true
        this.expandedRowKeys = []
        let params = this.getQueryParams()
        return new Promise((resolve) => {
          getAction(this.url.list, params).then(res => {
            if (res.success) {
              let result = res.result
              if (Number(result.total) > 0) {
                this.ipagination.total = Number(result.total)
                this.dataSource = this.getDataByResult(res.result.records)
                resolve()
              } else {
                this.ipagination.total = 0
                this.dataSource = []
              }
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
            this.loading = false
          })
        })
      },
      getDataByResult(result) {
        return result.map(item => {
          //判断是否标记了带有子节点
          if (item[this.hasChildrenField] == '1') {
            let loadChild = { id: item.id + '_loadChild', name: 'loading...', isLoading: true }
            item.children = [loadChild]
          }
          return item
        })
      },
      handleExpand(expanded, record) {
        // 判断是否是展开状态
        if (expanded) {
          this.expandedRowKeys.push(record.id)
          if (record.children.length > 0 && record.children[0].isLoading === true) {
            let params = this.getQueryParams()//查询条件
            params[this.pidField] = record.id
            getAction(this.url.childList, params).then((res) => {
              if (res.success) {
                if (res.result && res.result.length > 0) {
                  record.children = this.getDataByResult(res.result)
                  this.dataSource = [...this.dataSource]
                } else {
                  record.children = ''
                  record.hasChildrenField = '0'
                }
              } else {
                this.$message.warning(this.$t('操作失败'))
              }
            })
          }
        } else {
          let keyIndex = this.expandedRowKeys.indexOf(record.id)
          if (keyIndex >= 0) {
            this.expandedRowKeys.splice(keyIndex, 1)
          }
        }
      },
      initDictConfig() {
      },
      modalFormOk(formData, arr) {
        if (!formData.id) {
          this.addOk(formData, arr)
        } else {
          if (!arr) {
            this.loadData()
          } else {
            this.editOk(formData, this.dataSource)
            this.dataSource = [...this.dataSource]
          }
        }
      },
      editOk(formData, arr) {
        if (arr && arr.length > 0) {
          for (let i = 0; i < arr.length; i++) {
            if (arr[i].id == formData.id) {
              arr[i] = formData
              break
            } else {
              this.editOk(formData, arr[i].children)
            }
          }
        }
      },
      async addOk(formData, arr) {
        if (!formData[this.pidField]) {
          this.loadData()
        } else {
          if (this.loadParent === true) {
            this.expandTreeNode(formData[this.pidField])
            this.loadParent = false
          } else {
            this.expandedRowKeys = []
            for (let i of arr) {
              await this.expandTreeNode(i)
            }
          }
        }
      },
      expandTreeNode(nodeId) {
        return new Promise((resolve, reject) => {
          this.getFormDataById(nodeId, this.dataSource)
          let row = this.parentFormData
          this.expandedRowKeys.push(nodeId)
          let params = this.getQueryParams()//查询条件
          params[this.pidField] = nodeId
          getAction(this.url.childList, params).then((res) => {
            if (res.success) {
              if (res.result && res.result.length > 0) {
                row.children = this.getDataByResult(res.result)
              } else {
                row.children = ''
              }
              this.dataSource = [...this.dataSource]
              resolve()
            } else {
              reject()
            }
          })
        })
      },
      getFormDataById(id, arr) {
        if (arr && arr.length > 0) {
          for (let i = 0; i < arr.length; i++) {
            if (arr[i].id == id) {
              this.parentFormData = arr[i]
            } else {
              this.getFormDataById(id, arr[i].children)
            }
          }
        }
      },
      handleAddChild(record) {
        this.loadParent = true
        let obj = {}
        obj[this.pidField] = record['id']
        this.$refs.modalForm.add(obj)
      },
      handleDeleteNode(record) {
        if (!this.url.delete) {
          this.$message.error('请设置url.delete属性!')
          return
        }
        var that = this
        deleteAction(that.url.delete, { id: record.id }).then((res) => {
          if (res.success) {
            if (!record[this.pidField] || record[this.pidField] == '0' || record[this.pidField].length == 0) {
              that.loadData(1)
            } else {
              that.$message.success(this.$t('操作成功'))
              that.expandTreeNode(record[this.pidField])
            }
          } else {
            that.$message.warning(this.$t('操作失败'))
          }
        })
      }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>