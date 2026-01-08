package SFG.Street_Food_Go.Port.impl.model;

/**
 * PhoneNumberValidationResult DTO.
 *
 * thanks to mr @author Dimitris Gkoulis
 *
 */
public record PhoneNumberValidationResult(
        String raw,
        boolean valid,
        String type,
        String e164
) {

    public static PhoneNumberValidationResult invalid(final String raw) {
        return new PhoneNumberValidationResult(raw, false, null, null);
    }

    public static PhoneNumberValidationResult valid(final String raw, final String type, final String e164) {
        return new PhoneNumberValidationResult(raw, true, type, e164);
    }
}
