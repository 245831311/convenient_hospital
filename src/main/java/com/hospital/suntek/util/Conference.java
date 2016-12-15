package com.hospital.suntek.util;

import java.util.ArrayList;
import java.util.List;

public class Conference {

	public final static String CONFERENCE_ROLE_CHAIR = "chair";
	public final static String CONFERENCE_ROLE_GENERAL = "general";

	public final static String ADDRESS_ENTRY_TYPE_EMAIL = "email";
	public final static String ADDRESS_ENTRY_TYPE_SMS = "sms";
	public final static String ADDRESS_ENTRY_TYPE_PHONE = "phone";
	public final static String ADDRESS_ENTRY_TYPE_LEFT_PHONE = "leftPhone";
	public final static String ADDRESS_ENTRY_TYPE_RIGHT_PHONE = "rightPhone";

	public final static String MEDIA_TYPE_AUDIO = "Audio";
	public final static String MEDIA_TYPE_VIDEO = "Video";
	public final static String MEDIA_TYPE_HDVIDEO = "HDVideo";
	public final static String MEDIA_TYPE_TELEPRESENCE = "Telepresence";
	public final static String MEDIA_TYPE_DATA = "Data";
	public final static String MEDIA_TYPE_DESKTOPSHARING = "DesktopSharing";

	public final static int ASSIST_MEDIA_TYPE_FILM = 1;
	public final static int ASSIST_MEDIA_TYPE_LIVE = 2;

	public final static String MEDIA_CODEC_H261 = "H.261";
	public final static String MEDIA_CODEC_H263 = "H.263";
	public final static String MEDIA_CODEC_H264 = "H.264";
	public final static String MEDIA_CODEC_MPEG4 = "MPEG4";

	public final static String MEDIA_BAND_WIDTH_128K = "128K";
	public final static String MEDIA_BAND_WIDTH_384K = "384K";
	public final static String MEDIA_BAND_WIDTH_512K = "512K";
	public final static String MEDIA_BAND_WIDTH_768K = "768K";
	public final static String MEDIA_BAND_WIDTH_1M = "1M";
	public final static String MEDIA_BAND_WIDTH_2M = "2M";
	public final static String MEDIA_BAND_WIDTH_4M = "4M";
	public final static String MEDIA_BAND_WIDTH_8M = "8M";

	public final static String MEDIA_RESOLUTION_RATIO_QCIF = "QCIF";
	public final static String MEDIA_RESOLUTION_RATIO_GIF = "GIF";
	public final static String MEDIA_RESOLUTION_RATIO_4CIF = "4CIF";
	public final static String MEDIA_RESOLUTION_RATIO_720P = "720P";
	public final static String MEDIA_RESOLUTION_RATIO_1080P = "1080P";

	public String subject; // 会议主题,长度限制为128个字符
	public String confId; // 会议ID
	public String subConfId; // 子会议ID
	public String chairPwd; // 主持人密码
	public String generalPwd; // 普通与会者密码
	public String mediaxAccount;// 主持人Web账号
	public String mediaxPwd;// 主持人Web密码
	public long startTime; // 会议开始时间,单位毫秒,使用UTC时间,预定创建会议时,如果没有指定开始时间,则表示会议马上开始
	public int timeZone = 56; // 时区,各个时区的枚举值
	public long timeLen; // 会议时长,单位毫秒
	public int size; // 会议方数
	public List<String> mediaTypes; // 会议媒体类型
	public String language = "zh-CN"; // 语言
	public int summerTime; // 夏令时时长。单位是毫秒
	public boolean isAllowInvite = true; // 是否接受邀请
	public boolean isAllowVideoControl = true; // 是否允许视频控制,true：允许视频控制,false：不允许视频控制
	public List<Attendee> attendees; // 预定会议时,指定的与会者列表,该与会者列表可以用于发送会议通知、会议提醒、会议开始时候进行自动邀请
	public int welcomeVoiceID = 0; // -1:无提示音, 0:系统默认提示音
	public int firstAttendeeVoiceID = -1;// -1:无提示音, 0:系统默认提示音
	public int enterVoiceID = -2; // -1:无提示音, -2:提示音(DING), -3:提示音(DU),
									// -4:广播与会者姓名
	public int leaveVoiceID = -2; // -1:无提示音, -2:提示音(DING), -3:提示音(DU),
									// -4:广播与会者姓名
	public boolean isWaitChairman = false; // 主席入会前会议是否自动闭音,直到主席入会后自动解除会议闭,
	// true:默认闭音,false:默认不闭音
	public boolean isAutoInvite = false; // 是否进行自动邀请
	public boolean isCycleType = false; // 是否是周期会议
	public boolean isAllowRecord = false; // 会议是否启用网络录制
	public boolean isAutoRecord = false; // 会议是否自动启动录制,该参数只有isAllowRecord为true的情况下才生效
	public AssistantMedia assistantMediaParams; // 辅流参数
	public boolean isPermitGuestEnter = true; // 当主席还没入会时，是否允许来宾入会
	public int accessValidateType = 1; // 鉴权方式, 1:统一接入+会议ID+会议密码

