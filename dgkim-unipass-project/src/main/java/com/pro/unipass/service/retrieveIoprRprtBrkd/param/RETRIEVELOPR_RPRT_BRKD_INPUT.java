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
	
	public String getShipCallImoNo() {
		return shipCallImoNo;
	}
	public void setShipCallImoNo(String shipCallImoNo) {
		this.shipCallImoNo = shipCallImoNo;
	}
	public String getSeaFlghIoprTpcd() {
		return seaFlghIoprTpcd;
	}
	public void setSeaFlghIoprTpcd(String seaFlghIoprTpcd) {
		this.seaFlghIoprTpcd = seaFlghIoprTpcd;
	}
	public String getCstmSgn() {
		return cstmSgn;
	}
	public void setCstmSgn(String cstmSgn) {
		this.cstmSgn = cstmSgn;
	}
}
