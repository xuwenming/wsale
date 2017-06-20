package jb.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 * 信任管理器
 * @author 李欣桦
 * @date 2014-11-21下午9:15:08
 */
public class MyX509TrustManager implements X509TrustManager {
	
	 private X509Certificate[] certificates;

	// 检查客户端证书
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		if (this.certificates == null) {
            this.certificates = chain;
            System.out.println("init at checkClientTrusted");
        }
	}

	// 检查服务器端证书
	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		if (this.certificates == null) {
            this.certificates = chain;
            System.out.println("init at checkServerTrusted");
        }
	}

	// 返回受信任的X509证书数组
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
