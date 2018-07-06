package may.flight.luck.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class QueryStringUtils {
    public static String getQueryString(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        Map<String, String[]> params = request.getParameterMap();
        if (null == params) {
            return null;
        }
        String queryString = "";
        for (String key:params.keySet()) {
            if (StringUtils.isEmpty(key)) {
                continue;
            }
            String value = request.getParameter(key);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            queryString = StringUtils.isEmpty(queryString) ?  queryString + key+"="+value : queryString + "&"+key+"="+value;
        }
        return queryString;
    }
}
