package com.pro.unipass.service.retrieveIoprRprtBrkd;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import com.pro.unipass.common.Const;
import com.pro.unipass.service.DataProcess;
import com.pro.unipass.service.retrieveIoprRprtBrkd.param.RETRIEVELOPR_RPRT_BRKD_INPUT;
import com.pro.unipass.service.retrieveIoprRprtBrkd.param.RETRIEVELOPR_RPRT_BRKD_OUTPUT;

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
    
	@Override
	public Object parameterConverter(Map<String, Object> param){
		RETRIEVELOPR_RPRT_BRKD_INPUT input = new RETRIEVELOPR_RPRT_BRKD_INPUT();
		input.setCstmSgn(Strings.isNotEmpty((String)param.get("cstmSgn")) ? (String) param.get("cstmSgn") : "");
		input.setShipCallImoNo(Strings.isNotEmpty((String)param.get("shipCallImoNo")) ? (String) param.get("shipCallImoNo") : "");
		input.setSeaFlghIoprTpcd(Strings.isNotEmpty((String)param.get("seaFlghIoprTpcd")) ? (String) param.get("seaFlghIoprTpcd") : "");
		
		logger.info(String.format("==> parameterConverter : input : %s", input.toString()));
		return input;
	}

	@Override
	public Map<String, Object> resultDataProcess(Object param) {
		Map<String, Object> paramMap = (Map<String, Object>)param;
		RETRIEVELOPR_RPRT_BRKD_INPUT input = (RETRIEVELOPR_RPRT_BRKD_INPUT) paramMap.get("input");
		
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
//		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(fullUrl);
//		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
//		
//		WebClient client = WebClient.builder()
//				.uriBuilderFactory(factory)
//	 			.baseUrl(fullUrl)
//				.build();
//		
//		Mono<RETRIEVELOPR_RPRT_BRKD_OUTPUT> response = client.get().uri(uriBuilder -> uriBuilder
//					.queryParam("crkyCn", licenseKey)
//					.queryParam("seaFlghIoprTpcd", input.getSeaFlghIoprTpcd())
//					.queryParam("shipCallImoNo", input.getShipCallImoNo())
////					.queryParam("cstmSgn", input.get("cstmSgn"))
//					.build())
//				.retrieve()
//				.bodyToMono(RETRIEVELOPR_RPRT_BRKD_OUTPUT.class);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "xml", Charset.forName("UTF-8"));
		headers.setContentType(mediaType);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		fullUrl += "?crkyCn=";
		fullUrl += licenseKey;
		
		if(Strings.isNotEmpty(input.getSeaFlghIoprTpcd())) {
			fullUrl += "&seaFlghIoprTpcd=";
			fullUrl += input.getSeaFlghIoprTpcd();
		}
		if(Strings.isNotEmpty(input.getShipCallImoNo())) {
			fullUrl += "&shipCallImoNo=";
			fullUrl += input.getShipCallImoNo();
		}
		if(Strings.isNotEmpty(input.getCstmSgn())) {
			fullUrl += "&cstmSgn=";
			fullUrl += input.getCstmSgn();
		}

		String logFullUrl = String.format("==> resultDataProcess : fullUrl : %s", fullUrl.toString());
		logger.info(logFullUrl);
		
		RETRIEVELOPR_RPRT_BRKD_OUTPUT response = restTemplate.getForObject(fullUrl, RETRIEVELOPR_RPRT_BRKD_OUTPUT.class);
//		RETRIEVELOPR_RPRT_BRKD_OUTPUT response = restTemplate.postForObject(fullUrl, entity, RETRIEVELOPR_RPRT_BRKD_OUTPUT.class);
		   
//		response.subscribe();
		
		String logResponse = String.format("==> resultDataProcess : response : %s", response.toString());
		logger.info(logResponse);
		
		// 성공 응답 결과 테스트
		resultMap.put(Const.RESULT_STATUS , Const.SUCCESS);
		resultMap.put(Const.RESULT_MESSAGE, "SUCCESS");
		resultMap.put(Const.RESULT_DATA   , new HashMap<String, Object>());
		
		return resultMap;
	}

}