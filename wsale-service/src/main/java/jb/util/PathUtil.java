package jb.util;

import jb.absx.F;
import jb.listener.Application;

public abstract class PathUtil {
	public static String getBathPath(){
		return Application.getString("SV100");
	}
	
	public static String getUrlPath(String url){
		if(F.empty(url))return null;
		return getBathPath() + url;
	}
	
}
