package com.trinetra.mailer;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.trinetra.exception.CustomException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    private TaskExecutor taskExecutor;
    
    public MailServiceImpl() {
    	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    	executor.setCorePoolSize(3);
    	executor.setMaxPoolSize(3);
    	executor.setThreadNamePrefix("email-sender-thread");
    	executor.initialize();
    	this.taskExecutor = executor;
    }

    public void sendEmail(Mail mail) {

        taskExecutor.execute(new Runnable() {

            public void run() {
                try {
               sendMailSimple(mail);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CustomException("Failed to send email to: " + mail.getMailTo() + " reason: "+e.getMessage());

                }
            }
        });

    }
    
	@Override
	public void sendAlertConfigEmailWithAttachment(Mail mail, String fileName) throws CustomException {		
		
		Future<?> future = 
		((ThreadPoolTaskExecutor) taskExecutor).submit(new Runnable() {

			public void run() {
				try {
					sendMailWithAttachment(mail, fileName);
				} catch (Exception e) {
					e.printStackTrace();
					throw new CustomException(
							"Failed to send email to: " + mail.getMailTo() + " reason: " + e.getMessage());
				}
			}
		});
		
		try {
			future.get();
		} catch(InterruptedException | ExecutionException e) {
			Throwable cause = e.getCause();
			if(cause instanceof CustomException) {
				throw (CustomException) cause;
			}
			else {
				throw new CustomException("Failed to send email to: " + mail.getMailTo());
			}	
				
		}
	}

    protected void sendMailWithAttachment(Mail mail, String fileName) throws CustomException{
    	MimeMessage mimeMessage = mailSender.createMimeMessage();
    	
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), ""));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            
            System.out.println("file name = "+ fileName);
            File file = new File(fileName);
            if(!file.isFile()) {
            	throw new CustomException("PDF File Not Found.");
            }
            DataSource source = new FileDataSource(fileName);
            mimeMessageHelper.addAttachment(fileName, source);
            
            mailSender.send(mimeMessageHelper.getMimeMessage());


        } catch (IOException e) {
			e.printStackTrace();
			throw new CustomException("Failed to send email to: " + mail.getMailTo() + " reason: "+e.getMessage());
		} catch (MessagingException e) {
            e.printStackTrace();
            throw new CustomException("Failed to send email to: " + mail.getMailTo() + " reason: "+e.getMessage());
        } catch(CustomException e) {
        	e.printStackTrace();
        	throw new CustomException("PDF File Not Found.");
        }
		
	}

	public void sendMailSimple(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), ""));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent());
            mailSender.send(mimeMessageHelper.getMimeMessage());


        } catch (MessagingException e) {
            e.printStackTrace();
            throw new CustomException("Failed to send email to: " + mail.getMailTo() + " reason: "+e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new CustomException("Failed to send email to: " + mail.getMailTo() + " reason: "+e.getMessage());
        }
    }


	


}