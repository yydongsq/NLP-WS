# 接口文档

## 1. 环境配置

**Python：**使用Python3.7版本，Python下载地址：https://www.python.org/downloads/windows/，添加环境变量，path中添加E:\Python\Python39（使用自己的下载路径）。

**pip：**下载pip包管理工具，下载地址：https://pypi.python.org/pypi/pip#downloads。（pip已内置于Python3.4和2.7及以上版本，其它版本需另行安装）。

**修改pip下载源：**由于Python现在下载源都是默认国外的，而国外的源又都太慢，并且很容易下载失败，所以可以更换源为国内的镜像，比较常用的有清华、中科大、阿里等，推荐使用清华镜像。

**临时使用国内镜像：**可以在pip的时候在后面加上-i参数，指定pip源。

```shell
pip install scrapy -i https://pypi.tuna.tsinghua.edu.cn/simple
```

**永久修改源：**直接在user目录中创建一个pip目录，如C:\User\xx\pip，在pip目录下创建文件pip.ini，或者win+r打开输入%HOMEPATH%，在此目录下创建pip文件夹，在pip目录中创建pip.ini文件。

```shell
[global]
timeout = 6000
index-url = https://pypi.tuna.tsinghua.edu.cn/simple
trusted-host = pypi.tuna.tsinghua.edu.cn
```

**pip下载资源的默认路径：**python安装目录/lib/site-package/。

## 2. 接口封装

**接口封装的工具：**使用Flask RESTful 工具封装Python代码为RESTful API接口，Flask RESTful的文档地址：

https://flask-restful.readthedocs.io/en/latest/。

**安装Flask-RESTful库：**

```shell
pip install flask
pip install jinja2
pip install flask-restful
```

## 3. 分词工具

**导入Thulac分词工具：**

```shell
pip install thulac	#通过 import thulac 来引用
```

**导入jieba分词工具：**

```shell
pip install jieba	#通过import jieba 来引用 （第一次import时需要构建Trie树，需要几秒时间）
```

**导入LTP分词工具：**

```shell
pip install ltp	

from ltp import LTP
ltp = LTP() # 默认加载 Small 模型
```

**防止调用jieba时出现异常：**No module named 'cv2' 

```shell
pip install opencv-python
```

