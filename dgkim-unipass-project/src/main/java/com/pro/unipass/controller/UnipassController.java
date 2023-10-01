package com.pro.unipass.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.apache.naming.factory.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.pro.unipass.common.Const;
import com.pro.unipass.common.ResponseData;
import com.pro.unipass.service.DataProcess;
import com.pro.unipass.service.retrieveIoprRprtBrkd.RetrieveIoprRprtBrkdDataProcess;

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
	@RequestMapping(value = "/unipass/{serviceNm}/{licenseKey}", method = RequestMethod.POST)
	public Map<String, Object> callService( @PathVariable String serviceNm
			                              , @PathVariable String licenseKey
			                              , @RequestBody Map<String, Object> requestBody) {
		
		// Service Name이 없을 경우 Error Return
		if(Strings.isEmpty(serviceNm)) {
			return new ResponseData( Const.ERROR
                    , "Service Name is empty."
                    , requestBody
                    );
		}
		
		// License key가 없을 경우 Error Return
		if(Strings.isEmpty(licenseKey)) {
			return new ResponseData( Const.ERROR
                    , "License key is empty."
                    , requestBody
                    );
		}
		
		try {
			// Service 구현된 Bean 등록을 위한 Application Context 생성
			GenericApplicationContext context = new GenericApplicationContext(); 
			context.registerBean("retrieveIoprRprtBrkd", RetrieveIoprRprtBrkdDataProcess.class);
//			context.containsBeanDefinition(serviceNm);
			context.refresh();
			
			// 요청 받은 Service Name이 Beand으로 등록되지 않을 경우 Error Return
	        if(!context.containsBean(serviceNm)) {
				return new ResponseData( Const.ERROR
	                    , "Service Name is not registered."
	                    , requestBody
	                    );
	        }
	        
	        // Business Logic이 구현된 Bean 객체 생성
	        DataProcess dataProcess = (DataProcess) context.getBean(serviceNm);
	        
	        // Request Data 세팅
			Map<String,Object> requestDataParam = new HashMap<String,Object>();
			requestDataParam.put("service_nm" , serviceNm);
			requestDataParam.put("license_key", licenseKey );
			
			// 각 Bean에 맞는 Parameter Convert
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
