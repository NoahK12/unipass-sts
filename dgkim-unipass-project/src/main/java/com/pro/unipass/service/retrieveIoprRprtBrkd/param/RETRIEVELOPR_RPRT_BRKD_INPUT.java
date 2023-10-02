package com.pro.unipass.service.retrieveIoprRprtBrkd.param;

import lombok.Data;

/**
 * @filename    : RETRIEVELOPR_RPRT_BRKD_INPUT
 * @description : RetrieveIoprRprtBrkdDataProcess input VO
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

@Data
public class RETRIEVELOPR_RPRT_BRKD_INPUT {
	private String shipCallImoNo;
	private String seaFlghIoprTpcd;
	private String cstmSgn;
}
