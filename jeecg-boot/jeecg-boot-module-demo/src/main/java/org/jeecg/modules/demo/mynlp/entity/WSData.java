package org.jeecg.modules.demo.mynlp.entity;
/**
 * @author sq
 * @create 2022-03-28-20:58
 */

import lombok.Data;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:58
 */
@Data
public class WSData {
    private String code;
    private WSModel[] data;
}
