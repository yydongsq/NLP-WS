<template>
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery" :form="form">
        <a-row :gutter="24">
          <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <a-form-item label="数据集名称">
              <a-select
                v-decorator="[
                'DataSetName', // 给表单赋值或拉取表单时，该input对应的key
                {rules: [{ required: true, message: '请选择数据集名称!' }]}
                ]"
                placeholder="请选择数据集名称"
                :value="DataSetName"
                @change="handleDataSetChange">
                <a-select-option v-for="d in dataSets" :key="d.id" :value="d.id">{{d.dtName}}</a-select-option>
              </a-select>
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
                <a-select-option v-for="m in models" :key="m.id" :value="m.id">{{m.modelName}}</a-select-option>
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
              <a-button type="primary" @click="searchDataSet" icon="file-text">查看数据集</a-button>
              <a-button type="primary" @click="searchQuery" icon="bar-chart" style="margin-left: 8px">进行分词</a-button>
              <a-button type="primary" @click="searchPSResult" icon="file-search" style="margin-left: 8px">查看分词结果</a-button>
              <a-button type="primary" @click="searchPartOfSpeech" icon="question-circle" style="margin-left: 8px">词性说明</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
      <a-modal v-model:visible="visible" :title="modalTitle" @ok="handleOk">
        <PartOfSpeech :ModelPosId = "ModelPosId" v-show = "PSVisible"></PartOfSpeech>
        <div v-show="DataSetVisible">{{dataSetContent}}</div>
        <div v-show="DataSetResultVisible">{{dataSetResultContent}}</div>
      </a-modal>
      <!--
       定义一个on的方法监听子组件的状态（接收子组件向父组件传值），此处v-on:DataSetResult也可以定义为@DataSetResult
      -->
      <!--
       通过ref调用子组件的方法
       ref被用来给元素或子组件注册引用信息。引用信息将会注册在父组件的 $refs对象上。如果在普通的 DOM 元素上使用，引用指向的就是 DOM 元素；如果用在子组件上，引用就指向该子组件实例
       通俗的讲，ref特性就是为元素或子组件赋予一个ID引用,通过this.$refs.refName来访问元素或子组件的实例
       this.$refs是一个对象，持有当前组件中注册过 ref特性的所有 DOM 元素和子组件实例
       -->
      <WSIndex :ShowModel = "ShowModel"
               :DataSet = "DataSet"
               :dataSetId = "dataSetId"
               :checked = "checked"
               :ModelPosId = "ModelPosId"
               ref="child"
               v-on:DataSetResultAll="DataSetResultAll">
      </WSIndex>
    </div>
    <!-- 查询区域-END -->
