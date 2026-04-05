package com.l2p.hmps.scheduler;

import com.l2p.hmps.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailScheduler {

    private final EmailService emailService;

    // Runs every day at 8 AM
//    @Scheduled(cron = "0 0 8 * * *")
//    @Scheduled(cron = "*/10 * * * * *")
    public void sendAppointmentReminders() {

        log.info("Running daily appointment reminders...");

        // ⚠️ For now just test with dummy data
        emailService.sendAppointmentReminder("sahakash17@gmail.com", "Test User");
    }
}