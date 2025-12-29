package com.leaveflow.employeeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiService {

    private final RestTemplate restTemplate;

    @Value("${services.calendar.url}")
    private String calendarServiceUrl;

    @Value("${services.leave.url}")
    private String leaveServiceUrl;

    @Value("${services.notification.url}")
    private String notificationServiceUrl;

    // Get all holidays
    public List<Map<String, Object>> getAllHolidays() {
        try {
            return restTemplate.exchange(
                    calendarServiceUrl + "/calendar/holidays",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            ).getBody();
        } catch (Exception e) {
            System.err.println("Error fetching holidays: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Get all leaves
    public List<Map<String, Object>> getAllLeaves() {
        try {
            return restTemplate.exchange(
                    leaveServiceUrl + "/leaves",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            ).getBody();
        } catch (Exception e) {
            System.err.println("Error fetching leaves: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Create leave
    public Map<String, Object> createLeave(Map<String, Object> leaveRequest) {
        try {
            return restTemplate.postForObject(
                    leaveServiceUrl + "/leaves",
                    leaveRequest,
                    Map.class
            );
        } catch (Exception e) {
            System.err.println("Error creating leave: " + e.getMessage());
            throw new RuntimeException("Failed to create leave: " + e.getMessage());
        }
    }

    // Get all notifications
    public List<Map<String, Object>> getAllNotifications() {
        try {
            return restTemplate.exchange(
                    notificationServiceUrl + "/notifications",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            ).getBody();
        } catch (Exception e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}