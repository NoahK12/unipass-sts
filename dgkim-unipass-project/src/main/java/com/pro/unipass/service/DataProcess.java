package com.pro.unipass.service;

import java.util.Map;

/**
 * @filename    : DataProcess
 * @description : Unipass Restful API 호출 Interface
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

public interface DataProcess {
	public Object parameterConverter(Map<String, Object> parma);
	
	public Map<String, Object> resultDataProcess(Object result);
}
