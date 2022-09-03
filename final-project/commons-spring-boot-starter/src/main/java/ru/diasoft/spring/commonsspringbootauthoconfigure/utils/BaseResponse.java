package ru.diasoft.spring.commonsspringbootauthoconfigure.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    @JsonProperty("retStatus")
    private Boolean retStatus;
    @JsonProperty("retMessage")
    private String retMessage;
    @JsonProperty("exception")
    private String exception;
    @JsonProperty("errors")
    private List<String> errors;

    public void success() {
        this.retStatus = true;
    }
}
