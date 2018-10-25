package may.flight.luck.controller;

import com.yadong.ye.bean.MailDetailData;
import com.yadong.ye.dubbo.MailSendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("admin/")
@PropertySource("classpath:system.properties")
public class AdminController extends BaseController{
    @Value("${static.path}")
    private String fileDirectory;

    @Value("${static.domain}")
    private String domain;

    @Value("${static.password}")
    private String password;

    @Resource
    MailSendService mailSendService;


    @RequestMapping("home.htm")
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "home";
    }

    @RequestMapping("upload.htm")
    @ResponseBody
    public String upload(@RequestParam MultipartFile file, String token, String mail) {
        if (!StringUtils.equals(password, token)) {
            return "密码错误";
        }
        if (null == file || file.isEmpty()) {
            return "file is null";
        }
        String name = System.currentTimeMillis() + file.getOriginalFilename();
        File newFile = new File(fileDirectory, name);
        try {
            file.transferTo(newFile);
        }catch (IOException e) {
            return e.getMessage();
        }
        String url = domain + name;
        if (StringUtils.isNotBlank(mail)) {
            MailDetailData data = new MailDetailData();
            data.setContent("文件:"+file.getOriginalFilename()+"，已经上传成功，地址:"+url);
            data.setReceiveMailAccount(mail);
            data.setSubject("文件上传成功通知");
            mailSendService.sendSimpleMail(data);
        }
        return url;
    }
}
