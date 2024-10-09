<template>
	<view class="uni-combox" v-clickoutside="clickOut">
		<view v-if="label" class="uni-combox__label" :style="labelStyle">
			<text>{{label}}:</text>
		</view>
		<view class="uni-combox__input-box">
			<input class="uni-combox__input" type="text" :placeholder="placeholder" v-model="inputVal" @input="onInput"
			 @focus="onFocus" @blur="onBlur" />
			<uni-icons class="uni-combox__input-arrow" type="arrowdown" size="14" @click="toggleSelector"></uni-icons>
			<view class="uni-combox__selector" v-if="showSelector">
				<scroll-view scroll-y="true" class="uni-combox__selector-scroll">
					<view class="uni-combox__selector-empty" v-if="filterCandidatesLength === 0">
						<text>{{emptyTips}}</text>
					</view>
					<view class="uni-combox__selector-item" v-for="(item,index) in filterCandidates" :key="index" @click="onSelectorClick(index)">
						<text>{{content ? item[content] : item}}</text>
					</view>
				</scroll-view>
			</view>
		</view>
	</view>
</template>

<script>
	/**
	 * Combox 组合输入框
	 * @description 组合输入框一般用于既可以输入也可以选择的场景
	 * @tutorial https://ext.dcloud.net.cn/plugin?id=1261
	 * @property {String} label 左侧文字
	 * @property {String} labelWidth 左侧内容宽度
	 * @property {String} placeholder 输入框占位符
	 * @property {Array} candidates 候选项列表
	 * @property {String} emptyTips 筛选结果为空时显示的文字
	 * @property {String} value 组合框的值
	 * @property {String} inputFilter 用于控制是否输入框下拉筛选
	 * @property {String} content 指定下拉框显示字段，不传则为item本身
	 * @property {String} keys 指定返回值，不传则返回为item本身
	 */
	export default {
		name: 'uniCombox',
		props: {
			label: {
				type: String,
				default: ''
			},
			labelWidth: {
				type: String,
				default: 'auto'
			},
			placeholder: {
				type: String,
				default: ''
			},
			candidates: {
				type: Array,
				default () {
					return []
				}
			},
			emptyTips: {
				type: String,
				default: '无匹配项'
			},
			value: {
				type: [String, Number],
				default: ''
			},
			inputFilter: {
				type:Boolean,
				default:true
			},
			content: {
				type:String,
				default:''
			},
			keys: {
				type:String,
				default:''
			}
		},
		data() {
			return {
				showSelector: false,
				inputVal: ''
			}
		},
		computed: {
			labelStyle() {
				if (this.labelWidth === 'auto') {
					return {}
				}
				return {
					width: this.labelWidth
				}
			},
			filterCandidates() {
				return this.candidates.filter((item) => {
					if(this.content){
						return this.inputFilter ? item[this.content].toString().indexOf(this.inputVal) > -1 : item[this.content]
					}else{
						return this.inputFilter ? item.toString().indexOf(this.inputVal) > -1 : item
					}
				})
			},
			filterCandidatesLength() {
				return this.filterCandidates.length
			}
		},
		watch: {
			value: {
				handler(newVal) {
					this.inputVal = newVal
				},
				immediate: true
			}
		},
		methods: {
			clickOut(){
				this.showSelector = false
			},
			toggleSelector() {
				this.showSelector = !this.showSelector
			},
			onFocus() {
				this.showSelector = true
			},
			onBlur() {
				setTimeout(() => {
					this.showSelector = false
				}, 153)
			},
			onSelectorClick(index) {
				this.inputVal = this.content ? this.filterCandidates[index][this.content] : this.filterCandidates[index]
				let reBackVal = this.keys ? this.filterCandidates[0][this.keys] : this.inputVal
				this.showSelector = false
				this.$emit('change', reBackVal)
			},
			onInput() {
				setTimeout(() => {
					this.$emit('input', this.inputVal)
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.uni-combox {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		height: 40px;
		flex-direction: row;
		align-items: center;
		// border-bottom: solid 1px #DDDDDD;
	}

	.uni-combox__label {
		font-size: 14px;
		line-height: 22px;
		padding-right: 10px;
		color: #333333;
	}

	.uni-combox__input-box {
		position: relative;
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex: 1;
		flex-direction: row;
		align-items: center;
	}

	.uni-combox__input {
		flex: 1;
		font-size: 16px;
		height: 22px;
		line-height: 22px;
		.uni-input-placeholder {
		    font-size: 14px;
		}
	}
	.uni-combox__input-box {
		border: 1px solid #d7d7d7;
		border-radius: 5px;
		padding: 0 0 0 5px;
		box-sizing: border-box;
		height: 29px !important;
	}

	.uni-combox__input-arrow {
		padding: 10px;
	}

	.uni-combox__selector {
		/* #ifndef APP-NVUE */
		box-sizing: border-box;
		/* #endif */
		position: absolute;
		top: 42px;
		left: 0;
		width: 100%;
		background-color: #FFFFFF;
		border-radius: 6px;
		box-shadow: #DDDDDD 4px 4px 8px, #DDDDDD -4px -4px 8px;
		z-index: 2;
	}

	.uni-combox__selector-scroll {
		/* #ifndef APP-NVUE */
		max-height: 200px;
		box-sizing: border-box;
		/* #endif */
	}

	.uni-combox__selector::before {
		/* #ifndef APP-NVUE */
		content: '';
		/* #endif */
		position: absolute;
		width: 0;
		height: 0;
		border-bottom: solid 6px #FFFFFF;
		border-right: solid 6px transparent;
		border-left: solid 6px transparent;
		left: 50%;
		top: -6px;
		margin-left: -6px;
	}

	.uni-combox__selector-empty,
	.uni-combox__selector-item {
		/* #ifndef APP-NVUE */
		display: flex;
		cursor: pointer;
		/* #endif */
		word-break: break-all;
		line-height: 36px;
		font-size: 14px;
		text-align: left;
		border-bottom: solid 1px #DDDDDD;
		margin: 0px 10px;
	}

	.uni-combox__selector-empty:last-child,
	.uni-combox__selector-item:last-child {
		/* #ifndef APP-NVUE */
		border-bottom: none;
		/* #endif */
	}
</style>
