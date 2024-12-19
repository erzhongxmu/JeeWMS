<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{title1}}</block>
			<view slot="right" @click="remain2">剩余任务</view>
		</cu-custom>
		<view class="Search" >
			<view>
				<input type="text" v-model="conditionObj.query01" :focus="Focus['isFocus1']"  @confirm="getDetails2(1)"  :placeholder="$t('pages.'+'请扫描托盘号')" />
				<image class="clear" src="../../static/ZX/clear.png" @click="close('query01')" v-show="conditionObj['query01']"></image>
			</view>	
			<view>
				<input type="text" v-model="conditionObj.query02" :focus="Focus['isFocus2']"  @confirm="getDetails1(1)" :placeholder="$t('pages.'+'请扫描储位')" style="margin-top: 16rpx;" />
				<image class="clear" src="../../static/ZX/clear.png" @click="close('query02')" v-show="conditionObj['query02']"></image>
			</view>
		</view>
		<!--  @scrolltolower='scroll' -->
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;">
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
			<image @click="expurgate(index,item.tinId)" class="clear2" src="../../static/ZX/expurgate.png"></image>
			<view class="item">
				<view>{{$t('pages.' + '产品编码')}}：</view>
				<view style="color: #2D2F2E;">{{item.goodsId}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.' + '产品名称')}}：</view>
				<view style="color: #2D2F2E;">{{item.goodsName}}</view>
			</view>
			<!-- <view class="item">
				<view>{{$t('pages.' + '到货日期')}}：</view>
				<view style="color: #2D2F2E;">{{item.goodsProData}}</view>
			</view> -->
			<view class="item">
				<view>{{$t('pages.' + 'PO号')}}：</view>
				<view style="color: #2D2F2E;">{{item.goodsBatch}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.' + '储位')}}：</view>
				<view style="color: #2D2F2E;">{{item.binFrom}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.' + '托盘号')}}：</view>
				<view style="color: #2D2F2E;">{{item.tinFrom}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.' + '数量')}}：</view>
				<view style="color: #2D2F2E;">{{item.baseGoodscount}}</view>
			</view>
			<view class="item">
				<view>{{$t('pages.' + '单位')}}：</view>
				<view style="color: #2D2F2E;">{{item.goodsUnit}}</view>
			</view>
		</view>
		</scroll-view>
		<view   class="btn"  @click="preserve()">{{$t('pages.'+ '转储')}}</view>
		<view class="btn2" ></view>
		<model ref='uniModel'></model>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				id:"",
				query01:'',
				query02:'',
				title:'',
				title1:'',
				data:[],
				condition:{},
				conditionObj:{},
				userName:'',
				pageNo:1,
				pageSize:10,
				Focus:{},
				Focus2:{},
				Fun:'',
			}
		},
		onUnload(){
    	},
		onLoad() {
			this.title = this.$Route.query.title
			this.title1 = this.$Route.query.NameKey
			this.id = this.$Route.query.id
			this.userName = uni.getStorageSync('login_user_info').username || '';
			// this.getDetails()
		},
		methods: {
			remain2(){
				this.$Router.push({
					path: '/pages/zx/zx29-1',
					params:{
						query01:this.conditionObj.query01,
						title:this.title,
						id:this.id
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
			scanClick(e){
				this.typescan = e
				this.$Router.push({name: 'barcode',params:{typescan:e}})
			},
			// scroll(){
				// this.pageNo++
				// this.getDetails()
			// },
			preserve(){
				if(!this.conditionObj.query02) return uni.showToast({ icon:'none', title: '请扫描储位'})
				let arr = []
				let that = this
				// console.log(this.data,132)
				this.data.map(item => {
					arr.push({
						goodsQua: item.goodsQua,
						id: item.id,
						tinId: item.tinId,
						binTo :this.conditionObj.query02,
					})
				})
				this.$refs.uniModel.open({
					content: that.$t(`是否转移到${this.conditionObj.query02}`), 
					cancelText:'否', 
					confirmText:'是',
					success(res) {
						if (res.confirm) {
							that.goTO(arr)
						}
					}
				})
			},
			goTO(data){
				uni.showLoading({title: '加载中',mask:true})
				this.$http.put('/jeewms/wmToMoveGoods/editBatch',data).then(res => {
					uni.showToast({ title:res.data.message,icon:'none' })
					uni.hideLoading()
					if(res.data.code == 200){
						this.data = []
						this.conditionObj.query02 = ''
					}
				})	
			},
			getDetails2(){
				if(!this.conditionObj.query01){return uni.showToast({ icon:'none', title: this.$t('请扫描箱码')})}
				// this.Focus.isFocus1 = false
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					moveSta: '计划中',
					tinFrom:this.conditionObj.query01
				};
				this.$http.get('/jeewms/wmToMoveGoods/list',{params:data}).then(res => {
					// this.Focus.isFocus2 = true
					if(res.data.code == 500) return uni.showToast({title: this.$t('pages.' + res.data.message) ,icon:'none'})
					this.conditionObj.query01 = ''
					let records = res.data.result.records
					let obj = {}
					if(records.length == 0){
						return uni.showToast({title: this.$t('请确认是否存在该箱码') ,icon:'none'})
					}else{
						obj = records[0]
					}
					let i = ''
					this.data.map(item =>{
						if(item.tinId == obj.tinId){
							i = item.tinId
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
			padding: 10rpx 0 ;
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
			padding: 20rpx 33rpx;
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
				view{
					&:last-child{
						width: 60%;
						text-align: right;
					}
				}
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
			.btn{
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
