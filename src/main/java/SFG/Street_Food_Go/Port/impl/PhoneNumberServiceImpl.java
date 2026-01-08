package SFG.Street_Food_Go.Port.impl;

import SFG.Street_Food_Go.Port.PhoneNumberService;

import SFG.Street_Food_Go.Port.impl.model.PhoneNumberValidationResult;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.stereotype.Service;
import java.util.Locale;

/**
 * libphonenumber-based implementation of {@link PhoneNumberService}.
 *
 * @author Dimitris Gkoulis
 */
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberUtil phoneNumberUtil;
    private final String defaultRegion;

    public PhoneNumberServiceImpl() {
        this.phoneNumberUtil = PhoneNumberUtil.getInstance();
        this.defaultRegion = "GR";
    }

    @Override
    public PhoneNumberValidationResult validatePhoneNumber(final String rawPhoneNumber) {
        if (rawPhoneNumber == null) return PhoneNumberValidationResult.invalid(rawPhoneNumber);
        if (rawPhoneNumber.isBlank()) return PhoneNumberValidationResult.invalid(rawPhoneNumber);
        try {
            final Phonenumber.PhoneNumber phoneNumber = this.phoneNumberUtil.parse(rawPhoneNumber, this.defaultRegion);
            if (!this.phoneNumberUtil.isValidNumber(phoneNumber)) {
                return PhoneNumberValidationResult.invalid(rawPhoneNumber);
            }
            return PhoneNumberValidationResult.valid(
                    rawPhoneNumber,
                    this.phoneNumberUtil.getNumberType(phoneNumber).name().toLowerCase(Locale.ROOT),
                    this.phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
            );
        } catch (NumberParseException e) {
            System.err.println("==================");
            return PhoneNumberValidationResult.invalid(rawPhoneNumber);
        }
    }
}