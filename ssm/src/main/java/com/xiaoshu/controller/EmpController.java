package com.xiaoshu.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Echarts;
import com.xiaoshu.entity.Emp;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.Tj;
import com.xiaoshu.service.EmpService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;

@Controller
@RequestMapping("/emp")
public class EmpController {
	static Logger logger = Logger.getLogger(EmpController.class);
	@Autowired
	private EmpService es;
	@Autowired
	private OperationService operationService;
	
	
	@RequestMapping("empIndex")
	public String index(HttpServletRequest request,Integer menuid) {
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		return "emp";
	}
	
	@RequestMapping("emplist")
	public void deptList(Emp emp,HttpServletRequest request,HttpServletResponse response,String offset,String limit){
		try {
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<Emp> pageInfo = es.findEmpPage(emp, pageNum, pageSize);
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",pageInfo.getTotal() );
			jsonObj.put("rows", pageInfo.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("展示错误",e);
		}
	}
	
	@RequestMapping("export")
	public void dc(HttpServletRequest request,HttpServletResponse response) throws Exception {
		JSONObject result=new JSONObject();
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet=wb.createSheet();
		String title[]= {"编号","员工名","生日","部门"};
		HSSFRow first=sheet.createRow(0);
		for(int i=0;i<title.length;i++) {
			first.createCell(i).setCellValue(title[i]);
		}
		
		List<Emp> el=es.findAllEmp();
		for(int i=0;i<el.size();i++) {
			HSSFRow r=sheet.createRow(i+1);
			r.createCell(0).setCellValue(el.get(i).getEid());
			r.createCell(1).setCellValue(el.get(i).getEname());
			r.createCell(2).setCellValue(el.get(i).getEbirth());
			r.createCell(3).setCellValue(el.get(i).getDept().getDname());
		}
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("员工列表.xls", "UTF-8"));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/octet-stream");
//		OutputStream os;
//		File f=new File("d:/aaa.xls");
//		os=new FileOutputStream(f);
		
		wb.write(response.getOutputStream());
		
		result.put("success", true);
	}
	
	
	
	
	
	@RequestMapping("dcemp")
	public void dcemp(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet s1=wb.createSheet();
		HSSFRow f=s1.createRow(0);
		String[] titles= {"员工编号","员工姓名","生日","部门"};
		for(int i=0;i<titles.length;i++) {
			f.createCell(i).setCellValue(titles[i]);
		}
		
		List<Emp> el=es.findAllEmp();
		for(int i=0;i<el.size();i++) {
			HSSFRow r=s1.createRow(i+1);
			r.createCell(0).setCellValue(el.get(i).getEid());
			r.createCell(1).setCellValue(el.get(i).getEname());
			r.createCell(2).setCellValue(TimeUtil.formatTime(el.get(i).getEbirth(), "yyyy-MM-dd"));
			r.createCell(3).setCellValue(el.get(i).getDept().getDname());
		}
		
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode("测试员工下载.xlsx", "UTF-8"));
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "application/octet-stream");

		wb.write(response.getOutputStream());
	
		
		
//		File exc=new File("f:/员工信息表.xls");
//		OutputStream os=new FileOutputStream(exc);
//		wb.write(os);
//		wb.close();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("success", true);
	}
	
	@RequestMapping("dr")
	@ResponseBody
	public void dr(MultipartFile wj) throws Exception {
		HSSFWorkbook wb=new HSSFWorkbook(wj.getInputStream());
		HSSFSheet s1=wb.getSheetAt(0);
		int num=s1.getLastRowNum();
		for(int i=1;i<=num;i++) {
			HSSFRow r=s1.getRow(i);
			String name=r.getCell(0).getStringCellValue();
			int did=(int) r.getCell(2).getNumericCellValue();
			String bith=r.getCell(1).getStringCellValue();
			
			Emp e=new Emp();
			e.setEname(name);
			e.setEbirth(TimeUtil.ParseTime(bith, "yyyy-MM-dd"));
			e.setDid(did);
			es.add(e);
		}
	}
	
	
	@RequestMapping("gettj")
	@ResponseBody
	public void getTj(Emp emp,HttpServletRequest request,HttpServletResponse response){
		try {
			List<Tj> l=es.getTj();
			Object json = JSONObject.toJSON(l);
	        response.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("展示错误",e);
		}
	}
	
	
	
	@RequestMapping("/getecharts")
	public void getEcharts(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List<Echarts> el=es.getEcharts();
		Object json=JSONObject.toJSON(el);
		response.getWriter().write(json.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//导入
	@RequestMapping("xdr")
	@ResponseBody
	public void xdr(MultipartFile drwj) throws Exception {
		//先得到工作簿
		Workbook wb=WorkbookFactory.create(drwj.getInputStream());
		//得到sheet
		Sheet s1=wb.getSheetAt(0);
		//导入要导入多条所以需要循环。循环开始位置好确定，结束位置不好确定，需要得多最后一条的行号
		int num=s1.getLastRowNum();		
		//得到格
		for(int i=1;i<=num;i++) {
			Row r=s1.getRow(i);
			String ename=r.getCell(0).getStringCellValue();
			//String dname=r.getCell(2).getStringCellValue();
			//根据部门名称得到部门ID的方法
			int did=(int) r.getCell(2).getNumericCellValue();
			String birthday=r.getCell(1).getStringCellValue();
			Emp e=new Emp();
			e.setEname(ename);
			e.setDid(did);
			e.setEbirth(TimeUtil.ParseTime(birthday, "yyyy-MM-dd"));
			es.add(e);
			
			
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
