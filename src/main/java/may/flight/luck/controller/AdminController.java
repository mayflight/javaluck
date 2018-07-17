package may.flight.luck.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("admin/")
public class AdminController extends BaseController{
    @RequestMapping("home.htm")
    public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "home";
    }
}
