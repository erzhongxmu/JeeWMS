<template>
  <j-modal
    :width="modalWidth"
    :visible="visible"
    :title="title"
    switchFullscreen
    @ok="handleSubmit"
    @cancel="close"
    style="top:50px"
    
  >
    <a-row :gutter="10" style="background-color: #ececec; padding: 10px; margin: -10px">
      <!-- <a-col :md="6" :sm="24">
        <a-card :bordered="false">
          <a-directory-tree
            selectable
            :selectedKeys="selectedDepIds"
            :checkStrictly="true"
            :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
            :treeData="departTree"
            :expandAction="false"
            :expandedKeys.sync="expandedKeys"
            @select="onDepSelect"
          />
        </a-card>
      </a-col> -->
      <a-col :md="24" :sm="24">
        <a-card :bordered="false">
          {{$t('用户账号')}}:
          <a-input-search
            :style="{width:'150px',marginBottom:'15px'}"
            :placeholder="$t('请选择')"
            v-model="queryParam.username"
            @search="onSearch"
          ></a-input-search>
          <a-button @click="searchReset(1)" style="margin-left: 20px" icon="redo">{{$t('重置')}}</a-button>
          <!--用户列表-->
          <a-table
            ref="table"
            :scroll="scrollTrigger"
            size="middle"
            class="j-table-force-nowrap"
            rowKey="id"
            :columns="columns"
            :dataSource="dataSource"
            :pagination="ipagination"
            :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange,type: getType}"
            :loading="loading"
            @change="handleTableChange">
          </a-table>
        </a-card>
      </a-col>
    </a-row>
  </j-modal>
</template>

