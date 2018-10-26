package may.flight.luck.handler;

import com.alibaba.fastjson.JSON;
import com.yadong.ye.bean.MailDetailData;
import com.yadong.ye.dubbo.MailSendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@PropertySource("classpath:system.properties")
public class CustomExceptionResolver extends SimpleMappingExceptionResolver {
    @Resource
    MailSendService mailSendService;
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Value("${mail}")
    private String mail;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("custom_error", ex);
        MailDetailData detailData = new MailDetailData();
        detailData.setSubject(ex.getLocalizedMessage());
        detailData.setContent(JSON.toJSONString(ex.getStackTrace()));
        detailData.setReceiveMailAccount(mail);
        mailSendService.sendSimpleMail(detailData);
        ModelAndView view = super.resolveException(request, response, handler, ex);
        view.addObject("error", ex.getLocalizedMessage());
        return view;
    }
}
