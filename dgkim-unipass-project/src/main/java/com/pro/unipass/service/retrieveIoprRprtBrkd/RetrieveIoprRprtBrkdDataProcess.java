package com.pro.unipass.service.retrieveIoprRprtBrkd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import com.pro.unipass.common.Const;
import com.pro.unipass.service.DataProcess;

import reactor.core.publisher.Mono;

/**
 * @filename    : RetrieveIoprRprtBrkdRecvDataProcess
 * @description : Unipass에서 제공하는 입출항보고내역 정보 조회
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

@Service("retrieveIoprRprtBrkd")
public class RetrieveIoprRprtBrkdDataProcess implements DataProcess{

	private Logger logger = LoggerFactory.getLogger(RetrieveIoprRprtBrkdDataProcess.class);
    
    @Value("${unipass_url}")
    private String unipassUrl;
    
    @Value("${retrieve_iopr_rprt_brkd.service_url}")
    private String serviceUrl;
    
    @Value("${retrieve_iopr_rprt_brkd.license_key}")
    private String licenseKey;
    
    private List<String> queryParamList = new ArrayList<String>(Arrays.asList(new String[]{ "shipCallImoNo", "seaFlghIoprTpcd", "cstmSgn"}));
    
	@Override
	public Object parameterConverter(Map<String, Object> param){
		Map<String, Object> input = new HashMap<String, Object>();
		
		for(String data : queryParamList) {
			input.put(data, (String)param.get(data));
		}
		
		logger.info(String.format("==> parameterConverter : input : %s", input.toString()));
		return input;
	}

	@Override
	public Map<String, Object> resultDataProcess(Object param) {
		Map<String, Object> paramMap = (Map<String, Object>)param;
		Map<String, Object> input = (Map<String, Object>) paramMap.get("input");
		
		String logParamMap = String.format("==> resultDataProcess : paramMap : %s", paramMap.toString());
		logger.info(logParamMap);
		
		String logInput = String.format("==> resultDataProcess : input : %s", input.toString());
		logger.info(logInput);
		
		String logUnipassUrl = String.format("==> resultDataProcess : unipassUrl : %s", unipassUrl.toString());
		logger.info(logUnipassUrl);
		
		String logServiceUrl = String.format("==> resultDataProcess : serviceUrl : %s", serviceUrl.toString());
		logger.info(logServiceUrl);
		
		String logLicenseKey = String.format("==> resultDataProcess : licenseKey : %s", licenseKey.toString());
		logger.info(logLicenseKey);
		
		
		Map<String, Object> resultMap  = new HashMap<String, Object>();
		
		String fullUrl = unipassUrl + serviceUrl;
		
		// URI 인코딩 설정
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(fullUrl);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
		
		WebClient client = WebClient.builder()
				.uriBuilderFactory(factory)
	 			.baseUrl(fullUrl)
				.build();
		
		Mono<String> response = client.get().uri(uriBuilder -> uriBuilder
					.queryParam("crkyCn", licenseKey)
					.queryParam("seaFlghIoprTpcd", input.get("seaFlghIoprTpcd"))
					.queryParam("shipCallImoNo", input.get("shipCallImoNo"))
//					.queryParam("cstmSgn", input.get("cstmSgn"))
					.build())
				.retrieve()
				.bodyToMono(String.class);
		response.subscribe();
		String logResponse = String.format("==> resultDataProcess : response : %s", response.toString());
		logger.info(logResponse);
		
		// 성공 응답 결과 테스트
		resultMap.put(Const.RESULT_STATUS , Const.SUCCESS);
		resultMap.put(Const.RESULT_MESSAGE, "SUCCESS");
		resultMap.put(Const.RESULT_DATA   , new HashMap<String, Object>());
		
		return resultMap;
	}

}