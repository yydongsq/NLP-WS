from pickle import TRUE
from flask import Flask,jsonify
from flask_restful import reqparse, abort, Api, Resource
import thulac
import jieba
import jieba.posseg as pseg
from ltp import LTP
import hanlp

app = Flask(__name__)

# 防止返回中文字符出现乱码
app.config['JSON_AS_ASCII'] = False
app.config.update(RESTFUL_JSON=dict(ensure_ascii=False))
api = Api(app)

# 获取请求参数
parser = reqparse.RequestParser()
parser.add_argument('text')

# 自定义响应格式
@api.representation('application/json;charset=utf-8') 
# Thulac
def select_participletype_byThulac(text,participle_type):
    if participle_type == "sentence":
        thun = thulac.thulac()   #默认模式
        result = thun.cut(text,text = False) #进行一句话分词,text = False是否返回文本，不返回文本则返回一个二维数组
        print("----------调用Thulac成功----------")
        return result
    else:
        thun = thulac.thulac()  #如增加参数seg_only=True只进行分词，不进行词性标注
        thun.cut_f("input.txt", "output.txt")  #对input.txt文件内容进行分词，输出到output.txt
        print("----------调用Thulac成功----------")
        return '已输出'   

#自定义响应格式
@api.representation('application/json;charset=utf-8') 
# Jieba
def select_participletype_byJieba(text,participle_type):
    if participle_type == "sentence":
        jieba.enable_paddle()   #启动paddle模式(词性标注)
        words = pseg.cut(text,use_paddle=True)
        t = {}
        i = 0
        for word, flag in words:
            s = [word,flag]
            t[i] = s
            i = i + 1
        result = jsonify(t) #转换为json字符串
        print("----------调用Jieba成功----------")
        return result
    else:
        return ''     

#自定义响应格式
@api.representation('application/json;charset=utf-8') 
# Ltp
def select_participletype_byLtp(text,participle_type):
    if participle_type == "sentence":
        ltp = LTP()  # 载入模型，默认加载 Small 模型
        segment, _ = ltp.seg([text])
        seg, hidden = ltp.seg([text])   #分词
        pos = ltp.pos(hidden)   #词性标注
        t = {}
        t[0] = seg.pop()
        t[1] = pos.pop()
        result = jsonify(t) #转换为json字符串
        print("----------调用Ltp成功----------")
        return result
    else:
        return ''

#自定义响应格式
@api.representation('application/json;charset=utf-8') 
# HanLP
def select_participletype_byHanLP(text,participle_type):
    if participle_type == "sentence":
        # MTL多任务，具体任务见模型名称，语种见名称最后一个字段或相应语料库
        hanlp.pretrained.mtl.ALL 
        #调用hanlp.load进行加载，模型会自动下载到本地缓存。自然语言处理分为许多任务，分词只是最初级的一个。
        #与其每个任务单独创建一个模型，不如利用HanLP的联合模型一次性完成多个任务。
        HanLPUtil = hanlp.load(hanlp.pretrained.mtl.CLOSE_TOK_POS_NER_SRL_DEP_SDP_CON_ELECTRA_BASE_ZH)
        #执行所有标准的词性标注（pos：默认执行ctb标准；pos/pku：执行PKU词性标注；pos/ctb：执行CTB词性标注；pos/863：执行863词性标注；pos/*：执行所有标准）
        #以pos开头的字段为词性，以tok开头的第一个数组为单词，两者按下标一一对应。      
        result = HanLPUtil([text], tasks='pos/pku')
        print("----------调用HanLP成功----------")
        return result
    else:
        return ''

#thulac分词
class Thulac(Resource):
    @api.representation('application/json;charset=utf-8') 
    def post(self,participle_type):
        args = parser.parse_args()
        text = args['text']
        result = select_participletype_byThulac(text,participle_type)
        return result

#jieba分词
class Jieba(Resource):
    @api.representation('application/json;charset=utf-8') 
    def post(self,participle_type):
        args = parser.parse_args()
        text = args['text']
        result = select_participletype_byJieba(text,participle_type)
        return result

#ltp分词
class Ltp(Resource):
    @api.representation('application/json;charset=utf-8') 
    def post(self,participle_type):
        args = parser.parse_args()
        text = args['text']
        result = select_participletype_byLtp(text,participle_type)
        return result

#hanlp分词
class HanLP(Resource):
    @api.representation('application/json;charset=utf-8') 
    def post(self,participle_type):
        args = parser.parse_args()
        text = args['text']
        result = select_participletype_byHanLP(text,participle_type)
        return result

# 设置路由
api.add_resource(Thulac, '/thulac/<participle_type>')
api.add_resource(Jieba, '/jieba/<participle_type>')
api.add_resource(Ltp, '/ltp/<participle_type>')
api.add_resource(HanLP, '/hanlp/<participle_type>')


if __name__ == '__main__':
    app.run(debug=True)