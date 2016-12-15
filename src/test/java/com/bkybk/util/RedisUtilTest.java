package com.bkybk.util;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hospital.constants.AppConstants;
import com.hospital.model.Agenda;
import com.hospital.util.RedisUtil;

import redis.clients.jedis.Jedis;

public class RedisUtilTest {
	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			//e.g redis存取普通字符串列表
			/*jedis = new Jedis("127.0.0.1",6379,100000);
			//jedis = new Jedis("127.0.0.1");
			jedis.rpush("sessionId", new String[]{"1","2","3","4","5"});
	        long len = jedis.llen("sessionId");
	        List<String> values = jedis.lrange("sessionId", 0, len);
	        System.out.println("删除前list:"+values);
	        
	        long count = jedis.del("sessionId");
	        len = jedis.llen("sessionId");
	        values = jedis.lrange("sessionId", 0, len);
	        System.out.println("成功删除"+count+"个元素!list:"+values);*/
	        
	        //e.g redis存取对象列表
	       /* Agenda agenda1 = new Agenda();
	        agenda1.setAgendaId(IdWorkerUtil.nextId());
	        agenda1.setStaff("张三");
	        byte[] b1 = SerializableUtils.serialize(agenda1);
	        String encodeB1 = new String(b1,AppConstants.CHARSET_ISO8859_1);
	        
	        Agenda agenda2 = new Agenda();
	        agenda2.setAgendaId(IdWorkerUtil.nextId());
	        agenda2.setStaff("李四");
	        byte[] b2 = SerializableUtils.serialize(agenda2);
	        String encodeB2 = new String(b2,AppConstants.CHARSET_ISO8859_1);
	        System.out.println("序列号对象:b1:"+encodeB1
        	+",b2:"+encodeB2);
	        
	        Agenda c1 = SerializableUtils.unserialize(encodeB1.getBytes(AppConstants.CHARSET_ISO8859_1), Agenda.class);
	        Agenda c2 = SerializableUtils.unserialize(encodeB2.getBytes(AppConstants.CHARSET_ISO8859_1), Agenda.class);
	        System.out.println("反序列号对象:c1:"+JSONObject.toJSONString(c1)
	        	+",c2:"+JSONObject.toJSONString(c2));*/
	        
			/*byte[]  b1= new byte[] { 50, 0, -1, 28, -24 };

			String string = new String(b1);

			byte[] ret = string.getBytes();
			System.out.println("ddd");
			
			
			byte[]  b2= new byte[] { 50, 0, -1, 28, -24 };
			
			String string2 = new String(b2, "ISO-8859-1");
			
			byte[] ret2 = string2.getBytes("ISO-8859-1");
			System.out.println("ddd2");*/
			
			RedisUtil.del("D36B957FCDBE85B17016153D0300ADA6-agendas");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
