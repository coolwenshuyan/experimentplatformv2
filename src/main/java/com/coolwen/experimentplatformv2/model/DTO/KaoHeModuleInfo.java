package com.coolwen.experimentplatformv2.model.DTO;

import java.util.Date;

/**
 * @Description
 * @Author Metal
 * @Date 2021/1/4 17:34
 * @Version 1.0
 */
public class KaoHeModuleInfo {

    private String name;
    private Date kaohe_starttime;
    private Date kaohe_endtime;



    public KaoHeModuleInfo(String name, Date kaohe_starttime, Date kaohe_endtime) {
        this.name = name;
        this.kaohe_starttime = kaohe_starttime;
        this.kaohe_endtime = kaohe_endtime;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getKaohe_starttime() {
        return kaohe_starttime;
    }

    public void setKaohe_starttime(Date kaohe_starttime) {
        this.kaohe_starttime = kaohe_starttime;
    }

    public Date getKaohe_endtime() {
        return kaohe_endtime;
    }

    public void setKaohe_endtime(Date kaohe_endtime) {
        this.kaohe_endtime = kaohe_endtime;
    }
}
