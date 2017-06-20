package jb.util.wx;

/**
 * jsapi_ticket是公众号用于调用微信JS接口的临时票据
 * 
 */
public class JsapiTicket {
	// 获取到的凭证
	private String jsapi_ticket;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	public String getJsapi_ticket() {
		return jsapi_ticket;
	}

	public void setJsapi_ticket(String jsapi_ticket) {
		this.jsapi_ticket = jsapi_ticket;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}