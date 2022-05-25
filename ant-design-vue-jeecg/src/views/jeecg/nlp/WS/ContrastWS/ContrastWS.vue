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
              mode="multiple"
              @change='handleModelChange'>
              <a-select-option v-for="m in models" :key="m.id" :value="m.modelName">{{m.modelName}}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <!--        <a-col :xl="2" :lg="7" :md="8" :sm="24">
                  <a-form-item label="是否展示词性">
                    <a-switch :checked="checked" @change="onChange"></a-switch>
                  </a-form-item>
                </a-col>-->
        <a-col :xl="5" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="bar-chart">进行分词</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
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
        <!-- 多列柱状图 -->
        <BarMultidNlp title="分词对比图" :height="450" :fields="fields" :dataSource="dataSource"/>
      </a-row>
    </a-form>
  </div>
  <!-- 查询区域-END -->
</template>

<script>
  import BarMultidNlp from '@/views/jeecg/nlp/WS/Chart/BarMultidNlp.vue'
  import {message} from "ant-design-vue"
  import {putNlpAction, getAction} from '@/api/manage'  //调用main.js中的通用请求方法

  export default {
    name: "WSContrast",
    components: {
      BarMultidNlp,
    },
    data() {
      return {
        DataSetName: "",
        dataSetId: "",
        ModelName: "",
        ModelNames: [], //选中的模型的名称
        checked: false,
        dataSets: [],
        models: [],
        arrListAllData: [],
        filedsAllKey: [],
        wsResults: [], //多个分词结果数据数组
        wsSliceResults: [], //切分后的多个分词结果数据数组
        dataSource: [], //多列柱状图数据源
        fields: [], //多列柱状图x轴名称
        totalPageNums: 1, //总页数
        currentPage: 1, //当前页数
        jumpPage: 1, //跳转的页数
        disabledBefore: false, //设置上一页是否可点击
        disabledAfter: false, //设置下一页是否可点击
        pageVisiable: true, //翻页条显示与关闭
        url: {
          getModelData: "/jeecg-demo/mynlp/tbNlpModel/list", //请求后台模型数据API接口
          getDataSetData: "/jeecg-demo/mynlp/tbNlpDataset/list", //请求后台数据集数据API接口
          getModelDataByStatus: "/jeecg-demo/mynlp/tbNlpModel/listByStatus", //根据模型状态查询列表
          getHanLPWS: "/jeecg-demo/mynlp/hanLPWS",
          getJiebaWS: "/jeecg-demo/mynlp/jiebaWS",
          getLtpWS: "/jeecg-demo/mynlp/ltpWS",
          getThulacWS: "/jeecg-demo/mynlp/thulacWS",
          queryTbNlpPosDetailListByMainId: "/jeecg-demo/mynlp/tbNlpPos/queryTbNlpPosDetailByMainId", //通过自然语言词性标注集明细主表ID查询
        },
        form: this.$form.createForm(this), // 只有这样注册后，才能通过表单拉取数据
      }
    },
    //在页面创建成功后执行加载下拉列表的数据
    created() {
      let modelUrl = this.url.getModelDataByStatus;
      let dataSetUrl = this.url.getDataSetData;
      let param = {
        'model_status': "已启用"
      }
      this.loadSelectData(modelUrl, "model", param);
      this.loadSelectData(dataSetUrl, "dataSet", null);
    },
    methods: {
      loadData(setSelectUrl) {

      },
      //发送axios异步请求加载数据
      loadSelectData(url, type, param) {
        getAction(url, param).then((res) => {
          if (res.success) {
            if (type === "model") {
              this.models = res.result;
            }
            if (type === "dataSet") {
              this.dataSets = res.result.records;
            }
          } else {
            message.warning('请求超时，请重试！', 2)
          }
        })
      },
      handleDataSetChange(value) {
        let obj = {};
        //从当前dataSets数组中寻找
        obj = this.dataSets.find(function (item) {
          //判断id相等
          return item.id === value;
        });
        //obj就是被选中的那个对象
        this.dataSetId = value;
        this.DataSetName = obj.dtName;
      },
      handleModelChange(value) {
        this.ModelNames = value;
      },
      //是否开启展示词性功能
      onChange(checked) {
        this.checked = checked;
      },
      //进行分词
      searchQuery() {
        //通过validateFields的方法，能够校验必填项是否有值，若无，则页面会给出警告！
        this.form.validateFields((err, values) => {
          console.log("err = " + err);
          //判断必填项校验是否通过
          if (!err) {
            this.getUrl();
          }
        })
      },
      searchReset() {
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
      // 选择请求url
      getUrl() {
        let that = this;
        let setSelectUrl = [];
        let dtText = {
          "dtText": this.DataSet
        };
        let dtTextJsonString = JSON.stringify(dtText);
        for (let i = 0; i < that.ModelNames.length; i++) {
          switch (that.ModelNames[i]) {
            case 'HanLP': {
              setSelectUrl.push(putNlpAction(that.url.getHanLPWS, {dataSetId: that.dataSetId}, dtTextJsonString));
            }
              break;
            case 'Thulac': {
              setSelectUrl.push(putNlpAction(that.url.getThulacWS, {dataSetId: that.dataSetId}, dtTextJsonString));
            }
              break;
            case 'Jieba': {
              setSelectUrl.push(putNlpAction(that.url.getJiebaWS, {dataSetId: that.dataSetId}, dtTextJsonString));
            }
              break;
            case 'Ltp': {
              setSelectUrl.push(putNlpAction(that.url.getLtpWS, {dataSetId: that.dataSetId}, dtTextJsonString));
            }
              break;
            default:
            //
          }
        };
        const loding = message.loading('模型加载中，请稍后...', 0); //第二个参数设置为0一直显示加载中
        this.$axios.all(setSelectUrl)
          .then(that.$axios.spread(function (stWS, ndWS, rdWS, thWS) {
            loding(); //关闭正在加载弹窗
            that.wsResults = [];
            let stWSSuccess =  true;
            let ndWSSuccess =  true;
            let rdWSSuccess =  true;
            let thWSSuccess =  true;
            if (stWS !== undefined) {
              stWSSuccess = stWS.success;
              if(stWSSuccess !== undefined && stWSSuccess !== false){
                let stWSData = JSON.parse(stWS.result).data;
                that.wsResults.push(stWSData);
              }else{
                stWSSuccess = false;
              }
            }
            if (ndWS !== undefined) {
              ndWSSuccess = ndWS.success;
              if(ndWSSuccess !== undefined && ndWSSuccess !== false){
                let ndWSData = JSON.parse(ndWS.result).data;
                that.wsResults.push(ndWSData);
              }else{
                ndWSSuccess = false;
              }
            }
            if (rdWS !== undefined) {
              rdWSSuccess = rdWS.success;
              if(rdWSSuccess !== undefined && rdWSSuccess !== false){
                let rdWSData = JSON.parse(rdWS.result).data;
                that.wsResults.push(rdWSData);
              }else{
                rdWSSuccess = false;
              }
            }
            if (thWS !== undefined) {
              thWSSuccess = thWS.success;
              // 请求没有超时并且成功返回数据
              if(thWSSuccess !== undefined && thWSSuccess !== false){
                let thWSData = JSON.parse(thWS.result).data;
                that.wsResults.push(thWSData);
              }else{
                // 请求超时，thWS = 后端返回错误信息：请求超时，请重试！
                thWSSuccess = false;
              }
            }
            if(stWSSuccess && ndWSSuccess && rdWSSuccess && thWSSuccess){
              message.success('模型加载成功！', 2);
              console.log("所有结果加载成功");
              console.log(that.wsResults);
              that.setBarMultidNlpDataSource();
              that.getBarMultidNlpDataSource(0);
            }else{
              message.warning('请求超时，请重试！', 2);
            }
          }))
      },
      //设置多列柱状图数据源
      setBarMultidNlpDataSource() {
        this.wsSliceResults = [];
        for (let i = 0; i < this.ModelNames.length; i++) {
          this.wsSliceResults.push(this.dataSetGroup(this.wsResults[i], 12)); //进行数组的切分
        }
        console.info("this.wsSliceResults = ");
        console.info(this.wsSliceResults);
        let arrListAllData = [];
        let arrListAllFields = [];
        for (let i = 0; i < this.ModelNames.length; i++) {
          let arrListData = [];
          let arrListFields = [];
          for (let j = 0; j < this.wsSliceResults[i].length; j++) {
            let wsObj = {};
            let wsWord = [];
            for (let k = 0; k < this.wsSliceResults[i][j].length; k++) {
              wsObj.type = this.ModelNames[i];
              let key = this.wsSliceResults[i][j][k].word;
              wsWord.push(key);
              let y = Math.floor(Math.random() * 100) + 1; //生成[0,100]随机数
              wsObj[key] = y;
            }
            arrListData.push(wsObj);
            arrListFields.push(wsWord);
          }
          arrListAllData.push(arrListData);
          arrListAllFields.push(arrListFields);
        }
        console.info("arrListAllData = ");
        console.info(arrListAllData);
        console.info("arrListAllFields = ");
        console.info(arrListAllFields);

        //this.fields =  ['4月', '18日', '中国','华盛顿','在', '大使','新华社', '电', '秦刚','美国', '驻'];
        let lengthSize = [];
        for (let i = 0; i < this.ModelNames.length; i++) {
          lengthSize.push(arrListAllData[i].length);
        }
        let maxLength = Math.max.apply(Math, lengthSize);
        this.totalPageNums = maxLength;
        this.currentPage = 1;
        this.disabledSelectPage();

        let filedsAllKey = [];
        for (let i = 0; i < maxLength; i++) {
          let filedsKey = [];
          let sliceListAll = [];
          for (let j = 0; j < this.ModelNames.length; j++) {
            let sliceArrList = [];
            sliceArrList = arrListAllFields[j][i];
            if (sliceArrList !== undefined) {
              sliceListAll.push(this.dataSetGroup(sliceArrList, 1));
            }
            if (j === this.ModelNames.length - 1) {
              for (let k = 0; k < 12; k++) {
                for (let m = 0; m < sliceListAll.length; m++) {
                  filedsKey = filedsKey.concat(sliceListAll[m][k]);
                  filedsKey = filedsKey.filter((item, index) => {
                    return filedsKey.indexOf(item) === index
                  })
                }
              }
            }
          }
          filedsAllKey.push(filedsKey);
        }
        console.info("filedsAllKey = ");
        console.info(filedsAllKey);
        this.filedsAllKey = filedsAllKey;
        this.arrListAllData = arrListAllData;
      },
      getBarMultidNlpDataSource(page) {
        this.dataSource = [];
        this.fields = [];
        this.fields = this.filedsAllKey[page];
        for (let i = 0; i < this.ModelNames.length; i++) {
          this.dataSource.push(this.arrListAllData[i][page]);
        }
        console.info("dataSource = ");
        console.info(this.dataSource);
      },
      // 将dataSetArr数组按照subGroupLength个一份分成若干数组
      dataSetGroup(dataSetArr, subGroupLength) {
        let index = 0;
        let newArray = [];
        while (index < dataSetArr.length) {
          newArray.push(dataSetArr.slice(index, index += subGroupLength));
        }
        return newArray;
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
          if (this.totalPageNums === 0) this.currentPage = 0;
        } else {
          // 总页数大于1，则可以点击下一页，禁止点击上一页
          this.disabledBefore = true;
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
            } else {
              this.currentPage = this.totalPageNums === 1 ? 1 : 0; //筛选后如果没有数据，则点击首页显示总页数为0
            }
            this.getBarMultidNlpDataSource(0);
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
            this.getBarMultidNlpDataSource(this.totalPageNums - 1);
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
            this.getBarMultidNlpDataSource(this.currentPage - 1);
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
            this.getBarMultidNlpDataSource(this.currentPage - 1);
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
            if (this.totalPageNums === 1) {
              this.disabledAfter = true;
              this.disabledBefore = true;
            }
            // 跳转到第一页时，禁止点击上一页按钮
            else if (this.currentPage === "1") {
              this.disabledBefore = true;
            }
            // 跳转到最后一页时，禁止点击下一页按钮
            else if (this.currentPage == this.totalPageNums) {
              this.disabledAfter = true;
            }
            this.getBarMultidNlpDataSource(this.jumpPage - 1);
          }
            break;
          default:
          //throw Error("not implemented case")
        }
      },
    }
  }
</script>

<style scoped>

</style>