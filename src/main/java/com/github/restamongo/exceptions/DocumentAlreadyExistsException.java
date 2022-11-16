package com.github.restamongo.exceptions;
/**
 * Runtime exception for documents that already exist within database.
 */
public class DocumentAlreadyExistsException extends RuntimeException{
    /**
     * Default constructor with generic message.
     */
    public DocumentAlreadyExistsException() {
        super("Specified document already exists.");
    }

    /**
     * Constructor with id information.
     *
     * @param id document id
     */
    public DocumentAlreadyExistsException(String id) {
        super(String.format("Document with id \"%s\" already exists.", id));
    }
}
