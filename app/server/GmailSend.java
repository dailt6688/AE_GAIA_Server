package server;

import helper.Config;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class GmailSend {

    public static void sendGmail(String subject, String gmailDestination, String content) {
        if (gmailDestination != null && gmailDestination.contains("@")) {
            try {
                sendEmailWithTSL(subject, gmailDestination, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void sendEmailWithTSL(String subject, String gmailDestination, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.GMAIL_SEND, Config.GMAIL_PASSWORD);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Config.GMAIL_SEND));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(gmailDestination));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void sendEmailWithSSL(String subject, String gmailDestination, String content) {
//        Properties props = new Properties();
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.socketFactory.port", "465");
//        props.put("mail.smtp.socketFactory.class",
//                "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "465");
//        Session session = Session.getDefaultInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(Config.GMAIL_SEND, Config.GMAIL_PASSWORD);
//                    }
//                });
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(Config.GMAIL_SEND));
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(gmailDestination));
//            message.setSubject(subject);
//            message.setText(content);
//            Transport.send(message);
////            System.out.println("Mail sent succesfully!");
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
