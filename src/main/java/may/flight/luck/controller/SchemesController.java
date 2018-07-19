package may.flight.luck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//weixin://scanqrcode
//alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/c1x03418odvudezogjca9ac
@Controller
@RequestMapping("scheme/")
public class SchemesController extends BaseController {
    @RequestMapping("alipay/red_code.htm")
    public String redCode(HttpServletRequest request, HttpServletResponse response, Model model) {
        String redCode = "alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/c1x03418odvudezogjca9ac";
        model.addAttribute("code", redCode);
        return "red_code";
    }
}
