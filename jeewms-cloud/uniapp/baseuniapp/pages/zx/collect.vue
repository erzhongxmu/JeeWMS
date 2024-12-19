<template>
	<view class="content">
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">{{title1}}</block>
		</cu-custom>
		<view class="Search">
			<!-- <input type="text" :focus="isFocus1" v-model="query01"  @confirm="getDetails(1)" :placeholder="$t('pages.PleaseScanTheOrderNumber')" />
			<input type="text" :focus="isFocus2" v-model="query02"  @confirm="getDetails(1)" :placeholder="$t('pages.PleaseScanTheProduct')" style="margin:4rpx 0;"/> -->
			<view v-for="(item,index) in condition.search">
				<input  type="text" :focus="Focus['isFocus'+(index+1)]" v-model="conditionObj[item.query10]"  @confirm="getDetails(index+1)" :placeholder="$t('pages.'+item.query08)"  style="margin:8rpx 0;"/>
				<image class="clear" src="../../static/ZX/clear.png" mode="" @click="close(item.query10)" v-show="conditionObj[item.query10]"></image>
			</view>
		</view>
		<scroll-view scroll-y="true" style="flex: 1;overflow: auto;" @scrolltolower='scroll'>
			<view class="body_t" v-for="(item,index) in data" :key=	'index'>
				<view  v-for="(v,i) in condition.list">
					<view class="item" v-if="v.query04 == 'text'">
						<view>{{$t('pages.'+v.query03)}}：</view>
						<view style="color: #2D2F2E;max-width: 70%;">{{item[v.query02] || ''}}</view>
					</view>
					<view class="inputText"  v-if="v.query04 == 'input'">
						<text>{{$t('pages.'+v.query03)}}：</text>
						<input v-model="item[v.query02]" class="inputText_itm" :type="v.query09 || 'text'" :focus="Focus2[index + 'isFocus' + i]"  @confirm="focusFun(index,i)" />
					</view>

					<view class="inputText" v-if="v.query04 == 'date'">
						<text>{{$t('pages.'+v.query03)}}：</text>
						<myDate  v-model="item[v.query02]" class="inputText_itm"></myDate>
					</view>
				</view>
				<view v-for="(v,i) in condition.button" :key='i'  class="btn" @click="preserve(item,v)">{{$t('pages.'+v.query03)}}</view>
			</view>
		</scroll-view>
		<model ref='uniModel'></model>
	</view>
</template>

<script>
	import {dateFormat} from "@/common/util/tools"
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
				Focus:{},
				Focus2:{},
				Fun:'',
			}
		},
		components:{
		},
		onLoad() {
			this.title = this.$Route.query.title
			this.title1 = this.$Route.query.NameKey
			this.id = this.$Route.query.id
			this.userName = uni.getStorageSync('login_user_info').username || '';
			this.getdata()
		},
		methods: {
			close(str) {
				this.conditionObj[str] = ''
				this.data = []
			},
			addfocus(arr,arr1){
				let obj = JSON.parse(JSON.stringify(this.Focus2))
				arr.map((item,index)=>{
					arr1.map((v,i )=>{
						if(v.query04 == 'input'){
							obj[index + 'isFocus' + i] = false
						}
					})
				})
				this.Focus2 = obj
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
			scroll(){
				this.pageNo++
				this.getDetails()
			},

			preserve(item,fun){
				let data = { ...item, username: this.userName, listtype: this.title };
				let that = this
				switch(that.title) {
				     case '采购入库':
				        this.$refs.uniModel.open({ content: this.$t('pages.是否需要打印箱唛'), cancelText:'否', confirmText:'是',
				        	success(res) {
				        		if (res.confirm) {
									data.query16 = 1
				        			that.goOn(data)
				        		}
				        	},
							cancel(){
								that.goOn(data)
							}
				        })
				        break;
				     case '生产入库':
				        this.$refs.uniModel.open({ content: this.$t('pages.是否需要打印箱唛'), cancelText:'否', confirmText:'是',
				        	success(res) {
				        		if (res.confirm) {
				        			data.query16 = 1
				        			that.goOn(data)
				        		}
				        	},
				        	cancel(){
				        		that.goOn(data)
				        	}
				        })
				        break;
				     default:
						that.goOn(data)
				}

			},
			goOn(data){
				let that = this
				for (var key in data) {
					data[key] = String(data[key]).trim()
				}
				uni.showLoading({ title: that.$t('加载中'), mask: true });
				this.$http.post('/jeewms/pdaPapi/postdata', data).then(res => {
					uni.showToast({title: this.$t('pages.' + res.data.message) ,icon:'none'})
					if (res.data.code == 200) {
						that.data = [];
						that.pageNo = 1;
						that.getDetails();
					}
				})
			},
			getPrint(e){
				// 入库标签打印
				new Promise((resolve)=>{
					this.$http.get('/jeewms/wmImNoticeH/labelPrinting',{params:{id:e}}).then(res => {resolve()})
				}).then(()=>{
					this.$http.get('/jeewms/wmImNoticeH/labelPrints',{params:{id:e}}).then(res => {})
				})
			},
			getDetails(e){
				if(e){
					this.data = []
					this.pageNo = 1
				}
				let data = {
					pageNo:this.pageNo,
					pageSize:this.pageSize,
					username:this.userName,
					listtype:this.title,
					...this.conditionObj
				};
				this.$http.get('/jeewms/pdaGapi/getlist',{params:data}).then(res => {
					if(res.data.code == 500) return uni.showToast({title: this.$t('pages.' + res.data.message) ,icon:'none'})
					if(e){
						this.Focus['isFocus'+(e)] = false
						this.Focus['isFocus'+(e + 1)] = true
					}
					if(this.title == '采购入库'){
						let time = dateFormat(new Date())
						res.data.result.records.map(item=>{
							item.query07 = time
						})
					}
					this.data = this.data.concat(res.data.result.records)
					this.addfocus(this.data,this.condition.list)
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
			padding: 20rpx 33rpx;
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
