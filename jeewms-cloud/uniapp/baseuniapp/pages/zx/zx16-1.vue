<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{query01}}</block>
		</cu-custom>
		<view class="total">
			剩余{{total}}箱
		</view>
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" lower-threshold="30" @scrolltolower="scrollLower">
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
				<view  v-for="(v,i) in condition.list" v-if="v.query03 != '储位'">
					<view class="item" v-if="v.query04 == 'text'">
						<view>{{$t('pages.'+ v.query03)}}：</view>
						<view 
						:style="{color: v.query03 != '托盘号'?'#2D2F2E':'#3396E2',fontSize: v.query03 != '托盘号'?'28rpx':'32rpx',maxWidth:'70%'}" 
						>{{item[v.query02] || ''}}</view>
					</view>
				</view>
			</view>
			<uni-load-more :status="status"></uni-load-more>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				query01:'',
				data:[],
				pageNo:1,
				pageSize:10,
				userName:'',
				title:'',
				id:'',
				condition:{},
				allNum:1,
				status: 'more',
				total:0
			}
		},
		onLoad() {
			this.query01 = this.$Route.query.query01
			this.title = this.$Route.query.title
			this.id = this.$Route.query.id
			this.userName = uni.getStorageSync('login_user_info').username || '';
			this.data = []
			this.getdata()
			this.getList()
		},
		methods: {
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
							if(item.query04 != 'button'){
								list.push(item)
							}
						}
					})
					 this.condition = {
						 list
					 }
				})
			},
			scrollLower() {
				if (this.pageNo >= this.allNum) {
					this.status = "noMore"
					return
				} else {
					this.status = "loading"
				}
				this.pageNo++
				this.getList()
			},
			getList(){
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username:this.userName,
					listtype:this.title,
					query01:this.query01
				};
				this.$http.get('/jeewms/pdaGapi/getlist',{params:data}).then(res => {
					if(res.data.code == 500) return uni.showToast({title: this.$t('pages.' + res.data.message) ,icon:'none'})
					console.log(res.data.result.pages)
					this.allNum = res.data.result.pages
					this.total = res.data.result.total
					this.status = "noMore"
					this.data = [...this.data,...res.data.result.records]
				});
			}
		}
	}
</script>

<style  scoped lang="less">
	.content{
		width: 100%;
		height: 100vh;
		background-color: #F3F4F6;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		.total{
			font-size: 24rpx;
			color: #3297E2;
			line-height: 60rpx;
			margin-left: 20rpx;
		}
		.body_t{
			width: 	92.53%;
			min-height: 500rpx;
			margin: 0rpx auto 27rpx;
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
