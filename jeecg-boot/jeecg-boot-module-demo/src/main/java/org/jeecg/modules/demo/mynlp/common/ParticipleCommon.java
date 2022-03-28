package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-03-28-20:51
 */


import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jeecg.modules.demo.mynlp.model.ParticipleData;
import org.jeecg.modules.demo.mynlp.model.ParticipleModel;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:51
 */
public class ParticipleCommon {
    public String getHanLPParticipleNS(String text){
        //Map<String, ParticipleModel> personMap = new HashMap<>();
        Gson gson = new Gson();
        ParticipleData participleData = gson.fromJson(text, ParticipleData.class);
        System.out.println(participleData);
        ParticipleModel[] data = participleData.getData();
        ArrayList<ParticipleModel> list = new ArrayList();
        for (int i = 0; i < data.length; i++) {
            if(data[i].getNature().equals("n") || data[i].getNature().equals("ns")){
                list.add(data[i]);
            }
        }
        ParticipleData participleData_result = new ParticipleData();
        participleData_result.setCode("0");
        //java中的强制类型转换只是针对单个对象的，想要偷懒将整个数组转换成另外一种类型的数组是不行的，这和数组初始化时需要一个个来也是类似的。
        //ParticipleModel[]  da = (ParticipleModel[])list.toArray();
        ParticipleModel[] participleModels = list.toArray(new ParticipleModel[list.size()]);
        participleData_result.setData(participleModels);
        String participleJsonString = gson.toJson(participleData_result);
        return participleJsonString;
    }
    public String getHanLPParticiple(String text,String type){
        //请求头中的token
        String token= "1145e79449474c64aa599cf7b860430a1648478554467token";
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
}
