package com.leisure.rest;

import com.leisure.config.exception.ForbiddenAccessException;
import com.leisure.entity.Status;
import com.leisure.service.StatusService;
import com.leisure.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/status")
public class StatusRest {
    @Autowired
    private StatusService statusService;
    @Autowired
    private RequestUtil requestUtil;
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Status createStatus(@RequestBody Status status){
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();

        return statusService.createStatus(status);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Status> getStatus(){
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        return statusService.getAllStatus();
    }

    @GetMapping(value = "/getStatusById/{statusId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Status> getStatusById(@PathVariable Long statusId) throws Exception{
        if(!this.requestUtil.isAdmin()) throw new ForbiddenAccessException();
        Status status = this.statusService.getStatusById(statusId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
