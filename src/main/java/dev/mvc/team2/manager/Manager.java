package dev.mvc.team2.manager;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity

public class Manager {
//  CREATE TABLE MANAGER
// (
//   MANAGERNO NUMBER(10),
//   ID VARCHAR2(30),
//   PASSWD VARCHAR2(200 BYTE),
//   MNAME VARCHAR2(20),
//   PHONE VARCHAR2(14),
//   GRADE NUMBER(3),
//   REG_DATE DATE
//)
//

  @Id

  @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="manager_seq")
  @SequenceGenerator(name="manager_seq", sequenceName="ISSUE_SEQ", allocationSize=1)
  private long managerno;

  private String id;
  private String passwd;
  private String mname;
  private int grade;
  @Column(name = "reg_date")

  private String regDate;

  public Manager(String id, String passwd, String mname, int grade, String regDate) {
    this.id = id;
    this.passwd = passwd;
    this.mname = mname;
    this.grade = grade;
    this.regDate = regDate;
  }

  public Manager() {

  }
}
