package com.krisleonard.codingproject.appsheet.util;

import org.springframework.util.StringUtils;

/**
 * Util class for various static operations related to phone numbers such as phone number validation.
 */
public class PhoneUtil {

    /** RegEx pattern for a valid USA phone number */
    private static final String USA_PHONE_PATTERN = "\\(?\\d{3}\\)?[-\\.]? *\\d{3}[-\\.]? *[-\\.]?\\d{4}";

    /**
     * Validate whether a phone number is a valid USA phone number.
     *
     * @param phoneNumber The phone number to validate
     * @return Whether a phone number is a valid USA phone number.
     */
    public static boolean validUSAPhoneNumber(String phoneNumber) {
        if(StringUtils.isEmpty(phoneNumber)) {
            return false;
        }
        return phoneNumber.matches(USA_PHONE_PATTERN);
    }

}
