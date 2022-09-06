package ru.diasoft.spring.frontservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse extends BaseResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
}