<script>
  import {filterObj} from '@/utils/util'
  import {queryDepartTreeList, getUserList, queryUserByDepId} from '@/api/api'

  export default {
    name: 'JSelectUserByDepModal',
    components: {},
    props: ['modalWidth', 'multi', 'userIds'],
    data() {
      return {
        queryParam: {
          username: "",
        },
        columns: [
          {
            title: this.$t('用户账号'),
            align: 'left',
            dataIndex: 'username'
          },
          {
            title: this.$t('用户姓名'),
            align: 'left',
            dataIndex: 'realname'
          },
          {
            title: this.$t('性别'),
            align: 'left',
            dataIndex: 'sex',
            customRender:  (text)=> {
              if (text === 1) {
                return this.$t('男')
              } else if (text === 2) {
                return this.$t('女')
              } else {
                return text
              }
            }
          },
          {
            title: this.$t('联系方式'),
            align: 'left',
            dataIndex: 'phone'
          },
          // {
          //   title: '部门',
          //   align: 'left',
          //   dataIndex: 'orgCodeTxt'
          // }
        ],
        scrollTrigger: {},
        dataSource: [],
        selectedRowKeys: [],
        selectUserRows: [],
        selectUserIds: [],
        title: this.$t('用户'),
        ipagination: {
          current: 1,
          pageSize: 10,
          pageSizeOptions: ['10', '20', '30'],
          showTotal: (total, range) => {
            return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' '  + total + ' ' + this.$t('项目')
          },
          showQuickJumper: true,
          showSizeChanger: true,
          total: 0
        },
        isorter: {
          column: 'createTime',
          order: 'desc'
        },
        selectedDepIds: [],
        departTree: [],
        visible: false,
        form: this.$form.createForm(this),
        loading: false,
        expandedKeys: [],
      }
    },
    computed: {
      // 计算属性的 getter
      getType: function () {
        return this.multi == true ? 'checkbox' : 'radio';
      }
    },
    watch: {
      userIds: {
        immediate: true,
        handler() {
          this.initUserNames()
        }
      },
    },
    created() {
      // 该方法触发屏幕自适应
      this.resetScreenSize();
      this.loadData()
    },
    methods: {
      initUserNames() {
        if (this.userIds) {
          // 这里最后加一个 , 的原因是因为无论如何都要使用 in 查询，防止后台进行了模糊匹配，导致查询结果不准确
          let values = this.userIds.split(',') + ','
          getUserList({
            username: values,
            pageNo: 1,
            pageSize: values.length
          }).then((res) => {
            if (res.success) {
              let selectedRowKeys = []
              let realNames = []
              res.result.records.forEach(user => {
                realNames.push(user['realname'])
                selectedRowKeys.push(user['id'])
              })
              this.selectedRowKeys = selectedRowKeys
              this.$emit('initComp', realNames.join(','))
            }
          })
        } else {
          // JSelectUserByDep组件bug issues/I16634
          this.$emit('initComp', '')
        }
      },
      async loadData(arg) {
        if (arg === 1) {
          this.ipagination.current = 1;
        }
        if (this.selectedDepIds && this.selectedDepIds.length > 0) {
          await this.initQueryUserByDepId(this.selectedDepIds)
        } else {
          this.loading = true
          let params = this.getQueryParams()//查询条件
          await getUserList(params).then((res) => {
            if (res.success) {
              this.dataSource = res.result.records
              this.ipagination.total = res.result.total
            }
          }).finally(() => {
            this.loading = false
          })
        }
      },
      // 触发屏幕自适应
      resetScreenSize() {
        let screenWidth = document.body.clientWidth;
        if (screenWidth < 500) {
          this.scrollTrigger = {x: 800};
        } else {
          this.scrollTrigger = {};
        }
      },
      showModal() {
        this.visible = true;
        this.queryDepartTree();
        this.initUserNames()
        this.loadData();
        // this.form.resetFields();
      },
      getQueryParams() {
        let param = Object.assign({}, this.queryParam, this.isorter);
        param.field = this.getQueryField();
        param.pageNo = this.ipagination.current;
        param.pageSize = this.ipagination.pageSize;
        return filterObj(param);
      },
      getQueryField() {
        let str = 'id,';
        for (let a = 0; a < this.columns.length; a++) {
          str += ',' + this.columns[a].dataIndex;
        }
        return str;
      },
      searchReset(num) {
        let that = this;
        if (num !== 0) {
          that.queryParam = {};
          that.loadData(1);
        }
        that.selectedRowKeys = [];
        that.selectUserIds = [];
        that.selectedDepIds = [];
      },
      close() {
        this.searchReset(0);
        this.visible = false;
      },
      handleTableChange(pagination, filters, sorter) {
        //TODO 筛选
        if (Object.keys(sorter).length > 0) {
          this.isorter.column = sorter.field;
          this.isorter.order = 'ascend' === sorter.order ? 'asc' : 'desc';
        }
        this.ipagination = pagination;
        this.loadData();
      },
      handleSubmit() {
        let that = this;
        this.getSelectUserRows();
        that.$emit('ok', that.selectUserRows, that.selectUserIds);
        that.searchReset(0)
        that.close();
      },
      //获取选择用户信息
      getSelectUserRows(rowId) {
        let dataSource = this.dataSource;
        let userIds = "";
        this.selectUserRows = [];
        for (let i = 0, len = dataSource.length; i < len; i++) {
          if (this.selectedRowKeys.includes(dataSource[i].id)) {
            this.selectUserRows.push(dataSource[i]);
            userIds = userIds + "," + dataSource[i].username
          }
        }
        this.selectUserIds = userIds.substring(1);
      },
      // 点击树节点,筛选出对应的用户
      onDepSelect(selectedDepIds) {
        if (selectedDepIds[0] != null) {
          this.initQueryUserByDepId(selectedDepIds); // 调用方法根据选选择的id查询用户信息
          if (this.selectedDepIds[0] !== selectedDepIds[0]) {
            this.selectedDepIds = [selectedDepIds[0]];
          }
        }
      },
      onSelectChange(selectedRowKeys, selectionRows) {
        this.selectedRowKeys = selectedRowKeys;
        this.selectionRows = selectionRows;
      },
      onSearch() {
        this.loadData(1);
      },
      // 根据选择的id来查询用户信息
      initQueryUserByDepId(selectedDepIds) {
        this.loading = true
        return queryUserByDepId({id: selectedDepIds.toString()}).then((res) => {
          if (res.success) {
            this.dataSource = res.result;
            this.ipagination.total = res.result.length;
          }
        }).finally(() => {
          this.loading = false
        })
      },
      queryDepartTree() {
        queryDepartTreeList().then((res) => {
          if (res.success) {
            this.departTree = res.result;
            // 默认展开父节点
            this.expandedKeys = this.departTree.map(item => item.id)
          }
        })
      },
      modalFormOk() {
        this.loadData();
      }
    }
  }
</script>

<style scoped>
  .ant-table-tbody .ant-table-row td {
    padding-top: 10px;
    padding-bottom: 10px;
  }

  #components-layout-demo-custom-trigger .trigger {
    font-size: 18px;
    line-height: 64px;
    padding: 0 24px;
    cursor: pointer;
    transition: color .3s;
  }
</style>