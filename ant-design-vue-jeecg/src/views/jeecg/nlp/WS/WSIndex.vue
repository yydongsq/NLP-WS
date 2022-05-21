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
                  <a-select-option v-for="p in poses" :key="p.id" :value="p.id">{{p.posTags}}/{{p.posMeaning}}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                <a-button type="primary" @click="selectPos" icon="search">开始筛选</a-button>
              </span>
            </a-col>
          </a-row>
          <a-row>
            <span v-show="pageVisiable">
            <a-button type="" @click="selectPage('first')" icon="">首页</a-button>
            <a-button :disabled="disabledBefore" style="margin-left: 5px" type="" @click="selectPage('before')"
                      icon="left">上一页
            </a-button>
            <a-button :disabled="disabledAfter" style="margin-left: 5px" type="" @click="selectPage('after')"
                      icon="right">下一页
            </a-button>
            <a-button style="margin-left: 5px" type="" @click="selectPage('last')" icon="">最后一页</a-button>
            <span style="margin-left: 5px">共{{totalPageNums}}页 当前在{{currentPage}}/{{totalPageNums}} 跳转到<a-input
              style="width: 50px" type="text" v-model="jumpPage"/>页
            <a-button style="margin-left: 5px" type="" @click="selectPage('jump')" icon="">确定</a-button>
            </span>
            </span>
            <bar class="statistic" :title="ShowReloadModel" :dataSource="countSource" :DataSetResult="DataSetResult"
                 :height="400"/>
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
                  <a-select-option v-for="p in poses" :key="p.id" :value="p.id">{{p.posTags}}/{{p.posMeaning}}
                  </a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
                <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
                  <a-button type="primary" @click="selectPos" icon="search">开始筛选</a-button>
                </span>
            </a-col>
          </a-row>
          <a-row>
            <span v-show="pageVisiable">
            <a-button type="" @click="selectPage('first')" icon="">首页</a-button>
            <a-button :disabled="disabledBefore" style="margin-left: 5px" type="" @click="selectPage('before')"
                      icon="left">上一页
            </a-button>
            <a-button :disabled="disabledAfter" style="margin-left: 5px" type="" @click="selectPage('after')"
                      icon="right">下一页
            </a-button>
            <a-button style="margin-left: 5px" type="" @click="selectPage('last')" icon="">最后一页</a-button>
            <span style="margin-left: 5px">共{{totalPageNums}}页 当前在{{currentPage}}/{{totalPageNums}} 跳转到<a-input
              style="width: 50px" type="text" v-model="jumpPage"/>页
            <a-button style="margin-left: 5px" type="" @click="selectPage('jump')" icon="">确定</a-button>
            </span>
            </span>
            <pie class="statistic" :title="ShowReloadModel" :dataSource="countSource" :DataSetResult="DataSetResult"
                 :height="400"/>
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
  import {putNlpAction, getAction} from '@/api/manage'
  import {message, Button} from 'ant-design-vue';

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
        tabStatus: "bar",  //统计图类型，默认进行分词时展示柱状图
        ShowReloadModel: '',  //加载成功的模型名称
        ShowModelUpdate: false, //判断模型是否改变
        DataSetResult: "", //当前组件与柱状图和饼状图子组件进行传值，并且传递给父组件的分词结果展示
        DataSetResultAll: "", //查看分词结果的数据
        posTag: "", //词性标签
        posTagsAndMeaning: undefined, //词性标签和含义，定义为undefined为了让placeholder显示
        poses: [], //词性明细数组
        json_DataSet: [],  //调用接口得到的原始分词结果
        arrList: [[]],  //切分后的分词结果
        json_SelectDataSet: [],  //筛选后的分词结果
        totalPageNums: 1, //总页数
        currentPage: 1, //当前页数
        jumpPage: 1, //跳转的页数
        disabledBefore: false, //设置上一页是否可点击
        disabledAfter: false, //设置下一页是否可点击
        pageVisiable: false, //翻页条显示与关闭
        url: {
          getHanLPWS: "/jeecg-demo/mynlp/hanLPWS",
          getJiebaWS: "/jeecg-demo/mynlp/jiebaWS",
          getLtpWS: "/jeecg-demo/mynlp/ltpWS",
          getThulacWS: "/jeecg-demo/mynlp/thulacWS",
          queryTbNlpPosDetailListByMainId: "/jeecg-demo/mynlp/tbNlpPos/queryTbNlpPosDetailByMainId", //通过自然语言词性标注集明细主表ID查询
        },
        form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
      }
    },
    methods: {
      loadData(url, param, dtTextJsonString) {
        const loding = message.loading('模型加载中，请稍后...', 0); //第二个参数设置为0一直显示加载中
        putNlpAction(url, param, dtTextJsonString).then((res) => {
          loding(); //关闭正在加载弹窗
          if (res.success) {
            message.success('模型加载成功！', 2);
            this.json_DataSet = []; //在每次重新分词前刷新分词结果数组（置空）
            this.searchReset(); //重新进行全分词后将词性下拉列表置空
            this.getPosDetail();  //模型加载成功后为词性下拉列表赋值
            let json_Data = JSON.parse(res.result);
            this.json_DataSet = json_Data.data;
            this.ShowReloadModel = this.ShowModel + "分词结果"; //分词后图表左上方展示的模型名称
            let json_DataSet = this.json_DataSet;
            this.getDataSetResultAll(json_DataSet); //为查看分词结果内容赋值，这里是全部分词的结果
            this.arrList = this.dataSetGroup(json_DataSet, 20); //进行数组的切分
            this.totalPageNums = this.arrList.length; //总页数
            this.currentPage = 1;
            this.pageVisiable = true; //显示分页条
            this.disabledSelectPage(); //初始化第一次显示时上一页和下一页按钮的状态
            this.getWSData(0); //加载图表数据，默认显示第一页
          } else {
            message.warning('请求超时，请重试！', 2)
            //DataSetResult是在父组件on监听的方法，第二个参数DataSetResult是需要传的值
            this.$emit('DataSetResultAll', false);
          }
        })
      },
      // 为查看分词结果页面赋值，此处是全部分词结果
      getDataSetResultAll(data) {
        let wordOrNature = "";
        this.DataSetResultAll = ""; //在每次重新分词前刷新分词结果数据数据（防止内容叠加显示）
        for (let i = 0; i < data.length; i++) {
          wordOrNature = this.checked === true ? data[i].word + "/" + data[i].nature : data[i].word;
          this.DataSetResultAll += " [ " + wordOrNature + " ] ";
        }
        //DataSetResult是在父组件引用改组件时定义的v-on（或@）监听方法，第二个参数DataSetResult是需要传的值
        this.$emit('DataSetResultAll', this.DataSetResultAll);
      },
      // 为柱状图和饼状图填充数据
      getWSData(page) {
        console.info("checked = " + this.checked);
        let data = [];
        if (this.arrList.length === 0) {
          message.warning("分词结果中不含指定词性！", 2)
        } else {
          data = this.arrList[page]
        }
        let wordOrNature = "";
        this.DataSetResult = ""; //在每次重新分词前刷新分词结果数据数据（防止内容叠加显示）
        this.countSource = [];  //在每次重新分词前刷新图表数据源数组（置空）
        for (let i = 0; i < data.length; i++) {
          wordOrNature = this.checked === true ? data[i].word + "/" + data[i].nature : data[i].word;
          this.DataSetResult += " [ " + wordOrNature + " ] ";
          let y = Math.floor(Math.random() * 100) + 1; //生成[0,100]随机数
          if (this.tabStatus === "bar") {
            this.countSource.push({
              x: wordOrNature,
              y: y
            })
          } else {
            this.countSource.push({
              item: wordOrNature,
              count: y
            })
          }
        }
      },
      // 进行词性筛选
      selectPos() {
        //如果没有选中模型并进行分词操作进行分词提示，否则进行必填项校验提示，基本逻辑为只有进行分词之后并进行了词性筛选选择才会触发分词筛选结果展示
        if (this.ShowModel === "") {
          message.warning("请先进行分词！", 2);
        } else {
          //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！(对指定字段进行校验，第一个参数是一个数组，数组里是指定字段的名称)
          this.form.validateFields(['posTagsAndMeaning'], (err) => {
            console.log("err = " + err);
            //判断必填项校验是否通过
            if (!err) {
              this.json_SelectDataSet = []; //重新进行词性选中后清空上一个筛选的数据
              //如果这样直接赋值改变赋值后的数组会造成原数组的数据也改变，因为JavaScript存储对象时存储的是地址，浅拷贝导致两个数组指向同一块内存地址。
              //this.json_SelectDataSet = this.json_DataSet;
              //深拷贝（slice、concat）
              this.json_SelectDataSet = [].concat(this.json_DataSet);//或者Dataset = this.json_DataSet.slice(0)效果相同
              let SelectDataSet = this.json_SelectDataSet;
              if (this.posTag !== "全部") {
                for (let i = 0; i < SelectDataSet.length; i++) {
                  if (SelectDataSet[i].nature !== this.posTag) {
                    SelectDataSet.splice(i, 1);//从i开始（包括i，i从0开始）删除一个元素
                    i--; //删除数组指定元素之后，将i重新定位在删除的位置跟此时位置上的新元素重新比较
                  }
                }
              }
              this.getDataSetResultAll(SelectDataSet); //为查看分词结果内容赋值，这里是筛选分词的结果（没有切分）
              this.arrList = [[]]; //在筛选并切分之前，将未筛选时的切分数据清空
              this.arrList = this.dataSetGroup(SelectDataSet, 20); //进行数组的切分
              this.totalPageNums = this.arrList.length; //总页数
              this.currentPage = 1;
              this.disabledSelectPage(); //初始化第一次显示时上一页和下一页按钮的状态
              this.getWSData(0);
            }
          })
        }
      },
      // 页面跳转
      selectPage(value) {
        switch (value) {
          case 'first': {
            this.jumpPage = "";
            // 当总页数大于1时，点击首页后，禁止点击上一页，可以点击下一页
            if (this.totalPageNums > 1) {
              this.disabledAfter = false;
              this.disabledBefore = true;
              this.currentPage = 1;
            }else{
              this.currentPage = this.totalPageNums === 1 ? 1 : 0; //筛选后如果没有数据，则点击首页显示总页数为0
            }
            this.getWSData(0);
          }
            break;
          case 'last': {
            this.jumpPage = "";
            // 当总页数大于1时，点击最后一页后，禁止点击下一页，可以点击上一页
            if (this.totalPageNums > 1) {
              this.disabledAfter = true;
              this.disabledBefore = false;
            }
            this.currentPage = this.totalPageNums;
            this.getWSData(this.totalPageNums - 1);
          }
            break;
          case 'before': {
            this.jumpPage = "";
            this.currentPage--;
            this.disabledAfter = false;
            // 点击上一页后，如果上一页为第一页，则禁止点击上一页
            if (this.currentPage === 1) {
              this.disabledBefore = true;
            }
            this.getWSData(this.currentPage - 1);
          }
            break;
          case 'after': {
            this.jumpPage = "";
            this.currentPage++;
            this.disabledBefore = false;
            // 点击下一页后，如果下一页为最后一页，则禁止点击下一页
            if (this.currentPage === this.totalPageNums) {
              this.disabledAfter = true;
            }
            this.getWSData(this.currentPage - 1);
          }
            break;
          case 'jump': {
            if (!/^[1-9]([0-9]*)$/.test(this.jumpPage)) {
              message.warning("请输入合法数字！", 2);
              break;
            } else {
              if (this.jumpPage < 1 || this.jumpPage > this.totalPageNums) {
                message.warning("请输入指定数据范围！", 2);
                break;
              }
            }
            this.disabledAfter = false;
            this.disabledBefore = false;
            this.currentPage = this.jumpPage;
            // 当数据只有一页的时候，跳转到第一页禁止点击上一页和下一页按钮
            if(this.totalPageNums === 1){
              this.disabledAfter = true;
              this.disabledBefore = true;
            }
            // 跳转到第一页时，禁止点击上一页按钮
            else if(this.currentPage === "1") {
              this.disabledBefore = true;
            }
            // 跳转到最后一页时，禁止点击下一页按钮
            else if (this.currentPage == this.totalPageNums) {
              this.disabledAfter = true;
            }
            this.getWSData(this.jumpPage - 1);
          }
            break;
          default:
          //throw Error("not implemented case")
        }
      },
      // 初始化第一次显示时上一页和下一页按钮的状态
      disabledSelectPage() {
        this.jumpPage = "";
        this.disabledAfter = false;
        this.disabledBefore = false;
        // 初始化第一次加载数据显示时，默认显示在第一页，如果总页数为1或者0，则禁止点击上一页和下一页
        if (this.totalPageNums === 1 || this.totalPageNums === 0) {
          this.disabledAfter = true;
          this.disabledBefore = true;
          // 当总页数为0，则当前页数也为0
          if(this.totalPageNums === 0) this.currentPage = 0;
        } else {
          // 总页数大于1，则可以点击下一页，禁止点击上一页
          this.disabledBefore = true;
        }
      },
      // 选择统计图类别
      callback(key) {
        this.searchReset(); //重新选择统计图类别后将词性下拉列表置空
        if (this.ShowModel === "") {
          message.warning("请先进行分词！", 2);
        } else {
          if (key === "1") {
            this.tabStatus = "bar";
          } else {
            this.tabStatus = "pie";
          }
          this.arrList = [[]]; //数据清空
          this.arrList = this.dataSetGroup(this.json_DataSet, 20); //进行数组的切分,将分词数据重置为全分词模式
          this.totalPageNums = this.arrList.length; //总页数
          this.currentPage = 1;
          this.disabledSelectPage(); //初始化第一次显示时上一页和下一页按钮的状态
          this.getWSData(0);
        }
      },
      // 选择请求url
      getUrl(param) {
        let showModel = this.ShowModel;
        let dataSetId = this.dataSetId;
        let url = "";
        let dtText = {};  //初始化用户自定义输入的文本内容对象
        // 判断如果不是调用数据集时对文本内容对象进行属性赋值
        if (dataSetId === undefined) {
          dtText = {
            "dtText": this.DataSet
          };
        }
        let dtTextJsonString = JSON.stringify(dtText);
        switch (showModel) {
          case 'HanLP': {
            param = {
              dataSetId: dataSetId
            };
            url = this.url.getHanLPWS;
          }
            break;
          case 'Thulac': {
            param = {
              dataSetId: dataSetId
            };
            url = this.url.getThulacWS;
          }
            break;
          case 'Jieba': {
            param = {
              dataSetId: dataSetId
            };
            url = this.url.getJiebaWS;
          }
            break;
          case 'Ltp': {
            param = {
              dataSetId: dataSetId
            };
            url = this.url.getLtpWS;
          }
            break;
          default:
          //
        }
        if (url !== "") {
          this.loadData(url, param, dtTextJsonString);
        } else {
          if (showModel === "") {
            message.warning("请先进行分词！", 2);
          } else {
            message.warning("该模型未封装！", 2);
          }
        }
      },
      handlePosChange(value) {
        if (value === "0") {
          this.posTag = "全部"; //词性的标签
          //this.posTagsAndMeaning = "全部/全部"; //将词性标签和含义进行组合显示在下拉列表
        } else {
          let obj = {};
          //从当前models数组中寻找
          obj = this.poses.find(function (item) {
            //判断id相等
            return item.id === value;
          });
          //obj就是被选中的那个对象
          this.posTag = obj.posTags; //词性的标签
          //this.posTagsAndMeaning = obj.posTags + "/" + obj.posMeaning; //将词性标签和含义进行组合显示在下拉列表
        }
      },
      //将将词性下拉列表置为空
      searchReset() {
        this.posTagsAndMeaning = undefined;
        //加入v-decorator后无法使用v-model进行双向数据绑定，通过 setFieldsValue() 方法进行数据改变
        this.form.setFieldsValue({
          posTagsAndMeaning: undefined
        })
      },
      //获取词性标注集明细
      getPosDetail() {
        let requestUrl = this.url.queryTbNlpPosDetailListByMainId;
        let param = {
          "id": this.ModelPosId
        }
        getAction(requestUrl, param).then((res) => {
          console.info("res.success = " + res.success);
          if (res.success) {
            this.poses = [{
              "id": "0",
              "posTags": "全部",
              "posMeaning": "全部"
            }]
            this.poses = this.poses.concat(res.result);
          } else {
            message.warning('请求超时，请重试！', 2)
          }
        })
      },
      // 将dataSetArr数组按照subGroupLength个一份分成若干数组
      dataSetGroup(dataSetArr, subGroupLength) {
        let index = 0;
        let newArray = [];
        while (index < dataSetArr.length) {
          newArray.push(dataSetArr.slice(index, index += subGroupLength));
        }
        return newArray;
      }
    },
    created() {
    },
    watch: {
      //将ShowModel作为监视属性
      ShowModel: {
        //immediate:true, //初始化时让handler调用一下
        deep: true,//深度监视
        handler(newValue, oldValue) {
          this.showModelUpdate = true;
          this.getUrl({});
        }
      },
      //将DataSet作为监视属性
      DataSet: {
        //immediate:true, //初始化时让handler调用一下
        deep: true,//深度监视
        handler(newValue, oldValue) {
          //解决数据集和模型同时改变时重复调用gatUrl方法
          if (this.showModelUpdate === false) {
            this.getUrl({});
          } else {
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