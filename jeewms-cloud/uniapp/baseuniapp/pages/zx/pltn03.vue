<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{title1}}</block>
		</cu-custom>
		<u-form :model="form" ref="uForm" class="uForm">
			<u-form-item label="子PO" label-width='160' prop="query01">
				<u-input v-model="form.query01" :placeholder="$t('请输入子PO')"  @confirm="getDetails"/>
			</u-form-item>
			<u-form-item label="客户名称" label-width='160' prop="query02">
				<u-input v-model="form.query02" :placeholder="$t('请输入客户名称')" disabled/>
			</u-form-item>
			<u-form-item label="企业属性" label-width='160' prop="query03">
				<u-input v-model="form.query03" :placeholder="$t('请输入企业属性')" disabled/>
			</u-form-item>
			<u-form-item label="供应商" label-width='160' prop="query04">
				<u-input v-model="form.query04" :placeholder="$t('请输入供应商')" disabled/>
			</u-form-item>
			<u-form-item label="采购员" label-width='160' prop="query05">
				<u-input v-model="form.query05" :placeholder="$t('请输入采购员')" disabled/>
			</u-form-item>
			<u-form-item label="SKU编码" label-width='160' prop="query06">
				<u-input v-model="form.query06" :placeholder="$t('请输入SKU编码')" disabled/>
			</u-form-item>
			<u-form-item label="产品名称" label-width='160' prop="query07">
				<u-input v-model="form.query07" :placeholder="$t('请输入产品名称')" disabled/>
			</u-form-item>
			<u-form-item label="订单数量" label-width='160' prop="query08">
				<u-input v-model="form.query08" :placeholder="$t('请输入订单数量')" disabled/>
			</u-form-item>
			<u-form-item label="跟单员" label-width='160' prop="query09">
				<u-input v-model="form.query09" :placeholder="$t('请输入跟单员')" disabled/>
			</u-form-item>
			<u-form-item label="验货类型" label-width='160' prop="query10">
				<u-input v-model="form.query10" :placeholder="$t('请输入验货类型')" disabled/>
			</u-form-item>
			<u-form-item label="验货工时(H)" label-width='160' prop="query11">
				<u-input v-model="form.query11" :placeholder="$t('请输入验货工时')" />
			</u-form-item>
			<u-form-item label="验货数" label-width='160' prop="query12">
				<u-input v-model="form.query12" :placeholder="$t('请输入验货数量')" @input="queryInput(1)"/>
			</u-form-item>
			<u-form-item label="良品数" label-width='160' prop="query13">
				<u-input v-model="form.query13" :placeholder="$t('请输入良品数量')" @input="queryInput(2)"/>
			</u-form-item>
			<u-form-item label="不良品数" label-width='160' prop="query14">
				<u-input v-model="form.query14" :placeholder="$t('请输入不良品数量')" disabled/>
			</u-form-item>
			<u-form-item label="不良比例" label-width='160' prop="query15">
				<u-input v-model="form.query15" :placeholder="$t('请输入不良比例')" disabled/>
			</u-form-item>
			<u-form-item label="不良描述" label-width='160' prop="query16">
				<u-input v-model="form.query16"  :placeholder="$t('请输入不良品描述')" type="textarea" :auto-height="true"/>
			</u-form-item>
			<u-form-item label="最终判定" label-width='160' prop="query17">
				<view class="formItemBody2"  :style="{color:form.query17?'#000':'#bbb'}" @click="getDictItems">
					{{form.query17?form.query17:$t('请选择')}}
				</view>
			</u-form-item>
			<u-form-item label="验货员" label-width='160' prop="query18">
				<u-input v-model="form.query18" :placeholder="$t('请输入验货员')" disabled/>
			</u-form-item>
			<u-form-item label="验货日期" label-width='160' prop="query19">
				<view class="formItemBody2"  :style="{color:form.query19?'#000':'#bbb'}" @click="xuanzheRQ">
					{{form.query19?form.query19:$t('请选择')}}
				</view>
			</u-form-item>
			<u-form-item label="箱长宽高" label-width='160' prop="query20">
				<u-input v-model="form.query20" :placeholder="$t('请输入箱子的长宽高')" />
			</u-form-item>
			<u-form-item label="每箱重量" label-width='160' prop="query21">
				<u-input v-model="form.query21" :placeholder="$t('请输入每箱重量')" />
			</u-form-item>
			<u-form-item label="尾箱备注" label-width='160' prop="query22">
				<u-input v-model="form.query22" :placeholder="$t('请输入尾箱备注')" type="textarea" :auto-height="true"/>
			</u-form-item>
			<u-form-item label="附件" label-width='160' prop="query22">
				<u-upload :action="action" :file-list="fileList" @on-change="uploadSuccess" @on-remove="onRemove" :header="{'X-Access-Token':TOKEN}"></u-upload>
			</u-form-item>
			<u-button type="primary" @click="preserve" >提交</u-button>
		</u-form>
		<u-select v-model="uSelectShow" mode="single-column" :list="uSelectList"  @selectConfirm="selectConfirm"></u-select>
		<u-calendar v-model="show" mode="date" @change="calendarChange"></u-calendar>
	</view>
