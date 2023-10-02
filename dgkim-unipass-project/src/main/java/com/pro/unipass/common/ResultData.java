package com.pro.unipass.common;

import java.util.HashMap;

/**
 * @filename    : ResultData
 * @description : API 결과 데이터
 * @author      : dgkim
 * @since       : 2023-10-02
 * @modify      :
 */

public class ResultData extends HashMap<String, Object>{

	public ResultData(int totalCnt, String resultInfo, Object resultList) {
		this.setTotalCnt(totalCnt);
		this.setResultList(resultList);
		this.setResultInfo(resultInfo);
	}
	
	public ResultData(int totalCnt, String resultInfo) {
		this.setTotalCnt(totalCnt);
		this.setResultInfo(resultInfo);
	}

	public void setResultList(Object resultList) {
		this.put(Const.RESULT_LIST, resultList);
	}
	
	public void setResultInfo(String resultInfo) {
		this.put(Const.RESULT_INFO, resultInfo);
	}
	
	public String getResultInfo() {
		return (String) this.get(Const.RESULT_INFO);
	}
	
	public void setTotalCnt(int totalCnt) {
		this.put(Const.TOTAL_CNT, totalCnt);
	}
	
	public int getTotalCnt() {
		return (int) this.get(Const.TOTAL_CNT);
	}
	
}
