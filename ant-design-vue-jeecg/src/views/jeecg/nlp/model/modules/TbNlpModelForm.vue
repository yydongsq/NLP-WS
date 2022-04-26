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
              <a-input v-model="model.modelPosName" placeholder="请输入词性标注集"  ></a-input>
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
              { required: true, message: '请输入词性标注集!'},
           ],
        },
        url: {
          add: "/jeecg-demo/mynlp/tbNlpModel/add",
          edit: "/jeecg-demo/mynlp/tbNlpModel/edit",
          queryById: "/jeecg-demo/mynlp/tbNlpModel/queryById"
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
    }
  }
</script>