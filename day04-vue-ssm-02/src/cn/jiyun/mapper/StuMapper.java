package cn.jiyun.mapper;

import java.util.List;

import cn.jiyun.pojo.Clazz;
import cn.jiyun.pojo.Student;

public interface StuMapper {

	List<Student> findAll();

	List<Clazz> findClazz();

	int addStu(Student stu);

	int delStu(Integer[] ids);

	int updateStu(Student stu);

}
