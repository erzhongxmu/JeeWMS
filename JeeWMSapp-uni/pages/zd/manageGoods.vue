<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">商品资料</block>
		</cu-custom>
		<view class="Search" >
			<!-- <view class="saoma_father2" >
				<input  type="text" value="" :focus="isFocus2" v-model="query01"  @confirm="getDetails(1)" placeholder="请扫描商品编码"/>
			</view>	 -->
			<view class="saoma_father2" >
				<input class="input_second" type="text" :focus="isFocus2" v-model="query01"  @confirm="getDetails(1)" :placeholder="$t('请扫描商品编码')" style="margin-top:10rpx;"/>
				<image @click="scanClick('query01')" src="../../static/ZX/sm.png" class="saoma" ></image>
			</view>	
		</view>
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
				<view class="item">
					<view>商品编码：</view>
					<view style="color: #2D2F2E;">{{item.shpBianMa || ''}}</view>
				</view>
				<view class="item">
					<view>商品名称：</view>
					<view style="color: #2D2F2E;">{{item.shpMingCheng || ''}}</view>
				</view>
 
				 <view class="inputText">
				 	<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
				 		<text>商品条码：</text>
				 		<input v-model="item.shpTiaoMa" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
				 	</view>
				 </view>
				<view class="inputText">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>重量(KG)：</text>
						<input v-model="item.zhlKg" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="inputText">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>体积(cm³)：</text>
						<input v-model="item.tiJiCm" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="inputText">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>长(cm)：</text>
						<input v-model="item.chZhXiang" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="inputText">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>宽(cm)：</text>
						<input v-model="item.kuZhXiang" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
				<view class="inputText">
					<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
						<text>高(cm)：</text>
						<input v-model="item.gaoZhXiang" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
					</view>
				</view>
			 <view class="inputText">
			 	<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
			 		<text>码盘单层：</text>
			 		<input v-model="item.mpDanCeng" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
			 	</view>
			 </view>
			 <view class="inputText">
			 	<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
			 		<text>码盘层高：</text>
			 		<input v-model="item.mpCengGao" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
			 	</view>
			 </view>
			 <view class="inputText">
			 	<view class="" style="display: flex; width: 100%; align-items: center;justify-content: space-between;">
			 		<text>保质期：</text>
			 		<input v-model="item.bzhiQi" style=" color: #3297E2;text-align: right;padding-right: 20rpx;width: 300rpx;margin-top: 0;"   placeholder-style="color:#E0E2E4"/>
			 	</view>
			 </view>
				<view class="btn" @click="preserve(item)">保存</view>
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
				typescan:'',
			}
		},
		onUnload(){
        	uni.$off('scan-listener')
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
			preserve(item, index) {
			let data = {
				updateBy: this.userName,
				id: item.id,
				mpDanCeng: item.mpDanCeng,
				mpCengGao: item.mpCengGao,
				shpTiaoMa: item.shpTiaoMa,
				bzhiQi: item.bzhiQi,
				tiJiCm: item.tiJiCm,
				zhlKg: item.zhlKg,
				chZhXiang: item.chZhXiang,
				kuZhXiang: item.kuZhXiang,
				gaoZhXiang: item.gaoZhXiang
			}
			// if(!data.mpDanCeng) {
			// 	uni.showToast({title: '请输入码盘单层' ,icon:'none'})
			// }
			// if(!data.mpCengGao) {
			// 	uni.showToast({title: '请输入码盘层高' ,icon:'none'})
			// }
			// if(!data.shpTiaoMa) {
			// 	uni.showToast({title: '请输入商品条码' ,icon:'none'})
			// }
			// if(!data.bzhiQi) {
			// 	uni.showToast({title: '请选择保质期（天）' ,icon:'none'})
			// }
			// if(!data.tiJiCm) {
			// 	uni.showToast({title: '请输入体积' ,icon:'none'})
			// }
			// if(!data.zhlKg) {
			// 	uni.showToast({title: '请输入重量' ,icon:'none'})
			// }
			// if(!data.chZhXiang) {
			// 	uni.showToast({title: '请输入长' ,icon:'none'})
			// }
			// if(!data.kuZhXiang) {
			// 	uni.showToast({title: '请输入宽' ,icon:'none'})
			// }
			// if(!data.gaoZhXiang) {
			// 	uni.showToast({title: '请输入高' ,icon:'none'})
			// }
			let url = `/jeewms/rest/pdaapi/mdGoodsController/save?mdGoodsstr=${JSON.stringify(data)}`
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
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username:this.userName,
					searchstr:this.query01,
				}
				console.log(123231231,data,'--------------');
				this.$http.get('/jeewms/rest/pdaapi/mdGoodsController/list',{params:data}).then(res => {
					console.log(123231231,res,'------');
					this.data = res.data.obj
				})	
			},
			bindPickerChange(e,i,key,list){
				let index = e.detail.value;
				this.$nextTick(()=>{
					this.$set(this.data[i], 'query10', list[index].query01)	
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
			height: 100rpx;
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
					margin-top: 16rpx;
					
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
	.search-select{
		display: flex;
		width: 92.66%;
		height: 35px;
		border-radius: 30px;
		background-color: #F3F4F6;
		.search-picker{
			display: flex;
			align-items: center;
		}
	}
	.select-input{
		width: 300rpx !important;
		display:flex;
		align-items: center;
		height: 34px;
		border: 1px solid #3297E2;
		border-radius: 5px;
		padding-right: 20rpx;
		input{
			border: none !important;
		}
	}
</style>
