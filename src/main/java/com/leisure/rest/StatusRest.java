package com.leisure.rest;

import com.leisure.entity.Status;
import com.leisure.service.StatusService;
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Status createStatus(@RequestBody Status status){
        return statusService.createStatus(status);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Status> getStatus(){
        return statusService.getAllStatus();
    }

    @GetMapping(value = "/getStatusById/{statusId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Status> getStatusById(@PathVariable Long statusId) throws Exception{
        Status status = this.statusService.getStatusById(statusId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
