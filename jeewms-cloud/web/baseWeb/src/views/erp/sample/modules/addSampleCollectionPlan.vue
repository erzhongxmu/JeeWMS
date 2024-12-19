<template>
    <j-modal :title="title" :width="width" :visible="visible" switchFullscreen @ok="handleOk" @cancel="close"
        cancelText="关闭" :maskClosable="false">
        <a-spin :spinning="confirmLoading">
            <div class="table-page-search-wrapper">
                <a-form layout="inline" @keyup.enter.native="searchQuery">
                    <a-row :gutter="24">
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item label="主PO号">
                                <a-input placeholder="请输入主PO号" v-model="queryParam.query13"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item label="子PO号">
                                <a-input placeholder="请输入子PO号" v-model="queryParam.query14"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item label="客户">
                                <a-input placeholder="请输入客户" v-model="queryParam.query24"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item label="销售单号">
                                <a-input placeholder="请输入销售单号" v-model="queryParam.link03"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item label="内部发票号">
                                <a-input placeholder="请输入内部发票号" v-model="queryParam.query17"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item label="商品编码">
                                <a-input placeholder="请输入商品编码" v-model="queryParam.query09"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <a-form-item label="商品名称">
                                <a-input placeholder="请输入商品名称" v-model="queryParam.query10"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="6" :lg="7" :md="8" :sm="24">
                            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                                <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                                <a-button type="primary" @click="searchReset" icon="reload"
                                    style="margin-left: 8px">重置</a-button>
                            </span>
                        </a-col>
                    </a-row>
                </a-form>
            </div>
            <a-table ref="table" size="middle" :scroll="{ x: true }" bordered rowKey="id" :columns="columns"
                :dataSource="dataSource" :pagination="ipagination" :loading="loading"
                :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" class="j-table-force-nowrap"
                @change="handleTableChange">
                <template slot="input" slot-scope="text,record">
                    <a-input v-model="record.num18"/>
                </template>
            </a-table>
        </a-spin>

        <j-modal title="确认计划收款数据" :width="800" :visible="visible2" switchFullscreen @ok="handleOk2" @cancel="close2"
        cancelText="关闭" :maskClosable="false">

        <div class="table-page-search-wrapper">
                <a-form layout="inline" @keyup.enter.native="searchQuery">
                    <a-row :gutter="24">
                        <a-col :xl="12" :lg="7" :md="8" :sm="24">
                            <a-form-item label="PAYMENT LIST">
                                <a-input placeholder="请输入PAYMENT LIST" v-model="model.query19"></a-input>
                            </a-form-item>
                        </a-col>
                        <a-col :xl="12" :lg="7" :md="8" :sm="24">
                            <a-form-item label="备注">
                                <a-input placeholder="请输入备注" v-model="model.text01"></a-input>
                            </a-form-item>
                        </a-col>
                    </a-row>
                </a-form>
            </div>
            <a-table ref="table" size="middle" :scroll="{ x: true }" bordered rowKey="id" :columns="columns2"
                :dataSource="dataSource2"  :loading="loading2"
                 class="j-table-force-nowrap">
                <template slot="input" slot-scope="text,record">
                    <a-input v-model="record.num18"/>
                </template>
            </a-table>
        </j-modal>
    </j-modal>
</template>
  
<script>

