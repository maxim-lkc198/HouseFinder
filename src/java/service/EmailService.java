/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 * Service interface for sending emails.
 */
public interface EmailService {

    /**
     * Sends a simple email.
     *
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param htmlBody The HTML content of the email.
     */
    void sendEmail(String to, String subject, String htmlBody);
}