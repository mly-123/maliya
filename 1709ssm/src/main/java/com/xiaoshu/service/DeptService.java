package com.xiaoshu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JEditorPane;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeptMapper;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.RoleExample;
import com.xiaoshu.entity.RoleExample.Criteria;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class DeptService {
	
	@Autowired
	DeptMapper dm;
	
	@Autowired
	private JedisPool jp;
	
	@Autowired
	private RedisTemplate rt;
	
	
	
//	
//	@Autowired
//	private JedisPool jp;
	
//	@Autowired
//	private RedisTemplate redistemplate;
	
	public PageInfo<Dept> findDpetPage(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Dept> deptList = dm.selectAll();
		PageInfo<Dept> pageInfo = new PageInfo<Dept>(deptList);
		return pageInfo;
	}
	
	public List<Dept> findDpet() {
		List<Dept> l=new ArrayList<Dept>();
		l=(List<Dept>) rt.boundHashOps("hc").get("dept");
		if(!CollectionUtils.isEmpty(l)) {
			System.out.println("缓存里有，走缓存");
			for(Dept d:l) {
				System.out.println(d.getDname());
			}
			
			
			
			
			
			return l;
		}
		System.out.println("缓存里没有，走数据库");
		l=dm.selectAll();
		if(!CollectionUtils.isEmpty(l)) {
			rt.boundHashOps("hc").put("dept", l);
		}
		return l;
	}
//		List<Dept> deptList=new ArrayList<Dept>();
////		Jedis j=jp.getResource();
////		String deptl=j.hget("cache", "dept");
//		//deptList=(List<Dept>) redistemplate.boundHashOps("cache").get("dept");
//		if(!CollectionUtils.isEmpty(deptList)) {
//
//			System.out.println("走redis");
//			return deptList;
//		}
		
		
//		if(com.xiaoshu.util.StringUtil.isNotEmpty(deptl)) {
//			deptList=JSONArray.parseArray(deptl,Dept.class);
//			System.out.println("走redis");
//			return deptList;
//		}		
//		deptList= dm.selectAll();
//		System.out.println("走数据库");
//		if(!CollectionUtils.isEmpty(deptList)) {
////			Map<String, String> m=new HashMap<String, String>();
////			m.put("dept", JSONObject.toJSONString(deptList));
//			//j.hset("cache", m);
////			j.hset("cache", "dept", JSONObject.toJSONString(deptList));
//			//redistemplate.boundHashOps("cache").put("dept", deptList);
//		}
//		
//		return deptList;
	//}

	public void addDept(Dept d) {
		dm.insert(d);
		rt.delete("hc");
//		redistemplate.delete("cache");
	}

	public void updateDept(Dept d) {
		rt.delete("hc");
		dm.updateByPrimaryKey(d);
		
	}

	public void deleteDept(int did) {
		dm.deleteByPrimaryKey(did);
		rt.delete("hc");
		
	}

}
