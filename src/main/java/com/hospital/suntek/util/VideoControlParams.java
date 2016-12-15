package com.hospital.suntek.util;

import java.util.ArrayList;

public class VideoControlParams {

	public final static String IMAGE_TYPE_SINGLE = "Single"; // 单画面
	public final static String IMAGE_TYPE_TWO = "Two"; // 二画面
	public final static String IMAGE_TYPE_THREE = "Three"; // 三画面
	public final static String IMAGE_TYPE_FOUR = "Four"; // 四画面
	public final static String IMAGE_TYPE_SIX = "Six"; // 六画面
	public final static String IMAGE_TYPE_EIGHT = "Eight"; // 八画面
	public final static String IMAGE_TYPE_NINE = "Nine"; // 九画面
	public final static String IMAGE_TYPE_THIRTEENR = "ThirteenR"; // 十三画面R
	public final static String IMAGE_TYPE_THIRTEENM = "ThirteenM"; // 十三画面M
	public final static String IMAGE_TYPE_SIXTEEN = "Sixteen"; // 十六画面

	public final static String SWITCH_MODE_CHANGE_OVER_VOICE = "ChangeOverVoice"; // 画面随着声音切换
	public final static String SWITCH_MODE_FIXATION = "Fixation"; // 画面固定

	public final static String SWITCH_MODE_FIXED = "Fixed";
	public final static String SWITCH_MODE_AUTOBROWSE = "AutoBrowse";
	public final static String SWITCH_MODE_VAS = "VAS";
	public final static String SWITCH_MODE_ROLL_CALL = "RollCall";
	public final static String SWITCH_MODE_FREE = "Free";

	public String imageType; // 画面的显示方式
	public String switchMode; // 画面的切换方式
	public ArrayList<String> displayContent; // 当switchMode为固定画面的时候该字段必须填写
	public ArrayList<SubscriberInPic> subscriberInPics; // 每个画面中轮询显示的用户列表集合
	public int switchTime; // 会议中多用户切换的时间间隔,单位毫秒

	public VideoTemplate videoTemplates[]; // 终端档次视频模板,只适用于会议管理,不用于会议控制

	public static class VideoTemplate {
		public String templateName; // 模板名称,用户填写
		public String videoCodec; // 视频编码类型
		public String audioCodec; // 音频编码类型
		public String bandWidth; // 带宽类型
		public String resolutionRatio; // 分辨率
		public String type; // 只有当预定混合网真会议时,需要使用该字段进行区分对应的是高清的档次还是网真的档次
		public boolean isDefaultSelected; // 是否是默认模板
	}

	public static class SubscriberInPic {
		public int index; // 多画面中每个画面的编号,编号从1开始
		public String subscriber; // 每个画面中用户标识,即呼叫ID
		public String currentDisplaySubscriber; // 当前正在显示的用户标志,即呼叫ID
		public boolean isMainPicture; // 是否是主画面
		public boolean isAssistStream; // 是否为辅流
	}

	public static class VideoSelection {
		public ArrayList<SubscriberInPic> subscriberInPics;
		public int switchTime = 5;
		public boolean isSelection = false;
		public String dstScreen;
	}
}
