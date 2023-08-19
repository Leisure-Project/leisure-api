package com.leisure.job;

import com.leisure.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ClientService clientService;

    @Async
    @Scheduled(cron = "00 12 2 * * ?")
    public void resetClients(){
        try {
            logger.error("Inicio de reseteo de clientes");
            String message =  this.clientService.resetClients();
            logger.error("Fin de reseteo de clientes: ");
            logger.error(String.format("%s", message));
        } catch (Exception e){
            logger.error("Error al resetear clientes. " + e.getMessage());
        }
    }

    @Async
    @Scheduled(cron = "* */60 * * * *")
    public void verifyClientsStatus() {
        try {
            logger.error("Inicio de verificacion de estado de clientes");
            List<String> message = this.clientService.verifyClientsStatus();
            logger.error("Fin de verificacion de estado de clientes");
        } catch (Exception e) {
            logger.error("Error al verificar estado de clientes. " + e.getMessage());
        }
    }
}
