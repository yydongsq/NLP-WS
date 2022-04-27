<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="模型类型编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelTypeCode">
              <a-input v-model="model.modelTypeCode" placeholder="请输入模型类型编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="模型类型名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelTypeName">
              <a-input v-model="model.modelTypeName" placeholder="请输入模型类型名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="模型编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelNo">
              <a-input v-model="model.modelNo" placeholder="请输入模型编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="模型名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelName">
              <a-input v-model="model.modelName" placeholder="请输入模型名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="模型来源" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelSource">
              <a-input v-model="model.modelSource" placeholder="请输入模型来源"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="模型创建人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelCreatBy">
              <a-input v-model="model.modelCreatBy" placeholder="请输入模型创建人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="模型创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelCreatTime">
              <j-date placeholder="请选择模型创建时间"  v-model="model.modelCreatTime" :show-time="true" date-format="YYYY-MM-DD" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="词性标注集" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="modelPosName">
              <a-select
                placeholder="请选择词性标注集"
                v-model="model.modelPosName"
                @change='handlePosChange'>
                <a-select-option v-for="p in pos" :key="p.id" :value="p.id">{{p.posName}}</a-select-option>
              </a-select>
              <!--<a-input v-model="model.modelPosName" placeholder="请输入词性标注集"  ></a-input>-->
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="memo">
              <a-textarea v-model="model.memo" rows="4" placeholder="请输入备注" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'
  import {message} from "ant-design-vue";

  export default {
    name: 'TbNlpModelForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
           modelTypeCode: [
              { required: true, message: '请输入模型类型编码!'},
           ],
           modelTypeName: [
              { required: true, message: '请输入模型类型名称!'},
           ],
           modelNo: [
              { required: true, message: '请输入模型编号!'},
           ],
           modelName: [
              { required: true, message: '请输入模型名称!'},
           ],
           modelSource: [
              { required: true, message: '请输入模型来源!'},
           ],
           modelCreatBy: [
              { required: true, message: '请输入模型创建人!'},
           ],
           modelCreatTime: [
              { required: true, message: '请输入模型创建时间!'},
           ],
          modelPosName: [
              { required: true, message: '请选择词性标注集!'},
           ],
        },
        pos:[],
        url: {
          add: "/jeecg-demo/mynlp/tbNlpModel/add",
          edit: "/jeecg-demo/mynlp/tbNlpModel/edit",
          queryById: "/jeecg-demo/mynlp/tbNlpModel/queryById",
          getPosData: "/jeecg-demo/mynlp/tbNlpPos/list"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
      if(this.disabled === false){
        let posDataUrl = this.url.getPosData;
        this.loadData(posDataUrl,null);
      }
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }

        })
      },
      handlePosChange(value){
        console.info("pos.id = " + value);
        let obj = {};
        //从当前pos数组中寻找
        obj = this.pos.find(function (item) {
          //判断id相等
          return item.id === value;
        });
        //obj就是被选中的那个对象
        this.model.modelPosId = value;
        //如果直接给model.modelPosName赋值，会导致select上对应的数据绑定不生效（即选中option后不显示），因为vue 无法监听动态新增的属性的变化，需要用 $set 来为这些属性赋值。
        //Vue无法监听新增的属性值的变化，需要通过set 来进行设置。或者在model:{}中手动加入所有属性。
        this.$set(this.model, this.model.modelPosName, obj.posName);
        console.info("pos.posName = " + obj.posName);
        this.model.modelPosName = obj.posName;
        this.$forceUpdate();
      },
      //发送axios异步请求加载数据
      loadData(url,param) {
        getAction(url,param).then((res) => {
          console.info("res.success = " + res.success);
          if (res.success) {
            this.pos = res.result.records;
          }else{
            message.warning('请求超时，请重试！',2)
          }
        })
      },
    }
  }
</script>