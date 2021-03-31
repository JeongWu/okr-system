package com.eximbay.okr.controller;

import com.eximbay.okr.config.security.MyUserDetails;
import com.eximbay.okr.constant.FlagOption;
import com.eximbay.okr.service.Interface.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ApplicationController {

    private final ICompanyService companyService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        Object currentMember = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentMember instanceof MyUserDetails) return "redirect:/";
        boolean isGoogleLogin = companyService  .getCompany()
                                                .map(m->m.getGoogleSignIn().equals(FlagOption.Y))
                                                .orElse(false);
        model.addAttribute("isGoogleLogin", isGoogleLogin);
        return "login/login";
    }
}
