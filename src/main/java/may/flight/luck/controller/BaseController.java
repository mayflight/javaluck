package may.flight.luck.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    protected HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }
    @Resource
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void print(HttpServletResponse response, String text) {
        if (null == response) {
            return;
        }
        try {
            response.getWriter().print(text);
        } catch (Exception e) {

        }
    }
}
