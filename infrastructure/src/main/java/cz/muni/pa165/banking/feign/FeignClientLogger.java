package cz.muni.pa165.banking.feign;

import feign.Logger;
import feign.Request;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FeignClientLogger extends Logger {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignClientLogger.class);

    protected void logRequest(String configKey, Logger.Level logLevel, Request request) {
        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {
            super.logRequest(configKey, logLevel, request);
        } else {
            int bodyLength = 0;
            String bodyText = null;
            if (request.body() != null) {
                bodyLength = request.body().length;
                bodyText = request.charset() != null ? new String(request.body(), request.charset()) : null;
            }

            String restApiMethodName = methodTag(configKey);
            this.log(configKey, "REST-REQUEST\n%s\n---------------------------\nHTTP-Method: %s\nURL: %s\n%s-byte body:\n%s\n-----", restApiMethodName, request.httpMethod().name(), request.url(), bodyLength, bodyText);
        }

    }

    protected Response logAndRebufferResponse(String configKey, Logger.Level logLevel, Response response, long elapsedTime) throws IOException {
        if (logLevel.ordinal() >= Level.HEADERS.ordinal()) {
            return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
        } else {
            int status = response.status();
            Request request = response.request();
            byte[] bodyData = IOUtils.toByteArray(response.body().asInputStream());
            int bodyLength = bodyData != null ? bodyData.length : 0;
            String bodyText = bodyLength > 0 && response.charset() != null ? new String(bodyData, response.charset()) : null;
            String restApiMethodName = methodTag(configKey);
            this.log(configKey, "REST-RESPONSE\n%s\n---------------------------\nHTTP-Method: %s\nURL: %s\nResponse-Status: %s\nElapsed-Time: %s ms\n%s-byte body:\n%s\n-----", restApiMethodName, request.httpMethod().name(), request.url(), status, elapsedTime, bodyLength, bodyText);
            return response.toBuilder().body(bodyData).build();
        }
    }

    protected void log(String configKey, String format, Object... args) {
        String message = String.format(format, args);
        this.logger.info(message);
    }
}