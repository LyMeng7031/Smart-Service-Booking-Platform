package com.example.smart_service.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
  private String username;
  private String email;
  private String password;
  private String phone;
  private String prifileImage;
  private String status = "active";

}
