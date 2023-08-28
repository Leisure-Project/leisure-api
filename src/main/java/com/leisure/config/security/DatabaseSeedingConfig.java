package com.leisure.config.security;

import com.leisure.service.RoleService;
import com.leisure.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeedingConfig {
    @Autowired
    RoleService roleService;
    @Autowired
    StatusService statusService;
    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) throws Exception {
        roleService.seed();
        statusService.seed();
    }
}
