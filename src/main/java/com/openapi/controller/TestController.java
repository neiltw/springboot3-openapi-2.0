package com.openapi.controller;

import com.openapi.service.TestDBService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "api", description = "測試")
public class TestController {

    @Autowired
    private TestDBService testDBService;


    @Operation(summary = "測試db data", description = "測試es ",parameters = {@Parameter(name = "userId" , description = "用戶id")})
    @ApiResponse(responseCode = "2xx" , description = "成功")
    @GetMapping(value = "/db/{userId}"  , produces = APPLICATION_JSON_VALUE)
    public Object getDB(@PathVariable Long userId){
        Object data = testDBService.findByUserId(userId);
        return data;
    }
}