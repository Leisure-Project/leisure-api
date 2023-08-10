package com.leisure.service;

import com.leisure.entity.Status;

import java.util.List;

public interface StatusService {
    Status createStatus(Status status);
    List<Status> getAllStatus();
}
