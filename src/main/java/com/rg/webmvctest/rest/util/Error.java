package com.rg.webmvctest.rest.util;

import java.util.Objects;

public class Error {

    public static final char PATH_SEPARATOR = '.';

    private final String code;

    private final String message;

    private final String details;

    private final String path;

    private final String userMessage;

    private Error(String code, String message, String details, String path, String userMessage) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.path = path;
        this.userMessage = userMessage;
    }

    public static Builder error() {
        return new Builder();
    }

    public static Builder error(String code) {
        return new Builder().withCode(code);
    }

    public static Builder like(Error other) {
        return new Builder()
                .withCode(other.getCode())
                .withMessage(other.getMessage())
                .withDetails(other.getDetails())
                .withPath(other.getPath())
                .withUserMessage(other.getUserMessage());
    }

    /**
     * @return error code, this (and path if present) must uniquely identify error
     */
    public String getCode() {
        return code;
    }

    /**
     * @return message for technical interpretation (i.e. message code)
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return additional details, might contain stack trace
     */
    public String getDetails() {
        return details;
    }

    /**
     * @return path to field that contains error
     */
    public String getPath() {
        return path;
    }

    /**
     * @return user friendly translated message
     */
    public String getUserMessage() {
        return userMessage;
    }

    public Error cloneWithPrefixedPath(String prefix) {
        return Error.like(this).addPrefixToPath(prefix).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Error)) {
            return false;
        }

        Error other = (Error) obj;
        return Objects.equals(code, other.code)
                && Objects.equals(path, other.path)
                && Objects.equals(message, other.message)
                && Objects.equals(details, other.details)
                && Objects.equals(userMessage, other.userMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, path, message, details, userMessage);
    }

    @Override
    public String toString() {
        return String.format("Error{code='%s', message='%s', details='%s', path='%s', userMessage='%s'}", code, message, details, path, userMessage);
    }

    public static final class Builder {

        private static final String DEFAULT_CODE = "ERROR";
        private static final String DEFAULT_MESSAGE = "An error has occurred";

        private String code = DEFAULT_CODE;

        private String message = DEFAULT_MESSAGE;

        private String details;

        private String path;

        private String userMessage;

        private Builder() {
        }

        public Error build() {
            return new Error(code, message, details, path, userMessage);
        }

        public Builder fromException(Throwable exception, ExceptionMappingMode mode) {

            if (mode == ExceptionMappingMode.INTERNAL_DETAILS ||
                    mode == ExceptionMappingMode.INTERNAL_DETAILS_WITH_STACKTRACE) {
                this.message = exception.getMessage();
            }

            if (mode == ExceptionMappingMode.INTERNAL_DETAILS) {
                this.details = exception.getClass().getSimpleName();
            }
            if (mode == ExceptionMappingMode.INTERNAL_DETAILS_WITH_STACKTRACE) {
                this.details = StackTraceDumper.dumpStackTrace(exception);
            }
            return this;
        }

        public Builder fromException(Exception exception) {
            return fromException(exception, ExceptionMappingMode.NO_INTERNAL_DETAILS);
        }

        /**
         * Please use {@link #fromException(Throwable, ExceptionMappingMode)} instead.
         *
         * @deprecated since 1.6.0
         */
        @Deprecated
        public Builder fromException(Throwable exception, boolean withStackTrace) {
            ExceptionMappingMode mode = withStackTrace ?
                    ExceptionMappingMode.INTERNAL_DETAILS_WITH_STACKTRACE : ExceptionMappingMode.NO_INTERNAL_DETAILS;
            return fromException(exception, mode);
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withDetails(String details) {
            this.details = details;
            return this;
        }

        public Builder withUserMessage(String userMessage) {
            this.userMessage = userMessage;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public Builder addPrefixToPath(String prefix) {
            if (this.path != null) {
                this.path = new StringBuilder(prefix).append(Error.PATH_SEPARATOR).append(this.path).toString();
            } else {
                this.path = prefix;
            }
            return this;
        }
    }
}