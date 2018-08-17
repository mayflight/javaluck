package may.flight.luck.controller;

import com.alibaba.fastjson.JSONObject;
import may.flight.luck.component.RedisUtils;
import may.flight.luck.entity.Trade;
import may.flight.luck.service.AllMessageService;
import may.flight.luck.service.TradeService;
import may.flight.luck.utils.IpUtil;
import may.flight.luck.utils.QueryStringUtils;
import may.flight.luck.utils.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Controller
@RequestMapping("main/")
public class MainController extends BaseController {

   private static Logger logger = LoggerFactory.getLogger(MainController.class);
   @Resource
   private AllMessageService allMessageService;
   @Resource
   private TradeService tradeService;
//   @Resource
//   private MemcachedClient memcachedClient;


    private RedisUtils redisUtils;


    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    private volatile boolean isOpen;

    @RequestMapping("start.htm")
    public void start(HttpServletRequest request, HttpServletResponse response, String text) {
        Object[] logParams =  {IpUtil.clientIp(request), QueryStringUtils.getQueryString(request), StreamUtil.readRequestInputStream(request)};
        logger.error("client_ip:{}, request_params:{}, request_body:{}", logParams);
        redisUtils.setValue("request_params", logParams, 1, TimeUnit.DAYS);
        text = StringUtils.isEmpty(text) ? "success" : text;
        print(response, text);
    }


    @RequestMapping("print_params.htm")
    public void printLog(HttpServletRequest request, HttpServletResponse response) {
        print(response, JSONObject.toJSONString(redisUtils.getValue("request_params")));
    }

   @RequestMapping("rule.htm")
    public void rule(HttpServletRequest request, HttpServletResponse response) {
       Map<String,String> map = allMessageService.getNameAgeMap(1);
       print(response, map.toString());
   }


   @RequestMapping("view.htm")
   public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("log", JSONObject.toJSONString(redisUtils.getValue("request_params")));
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

   @RequestMapping("trade/insert.htm")
   public void tradeInsert(HttpServletRequest request, HttpServletResponse response) {
       Trade trade = new Trade();
       trade.setAmount(new BigDecimal(100));
       trade.setBuyId("ye");
       trade.setMerchantId("001");
       trade.setOrderNo("1q7h73u83u833d32");
       tradeService.insertOrder(trade);
       print(response, "success");
   }

   @RequestMapping(value = "trade/select.htm")
   @ResponseBody
   public Trade selectTrade(HttpServletResponse response) {
       return tradeService.selectOrderByOrder("1q7h73u83u833d32");
   }

   @RequestMapping("trade/delete.htm")
    public void deleteTrade(Integer key,HttpServletResponse response) {
        tradeService.delete(key);
        print(response, "success");
   }
}
