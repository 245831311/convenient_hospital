package com.hospital.common;

/**
 * 返回码定义
 * 
 * @author chenxb
 *
 */
public interface Code {
	/**
	 * 返回代码定义，操作成功
	 */
	public static final int CODE_OK = 200;

	/**
	 * 请求非法
	 */
	public static final int CODE_BAD_REQUEST = 400;
	/**
	 * 资源冲突 
	 */
	public static final int CODE_CONFLICT = 409;
	
	/**
	 * 未认证
	 */
	public static final int CODE_UNAUTHORIZED = 401;
	
	/**
	 * 禁止访问
	 */
	public static final int CODE_FORBIDDEN = 403;
	
	/**
	 * 资源不存在
	 */
	public static final int CODE_NOT_FOUND = 404;
	
	/**
	 * 请求不被接受
	 */
	public static final int CODE_NOT_ACCEPTABLE = 406;

	/**
	 * 服务器内部错误
	 */
	public static final int CODE_SERVER_ERROR = 500;
	
	/**
	 * 发送限制
	 */
	public static final int CODE_LIMIT_ERROR = 999;
	
	
	public static final int CODE_XMPP_ACCOUNT_DISABLED = 801;
	
	/**
	 * 
	 */
	public static final int CODE_XMPP_RECALL_BUSY = 10003;

	/**
	 * 组内转接时,客服不在线
	 */
	public static final int CODE_XMPP_TRANSFER_OFFLINE = 10009;
	
	/**
     * 无客服人员在线
     */
    public static final int CODE_XMPP_NOT_AGENT = 20000;
    /**
     * 无客服人员在线，但具有机器人能力
     */
    public static final int CODE_XMPP_NOT_AGENT_WITHROBOT = 20001;
}
