package cn.itrip.controller;



import org.springframework.stereotype.Component;

import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class sendMail {
        private static String e="mxy15033134984@126.com";
        private static String auth="VMZKBOWMCRCXBTBL";
        private static Properties pro=new Properties();
        static{
            pro.setProperty("mail.host","smtp.126.com");
            pro.setProperty("mail.smtp.auth","true");
        }
        private static Session getSession(){
                Authenticator authenticator = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(e,auth);
                    }
                };
            return Session.getDefaultInstance(pro,authenticator);
        }
        public  static void send(String to,String title,String text) throws MessagingException {
            Session s=getSession();
            Message message=new MimeMessage(s);
            message.setFrom(new InternetAddress(e));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
            message.setSubject(title);
            message.setContent("您的验证码是:<h4><a>"+text+"</a></h3>", "text/html;charset=UTF-8");

            Transport.send(message);
        }

    public static void main(String[] args) throws MessagingException {
        sendMail.send("mxy15033134984@163.com","验证","1234");
    }

}
