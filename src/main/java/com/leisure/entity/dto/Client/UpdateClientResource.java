package com.leisure.entity.dto.Client;

import com.leisure.entity.Bank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClientResource {
    private String pictureProfile;
    private String email;
    private String phoneNumber;
    private String cci;
    private String bankAccount;
    private String dir;
    private String dni;
    private Bank bank;
}
