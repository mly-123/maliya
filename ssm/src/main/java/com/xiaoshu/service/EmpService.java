package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.EmpMapper;
import com.xiaoshu.entity.Echarts;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.Tj;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class EmpService {
	
	@Autowired
	private EmpMapper em;
	
	public PageInfo<Emp> findEmpPage(Emp emp, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Emp> empList = em.selectEmpAndDept(emp);
		PageInfo<Emp> pageInfo = new PageInfo<Emp>(empList);
		return pageInfo;
	}
	
	public List<Emp> findAllEmp(){
		List<Emp> empList = em.selectEmpAndDept(null);
		return empList;
	}
	
	public void add(Emp e) {
		em.insert(e);
	}
	
	public List<Tj> getTj(){
		return em.getTj();
	}
	
	
	public List<Echarts> getEcharts(){
		return em.getEcharts();
	}

}