</template>
<script>
  import WSIndex from '@/views/jeecg/nlp/WS/WSIndex.vue'
  import PartOfSpeech from '@/views/jeecg/nlp/WS/PartOfSpeech/PartOfSpeech.vue'
  import { getAction } from '@/api/manage'  //调用main.js中的通用请求方法
  import {message} from "ant-design-vue";

    export default {
        name: "DataSetWS",
        components: {
          WSIndex,
          PartOfSpeech
        },
      data(){
        return{
          checked:false,  //词性展示开启关闭
          visible:false,  //对话框开启关闭
          DataSetName: undefined,
          ModelName: undefined,
          DataSet: '',  //双向绑定传递给子组件
          ShowModel:'', //双向绑定传递给子组件
          models:[],  //模型下拉列表数组
          dataSets:[],  //数据集名称下拉列表数组
          dataSetContent:"",  //数据集内容
          dataSetResultContent:"",  //分词结果内容
          dataSetId:"", //选中的数据集id
          modalTitle:"",  //对话框标题
          PSVisible:false,  //分词标注集展示关闭
          DataSetVisible:false, //数据集展示关闭
          DataSetResultVisible:false, //分词结果展示关闭
          ModelPosId:"",  //模型对应的词性标注集ID
          ModelPosName:"",  //模型对应的词性标注集名称
          url: {
            getModelData:"/jeecg-demo/mynlp/tbNlpModel/list", //请求后台模型数据API接口
            getDataSetData:"/jeecg-demo/mynlp/tbNlpDataset/list", //请求后台数据集数据API接口
            getModelDataByStatus:"/jeecg-demo/mynlp/tbNlpModel/listByStatus", //根据模型状态查询列表
          },
          form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
        }
      },
      //在页面创建成功后执行加载下拉列表的数据
      created() {
        let modelUrl = this.url.getModelDataByStatus;
        let dataSetUrl = this.url.getDataSetData;
        let param = {
          'model_status':"已启用"
        }
        this.loadData(modelUrl,"model",param);
        this.loadData(dataSetUrl,"dataSet",null);
      },
      methods: {
        //进行分词
        searchQuery(){
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
          this.form.validateFields((err, values) => {
            console.log("err = " + err);
            //判断必填项校验是否通过
            if (!err) {
              //判断在上一次调用模型失败之后再次进行分词有没有更改数据集和模型，以防止引起监视属性改变而重复调用模型
              if(this.DataSet === this.dataSetContent && this.ShowModel === this.ModelName){
                //在调用超时再次点击进行分词调用子组件的方法尝试重新进行分词
                this.$refs.child.getUrl({});
              }else{
                //通过双向绑定改变监视属性调用模型
                this.DataSet = this.dataSetContent;
                this.ShowModel = this.ModelName;
                console.log("values = " + values);
              }
            }
          })
        },
        //查看数据集
        searchDataSet(){
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！(对指定字段进行校验，第一个参数是一个数组，数组里是指定字段的名称)
          this.form.validateFields(['DataSetName'],(err) => {
            console.log("err = " + err);
            //判断必填项校验是否通过
            if (!err) {
              this.modalTitle = this.DataSetName;
              this.visible = true;
              this.PSVisible = false;
              this.DataSetResultVisible = false;
              this.DataSetVisible = true;
            }
          })
        },
        //是否开启展示词性功能
        onChange(checked) {
          this.checked = checked;
        },
        //查看词性说明
        searchPartOfSpeech(){
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！(对指定字段进行校验，第一个参数是一个数组，数组里是指定字段的名称)
          this.form.validateFields(['ModelName'],(err) => {
            console.log("err = " + err);
            //判断必填项校验是否通过
            if (!err) {
              this.modalTitle = this.ModelPosName;
              this.visible = true;
              this.DataSetVisible = false;
              this.DataSetResultVisible = false;
              this.PSVisible = true;
            }
          })
        },
        //关闭词性说明对话框窗口
        handleOk(){
          this.visible = false;
        },
        //将输入框置为空
        searchReset(){
          this.DataSetName = undefined;
          this.ModelName = undefined;
          this.checked = false;
          //加入v-decorator后无法使用v-model进行双向数据绑定，通过 setFieldsValue() 方法进行数据改变
          this.form.setFieldsValue({
            DataSetName: undefined,
            ModelName: undefined,
            checked: false
          })
        },
        //发送axios异步请求加载数据
        loadData(url,type,param) {
          getAction(url,param).then((res) => {
            console.info("res.success = " + res.success);
            if (res.success) {
              if(type === "model"){
                this.models = res.result;
              }
              if(type === "dataSet"){
                this.dataSets = res.result.records;
              }
            }else{
              message.warning('请求超时，请重试！',2)
            }
          })
        },
        //绑定选中的模型名称
        handleModelChange(value){
          let obj = {};
          //从当前models数组中寻找
          obj = this.models.find(function (item) {
            //判断id相等
            return item.id === value;
          });
          //obj就是被选中的那个对象
          console.info("obj.modelPosName = " + obj.modelPosName);
          console.info("obj.modelPosId = " + obj.modelPosId);
          this.ModelName = obj.modelName;
          this.ModelPosName = obj.modelPosName; //模型对应的词性标注集名称
          this.ModelPosId = obj.modelPosId; //模型对应的词性标注集ID
        },
        //绑定选中的数据集名称(传进来的value是select组件选中的value值)
        handleDataSetChange(value){
          let obj = {};
          //从当前dataSets数组中寻找
          obj = this.dataSets.find(function (item) {
            //判断id相等
            return item.id === value;
          });
          console.info("obj.dtName = " + obj.dtName);
          console.info("obj.id = " + obj.id);
          //obj就是被选中的那个对象
          this.dataSetId = value;
          this.DataSetName = obj.dtName;
          this.dataSetContent = obj.dtText;
        },
        //查看分词结果内容
        searchPSResult(){
          if(this.dataSetResultContent !== ''){
            this.modalTitle = this.DataSetName;
            this.visible = true;
            this.DataSetVisible = false;
            this.PSVisible = false;
            this.DataSetResultVisible = true;
          }else{
            message.warning("请先进行分词！",2);
          }
        },
        //子组件传值给父组件(dataSetResult就是子组件传过来的值)
        DataSetResultAll(dataSetResultAll){
          if(dataSetResultAll !== false){
            this.dataSetResultContent = dataSetResultAll;
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