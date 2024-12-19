<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{title1}}({{data.length}})</block>
			<view slot="right" @click="remain2">剩余任务</view>
		</cu-custom>
		<view class="Search">
			<view>
				<input  type="text" :focus="Focus['isFocus1']" v-model="conditionObj.query01"  @confirm="getDetails1('isFocus1')" :placeholder="$t('请扫描单号')"  style="margin:8rpx 0;"/>
				<image class="clear" src="../../static/ZX/clear.png" @click="close('query01')" v-show="conditionObj.query01"></image>
			</view> 
			<view>
				<input  type="text" :focus="Focus['isFocus2']" v-model="conditionObj.query02"  @confirm="getDetails2('isFocus2')" :placeholder="$t('请扫描箱码')"  style="margin:8rpx 0;"/>
				<image class="clear" src="../../static/ZX/clear.png" @click="close('query02')" v-show="conditionObj.query02"></image>
			</view> 
			<view>
				<input  type="text" :focus="Focus['isFocus3']" v-model="conditionObj.query03"  :placeholder="$t('请扫描库位')"  style="margin:8rpx 0;"/>
				<image class="clear" src="../../static/ZX/clear.png" @click="close('query03')" v-show="conditionObj.query03"></image>
			</view> 
		</view>
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;">
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
				<image @click="expurgate(index,item.query09)" class="clear2" src="../../static/ZX/expurgate.png"></image>
				<view  v-for="(v,i) in condition.list" v-if="v.query03 != '储位'">
					<view class="item" v-if="v.query04 == 'text'">
						<view>{{$t('pages.'+ v.query03)}}：</view>
						<view 
						:style="{color: v.query03 != '托盘号'?'#2D2F2E':'#3396E2',fontSize: v.query03 != '托盘号'?'28rpx':'32rpx',maxWidth:'70%'}" 
						>{{item[v.query02] || ''}}</view>
					</view>
				</view>
			</view>
		</scroll-view>
		<view   class="btn"  @click="preserve()">{{$t('上架')}}</view>
		<view class="btn2" ></view>
		<model ref='uniModel'></model>
	</view>
</template>

<script>
	import dyDatePicker from './../component/dyDatePicker'
	export default {
		data() {
			return {
				id:"",
				title:'',
				title1:'',
				data:[],
				condition:{},
				conditionObj:{},
				userName:'',
				pageNo:1,
				pageSize:10,
				Focus:{
					isFocus1:true,
					isFocus2:false,
				},
				Focus2:{},
				Fun:'',
			}
		},
		components:{
			dyDatePicker
		},
		onLoad() {
			this.Focus.isFocus1 = true
			this.title = this.$Route.query.title
			this.title1 = this.$Route.query.NameKey
			this.id = this.$Route.query.id
			this.userName = uni.getStorageSync('login_user_info').username || '';
			this.getdata()
		},
		methods: {
			remain2(){
				if(!this.conditionObj.query01){ return uni.showToast({ icon:'none', title: this.$t('请扫描单号')})}
				this.$Router.push({
					path: '/pages/zx/zx16-1',
					params:{
						query01:this.conditionObj.query01,
						title:this.title,
						id:this.id,
					}
				})
			},
			expurgate(index,tinid){
				let that = this
				this.$refs.uniModel.open({ 
					content: this.$t('是否删除箱码') + tinid, 
					cancelText:'否', 
					confirmText:'是',
					success(res) {
						if (res.confirm) {
							that.data.splice(index,1)
						}
					}
				})
			},
			close(str) {
				this.conditionObj[str] = ''
				this.data = []
			},
			focusFun(index,i){
				let obj = JSON.parse(JSON.stringify(this.Focus2))
				for(let key in obj){
					obj[key] =  false
				}
				obj[index + 'isFocus' + (i + 1)] = true
				this.Focus2 = obj
			},
			dyDatePickerChange(e,index,str){
				this.data[index][str] = e
			},
			getdata(){ 
				if(!this.id) return
				let data = {
					query01:this.id,
					pageNo:1,
					pageSize:1000
				}
				this.$http.get('/pda/wmPdaConfig/list',{params:data}).then(res => {
					let data = JSON.parse((JSON.stringify(res.data.result.records)))
					let search = [];
					let button = [];
					let list = [];
					data.sort((x,y) => {
						return x.query06 -y.query06;
					})
					data.map((item,index)=>{
						// 是否禁用
						if(item.query07 == '是'){
							// 是不是搜索条件
							if(item.query05 == '是'){
								search.push(item)
							}
							if(item.query04 == 'button'){
								button.push(item)
							}
							if(item.query04 != 'button'){
								list.push(item)
							}
						}
					})
					search.map((item,index)=>{
						this.Focus['isFocus'+(index+1)] = false
						if(index == 0){
							this.Focus['isFocus'+(index+1)] = true
						}
					})
					 this.condition = {
						 search,
						 button,
						 list
					 }
				})
			},
			preserve(){
				if(!this.conditionObj.query03) return uni.showToast({ icon:'none', title: '请扫描库位'})
				if(this.data.length == 0) return uni.showToast({ icon:'none', title: '请扫描箱码'})
				let binIDs = []
				let that = this
				this.data.map((item,idnex)=>{
					binIDs.push(item.id)
				})
				binIDs = binIDs.join(',')
				let data = { 
					id:binIDs,
					query10:this.conditionObj.query03, 
					query04:this.conditionObj.query01, 
					username: this.userName, 
					listtype: this.title,
				};
				this.$refs.uniModel.open({
					content: that.$t('是否上架'), 
					cancelText:'否', 
					confirmText:'是',
					success(res) {
						if (res.confirm) {
							that.goTO(data)
						}
					}
				})
			},
			goTO(data){
				uni.showLoading({ title: this.$t('加载中'), mask: true });
				this.$http.post('/jeewms/pdaPapi/postdata', data).then(res => {
					uni.showToast({title: this.$t('pages.' + res.data.message) ,icon:'none'})
					if (res.data.code == 200) {
						this.data = [];
						this.conditionObj.query03 = ''
					}
				})
			},
			getDetails1(){
				if(!this.conditionObj.query01){ return uni.showToast({ icon:'none', title: this.$t('请扫描单号')})}
				this.Focus.isFocus1 = false
				this.Focus.isFocus2 = true
			},
			getDetails2(){
				if(!this.conditionObj.query01){return uni.showToast({ icon:'none', title: this.$t('请扫描单号')})}
				if(!this.conditionObj.query02){return uni.showToast({ icon:'none', title: this.$t('请扫描箱码')})}
				this.Focus.isFocus2 = false
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username:this.userName,
					listtype:this.title,
					...this.conditionObj
				};
				this.$http.get('/jeewms/pdaGapi/getlist',{params:data}).then(res => {
					this.Focus.isFocus2 = true
					if(res.data.code == 500) return uni.showToast({title: this.$t('pages.' + res.data.message) ,icon:'none'})
					this.conditionObj.query02 = ''
					let records = res.data.result.records
					let obj = {}
					if(records.length == 0){
						return uni.showToast({title: this.$t('请确认该箱是否属于此入库单或是否已上架') ,icon:'none'})
					}else{
						obj = records[0]
					}
					let i = ''
					this.data.map(item =>{
						if(item.query09 == obj.query09){
							i = item.query09
						}
					})
					if(i){
						uni.showToast({title: this.$t('请勿重复扫码') ,icon:'none'})
					}else{
						this.data.push(obj)
					}
				});
			},
		}
	}
