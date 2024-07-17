package dev.mvc.team2.allowips;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

@Component

public class IpAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private IpService ipService;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestContext) {
        String remoteAddr = requestContext.getRequest().getRemoteAddr();
        List<String> allowedIps = ipService.getAllowedIps();

        boolean granted = allowedIps.contains(remoteAddr);
        return new AuthorizationDecision(granted);
    }
}
