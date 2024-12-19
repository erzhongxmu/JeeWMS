<template>
		<view class="cu-form-group" @click="timechange">
			<view class="title"><text class="text-red" v-if="required">*</text>{{label}}</view>
			<input
			  :placeholder="placeholder"
			   name="input"
			   v-model="selected"
			   disabled="true"
			></input>
			<w-picker
			    :visible.sync="visible"
				ref="picker" 
			    mode="date" 
			    startYear="2020" 
			    endYear="2100"
				:value="value"
			    :fields="fields"
			    @confirm="confirm"
				:current='true'
			 ></w-picker>
		</view>
</template>

<script>
	export default {
	        name: "AppSecelt",
	        props:{
	            placeholder:{
	                type:String,
	                default:'请选择',
	                required:false
	            },
	            label:{
	                type:String,
	                default:'',
	                required:false
	            },
				fields:{
				    type:String,
				    default:'second',
				    required:false
				},
	            value:{
	                type:String,
	                required:false
	            } ,
				required:{
	                type:Boolean,
					default:false,
	                required:false
	            }
	        },
	        data(){
	            return {
	                visible:false,
	                selected:''
	            }
	        },
	        watch:{
	            value:{
	                immediate:true,
	                handler(val){
	                  if(!val){
	                    this.selected = ''
	                  }else{
	                    this.selected = val;
	                }
	              }
	            }
	        },
	        created(){
				
	        },
	        methods:{
				timechange(){
					this.$refs.picker.show()
				},
	            confirm(e){
					let backString = e.value;
					this.selected = e.value;
	                this.$emit('input',backString);
	            }
	        },
	        model: {
	            prop: 'value',
	            event: 'input'
	        }
	    }
</script>
	
<style scoped>
	.cu-form-group{
		min-height: 45rpx;
		text-align: right;
		padding: 0;
		border: 1px solid #CCCCCC;
		width: 44%;
	}
</style>