package may.flight.luck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("main/")
public class MainController {
    @RequestMapping("start.htm")
    public void start(HttpServletRequest request, HttpServletResponse response, String text) {
        response.setCharacterEncoding("utf-8");
        if (StringUtils.isEmpty(text)) {
            text = "spring mvc 开始";
        }
        try {
            response.getWriter().print(text);
        } catch (Exception e) {

        }
    }

}
