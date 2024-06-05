package dev.mvc.tool;

import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

public class IpLocationService {
    private final String IPINFO_API = "https://ipinfo.io/{ip}?token={token}";
    private final String token = "4a8864ceacd88d";

    public Map<String, Object> getLocation(String ip) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(IPINFO_API, Map.class, ip, token);

        Map<String, String> locationInfo = new HashMap<>();
        if (response != null) {
            locationInfo.put("city", (String) response.get("city"));
            locationInfo.put("region", (String) response.get("region"));
            locationInfo.put("country", (String) response.get("country"));
        }
        return response;
    }
}