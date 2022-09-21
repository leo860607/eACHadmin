package com.fstop.eachadmin.service;

import java.util.Map;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import util.SpringAppCtxHelper;
@Service
@Slf4j

/**
 * aop使用對應欄位的中文名稱
 * @author hugo
 *
 */
public class FieldsMapService implements BeanNameAware{
	public static String beanName = "";
	
	public Map<String, Object> args;
	
	public static Object getArg(String name)
	{
		FieldsMapService b = SpringAppCtxHelper.getBean(beanName);
		return b.getArgs().get(name);
	}

	public static String getStringArg(String name)
	{
		FieldsMapService b = SpringAppCtxHelper.getBean(beanName);
		if (b == null)
		{
			System.out.println("Arguments is null");
		}
		return (String) b.getArgs().get(name);
	}
	
	public Map<String, Object> getArgs() {
		return args;
	}

	public void setArgs(Map<String, Object> args) {
		this.args = args;
	}

	@Override
	public void setBeanName(String arg0) 
	{
		log.debug("set bean name=" + arg0);
		beanName = arg0;
	}
	
	
}

