package may.flight.luck.controller;

import javax.servlet.http.HttpServletResponse;

public class BaseController {
    public void print(HttpServletResponse response, String text) {
        if (null == response) {
            return;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try {
            response.getWriter().print(text);
        } catch (Exception e) {

        }
    }
}
