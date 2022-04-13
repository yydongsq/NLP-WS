package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-04-06-16:15
 */

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeecg.modules.demo.mynlp.entity.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 16:15
 */
@Slf4j
public class JiebaWSCommon {
    public String getJiebaWS(String text,String type){
        //封装Thulac模型的Flask RestFull API接口地址
        String url="http://127.0.0.1:5000/jieba/sentence";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String jieba_data = doJiebaApi(url,params);
        Gson gson = new Gson();
        //创建map对象，用于接收json字符串的转换
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        //将json字符串转换为map集合
        HashMap map_result = gson.fromJson(jieba_data, map.getClass());
        ArrayList<JiebaWSModel> list = new ArrayList<>();
        //将map集合中的分词数据封装在对应的实体类中
        for (int i = 0; i < map_result.size(); i++) {
            String key = String.valueOf(i);
            ArrayList<String> list_data = (ArrayList<String>)map_result.get(key);
            JiebaWSModel jiebaWSModel = new JiebaWSModel();
            jiebaWSModel.setWord(list_data.get(0));
            jiebaWSModel.setNature(list_data.get(1));
            list.add(jiebaWSModel);
        }
        JiebaWSData jiebaParticipleData = new JiebaWSData();
        //设置响应json数据中键名key为code的值
        jiebaParticipleData.setCode("0");
        //将list转换为数组
        JiebaWSModel[] jiebaWSModels = list.toArray(new JiebaWSModel[list.size()]);
        //设置响应json数据中键名key为data的值
        jiebaParticipleData.setData(jiebaWSModels);
        //将实体类对象转换为json字符串
        String resultJsonString = gson.toJson(jiebaParticipleData);
        return resultJsonString;
    }

    public String doJiebaApi(String url, Map<String,Object> params) {
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
            log.info("-----------------------调用Jieba模型失败---------------------");
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
