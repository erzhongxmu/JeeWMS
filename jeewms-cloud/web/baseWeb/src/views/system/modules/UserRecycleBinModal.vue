<template>
  <j-modal
    :width="1000"
    :title="title"
    :visible="innerVisible"
    @cancel="handleCancel"
    
    :okButtonProps="{style:{display:'none'}}"
  >
    <a-alert type="info" showIcon style="margin-bottom: 16px;">
      <template slot="message">
        <span>{{$t('已选择')}}</span>
        <a style="font-weight: 600;padding: 0 4px;">{{ selectedRowKeys.length }}</a>
        <span>{{$t('项目')}}</span>
        <template v-if="selectedRowKeys.length>0">
          <a-divider type="vertical"/>
          <a @click="handleClearSelection">{{$t('清空选择')}}</a>
          <a-divider type="vertical"/>
          <a @click="handleRevertBatch">{{$t('批量还原')}}</a>
          <a-divider type="vertical"/>
          <a @click="handleDeleteBatch">{{$t('批量删除')}}</a>
        </template>
      </template>
    </a-alert>

    <a-table
      ref="table"
      rowKey="id"
      size="middle"
      bordered
      :columns="columns"
      :loading="loading"
      :dataSource="dataSource"
      :pagination="false"
      :rowSelection="{selectedRowKeys, onChange: handleTableSelectChange}"
    >

      <!-- 显示头像 -->
      <template slot="avatarslot" slot-scope="text, record, index">
        <div class="anty-img-wrap">
          <a-avatar shape="square" :src="url.getAvatar(record.avatar)" icon="user"/>
        </div>
      </template>

      <span slot="action" slot-scope="text, record">
        <a @click="handleRevert([record.id])"><a-icon type="redo"/> {{$t('恢复用户')}}</a>
        <a-divider type="vertical"/>
        <a @click="handleDelete([record.id])"><a-icon type="delete"/>  {{$t('彻底删除')}}</a>
      </span>
    </a-table>

  </j-modal>
</template>

<script>
  import { putAction,deleteAction,getFileAccessHttpUrl } from "@/api/manage"

  // 高度封装的请求，请务必使用 superRequest.call(this,{}) 的方式调用
  function superRequest(options) {
    this.loading = !!options.loading
    options.promise.then(res => {
      if (res.success && typeof options.success === 'function') {
        options.success(res)
      } else {
        throw new Error(res.message)
      }
    }).catch(e => {
      console.error('查询已删除的用户失败：', e)
      // this.$message.warning('查询已删除的用户失败：' + (e.message || e))
    }).finally(() => {
      this.loading = false
    })
  }

  export default {
    name: 'UserRecycleBinModal',
    props: {
      visible: {
        type: Boolean,
        default: false
      },
    },
    data() {
      return {
        title: this.$t('回收站'),
        loading: false,
        innerVisible: false,
        selectedRowKeys: [],
        dataSource: [],
        columns: [
          { title: '#', align: 'left', key: 'rowIndex', width: 80, customRender: (t, r, i) => i + 1 },
          { title: this.$t('用户账号'), align: 'left', dataIndex: 'username' },
          { title: this.$t('用户姓名'), align: 'left', dataIndex: 'realname', },
          { title: this.$t('头像'), align: 'left', dataIndex: 'avatar', scopedSlots: { customRender: 'avatarslot' } },
          // { title: '部门', align: 'left', dataIndex: 'orgCode' },
          { title: this.$t('操作'), align: 'left', dataIndex: 'action', width: 200, scopedSlots: { customRender: 'action' } }
        ],
        url: {
          getAvatar: (path) => getFileAccessHttpUrl(`${path}`),
          // 回收站操作，get = 获取列表；put = 取回；delete = 彻底删除
          recycleBin: '/sys/user/recycleBin',
          putRecycleBin: '/sys/user/putRecycleBin',
          deleteRecycleBin: '/sys/user/deleteRecycleBin',
        },
      }
    },
    watch: {
      visible: {
        immediate: true,
        handler(val) {
          if (val) {
            this.loadData()
          }
          this.innerVisible = val
        }
      },
      innerVisible(val) {
        this.$emit('update:visible', val)
      },
    },
    methods: {
      loadData() {
        superRequest.call(this, {
          loading: true,
          promise: this.$http.get(this.url.recycleBin),
          success: res => this.dataSource = res.result
        })
      },
      handleOk() {
        this.loadData()
        this.$emit('ok')
      },
      handleCancel() {
        this.innerVisible = false
      },
      // 还原用户
      handleRevert(userIds) {
        let thit = this
        this.$confirm({
          title: thit.$t('恢复用户'),
          content: thit.$t('你确定吗?'),
          centered: true,
          onOk: () => {
            putAction(this.url.putRecycleBin,{userIds:userIds.join(',')}).then((res)=>{
              if(res.success){
                this.handleOk()
                this.handleClearSelection()
                this.$message.success(thit.$t('操作成功'))
              }
            })
          }
        })
      },
      // 彻底删除用户
      handleDelete(userIds) {
        let thit = this
        this.$confirm({
          title: thit.$t('彻底删除'),
          content: (<div>
            <p> {thit.$t('您确定要彻底删除吗?')}</p>
            <p style="color:red;">{thit.$t('注意：彻底删除后将无法恢复，请谨慎操作!')}</p>
          </div>),
          centered: true,
          onOk: () => {
            var that = this;
            deleteAction(that.url.deleteRecycleBin, {userIds: userIds.join(',')}).then((res) => {
              if (res.success) {
                this.loadData()
                this.handleClearSelection()
                this.$message.success(thit.$t('操作成功'))
              } else {
                that.$message.warning(this.$t('操作失败'));
              }
            });
          },
        })
      },
      handleRevertBatch() {
        this.handleRevert(this.selectedRowKeys)
      },
      handleDeleteBatch() {
        this.handleDelete(this.selectedRowKeys)
      },
      handleClearSelection() {
        this.handleTableSelectChange([], [])
      },
      handleTableSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys
        this.selectionRows = selectionRows
      },
    }
  }
</script>

<style lang="less" scoped></style>