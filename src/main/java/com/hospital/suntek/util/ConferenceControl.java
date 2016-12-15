package com.hospital.suntek.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

public class ConferenceControl {

	public final static int AGENDA_ACTION_ADD = 1;
	public final static int AGENDA_ACTION_UPDATE = 2;

	public String token;
	public String confId;
	public String chairPwd;
	public String eTag; // 扩展协议字段，用”最后修改时间的毫秒值”填充

	public HttpsClientUtil httpsClientUtil = new HttpsClientUtil();

	private static final String mediaXAddress = "https://221.176.60.36/rest/V3R8C30";

	public int login(String username, String password) {
		confId = username;
		chairPwd = password;
		try {
			String url = mediaXAddress + "/login?" + "accountName=" + URLEncoder.encode(username, "utf-8")
					+ "&password=" + URLEncoder.encode(password, "utf-8") + "&accountType=ConferenceID";
			System.out.println(url);

			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "text/xml;charset=UTF-8");

			StringBuffer content = new StringBuffer();
			content.append(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><account><ipaddr>58.62.93.91</ipaddr><pinCode>0</pinCode></account>");

			HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPost(url, headers, content.toString());
			System.out.println(responseInfo.body);
			if (responseInfo.code != 200) {
				return -1;
			}

			try {
				Document document = DocumentHelper.parseText(responseInfo.body);
				Node node = document.selectSingleNode("/loginResult/result/resultCode");
				int resultCode = Integer.parseInt(node.getText());
				if (resultCode != MediaXResultCode.CODE_SUCCESS) {
					return -1;
				}
				node = document.selectSingleNode("/loginResult/profile/token");
				token = node.getText();
				System.out.printf("Get Token: %s\n", token);

			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int logout() {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}
		String url = mediaXAddress + "/logout";
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doDelete(url, headers);
		System.out.println(responseInfo.body);
		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int inviteParticipants(ArrayList<Conference.InviteParas> participants) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}
		Document document = DocumentHelper.createDocument();
		Element parsxml = document.addElement("inviteParas");
		for (int i = 0; i < participants.size(); i++) {
			Conference.InviteParas participant = participants.get(i);
			Element parxml = parsxml.addElement("invitePara");
			if (participant.name != null) {
				parxml.addElement("name").setText(participant.name);
			}
			if (participant.phone != null) {
				parxml.addElement("phone").setText(participant.phone);
			}
			if (participant.email != null) {
				parxml.addElement("email").setText(participant.email);
			}
			if (participant.sms != null) {
				parxml.addElement("sms").setText(participant.sms);
			}
			if (participant.role != null) {
				parxml.addElement("role").setText(participant.role);
			}

			parxml.addElement("isMute").setText(String.valueOf(participant.isMute));

			if (participant.accountId != null) {
				parxml.addElement("accountId").setText(participant.accountId.toString());
			}
			if (participant.position != null) {
				parxml.addElement("position").setText(participant.position.toString());
			}
		}
		String content = document.asXML();
		System.out.println(content);
		String url = mediaXAddress + "/conferences/" + confId + "/participants";
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPost(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200 || responseInfo.code != 202) {
			return -1;
		}

		return 0;
	}

	public int cancelIviteParticipants(String phone) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}
		String url = mediaXAddress + "/conferences/" + confId + "/phone/" + phone;
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doDelete(url, headers);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					cancelIviteParticipants(phone);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int quitConference(String participantId) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/" + participantId;
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doDelete(url, headers);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					quitConference(participantId);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int deleteAttendee(String attendeeId) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/attendees/" + attendeeId;
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doDelete(url, headers);
		System.out.println(responseInfo.body);
		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					deleteAttendee(attendeeId);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int destroyConference() {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}
		String url = mediaXAddress + "/conferences/" + confId;
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doDelete(url, headers);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					destroyConference();
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int queryOnlineConference(Conference conf) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId;
		System.out.println(url);

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat greenwichDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		greenwichDate.setTimeZone(TimeZone.getTimeZone("GMT"));
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("If-Modified-Since", greenwichDate.format(cal.getTime()));
		if (eTag != null) {
			headers.put("If-None-Match", eTag);
		}

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doGet(url, headers);
		System.out.println(responseInfo.body);
		if (responseInfo.code == 404) {
			System.out.printf("Conference %s is not starting\n", conf.confId);
			return -1;
		}
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/spellQueryconference/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					queryOnlineConference(conf);
				}
				return -1;
			}

			List<?> nodes = document.selectNodes("/spellQueryconference/conference/inviteStates/inviteState");
			Iterator<?> iterator = nodes.iterator();
			conf.inviteStates = new ArrayList<>();
			while (iterator.hasNext()) {
				Conference.InviteState inviteState = new Conference.InviteState();
				Element elem = (Element) iterator.next();
				inviteState.updateTime = elem.elementText("updateTime");
				inviteState.name = elem.elementText("name");
				inviteState.phone = elem.elementText("phone");
				inviteState.state = Integer.parseInt(elem.elementText("state"));
				conf.inviteStates.add(inviteState);
			}
			
			nodes = document.selectNodes("/spellQueryconference/conference/participants/participant");
			iterator = nodes.iterator();
			conf.participants = new ArrayList<>();
			while (iterator.hasNext()) {
				Conference.Participant participant = new Conference.Participant();
				Element elem = (Element) iterator.next();
				participant.participantID = elem.elementText("participantID");
				participant.updateTime = elem.elementText("updateTime");
				participant.name = elem.elementText("name");
				participant.role = elem.elementText("role");
				participant.state = Integer.parseInt(elem.elementText("state"));
				participant.participantType = elem.elementText("participantType");
				participant.mediaTypes = elem.elementText("mediaTypes");
				participant.attendTime = elem.elementText("attendTime");
				participant.canListen = Boolean.parseBoolean(elem.elementText("canListen"));
				participant.isMute = Boolean.parseBoolean(elem.elementText("isMute"));
				participant.allowVideo = Boolean.parseBoolean(elem.elementText("allowVideo"));
				participant.isRollcalled = Boolean.parseBoolean(elem.elementText("isRollcalled"));
				participant.hasTokenRing = Boolean.parseBoolean(elem.elementText("hasTokenRing"));
				participant.isBroadcast = Boolean.parseBoolean(elem.elementText("isBroadcast"));
				participant.isSpeaking = Boolean.parseBoolean(elem.elementText("isSpeaking"));
				participant.isReqModerator = Boolean.parseBoolean(elem.elementText("isReqModerator"));

				participant.subscribers = new ArrayList<>();
				List<?> subs = elem.selectNodes("subscribers/subscriber");
				Iterator<?> itr = subs.iterator();
				while (itr.hasNext()) {
					Conference.Subscriber subscriber = new Conference.Subscriber();
					Element subElem = (Element) itr.next();
					subscriber.subscriberID = subElem.elementText("subscriberID");
					subscriber.callID = subElem.elementText("callID");
					subscriber.state = Integer.parseInt(subElem.elementText("state"));
					participant.subscribers.add(subscriber);
				}
				
				conf.participants.add(participant);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int getParticipantAuthorized(String participantId) {

		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/" + participantId + "/authorized";
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doGet(url, headers);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		return 0;
	}

	public int extendConference(int length) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/length";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		String content = "length=" + String.valueOf(length);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					extendConference(length);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchAllMute(boolean isAllMute) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/isAllMute ";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "isAllMute=" + String.valueOf(isAllMute);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchAllMute(isAllMute);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchParticipantMute(String participantId, boolean isMute) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/" + participantId + "/isMute";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "isMute=" + String.valueOf(isMute);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}
		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchParticipantMute(participantId, isMute);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchLockConference(boolean isLock) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/lockState";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "lockState=" + String.valueOf(isLock);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchLockConference(isLock);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchCanListen(String participantId, boolean isListen) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/" + participantId + "/canListen";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "canListen=" + String.valueOf(isListen);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchCanListen(participantId, isListen);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchRecord(boolean operation) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/setRecord";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "operation=" + String.valueOf(operation);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchRecord(operation);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchReportSpeaker(boolean isReport) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/isReportSpeaker";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "isReportSpeaker=" + String.valueOf(isReport);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}
		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchReportSpeaker(isReport);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchReportNetCondition(boolean isReport) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/isReportNetCondition";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "isReportNetCondition=" + String.valueOf(isReport);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchReportNetCondition(isReport);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchApplyChair(String participantId, boolean roleTags) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/" + participantId + "/roleTags";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "roleTags=" + String.valueOf(roleTags);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchApplyChair(participantId, roleTags);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int rejectApplyChair(String participantId) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/sets/" + participantId + "/roleTags";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "roleTags=false";

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					rejectApplyChair(participantId);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int acceptApplyChair(String participantId) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/moderator";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		Map<String, String> content = new HashMap<>();
		content.put("participantID", participantId);
		content.put("role", "chair");

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					acceptApplyChair(participantId);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int doAgenda(ArrayList<Conference.Agenda> agendas, int action) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/agenda";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		Document document = DocumentHelper.createDocument();
		Element agenda = document.addElement("agenda");
		for (int i = 0; i < agendas.size(); i++) {
			Conference.Agenda agendaInfo = agendas.get(i);
			Element agendaEntry = agenda.addElement("agendaEntry");
			agendaEntry.addElement("index").setText(String.valueOf(agendaInfo.index));
			agendaEntry.addElement("startTime").setText(String.valueOf(agendaInfo.startTime));
			agendaEntry.addElement("length").setText(String.valueOf(agendaInfo.length));
			agendaEntry.addElement("content").setText(agendaInfo.content);
		}
		String content = document.asXML();
		HttpsClientUtil.ResponseInfo responseInfo = null;

		if (action == AGENDA_ACTION_ADD) {
			responseInfo = httpsClientUtil.doPost(url, headers, content);
		} else if (action == AGENDA_ACTION_UPDATE) {
			responseInfo = httpsClientUtil.doPut(url, headers, content);
		}
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					doAgenda(agendas, action);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int addAgenda(ArrayList<Conference.Agenda> agendas) {
		return doAgenda(agendas, AGENDA_ACTION_ADD);
	}

	public int updateAgenda(ArrayList<Conference.Agenda> agendas) {
		return doAgenda(agendas, AGENDA_ACTION_UPDATE);
	}

	public int deleteAgenda(ArrayList<Integer> indexs) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/removeAgenda";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		Document document = DocumentHelper.createDocument();
		Element indices = document.addElement("indices");
		for (int i = 0; i < indexs.size(); i++) {
			indices.addElement("indice").setText(indexs.get(i).toString());
		}

		String content = document.asXML();

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					deleteAgenda(indexs);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int setAgendaStatus(ArrayList<Conference.Agenda> agendas) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/agendaStatus";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		Document document = DocumentHelper.createDocument();
		Element agenda = document.addElement("agenda");
		for (int i = 0; i < agendas.size(); i++) {
			Conference.Agenda agendaInfo = agendas.get(i);
			Element agendaEntry = agenda.addElement("agendaEntry");
			agendaEntry.addElement("index").setText(String.valueOf(agendaInfo.index));
			agendaEntry.addElement("agendaEntryStatus").setText(agendaInfo.status);
		}
		String content = document.asXML();

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					setAgendaStatus(agendas);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int queryAgendaInfo() {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/agenda";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doGet(url, headers);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/agendaResult/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					queryAgendaInfo();
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int selectScreen(String participantId, ArrayList<VideoControlParams.VideoSelection> videoSelections) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/" + participantId + "/srcScreen";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		Document document = DocumentHelper.createDocument();
		Element videoSelectionsElem = document.addElement("videoSelections");
		for (int i = 0; i < videoSelections.size(); i++) {
			VideoControlParams.VideoSelection videoSelection = videoSelections.get(i);
			Element videoSelectionElem = videoSelectionsElem.addElement("videoSelection");
			if (videoSelection.subscriberInPics != null) {
				for (int j = 0; j < videoSelection.subscriberInPics.size(); j++) {
					VideoControlParams.SubscriberInPic subscriberInPic = videoSelection.subscriberInPics.get(j);
					Element subscriberInPicElem = videoSelectionElem.addElement("subscriberInPics");
					subscriberInPicElem.addElement("index").setText(String.valueOf(subscriberInPic.index));
					subscriberInPicElem.addElement("type").setText("0");
					subscriberInPicElem.addElement("subscriber").setText(subscriberInPic.subscriber);
					subscriberInPicElem.addElement("currentDisplaySubscriber")
							.setText(subscriberInPic.currentDisplaySubscriber);
				}
			}
			videoSelectionElem.addElement("switchTime").setText(String.valueOf(videoSelection.switchTime));
			videoSelectionElem.addElement("selectionByScreen").setText(String.valueOf(videoSelection.isSelection));
			if (videoSelection.dstScreen != null) {
				videoSelectionElem.addElement("dstScreen").setText(videoSelection.dstScreen);
			}
		}

		String content = document.asXML();

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					selectScreen(participantId, videoSelections);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchMixedPicture(VideoControlParams param) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/mixedPicture";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		Document document = DocumentHelper.createDocument();
		Element mixedPicture = document.addElement("mixedPicture");
		mixedPicture.addElement("imageType").setText(param.imageType);
		mixedPicture.addElement("switchMode").setText(param.switchMode);
		mixedPicture.addElement("switchTime").setText(String.valueOf(param.switchTime));
		if (param.subscriberInPics != null) {
			for (int i = 0; i < param.subscriberInPics.size(); i++) {
				VideoControlParams.SubscriberInPic subscriberInPic = param.subscriberInPics.get(i);
				Element subscriberInPics = mixedPicture.addElement("subscriberInPics");
				subscriberInPics.addElement("index").setText(String.valueOf(subscriberInPic.index));
				subscriberInPics.addElement("type").setText("0");
				subscriberInPics.addElement("subscriber").setText(subscriberInPic.subscriber);
				subscriberInPics.addElement("currentDisplaySubscriber")
						.setText(subscriberInPic.currentDisplaySubscriber);
			}
		}

		String content = document.asXML();

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchMixedPicture(param);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int switchRollCall(String participantId, boolean isRollcalled) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/participants/" + participantId + "/isRollcalled";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "isRollcalled=" + String.valueOf(isRollcalled);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}
		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					switchRollCall(participantId, isRollcalled);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int swtichTokenRing(boolean isHas) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/hasTokenRing";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String content = "hasTokenRing=" + String.valueOf(isHas);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					swtichTokenRing(isHas);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int updateMediaType(ArrayList<String> mediaTypes) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/mediaTypes";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String types = null;
		for (int i = 0; i < mediaTypes.size(); i++) {
			types += mediaTypes.get(i) + ";";
		}
		String content = "";
		if (types != null) {
			content = "mediaTypes=" + types;
		}

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					updateMediaType(mediaTypes);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int subscribeConference(String notifyConference, String notifySpeakerChange, String confToken, boolean enable) {
		if (token == null) {
			System.out.println("You has not login");
			return -1;
		}

		String url = mediaXAddress + "/conferences/" + confId + "/subscribeConference";
		System.out.println(url);
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + token);
		headers.put("Conference-Authorization", "Basic " + confToken);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		Document document = DocumentHelper.createDocument();
		Element subscribeConference = document.addElement("subscribeConference");
		subscribeConference.addElement("address").setText(notifyConference);
		subscribeConference.addElement("addr4SpeakerChange").setText(notifySpeakerChange);
		subscribeConference.addElement("enable").setText(String.valueOf(enable));

		String content = document.asXML();

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/subscribeConference/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					if (login(confId, chairPwd) != 0) {
						return -1;
					}
					subscribeConference(notifyConference, notifyConference, confToken, enable);
				}
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
