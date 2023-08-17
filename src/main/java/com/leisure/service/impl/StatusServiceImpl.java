package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Status;
import com.leisure.repository.StatusRepository;
import com.leisure.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;
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

}
