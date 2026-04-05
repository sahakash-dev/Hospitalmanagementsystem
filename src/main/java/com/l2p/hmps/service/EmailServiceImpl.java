package com.l2p.hmps.service;

import com.l2p.hmps.exception.EmailSendingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendAppointmentConfirmation(String toEmail, String patientName) {
        validateEmail(toEmail);

        String html = processTemplate("appointment-confirmation", "name", patientName);
        sendEmail(toEmail, "Appointment Confirmed", html);
    }

    @Override
    public void sendLabResultAlert(String toEmail, boolean isCritical) {
        validateEmail(toEmail);

        Context context = new Context();
        context.setVariable("critical", isCritical);

        String html = processTemplate("lab-result-alert", context);

        String subject = isCritical ? "URGENT: Lab Result" : "Lab Result Ready";
        sendEmail(toEmail, subject, html);
    }

    @Override
    public void sendAppointmentReminder(String toEmail, String patientName) {
        validateEmail(toEmail);

        String html = processTemplate("appointment-reminder", "name", patientName);
        sendEmail(toEmail, "Appointment Reminder", html);
    }

    @Override
    public void sendBillReminder(String toEmail, String patientName) {
        validateEmail(toEmail);

        String html = processTemplate("bill-reminder", "name", patientName);
        sendEmail(toEmail, "Bill Reminder", html);
    }

    private String processTemplate(String template, String key, String value) {
        try {
            Context context = new Context();
            context.setVariable(key, value);
            return templateEngine.process(template, context);
        } catch (Exception e) {
            log.error("Template processing failed: {}", template, e);
            throw new EmailSendingException("Failed to process email template: " + template);
        }
    }

    private String processTemplate(String template, Context context) {
        try {
            return templateEngine.process(template, context);
        } catch (Exception e) {
            log.error("Template processing failed: {}", template, e);
            throw new EmailSendingException("Failed to process email template: " + template);
        }
    }

    private void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);

            log.info("Email sent successfully to {}", to);

        } catch (Exception e) {
            log.error("Email sending failed to {}", to, e);
            throw new EmailSendingException("Failed to send email to: " + to);
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new EmailSendingException("Email cannot be null or empty");
        }
    }
}