</template>
<script>
export default {
	data() {
		return {
			show:false,
			title:"",
			title1:'',
			userName:"",
			form: {},
			TOKEN:uni.getStorageSync('Access-Token'),
			action: uni.getStorageSync('base_url') + '/sys/common/upload',
			fileList: [],
			urlImg:[],
			uSelectShow:false,
			uSelectList:[],
			rules: {
				// query02:[{required: true, message:this.$t('请输入客户名称'),trigger: 'change'}],
				// query03:[{required: true, message:this.$t('请输入企业属性'),trigger: 'change'}],
				// query04:[{required: true, message:this.$t('请输入供应商'),trigger: 'change'}],
				// query05:[{required: true, message:this.$t('请输入采购员'),trigger: 'change'}],
				// query06:[{required: true, message:this.$t('请输入SKU'),trigger: 'change'}],
				// query07:[{required: true, message:this.$t('请输入产品名称'),trigger: 'change'}],
				// query08:[{required: true, message:this.$t('请输入订单数量'),trigger: 'change'}],
				// query09:[{required: true, message:this.$t('请输入跟单员'),trigger: 'change'}],
				// query10:[{required: true, message:this.$t('请输入验货类型'),trigger: 'change'}],
				query11:[{required: true, message:this.$t('请输入验货工时/格式错误'),trigger: 'change',type:"number"}],
				query12:[{required: true, message:this.$t('请输入验货数量/格式错误'),trigger: 'change',type:"number"}],
				query13:[{required: true, message:this.$t('请输入良品数量/格式错误'),trigger: 'change',type:"number"}],
				query14:[{required: true, message:this.$t('请输入不良品数量/格式错误'),trigger: 'change',type:"number"}],
				query15:[{required: true, message:this.$t('请输入不良比例'),trigger: 'change',type:"number"}],
				query16:[{required: true, message:this.$t('请输入不良品描述'),trigger: 'change'}],
				query17:[{required: true, message:this.$t('请输入最终判定'),trigger: 'change'}],
				query19:[{required: true, message:this.$t('请输入验货日期'),trigger: 'change'}],
				query20:[{required: true, message:this.$t('请输入箱子的长宽高'),trigger: 'change'}],
				query21:[{required: true, message:this.$t('请输入每箱重量/格式错误'),trigger: 'change'}],
				query22:[{required: true, message:this.$t('请输入尾箱备注'),trigger: 'change'}],
				query01: [{required: true, message: this.$t('请输入子PO'),trigger: 'change'}]
			},
			switchVal: false
		};
	},
	onLoad() {
		this.title = this.$Route.query.title
		this.title1 = this.$Route.query.NameKey
		this.userName = uni.getStorageSync('login_user_info').username || '';
		
	},
	onReady() {
		this.$refs.uForm.setRules(this.rules);
	},
	methods: {
		selectConfirm(e){
			console.log(e);
			this.form.query17 = e[0].value
		},
		calendarChange(e){
			this.form.query19 = e.result
			console.log(e,'-------------calendarChange');
		},
		xuanzheRQ(){
			this.show = true
		},
		queryInput(){
			if(this.form.query12 && this.form.query13){
				// if(Number(this.form.query12) > Number(this.form.query13)){
					this.form.query14 = Number(this.form.query12) - Number(this.form.query13)
					this.form.query15 = Number(this.form.query14) / Number(this.form.query12).toFixed(2)
				// }
			}
		},
		getDetails(){
			if (!this.form.query01) return uni.showToast({ title: '请输入单号', icon: 'none' })
			let data = {
				username: this.userName,
				listtype: 'QCJYJL',
				query01: this.form.query01
			}
			this.$http.get('/jeewms/pdaGapi/getlist', {
				params: data
			}).then(res => {
				if (res.data.result.records.length > 0) {
					this.form = res.data.result.records[0]
				}
				this.form.query18 = this.userName
			})
		},
		getDictItems(e){
			this.$http.get('/sys/dict/getDictItems/OQC_ZJ_XINXI').then(res => {
				if (res.data.result.length > 0) {
					let arr = []
					res.data.result.map(item=>{
						arr.push({value: item.value, label: item.value })
					})
					this.uSelectList = arr
					this.uSelectShow = true
				}
			})
		},
		uInputClick2(){
			this.uSelectShow = true
		},
		bindDateChange(e) {
			if(e.detail.value){
				this.$set(this.form,'query07',e.detail.value)
				let arr = e.detail.value.split("-");
				this.$set(this.form,'query08',arr[0].substring(2,4)+arr[1])
			}
		},
		uploadSuccess(res, index, lists, name){
			let data = JSON.parse(res.data)
			if(data.success){
				this.urlImg.push(data.message)
			}	
		},
		onRemove(index, lists){
			this.urlImg.splice(index,1)
		},
		preserve(){
			this.$refs.uForm.validate(valid => {
				if (valid) {
					let data = {
						...this.form,
						attr1:this.urlImg.join(","),
						username: this.userName,
						listtype: 'QCJYJL'
					};
					console.log(data);
					uni.showLoading({ title: '加载中', mask: true })
					this.$http.post('/jeewms/pdaPapi/postdata', data).then(res => {
						uni.showToast({ title: res.data.message, icon: 'none' });
						if (res.data.code == 200) {
							this.form = {}
						}
					})
				} else {
					console.log('验证失败');
				}
			});
		},
	}
};
</script>
<style scoped lang="less">
	.content {
		width: 100%;
		height: 100vh;
		background-color: #F3F4F6;
		overflow: auto;
		display: flex;
		flex-direction: column;
		background-color: #fff;
		.uForm{
			width: 95%;
			margin: 0 auto;
			/deep/ .u-form-item{
				padding: 2rpx 0;
			}
			/deep/ .u-list-item{
				width: 40rpx;
				height: 40rpx;
			}
			.formItemBody{
				width: 100%;
				height: 100%;
			}
			.formItemBody2{
				flex: 1;
				margin-left: 10rpx;
			}
		}
	}
</style>