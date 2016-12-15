package com.hospital.suntek.util;

import java.io.IOException;
import java.util.ArrayList;


public class RestApiTest {
	public static void main(String[] args) throws IOException {

		ConferenceManager mgr = ConferenceManager.getInstance();

		// 获取最高支持版本
		mgr.getVersion();

		Conference conf = new Conference();
		conf.subject = "RESTFul API Test";
		conf.startTime = System.currentTimeMillis() + 18000000;
		conf.timeLen = 3600000;

		conf.attendees = new ArrayList<>();

		Conference.Attendee attendee = new Conference.Attendee();
		attendee.addressEntry = new ArrayList<>();
		Conference.AddressEntry addressEntry = new Conference.AddressEntry();

		attendee.attendeeName = "苏裕威";
		attendee.conferenceRole = Conference.CONFERENCE_ROLE_CHAIR;
		addressEntry.address = "sip:+8613642710979_e@ims.ge.chinamobile.com";
		addressEntry.type = Conference.ADDRESS_ENTRY_TYPE_PHONE;
		attendee.addressEntry.add(addressEntry);
		conf.attendees.add(attendee);

		attendee = new Conference.Attendee();
		attendee.addressEntry = new ArrayList<>();
		addressEntry = new Conference.AddressEntry();
		attendee.attendeeName = "苏焕杰";
		attendee.conferenceRole = Conference.CONFERENCE_ROLE_GENERAL;
		addressEntry.address = "sip:+8613631383400_e@ims.ge.chinamobile.com";
		addressEntry.type = Conference.ADDRESS_ENTRY_TYPE_PHONE;
		attendee.addressEntry.add(addressEntry);
		conf.attendees.add(attendee);

		/*attendee = new ConferenceInfo.Attendee();
		attendee.addressEntry = new ArrayList<>();
		addressEntry = new ConferenceInfo.AddressEntry();
		attendee.attendeeName = "王杰";
		attendee.conferenceRole = ConferenceInfo.CONFERENCE_ROLE_GENERAL;
		addressEntry.address = "sip:+8613426206217_e@ims.ge.chinamobile.com";
		addressEntry.type = ConferenceInfo.ADDRESS_ENTRY_TYPE_PHONE;
		attendee.addressEntry.add(addressEntry);
		conf.attendees.add(attendee);*/

		conf.size = conf.attendees.size() + 3;
		conf.mediaTypes = new ArrayList<>();
		conf.mediaTypes.add(Conference.MEDIA_TYPE_VIDEO);
		
		conf.mediaxAccount = "sip:+8613642710979@ims.ge.chinamobile.com";
		conf.mediaxPwd = "suntek123";

		/*// 预约会议
		if (mgr.orderConference(conf) != 0) {
			System.out.println("orderConference failed.");
			return;
		}

		// 取消预约会议
		mgr.cancelConference(conf);*/

		// 创建即时会议
		if (mgr.createConference(conf) != 0) {
			System.out.println("createConference failed.");
			return;
		}

		ConferenceControl ctrl = new ConferenceControl();
		ctrl.login(conf.confId, conf.chairPwd);
		
		//ctrl.subscribeConference("http://59.46.81.40:8080/notifyConference", "http://59.46.81.40:8080/notifySpeakerChange", mgr.token, true);
		
		ArrayList<Conference.InviteParas> participants = new ArrayList<>();
		Conference.InviteParas participant = new Conference.InviteParas();

		// 请求会议详情
		mgr.queryConferenceInfo(conf);

		for (int i = 0; i < 5; i++) {
			ctrl.queryOnlineConference(conf);
			participants.clear();
			participant.name = "苏焕杰";
			participant.phone = "sip:+8613631383400_e@ims.ge.chinamobile.com";
			participant.role = Conference.CONFERENCE_ROLE_GENERAL;
			participants.add(participant);
			// 邀请与会者
			ctrl.inviteParticipants(participants);

			participants.clear();
			participant.name = "苏裕威";
			participant.phone = "sip:+8613642710979_e@ims.ge.chinamobile.com";
			participant.role = Conference.CONFERENCE_ROLE_CHAIR;
			participants.add(participant);
			// 邀请与会者
			ctrl.inviteParticipants(participants);

			/*participants.clear();
			participant.name = "王杰";
			participant.phone = "sip:+8613426206217_e@ims.ge.chinamobile.com";
			participant.role = Conference.CONFERENCE_ROLE_GENERAL;
			participants.add(participant);
			// 邀请与会者
			ctrl.inviteParticipants(participants);*/
		}
		ctrl.queryOnlineConference(conf);

		// 请求会议详情
		mgr.queryConferenceInfo(conf);
		
		// 结束会议
		ctrl.destroyConference();
		//ctrl.subscribeConference("http://59.46.81.40:8080/notifyConference", "http://59.46.81.40:8080/notifySpeakerChange", mgr.token, false);
		ctrl.logout();
	}
}
