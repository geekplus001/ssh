package com.atguigu.struts2.app.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {

	private DateFormat dateFormat;
	
	public DateConverter() {
		System.out.println("DateConverter's constructor...");
	}
	
	public DateFormat getDateFormat(){
		if(dateFormat == null){
			//获取当前 WEB 应用的初始化参数 pattern
			ServletContext servletContext = ServletActionContext.getServletContext();
			System.out.println(servletContext); 
			String pattern = servletContext.getInitParameter("pattern");
			dateFormat = new SimpleDateFormat(pattern);
		}
		
		return dateFormat;
	}
	
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		
		System.out.println("convertFromString...");
		
		if(toClass == Date.class){
			if(values != null && values.length > 0){
				String value = values[0];
				try {
					return getDateFormat().parseObject(value);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		//若没有转换成功, 则返回 values
		return values;
	}

	@Override
	public String convertToString(Map context, Object o) {
		
		System.out.println("convertToString...");
		
		if(o instanceof Date){
			Date date = (Date) o;
			return getDateFormat().format(date);
		}
		
		//若转换失败返回 null
		return null;
	}

}
