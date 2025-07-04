package com.foresys.app1.system.user.entity;

import com.foresys.core.util.DateUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@Table(name = "USR_USERMNG")
public class UserEntity {

	@Id
	@Column(name = "USER_ID")
	private String userId;
	@Column(name = "USER_NM")
	private String userNm;
	@Column(name = "PWD")
	private String pwd;
	@Column(name = "BRDD")
	private String brdd;
	@Column(name = "USER_STCD")
	private String userStcd;
	@Column(name = "ROLE_CD")
	private String roleCd;
	@Column(name = "BKCD")
	private String bkcd;
	@Column(name = "MTEL_TSNO")
	private String mtelTsno;
	@Column(name = "MTEL_TFNO")
	private String mtelTfno;
	@Column(name = "MTEL_TVNO")
	private String mtelTvno;
	@Column(name = "EML_ADDR")
	private String emlAddr;
	@Column(name = "BR_CD")
	private String brCd;
	@Column(name = "JBTT_CD")
	private String jbttCd;
	@Column(name = "DEPT_CD")
	private String deptCd;
	@Column(name = "TEAM_CD")
	private String teamCd;
	@Column(name = "CHRG_BZWR")
	private String chrgBzwr;
	@Column(name = "CON_IP")
	private String conIp;
	@Column(name = "LAST_LOGIN_DTTM")
	private String lastLoginDttm;
	@Column(name = "LOGIN_POSB_YN")
	private String loginPosbYn;
	@Column(name = "LOGIN_ERR_TRY_NTM")
	private String loginErrTryNtm;
	@Column(name = "PWD_LAST_CHG_DTTM")
	private String pwdLastChgDttm;
	@Column(name = "PWD_RESET_YN")
	private String pwdResetYn;
	@Column(name = "EXCEL_USE_YN")
	private String excelUseYn;
	@Column(name = "INDV_INFO_HNDLR_YN")
	private String indvInfoHndlrYn;
	@Column(name = "SMS_RPLY_NO")
	private String smsRplyNo;
	@Column(name = "REG_ID")
	private String regId;
	@Column(name = "REG_DTTM")
	private String regDttm;
	@Column(name = "CHG_ID")
	private String chgId;
	@Column(name = "CHG_DTTM")
	private String chgDttm;
	
	//insert 전 실행
	@PrePersist
	public void prePersist() {
		this.regDttm = DateUtil.getCurrentYyyyMMddHHmmss();
	}
	
	//update 전 실행
	@PreUpdate
	public void PreUpdate() {
		this.chgDttm = DateUtil.getCurrentYyyyMMddHHmmss();
	}
	
}
