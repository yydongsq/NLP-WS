## 项目介绍

自然语言分词展示管理系统是为分词处理结果数据做了一个可视化的展示，对分词结果数据针对需求进行了处理加工，可以满足对分词结果数据的统计分析，包括对分词结果数据进行频率的计算，按照模型的词性标注集的词性进行筛选，并结合柱状图和饼状图进行可视化的展示，以此有助于自然语言处理分词模型算法的研究，帮助初学者更好的分析模型算法的可行性和不足。

## 系统功能模块

**登录系统：**

<img src="assets/Readme/image-20250520151646450.png" alt="image-20250520151646450" style="zoom: 33%;" />

### 数据集管理功能模块

该功能可以对常用数据集进行维护，条件查询数据集信息，新增、编辑、删除和批量删除数据集信息，查看数据集详情和数据集内容详情，还具有将数据集数据导出为Excel表格文件，同时可以将数据集数据Excel表格文件进行导入等功能。 

<img src="assets/Readme/image-20250520151732720.png" alt="image-20250520151732720" style="zoom:33%;" />

### 模型管理功能模块

该功能可以对常用模型进行维护，条件查询模型信息，新增、编辑、启用、禁用、删除和批量删除模型信息，查看模型详情，还具有将模型数据导出为Excel表格文件，同时可以将模型数据Excel表格文件进行导入等功能。 

<img src="assets/Readme/image-20250520151753846.png" alt="image-20250520151753846" style="zoom:33%;" />

### 词性管理功能模块

该功能可以对常用词性标注集信息进行维护，条件查询词性标注集信息，新增、编辑、删除和批量删除词性标注集信息和词性标注集明细信息，查看词性标注集详情和词性标注集明细详情，还具有将词性标注集数据导出为Excel表格文件，同时可以将词性标注集数据Excel表格文件进行导入等功能。

<img src="assets/Readme/image-20250520151813768.png" alt="image-20250520151813768" style="zoom:33%;" />

<img src="assets/Readme/image-20250520153713652.png" alt="image-20250520153713652" style="zoom:33%;" />

### 模型演示功能模块

该功能实现对客户自输入的文本内容进行分词结果的展示，客户在输入框输入文本内容后选择调用模型输出分词结果，可以进行词性筛选、展示词性、查看分词结果、查看词性标注集等功能。 

<img src="assets/Readme/image-20250520151858354.png" alt="image-20250520151858354" style="zoom:33%;" />

<img src="assets/Readme/image-20250520151953707.png" alt="image-20250520151953707" style="zoom:33%;" />

<img src="assets/Readme/image-20250520151919271.png" alt="image-20250520151919271" style="zoom:33%;" />

<img src="assets/Readme/image-20250520152029707.png" alt="image-20250520152029707" style="zoom:33%;" />

### 分词展示功能模块

该功能实现对客户选择数据集进行分词结果的展示，客户在选择数据集后选择调用模型输出分词结果，可以进行词性筛选、展示词性、查看数据集内容、查看分词结果、查看词性标注集等功能。

<img src="assets/Readme/image-20250520152128812.png" alt="image-20250520152128812" style="zoom:33%;" />

### 分词对比模块

该功能实现对客户选择多个模型对同一数据集进行分词结果的对比展示，目前存在bug，待开发完善。

<img src="assets/Readme/image-20250520152220473.png" alt="image-20250520152220473" style="zoom:33%;" />
