package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Echarts;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.EmpExample;
import com.xiaoshu.entity.Tj;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EmpMapper extends BaseMapper<Emp> {
	
	List<Emp> selectEmpAndDept(Emp emp);
   
	List<Tj> getTj();
	
	
	
	List<Echarts> getEcharts();
}