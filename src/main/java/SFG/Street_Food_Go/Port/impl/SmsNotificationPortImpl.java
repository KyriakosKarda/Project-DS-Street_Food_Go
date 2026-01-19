package SFG.Street_Food_Go.Port.impl;

import SFG.Street_Food_Go.Port.SmsNotificationPort;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@Primary
public class SmsNotificationPortImpl implements SmsNotificationPort {
    private final RestClient restClient;

    public SmsNotificationPortImpl(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("http://service-noc:8081").build();
    }

    private record ExternalSmsRequest(String e164, String content){}



    @Override
    public boolean sendSms(String e164, String content) {
        try {
            restClient.post()
                    .uri("api/v1/sms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ExternalSmsRequest(e164,content))
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
