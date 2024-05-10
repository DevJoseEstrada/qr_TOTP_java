package org.devjmestrada.otpsystem.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OtpAuth {
    String userName;
    String secret;
    String issuer;
    String algorithm;
    int digits;
    int period;

    public String buildURI() {
        StringBuilder uri = new StringBuilder("otpauth://totp/").append(userName);
        if (secret != null) {uri.append("?secret=").append(secret);}
        if (issuer != null) {uri.append("&issuer=").append(issuer);}
        if (algorithm != null) {uri.append("&algorithm=").append(algorithm);}
        if (digits != 0) {uri.append("&digits=").append(digits);}
        if (period != 0) {uri.append("&period=").append(period);}
        return uri.toString();
    }
}
