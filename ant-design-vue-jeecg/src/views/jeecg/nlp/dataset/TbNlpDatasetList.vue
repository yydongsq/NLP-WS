<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="数据集类型名称">
              <a-input placeholder="请输入数据集类型名称" v-model="queryParam.dtTypeName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="数据集名称">
              <a-input placeholder="请输入数据集名称" v-model="queryParam.dtName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="数据集创建时间">
                <j-date :show-time="true" date-format="YYYY-MM-DD" placeholder="请选择数据集创建时间" v-model="queryParam.dtCreateTime"></j-date>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('自然语言分词数据集')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDataSetDetail(record)">数据集</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <a-modal v-model:visible="visible" :title="modalTitle" @ok="handleOk">
      <div>{{dataSetContent}}</div>
    </a-modal>
    <tb-nlp-dataset-modal ref="modalForm" @ok="modalFormOk"></tb-nlp-dataset-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import TbNlpDatasetModal from './modules/TbNlpDatasetModal'

  export default {
    name: 'TbNlpDatasetList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      TbNlpDatasetModal
    },
    data () {
      return {
        description: '自然语言分词数据集管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'数据集类型编码',
            align:"center",
            dataIndex: 'dtTypeCode'
          },
          {
            title:'数据集类型名称',
            align:"center",
            dataIndex: 'dtTypeName'
          },
          {
            title:'数据集编号',
            align:"center",
            dataIndex: 'dtNo'
          },
          {
            title:'数据集名称',
            align:"center",
            dataIndex: 'dtName'
          },
          {
            title:'数据集大小',
            align:"center",
            dataIndex: 'dtSize'
          },
          {
            title:'数据集创建人',
            align:"center",
            dataIndex: 'dtCreateBy'
          },
          {
            title:'数据集创建时间',
            align:"center",
            dataIndex: 'dtCreateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'数据集更新人',
            align:"center",
            dataIndex: 'dtUpdateBy'
          },
          {
            title:'数据集更新时间',
            align:"center",
            dataIndex: 'dtUpdateTime'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'dtMemo'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/jeecg-demo/mynlp/tbNlpDataset/list",
          delete: "/jeecg-demo/mynlp/tbNlpDataset/delete",
          deleteBatch: "/jeecg-demo/mynlp/tbNlpDataset/deleteBatch",
          exportXlsUrl: "/jeecg-demo/mynlp/tbNlpDataset/exportXls",
          importExcelUrl: "jeecg-demo/mynlp/tbNlpDataset/importExcel",

        },
        dictOptions:{},
        superFieldList:[],
        dataSetContent:'',  //数据集内容
        modalTitle:'',  //数据集窗口标题
        visible:false  //对话框开启关闭
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'dtTypeCode',text:'数据集类型编码',dictCode:''})
        fieldList.push({type:'string',value:'dtTypeName',text:'数据集类型名称',dictCode:''})
        fieldList.push({type:'string',value:'dtNo',text:'数据集编号',dictCode:''})
        fieldList.push({type:'string',value:'dtName',text:'数据集名称',dictCode:''})
        fieldList.push({type:'Text',value:'dtText',text:'数据集内容',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'dtSize',text:'数据集大小',dictCode:''})
        fieldList.push({type:'string',value:'dtCreateBy',text:'数据集创建人',dictCode:''})
        fieldList.push({type:'date',value:'dtCreateTime',text:'数据集创建时间'})
        fieldList.push({type:'string',value:'dtUpdateBy',text:'数据集更新人',dictCode:''})
        fieldList.push({type:'datetime',value:'dtUpdateTime',text:'数据集更新时间'})
        fieldList.push({type:'string',value:'dtMemo',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      },
      handleDataSetDetail(record){
        console.info("数据集record.dtText = " + record.dtText);
        this.visible = true;
        this.modalTitle = record.dtName;
        this.dataSetContent = record.dtText;
      },
      //关闭数据集详情窗口
      handleOk(){
        this.visible = false;
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>