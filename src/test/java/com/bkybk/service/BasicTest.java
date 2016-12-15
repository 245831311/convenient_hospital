package com.bkybk.service;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
//@RunWith(SpringJUnit4ClassRunner.class) 
@WebAppConfiguration(value = "src/main/webapp")  
@ContextConfiguration(locations= {"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class BasicTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Test
    public void test() {
    }
}