</script>

<style scoped lang="less">
	.content{
		width: 100%;
		height: 100vh;
		background-color: #F3F4F6;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		.Search{
			width: 100%;
			min-height: 101rpx;
			background: #FFFFFF;
			display: flex;
			justify-content: center;
			align-items: center;
			flex-direction: column;
			view{
				position:relative;
				width: 100%;
				display: flex;
				justify-content: center;
				align-items: center;
			}
			input{
				padding-left: 36rpx;
				width: 93%;
				height: 70rpx;
				background: #F3F4F6;
				border-radius: 30px;
				font-size: 28rpx;
				font-weight: 400;
			}
			.clear {
				position: absolute;
				top: 14px;
				right: 25px;
				width: 20px;
				height: 20px;
			}
		}
		.body_t{
			width: 	92.53%;
			min-height: 500rpx;
			margin: 27rpx auto;
			background: #FFFFFF;
			border-radius: 10rpx;
			padding: 40rpx 33rpx 20rpx 33rpx;
			position: relative;
			.clear2{
				width: 40rpx;
				height: 40rpx;
				position: absolute;
				top: 15rpx;
				right: 15rpx;
			}
			.item{
				width: 100%;
				height: 70rpx;
				display: flex;
				align-items: center;
				font-size: 28rpx;
				color: #666666;
				justify-content: space-between;
			}
			.inputText{
				width: 100%;
				height: 80rpx;
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding-bottom: 15rpx;
				text{
					font-size: 28rpx;
					font-weight: 400;
					color: #666666;
				}
				.inputText_it{
					width: 30%;
					height: 100%;
					border-radius: 10rpx;
					border: 1px solid  #3297E2;
				}
				.inputText_itm{
					text-align: right;
					width: 47.5%;
					height: 60rpx;
					border-radius: 10rpx;
					border: 1px solid  #3297E2;
					color:#3297E2 ;
				}
			}
		}
	}
	
	.btn{
		position: fixed;
		bottom: 5rpx;
		left: 50%;
		transform: translateX(-50%);
		margin: 16rpx auto 16rpx;
		width: 90%;
		height: 76rpx;
		background: #3396E2;
		border-radius: 10px;
		font-size: 36rpx;
		line-height: 76rpx;
		font-family: PingFang SC;
		font-weight: 400;
		text-align: center;
		color: #FFFEFE;
	}
	.btn2{
		width: 100%;
		height: 110rpx;
		margin-top: 20rpx;
		background-color: #fff;
	}
</style>
