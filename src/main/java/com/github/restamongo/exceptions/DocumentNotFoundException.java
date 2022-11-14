package com.github.restamongo.exceptions;

/**
 * Runtime exception for documents not found within database.
 */
public class DocumentNotFoundException extends RuntimeException {
    /**
     * Default constructor with generic message.
     */
    public DocumentNotFoundException() {
        super("Specified document does not exist.");
    }

    /**
     * Constructor with id information.
     *
     * @param id document id
     */
    public DocumentNotFoundException(String id) {
        super(String.format("Document with id '%s' does not exist.", id));
    }
}