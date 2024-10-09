<template>
	<view>
		<cu-custom bgColor="head2" :isBack="true">
			<block slot="backText"></block>
			<block slot="content">扫一扫</block>
		</cu-custom>
		<titheader :titContent='titContent'>
			<!-- <view class="more" @click="$refs.remark.open()" slot='elseR'>
				<image src="../../static/contant/more.png" mode=""></image>
			</view> -->
		</titheader>
		<!-- 扫码页面 -->
		<!-- #ifndef APP-PLUS -->
		<view class="wrap">
			<view class="u-tips-color">
				请在app中打开
			</view>
		</view>
		<!-- #endif -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				barcode: null,
				flash: false,
				typescan:'',
				tip: '将二维码放入框中，即可自动扫描',
				titContent:{
					isShow:true,
					title:'扫码',
					IconColor:'#fff',
					TextColor:'#fff',
					background:'#54ADBC'
				},
			}
		},
		onShow() {
			// 页面展示时，重新启动扫描检测
			if (this.barcode) {
				this.barcode.start()
			}
		},
		onLoad(params) {
			const {
				tip
			} = params
			if (tip) {
				this.tip = tip
			}
			this.typescan = this.$Route.query.typescan
			// #ifdef APP-PLUS
			let currentWebview = this.$scope.$getAppWebview();
			this.createBarcode(currentWebview)
			this.createTipInfoView(currentWebview)
			this.createFlashBarView(currentWebview)
			// #endif
		},
		onUnload() {
			// #ifdef APP-PLUS
			// plus.navigator.setFullscreen(false); // 不全屏
			// #endif
		},
		mounted() {

		},
		methods: {
			/**
			 * 创建二维码
			 * @param {Object} currentWebview
			 */
			createBarcode(currentWebview) {
				if (!this.barcode) {
					this.barcode = plus.barcode.create('barcode', [plus.barcode.QR,plus.barcode.CODE128], {
						top: `0px`,
						left: '0px',
						height: `100%`,
						width: '100%',
						position: 'absolute',
						background: 'transparent',
						frameColor: '#2C313C',
						scanbarColor: '#2C313C',
					});
					this.barcode.onmarked = this.onmarked;
					this.barcode.setFlash(this.flash);
					//此处未演示扫码成功回调的地址设置，实际请参考HTML5Plus API自行处理  
					//注意扫码区域需为正方形，否则影响扫码识别率  
					currentWebview.append(this.barcode);
				}
				this.barcode.start()
			},

			/**
			 * 创建提示信息
			 * @param {Object} currentWebview
			 */
			createTipInfoView(currentWebview) {
				const content = new plus.nativeObj.View('content', {
						top: '0px',
						left: '0px',
						height: '100%',
						width: '100%'
					},
					[{
						tag: 'font',
						id: 'scanTips',
						text: this.tip,
						textStyles: {
							size: '14px',
							color: '#ffffff',
							whiteSpace: 'normal'
						},
						position: {
							top: '90px',
							left: '10%',
							width: '80%',
							height: 'wrap_content'
						}
					}]);
				currentWebview.append(content);

			},
			// 创建 开关灯按钮
			createFlashBarView(currentWebview) {

				const openImg = this.crtFlashImg('static/yellow-scanBar.png')
				const closeImg = this.crtFlashImg('static/scanBar.png')
				const scanBarVew = new plus.nativeObj.View('scanBarVew', {
						top: '65%',
						left: '40%',
						height: '10%',
						width: '20%',
					},
					closeImg);
				scanBarVew.interceptTouchEvent(true);

				currentWebview.append(scanBarVew);

				scanBarVew.addEventListener("click", (e) => { //点亮手电筒
					this.flash = !this.flash;
					if (this.flash) {
						scanBarVew.draw(openImg);
					} else {
						scanBarVew.draw(closeImg)
					}
					if (this.barcode) {
						this.barcode.setFlash(this.flash);
					}
				}, false)
			},
			crtFlashImg(imgsrc) {
				return [{
					tag: 'img',
					id: 'scanBar',
					src: imgsrc,
					position: {
						width: '28%',
						left: '36%',
						height: '30%'
					}
				}, {
					tag: 'font',
					id: 'font',
					text: '轻触照亮',
					textStyles: {
						size: '10px',
						color: '#ffffff'
					},
					position: {
						width: '80%',
						left: '10%'
					}
				}]
			},
			// 扫码成功回调
			onmarked(type, result) {
				console.log('条码类型：' + type);
				console.log('条码内容：' + result, this.typescan);
				uni.$emit('scan-listener', { result: result,typescan:this.typescan })
				uni.navigateBack({ delta: 1 });
				// 业务代码  
				// 核对扫描结果
				// 判断是否是正确的格式
				// 不正确则跳转到 错误页面
					
			}

		}
	}
</script>

<style scoped>
	.wrap {
		height: calc(100vh);
		/* #ifdef H5 */
		height: calc(100vh - var(--window-top));
		/* #endif */
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}
</style>