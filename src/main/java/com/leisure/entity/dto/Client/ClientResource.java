package com.leisure.entity.dto.Client;

import com.leisure.entity.Bank;
import com.leisure.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ClientResource {
    private Long id;
    private String name;
    private Integer bonus;
    private String lastname;
    private String dni;
    private String email;
    private String cci;
    private String dir;
    private String bankAccount;
    private String phoneNumber;
    private Date activatedDate;
    private String date_created;
    private String pictureProfile;
    private Boolean isActive;
    private Long statusId;
    private Bank bank;
}
