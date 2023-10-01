package com.pro.unipass.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pro.unipass.common.Const;
import com.pro.unipass.common.ResponseData;
import com.pro.unipass.service.DataProcess;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @filename    : UnipassController
 * @description : Unipass Restfull API call Controller
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

@RestController
public class UnipassController {

	private Logger logger = LoggerFactory.getLogger(UnipassController.class);
    
    /**
     * 2023. 09. 29. : dgkim
     * Unipass Restfull API를 호출하기위한 callSerivce
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/unipass/{serviceNm}", method = RequestMethod.POST)
	public Map<String, Object> callService( @PathVariable String serviceNm
			                              , @RequestBody Map<String, Object> requestBody
			                              , HttpServletRequest request) {
		
		
		String logServiceNm = String.format("==> callService : serviceNm : %s", serviceNm.toString());
		logger.info(logServiceNm);
		
		String logRequestBody = String.format("==> callService : requestBody : %s", requestBody.toString());
		logger.info(logRequestBody);
		
		// Service Name이 없을 경우 Error Return
		if(Strings.isEmpty(serviceNm)) {
			return new ResponseData( Const.ERROR
                    , "Service Name is empty."
                    , requestBody
                    );
		}
		
		try {
			// Bean을 호출할 Web Application Context 
			WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
			
			// 요청 받은 Service가 Bean으로 등록되지 않을 경우 Error Return
	        if(!context.containsBean(serviceNm)) {
				return new ResponseData( Const.ERROR
	                    , "Service Name is not registered."
	                    , requestBody
	                    );
	        }
	        
	        // 요청 받은 Service의 Business Logic이 구현된 Bean
	        DataProcess dataProcess = (DataProcess) context.getBean(serviceNm);
	        
	        // Request Data 세팅
			Map<String,Object> requestDataParam = new HashMap<String,Object>();
			requestDataParam.put("service_nm" , serviceNm);
			
			// 각 Service에 맞는 Parameter Convert
			requestDataParam.put("input", dataProcess.parameterConverter(requestBody) );
			
			String logRequestDataParam = String.format("==> callService : requestDataParam : %s", requestDataParam.toString());
			logger.info(logRequestDataParam);
			
			// Unipass API 호출 Method 실행
			Map<String, Object> executeIfResultMap = dataProcess.resultDataProcess(requestDataParam);
	        
			// Unipass API 호출 결과 return
			if(Const.SUCCESS.equals(executeIfResultMap.get(Const.RESULT_STATUS))) {
				return new ResponseData( (String) executeIfResultMap.get(Const.RESULT_STATUS)
	                    , (String) executeIfResultMap.get(Const.RESULT_MESSAGE)
	                    , requestBody
	                    , executeIfResultMap.get(Const.RESULT_DATA)
	                    );
			} else {
				return new ResponseData( (String) executeIfResultMap.get(Const.RESULT_STATUS)
	                    , (String) executeIfResultMap.get(Const.RESULT_MESSAGE)
	                    , requestBody
	                    );
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			
			return new ResponseData( Const.ERROR
                    , exception.getMessage()
                    , requestBody
                    );
		}
		
	}
}
