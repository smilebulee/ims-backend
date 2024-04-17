package com.infogen.ims.user.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    
    private String tokenType;
    private String accessToken;
    private String refreshToken;

}
