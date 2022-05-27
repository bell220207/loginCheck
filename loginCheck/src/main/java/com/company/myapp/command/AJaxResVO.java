package com.company.myapp.command;
import java.util.LinkedHashMap;
import java.util.Map;

public class AJaxResVO {
	public static final String SUCCESS_Y = "Y";
	public static final String SUCCESS_N = "N";
	
	private String success = "Y";
	
	/** AJax처리 후 반환할 데이터 */
	private Map<String, Object> resData = new LinkedHashMap<String, Object>();
	
	/** 처리결과가 어떻게 됬는지 판단하는 플래그로 필요에 따라 사용함 (기본값:'0')*/
	private String result = "0";
	
	public Map<String, Object> addAttribute(String name, Object value) {
		
		resData.put(name, value);
		return resData;
	}
	
	public Map<String, Object> addAttribute(Map<String, Object> attr) {
		if (attr != null && attr.isEmpty()) {
			resData.putAll(attr);
		}
		return resData;
	}
	
	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Object> getResData() {
		return resData;
	}
}
