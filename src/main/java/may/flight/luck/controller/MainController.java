package may.flight.luck.controller;


import may.flight.luck.service.AllMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@Controller
@RequestMapping("main/")
public class MainController extends BaseController {

   private static Logger logger = LoggerFactory.getLogger(MainController.class);
   @Resource
   private AllMessageService allMessageService;

    @RequestMapping("start.htm")
    public void start(HttpServletRequest request, HttpServletResponse response, String text) {
        logger.error("开始{}", request.getRemoteHost());
        text = StringUtils.isEmpty(text) ? "据说看到这个页面的人会有好运哦!" : text;
        print(response, text);
    }

   @RequestMapping("rule.htm")
    public void rule(HttpServletRequest request, HttpServletResponse response) {
       Map<String,String> map = allMessageService.getNameAgeMap();
       print(response, map.toString());
   }


}
