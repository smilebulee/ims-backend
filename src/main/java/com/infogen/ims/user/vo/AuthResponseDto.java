package com.infogen.ims.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.infogen.ims.user.vo.Auth;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {
    private String userId;
    private String userNm;

    public static AuthResponseDto of(Member user) {
        return AuthResponseDto.builder()
                .userId(user.getUserId())
                .userNm(user.getUserNm())
                .build();
    }
}
