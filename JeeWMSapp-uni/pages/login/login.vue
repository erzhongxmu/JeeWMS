<template>
	<view class="zai-box">
		<scroll-view scroll-y class="page">
			<view style="text-align: center;" :style="[{animation: 'show ' + 0.4+ 's 1'}]">
				<image src="/static/login.png" mode='aspectFit' class="zai-logo"></image>
				<view class="zai-title">JEEWMS</view>
			</view>
			<view class="box padding-lr-xl login-paddingtop" :style="[{animation: 'show ' + 0.6+ 's 1'}]">

				<view class="cu-form-group margin-top round shadow-blur">
					<view class="title">{{$t('账号')}}:</view>
					<input :placeholder="$t('请输入账号')" name="input" v-model="userName"></input>
				</view>
				<view class="cu-form-group margin-top round shadow-blur" style="border-top: 0;">
					<view class="title">{{$t('密码')}}:</view>
					<input :placeholder="$t('请输入密码')" name="input" type="password" v-model="password"></input>
				</view>
				<!-- <view class="cu-form-group margin-top round">
					<view class="title">地址:http://</view>
					<input placeholder="请输服务器地址:192.168.0.1" name="input" v-model="iphost"></input>
				</view> -->
				<!-- onLogin -->
				<view class="padding  flex  flex-direction">
					<button v-if="PROJECT != 'SW'" class="cu-btn bg-rgb shadow-blur round lg" @tap="verifyUrl"> {{loading ?$t('登录中!'):$t('登录')}}</button>
					<button v-else class="cu-btn bg-rgb shadow-blur round lg" @tap="onLogin"> {{loading ? $t('登录中!'):$t('登录')}}</button>
				</view>
				<!-- #ifdef APP-PLUS -->
				<view class="padding flex flex-direction  text-center  ">
					{{$t('版本')}}:{{version}}
				</view>
				<!-- #endif -->

			</view>
		</scroll-view>
		<!-- 登录加载弹窗 -->
		<view class="cu-load load-modal" v-if="loading">
			<!-- <view class="cuIcon-emojifill text-orange"></view> -->
			<image src="/static/login.png" mode="aspectFit"></image>
			<view class="gray-text">{{$t('登录中!')}}</view>
		</view>
		
		<!-- BASE_URL 弹窗 -->
		<uni-popup ref="BASE_URL">
			<view class="box_Language">
				<view class="title">{{$t('地址配置')}}</view>
				<view class="item_input">
					<view class="title_item">IP:</view>
					<input type="text" value="" v-model="url.ip"/>
				</view>
				<view class="item_input">
					<view class="title_item">{{$t('端口')}}:</view>
					<input type="text" value="" v-model="url.port"/>
				</view>
				<view class="padding  flex  flex-direction">
					<button class="cu-btn bg-rgb shadow-blur round lg" @tap="setUrl">{{$t('message.confirm')}}</button>
				</view>
			</view>
		</uni-popup>

	</view>

</template>

