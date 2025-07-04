package com.foresys.core.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
public class ConnectUtil {

	/**
	 * 클라이언트 ip 가져오기
	 */
	public static String getClientIP() throws UnknownHostException {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		
		String ip = request.getHeader("X-Forwarded-For");
		log.info("> X-Forwarded-For : "+ip);
		
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			log.info("> Proxy-Client-IP : "+ip);
		}
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			log.info("> WL-Proxy-Client-IP : "+ip);
		}
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			log.info("> HTTP_CLIENT_IP : "+ip);
		}
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			log.info("> HTTP_X_FORWARDED_FOR : "+ip);
		}
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
			log.info("> X-Real-IP : "+ip);
		}
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-RealIP");
			log.info("> X-RealIP : "+ip);
		}
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("REMOTE_ADDR");
			log.info("> REMOTE_ADDR : "+ip);
		}
		if(ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			log.info("> getRemoteAddr : "+ip);
		}
		
		if(ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
			ip = InetAddress.getLocalHost().getHostAddress();
			log.info("> getHostAddress : "+ip);
		}
		
		log.info("> Result : IP Address : "+ip);

	    return ip;
	}
	
}
