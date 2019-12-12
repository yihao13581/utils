package com.cmp.common.util.exception;

/**
 * @CLassName GeneralException
 * @Description: 统一异常基础类（所有业务或系统级的异常必须继承该类或其子类）
 * @Author: shenhao
 * @date: 2019/12/10 17:40
 * @Version 1.0
 */
public class GeneralException extends Exception {
	//异常错误码
	private String errorCode;
	//省份编码
	private String provinceCode;

	public GeneralException(String errorCode) {
		this.errorCode = errorCode;
	}

	public GeneralException(String errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public GeneralException() {
	}

	public GeneralException(String errorCode, Throwable cause) {
		this(errorCode, errorCode, cause);
	}

	public GeneralException(String errorCode, String message, String provinceCode) {
		super(message);
		this.errorCode = errorCode;
		this.provinceCode = provinceCode;
	}
	public GeneralException(String errorCode,String message,Throwable cause){
		super(message, cause);
		this.errorCode=errorCode;
	}
	public GeneralException(String errorCode,String message,String provinceCode,Throwable cause){
		super(message, cause);
		this.errorCode=errorCode;
		this.provinceCode=provinceCode;
	}
	public String getProvinceCode(){
		if(provinceCode==null||"".equalsIgnoreCase(provinceCode)){
			return ErrorContants.DEFAULT_PROVINCECODE;
		}else{
			return  provinceCode;
		}
	}
	public String getErrorCode(){
		if(errorCode==null||"".equalsIgnoreCase(errorCode)){
			return  ErrorContants.DEFAULT_ERROR_CODE;
		}else return errorCode;
	}

//	@Override
//	public String getMessage() {
//		String message=super.getMessage();
//		if(message==null||"".equalsIgnoreCase(message)){
//			if(ExceptionEnv.isLoaded()){
//				message=ExceptionEnv.getString(getErrorCode(),null);
//			}else{
//				message=ErrorContants.DEFAULT_ERROR_MSG;
//			}
//		}
//		return message;
//	}
}
