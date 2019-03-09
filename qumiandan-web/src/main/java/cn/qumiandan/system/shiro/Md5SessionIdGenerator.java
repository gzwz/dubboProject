package cn.qumiandan.system.shiro;

import java.io.Serializable;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

/**
 * 自定义生成md5加密uuid
 * @author yuleidian
 * @version 创建时间：2018年11月2日 下午5:15:58
 */
public class Md5SessionIdGenerator implements SessionIdGenerator{

	@Override
	public Serializable generateId(Session session) {
		// MD5Util.encrypt(UUID.randomUUID().toString())
		return UUID.randomUUID().toString().replace("-", "");
	}
}
