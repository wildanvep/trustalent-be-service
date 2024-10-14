package com.evact.trustalent.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

    private String token;
    private Date tokenExpiredAt;
}
