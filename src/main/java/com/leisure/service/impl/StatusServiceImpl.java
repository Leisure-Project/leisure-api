package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Status;
import com.leisure.entity.enumeration.StatusName;
import com.leisure.repository.StatusRepository;
import com.leisure.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;
    private static String[] DEFAULT_STATUS = { "ACTIVO","INACTIVO"};
    private static String[] DEFAULT_STATUS_DESCRIPTION = {
            "El usuario ha realizado su consumo mínimo mensual.",
            "El usuario NO ha realizado su consumo mínimo mensual."
    };
    @Override
    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    @Override
    public Status getStatusById(Long statusId) {
        Optional<Status> optionalStatus = this.statusRepository.findById(statusId);
        if(optionalStatus.isEmpty()){
            throw new ResourceNotFoundException("Estado", statusId);
        }
        return optionalStatus.get();
    }

    @Override
    public void seed() {
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(DEFAULT_STATUS).forEach(name -> {
            StatusName statusName = StatusName.valueOf(name);
            if(!statusRepository.existsByName(statusName)) {
                statusRepository.save((new Status()).withName(statusName).withDescription(DEFAULT_STATUS_DESCRIPTION[i.get()]));
            }
            i.getAndIncrement();
        } );
    }

}
