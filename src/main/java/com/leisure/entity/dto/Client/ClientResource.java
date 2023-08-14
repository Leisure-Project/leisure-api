package com.leisure.entity.dto.Client;

import com.leisure.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClientResource {
    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String dni;
    private Date activatedDate;
    private Date createdDate;
    private String pictureProfile;
    private Boolean isActive;
    private Status status;
}
