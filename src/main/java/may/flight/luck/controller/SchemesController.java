package may.flight.luck.controller;

import may.flight.luck.tools.HttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//weixin://scanqrcode
//alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/c1x03418odvudezogjca9ac
@Controller
@RequestMapping("scheme/")
public class SchemesController extends BaseController {
    @RequestMapping("{id}/red_code.htm")
    public String redCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, Model model) {
        String redCode = "alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/c1x03418odvudezogjca9ac";
        if ("jiuyan".equals(id)) {
            redCode = "alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/c1x036533xmxf9ccpgbn3bc";
        }

        model.addAttribute("code", redCode);
        return "red_code";
    }

    @RequestMapping("wx/token.htm")
    public void getToken(HttpServletResponse response) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxdd95152208125a88&secret=0572ec9e65f5b48ebbcffa5f53d55918";
        String result = HttpUtils.httpGet(url, 30);
        print(response, result);
    }

//    @RequestMapping("wx/url")
//    public void getUrl(HttpServletResponse response, String token, String params) {
//        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+params;
//
//    }
}
