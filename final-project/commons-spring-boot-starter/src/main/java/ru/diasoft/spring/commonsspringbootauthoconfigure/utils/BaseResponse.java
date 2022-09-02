package ru.diasoft.spring.commonsspringbootauthoconfigure.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    @JsonProperty("retStatus")
    private Boolean retStatus = false;
    @JsonProperty("retMessage")
    private String retMessage;
}
