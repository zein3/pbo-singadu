package net.sytes.zeinhaddad.singadu.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DashboardController {
    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String user() {
        return "/user/index";
    }

    @GetMapping("/laporan")
    public String laporan() {
        return "/laporan/index";
    }

    @GetMapping("/laporan/create")
    public String createLaporan() {
        return "/laporan/create";
    }

    @GetMapping("/laporan/{id}/edit")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PENGAWAS')")
    public String editLaporan(@PathVariable Long id, Model model) {
        return "/laporan/edit";
    }

    @GetMapping("/jenis-masalah")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String jenisMasalah() {
        return "/ptype/index";
    }

    @GetMapping("/my-profile")
    public String profile() {
        return "/profile";
    }
}