<script>
	import {
		ACCESS_TOKEN,
		USER_NAME,
		USER_INFO
	} from "@/common/util/constants"
	import {
		mapActions
	} from "vuex"
	import myImageUpload from "@/components/my-componets/my-image-upload.vue"
	import mypage from "@/components/my-componets/my-page.vue"
	import myselect from "@/components/my-componets/my-select.vue"

	export default {
		components: {
			'my-image-upload': myImageUpload,
			'my-select': myselect
		},
		data() {
			return {
				url:{},
				modalName: null,
				ewmFocus: false,
				loading: false,
				userName: '',
				password: '',
				iphost: '',
				phoneNo: '',
				smsCode: '',
				showPassword: false, //是否显示明文
				loginWay: 1, //1: 账密，2：验证码
				smsCountDown: 0,
				smsCountInterval: null,
				toggleDelay: false,
				version: '',
				innerAudioContext: this.innerAudioContext,
				gpEwm: '',
			};
		},
		onLoad: function() {
			
			var base_url = uni.getStorageSync('base_url')
			if(base_url){
				let port = base_url.substr(base_url.lastIndexOf(':')+1)
				let ip = base_url.substring(base_url.lastIndexOf('/')+1, base_url.lastIndexOf(':'))
				this.url = {port, ip}
			}
			if (uni.getStorageSync('iphost')) {
				this.iphost = uni.getStorageSync('iphost')
			}
			// #ifdef APP-PLUS
			var that = this
			plus.runtime.getProperty(plus.runtime.appid, function(wgtinfo) {
				that.version = wgtinfo.version
			});
			// #endif
		},
		methods: {
			...mapActions(["mLogin", "PhoneLogin"]),
			hideModal(e) {
				this.modalName = null
				this.ewmFocus = false
			},
			handleEwmModal() {
				if (this.gpEwm.trim().length === 0) {
					this.modal.show({
						type: 'error',
						content: '请扫描工牌二维码'
					})
					return
				}
				this.$http.get('/sys/user/checkGp', {
					params: {
						checkcode: this.gpEwm
					}
				}).then(res => {
					if (res.data.success) {
						this.modal.show({
							type: 'success',
							content: this.$t('登录成功')
						})
						this.$Router.replaceAll({
							name: 'index'
						})
					} else {
						this.modal.show({
							type: 'error',
							content: res.data.message
						})
					}

				})
			},
			verifyUrl(){
				if (!this.userName || this.userName.length == 0) {
					// this.$tip.toast('请填写用户名');
					this.modal.show({
						type: 'warn',
						content: this.$t('请输入账号')
					})
					return;
				}
				if (!this.password || this.password.length == 0) {
					// this.$tip.toast('请填写密码');
					this.modal.show({
						type: 'warn',
						content:  this.$t('请输入密码')
					})
					return;
				}
				let BASE_URL = uni.getStorageSync('base_url')
				if(BASE_URL){
					// this.onLogin()
					this.$refs['BASE_URL'].open();
				}else{
					this.$refs['BASE_URL'].open();
				}
			},
			setUrl(){
				let ip = this.url.ip +':' + this.url.port
				var pattIp= /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\:([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/;
				// if(pattIp.test(ip)){
				// 	uni.setStorageSync('base_url', "http://" +  ip)
				// 	this.onLogin()
				// }else{
				// 	this.modal.show({ type: 'warn', content: this.$t('格式不正确')})
				// }
				uni.setStorageSync('base_url', "http://" +  ip)
				this.onLogin()
			},
			onLogin: function() {
				// if (!this.iphost || this.iphost.length == 0) {
				// 	this.modal.show({
				// 		type: 'warn',
				// 		content: '请填写请求地址'
				// 	})
				// 	return;
				// }
				uni.setStorageSync('iphost', this.iphost)
				let loginParams = {
					userName: this.userName,
					password: this.password
				}
				this.loading = true;
				
				this.mLogin(loginParams).then((res) => {
					console.log("mLogin", res)
					this.loading = false;
					if (res.data.success) {
						// this.$tip.success('登录成功!')
						// this.modalName = 'ewmModal'
						// this.ewmFocus = true
						// return;
						this.modal.show({
							type: 'success',
							content: '登录成功!'
						})
						this.$Router.replaceAll({
							name: 'index'
						})
						/* uni.reLaunch({
							url: '/pages/index/index'
						  }); */
					} else {
						// this.$tip.alert(res.data.message);
						this.modal.show({
							type: 'warn',
							content: res.data.message
						})
					}
				}).catch((err) => {
					let msg = err.data.message || "请求出现错误，请稍后再试"
					// this.$tip.alert(msg);
					this.modal.show({
						type: 'warn',
						content: msg
					})
				}).finally(() => {
					this.loading = false;
				})
			}
		}
	}
</script>

<style lang="less">
	.box uni-input {
		font-size: 15px;
	}

	.box .uni-input-placeholder {
		font-size: 15px;
	}

	.login-paddingtop {
		padding-top: 200 upx;
	}

	.zai-box {
		padding: 0 20 upx;
		padding-top: 100 upx;
		position: relative;
	}

	.zai-logo {
		width: 200upx;
		height: 300 upx;
	}

	.zai-title {
		font-size: 58upx;
		color: #000000;
		text-align: center;
	}

	.input-placeholder,
	.zai-input {
		color: #94afce;
	}

	.zai-label {
		padding: 60 upx 0;
		text-align: center;
		font-size: 30 upx;
		color: #a7b6d0;
	}

	.zai-btn {
		background: #ff65a3;
		color: #fff;
		border: 0;
		border-radius: 100 upx;
		font-size: 36 upx;
	}

	.zai-btn:after {
		border: 0;
	}

	/*按钮点击效果*/
	.zai-btn.button-hover {
		transform: translate(1 upx, 1 upx);
	}
	.box_Language {
		width: 80%;
		max-height: 600rpx;
		background: #fff;
		border-radius: 20rpx;
		overflow: auto;
		padding: 50rpx;
		padding-top: 24rpx;
		padding-bottom: 0;
		.title{
			width: 100%;
			font-size: 35rpx;
			text-align: center;
		}
		.item_input{
			width: 100%;
			height: 70rpx;
			display: flex;
			margin: 10rpx 0;
			view{
				width: 15%;
				height: 100%;
				line-height: 70rpx;
				font-size: 30rpx;
			}
			input{
				flex: 1;
				height: 100%;
				font-size: 30rpx;
				border-bottom: 1px solid #e9e9e9;
				padding: 0 10rpx;
			}
		}
	}
</style>
