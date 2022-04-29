<template>
  <a-card :bordered="false">
    <a-tabs defaultActiveKey="1" @change="callback">
      <a-tab-pane tab="柱状图" key="1">
        <a-form layout="inline" :form="form">
          <a-row :gutter="12">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="词性名称">
                <a-select
                  v-decorator="[
                  'posTagsAndMeaning', // 给表单赋值或拉取表单时，该input对应的key
                  {rules: [{ required: true, message: '请选择词性名称!' }]}
                  ]"
                  placeholder="请选择词性名称"
                  :value="posTagsAndMeaning"
                  @change='handlePosChange'>
                  <a-select-option v-for="p in poses" :key="p.id" :value="p.id">{{p.posTags}}/{{p.posMeaning}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-button type="primary" @click="selectPos" icon="search">开始筛选</a-button>
              </span>
            </a-col>
            <bar class="statistic" :title="ShowReloadModel" :dataSource="countSource" :DataSetResult="DataSetResult" :height="400"/>
          </a-row>
        </a-form>
      </a-tab-pane>
      <a-tab-pane tab="饼状图" key="2">
        <a-form layout="inline" :form="form">
          <a-row :gutter="24">
            <a-col :xl="5" :lg="7" :md="8" :sm="24">
              <a-form-item label="词性名称">
                <a-select
                  v-decorator="[
                    'posTagsAndMeaning', // 给表单赋值或拉取表单时，该input对应的key
                    {rules: [{ required: true, message: '请选择词性名称!' }]}
                    ]"
                  placeholder="请选择词性名称"
                  :value="posTagsAndMeaning"
                  @change='handlePosChange'>
                  <a-select-option v-for="p in poses" :key="p.id" :value="p.id">{{p.posTags}}/{{p.posMeaning}}</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="selectPos" icon="search">开始筛选</a-button>
                </span>
            </a-col>
            <pie class="statistic" :title="ShowReloadModel" :dataSource="countSource" :DataSetResult="DataSetResult" :height="450"/>
          </a-row>
        </a-form>
      </a-tab-pane>
    </a-tabs>
  </a-card>
</template>

