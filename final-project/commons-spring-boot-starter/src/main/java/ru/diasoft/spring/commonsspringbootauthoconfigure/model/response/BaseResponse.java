package ru.diasoft.spring.commonsspringbootauthoconfigure.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@ToString
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

    @JsonIgnore
    public boolean isSuccess() {
        return Boolean.TRUE.equals(this.retStatus);
    }

    @JsonIgnore
    public boolean isFail() {
        return Boolean.FALSE.equals(this.retStatus);
    }

    public static BaseResponse createFail(String retMessage) {
        return new BaseResponse().setRetStatus(false).setRetMessage(retMessage);
    }

    public static BaseResponse createSuccess() {
        return new BaseResponse().setRetStatus(true);
    }
}
