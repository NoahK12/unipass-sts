package com.pro.unipass.common;

import java.util.HashMap;

/**
 * @filename    : ResponseData
 * @description : callService 응답 데이터
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

public class ResponseData extends HashMap<String, Object>{

	public ResponseData(String resultStatus, String resultMessage, Object resultInput, Object resultData) {
		this.setResultStatus(resultStatus);
		this.setResultMessage(resultMessage);
		this.setResultInput(resultInput);
		this.setResultData(resultData);
	}
	
	public ResponseData(String resultStatus, String resultMessage, Object resultInput) {
		this.setResultStatus(resultStatus);
		this.setResultMessage(resultMessage);
		this.setResultInput(resultInput);
	}

	public void setResultData(Object resultData) {
		this.put(Const.RESULT_DATA, resultData);
	}
	
	public Object getResultData() {
		return this.get(Const.RESULT_DATA);
	}
	
	public void setResultInput(Object resultInput) {
		this.put(Const.RESULT_INPUT, resultInput);
	}
	
	public Object getResultInput() {
		return this.get(Const.RESULT_INPUT);
	}
	
	public void setResultMessage(String resultMessage) {
		
		if(resultMessage != null) {
			this.put(Const.RESULT_MESSAGE, resultMessage);
		}
	}
	
	public String getResultMessage() {
		return (String) this.get(Const.RESULT_MESSAGE);
	}
	
	public void setResultStatus(String resultStatus) {
		this.put(Const.RESULT_STATUS, resultStatus);
	}
	
	public String getResultStatus() {
		return (String) this.get(Const.RESULT_STATUS);
	}
	
}
