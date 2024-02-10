package com.pickpick.server.global.apiPayload.exception.handler;

import com.pickpick.server.global.apiPayload.code.BaseErrorCode;
import com.pickpick.server.global.apiPayload.exception.GeneralException;

public class NotificationHandler extends GeneralException {
    public NotificationHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

}
