package com.example.userservice.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotNull(message="first name should not be null")
    private String firstName;
    private String lastName;
    private Integer age;
    @NotBlank(message = "address should not be blank")
    private String address;
}
