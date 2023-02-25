package com.trinetra.mailer;

import com.trinetra.exception.CustomException;

public interface MailService {
    public void sendEmail(Mail mail);
    public void sendAlertConfigEmailWithAttachment(Mail mail, String fileName) throws CustomException;
}
