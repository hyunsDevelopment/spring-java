package com.kiwoom.app.session.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USR_USERMNG")
public class UsrMng {

    @Id
    @Size(max = 20)
    @Column(name = "USER_ID", nullable = false, length = 20)
    private String userId;

    @Size(max = 200)
    @Column(name = "USER_NM", length = 200)
    private String userNm;

    @Size(max = 20)
    @Column(name = "PWD", length = 20)
    private String pwd;

    @Size(max = 8)
    @Column(name = "BRDD", length = 8)
    private String brdd;

    @Size(max = 20)
    @Column(name = "USER_STCD", length = 20)
    private String userStcd;

    @Size(max = 20)
    @Column(name = "BKCD", length = 20)
    private String bkcd;

    @Size(max = 20)
    @Column(name = "MTEL_TSNO", length = 20)
    private String mtelTsno;

    @Size(max = 20)
    @Column(name = "MTEL_TFNO", length = 20)
    private String mtelTfno;

    @Size(max = 20)
    @Column(name = "MTEL_TVNO", length = 20)
    private String mtelTvno;

    @Size(max = 20)
    @Column(name = "EML_ADDR", length = 20)
    private String emlAddr;

    @Size(max = 20)
    @Column(name = "BRCD", length = 20)
    private String brcd;

    @Size(max = 20)
    @Column(name = "JBTT_CD", length = 20)
    private String jbttCd;

    @Size(max = 20)
    @Column(name = "DEPT_CD", length = 20)
    private String deptCd;

    @Size(max = 20)
    @Column(name = "TEAM_CD", length = 20)
    private String teamCd;

    @Size(max = 100)
    @Column(name = "CHRG_BZWR", length = 100)
    private String chrgBzwr;

    @Size(max = 20)
    @Column(name = "CON_IP", length = 20)
    private String conIp;

    @Size(max = 14)
    @Column(name = "LAST_LOGIN_DTTM", length = 14)
    private String lastLoginDttm;

    @Column(name = "LOGIN_POSB_YN")
    private Boolean loginPosbYn;

    @Column(name = "LOGIN_ERR_TRY_NTM")
    private Integer loginErrTryNtm;

    @Size(max = 14)
    @Column(name = "PWD_LAST_CHG_DTTM", length = 14)
    private String pwdLastChgDttm;

    @Column(name = "PWD_RESET_YN")
    private Boolean pwdResetYn;

    @Column(name = "EXCEL_USE_YN")
    private Boolean excelUseYn;

    @Size(max = 20)
    @Column(name = "INDV_INFO_HNDLR_CD", length = 20)
    private String indvInfoHndlrCd;

    @Size(max = 20)
    @Column(name = "SMS_RPLY_NO", length = 20)
    private String smsRplyNo;

    @Size(max = 20)
    @Column(name = "REG_ID", length = 20)
    private String regId;

    @Size(max = 14)
    @Column(name = "REG_DTTM", length = 14)
    private String regDttm;

    @Size(max = 20)
    @NotNull
    @Column(name = "CHG_ID", nullable = false, length = 20)
    private String chgId;

    @Size(max = 14)
    @NotNull
    @Column(name = "CHG_DTTM", nullable = false, length = 14)
    private String chgDttm;

}