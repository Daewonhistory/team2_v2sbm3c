package dev.mvc.team2.initializer;

import dev.mvc.team2.allowips.AllowIps;
import dev.mvc.team2.allowips.AllowIpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AllowIpsRepository allowIpsRepository;

    @Override
    public void run(String... args) throws Exception {
        if (allowIpsRepository.count() == 0) {
            AllowIps ip1 = new AllowIps();
            ip1.setId(1L);
            ip1.setIpAddress("127.0.0.1");

            AllowIps ip2 = new AllowIps();
            ip2.setId(2L);

            allowIpsRepository.saveAll(Arrays.asList(ip1, ip2));
        }
    }
}
