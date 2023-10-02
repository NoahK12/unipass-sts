package com.pro.unipass.service.retrieveIoprRprtBrkd;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.pro.unipass.common.Const;
import com.pro.unipass.common.ResultData;
import com.pro.unipass.service.DataProcess;
import com.pro.unipass.service.retrieveIoprRprtBrkd.param.RETRIEVELOPR_RPRT_BRKD_INPUT;

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
		// input 데이터 가공
		RETRIEVELOPR_RPRT_BRKD_INPUT input = new RETRIEVELOPR_RPRT_BRKD_INPUT();
		input.setCstmSgn(Strings.isNotEmpty((String)param.get("cstmSgn")) ? (String) param.get("cstmSgn") : "");
		input.setShipCallImoNo(Strings.isNotEmpty((String)param.get("shipCallImoNo")) ? (String) param.get("shipCallImoNo") : "");
		input.setSeaFlghIoprTpcd(Strings.isNotEmpty((String)param.get("seaFlghIoprTpcd")) ? (String) param.get("seaFlghIoprTpcd") : "");
		
		logger.info(String.format("==> parameterConverter : input : %s", input.toString()));
		return input;
	}

	@Override
	public Map<String, Object> resultDataProcess(Object param) {
		Map<String, Object> resultMap  = new HashMap<String, Object>();
		Map<String, Object> paramMap = (Map<String, Object>)param;
		RETRIEVELOPR_RPRT_BRKD_INPUT input = (RETRIEVELOPR_RPRT_BRKD_INPUT) paramMap.get("input");
		
		String logParamMap = String.format("==> resultDataProcess : paramMap : %s", paramMap.toString());
		logger.info(logParamMap);
		
		String logInput = String.format("==> resultDataProcess : input : %s", input.toString());
		logger.info(logInput);
		
		// unipass URL 설정
		String url = unipassUrl + serviceUrl;
		
		// unipass URI 설정
		StringBuffer fullUriBuffer = new StringBuffer();
		fullUriBuffer.append(url)
			.append("?crkyCn=")
			.append(licenseKey);
		
		if(Strings.isNotEmpty(input.getSeaFlghIoprTpcd())) {
			fullUriBuffer.append("&seaFlghIoprTpcd=")
				.append(input.getSeaFlghIoprTpcd());
		}
		if(Strings.isNotEmpty(input.getShipCallImoNo())) {
			fullUriBuffer.append("&shipCallImoNo=")
				.append(input.getShipCallImoNo());
		}
		if(Strings.isNotEmpty(input.getCstmSgn())) {
			fullUriBuffer.append("&cstmSgn=")
				.append(input.getCstmSgn());
		}
		String fullUri = fullUriBuffer.toString();
		
		String logfullUri = String.format("==> resultDataProcess : fullUri : %s", fullUri.toString());
		logger.info(logfullUri);
		
		// URI 인코딩 설정
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(url);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
		
		// WebClinet builder 설정
		WebClient client = WebClient.builder()
				.uriBuilderFactory(factory)
	 			.baseUrl(url)
				.build();
		
		Mono<String> response = client.get()
				.uri(fullUri)
				.retrieve()
				.bodyToMono(String.class);
		
		response.doOnSuccess(result -> {
			// 응답받은 xml 데이터를 json object로 파싱
			JSONObject jsonObject = XML.toJSONObject(result.toString());
			
			// 응답 결과가 없을 시 사전에 정해진 응답데이터 형태가 아니기 때문에 Error 상태로 Result Data 세팅
			if(jsonObject.isNull("ioprRprtBrkdQryRtnVo")) {
				resultMap.put(Const.RESULT_STATUS , Const.ERROR);
				resultMap.put(Const.RESULT_MESSAGE, "응답 결과가 없습니다.");
			} else {
				// 전체 응답 데이터 추출
				Map<String, Object> ioprRprtBrkdQryRtnVo = jsonObject.getJSONObject("ioprRprtBrkdQryRtnVo").toMap();
				
				// 입출항 데이터 갯수
				int tCnt = (int) ioprRprtBrkdQryRtnVo.get("tCnt");
				
				// 조회된 입출항 데이터가 없을 경우 
				if(tCnt < 1) {
					// 응답결과 Convert
					ResultData resultData = new ResultData(tCnt, (String) ioprRprtBrkdQryRtnVo.get("ntceInfo"));
					
					resultMap.put(Const.RESULT_STATUS , Const.SUCCESS);
					resultMap.put(Const.RESULT_MESSAGE, ioprRprtBrkdQryRtnVo.get("ntceInfo"));
					resultMap.put(Const.RESULT_DATA   , resultData);
				} else {
					// 전체 입출항 데이터
					List<Map<String, Object>> etprRprtQryBrkdQryVo = (List<Map<String, Object>>) ioprRprtBrkdQryRtnVo.get("etprRprtQryBrkdQryVo");
					
					// 입항일(etprDttm) 기준으로 정렬
					if(etprRprtQryBrkdQryVo.get(0).containsKey("etprDttm")) {
						etprRprtQryBrkdQryVo.sort(Comparator.comparing((Map<String, Object> map) -> (Long)map.get("etprDttm")).reversed());
						
					// 출항일(tkofDttm) 기준으로 정렬
					} else {
						etprRprtQryBrkdQryVo.sort(Comparator.comparing((Map<String, Object> map) -> (Long)map.get("tkofDttm")).reversed());
					}
					
					// 가장 최근에 입출항한 목록
					List<Map<String, Object>> latestList = new ArrayList<Map<String, Object>>();
					latestList.addAll(etprRprtQryBrkdQryVo);
					
					// 입출항 목록이 10건 이상일 경우 10건만 추출
					if(tCnt > 10) {
						latestList = etprRprtQryBrkdQryVo.subList(0, 9);
						tCnt = 10;
					}
					
					// 응답결과 Convert
					ResultData resultData = new ResultData(tCnt, (String) ioprRprtBrkdQryRtnVo.get("ntceInfo"), latestList);
					
					resultMap.put(Const.RESULT_STATUS , Const.SUCCESS);
					resultMap.put(Const.RESULT_MESSAGE, "SUCCESS");
					resultMap.put(Const.RESULT_DATA   , resultData);
				}
			}
		})
		// 에러 발생시 exception 처리
		.doOnError(error -> {
			String logError = String.format("==> resultDataProcess : error : %s", error.toString());
			logger.info(logError);
			throw new RuntimeException(error);
		})
		.log()
		.block(); // API의 응답결과를 실시간으로 출력해야하기 때문에 blocking 방식으로 호출
		
		String logResponse = String.format("==> resultDataProcess : response : %s", response.toString());
		logger.info(logResponse);
		
		return resultMap;
	}

}