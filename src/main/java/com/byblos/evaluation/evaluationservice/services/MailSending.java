package com.byblos.evaluation.evaluationservice.services;

import javax.mail.MessagingException;

public interface MailSending {
    
    void send(String to, String subject, String body) throws MessagingException;
    
    
    
}
