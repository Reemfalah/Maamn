package com.example.rawan.maamn;

public class result {

    String res, rdate, desc,id;
public result () {}

public result (String res,String rdate,String desc){
    this.res = res;
    this.rdate = rdate;
    this.desc = desc; }

    public String getRes() {
        return res;
    }

    public String getRdate() {
        return rdate;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
