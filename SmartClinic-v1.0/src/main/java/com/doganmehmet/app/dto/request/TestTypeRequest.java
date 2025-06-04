package com.doganmehmet.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestTypeRequest {
    @NotBlank(message = "Test type name cannot be blank")
    private String testTypeName;
}
