package jb.util.wx.servlet;

import jb.util.wx.SignUtil;
import jb.util.wx.service.CoreService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 类名: CoreServlet.java 
 * 描述: 接收微信服务器请求
 * 作者: 研发部平台 陈勇凯
 * 日期: 2014-12-17
 * 修改记录：
 * 日期              修改人            	 修改内容
 * 2014-12-17          陈勇凯      			 无
 */
public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = 4440739483644821986L;

	public CoreServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	/**
	 * @函数名 :
	 * @功能 : 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("****************doGet");
		// 微信加密签名
		String signature = request.getParameter("signature");
		System.out.println("*********signature" + signature);
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		System.out.println("*********timestamp" + timestamp);
		// 随机数
		String nonce = request.getParameter("nonce");
		System.out.println("*********nonce" + nonce);
		// 随机字符串
		String echostr = request.getParameter("echostr");
		System.out.println("*********echostr" + echostr);
		PrintWriter out = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			System.out.println("*********result");
			out.write(echostr);
		}
		out.close();
		out = null;

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 调用核心业务类接收消息、处理消息
		String respMessage = CoreService.processRequest(request);

		// 响应消息
		PrintWriter out = response.getWriter();
		out.write(respMessage);
		out.close();
	}

	public void init() throws ServletException {}

}
