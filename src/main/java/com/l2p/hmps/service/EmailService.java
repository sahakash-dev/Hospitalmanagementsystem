package com.l2p.hmps.service;

public interface EmailService {

    void sendAppointmentConfirmation(String toEmail, String patientName);

    void sendLabResultAlert(String toEmail, boolean isCritical);

    void sendAppointmentReminder(String toEmail, String patientName);

    void sendBillReminder(String toEmail, String patientName);
}