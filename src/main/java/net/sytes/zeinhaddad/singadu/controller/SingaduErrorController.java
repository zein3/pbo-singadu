package net.sytes.zeinhaddad.singadu.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class SingaduErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        return "/error";
    }
}
