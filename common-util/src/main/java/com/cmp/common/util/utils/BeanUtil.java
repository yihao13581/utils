package com.cmp.common.util.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @CLassName BeanUtil
 * @Description: Bean管理工具类
 * @Author: shenhao
 * @date: 2019/12/4 17:01
 * @Version 1.0
 */
public final class BeanUtil {

	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);

	public static Date parse(String date) {
		String method = "parse";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return isEmpty(date) ? null : dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("BeanUtil" + method + "convert String 2 Date Error.", e);
		}
		return null;
	}

	public static String format(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return date == null ? "" : dateFormat.format(date);
	}

	public static boolean isEmpty(String value) {
		return (value == null) || ("".equals(value.trim()));
	}

	public static boolean inNotEmpty(String value) {
		return !isEmpty(value);
	}

	public static Object getValueFormField(Field field, String name, String value) {
		Object object = value;
		String type = field.getType().getSimpleName();
		String tempNum = value;
		if ((isEmpty(tempNum)) && ("short,int,integer,long,double,float".contains(type))) {
			tempNum = "0";
		}
		if ("short".equalsIgnoreCase(type)) {
			object = Short.valueOf(tempNum);
		} else if (("char".equals(type)) || ("Character".equals(type))) {
			if (!isEmpty(tempNum))
				object = Character.valueOf(tempNum.charAt(0));
		} else if (("int".equals(type)) || ("Ingeger".equals(type)))
			object = Integer.valueOf(tempNum);
		else if ("long".equalsIgnoreCase(type))
			object = Long.valueOf(tempNum);
		else if ("byte".equalsIgnoreCase(type))
			object = Byte.valueOf(tempNum);
		else if ("float".equalsIgnoreCase(type))
			object = Float.valueOf(tempNum);
		else if ("double".equalsIgnoreCase(type))
			object = Double.valueOf(tempNum);
		else if ("boolean".equalsIgnoreCase(type))
			object = Boolean.valueOf(tempNum);
		else if ("Date".equalsIgnoreCase(type))
			object = parse(tempNum);
		else if ("byte[]".equalsIgnoreCase(type)) {
			object = ConvertUtil.string2Bytes(tempNum);
		}
		return object;
	}

	public static Object getValueFormField(Object object, Field field) {
		try {
			Object value=field.get(object);
			String type=field.getType().getSimpleName();
			if("short".equals(type))
				value=Short.valueOf(field.getShort(object));
			else if("char".equals(type))
				value=Character.valueOf(field.getChar(object));
			else if("int".equals(type))
				value=Integer.valueOf(field.getInt(object));
			else if("long".equals(type))
				value=Long.valueOf(field.getLong(object));
			else if("byte".equals(type))
				value=Byte.valueOf(field.getByte(object));
			else if("float".equals(type))
				value=Float.valueOf(field.getFloat(object));
			else if("double".equals(type))
				value=Double.valueOf(field.getDouble(object));
			else if("boolean".equals(type))
				value=Boolean.valueOf(field.getBoolean(object));
			else if("Date".equals(type))
				value=format((Date)value);
			else if("byte[]".equalsIgnoreCase(type)){
				if(value!=null)
					value = ConvertUtil.bytes2String((byte[])value);}
				else if ("String".equalsIgnoreCase(type)){
						if (value == null) {
							value ="";
						}
				}else{
					value=field.get(object);
			}
return value;

		} catch (Exception e) {
			logger.error("BeanUtil getValueFromField error ",e);
			return null;
		}
	}
	//rawtype,unchecked/
	public static Map convertBean(Object bean){
		Class type=bean.getClass();
		Map returnMap=new HashMap();
		BeanInfo beanInfo=null;
		try{
			beanInfo = Introspector.getBeanInfo(type);
		}catch(IntrospectionException e){
			logger.error("转bean出错",e);
		}
		if(null==beanInfo){
			logger.error("转bean出错，beanInfo==null");
			return returnMap;
		}
		PropertyDescriptor[] propertyDescriptors=beanInfo.getPropertyDescriptors();
		try{
			for(int i=0;i<propertyDescriptors.length;i++){
				PropertyDescriptor descriptor=propertyDescriptors[i];
				String propertyName=descriptor.getName();
				if(!"class".equals(propertyName)){
					Method readMethod=descriptor.getReadMethod();
					Object result=readMethod.invoke(bean,new Object[0]);
					if(result!=null){
						returnMap.put(propertyName,result);
					}
				}
			}

		}catch(IllegalAccessException e){
			logger.error("转bean出错",e);
		}catch(InvocationTargetException e){
			logger.error("转bean出错",e);
		}
		return returnMap;
	}
}
