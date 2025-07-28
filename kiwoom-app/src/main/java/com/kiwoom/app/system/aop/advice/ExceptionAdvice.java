package com.kiwoom.app.system.aop.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kiwoom.app.system.dto.ComRes;
import com.kiwoom.app.system.exception.BusinessException;
import com.kiwoom.app.system.exception.BusinessExceptionCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.net.ssl.SSLHandshakeException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@SuppressWarnings("rawtypes")
public class ExceptionAdvice {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ComRes> validationError(HttpMessageNotReadableException ex) {
		return ResponseEntity.badRequest()
				.body(
						ComRes.builder()
								.rsltCd(9)
								.rsltMsg(ex.getMessage())
								.build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ComRes> validationError(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
	    String errors = fieldErrors.stream()
						           .map(error -> error.getField() + ": " + error.getDefaultMessage())
						           .collect(Collectors.joining(", "));

		return ResponseEntity.badRequest()
		        .body(
						ComRes.builder()
			        		  .rsltCd(9)
		        			  .rsltMsg(errors)
		        			  .build());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ComRes> validationError(ConstraintViolationException ex) {
		return ResponseEntity.badRequest()
		        .body(
						ComRes.builder()
			        		  .rsltCd(9)
		        			  .rsltMsg(ex.getMessage())
		        			  .build());
	}

	@ExceptionHandler(SQLException.class)
	protected ResponseEntity<ComRes> validationError(SQLException ex) {
		return ResponseEntity.status(500)
		        .body(
						ComRes.builder()
			        		  .rsltCd(9)
		        			  .rsltMsg("DB전송 쿼리에 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.")
		        			  .build());
	}

	@ExceptionHandler(NullPointerException.class)
	protected ResponseEntity<ComRes> validationError(NullPointerException ex) {
		return ResponseEntity.status(500)
		        .body(
						ComRes.builder()
			        		  .rsltCd(9)
		        			  .rsltMsg("Nullpointer Ex 발생. 관리자에게 문의하시기 바랍니다.")
		        			  .build());
	}

	@ExceptionHandler(JsonProcessingException.class)
	protected ResponseEntity<ComRes> validationError(JsonProcessingException ex) {
		return ResponseEntity.status(500)
		        .body(
						ComRes.builder()
				        	  .rsltCd(9)
			        		  .rsltMsg("XML문자열로 변환 도중 에러가 발생 하였습니다. 관리자에게 문의하시기 바랍니다.")
			        		  .build());
	}

	@ExceptionHandler(JsonMappingException.class)
	protected ResponseEntity<ComRes> validationError(JsonMappingException ex) {
		return ResponseEntity.status(500)
		        .body(
						ComRes.builder()
			        		  .rsltCd(9)
		        			  .rsltMsg("XML문자열을 Vo로 변환 도중 에러가 발생 하였습니다. 관리자에게 문의하시기 바랍니다.")
		        			  .build());
	}

	@ExceptionHandler(SSLHandshakeException.class)
	protected ResponseEntity<ComRes> validationError(SSLHandshakeException ex) {
		return ResponseEntity.status(500)
		        .body(
						ComRes.builder()
			        	  	  .rsltCd(9)
		        			  .rsltMsg("Https 전송간에 HandShake 오류가 발생하였습니다.")
		        		  	  .build());
	}
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<ComRes> validationError(RuntimeException ex) {
		return ResponseEntity.status(500)
				.body(
						ComRes.builder()
						.rsltCd(9)
						.rsltMsg("잠시 후 다시 시도해주세요." + "["+ex.getMessage()+"]")
						.build());
	}

	@ExceptionHandler(HttpClientErrorException.class)
	protected ResponseEntity<ComRes> validationError(HttpClientErrorException ex) {
		return ResponseEntity.status(ex.getStatusCode())
		        .body(
						ComRes.builder()
			        		  .rsltCd(9)
		        			  .rsltMsg("타 기관 연동 중 오류가 발생하였습니다." + "["+ex.getMessage()+"]")
		        			  .build());
	}

	// TODO 오류 케이스 추가

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ComRes> businessException(BusinessException ex) {
		BusinessExceptionCode code = ex.getBusinessExceptionCode();
		return ResponseEntity.ok()
				.body(
						ComRes.builder()
								.rsltCd(code.getCode())
								.rsltMsg(code.getMessage())
								.build());
	}
}
