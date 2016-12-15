package com.hospital.suntek.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpsClientUtil {

	private HttpsClient https = new HttpsClient();

	public final int POST_METHOD = 1;
	public final int PUT_METHOD = 2;
	public final int GET_METHOD = 3;
	public final int DELETE_METHOD = 4;
	
	public class ResponseInfo {
		int code;
		public String body;
	}

	public ResponseInfo doPost(String url, Map<String, String> headers, Map<String, String> content, String charset) {

		HttpPost post = null;
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			post = new HttpPost(url);

			// 设置请求头
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					post.addHeader(elem.getKey(), elem.getValue());
				}
			}

			// 设置参数
			if (content != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator<Entry<String, String>> iterator = content.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}

				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
					post.setEntity(entity);
				}
			}
			HttpResponse response = https.execute(post);
			if (response != null) {
				responseInfo.code = response.getStatusLine().getStatusCode();
				System.out.println(response.getStatusLine().toString());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseInfo.body = EntityUtils.toString(resEntity, charset);
				}
			} else {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseInfo;
	}

	public ResponseInfo doPost(String url, Map<String, String> headers, Map<String, String> content) {
		return doPost(url, headers, content, "utf-8");
	}

	public ResponseInfo doPost(String url, Map<String, String> headers, String content, String charset) {
		HttpPost post = null;
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			post = new HttpPost(url);

			// 设置请求头
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					post.addHeader(elem.getKey(), elem.getValue());
				}
			}
			// 设置POST内容
			if (!content.isEmpty()) {
				StringEntity stringEntity = new StringEntity(content, charset);
				post.setEntity(stringEntity);
			}
			HttpResponse response = https.execute(post);
			if (response != null) {
				responseInfo.code = response.getStatusLine().getStatusCode();
				System.out.println(response.getStatusLine().toString());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseInfo.body = EntityUtils.toString(resEntity, charset);
				}
			} else {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseInfo;
	}

	public ResponseInfo doPost(String url, Map<String, String> headers, String content) {
		return doPost(url, headers, content, "utf-8");
	}

	public ResponseInfo doPut(String url, Map<String, String> headers, Map<String, String> content, String charset) {

		HttpPut put = null;
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			put = new HttpPut(url);

			// 设置请求头
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					put.addHeader(elem.getKey(), elem.getValue());
				}
			}

			// 设置参数
			if (content != null) {
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator<Entry<String, String>> iterator = content.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
				}

				if (list.size() > 0) {
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
					put.setEntity(entity);
				}
			}
			HttpResponse response = https.execute(put);
			if (response != null) {
				responseInfo.code = response.getStatusLine().getStatusCode();
				System.out.println(response.getStatusLine().toString());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseInfo.body = EntityUtils.toString(resEntity, charset);
				}
			} else {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseInfo;
	}

	public ResponseInfo doPut(String url, Map<String, String> headers, Map<String, String> content) {
		return doPut(url, headers, content, "utf-8");
	}

	public ResponseInfo doPut(String url, Map<String, String> headers, String content, String charset) {
		HttpPut put = null;
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			put = new HttpPut(url);

			// 设置请求头
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					put.addHeader(elem.getKey(), elem.getValue());
				}
			}
			// 设置POST内容
			if (!content.isEmpty()) {
				StringEntity stringEntity = new StringEntity(content, charset);
				put.setEntity(stringEntity);
			}
			HttpResponse response = https.execute(put);
			if (response != null) {
				responseInfo.code = response.getStatusLine().getStatusCode();
				System.out.println(response.getStatusLine().toString());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseInfo.body = EntityUtils.toString(resEntity, charset);
				}
			} else {

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseInfo;
	}

	public ResponseInfo doPut(String url, Map<String, String> headers, String content) {
		return doPut(url, headers, content, "utf-8");
	}

	public ResponseInfo doGet(String url, Map<String, String> headers, String charset) {
		HttpGet get = null;
		ResponseInfo responseInfo = new ResponseInfo();
		try {
			get = new HttpGet(url);

			// 设置请求头
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					get.addHeader(elem.getKey(), elem.getValue());
				}
			}
			HttpResponse response = https.execute(get);
			if (response != null) {
				responseInfo.code = response.getStatusLine().getStatusCode();
				System.out.println(response.getStatusLine().toString());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseInfo.body = EntityUtils.toString(resEntity, charset);
				}
			} else {

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return responseInfo;
	}

	public ResponseInfo doGet(String url, Map<String, String> headers) {
		return doGet(url, headers, "utf-8");
	}

	public ResponseInfo doDelete(String url, Map<String, String> headers, String charset) {
		HttpDelete del = null;
		ResponseInfo responseInfo = new ResponseInfo();

		try {
			del = new HttpDelete(url);

			// 设置请求头
			if (headers != null) {
				Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
				while (iterator.hasNext()) {
					Entry<String, String> elem = iterator.next();
					del.addHeader(elem.getKey(), elem.getValue());
				}
			}
			HttpResponse response = https.execute(del);
			if (response != null) {
				responseInfo.code = response.getStatusLine().getStatusCode();
				System.out.println(response.getStatusLine().toString());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					responseInfo.body = EntityUtils.toString(resEntity, charset);
				}
			} else {

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return responseInfo;
	}

	public ResponseInfo doDelete(String url, Map<String, String> headers) {
		return doDelete(url, headers, "utf-8");
	}
}
