<template>
	<a-card :bordered="false">
		<!-- 查询区域 -->
		<div class="table-page-search-wrapper">
			<a-form layout="inline" @keyup.enter.native="searchQuery">
				<a-row :gutter="24">
					<a-col :xl="6" :lg="7" :md="8" :sm="24">
						<a-form-item label="报表编码">
							<a-input placeholder="请输入报表编码" v-model="queryParam.code"></a-input>
						</a-form-item>
					</a-col>
					<a-col :xl="6" :lg="7" :md="8" :sm="24">
						<a-form-item label="报表名字">
							<a-input placeholder="请输入报表名字" v-model="queryParam.name"></a-input>
						</a-form-item>
					</a-col>
					<a-col :xl="6" :lg="7" :md="8" :sm="24">
						<span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
							<a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
							<a-button  @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
							<!-- <a @click="handleToggleSearch" style="margin-left: 8px">
								{{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
								<a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
							</a> -->
						</span>
					</a-col>
				</a-row>
			</a-form>
		</div>
		<!-- 查询区域-END -->

		<!-- 操作按钮区域 -->
		<div class="table-operator">
			<a-button @click="handleAdd" type="primary" icon="plus">录入</a-button>
			<!-- <a-button icon="download" @click="handleExportXls('onl_cgreport_head')">{{$t('导出')}}</a-button>
			<a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
			 @change="handleImportExcel">
				<a-button icon="import">{{$t('导入')}}</a-button>
			</a-upload>
			高级查询区域
			<j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
			<a-dropdown v-if="selectedRowKeys.length > 0">
				<a-menu slot="overlay">
					<a-menu-item key="1" @click="batchDel">
						<a-icon type="delete" />{{$t('删除')}}</a-menu-item>
				</a-menu>
				<a-button style="margin-left: 8px"> {{$t('批量操作')}}
					<a-icon type="down" />
				</a-button>
			</a-dropdown> -->
		</div>

		<!-- table区域-begin -->
		<div>
			<div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
				<i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
				<a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
			</div>

			<a-table ref="table" size="middle" bordered rowKey="id" class="j-table-force-nowrap" :scroll="{x:true}" :columns="columns"
			 :dataSource="dataSource" :pagination="ipagination" :loading="loading" :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
			 @change="handleTableChange">

				<template slot="htmlSlot" slot-scope="text">
					<div v-html="text"></div>
				</template>
				<template slot="imgSlot" slot-scope="text">
					<span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
					<img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;" />
				</template>
				<template slot="fileSlot" slot-scope="text">
					<span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
					<a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
						下载
					</a-button>
				</template>

				<span slot="action" slot-scope="text, record">
					<a @click="handleEdit(record)">{{$t('编辑')}}</a>

					<a-divider type="vertical" />
					<a-dropdown>
						<a class="ant-dropdown-link">更多
							<a-icon type="down" /></a>
						<a-menu slot="overlay">
							<!-- <a-menu-item>
								<a @click="handleDetail(record)">详情</a>
							</a-menu-item> -->
							<a-menu-item>
								<a @click="configurationAddress(record)">配置地址</a>
							</a-menu-item>
							<a-menu-item>
								<a @click="funcTest(record)">功能测试</a>
							</a-menu-item>
							<a-menu-item>
								<a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">
									<a>{{$t('删除')}}</a>
								</a-popconfirm>
							</a-menu-item>
						</a-menu>
					</a-dropdown>
				</span>

			</a-table>
		</div>

		<onl-cgreport-head-modal ref="modalForm" @ok="modalFormOk" />
			<j-modal v-model="visible" title="bi访问链接" on-ok="handleOk">
				<template slot="footer">
					<a-button @click="handleCancel">
						返回
					</a-button>
					<a-button type="primary" :loading="loading" @click="copy">
						复制
					</a-button>
				</template>
				<p id="wrapper">{{ ModalText }}</p>
			</j-modal>
	</a-card>
</template>

<script>
	import {
		JeecgListMixin
	} from '@/mixins/JeecgListMixin'
	import OnlCgreportHeadModal from './modules/OnlCgreportHeadModal'
	import {
		filterMultiDictText
	} from '@/components/dict/JDictSelectUtil'
	import '@/assets/less/TableExpand.less'
	import JSuperQuery from '@/components/jeecg/JSuperQuery.vue'

	export default {
		name: "OnlCgreportHeadList",
		mixins: [JeecgListMixin],
		components: {
			OnlCgreportHeadModal,
			JSuperQuery
		},
		data() {
			return {
				description: '管理页面',
				visible: false, //配置地址 弹窗状态
				ModalText: '', // 报表访问链接
				// 表头
				columns: [{
						title: '#',
						dataIndex: '',
						key: 'rowIndex',
						width: 60,
						align: 'left',
						customRender: function(t, r, index) {
							return parseInt(index) + 1;
						}
					},
					{
						title: '报表编码',
						align: 'left',
						dataIndex: 'code'
					},
					{
						title: '报表名字',
						align: 'left',
						dataIndex: 'name'
					},
					{
						title: '报表SQL',
						align: 'left',
						dataIndex: 'cgrSql'
					},
					{
						title: '返回值字段',
						align: 'left',
						dataIndex: 'returnValField'
					},
					{
						title: '返回文本字段',
						align: 'left',
						dataIndex: 'returnTxtField'
					},
					{
						title: '返回类型，单选或多选',
						align: 'left',
						dataIndex: 'returnType'
					},
					{
						title: '动态数据源',
						align: 'left',
						dataIndex: 'dbSource'
					},
					{
						title: '描述',
						align: 'left',
						dataIndex: 'content'
					},
					{
						title: '修改时间',
						align: 'left',
						dataIndex: 'updateTime',
						customRender: (text) => {
							return !text ? "" : (text.length > 10 ? text.substr(0, 10) : text)
						}
					},
					{
						title: '修改人id',
						align: 'left',
						dataIndex: 'updateBy'
					},
					{
						title: '创建时间',
						align: 'left',
						dataIndex: 'createTime',
						customRender: (text) => {
							return !text ? "" : (text.length > 10 ? text.substr(0, 10) : text)
						}
					},
					{
						title: '创建人id',
						align: 'left',
						dataIndex: 'createBy'
					},
					{
						title: '仓库',
						align: 'left',
						dataIndex: 'repository'
					},
					{
						title: this.$t('操作'),
						dataIndex: 'action',
						align: 'left',
						fixed: "right",
						width: 147,
						scopedSlots: {
							customRender: 'action'
						},
					}
				],
				url: {
					list: "/onl_cgreport_head/onlCgreportHead/list",
					delete: "/onl_cgreport_head/onlCgreportHead/delete",
					deleteBatch: "/onl_cgreport_head/onlCgreportHead/deleteBatch",
					exportXlsUrl: "/onl_cgreport_head/onlCgreportHead/exportXls",
					importExcelUrl: "/onl_cgreport_head/onlCgreportHead/importExcel",
				},
				dictOptions: {},
				superFieldList: [],
			}
		},
		created() {
			this.getSuperFieldList();
		},
		computed: {
			importExcelUrl: function() {
				return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
			}
		},
		methods: {
			initDictConfig() {},
			getSuperFieldList() {
				let fieldList = [];
				fieldList.push({
					type: 'string',
					value: 'code',
					text: '报表编码',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'name',
					text: '报表名字',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'cgrSql',
					text: '报表SQL',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'returnValField',
					text: '返回值字段',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'returnTxtField',
					text: '返回文本字段',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'returnType',
					text: '返回类型，单选或多选',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'dbSource',
					text: '动态数据源',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'content',
					text: '描述',
					dictCode: ''
				})
				fieldList.push({
					type: 'date',
					value: 'updateTime',
					text: '修改时间'
				})
				fieldList.push({
					type: 'string',
					value: 'updateBy',
					text: '修改人id',
					dictCode: ''
				})
				fieldList.push({
					type: 'date',
					value: 'createTime',
					text: '创建时间'
				})
				fieldList.push({
					type: 'string',
					value: 'createBy',
					text: '创建人id',
					dictCode: ''
				})
				fieldList.push({
					type: 'string',
					value: 'repository',
					text: '仓库',
					dictCode: 'LargeScreenDataType'
				})
				this.superFieldList = fieldList
			},
			// 配置地址
			configurationAddress(record) {
				this.visible = true
				this.ModalText = 'basebi/' + record.id
			},
			copy(e) {
				// this.ModalText = 'The modal will be closed after two seconds';
				this.$nextTick(() => {
					var range = document.createRange();
					range.selectNode(document.getElementById('wrapper'));
					var selection = window.getSelection();
					if (selection.rangeCount > 0) selection.removeAllRanges();
					selection.addRange(range);
					document.execCommand('copy');
					this.$message.success('链接已复制成功');
				})
				this.visible = false;
			},
			handleOk(e) {
				this.visible = false;
			},
			handleCancel(e) {
				console.log('Clicked cancel button');
				this.visible = false;
			},
			// 功能测试
			funcTest(record) {
				this.$router.push({
					path: "/online/cgreport/" + record.id
				})
			}

		}
	}
</script>
<style scoped>
	@import '~@assets/less/common.less';
</style>
