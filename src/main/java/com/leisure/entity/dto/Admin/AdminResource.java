package com.leisure.entity.dto.Admin;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AdminResource {
    private Long id;
    private String alias;
    private String name;
    private String lastname;
    private String dni;
    private String email;
    private String phoneNumber;
    private String pictureProfile;
    private String date_created;
}
