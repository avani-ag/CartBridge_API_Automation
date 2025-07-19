package core;

public enum StatusCodes {

    SUCCESS(200,"Successfully executed"),
    CREATED(201,"Successfully created"),
    NO_CONTENT(204,"Successfully executed, but with no content"),
    BAD_REQUEST(400,"Bad Request"),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403,"Forbidden"),
    NOT_FOUND(404,"Not found"),

    SERVER_ERROR(500,"Server Error");

    int statusCode;
    String description;

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }


    StatusCodes(int statusCode,String description)
    {
        this.statusCode=statusCode;
        this.description=description;
    }


}
