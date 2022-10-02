package com.openapi.service.impl;

import com.openapi.service.TestDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neil.lin
 * @version 1.0
 * @since 2022-10-02 - 10:38 AM
 */
@Slf4j
@Service
public class TestDBServiceImpl implements TestDBService {

    @Override
    public Object findByUserId(Long userId) {
        Map<String, String> map = new HashMap<>();
        map.put("user_id",userId.toString());
        return map;
    }
}
