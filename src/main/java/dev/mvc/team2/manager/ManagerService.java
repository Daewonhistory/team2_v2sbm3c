package dev.mvc.team2.manager;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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



  public Manager readById(String id) {
     return repository.readById(id);
   }

   public int checkId(String id) {
     return repository.checkId(id);
   }

  @Transactional
  public int save(Manager manager) {
    try {
      repository.save(manager);
      return 1; // 성공 시 1 반환
    } catch (Exception e) {
      return 0; // 실패 시 0 반환
    }
  }
}
