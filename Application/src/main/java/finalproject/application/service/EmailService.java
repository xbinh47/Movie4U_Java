package finalproject.application.service;

import finalproject.application.entity.Ticket;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface EmailService {
    void sendEmail(String to, String subject, String text) throws MessagingException;
    void sendTicket(String to, String subject, Ticket ticket) throws URISyntaxException, IOException;
}
