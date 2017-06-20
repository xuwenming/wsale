package jb.tag;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import jb.absx.F;
import jb.listener.Application;
import jb.service.BasedataServiceI;

public class SelectTagBySql extends TagSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2709846727239749266L;
	private String name;
	private String dataType;
	private String value;
	private boolean editable;
	private boolean hasDownArrow = true;
	private boolean required;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@SuppressWarnings("rawtypes")
	@Override  
    public int doStartTag() throws JspException {  
		JspWriter out = pageContext.getOut();  		  
        try{
        	out.print("<select name=\""+name+"\" id=\""+name+"\" class=\"easyui-combobox\" data-options=\"width:140,height:29,editable:"+editable+",hasDownArrow:"+hasDownArrow+"\">");
        	BasedataServiceI service = Application.getBasedataService();
        	String sql = Application.get(dataType).getDescription();
        	List<Map> baseDataList = service.getSelectMapList(sql, null);
        	if(!required)
        	out.print("<option value=\"\"></option>");
        	String value;
        	String text;
        	for(Map bd : baseDataList){
        		value = bd.get("value").toString();
        		text = bd.get("text").toString();
        		if(F.empty(this.value)||!this.value.equals(value)){
                	out.print("<option value=\""+value+"\">"+text+"</option>");
        		}else{
                	out.print("<option value=\""+value+"\" selected=\"selected\">"+text+"</option>");
        		}
        	}
        	out.print("</select>");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return super.doStartTag();
        //return TagSupport.EVAL_BODY_INCLUDE;//输出标签体内容  
        //return TagSupport.SKIP_BODY;//不输出标签体内容  
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isHasDownArrow() {
		return hasDownArrow;
	}

	public void setHasDownArrow(boolean hasDownArrow) {
		this.hasDownArrow = hasDownArrow;
	}
}
