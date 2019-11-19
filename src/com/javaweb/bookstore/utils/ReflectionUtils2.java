package com.javaweb.bookstore.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射的 Utils 函数集合
 * 提供访问私有变量, 获取泛型类型 Class, 提取集合中元素属性等 Utils 函数
 * @author Administrator
 *
 */
public class ReflectionUtils2 {

	/**
	 * 通过反射, 获得定义 Class 时声明的父类的泛型参数的类型
	 * 如: public EmployeeDao extends BaseDao<Employee, String>
	 * @param clazz
	 * @param index
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index){
		Type type = clazz.getGenericSuperclass();
		
		if (!(type instanceof ParameterizedType)) {
			return Object.class;
		}
		
		Type[] typeArgs = ((ParameterizedType) type).getActualTypeArguments();
		
		if (index > typeArgs.length || index < 0) {
			return Object.class;
		}
		
		if (!(typeArgs[index] instanceof Class)) {
			return Object.class;
		}
		
		return (Class) typeArgs[index];
	}
	
	/**
	 * 通过反射, 获得 Class 定义中声明的父类的泛型参数类型
	 * 如: public EmployeeDao extends BaseDao<Employee, String>
	 * @param <T>
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Class<T> getSuperGenericType(Class clazz){
//		if (clazz != null) {
//			return getSuperClassGenricType(clazz, 0);
//		}
//		
//		return null;
		return getSuperClassGenricType(clazz, 0);
	}
	
	/**
	 * 循环向上转型, 获取对象的 DeclaredMethod
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @return
	 */
	public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes){
		
//		for (Class superClass = object.getClass(); superClass != Object.class; superClass = parameterTypes.getClass()) {
//			
//		}
		
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 使 filed 变为可访问
	 * @param field
	 */
	public static void makeAccessible(Field field){
		
//		if (field != null) {
//			field.setAccessible(true);
//		}
		if (!Modifier.isPublic(field.getModifiers())) {
			field.setAccessible(true);
		}
	}
	
	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * @param object
	 * @param filedName
	 * @return
	 */
	public static Field getDeclaredField(Object object, String fieldName){
		
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		return null;
	}
	
	/**
	 * 直接调用对象方法, 而忽略修饰符(private, protected)
	 * @param object
	 * @param methodName
	 * @param parameterTypes
	 * @param parameters
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static Object invokeMethod(Object object, String methodName, Class<?> [] parameterTypes,
			Object [] parameters) throws InvocationTargetException {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		
		if (method == null) {
			throw new IllegalArgumentException("Could not find the method [" + methodName + "] on target [" + object + "]");
		}
		
		try {
			method.invoke(object, parameters);
		} catch (IllegalAccessException e) {
			System.out.println("不可能抛出的异常！");
		}
		return null;
	}
	
	/**
	 * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
	 * @param object
	 * @param fieldName
	 * @param value
	 */
	public static void setFieldValue(Object object, String fieldName, Object value){
		
//		Field field = getDeclaredField(object, fieldName);
//		try {
//			field.set(object, value);
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Field field = getDeclaredField(object, fieldName);
		
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		
		makeAccessible(field);
		
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			System.out.println("不可能抛出的异常！");
		}
		
	}
	
	/**
	 * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object object, String fieldName){
		Field field = getDeclaredField(object, fieldName);
		
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
		}
		
		makeAccessible(field);
		
		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			System.out.println("不可能抛出的异常！");
		}
		
		return result;
	}
}
