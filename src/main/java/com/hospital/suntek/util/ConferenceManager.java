package com.hospital.suntek.util;

import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.Element;

public class ConferenceManager {

	public final int CONFERENCE_ACTION_ORDER = 1;
	public final int CONFERENCE_ACTION_CREATE = 2;
	public final int CONFERENCE_ACTION_UPDATE = 3;

	private Map<String, UserInfo> userInfos = new HashMap<>();

	public String token;
	private class UserInfo {
		public UserInfo(String account, String password) {
			this.account = account;
			this.password = password;
		}

		public String token;
		public String account;
		public String password;
	}

	public HttpsClientUtil httpsClientUtil = new HttpsClientUtil();

	private static final String mediaXAddress = "https://221.176.60.36/rest/V3R8C30";

	private ConferenceManager() {

	}

	private static final ConferenceManager MANAGER = new ConferenceManager();

	public static ConferenceManager getInstance() {
		return MANAGER;
	}

	private UserInfo getUserInfo(Conference conf) {
		UserInfo userInfo = userInfos.get(conf.mediaxAccount);
		if (userInfo == null) {
			userInfo = new UserInfo(conf.mediaxAccount, conf.mediaxPwd);
			userInfos.put(conf.mediaxAccount, userInfo);
		}
		return userInfo;
	}

