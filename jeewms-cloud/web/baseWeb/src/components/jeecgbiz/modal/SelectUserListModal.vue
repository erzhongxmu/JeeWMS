<template>
  <j-modal
    title="用户列表"
    :width="1000"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel">

    <a-table
      ref="table"
      bordered
      size="middle"
      class="j-table-force-nowrap"
      rowKey="id"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="ipagination"
      :loading="loading"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"></a-table>
  </j-modal>
</template>

<script>
  import {getUserList} from '@/api/api'
  import {JeecgListMixin} from '@/mixins/JeecgListMixin'

  export default {
    name: "SelectUserListModal",
    mixins: [JeecgListMixin],
    data() {
      return {
        title: "操作",
        visible: false,
        model: {},
        confirmLoading: false,
        url: {
          add: "/act/model/create",
          list: "/sys/user/list"
        },
        columns: [
          {
            title: '用户账号',
            align: 'left',
            dataIndex: 'username',
            fixed: 'left',
            width: 200
          },
          {
            title: '用户姓名',
            align: 'left',
            dataIndex: 'realname',
          },
          {
            title: '性别',
            align: 'left',
            dataIndex: 'sex_dictText'
          },
          {
            title: '手机号码',
            align: 'left',
            dataIndex: 'phone'
          },
          {
            title: '邮箱',
            align: 'left',
            dataIndex: 'email'
          },
          {
            title: '状态',
            align: 'left',
            dataIndex: 'status_dictText'
          }
        ]
      }
    },
    created() {
      //Step.2 加载用户数据
      getUserList().then((res) => {
        if (res.success) {
          this.dataSource = res.result.records;
          this.ipagination.total = res.result.total;
        }
      })
    },
    methods: {
      open() {
        this.visible = true;

        //Step.1 清空选中用户
        this.selectedRowKeys = []
        this.selectedRows = []
      },
      close() {
        this.$emit('close');
        this.visible = false;
      },
      handleChange(info) {
        let file = info.file;
        if (file.response.success) {
          this.$message.success(file.response.message);
          this.$emit('ok');
          this.close()
        } else {
          this.$message.warn(file.response.message);
          this.close()
        }

      },
      handleCancel() {
        this.close()
      },
      handleSubmit() {
        this.$emit('ok', this.selectionRows);
        this.close()
      },
    }
  }
</script>

<style>

</style>
