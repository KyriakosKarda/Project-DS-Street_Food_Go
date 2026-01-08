package SFG.Street_Food_Go.Port;

import SFG.Street_Food_Go.Port.impl.model.PhoneNumberValidationResult;

/**
 * Service for managing phone numbers.
 *
 * respect for providing us the service.
 *
 * @author Dimitris Gkoulis
 */
public interface PhoneNumberService {

    PhoneNumberValidationResult validatePhoneNumber(final String rawPhoneNumber);
}