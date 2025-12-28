package com.leaveflow.employeeservice.controller.view;

import com.leaveflow.employeeservice.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/notifications")
@RequiredArgsConstructor
public class NotificationViewController {

    private final ApiService apiService;

    @GetMapping
    public String viewNotifications(Model model) {
        model.addAttribute("notifications", apiService.getAllNotifications());
        return "notifications";
    }
}