package jb.controller;

import jb.absx.F;
import jb.interceptors.TokenManage;
import jb.listener.Application;
import jb.pageModel.Colum;
import jb.pageModel.DataGrid;
import jb.pageModel.Json;
import jb.pageModel.SessionInfo;
import jb.service.UserServiceI;
import jb.util.Constants;
import jb.util.StringEscapeEditor;
import jb.util.Util;
import jb.util.oss.OSSUtil;
import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 基础控制器
 * 
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

	@Autowired
	private TokenManage tokenManage;

	@Autowired
	private UserServiceI userService;

	public static final String DEFAULT_TOKEN = TokenManage.DEFAULT_TOKEN;
	private String _publishSettingVal = "2"; //生产环境
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(false, false));
	}

	/**
	 * 用户跳转JSP页面
	 * 
	 * 此方法不考虑权限控制
	 * 
	 * @param folder
	 *            路径
	 * @param jspName
	 *            JSP名称(不加后缀)
	 * @return 指定JSP页面
	 */
	@RequestMapping("/{folder}/{jspName}")
	public String redirectJsp(@PathVariable String folder, @PathVariable String jspName) {
		return "/" + folder + "/" + jspName;
	}
	
	//@ExceptionHandler(Exception.class)  
	//@ResponseBody
	public Json ExceptionHandler(Exception e) {
		Json j = new Json();
		if(_publishSettingVal.equals(Application.getString(Constants.SYSTEM_PUBLISH_SETTING))){
			j.setMsg(Application.getString(Constants.SYSTEM_GLOBAL_MESSAGE));
		}else{
			j.setMsg(e.getMessage());
		}
		return j;
	}

	protected SessionInfo getSessionInfo(HttpServletRequest request){
		SessionInfo s = tokenManage.getSessionInfo(request);
		return s;
	}
	
	/**
	 * 下载表格
	 * @param colums
	 * @param dg
	 * @param response
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws java.io.IOException
	 */
	protected void downloadTable(List<Colum> colums,DataGrid dg,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
		downloadTable(colums,dg,null,response);
	}

	/**
	 * 下载表格
	 * @param colums
	 * @param dg
	 * @param response
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws java.lang.reflect.InvocationTargetException
	 * @throws java.io.IOException
	 */
	protected void downloadTable(List<Colum> colums,DataGrid dg, String fileName, HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException {
		Iterator<Colum> it = colums.iterator();
		while(it.hasNext()) {
			Colum c = it.next();
			if(c.isHidden()||"action".equals(c.getField()))
				it.remove();
		}
		XSSFWorkbook xs=new XSSFWorkbook();
		XSSFSheet sheet=xs.createSheet("exprot data");
		XSSFRow row=sheet.createRow((short)0);
		short i = 0;
		for(Colum c: colums){
			row.createCell(i).setCellValue(c.getTitle());
			i++;
		}
		short j = 1;
		Object invObj = null;
		for(Object o : dg.getRows()){
			row = sheet.createRow(j);
			Class<?> _class = o.getClass();
			if(F.empty(fileName))
				fileName = _class.getName();
			i = 0;
			for(Colum c: colums){
				Method method=_class.getMethod("get"+F.toUpperCaseFirst(c.getField()));
				invObj = method.invoke(o);
				if(invObj==null)
					row.createCell(i).setCellValue("");
				else
					row.createCell(i).setCellValue(invObj.toString());
				i++;
			}
			j++;
		}
		String mimetype = "application/x-msdownload";
		response.setContentType(mimetype);
		String downFileName = fileName+".xlsx";
		String inlineType = "attachment"; // 是否内联附件
		response.setHeader("Content-Disposition", inlineType
				+ ";filename=\"" + downFileName + "\"");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			xs.write(out);
		} catch (IOException e) {
		} finally {
			if (out != null)
				out.flush();
			if (out != null)
				out.close();
		}
	}

	public String uploadLocalFile(HttpServletRequest request, String dirName, MultipartFile file,  String fileName){
		if(file==null||file.isEmpty())
			return null;
		String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.UPLOADFILE+"/"+dirName);
		File f = new File(realPath);
		if(!f.exists())
			f.mkdir();

		if(F.empty(fileName)) {
			fileName = file.getOriginalFilename();
		} else {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			fileName = fileName + "_" + Util.CreateNoncestr(4) + System.currentTimeMillis() + suffix.toLowerCase();
		}
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, fileName));
			return Constants.UPLOADFILE+"/"+dirName+"/"+fileName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String uploadFile(HttpServletRequest request, String dirName, MultipartFile file,  String fileName){
//		if(file==null||file.isEmpty())
//			return null;
//		String realPath = request.getSession().getServletContext().getRealPath("/"+Constants.UPLOADFILE+"/"+dirName);
//		File f = new File(realPath);
//		if(!f.exists())
//			f.mkdir();
//
//		if(F.empty(fileName)) {
//			fileName = file.getOriginalFilename();
//		} else {
//			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//			fileName = fileName + "_" + Util.CreateNoncestr(4) + System.currentTimeMillis() + suffix.toLowerCase();
//		}
//		try {
//			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, fileName));
//			return Constants.UPLOADFILE+"/"+dirName+"/"+fileName;
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

		if(file==null||file.isEmpty())
			return null;
		if(F.empty(fileName)) {
			fileName = file.getOriginalFilename();
		} else {
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			fileName = fileName + "_" + Util.CreateNoncestr(4) + System.currentTimeMillis() + suffix.toLowerCase();
		}
		try {
			return OSSUtil.putInputStream(OSSUtil.bucketName, file.getInputStream(), Constants.UPLOADFILE+"/"+dirName+"/"+fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	public String uploadFile(MultipartFile file){
		if(file==null||file.isEmpty())
			return null;
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = UUID.randomUUID() + suffix;
		try {
			return OSSUtil.putInputStream(OSSUtil.bucketName, file.getInputStream(), fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String uploadFile(String fileType,MultipartFile file){
		if(file==null||file.isEmpty())
			return null;
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String fileName = getYmdPath(fileType,suffix);
		try {
			return OSSUtil.putInputStream(OSSUtil.bucketName, file.getInputStream(), fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String uploadFile(HttpServletRequest request, String dirName, MultipartFile file){
		return uploadFile(request, dirName, file, dirName);
	}

	protected String getYmdPath(String fileType,String fileName){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String path = fileType+"/"+calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.getTimeInMillis()+fileName;
		return path;
	}
	
	public boolean deleteFile(HttpServletRequest request, String filePath){
		String realPath = request.getSession().getServletContext().getRealPath("/");  
		filePath = realPath + "/" + filePath;
		File f = new File(filePath);
		return f.delete();
	}

}
 