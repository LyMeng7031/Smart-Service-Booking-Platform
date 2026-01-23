package com.example.smart_service.dto.request;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@Schema(description = "Register Request DTO")
public class RegisterRequest {
  @Schema(
        description = "Unique username",
        example = "sa123",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
  private String username;
  @Schema(
        description = "Email address",
        example = "sa123@example.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
  private String email;
  @Schema(
        description = "Password",
        example = "password123",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
  private String password;
  @Schema(
        description = "Phone number",
        example = "+94771234567"
    )
  private String phone;
  @Schema(
        description = "Profile image URL",
        example = "https://example.com/profile.jpg"
    )
  private String profileImage;
  private String status = "active";

}
