package cn.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jiyun.mapper.StuMapper;
import cn.jiyun.pojo.Student;

@Service
public class StuServiceImpl implements StuService {

	@Autowired
	private StuMapper sm;
	public List<Student> findAll() {
		// TODO Auto-generated method stub
		List<Student> list = sm.findAll();
		return list;
	}

}
