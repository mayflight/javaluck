package may.flight.luck.utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XSSFilterHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final String ESCAPE_ENCODE = "UTF-8";
    public XSSFilterHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (null == value) {
            return null;
        }
        value = HtmlUtils.htmlEscape(value, ESCAPE_ENCODE);
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] array = super.getParameterValues(name);
        if (null == array) {
            return null;
        }
        for (int i = 0; i < array.length ; i++) {
            String value = array[i];
            if (StringUtils.isBlank(value)) {
                continue;
            }
            value = HtmlUtils.htmlEscape(value, ESCAPE_ENCODE);
            array[i] = value;
        }
        return array;
    }

}
