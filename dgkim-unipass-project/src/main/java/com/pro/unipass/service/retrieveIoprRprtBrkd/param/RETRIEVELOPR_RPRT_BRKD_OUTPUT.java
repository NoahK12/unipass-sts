package com.pro.unipass.service.retrieveIoprRprtBrkd.param;

import lombok.Data;

/**
 * @filename    : RETRIEVELOPR_RPRT_BRKD_OUTPUT
 * @description : RetrieveIoprRprtBrkdDataProcess input VO
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

@Data
public class RETRIEVELOPR_RPRT_BRKD_OUTPUT {
	private IoprRprtBrkdQryRtnVo ioprRprtBrkdQryRtnVo;

	public IoprRprtBrkdQryRtnVo getIoprRprtBrkdQryRtnVo() {
		return ioprRprtBrkdQryRtnVo;
	}

	public void setIoprRprtBrkdQryRtnVo(IoprRprtBrkdQryRtnVo ioprRprtBrkdQryRtnVo) {
		this.ioprRprtBrkdQryRtnVo = ioprRprtBrkdQryRtnVo;
	}
}