	// "AutoBrowse":自动浏览,也叫轮询模式,轮询广播使用该枚举值,"VAS":声控切换模式,启用声控选用该枚举值,
	// "RollCall":点名模式

	// 由会议能力服务器填写
	public String scheduserName; // 会议预定者We账号名,长度最大限制为96个字符
	public String scheduserMobile; // 会议预定者的手机号
	public String conferenceState; // 会议当前状态, "Schedule":预定状态,
									// "Creating":正在创建状态,
									// "Created": 会议已经被创建，并正在召开,
									// "Destroyed”：会议已经关闭, "Noncreated":会议尚未召开
	public String accessNumber; // 会议接入号码
	public String conferenceType; // 会议类型
	public String accountID; // 会议预定者的用户ID
	public boolean isNeedParticipatorLimit; // 是否需要与会者限制
	public long remainningTime; // 会议剩余时间
	public boolean isLockState; // 会议锁定状态
	public boolean isAllMute; // 全场闭音状态
	public boolean isReportSpeaker; // 标记会议是否开启发言方上报功能
	public boolean isReportNetCondition; // 标记会议是否开启网络质量上报功能
	public boolean isConfAssistantOnline; // 标记会客室中的会议助手是否在线
	public String recordStatus; // 客户端录制会议状态, "Start":开始录制, "Pause":暂停录制,
								// "Stop":停止录制
	public String confVideoMode; // 会议视频模式, "Free":自由选看模式,仅适用于智真会议,
									// "Fixed":固定画面广播模式,广播使用该枚举值,
	
	public List<Participant> participants; // 与会者列表
	public List<InviteState> inviteStates; // 邀请用户状态列表

	public static class Attendee {
		public String attendeeName; // 与会者名称,长度限制为96个字符
		public String conferenceRole; // 会议中的角色
		public List<AddressEntry> addressEntry; // 地址条目数组
		public String attendeeType; // 与会者类型
		public boolean isDefaultMute; // 与会者入会时默认是否闭音
		public String pinCode; // 与会者pin码
		public String position; // 用户职位
	}

	public static class AddressEntry {
		public String address; // 地址信息,IMS账号
		public String type; // 地址类型, 目前默认类型为手机
	}

	public static class AssistantMedia {
		public int type = ASSIST_MEDIA_TYPE_FILM; // 角色标签(静态图片/活动图象)
		public int mpi = 5; // 辅流能力支持的帧率,当前取值范围 1－30
		public String codec = MEDIA_CODEC_H264; // 视频的编码格式
		public String bandWidth = MEDIA_BAND_WIDTH_512K; // 辅流能力支持的带宽
		public String resolutionRatio = MEDIA_RESOLUTION_RATIO_4CIF;// 辅流能力支持的画面分辨率
	}
	
	public static class Participant {
		public String participantID;
		public String updateTime;
		public String name;
		public List<Subscriber> subscribers;
		public String role;
		public int state;
		public String participantType;
		public String mediaTypes;
		public String attendTime;
		public boolean canListen;
		public boolean isMute;
		public boolean allowVideo;
		public boolean isRollcalled;
		public boolean hasTokenRing;
		public boolean isShare;
		public boolean isBroadcast;
		public boolean isSpeaking;
		public NetworkStatus networkStatus;
		public int regionID;
		public boolean isReqModerator;
		public String handsState;
		public String account;
		public String position;
		public int speakingVolume;
		public int hasCamera;
		public int multiStreamFlag;
		
	}
	
	public static class Subscriber {
		public String subscriberID;
		public String callID;
		public int state;
		public String screenType;
		public String viewedCallID;
		public NetworkStatus networkStatus;
	}
	
	public static class NetworkStatus {
		public String netInfo;
		public String lostPacketRate;
	}
	
	public static class InviteState {
		public String name; // 用户姓名，最大不超过127个字符
		public String phone; // 用户电话号码
		public String callId; // 呼叫ID。会议中、正在邀请的用户返回呼叫ID
		public String updateTime; // 邀请信息信更新的时间(毫秒级)
		public int state; // 邀请结果状态
	}

	public static class Agenda {
		public final static String TO_BE_DONE = "ToBeDone";
		public final static String ON_GOING = "OnGoing";
		public final static String DONE = "Done";

		public int index; // 议程索引，不能重复
		public long startTime; // 议程开始时间
		public long length; // 议程持续时长
		public String status; // 议程状态
		public String content; // 议程内容
	}
	
	public static class InviteParas {
		public String name;
		public String phone;
		public String email;
		public String sms;
		public String role;
		public String accountId;
		public String position;
		public boolean isMute = false;
	}

}
