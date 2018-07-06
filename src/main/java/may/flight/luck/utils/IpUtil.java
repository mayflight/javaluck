package may.flight.luck.utils;


import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    public static String clientIp(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (!StringUtils.isEmpty(ip)
                && ip.contains(",")) {
            String[] ipList = ip.split(",");
            ip =  ipList[0];
        }
        return ip;
    }
}
