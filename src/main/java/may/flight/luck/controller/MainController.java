package may.flight.luck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("main/")
public class MainController extends BaseController {
    @RequestMapping("start.htm")
    public void start(HttpServletRequest request, HttpServletResponse response, String text) {
        text = StringUtils.isEmpty(text) ? "据说看到这个页面的人会有好运哦!" : text;
        print(response, text);
    }

}
