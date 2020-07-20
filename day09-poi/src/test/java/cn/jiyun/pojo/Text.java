package cn.jiyun.pojo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class Text {
	@Test
	public void out() throws Exception {
		
		Student student = new Student();
		student.setSid(1);
		student.setSname("小王");
		student.setSex("女");
		student.setBirthday(new Date("2020/09/09"));
		student.setHobby("唱歌");
		student.setCid(1);
		
		Student student2 = new Student();
		student2.setSid(2);
		student2.setSname("小话");
		student2.setSex("女");
		student2.setBirthday(new Date("2020/09/09"));
		student2.setHobby("唱歌");
		student2.setCid(1);
		
		Student student3 = new Student();
		student3.setSid(3);
		student3.setSname("小史");
		student3.setSex("女");
		student3.setBirthday(new Date("2020/09/09"));
		student3.setHobby("唱歌");
		student3.setCid(1);
		
		Student student4 = new Student();
		student4.setSid(4);
		student4.setSname("小艾");
		student4.setSex("女");
		student4.setBirthday(new Date("2020/09/09"));
		student4.setHobby("唱歌");
		student4.setCid(1);
		
		
		List<Student> list=new ArrayList<Student>();
		list.add(student);
		list.add(student2);
		list.add(student3);
		list.add(student4);
		
		
		//1,实例化工作簿
		XSSFWorkbook workbook=new XSSFWorkbook();
		//2.创建sheet页
		XSSFSheet sheet=workbook.createSheet("学生信息");
		//3.创建row
		XSSFRow row0=sheet.createRow(0);
		//4.创建单元格并赋值
		String[] title= {"编号","姓名","性别","生日","爱好","班级"};
		for (int i = 0; i < title.length; i++) {
			XSSFCell cell =row0.createCell(i);
			cell.setCellValue(title[i]);
		}
		for (int i = 0; i < list.size(); i++) {
			XSSFRow row=sheet.createRow(i+1);
			row.createCell(0).setCellValue(list.get(i).getSid());
			row.createCell(1).setCellValue(list.get(i).getSname());
			row.createCell(2).setCellValue(list.get(i).getSex());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			row.createCell(3).setCellValue(simpleDateFormat.format(list.get(i).getBirthday()));
			row.createCell(4).setCellValue(list.get(i).getHobby());
			if(1==(list.get(i).getCid())) {
				row.createCell(5).setCellValue("H1909A");
			}else if(2==(list.get(i).getCid())) {
				row.createCell(5).setCellValue("H1909B");
			}else {
				row.createCell(5).setCellValue("H1909C");
			}
		}
		//5.导出
		FileOutputStream outputStream=new FileOutputStream("E:/学生信息表.xlsx");
		workbook.write(outputStream);
	}                
	@Test
	public void in() throws Exception {
		 FileInputStream fileInputStream = new FileInputStream("E:/学生信息表.xlsx");
		 //1.创建工作簿
		  XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream); 
		  //2.读取sheet页
		  XSSFSheet sheet=workbook.getSheetAt(0);
		  //3.获取行
		  int lastRowNum=sheet.getLastRowNum();
		  List<Student> list=new ArrayList<Student>();
		  for (int i = 1; i <=lastRowNum; i++) {
			  XSSFRow row=sheet.getRow(i);
			  Student student=new Student();
			  student.setSid((int)row.getCell(0).getNumericCellValue());
			  student.setSname(row.getCell(1).getStringCellValue());
			  student.setSex(row.getCell(2).getStringCellValue());
			  String bir=row.getCell(3).getStringCellValue();
			  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			  student.setBirthday(simpleDateFormat.parse(bir));
			  student.setHobby(row.getCell(4).getStringCellValue());
			  if ("H1909A".equals(row.getCell(5).getStringCellValue())) {
				  student.setCid(1);
			}else if("H1909B".equals(row.getCell(5).getStringCellValue())) {
				student.setCid(2);
			}else {
				student.setCid(3);
			}
			list.add(student);
		}
		  System.out.println(list);
		  
	}

}
