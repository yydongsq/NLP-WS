<template>
  <div class="table-page-search-wrapper">
    <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
      <a-row :gutter="24">
        <a-col :xl="5" :lg="7" :md="8" :sm="24">
          <a-form-item label="数据集内容">
            <a-input
              v-decorator="[
                'DataContent', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请输入数据集内容!' }]}
                ]"
              placeholder="请输入数据集内容"
              v-model="DataContent">
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :xl="5" :lg="7" :md="8" :sm="24">
          <a-form-item label="模型名称">
            <a-select
              v-decorator="[
                'ModelName', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请选择模型名称!' }]}
                ]"
              placeholder="请选择模型名称"
              :value="ModelName"
              @change='handleModelChange'>
              <a-select-option v-for="m in models" :key="m.id" :value="m.modelName">{{m.modelName}}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :xl="3" :lg="7" :md="8" :sm="24">
          <a-form-item label="是否展示词性">
            <a-switch  :checked="checked" @change="onChange"></a-switch>
          </a-form-item>
        </a-col>
        <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">进行分词</a-button>
              <a-button type="primary" @click="searchPSResult" icon="file-search" style="margin-left: 8px">查看分词结果</a-button>
              <a-button type="primary" @click="searchPartOfSpeech" icon="question-circle" style="margin-left: 8px">词性说明</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
        </a-col>
      </a-row>
    </a-form>
    <a-modal v-model:visible="visible" :title="modalTitle" @ok="handleOk">
      <PartOfSpeech v-show="PSVisible"></PartOfSpeech>
      <div v-show="DataSetResultVisible">{{dataSetResultContent}}</div>
    </a-modal>
    <WSIndex
      :ShowModel = "ShowModel"
      :DataSet = "DataSet"
      :checked = "checked"
      ref="child"
      v-on:DataSetResult="DataSetResult">
    </WSIndex>
  </div>
</template>

<script>
  import WSIndex from '@/views/jeecg/nlp/WS/WSIndex.vue'
  import PartOfSpeech from '@/views/jeecg/nlp/WS/PartOfSpeech/PartOfSpeech.vue'
  import { getAction } from '@/api/manage'
  import { message, Button } from 'ant-design-vue';

  export default {
    name: 'TextWS',
    components: {
      WSIndex,
      PartOfSpeech
    },
    data() {
      return {
        checked:false,
        visible:false,
        DataContent: undefined,
        ModelName: undefined,
        DataSet: '',
        ShowModel:'',
        models:[],
        modalTitle:"",  //对话框标题
        PSVisible:false,  //分词标注集展示关闭
        DataSetResultVisible:false, //分词结果展示关闭
        dataSetResultContent:"",  //分词结果内容
        url: {
          getModelData:"/jeecg-demo/mynlp/tbNlpModel/listByStatus",
        },
        form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
      }
    },
    created() {
      let url = this.url.getModelData;
      this.loadModelData(url,null);
    },
    methods: {
      //进行分词
      searchQuery(){
        //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
        this.form.validateFields((err, values) => {
          //判断必填项校验是否通过
          if (!err) {
            //判断在上一次调用模型失败之后再次进行分词有没有更改数据集和模型，以防止引起监视属性改变而重复调用模型
            if( this.DataSet === this.DataContent && this.ShowModel === this.ModelName){
              //在调用超时再次点击进行分词调用子组件的方法尝试重新进行分词
              this.$refs.child.getUrl("WordSegmentation",{});
            }else{
              this.DataSet = this.DataContent;
              this.ShowModel = this.ModelName;
              console.log(values);
            }
          }
        })
      },
      //是否开启展示词性功能
      onChange(checked) {
        this.checked = checked;
      },
      //是否弹出词性说明对话框
      searchPartOfSpeech(){
        this.modalTitle = "863词性标注集";
        this.visible = true;
        this.DataSetResultVisible = false;
        this.PSVisible = true;
      },
      //关闭词性说明对话框窗口
      handleOk(){
        this.visible = false;
      },
      //将输入框置为空
      searchReset(){
        this.DataContent = undefined;
        this.ModelName = undefined;
        this.checked = false;
        //加入v-decorator后无法使用v-model进行双向数据绑定，通过 setFieldsValue() 方法进行数据改变
        this.form.setFieldsValue({
          DataContent: undefined,
          ModelName: undefined,
          checked: false
        })
      },
      loadModelData(url,param) {
        getAction(url,param).then((res) => {
          console.info("res.success = " + res.success);
          if (res.success) {
            this.models = res.result;
          }else{
            message.warning('请求超时，请重试！',2)
            //var that = this;
            //that.$message.warning(res.message);
          }
        })
      },
      handleModelChange(value){
        this.ModelName = value;
      },
      //查看分词结果内容
      searchPSResult(){
        if(this.dataSetResultContent !== ''){
          this.modalTitle = "分词结果";
          this.visible = true;
          this.PSVisible = false;
          this.DataSetResultVisible = true;
        }else{
          message.warning("请先进行分词！",2);
        }
      },
      //子组件传值给父组件(dataSetResult就是子组件传过来的值)
      DataSetResult(dataSetResult){
        if(dataSetResult !== false){
          this.dataSetResultContent = dataSetResult;
        }else{
          //调用超时
        }
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>