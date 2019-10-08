package top.neospot.cloud.common.exceptions;

public class CloudBizException extends RuntimeException {

    public CloudBizException() {
        this("something happens at cloud app, please contact the admin for more error info");
    }

    public CloudBizException(String message) {
        super(message);
    }
}
