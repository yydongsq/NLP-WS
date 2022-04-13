package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-03-28-20:51
 */


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jeecg.modules.demo.mynlp.entity.HanLPParticipleData;
import org.jeecg.modules.demo.mynlp.entity.HanLPParticipleModel;
import org.jeecg.modules.demo.mynlp.entity.HanLPParticipleData;
import org.jeecg.modules.demo.mynlp.entity.HanLPParticipleModel;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:51
 */
@Slf4j
public class HanLPParticipleCommon {

    /**
     * 对分词后的数据进行词性筛选
     * @param text
     * @return
     */
    public String getHanLPParticipleNS(String text){
        //Map<String, ParticipleModel> personMap = new HashMap<>();
        Gson gson = new Gson();
        //将json字符串转换为实体类对象
        HanLPParticipleData participleData = gson.fromJson(text, HanLPParticipleData.class);
        //获取分词后的data数据部分
        HanLPParticipleModel[] data = participleData.getData();
        ArrayList<HanLPParticipleModel> list = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            if(data[i].getNature().equals("n") || data[i].getNature().equals("ns")){
                list.add(data[i]);
            }
        }
        HanLPParticipleData participleData_result = new HanLPParticipleData();
        participleData_result.setCode("0");
        //java中的强制类型转换只是针对单个对象的，想要偷懒将整个数组转换成另外一种类型的数组是不行的，这和数组初始化时需要一个个来也是类似的。
        //ParticipleModel[]  da = (ParticipleModel[])list.toArray();
        HanLPParticipleModel[] participleModels = list.toArray(new HanLPParticipleModel[list.size()]);
        participleData_result.setData(participleModels);
        String participleJsonString = gson.toJson(participleData_result);
        return participleJsonString;
    }

    public String getHanLPParticiple(String text,String type){
        //请求头中的token
        String token= "556530700a074f57bb238d6146be412d1648730209277token";
        //申请的接口地址
        String url="http://comdo.hanlp.com/hanlp/v1/segment/standard";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String data_HanLP = doHanlpApi(token,url,params);
        String result_HanLP = data_HanLP;
        if("get_n".equals(type)){
            result_HanLP = getHanLPParticipleNS(data_HanLP);
        }
        return result_HanLP;
    }

    /**
     * 执行官网自行注册的API
     * @param token
     * @param url
     * @param params
     * @return
     */
    public String doHanlpApi(String token,String url,Map<String,Object> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加header请求头，token请放在header里
            httpPost.setHeader("token", token);
            // 创建参数列表
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            if (params != null) {
                for (String key : params.keySet()) {
                    //所有参数依次放在paramList中
                    paramList.add(new BasicNameValuePair(key, (String) params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            return resultString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(response!=null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 执行自己封装的API
     * @param text
     * @param type
     * @return
     */
    public String getHanLPParticipleByMyAPI(String text,String type){
        //封装HanLP模型的Flask RestFull API接口地址
        String url="http://127.0.0.1:5000/hanlp/sentence";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String hanlp_data = doMyHanLPApi(url,params);
        Gson gson = new Gson();
        //创建map对象，用于接收json字符串的转换
        HashMap<String, ArrayList<ArrayList<String>>> map = new HashMap<>();
        //将json字符串转换为map集合
        HashMap map_result = gson.fromJson(hanlp_data, map.getClass());
        ArrayList<HanLPParticipleModel> list = new ArrayList<HanLPParticipleModel>();
        //获取分词数组和词性数组
        ArrayList<ArrayList<String>> list_word_temp = (ArrayList<ArrayList<String>>)map_result.get("tok/fine");
        ArrayList<ArrayList<String>> list_nature_temp = (ArrayList<ArrayList<String>>)map_result.get("pos/pku");
        ArrayList<String> list_word = list_word_temp.get(0);
        ArrayList<String> list_nature = list_nature_temp.get(0);
        //将map集合中的分词数据封装在对应的实体类中
        for (int i = 0; i < list_word.size(); i++) {
            HanLPParticipleModel hanlpParticipleModel = new HanLPParticipleModel();
            hanlpParticipleModel.setWord(list_word.get(i));
            hanlpParticipleModel.setNature(list_nature.get(i));
            list.add(hanlpParticipleModel);
        }
        HanLPParticipleData hanlpParticipleData = new HanLPParticipleData();
        //设置响应json数据中键名key为code的值
        hanlpParticipleData.setCode("0");
        //将list转换为数组
        HanLPParticipleModel[] hanlpParticipleModels = list.toArray(new HanLPParticipleModel[list.size()]);
        //设置响应json数据中键名key为data的值
        hanlpParticipleData.setData(hanlpParticipleModels);
        //将实体类对象转换为json字符串
        String resultJsonString = gson.toJson(hanlpParticipleData);
        return resultJsonString;
    }

    public String doMyHanLPApi(String url, Map<String,Object> params) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            //添加header请求头
            httpPost.setHeader("Content-type", "application/json; charset=utf-8");
            Gson gson = new Gson();
            //将Map集合转换成json字符串
            String personMapJsonString = gson.toJson(params);
            // 构建消息实体
            StringEntity entity = new StringEntity(personMapJsonString, Charset.forName("UTF-8"));
            entity.setContentEncoding("UTF-8");
            // 发送Json格式的数据请求
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            Header contentType = response.getEntity().getContentType();//application/json
            Header contentEncoding = response.getEntity().getContentEncoding();//null
            resultString = EntityUtils.toString(response.getEntity(),"utf-8");
            return resultString;
        } catch (Exception e) {
            log.info("-----------------------调用HanLP模型失败---------------------");
            e.printStackTrace();
        }
        finally {
            if(response!=null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
