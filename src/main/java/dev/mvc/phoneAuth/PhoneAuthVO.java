package dev.mvc.phoneAuth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneAuthVO {
  private int id;
  private String phone;
  private String auth;
}