import { getAction, postAction } from '@/api/manage'
export default {
    name: 'addProformaInvoice',
    components: {
    },
    data() {
        return {
            title: '',
            width: 1300,
            model: {},
            visible: false,
            visible2: false,
            loading2: false,
            loading: false,
            confirmLoading: false,
            selectedRowKeys: [],
            selectionRows: [],
            dataSource: [],
            dataSource2: [],
            columns2:[
                {
                    title: '本次开票金额',
                    align: 'center',
                    dataIndex: 'num18',
                    scopedSlots: { customRender: 'input' },
                },
                {
                    title: '可开票金额',
                    align: 'center',
                    dataIndex: 'num17',
                },
                {
                    title: '关联单号',
                    align: 'center',
                    dataIndex: 'query04',
                },
                {
                    title: '主PO',
                    align: 'center',
                    dataIndex: 'query13',
                },
                {
                    title: '子PO',
                    align: 'center',
                    dataIndex: 'query14',
                },
                {
                    title: '客户',
                    align: 'center',
                    dataIndex: 'query24',
                },
                {
                    title: '销售单号',
                    align: 'center',
                    dataIndex: 'link03',
                },
                {
                    title: '内部发票号',
                    align: 'center',
                    dataIndex: 'query17',
                },
                {
                    title: '商品编码',
                    align: 'center',
                    dataIndex: 'query09',
                },
                {
                    title: '商品名称',
                    align: 'center',
                    dataIndex: 'query10',
                },
                {
                    title: '数量',
                    align: 'center',
                    dataIndex: 'num01',
                },
                {
                    title: '单位',
                    align: 'center',
                    dataIndex: 'query12',
                },
                {
                    title: '单价',
                    align: 'center',
                    dataIndex: 'num04',
                },
                {
                    title: '金额',
                    align: 'center',
                    dataIndex: 'num07',
                },
                {
                    title: '币种',
                    align: 'center',
                    dataIndex: 'query22',
                },
            ],
            columns: [
                {
                    title: '关联单号',
                    align: 'center',
                    dataIndex: 'query04',
                },
                {
                    title: '主PO',
                    align: 'center',
                    dataIndex: 'query13',
                },
                {
                    title: '子PO',
                    align: 'center',
                    dataIndex: 'query14',
                },
                {
                    title: '客户',
                    align: 'center',
                    dataIndex: 'query24',
                },
                {
                    title: '销售单号',
                    align: 'center',
                    dataIndex: 'link03',
                },
                {
                    title: '内部发票号',
                    align: 'center',
                    dataIndex: 'query17',
                },
                {
                    title: '商品编码',
                    align: 'center',
                    dataIndex: 'query09',
                },
                {
                    title: '商品名称',
                    align: 'center',
                    dataIndex: 'query10',
                },
                {
                    title: '数量',
                    align: 'center',
                    dataIndex: 'num01',
                },
                {
                    title: '单位',
                    align: 'center',
                    dataIndex: 'query12',
                },
                {
                    title: '单价',
                    align: 'center',
                    dataIndex: 'num04',
                },
                {
                    title: '金额',
                    align: 'center',
                    dataIndex: 'num07',
                },
                {
                    title: '可开票金额',
                    align: 'center',
                    dataIndex: 'num17',
                },
                {
                    title: '币种',
                    align: 'center',
                    dataIndex: 'query22',
                },
            ],
            ipagination: {
                current: 1,
                pageSize: 20,
                pageSizeOptions: ['10', '20', '30', '50', '100'],
                showTotal: (total, range) => {
                    return range[0] + '-' + range[1] + '  ' + ' ' + this.$t('共') + ' ' + total + ' ' + this.$t('项目')
                },
                showQuickJumper: true,
                showSizeChanger: true,
                total: 0
            },
            queryParam: {},
        }
    },
    computed: {
        formDisabled() {
            return this.disabled
        },
    },
    methods: {
        searchReset(){
            this.queryParam = {}
            this.ipagination.current = 1
            this.loadData()
        },
        onSelectChange(selectedRowKeys, selectionRows) {
            this.selectedRowKeys = selectedRowKeys
            this.selectionRows = selectionRows
        },
        searchQuery() {
            this.ipagination.current = 1
            this.loadData()
        },
        loadData() {
            let obj = { pageNo: this.ipagination.current, pageSize: this.ipagination.pageSize, ...this.queryParam}
            getAction("/jeeerp/vOmsInvWq/list", obj).then(res => {
                if (res.success) {
                    res.result.records.map((item, index) => {
                        item.num18 = item.num17
                    })
                    this.dataSource = res.result.records
                    this.ipagination.total = res.result.total
                }
            })
        },
        handleTableChange() {
            //分页、排序、筛选变化时触发
            //TODO 筛选
            if (Object.keys(sorter).length > 0) {
                this.isorter.column = sorter.field
                this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
            }
            this.ipagination = pagination
            this.loadData()
        },
        valueChange(e) {
            if (e.row.num01 && e.row.num04) {
                this.getAllTable().then(tables => {
                    tables[0].setValues([])
                })
            }
        },
        add() {
            this.title = '计划收款'
            this.visible = true
            this.loadData()
        },
        edit(record) {
            this.visible = true
        },
        close() {
            this.ipagination.current = 1
            this.ipagination.pageSize = 20
            this.visible = false
            this.visible2 = false
            this.loading2 = false
            this.loading = false
            this.confirmLoading = false
            this.selectedRowKeys = []
            this.selectionRows = []
            this.dataSource = []
            this.dataSource2 = []
        },
        // handleOk() {
        //     this.dataSource2 = JSON.parse(JSON.stringify(this.selectionRows));
        //     this.model.query19 = this.dataSource2[0].query19
        //     this.model.text01 = this.dataSource2[0].text01
        //     this.visible2 = true
        // },
        handleOk() {
            var query09 = this.selectionRows.some(v=>{
                return v.query09 != this.selectionRows[0].query09
            })
            var query22 = this.selectionRows.some(v=>{
                return v.query22 != this.selectionRows[0].query22
            })
            if (!query09 && !query22) {
            this.dataSource2 = JSON.parse(JSON.stringify(this.selectionRows));
            this.model.query19 = this.dataSource2[0].query19
            this.model.text01 = this.dataSource2[0].text01
            this.visible2 = true
            }else{
                this.$message.info(this.$t('请选择相同供应商和币种'))
            }

        },
        handleOk2(){
            let data = JSON.parse(JSON.stringify(this.dataSource2)); 
            if(!this.model.query19) return this.$message.warning(this.$t("PAYMENT LIST不能为空"))
            let str = ""
            let str2 = ""
            console.log(this.model);
            let num = 0
            data.map((item,index)=>{
                num += Number(item.num18)
            })
            data.map((item,index)=>{
                item.num07 = item.num18
                item.num09 = num
                item.query19 = this.model.query19
                item.text01 =  this.model.text01
                item = {...item,...this.model}
                if(isNaN(item.num18)){
                    str2 = item.query14
                }
                // let regPos = /^[0-9]+(.[0-9]{2})?$/ //判断是否是数字。
                // if(!regPos.test(item.num18)){
                //     str2 = item.query14
                // }
                if(!item.num18){
                    str2 = item.query14
                }
                if(Number(item.num18) > Number(item.num17)){
                    str = item.query14
                }
            })
            if(str2) return this.$message.warning('请检查子PO' + str + "金额输入格式错误")
            if(str) return this.$message.warning('请检查子PO' + str + "金额是否是否大于可开票金额")
            let data2 = {
                postType: "YPSKJH",
                list01: data
            }
            postAction("/jeeerp/busiPaymentReceived/postdata",data2).then(res=>{
                if (res.success) {
                    this.$emit("ok")
                    this.close()
                    this.$message.success(this.$t('操作成功'))
                }else{
                    this.$message.warning(this.$t('操作失败'))
                }
            })  
            console.log( this.dataSource2);
        },
        close2() {
            this.visible2 = false
            this.loading2 = false
            this.dataSource2 = []
        },
    }
}
</script>
	