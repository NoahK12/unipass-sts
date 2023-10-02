package com.pro.unipass.service.retrieveIoprRprtBrkd.param;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @filename    : EtprRprtQryBrkdQryVo
 * @description : EtprRprtQryBrkdQryVo VO
 * @author      : dgkim
 * @since       : 2023-09-29
 * @modify      :
 */

@Data
public class EtprRprtQryBrkdQryVo {
	private String cstmSgn;
	private String shipLamrPlcCd;
	private String alPsngPecnt;
	private String dptrPortAirptCd;
	private String dptrPortAirptNm;
	private String alCrmbPecnt;
	private String shipAirCntyCd;
	private String etprDttm;
	private String shipFlgtNm;
	private String shipLamrPlcNm;
	private String cstmNm;
	private String shipAirCntyNm;
	private String ioprSbmtNo;
	
	public String getCstmSgn() {
		return cstmSgn;
	}
	public void setCstmSgn(String cstmSgn) {
		this.cstmSgn = cstmSgn;
	}
	public String getShipLamrPlcCd() {
		return shipLamrPlcCd;
	}
	public void setShipLamrPlcCd(String shipLamrPlcCd) {
		this.shipLamrPlcCd = shipLamrPlcCd;
	}
	public String getAlPsngPecnt() {
		return alPsngPecnt;
	}
	public void setAlPsngPecnt(String alPsngPecnt) {
		this.alPsngPecnt = alPsngPecnt;
	}
	public String getDptrPortAirptCd() {
		return dptrPortAirptCd;
	}
	public void setDptrPortAirptCd(String dptrPortAirptCd) {
		this.dptrPortAirptCd = dptrPortAirptCd;
	}
	public String getDptrPortAirptNm() {
		return dptrPortAirptNm;
	}
	public void setDptrPortAirptNm(String dptrPortAirptNm) {
		this.dptrPortAirptNm = dptrPortAirptNm;
	}
	public String getAlCrmbPecnt() {
		return alCrmbPecnt;
	}
	public void setAlCrmbPecnt(String alCrmbPecnt) {
		this.alCrmbPecnt = alCrmbPecnt;
	}
	public String getShipAirCntyCd() {
		return shipAirCntyCd;
	}
	public void setShipAirCntyCd(String shipAirCntyCd) {
		this.shipAirCntyCd = shipAirCntyCd;
	}
	public String getEtprDttm() {
		return etprDttm;
	}
	public void setEtprDttm(String etprDttm) {
		this.etprDttm = etprDttm;
	}
	public String getShipFlgtNm() {
		return shipFlgtNm;
	}
	public void setShipFlgtNm(String shipFlgtNm) {
		this.shipFlgtNm = shipFlgtNm;
	}
	public String getShipLamrPlcNm() {
		return shipLamrPlcNm;
	}
	public void setShipLamrPlcNm(String shipLamrPlcNm) {
		this.shipLamrPlcNm = shipLamrPlcNm;
	}
	public String getCstmNm() {
		return cstmNm;
	}
	public void setCstmNm(String cstmNm) {
		this.cstmNm = cstmNm;
	}
	public String getShipAirCntyNm() {
		return shipAirCntyNm;
	}
	public void setShipAirCntyNm(String shipAirCntyNm) {
		this.shipAirCntyNm = shipAirCntyNm;
	}
	public String getIoprSbmtNo() {
		return ioprSbmtNo;
	}
	public void setIoprSbmtNo(String ioprSbmtNo) {
		this.ioprSbmtNo = ioprSbmtNo;
	}
}
