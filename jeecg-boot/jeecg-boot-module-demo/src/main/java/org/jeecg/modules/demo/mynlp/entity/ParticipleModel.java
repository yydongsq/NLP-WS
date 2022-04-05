package org.jeecg.modules.demo.mynlp.entity;


import java.util.Objects;

/**
 * @author sq
 * @create 2022-03-28-20:58
 */

/**
 * @Description: TODO
 * @author: ydy
 * @date: 2022年03月28日 20:58
 */
public class ParticipleModel {
    private String nature;
    private String word;

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Participle{" +
                "nature='" + nature + '\'' +
                ", word='" + word + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipleModel that = (ParticipleModel) o;
        return Objects.equals(nature, that.nature) &&
                Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nature, word);
    }
}
