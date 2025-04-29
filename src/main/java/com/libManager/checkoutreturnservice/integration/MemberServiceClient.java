package com.libManager.checkoutreturnservice.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "membermanagementservice")
public interface MemberServiceClient {

    @GetMapping("/api/members/{id}")
    MemberInfo getMemberById(@PathVariable Long id);
}