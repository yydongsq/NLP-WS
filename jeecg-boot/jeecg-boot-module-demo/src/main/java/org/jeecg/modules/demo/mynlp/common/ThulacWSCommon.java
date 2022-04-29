package org.jeecg.modules.demo.mynlp.common;/**
 * @author sq
 * @create 2022-04-05-17:43
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.demo.mynlp.entity.ThulacWSData;
import org.jeecg.modules.demo.mynlp.entity.ThulacWSModel;
import org.jeecg.modules.demo.mynlp.util.WsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月05日 17:43
 */
@Slf4j
public class ThulacWSCommon {

    /**
     * 调用API接口进行分词
     * @param text
     * @return
     */
    public String getThulacWS(String text){
        //封装Thulac模型的Flask RestFull API接口地址
        String url="http://127.0.0.1:5000/thulac/sentence";
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("text", text);
        //执行api
        String data_Thulac = WsUtils.doWsApi(url,params);
        //从字符串解析JSON数组
        JSONArray data = JSON.parseArray(data_Thulac);
        ArrayList<ThulacWSModel> list = new ArrayList();
        //获取二维数组中的分词数据赋值给thulacParticipleModel实体类对象的两个参数
        for (int i = 0; i < data.size(); i++) {
            ThulacWSModel thulacWSModel = new ThulacWSModel();
            JSONArray data_word = (JSONArray)data.get(i);
            thulacWSModel.setWord((String) data_word.get(0));
            thulacWSModel.setNature((String) data_word.get(1));
            list.add(thulacWSModel);
        }
        ThulacWSData thulacWSData = new ThulacWSData();
        thulacWSData.setCode("0");
        //将list集合转换为数组，并给thulacParticipleData对象的data属性赋值
        ThulacWSModel[] thulacWSModels = list.toArray(new ThulacWSModel[list.size()]);
        Gson gson = new Gson();
        thulacWSData.setData(thulacWSModels);
        //将thulacParticipleData对象转换为json字符串
        String resultJsonString = gson.toJson(thulacWSData);
        return resultJsonString;
    }
}
