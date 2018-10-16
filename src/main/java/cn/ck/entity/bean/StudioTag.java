package cn.ck.entity.bean;

import cn.ck.entity.Studio;

import java.util.Arrays;

/**
 * @author 马圳彬
 * @version 1.0
 * @description chuangke
 * @date 2018/10/12 12:40
 **/
public class StudioTag {
    private Studio studio;
    private String tag[];
    private String field[];

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public String[] getField() {
        return field;
    }

    public void setField(String[] field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "StudioTag{" +
                "studio=" + studio +
                ", tag=" + Arrays.toString(tag) +
                ", field=" + Arrays.toString(field) +
                '}';
    }
}
