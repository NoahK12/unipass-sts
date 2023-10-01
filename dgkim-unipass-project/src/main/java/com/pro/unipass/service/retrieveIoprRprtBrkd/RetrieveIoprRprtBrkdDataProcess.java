package com.pro.unipass.service.retrieveIoprRprtBrkd;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pro.unipass.common.Const;
import com.pro.unipass.service.DataProcess;
import com.pro.unipass.service.retrieveIoprRprtBrkd.param.RETRIEVELOPR_RPRT_BRKD_INPUT;

	/**
	 * @filename    : RetrieveIoprRprtBrkdRecvDataProcess
	 * @description : Unipass에서 제공하는 입출항보고내역 정보 조회
	 * @author      : dgkim
	 * @since       : 2023-09-29
	 * @modify      :
	 */

	@Service
	public class RetrieveIoprRprtBrkdDataProcess implements DataProcess{

		private Logger logger = LoggerFactory.getLogger(RetrieveIoprRprtBrkdDataProcess.class);

		@Override
		public Object parameterConverter(Map<String, Object> param){
			Map<String, Object> input = new HashMap<String, Object>();
			
			input.put("shipCallImoNo"  , (String)param.get("shipCallImoNo"));
			input.put("seaFlghIoprTpcd", (String)param.get("seaFlghIoprTpcd"));
			input.put("cstmSgn"        , (String)param.get("cstmSgn"));
//			RETRIEVELOPR_RPRT_BRKD_INPUT inputTest = new RETRIEVELOPR_RPRT_BRKD_INPUT();

//			inputTest.setI_CHDAT_TO((String)param.get("i_chdat_to")); // 변경일자 종료일
			

			logger.info(String.format("==> parameterConverter : input : %s", input.toString()));
			return input;
		}

		@Override
		public Map<String, Object> resultDataProcess(Object param) {
			Map<String, Object> paramMap = (Map<String, Object>)param;
			
			String logParamMap = String.format("==> resultDataProcess : paramMap : %s", paramMap.toString());
			logger.info(logParamMap);
			
			Map<String, Object> resultMap  = new HashMap<String, Object>();
			
			String decodeLicenseKeyString = "";
			byte[] decodeLicenseKey = Base64.getDecoder().decode((String) paramMap.get("license_key"));
			try {
				decodeLicenseKeyString = new String(decodeLicenseKey, "UTF8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			String logLicenseKeyString = String.format("==> resultDataProcess : decodeLicenseKey : %s", decodeLicenseKeyString.toString());
			logger.info(logLicenseKeyString);
			
			// 성공 응답 결과 테스트
			resultMap.put(Const.RESULT_STATUS , Const.SUCCESS);
			resultMap.put(Const.RESULT_MESSAGE, "SUCCESS");
			resultMap.put(Const.RESULT_DATA   , new HashMap<String, Object>());
			
			return resultMap;
		}

	}