	private String login(String username, String password) {
		String token = null;
		try {
			String url = mediaXAddress + "/login?" + "accountName=" + URLEncoder.encode(username, "utf-8")
					+ "&password=" + URLEncoder.encode(password, "utf-8") + "&accountType=WEB";
			System.out.println(url);

			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "text/xml;charset=UTF-8");

			String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><account><ipaddr>"
					+ InetAddress.getLocalHost().toString() + "</ipaddr></account>";

			HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPost(url, headers, content);
			System.out.println(responseInfo.body);
			if (responseInfo.code != 200) {
				return null;
			}
			Document document = null;
			try {
				document = DocumentHelper.parseText(responseInfo.body);
				Node node = document.selectSingleNode("/loginResult/result/resultCode");
				int resultCode = Integer.parseInt(node.getText());
				if (resultCode != MediaXResultCode.CODE_SUCCESS) {
					System.out.printf("user %s login failed, result code %d\n", username, resultCode);
					return null;
				}
				node = document.selectSingleNode("/loginResult/profile/token");
				token = node.getText();
				this.token = token;
				System.out.printf("Get Token: %s\n", token);

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return token;
	}

	@SuppressWarnings("unused")
	private int logout(String token) {
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
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				System.out.printf("logout failed, result code %d\n", resultCode);
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public String getVersion() {
		String url = mediaXAddress + "/version";
		System.out.println(url);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doGet(url, null);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return null;
		}
		return responseInfo.body;
	}

	public int doConference(Conference conf, int action) {
		UserInfo userInfo = getUserInfo(conf);
		if (userInfo.token == null) {
			userInfo.token = login(userInfo.account, userInfo.password);
			if (userInfo.token == null) {
				System.out.println("user login faild");
				return -1;
			}
		}

		Document document = DocumentHelper.createDocument();
		Element confxml = document.addElement("conferenceInfo");
		confxml.addElement("timeZone").setText(String.valueOf(conf.timeZone));
		if (conf.timeLen == 0 || conf.size == 0 || conf.mediaTypes == null) {
			return -1;
		}
		confxml.addElement("length").setText(String.valueOf(conf.timeLen));
		confxml.addElement("size").setText(String.valueOf(conf.size));
		for (int i = 0; i < conf.mediaTypes.size(); i++) {
			confxml.addElement("mediaTypes").setText(String.valueOf(conf.mediaTypes.get(i)));
		}
		confxml.addElement("language").setText(conf.language);
		confxml.addElement("isAllowInvite").setText(String.valueOf(conf.isAllowInvite));
		confxml.addElement("accessValidateType").setText(String.valueOf(conf.accessValidateType));

		if (action != CONFERENCE_ACTION_CREATE && conf.startTime != 0) {
			confxml.addElement("startTime").setText(String.valueOf(conf.startTime));
		}

		if (!conf.subject.isEmpty()) {
			confxml.addElement("subject").setText(conf.subject);
		}

		confxml.addElement("isAllowVideoControl").setText(String.valueOf(conf.isAllowVideoControl));
		if (conf.attendees != null) {
			for (int i = 0; i < conf.attendees.size(); i++) {
				Conference.Attendee attendee = conf.attendees.get(i);
				Element attendeeElem = confxml.addElement("attendees");

				attendeeElem.addElement("attendeeName").setText(attendee.attendeeName);
				attendeeElem.addElement("conferenceRole").setText(attendee.conferenceRole);
				Element address = attendeeElem.addElement("addressEntry");
				if (attendee.addressEntry != null) {
					for (int j = 0; j < attendee.addressEntry.size(); j++) {
						Conference.AddressEntry addressEntry = attendee.addressEntry.get(j);
						address.addElement("address").setText(addressEntry.address);
						address.addElement("type").setText(addressEntry.type);
					}
				}
			}
		}

		String content = document.asXML();
		System.out.println(content);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + userInfo.token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		if (action == CONFERENCE_ACTION_CREATE || action == CONFERENCE_ACTION_ORDER) {
			String url = mediaXAddress + "/conference";
			System.out.println(url);

			HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPost(url, headers, content);
			System.out.println(responseInfo.body);
			if (responseInfo.code != 200) {
				return -1;
			}

			try {
				document = DocumentHelper.parseText(responseInfo.body);
				Node node = document.selectSingleNode("/scheduleConferenceResult/result/resultCode");
				int resultCode = Integer.parseInt(node.getText());
				if (resultCode != MediaXResultCode.CODE_SUCCESS) {
					return -1;
				}
				node = document.selectSingleNode("/scheduleConferenceResult/conferenceInfo/conferenceKey/conferenceID");
				conf.confId = node.getText();
				node = document
						.selectSingleNode("/scheduleConferenceResult/conferenceInfo/conferenceKey/subConferenceID");
				conf.subConfId = node.getText();

				node = document.selectSingleNode("/scheduleConferenceResult/conferenceInfo/accessNumber");
				conf.accessNumber = node.getText();

				List<?> nodes = document.selectNodes("/scheduleConferenceResult/conferenceInfo/passwords");
				Iterator<?> iterator = nodes.iterator();
				while (iterator.hasNext()) {
					Element elem = (Element) iterator.next();
					String role = elem.element("conferenceRole").getText();
					if (role.equals("chair")) {
						conf.chairPwd = elem.element("password").getText();
					} else if (role.equals("general")) {
						conf.generalPwd = elem.element("password").getText();
					}
				}

				System.out.printf("Get ConfId: %s, subConfId: %s, chairPwd: %s, generalPwd: %s\n", conf.confId,
						conf.subConfId, conf.chairPwd, conf.generalPwd);

			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else if (action == CONFERENCE_ACTION_UPDATE) {
			String url = mediaXAddress + "/conferences/" + conf.confId + "/subConferenceID/" + conf.subConfId;
			System.out.println(url);

			HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, content);
			System.out.println(responseInfo.body);
			if (responseInfo.code != 200) {
				return -1;
			}

			try {
				Node node = document.selectSingleNode("/result/resultCode");
				int resultCode = Integer.parseInt(node.getText());
				if (resultCode != MediaXResultCode.CODE_SUCCESS) {
					if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
							|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
						userInfo.token = login(userInfo.account, userInfo.password);
						if (userInfo.token == null) {
							System.out.println("user login faild");
							return -1;
						} else {
							return doConference(conf, action);
						}
					}
					System.out.printf("%s do %d conference failed, result code %d\n", userInfo.account, action,
							resultCode);
					return -1;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}

		return 0;
	}

	public int orderConference(Conference conf) {
		return doConference(conf, CONFERENCE_ACTION_ORDER);
	}

	public int createConference(Conference conf) {
		return doConference(conf, CONFERENCE_ACTION_CREATE);
	}

	public int updateConference(Conference conf) {
		return doConference(conf, CONFERENCE_ACTION_UPDATE);
	}

	public int queryConferenceInfo(Conference conf) {
		UserInfo userInfo = getUserInfo(conf);
		if (userInfo.token == null) {
			userInfo.token = login(userInfo.account, userInfo.password);
			if (userInfo.token == null) {
				System.out.println("user login faild");
				return -1;
			}
		}

		String url = mediaXAddress + "/conferences/" + conf.confId + "/subConferenceID/" + conf.subConfId;
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + userInfo.token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doGet(url, headers);
		System.out.println(responseInfo.body);

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/conferenceResult/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					userInfo.token = login(userInfo.account, userInfo.password);
					if (userInfo.token == null) {
						System.out.println("user login faild");
						return -1;
					} else {
						return queryConferenceInfo(conf);
					}
				}
				System.out.printf("%s queryConferenceInfo failed, result code %d\n", userInfo.account, resultCode);
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int cancelConference(Conference conf) {
		UserInfo userInfo = getUserInfo(conf);
		if (userInfo.token == null) {
			userInfo.token = login(userInfo.account, userInfo.password);
			if (userInfo.token == null) {
				System.out.println("user login faild");
				return -1;
			}
		}

		String url = mediaXAddress + "/conferences/" + conf.confId + "/subConferenceID/" + conf.subConfId;
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + userInfo.token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doDelete(url, headers);
		System.out.println(responseInfo.body);

		try {
			Document document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {

					userInfo.token = login(userInfo.account, userInfo.password);
					if (userInfo.token == null) {
						System.out.println("user login faild");
						return -1;
					} else {
						return cancelConference(conf);
					}
				}
				System.out.printf("%s cancelConference failed, result code %d\n", userInfo.account, resultCode);
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int resetConferencePassword(Conference conf) {
		UserInfo userInfo = getUserInfo(conf);
		if (userInfo.token == null) {
			userInfo.token = login(userInfo.account, userInfo.password);
			if (userInfo.token == null) {
				System.out.println("user login faild");
				return -1;
			}
		}
		String url = mediaXAddress + "/conferences/" + conf.confId + "/subConferenceID/" + conf.subConfId
				+ "/resetConferencePassword";
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + userInfo.token);
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPut(url, headers, "");
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		Document document = DocumentHelper.createDocument();
		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					userInfo.token = login(userInfo.account, userInfo.password);
					if (userInfo.token == null) {
						System.out.println("user login faild");
						return -1;
					} else {
						return resetConferencePassword(conf);
					}
				}
				System.out.printf("%s resetConferencePassword failed, result code %d\n", userInfo.account, resultCode);
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int queryConferenceList(Conference conf) {
		UserInfo userInfo = getUserInfo(conf);
		if (userInfo.token == null) {
			userInfo.token = login(userInfo.account, userInfo.password);
			if (userInfo.token == null) {
				System.out.println("user login faild");
				return -1;
			}
		}

		String url = mediaXAddress + "/conferenceList";
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + userInfo.token);
		headers.put("Content-Type", "text/xml;charset=UTF-8");

		Document document = DocumentHelper.createDocument();
		Element conferenceFilter = document.addElement("conferenceFilter");
		Element filter = conferenceFilter.addElement("filter");
		filter.addElement("resultFields").setText("StartTime");
		filter.addElement("resultFields").setText("Subject");
		filter.addElement("resultFields").setText("ConferenceID");
		filter.addElement("resultFields").setText("SubConferenceID");
		filter.addElement("resultFields").setText("ConferenceState");
		filter.addElement("resultFields").setText("Length");
		filter.addElement("resultFields").setText("TimeZone");
		filter.addElement("resultFields").setText("ScheduserName");
		filter.addElement("resultFields").setText("summerTime");
		filter.addElement("resultFields").setText("isRecordConference");
		filter.addElement("resultFields").setText("mediaTypes");
		filter.addElement("resultFields").setText("accessNumber");
		filter.addElement("resultFields").setText("factEndTime");
		filter.addElement("resultFields").setText("accountID");
		filter.addElement("resultFields").setText("rownum");
		filter.addElement("resultFields").setText("totalSize");

		Element conditions = filter.addElement("conditions");
		conditions.addElement("key").setText("ConferenceState");
		conditions.addElement("value").setText("Destroyed");
		conditions.addElement("matching").setText("equal");
		filter.addElement("isAscend").setText("false");
		filter.addElement("pageIndex").setText("0");
		filter.addElement("pageSize").setText("10");

		conferenceFilter.addElement("isIncludeInvitedConference").setText("true");

		String content = document.asXML();

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPost(url, headers, content);
		System.out.println(responseInfo.body);
		if (responseInfo.code != 200) {
			return -1;
		}

		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/conferenceList/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					userInfo.token = login(userInfo.account, userInfo.password);
					if (userInfo.token == null) {
						System.out.println("user login faild");
						return -1;
					} else {
						return queryConferenceList(conf);
					}
				}
				System.out.printf("%s queryConferenceList failed, result code %d\n", userInfo.account, resultCode);
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}

	public int applyJoinConference(Conference conf) {
		UserInfo userInfo = getUserInfo(conf);
		if (userInfo.token == null) {
			userInfo.token = login(userInfo.account, userInfo.password);
			if (userInfo.token == null) {
				System.out.println("user login faild");
				return -1;
			}
		}
		Document document = DocumentHelper.createDocument();
		Element attendConferenceReq = document.addElement("attendConferenceReq");
		attendConferenceReq.addElement("enableAV").setText("true");
		attendConferenceReq.addElement("enableData").setText("false");
		attendConferenceReq.addElement("nickName").setText("suntek");
		attendConferenceReq.addElement("language").setText("zh-CN");
		String content = document.asXML();

		String url = mediaXAddress + "/conferences/" + conf.confId + "/attendConference";
		System.out.println(url);

		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "Basic " + userInfo.token);

		HttpsClientUtil.ResponseInfo responseInfo = httpsClientUtil.doPost(url, headers, content);
		try {
			document = DocumentHelper.parseText(responseInfo.body);
			Node node = document.selectSingleNode("/attendConferenceResult/result/resultCode");
			int resultCode = Integer.parseInt(node.getText());
			if (resultCode != MediaXResultCode.CODE_SUCCESS) {
				if (resultCode == MediaXResultCode.CODE_TOKEN_DISABLE
						|| resultCode == MediaXResultCode.CODE_UNAUTHORIZED) {
					userInfo.token = login(userInfo.account, userInfo.password);
					if (userInfo.token == null) {
						System.out.println("user login faild");
						return -1;
					} else {
						return applyJoinConference(conf);
					}
				}
				System.out.printf("%s applyJoinConference failed, result code %d\n", userInfo.account, resultCode);
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		return 0;
	}
}
