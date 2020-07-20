package cn.jiyun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.jiyun.mapper.StuMapper;
import cn.jiyun.pojo.Clazz;
import cn.jiyun.pojo.Student;

@Service
public class StuService {
	
	@Autowired
	private StuMapper stuMapper;

	public List<Student> findAll() {
		// TODO Auto-generated method stub
		return stuMapper.findAll();
	}

	public List<Clazz> findClazz() {
		// TODO Auto-generated method stub
		return stuMapper.findClazz();
	}

	public int addStu(Student stu) {
		// TODO Auto-generated method stub
		int i=stuMapper.addStu(stu);
		return i;
	}

	public int delStu(Integer[] ids) {
		// TODO Auto-generated method stub
		int i=stuMapper.delStu(ids);
		return i;
	}

	public int updateStu(Student stu) {
		// TODO Auto-generated method stub
		int i = stuMapper.updateStu(stu);
		return i;
	}

	

}
