package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-04-05-17:43
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        String result_Thulac = data_Thulac;
        return result_Thulac;
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
            Header contentType = response.getEntity().getContentType();
            Header contentEncoding = response.getEntity().getContentEncoding();
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
