package may.flight.luck.controller;

import com.alibaba.fastjson.JSONObject;
import may.flight.luck.service.AllMessageService;
import may.flight.luck.utils.IpUtil;
import may.flight.luck.utils.QueryStringUtils;
import may.flight.luck.utils.StreamUtil;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
   @Resource
   private MemcachedClient memcachedClient;

   private volatile boolean isOpen;

    @RequestMapping("start.htm")
    public void start(HttpServletRequest request, HttpServletResponse response, String text) {
        Object[] logParams =  {IpUtil.clientIp(request), QueryStringUtils.getQueryString(request), StreamUtil.readRequestInputStream(request)};
        logger.error("client_ip:{}, request_params:{}, request_body:{}", logParams);
        memcachedClient.set("request_params", 600, logParams);
        text = StringUtils.isEmpty(text) ? "据说看到这个页面的人会有好运哦!" : text;
        print(response, text);
    }

    @RequestMapping("print_params.htm")
    public void printLog(HttpServletRequest request, HttpServletResponse response) {
        Object params =  memcachedClient.get("request_params");
        print(response, JSONObject.toJSONString(params));
    }

   @RequestMapping("rule.htm")
    public void rule(HttpServletRequest request, HttpServletResponse response) {
       Map<String,String> map = allMessageService.getNameAgeMap(1);
       print(response, map.toString());
   }


   @RequestMapping("view.htm")
   public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("log", JSONObject.toJSONString(memcachedClient.get("request_params")));
        return "log";
   }

   @RequestMapping("run_loop.htm")
   public void runLoop(HttpServletResponse response,Model model) throws Exception{
        while (isOpen) {
            Thread.sleep(50000);
            System.out.println(Thread.currentThread().getName());
        }
   }

   @RequestMapping("modify.htm")
   public void modify(HttpServletResponse response) {
        isOpen = !isOpen;
        print(response, String.valueOf(isOpen));
   }

}
