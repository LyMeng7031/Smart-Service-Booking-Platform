package com.example.smart_service.dto.response;

import lombok.Data;

@Data
public class AuthResponse {
  private Long id;
  private String username;
  private String email;
  private String password;
  private String phone;
  private String prifileImage;
  private String status = "active";
  private String accesstoken;
  private String refreshtoken;
  
  public AuthResponse(Long id, String username, String email, String password, String phone, String prifileImage, String status, String accesstoken, String refreshtoken) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.prifileImage = prifileImage;
    this.status = status;
    this.accesstoken = accesstoken;
    this.refreshtoken = refreshtoken;
  }
}
