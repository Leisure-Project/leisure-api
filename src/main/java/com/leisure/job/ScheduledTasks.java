package com.leisure.job;

import com.leisure.service.ClientService;
import com.leisure.service.TeamService;
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

    private final ClientService clientService;
    private final TeamService teamService;

    public ScheduledTasks(ClientService clientService, TeamService teamService) {
        this.clientService = clientService;
        this.teamService = teamService;
    }

    @Async
    @Scheduled(cron = "0 0 12 1 * ?")
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
    @Scheduled(cron = "0 55 11 2 * * ?")
    public void calculateEarnings(){
        try {
            logger.error("Inicio de calculo de ganancias");
            List<String> message =  this.clientService.calculateEarnings();
            logger.error("Fin de calculo de ganancias");
            logger.error(String.format("%s", message));
        } catch (Exception e){
            logger.error("Error al calcular las ganancias: " + e.getMessage());
        }
    }
    @Async
    @Scheduled(cron = "0 0 */60 1-20 * ?")
    public void verifyClientsStatus() {
        try {
            logger.error("Inicio de verificacion de estado de clientes");
            List<String> message = this.clientService.verifyClientsStatus();
            this.teamService.removeDuplicates();
            logger.error("Fin de verificacion de estado de clientes");
        } catch (Exception e) {
            logger.error("Error al verificar estado de clientes. " + e.getMessage());
        }
    }

    @Async
    @Scheduled(cron = "0 */15 * * * ?")
    public void updateUserBonus() {
        try {
            logger.error("Inicio de actualización de bonus de clientes");
            String message = this.clientService.updateBonus();
            logger.error("Fin de actualización de bonus de clientes");
        } catch (Exception e) {
            logger.error("Error al actualizar bonus de clientes. " + e.getMessage());
        }
    }
}
