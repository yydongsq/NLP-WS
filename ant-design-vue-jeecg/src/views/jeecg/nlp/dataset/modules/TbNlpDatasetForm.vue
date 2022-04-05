<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="数据集类型编码" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtTypeCode">
              <a-input v-model="model.dtTypeCode" placeholder="请输入数据集类型编码"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数据集类型名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtTypeName">
              <a-input v-model="model.dtTypeName" placeholder="请输入数据集类型名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数据集编号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtNo">
              <a-input v-model="model.dtNo" placeholder="请输入数据集编号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数据集名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtName">
              <a-input v-model="model.dtName" placeholder="请输入数据集名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数据集内容" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtText">
              <j-editor v-model="model.dtText" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数据集大小" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtSize">
              <a-input-number v-model="model.dtSize" placeholder="请输入数据集大小" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数据集创建人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtCreateBy">
              <a-input v-model="model.dtCreateBy" placeholder="请输入数据集创建人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数据集创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtCreateTime">
              <j-date :show-time="true" date-format="YYYY-MM-DD" placeholder="请选择数据集创建时间" v-model="model.dtCreateTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dtMemo">
              <a-textarea v-model="model.dtMemo" rows="4" placeholder="请输入备注" />
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
    name: 'TbNlpDatasetForm',
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
           dtTypeCode: [
              { required: true, message: '请输入数据集类型编码!'},
           ],
           dtTypeName: [
              { required: true, message: '请输入数据集类型名称!'},
           ],
           dtNo: [
              { required: true, message: '请输入数据集编号!'},
           ],
           dtName: [
              { required: true, message: '请输入数据集名称!'},
           ],
           dtText: [
              { required: true, message: '请输入数据集内容!'},
           ],
           dtSize: [
              { required: true, message: '请输入数据集大小!'},
              { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},
           ],
           dtCreateBy: [
              { required: true, message: '请输入数据集创建人!'},
           ],
           dtCreateTime: [
              { required: true, message: '请输入数据集创建时间!'},
           ],
        },
        url: {
          add: "/jeecg-demo/mynlp/tbNlpDataset/add",
          edit: "/jeecg-demo/mynlp/tbNlpDataset/edit",
          queryById: "/jeecg-demo/mynlp/tbNlpDataset/queryById"
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