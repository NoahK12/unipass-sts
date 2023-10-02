package com.pro.unipass.service.retrieveIoprRprtBrkd.param;

import java.util.List;

import lombok.Data;

/**
 * @filename    : IoprRprtBrkdQryRtnVo
 * @description : IoprRprtBrkdQryRtnVo VO
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

@Data
public class IoprRprtBrkdQryRtnVo {
	private String ntceInfo;
	private Number tCnt;
	private List<EtprRprtQryBrkdQryVo> etprRprtQryBrkdQryVo;
	
	public String getNtceInfo() {
		return ntceInfo;
	}
	public void setNtceInfo(String ntceInfo) {
		this.ntceInfo = ntceInfo;
	}
	public Number gettCnt() {
		return tCnt;
	}
	public void settCnt(Number tCnt) {
		this.tCnt = tCnt;
	}
	public List<EtprRprtQryBrkdQryVo> getEtprRprtQryBrkdQryVo() {
		return etprRprtQryBrkdQryVo;
	}
	public void setEtprRprtQryBrkdQryVo(List<EtprRprtQryBrkdQryVo> etprRprtQryBrkdQryVo) {
		this.etprRprtQryBrkdQryVo = etprRprtQryBrkdQryVo;
	}
}
