package com.anan.msmService.service;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface MsmService {
    boolean send(Map<String, Object> param, String phone) throws ExecutionException, InterruptedException;
}
