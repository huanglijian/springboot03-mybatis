package cn.ck.entity.bean;

import cn.ck.entity.Collectres;
import cn.ck.entity.Resource;

public class ResCol extends Resource {
    private Collectres collectres;

    public Collectres getCollectres() {
        return collectres;
    }

    public void setCollectres(Collectres collectres) {
        this.collectres = collectres;
    }
}
