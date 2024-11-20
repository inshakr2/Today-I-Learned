package io.security.springsecuritymaster;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionInfoService {

    private final SessionRegistry sessionRegistry;

    public void getSessionInfo() {

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> allSessions = sessionRegistry.getAllSessions(principal, false);

            for (SessionInformation sessionInformation : allSessions) {
                System.out.println("session id : " + sessionInformation.getSessionId());
                System.out.println("session principal : " + sessionInformation.getPrincipal());
                System.out.println("session last request : " + sessionInformation.getLastRequest());
            }
        }

    }
}
