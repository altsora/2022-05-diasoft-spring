package ru.diasoft.spring.taskservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.diasoft.spring.commonsspringbootauthoconfigure.model.response.BaseResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreateTaskResponse extends BaseResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("state")
    private String state;
    @JsonProperty("uniqNumber")
    private Integer uniqNumber;
}
