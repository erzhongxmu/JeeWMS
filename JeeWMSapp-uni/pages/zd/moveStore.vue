<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">移储</block>
		</cu-custom>
		<view class="Search" style="flex-direction: column;">
			<!-- <input type="text" v-model="query01" :focus="isFocus1"  @confirm="getDetails(1)" placeholder="请扫描储位信息" />
			<input type="text" v-model="query02" :focus="isFocus2" @confirm="getDetails(1)" placeholder="请扫描商品编码" style="margin-top: 16rpx;" /> -->
			<view class="saoma_father1" >
				<input class="input_first" type="text" :focus="isFocus1" v-model="query01"  @confirm="getDetails(1)" :placeholder="$t('请扫描储位编码')" />
				<image @click="scanClick('query01')" src="../../static/ZX/sm.png" class="saoma" ></image>
			</view>
			<view class="saoma_father2" >
				<input class="input_second" type="text" :focus="isFocus2" v-model="query02"  @confirm="getDetails(1)" :placeholder="$t('请扫描商品编码')" style="margin-top:10rpx;"/>
				<image @click="scanClick('query02')" src="../../static/ZX/sm.png" class="saoma" ></image>
			</view>	
		</view>	
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
				<view class="item">
					<view>产品名称：</view>
					<view style="color: #2D2F2E;">{{item.goodsName}}</view>
				</view>
				<view class="item">
					<view>产品编码：</view>
					<view style="color: #2D2F2E;">{{item.goodsId}}</view>
				</view>
				<view class="item">
					<view>单位：</view>
					<view style="color: #2D2F2E;">{{item.goodsUnit}}</view>
				</view>
				<view class="item">
					<view>储位：</view>
					<view style="color: #2D2F2E;">{{item.binFrom}}</view>
				</view>
				<view class="item">
					<view>托盘：</view>
					<view style="color: #2D2F2E;">{{item.tinFrom}}</view>
				</view>
				<view class="item">
					<view>数量：</view>
					<view style="color: #3297E2">{{item.baseGoodscount}}</view>
				</view>
				<view class="inputText">
					<view>{{$t('pages.生产日期')}}：</view>
					<dyDatePicker placeholder="请选择" @getData="(e)=> dyDatePickerChange(e,index)" :childValue="item.goodsProData" class="inputText_itm" />
				</view>
				<view class="inputText">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>移储储位：</text>
						<input v-model="conditionObj['binTo']" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"  value=""  placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="inputText">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>到托盘：</text>
						<input v-model="conditionObj['tinId']" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"  value=""  placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="inputText"  style="border-bottom: 1px dashed #DBDBDB;">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>移储数量：</text>
						<input v-model="conditionObj['goodsQua']" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;" type="number" value=""  placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="btn"  @click="preserve(item)">移储</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import dyDatePicker from './../component/dyDatePicker'
	export default {
		data() {
			return {
				title:'',
				title1:'',
				userName:'',
				query01:'',
				query02:'',
				data:[],
				pageNo:1,
				pageSize:10,
				isFocus1: true,
				isFocus2: false,
				conditionObj: {},
			}
		},
		onLoad() {
			this.userName = uni.getStorageSync('login_user_info').userName || '';
			// this.getDetails();
			
			let that  = this
			uni.$off('scan-listener')
			uni.$on('scan-listener', (screenData)=>{
				that[screenData.typescan] = screenData.result 
				that.getDetails(1)
				that.data = []
			})
		},
		components:{
			dyDatePicker
		},
		methods: {
			scanClick(e){
				this.typescan = e
				this.$Router.push({path: '/components/barcode/barcode',params:{typescan:e}})
			},
			scroll(){
				this.pageNo++
				this.getDetails()
			},
			preserve(item, index) {
				let data = {
					updateBy: this.userName,
					id: item.id,
					baseGoodscount: item.baseGoodscount,
					tinId: this.conditionObj['tinId'],
					binTo: this.conditionObj['binTo'],
					goodsQua: this.conditionObj['goodsQua'],
					toGoodsProData: item.goodsProData
				}
				if(!data.binTo) {
					uni.showToast({title: '请输入移储储位' ,icon:'none'})
				}
				if(!data.toGoodsProData) {
					uni.showToast({title: '请输入移储数量' ,icon:'none'})
				}
				let url = `/jeewms/rest/pdaapi/wmToMoveGoodsController/save?wmToMoveGoodsstr=${JSON.stringify(data)}`
				let that = this
				that.goOn(data, url)
			},
			
			goOn(data,url){
				let that = this
				for (var key in data) {
					data[key] = String(data[key]).trim()
				}
				this.$http.post(url).then(res => {
					if (res.data.ok) {
						uni.showToast({title: '操作成功' ,icon:'none'})
						that.conditionObj = {}
						that.getDetails()
					} else {
						uni.showToast({title: res.data.errorMsg ,icon:'none'})
					}
				})
			},
			getDetails(e){
				if(e == 1){
					this.data = []
					this.isFocus1 =  false
					this.isFocus2 =  true
					this.pageNo = 1
				}
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username:this.userName,
					searchstr:this.query01,
					Searchstr2:this.query02
				}
				console.log(data,'------------');
				this.$http.get('/jeewms/rest/pdaapi/wmToMoveGoodsController/list',{params:data}).then(res => {
					this.data = res.data.obj
				})	
			},
			dyDatePickerChange(e,index){
				this.data[index]['goodsProData'] = e
			},
			bindPickerChange(e,i,key,list){
				let index = e.detail.value;
				this.$nextTick(()=>{
					this.$set(this.data[i], key, list[index].query01)	
				})
			}
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
		.Search {
			display: flex;
			flex-direction: column;
			width: 100%;
			height: 177rpx;
			background: #FFFFFF;
			display: flex;
			justify-content: center;
			align-items: center;
			.saoma_father1 {
				display: flex;
				width: 92.5%;
				.saoma {
					width: 55rpx;
					height: 55rpx; 
					margin-left: 10rpx;
					margin-top: 10rpx;
					
				}
				
				.input_first {
					flex: 1;
					
				}
				
			}
			
			.saoma_father2 {
				display: flex;
				width: 92.5%;
				.saoma {
					width: 55rpx;
					height: 55rpx; 
					margin-left: 10rpx;
					margin-top: 25rpx;
					
				}
				
				.input_second {
					flex: 1;
					margin-top: 16rpx;
					
				}
				
			}
		
			input {
				padding-left: 36rpx;
				width: 92.66%;
				height: 70rpx;
				background: #F3F4F6;
				border-radius: 30px;
				font-size: 28rpx;
				font-weight: 400;
			}
		}
		.body_t{
			width: 	92.53%;
			min-height: 500rpx;
			margin: 27rpx auto;
			background: #FFFFFF;
			border-radius: 10rpx;
			padding: 20rpx 33rpx;
			.title {
				width: 100%;
				height: 70rpx;
				position: relative;
				line-height: 60rpx;
				color: #3297E2;
				font-size: 34rpx;
			
				.posi_left {
					height: 30rpx;
					width: 6rpx;
					background: #3297E2;
					position: absolute;
					left: -32rpx;
					top: 15rpx;
				}
			
				.posi_right {
					height: 56rpx;
					width: 104rpx;
					background: #3297E2;
					position: absolute;
					right: -24px;
					top: 10rpx;
					font-size: 28rpx;
					line-height: 40rpx;
					color: #fff;
					text-align: center;
					background-size: 100% 100%;
					background-repeat: no-repeat;
				}
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
				margin-top: 10rpx;
				width: 100%;
				display: flex;
				justify-content: space-between;
				// border-bottom: 1px dashed #DBDBDB;
				padding-bottom: 31rpx;
				text{
					font-size: 28rpx;
					font-weight: 400;
					color: #666666;
				}
				view{
					width: 35%;
				}
				input{
					margin-top: 25rpx;
					height: 68rpx;
					border: 1px solid #3297E2;
					text-align: center;
					font-size: 28rpx;
					font-weight: 400;
					border-radius: 5px;
				}
				.inputText_itm {
					text-align: right;
					width: 47.5%;
					height: 60rpx;
					border-radius: 10rpx;
					border: 1px solid #3297E2;
					color: #3297E2;
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
	
</style>
