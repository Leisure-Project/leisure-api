package com.leisure.entity.dto.Client;

import com.leisure.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateClientResource {
    private String name;
    private String lastname;
    private String dni;
    private String email;
    private Boolean isActive;
    private String password;
    private Date activatedDate;
    private Date createdDate;
    private String pictureProfile;
}
