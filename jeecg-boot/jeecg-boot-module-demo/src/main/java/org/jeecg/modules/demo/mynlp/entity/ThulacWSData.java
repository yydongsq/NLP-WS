package org.jeecg.modules.demo.mynlp.entity;/**
 * @author sq
 * @create 2022-04-06-10:59
 */

import lombok.Data;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年04月06日 10:59
 */
@Data
public class ThulacWSData {
    private String code;
    private ThulacWSModel[] data;
}
