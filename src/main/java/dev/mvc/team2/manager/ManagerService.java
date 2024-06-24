package dev.mvc.team2.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
  @Autowired
  private final ManagerRepository repository;

  public ManagerService(ManagerRepository repository) {
    this.repository = repository;
  }

  /** 생성자 */


  /** Create, INSERT~, UPDATE~ */

   public Integer login(String id, String passwd) {
     return repository.login(id, passwd);
   }
}
