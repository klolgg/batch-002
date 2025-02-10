package site.klol.batch002.riot.exception;

import site.klol.batch002.common.exception.NoSkipException;

public class RiotKeyNotFoundException extends NoSkipException {
    public RiotKeyNotFoundException() {
    }

    public RiotKeyNotFoundException(String message) {
        super(message);
    }

    public RiotKeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RiotKeyNotFoundException(Throwable cause) {
        super(cause);
    }

    public RiotKeyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
