package cn.jiyun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.jiyun.pojo.Clazz;
import cn.jiyun.pojo.Student;
import cn.jiyun.service.StuService;

@Controller
@RequestMapping("stu")
public class StuController {

	@Autowired
	private StuService service;

	// 查询所有
	@RequestMapping("toShow")
	public String toShow() {
		return "show";
	}

	//@RequestMapping("findAll")
	//@ResponseBody
	//public List<Student> findAll() {
	//	List<Student> list = service.findAll();
	//	System.out.println(list);
	//	return list;
	//}
	
	//查询所有加分页
	@RequestMapping("findAll")
	@ResponseBody
	public PageInfo<Student> findAll(@RequestParam(defaultValue = "0",required = true)Integer pageNum){
		PageHelper.startPage(pageNum, 2);
		List<Student> student=service.findAll();
		PageInfo<Student> pageInfo = new PageInfo<Student>(student);
		return pageInfo;
	}

	// 跳转添加页面
	@RequestMapping("toAdd")
	public String toAdd() {
		return "add";
	}

	// 查询班级列表findClazz
	@ResponseBody
	@RequestMapping("findClazz")
	public List<Clazz> findClazz() {
		List<Clazz> cList = service.findClazz();
		return cList;
	}

	// 添加页面
	@ResponseBody
	@RequestMapping("addStu")
	public int addStu(@RequestBody Student stu) {
		int i = service.addStu(stu);
		return i;
	}

	// 删除
	@ResponseBody
	@RequestMapping("delStu")
	public int delStu(@RequestBody Integer[] ids) {
		int i = service.delStu(ids);
		return i;
	}

	// 修改
	@ResponseBody
	@RequestMapping("updateStu")
	public int updateStu(@RequestBody Student stu) {
		int i = service.updateStu(stu);
		return i;
	}

}
