package dev.mvc.team2.allowips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IpService {

    @Autowired
    private AllowIpsRepository allowIpsRepository;

    public List<String> getAllowedIps() {
        return allowIpsRepository.findAll()
                                  .stream()
                                  .map(AllowIps::getIp)
                                  .collect(Collectors.toList());
    }
}