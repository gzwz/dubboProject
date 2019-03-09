package cn.qumiandan.constant;

/**
 * redis缓存 key 前缀
 * @author yld
 *
 */
public class RedisKeyPrefix {

	/**
	 * 与前段交互web工程用户shiro session前缀
	 */
	public static final String MUTUALWEB_SHIRO_SESSION = "MUTUALWEB:SHIRO:SESSION:";
	
	/**
	 * 与前段交互web工程用户shiro cache前缀
	 */
	public static final String MUTUALWEB_SHIRO_CACHE = "MUTUALWEB:SHIRO:CACHE:";
}
