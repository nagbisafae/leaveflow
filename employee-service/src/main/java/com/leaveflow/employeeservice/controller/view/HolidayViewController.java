package com.leaveflow.employeeservice.controller.view;

import com.leaveflow.employeeservice.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/holidays")
@RequiredArgsConstructor
public class HolidayViewController {

    private final ApiService apiService;

    @GetMapping
    public String viewHolidays(Model model) {
        model.addAttribute("holidays", apiService.getAllHolidays());
        return "holidays";
    }
}