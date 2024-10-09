<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">库存查询</block>
		</cu-custom>
		<view class="Search" style="flex-direction: column;">
			<!-- <input type="text" v-model="query01" :focus="isFocus1"  @confirm="getDetails(1)"  placeholder="请扫描储位信息"  />
			<input type="text" v-model="query02" :focus="isFocus2" @confirm="getDetails(2)" placeholder="请扫描商品编码" style="margin-top: 16rpx;" /> -->
			
			<view class="saoma_father1" >
				<input class="input_first" type="text" :focus="isFocus1" v-model="query01"  @confirm="getDetails(1)" :placeholder="$t('请扫描储位信息')" />
				<image @click="scanClick('query01')" src="../../static/ZX/sm.png" class="saoma" ></image>
			</view>
			
			<view class="saoma_father2" >
				<input class="input_second" type="text" :focus="isFocus2" v-model="query02"  @confirm="getDetails(2)" :placeholder="$t('请扫描商品编码')" style="margin-top:10rpx;"/>
				<image @click="scanClick('query02')" src="../../static/ZX/sm.png" class="saoma" ></image>
			</view>	
		</view>
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key=	'index' >
				<view class="item">
					<view>商品名称：</view>
					<view style="color: #2D2F2E;">{{item.shpMingCheng}}</view>
				</view>
				<view class="item">
					<view>商品编码：</view>
					<view style="color: #2D2F2E;">{{item.goodsCode}}</view>
				</view>
				<view class="item">
					<view>生产日期：</view>
					<view style="color: #2D2F2E;">{{item.goodsProData}}</view>
				</view>
				<view class="item">
					<view>储位：</view>
					<view style="color: #2D2F2E;">{{item.kuWeiBianMa}}</view>
				</view>
				<view class="item">
					<view>托盘：</view>
					<view style="color: #2D2F2E;">{{item.binId}}</view>
				</view>
				<view class="item">
					<view>数量：</view>
					<view style="color: #3297E2">{{item.goodsQua}}</view>
				</view>
				<view class="item">
					<view>单位：</view>
					<view style="color: #2D2F2E;">{{item.goodsUnit}}</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
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
			}
		},
		onLoad() {
			this.userName = uni.getStorageSync('login_user_info').userName || '';
			// this.getDetails();
			let that  = this
			uni.$off('scan-listener')
			uni.$on('scan-listener', (screenData)=>{
				that[screenData.typescan] = screenData.result 
				that.getDetails()
				that.data = []
			})
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
			getDetails(e) { // 获取收货列表
			    if(this.query01 == '' && e == 2 || this.query01 == '' && e == 1){
			    	this.data = []
			    	this.isFocus1 =  false
			    	this.isFocus2 =  true
			    	this.pageNo = 1
					return
			    }
				let params = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username: this.userName,
					searchstr: this.query01,
					searchstr2: this.query02
				}
				console.log(params,'------------');
				this.$http.get('/jeewms/rest/pdaapi/wvStockController/list', {
					params
				}).then(res => {
					this.data = res.data.obj
					if(!this.data.length) {
						this.query01 = ''
						this.query02 = ''
					}
				})
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
