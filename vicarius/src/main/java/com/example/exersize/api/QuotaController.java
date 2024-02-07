package com.example.exersize.api;


import com.example.exersize.service.QuotaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("quota")
public class QuotaController {

    private final QuotaService quotaService;

    public QuotaController(QuotaService quotaService) {
        this.quotaService = quotaService;
    }

    @GetMapping("/{userId}")
    public String consumeQuota(@PathVariable String userId) {
        return quotaService.consumeQuota(userId);

    }

    @GetMapping("/all")
    public Map<String, String> getUsersQuota() {
        return quotaService.getUsersQuota();
    }

}
