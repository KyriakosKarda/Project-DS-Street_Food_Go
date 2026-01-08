package SFG.Street_Food_Go.Port;

public interface SmsNotificationPort {

    boolean sendSms(final String e164, final String content);

}
