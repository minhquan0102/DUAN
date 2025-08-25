package web.java6.shop.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Cấu hình Gmail SMTP
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        // Thay bằng email và App Password của bạn
        mailSender.setUsername("quanvmpd10099@fpt.edu.vn");
        mailSender.setPassword("pvkt bwes yrvg mepo");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // bật log gửi mail

        return mailSender;
    }
}
