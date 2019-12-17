package com.example.pulsardemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class StaticResourceController {
    @GetMapping(path = "/html")
    public void getHtml(HttpServletResponse response) {
        try {
            response.sendRedirect("https://precdn.xylink.com/public/mms/desktop.html");
        } catch (IOException e) {

        }
    }

    @GetMapping(path = "/mav")
    public ModelAndView getWithModelAndView() {
        String url = "redirect:https://precdn.xylink.com/public/mms/desktop.html";
        return new ModelAndView(url);
    }

    @GetMapping(path = "/redirect")
    public String getRedirectString() {
        return "redirect:https://precdn.xylink.com/public/mms/desktop.html";
    }
}
