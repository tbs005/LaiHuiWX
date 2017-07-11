package com.lhpc.util;

public class ConfigUtil {

	private ConfigUtil() {

	}

	/**
	 * 微信相关配置
	 */
	public static final String WX_APP_ID = "wxd6d79c4ca0fef838";// 公众号id
	public static final String WX_SECRET_KEY = "bace843875970ae9a941dcb6dbe16e6d";// 公众号秘钥
	public static final String WX_WEB_MCH_SECRET_KEY = "bace843875970ae9a941dcb6dbe16edd";
	public static final String WX_WEB_MCH_ID = "1439584802";// 商户号
	public static final String WX_LAIHUI_APP_SECRET_KEY = "2bfca107a5dac789f2fdhg1ca5ee7695";
	public static final String WX_PAY_WEB_NOTIFY_URL = "https://lhwx.laihuipinche.com/wx/wx_pay/notify";// 正式
	// public static final String WX_PAY_WEB_NOTIFY_URL = "https://lhwx.laihuipinche.com/wx_pays/notify/";//测试

	public static final String API_ADD_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template";// 获取微信消息模板URL
	public static final String GET_ALL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template";// 获取微信消息模板列表URL
	public static final String DEL_PRIVATE_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/del_private_template";// 删除微信消息模板URL
	public static final String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";// 获取微信消息模板URL
	public static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
	public static final String GET_OPENID = "https://api.weixin.qq.com/sns/oauth2/access_token";

	public static final String PUSH_PUBLISH = "7dN2fUAKlDU8oDHRStNoMEqWKCOgScnuS13SYw2JTCQ";// 发布成功推送模板

	public static final String PUSH_SCHEDULED = "whG3hS1O1Id05ma-mBakL3oF1TcQ6EKqry-BZjW67LU";// 拼车定座模板

	public static final String PUSH_AGGRESS_SCHEDULED = "5ILXYOr353BhHBl1_dA7EpbT1btnlA9Em0zkqjzb1bg"; // 订座成功通知

	/*
	 * 聚合短信验证码配置
	 */
	public static final String APPKEY = "6c871b315def3b449dbdbce28a4dc55a";
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	public static final int LOGIN_TPL_ID = 29230; // 登录验证码模板
	public static final int PUBLISH_SUCCESS = 39244; // 车主行程发布成功模板(发送给车主)
	public static final int PAY_SUCCESS = 39245; // 乘客支付成功模板(发送给车主)
	public static final int UNSUBSCRIBE_TRAVEL = 39246; // 乘客退订模板(发送给车主)
	public static final int SUCCESS_BOOK = 39247; // 乘客预订成功模板(发送给乘客)
	public static final int DENIAL_BOOK = 39252; // 车主拒绝乘客预定模板(发送给乘客)
	public static final int EXTRACT_CASH = 39385; // 车主提现到账(发送给车主)
	public static final int APPLY_EXTRACT = 39429; // 车主申请提现(发送给车主)

	/**
	 * 颜色
	 */
	public static final String BLACK = "#000000";// 黑色
	public static final String BLUE = "#222966";// 蓝色
	public static final String GREEN = "#0db407";// 绿色

}
