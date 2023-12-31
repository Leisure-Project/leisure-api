package com.leisure.rest;

import com.leisure.entity.Status;
import com.leisure.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/status")
public class StatusRest {
    @Autowired
    private StatusService statusService;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public Status createStatus(@RequestBody Status status){
        return statusService.createStatus(status);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Status> getStatus(){
        return statusService.getAllStatus();
    }
}
