package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Dept;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Role;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.DeptService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("/dept")
public class DeptController {
	static Logger logger = Logger.getLogger(DeptController.class);
	@Autowired
	private DeptService ds;
	@Autowired
	private OperationService operationService;
	
	@RequestMapping("deptIndex")
	public String index(HttpServletRequest request,Integer menuid) {
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		ds.findDpet();
		return "dept";
	}
	
	@RequestMapping("deptlist")
	public void deptList(HttpServletRequest request,HttpServletResponse response,String offset,String limit){
		JSONObject result=new JSONObject();
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Dept> pageInfo = ds.findDpetPage(pageNum, pageSize);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",pageInfo.getTotal() );
			jsonObj.put("rows", pageInfo.getList());
	        WriterUtil.write(response,jsonObj.toString());
	        result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色展示错误",e);
		}
	}
	
	@RequestMapping("/reserveDpet")
	public void addAndEditDept(HttpServletRequest request,Dept d,HttpServletResponse response) {
		Integer did = d.getDid();
		JSONObject result=new JSONObject();
		try {
			if (did != null) {   // userId不为空 说明是修改

				ds.updateDept(d);
				result.put("success", true);

				
			}else {   // 添加
				//新增操作
				ds.addDept(d);
				result.put("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("deleteDept")
	public void delDept(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				ds.deleteDept(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	@RequestMapping("/exportexcel")
	public void dc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet();
		String title[]= {"编号","部门名"};
		HSSFRow first=sheet.createRow(0);
		for(int i=0;i<title.length;i++) {
			first.createCell(i).setCellValue(title[i]);
		}
		
		List<Dept> el=ds.findDpet();
		for(int i=0;i<el.size();i++) {
			HSSFRow r=sheet.createRow(i+1);
			r.createCell(0).setCellValue(el.get(i).getDid());
			r.createCell(1).setCellValue(el.get(i).getDname());
		}
		OutputStream os;
		File file = new File(request.getSession().getServletContext().getRealPath("/")+"部门信息.xls");
		os = new FileOutputStream(file);
		wb.write(os);
		os.close();

	}
}
