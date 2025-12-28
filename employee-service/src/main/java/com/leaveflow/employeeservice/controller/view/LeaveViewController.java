package com.leaveflow.employeeservice.controller.view;

import com.leaveflow.employeeservice.service.ApiService;
import com.leaveflow.employeeservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ui/leaves")
@RequiredArgsConstructor
public class LeaveViewController {

    private final ApiService apiService;
    private final EmployeeService employeeService;

    @GetMapping
    public String viewLeaves(Model model) {
        model.addAttribute("leaves", apiService.getAllLeaves());
        return "leaves";
    }

    @GetMapping("/new")
    public String newLeaveForm(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "leave-form";
    }

    @PostMapping("/create")
    public String createLeave(
            @RequestParam Long employeeId,
            @RequestParam String leaveType,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String reason,
            RedirectAttributes redirectAttributes) {

        try {
            Map<String, Object> leaveRequest = new HashMap<>();
            leaveRequest.put("employeeId", employeeId);
            leaveRequest.put("startDate", startDate);
            leaveRequest.put("endDate", endDate);
            leaveRequest.put("leaveType", leaveType);
            leaveRequest.put("leaveReason", reason);

            apiService.createLeave(leaveRequest);

            redirectAttributes.addFlashAttribute("success",
                    "Leave request submitted successfully!");
            return "redirect:/ui/leaves";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Failed to create leave: " + e.getMessage());
            return "redirect:/ui/leaves/new";
        }
    }
}