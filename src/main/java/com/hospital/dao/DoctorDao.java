package com.hospital.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hospital.common.Code;
import com.hospital.common.MybatisBaseDao;
import com.hospital.common.ServiceException;
import com.hospital.model.User;
/**
 * 用户DAO
 * @author lhj
 *
 */
@Repository("doctorDao")
public class DoctorDao extends MybatisBaseDao<Object,String> {
}