package dev.mvc.tool;

import dev.mvc.dto.HistoryDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PublicTools {

  public Map<String, List<HistoryDTO>> groupByLoginDate(List<HistoryDTO> loginHistoryList) {

    return loginHistoryList.stream().collect(Collectors.groupingBy(HistoryDTO::getLogin_date));


  }
}
