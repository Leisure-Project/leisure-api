package com.leisure.entity.dto.Admin;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateAdminResource {
    private String alias;
    private String name;
    private String lastname;
    private String fullname;
    private String email;
    private String password;
    private Date createdDate;
}