<script>
  import Bar from '@/components/chart/Bar'
  import Pie from '@/components/chart/Pie'
  import ACol from 'ant-design-vue/es/grid/Col'
  import { putNlpAction,getAction } from '@/api/manage'
  import { message, Button } from 'ant-design-vue';

  export default {
    name: 'WSIndex',
    //接收父组件传来的属性
    //props:['ShowModel'],
    props: {
      ShowModel: "",  //用户选中的模型
      DataSet: "",  //用户自输入的文本内容
      checked: false,  //用户是否需要展示分词词性
      dataSetId: "", //用户选中的数据集ID
      ModelPosId: "", //用户选中的模型对应的词性标注集ID
    },
    components: {
      ACol,
      Bar,
      Pie
    },
    data() {
      return {
        queryParam: {}, // 查询条件
        countSource: [],  //图表数据源数组
        tabStatus:"bar",  //统计图类型，默认进行分词时展示柱状图
        ShowReloadModel: '',  //加载成功的模型名称
        ShowModelUpdate: false, //判断模型是否改变
        DataSetResult:"", //当前组件与柱状图和饼状图子组件进行传值，并且传递给父组件的分词结果展示
        posTag:"", //词性标签
        posTagsAndMeaning:undefined, //词性标签和含义，定义为undefined为了让placeholder显示
        poses:[], //词性明细数组
        json_DataSet:[],  //调用接口得到的原始分词结果
        json_SelectDataSet:[],  //筛选后的分词结果
        url: {
          getHanLPWS:"/jeecg-demo/mynlp/hanlp/hanLPWS",
          getJiebaWS:"/jeecg-demo/mynlp/jieba/jiebaWS",
          getLtpWS:"/jeecg-demo/mynlp/ltp/ltpWS",
          getThulacWS:"/jeecg-demo/mynlp/thulac/thulacWS",
          queryTbNlpPosDetailListByMainId:"/jeecg-demo/mynlp/tbNlpPos/queryTbNlpPosDetailByMainId", //通过自然语言词性标注集明细主表ID查询
        },
        form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
      }
    },
    methods: {
      loadData(url,param,dtTextJsonString) {
        const loding = message.loading('模型加载中，请稍后...',0); //第二个参数设置为0一直显示加载中
        putNlpAction(url,param,dtTextJsonString).then((res) => {
          loding(); //关闭正在加载弹窗
          if (res.success) {
            message.success('模型加载成功！',2);
            this.json_DataSet = []; //在每次重新分词前刷新分词结果数组（置空）
            this.searchReset(); //重新进行全分词后将词性下拉列表置空
            this.getPosDetail();  //模型加载成功后为词性下拉列表赋值
            let json_Data = JSON.parse(res.result);
            this.json_DataSet = json_Data.data;
            this.ShowReloadModel = this.ShowModel + "分词结果"; //分词后图表左上方展示的模型名称
            this.getWSData(this.json_DataSet);
          }else{
            message.warning('请求超时，请重试！',2)
            //DataSetResult是在父组件on监听的方法，第二个参数DataSetResult是需要传的值
            this.$emit('DataSetResult', false);
          }
        })
      },
      getWSData(data){
        console.info("checked = " + this.checked);
        if(data.length ===  0){
          message.warning("分词结果中不含指定词性！",2)
        }else{
          let wordOrNature = "";
          this.DataSetResult = ""; //在每次重新分词前刷新分词结果数据数据（防止内容叠加显示）
          this.countSource = [];  //在每次重新分词前刷新图表数据源数组（置空）
          for (let i = 0; i < data.length; i++) {
            wordOrNature = this.checked === true ? data[i].word + "/" + data[i].nature : data[i].word;
            this.DataSetResult += " [ " + wordOrNature + " ] ";
            if(this.tabStatus === "bar"){
              this.countSource.push({
                x: wordOrNature,
                y: 650
              })
            }else{
              this.countSource.push({
                item: wordOrNature,
                count:2
              })
            }
          }
          //DataSetResult是在父组件引用改组件时定义的v-on（或@）监听方法，第二个参数DataSetResult是需要传的值
          this.$emit('DataSetResult', this.DataSetResult);
        }
      },
      // 选择统计图类别
      callback(key) {
        this.searchReset(); //重新选择统计图类别后将词性下拉列表置空
        if(this.ShowModel === ""){
          message.warning("请先进行分词！",2);
        }else{
          if(key === "1"){
            this.tabStatus = "bar";
            this.getWSData(this.json_DataSet);  //将分词数据重置为全分词模式
          }else{
            this.tabStatus = "pie";
            this.getWSData(this.json_DataSet);  //将分词数据重置为全分词模式
          }
        }
      },
      // 进行词性筛选
      selectPos() {
        //如果没有选中模型并进行分词操作进行分词提示，否则进行必填项校验提示，基本逻辑为只有进行分词之后并进行了词性筛选选择才会触发分词筛选结果展示
        if(this.ShowModel === ""){
          message.warning("请先进行分词！",2);
        }else{
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！(对指定字段进行校验，第一个参数是一个数组，数组里是指定字段的名称)
          this.form.validateFields(['posTagsAndMeaning'],(err) => {
            console.log("err = " + err);
            //判断必填项校验是否通过
            if (!err) {
              this.json_SelectDataSet = []; //重新进行词性选中后清空上一个筛选的数据
              //如果这样直接赋值改变赋值后的数组会造成原数组的数据也改变，因为JavaScript存储对象时存储的是地址，浅拷贝导致两个数组指向同一块内存地址。
              //this.json_SelectDataSet = this.json_DataSet;
              //深拷贝（slice、concat）
              let Dataset = [].concat(this.json_DataSet);//或者Dataset = this.json_DataSet.slice(0)效果相同
              this.json_SelectDataSet = Dataset;
              let SelectDataSet = this.json_SelectDataSet;
              for (let i = 0; i < SelectDataSet.length; i++) {
                if(SelectDataSet[i].nature !== this.posTag){
                  SelectDataSet.splice(i, 1);//从i开始（包括i，i从0开始）删除一个元素
                  i--; //删除数组指定元素之后，将i重新定位在删除的位置跟此时位置上的新元素重新比较
                }
              }
              this.getWSData(SelectDataSet);
            }
          })
        }
      },
      // 选择请求url
       getUrl(param){
        let showModel = this.ShowModel;
        let dataSetId = this.dataSetId;
        let url = "";
        let dtText = {};  //初始化用户自定义输入的文本内容对象
         // 判断如果不是调用数据集时对文本内容对象进行属性赋值
        if(dataSetId === ""){
          dtText = {
            "dtText":this.DataSet
          };
        }
        let dtTextJsonString = JSON.stringify(dtText);
        if(showModel === "HanLP"){
          param = {
            dataSetId:dataSetId
          };
          url = this.url.getHanLPWS;
        }
        if(showModel === "Thulac"){
          param = {
            dataSetId:dataSetId
          };
          url = this.url.getThulacWS;
        }
        if(showModel === "Jieba"){
          param = {
            dataSetId:dataSetId
          };
          url = this.url.getJiebaWS;
        }
        if(showModel === "Ltp"){
          param = {
            dataSetId:dataSetId
          };
          url = this.url.getLtpWS;
        }
        if(url !== ""){
          this.loadData(url,param,dtTextJsonString);
        }else{
          if(showModel === ""){
            message.warning("请先进行分词！",2);
          }else{
            message.warning("该模型未封装！",2);
          }
        }
      },
      handlePosChange(value){
        let obj = {};
        //从当前models数组中寻找
        obj = this.poses.find(function (item) {
          //判断id相等
          return item.id === value;
        });
        //obj就是被选中的那个对象
        this.posTag = obj.posTags; //词性的标签
        this.posTagsAndMeaning = obj.posTags + "/" + obj.posMeaning; //将词性标签和含义进行组合显示在下拉列表
      },
      //将将词性下拉列表置为空
      searchReset(){
        this.posTagsAndMeaning = undefined;
        //加入v-decorator后无法使用v-model进行双向数据绑定，通过 setFieldsValue() 方法进行数据改变
        this.form.setFieldsValue({
          posTagsAndMeaning: undefined
        })
      },
      //获取词性标注集明细
      getPosDetail(){
        let requestUrl = this.url.queryTbNlpPosDetailListByMainId;
        let param = {
          "id" : this.ModelPosId
        }
        getAction(requestUrl,param).then((res) => {
          console.info("res.success = " + res.success);
          if (res.success) {
            this.poses = res.result;
          }else{
            message.warning('请求超时，请重试！',2)
          }
        })
      },
    },
    created() {
    },
    watch:{
      //将ShowModel作为监视属性
      ShowModel:{
        //immediate:true, //初始化时让handler调用一下
        deep:true,//深度监视
        handler(newValue,oldValue){
          this.showModelUpdate = true;
          this.getUrl({});
        }
      },
      //将DataSet作为监视属性
      DataSet:{
        //immediate:true, //初始化时让handler调用一下
        deep:true,//深度监视
        handler(newValue,oldValue){
          //解决数据集和模型同时改变时重复调用gatUrl方法
          if(this.showModelUpdate === false){
            this.getUrl({});
          }else{
            //解决数据集改变而模型不改变时不调用gatUrl方法
            this.showModelUpdate = false;
          }
        }
      },
      // //将ModelPosId作为监视属性
      // ModelPosId:{
      //   //immediate:true, //初始化时让handler调用一下，此时不使用初始化调用，因为这个组件和父组件同时加载，而父组件的模型列表还没有选中，此时id就为空，发起请求失败。
      //   deep:true,//深度监视
      //   handler(newValue,oldValue){
      //     let requestUrl = this.url.queryTbNlpPosDetailListByMainId;
      //     let param = {
      //       "id" : newValue
      //     }
      //     getAction(requestUrl,param).then((res) => {
      //       console.info("res.success = " + res.success);
      //       if (res.success) {
      //         this.poses = res.result;
      //       }else{
      //         message.warning('请求超时，请重试！',2)
      //       }
      //     })
      //   }
      // }
    }
  }
</script>
<style scoped>
  .ant-card-body .table-operator {
    margin-bottom: 18px;
  }

  .ant-table-tbody .ant-table-row td {
    padding-top: 15px;
    padding-bottom: 15px;
  }

  .anty-row-operator button {
    margin: 0 5px
  }

  .ant-btn-danger {
    background-color: #ffffff
  }

  .ant-modal-cust-warp {
    height: 100%
  }

  .ant-modal-cust-warp .ant-modal-body {
    height: calc(100% - 110px) !important;
    overflow-y: auto
  }

  .ant-modal-cust-warp .ant-modal-content {
    height: 90% !important;
    overflow-y: hidden
  }

  .statistic {
    padding: 0px !important;
    margin-top: 50px;
  }

  .statistic h4 {
    margin-bottom: 20px;
    text-align: center !important;
    font-size: 24px !important;;
  }

  .statistic #canvas_1 {
    width: 100% !important;
  }
</style>