package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Client;
import com.leisure.entity.Sales;
import com.leisure.entity.Status;
import com.leisure.entity.Team;
import com.leisure.entity.dto.Team.MembersTeamCountResource;
import com.leisure.entity.enumeration.StatusName;
import com.leisure.repository.ClientRepository;
import com.leisure.repository.SalesRepository;
import com.leisure.repository.StatusRepository;
import com.leisure.repository.TeamRepository;
import com.leisure.service.ClientService;
import com.leisure.util.Constants;
import com.leisure.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamServiceImpl teamService;
    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private AuthServiceImpl authService;
    private final String ENTITY = "Cliente";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Client save(Client client, Long statusId) throws Exception {
        Optional<Status> status = this.statusRepository.findById(statusId);
        if(status.isEmpty()){
            throw new ResourceNotFoundException("Status", statusId);
        }
        client.setStatus(status.get());
        logger.error("n" + client);
        Boolean existsUser = this.clientRepository.existsByDni(client.getDni());
        Boolean existsEmail = this.clientRepository.existsByEmail(client.getEmail());
        if(Boolean.TRUE.equals(existsUser)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el dni %s", client.getDni()));
        } else if(Boolean.TRUE.equals(existsEmail)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el email %s", client.getEmail()));
        }
        return this.authService.registerClient(client);
    }

    @Override
    public Client saveClientInDbAndTeam(Client client, Long parentId, Long statusId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(parentId);
        if(optionalClient.isEmpty()) {
            throw new ResourceNotFoundException(ENTITY, statusId);
        }
        Optional<Status> status = this.statusRepository.findById(statusId);
        if(status.isEmpty()){
            throw new ResourceNotFoundException("Status", statusId);
        }
        client.setStatus(status.get());
        Boolean existsUser = this.clientRepository.existsByDni(client.getDni());
        Boolean existsEmail = this.clientRepository.existsByEmail(client.getEmail());
        if(Boolean.TRUE.equals(existsUser)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el dni %s", client.getDni()));
        } else if(Boolean.TRUE.equals(existsEmail)){
            throw new RuntimeException(String.format("Ya existe un usuario registrado con el email %s", client.getEmail()));
        }
        List<Team> teamList = this.teamRepository.getTeamsByParentId(parentId);
        if(teamList.size() > 3){
            throw new RuntimeException("El máximo de miembros por equipo es de 3.");
        }

        Client newMember = this.authService.registerClient(client);

        Date createdDate = new Date();

        Team team = new Team();
        team.setParentId(parentId);
        team.setChildId(newMember.getId());
        team.setIsActive(true);
        team.setCreated_date(createdDate);
        this.teamRepository.save(team);
        //this.emailService.sendEmail(newMember.getId(), newMember.getEmail());
        return newMember;
    }

    @Override
    public Client update(Client client, Long clientId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, clientId);
        }
        Client clientUpdate = optionalClient.get();
        clientUpdate.setEmail(client.getEmail());
        clientUpdate.setPictureProfile(client.getPictureProfile());
        clientUpdate.setPhoneNumber(client.getPhoneNumber());
        clientUpdate.setDni(client.getDni());
        clientUpdate.setCci(client.getCci());
        clientUpdate.setBankAccount(client.getBankAccount());
        clientUpdate.setBank(client.getBank());
        clientUpdate.setDir(client.getDir());
        return this.clientRepository.save(clientUpdate);
    }

    @Override
    public Client getClientById(Long clientId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException(ENTITY, clientId);
        }
        return optionalClient.get();
    }

    @Override
    public Client getClientByDni(String dni) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findByDni(dni);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("No hay ningún cliente registrado con ese dni");
        }
        return optionalClient.get();
    }

    @Override
    public List<Client> getAllClients() throws Exception {
        List<Client> clientList = this.clientRepository.findAll();
        if(clientList.isEmpty()){
            throw new ResourceNotFoundException("No hay ningún cliente registrado en la plataforma");
        }
        return clientList;
    }

    @Override
    public String resetClients() throws Exception {
        String message = "";
        Status statusActive = this.statusRepository.getStatusByName(StatusName.valueOf(Constants.ESTADO_ACTIVO));
        Status statusInactive = this.statusRepository.getStatusByName(StatusName.valueOf(Constants.ESTADO_INACTIVO));
        List<Client> clientList = this.clientRepository.getAllClientsByStatusId(statusActive.getId());
        if(clientList.isEmpty()){
            throw new RuntimeException("No hay ningun usuario activo en la plataforma");
        }
        List<Client> clientsListAux = clientList.stream().map(x -> {
            x.setStatus(statusInactive);
            x.setActivatedDate(DateUtils.getCurrentDateAndHour());
            return x;
        }).collect(Collectors.toList());
        message = String.format("Se cambio el estado a inactivo de %d clientes", clientsListAux.size());
        return message;
    }

    @Override
    public List<String> verifyClientsStatus() throws Exception {
        List<String> messageList = new ArrayList<>();
        String message = "";
        Status statusInactive = this.statusRepository.getStatusByName(StatusName.valueOf(Constants.ESTADO_INACTIVO));
        List<Client> clientList = this.clientRepository.getAllClientsByStatusId(statusInactive.getId());
        if(clientList.isEmpty()){
            throw new RuntimeException("No hay ningun usuario inactivo en la plataforma");
        }
        List<Client> clientsInactive = new ArrayList<>();
        clientList.stream().filter(
                x -> {
                    LocalDate convertDate = x.getActivatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate actualDate = LocalDate.now();
                    LocalDate dateTwoMonthsLater = actualDate.minusMonths(2);
                    logger.error("" + convertDate + " " + convertDate.isBefore(dateTwoMonthsLater));
                    if(convertDate.isBefore(dateTwoMonthsLater)){
                        clientsInactive.add(x);
                    }
                    return false;
                }
        ).collect(Collectors.toList());

        message = String.format("Actualmente hay %d usuarios con un tiempo de inactividad mayor o igual a dos meses", clientsInactive.size());
        messageList.add(message);

        List<Long> parentsIdList = clientsInactive.stream().map(x -> x.getId()).collect(Collectors.toList());
        logger.error(""+parentsIdList);
        List<Team> teamList = this.teamRepository.getAllTeamByParentIdList(parentsIdList);
        Map<Object, List<Team>> teamMap = this.groupResultByParentId(teamList);

        List<Long> parentIdWithTeam = new ArrayList<>();
        for (Long parentId : parentsIdList) {
            if (teamMap.containsKey(parentId)) {
                parentIdWithTeam.add(parentId);
            }
        }

        message = String.format("De los %d usuarios inactivos, solo %d son jefes de un equipo", clientsInactive.size(), parentIdWithTeam.size());
        messageList.add(message);

        logger.error("" + teamMap);
        List<Team> oldMembers = teamMap.values().stream()
                .flatMap(List::stream)
                .filter(Team::getIsActive)
                .collect(Collectors.groupingBy(
                        team -> team.getParentId(),
                        Collectors.minBy(Comparator.comparing(team -> team.getCreated_date()))
                ))
                .values().stream()
                .map(Optional::get)
                .collect(Collectors.toList());
        logger.error(""+oldMembers);

        List<Long> newParents = oldMembers.stream().map(x -> {
            x.setIsActive(false);
            this.teamRepository.save(x);
            return x.getChildId();
        }).collect(Collectors.toList());
        logger.error(""+newParents);

        Map<Long, Map<String, Long>> resultMap = new HashMap<>();

        for (int i = 0; i < parentIdWithTeam.size(); i++) {
            Long parentId = parentIdWithTeam.get(i);
            Long childId = newParents.get(i);
            Map<String, Long> childMap = new HashMap<>();
            childMap.put("parentId", parentId);
            childMap.put("childId", childId);
            resultMap.put(parentId, childMap);
        }
        logger.error("" + resultMap);
        for (Team team : teamList) {
            Map<String, Long> replaceMap = resultMap.get(team.getParentId());
            if (replaceMap != null) {
                team.setParentId(replaceMap.get("childId"));
            }
        }
        logger.error("" + teamList);

        return messageList;
    }

    @Override
    public List<String> calculateEarnings() throws Exception {
        List<String> messages = new ArrayList<>();
        List<MembersTeamCountResource> teams = this.teamRepository.getMembersCount();
        messages.add(String.format("Se encontró un total de %d equipos.", teams.size()));
        for (MembersTeamCountResource item : teams){
            Sales sales = new Sales();
            sales.setClient(this.clientRepository.findById(item.getParentId()).get());
            sales.setPrice(3.0 * item.getTeamMembers());
            sales.setAmount(Math.toIntExact(item.getTeamMembers()));
            sales.setComments("Ganancias mensuales.");
            sales.setCreated_date(DateUtils.getCurrentDateAndHour());
            sales.setIsActive(true);
            this.salesRepository.save(sales);
        }
        messages.add("Ventas guardadas exitosamente en la base de datos.");
        return messages;
    }

    @Override
    public String updateBonus() throws Exception {
        List<Client> clientList = this.clientRepository.findAll();
        List<Client> lst = clientList.stream().map(x -> {
            if(this.teamRepository.existsByParentId(x.getId())){
                try {
                    x.setBonus(this.getBonus(x.getId()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                x.setBonus(0);
            }
            return x;
        }).collect(Collectors.toList());
        this.clientRepository.saveAll(lst);
        return "Bono actualizado";
    }

    public Map<Object, List<Team>> groupResultByParentId(List<Team> teamList){
        return teamList.stream()
                    .sorted((f1, f2) -> ((Date)f1.getCreated_date()).compareTo(f2.getCreated_date()))
                    .collect(Collectors.groupingBy(team -> team.getParentId()));
    }
    public  Integer getBonus(Long userId) throws Exception{
        Map<String, Long> lst = this.teamService.getMemberCountTeamHierarchy(userId);
        return lst.get("totalMembersActive").intValue() * 3;
    }
}

