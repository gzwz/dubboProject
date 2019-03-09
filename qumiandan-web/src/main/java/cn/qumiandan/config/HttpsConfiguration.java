package cn.qumiandan.config;

import org.springframework.beans.factory.annotation.Value;

//@Configuration()
public class HttpsConfiguration {
	
	@Value("${server.port}")
	private int port;

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}
}