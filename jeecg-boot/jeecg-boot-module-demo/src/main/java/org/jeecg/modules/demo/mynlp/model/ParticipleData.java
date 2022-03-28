package org.jeecg.modules.demo.mynlp.model;
/**
 * @author sq
 * @create 2022-03-28-20:58
 */

import java.util.Arrays;
import java.util.Objects;

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:58
 */
public class ParticipleData {
    private String code;
    private ParticipleModel[] data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ParticipleModel[] getData() {
        return data;
    }

    public void setData(ParticipleModel[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ParticipleData{" +
                "code='" + code + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipleData that = (ParticipleData) o;
        return Objects.equals(code, that.code) &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(code);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
