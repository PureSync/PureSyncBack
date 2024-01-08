package com.fcc.PureSync.context.admin.login;

//import com.fcc.PureSync.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@ToString
@RequiredArgsConstructor
@Controller
public class AdminController {

    //로그인 페이지
    @GetMapping("/admin/login")
    public String adminMain() {
        return "login/login";
    }


}
