package org.example.sstorage.init;

import org.example.sstorage.services.SUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Class that uploads administrator data to the system.
 *
 * @author UsoltsevI
 */
@Component
public class AdminInitializer {
    @Autowired
    private SUserService sUserService;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    /**
     * Upload admin to the database.
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        sUserService.registerAdmin(adminUsername, adminPassword);
    }
}
