package com.github.restamongo.exceptions;

/**
 * Runtime exception for invalid document contents that cannot be created within database.
 */
public class InvalidDocumentContentException extends RuntimeException {
    /**
     * Default constructor with generic message.
     */
    public InvalidDocumentContentException() {
        super("Specified document content is invalid.");
    }

    /**
     * Constructor with reason.
     *
     * @param reason custom reason
     */
    public InvalidDocumentContentException(String reason) {
        super(String.format("Specified document content is invalid due to: %s.", reason));
    }
}
