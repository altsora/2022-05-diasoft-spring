package ru.diasoft.spring.taskservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskResponse extends BaseResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("state")
    private String state;
}
