package jb.util.mns;

import java.util.Map;

/**
 * Created by wenming on 2017/5/2.
 */
public class MNSTemplate {

    private String templateCode; // 模板CODE
    private Map<String, String> params; // 在短信模板中定义的参数值

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
