package com.leaveflow.leaveservice.client;

import com.leaveflow.leaveservice.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EmployeeClient {
    private RestTemplate restTemplate;
    private String employeServiceBaseUrl;
    public EmployeeClient(RestTemplate restTemplate, @Value("${employe.service.url}") String employeServiceBaseUrl) {
       this.restTemplate = new RestTemplate();
        this.employeServiceBaseUrl =employeServiceBaseUrl ;
    }
    public EmployeeDto getEmployee(Long Id) {
        String url=employeServiceBaseUrl+"/employees/"+Id;
        return   restTemplate.getForObject(url, EmployeeDto.class);
    }

}
