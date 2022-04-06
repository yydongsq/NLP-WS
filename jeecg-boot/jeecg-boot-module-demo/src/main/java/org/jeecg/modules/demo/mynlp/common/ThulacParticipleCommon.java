package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-04-05-17:43
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecg.modules.demo.mynlp.entity.HanLPParticipleModel;
import org.jeecg.modules.demo.mynlp.entity.ThulacParticipleData;
import org.jeecg.modules.demo.mynlp.entity.ThulacParticipleModel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月05日 17:43
 */
public class ThulacParticipleCommon {

    public String getThulacParticiple(String text,String type){
        //封装Thulac模型的Flask RestFull API接口地址
        String url="http://127.0.0.1:5000/thulac/sentence";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String data_Thulac = doThulacApi(url,params);
        //从字符串解析JSON数组
        JSONArray data = JSON.parseArray(data_Thulac);
        ArrayList<ThulacParticipleModel> list = new ArrayList();
        //获取二维数组中的分词数据赋值给thulacParticipleModel实体类对象的两个参数
        for (int i = 0; i < data.size(); i++) {
            ThulacParticipleModel thulacParticipleModel = new ThulacParticipleModel();
            JSONArray data_word = (JSONArray)data.get(i);
            thulacParticipleModel.setWord((String) data_word.get(0));
            thulacParticipleModel.setNature((String) data_word.get(1));
            list.add(thulacParticipleModel);
        }
        ThulacParticipleData thulacParticipleData = new ThulacParticipleData();
        thulacParticipleData.setCode("0");
        //将list集合转换为数组，并给thulacParticipleData对象的data属性赋值
        ThulacParticipleModel[] thulacParticipleModels = list.toArray(new ThulacParticipleModel[list.size()]);
        Gson gson = new Gson();
        thulacParticipleData.setData(thulacParticipleModels);
        //将thulacParticipleData对象转换为json字符串
        String resultJsonString = gson.toJson(thulacParticipleData);
        return resultJsonString;
    }

    public String doThulacApi(String url, Map<String,Object> params) {
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
