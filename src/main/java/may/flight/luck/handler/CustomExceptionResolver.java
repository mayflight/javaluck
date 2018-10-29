package may.flight.luck.handler;
import com.yadong.ye.bean.BaseResult;
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
import java.io.PrintWriter;
import java.io.StringWriter;

@PropertySource("classpath:system.properties")
public class CustomExceptionResolver extends SimpleMappingExceptionResolver {
    @Resource
    MailSendService mailSendService;
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Value("${static.mail}")
    private String mail;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error("custom_error", ex);
        MailDetailData detailData = new MailDetailData();
        detailData.setSubject(ex.getMessage());
        detailData.setContent(getDetailError(ex));
        detailData.setReceiveMailAccount(mail);
        BaseResult result = mailSendService.sendSimpleMail(detailData);
        ModelAndView view = super.resolveException(request, response, handler, ex);
        if (result != null && result.getCode() != 0) {
            view.addObject("error", "send mail error:"+result.getMessage() +";;"+ ex.getLocalizedMessage());
        }else {
            view.addObject("error",  ex.getLocalizedMessage());
        }

        return view;
    }

    private String getDetailError(Exception e) {
        if (null == e) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
