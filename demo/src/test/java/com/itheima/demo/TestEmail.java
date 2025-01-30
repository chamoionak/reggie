
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TestEmail {
    private static String content = "<!DOCTYPE html>"
            + "<html>"
            + "<head>"
            + "<title>测试邮件2</title>"
            + "<meta name=\"content-type\" content=\"text/html; charset=UTF-8\">"
            + "</head>"
            + "<body>"
            + "这是一封测试邮件~"
            + "</body>"
            + "</html>"; // 可以用HTMl语言写

    public static void main(String[] args) {
        System.out.println("测试发送邮件2");
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();

        InputStream inputStream = TestEmail.class.getClassLoader().getResourceAsStream("qqEamil.properties");
        try {
            props.load(inputStream);	//加载properties文件
            inputStream.close();

            // 使用环境属性和授权信息，创建邮件会话
            Session session = Session.getInstance(props);
            // 通过session得到transport对象
            Transport ts = session.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，授权码
            ts.connect("smtp.qq.com",props.getProperty("mail.user"), props.getProperty("mail.password"));
            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            // 设置发件人

            InternetAddress from = new InternetAddress(props.getProperty("mail.user"));
            message.setFrom(from);

            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress("xxxxxxxx@qq.com");
            message.setRecipient(RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject("测试邮件");

            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();
        } catch (MessagingException e) {
            // 邮件异常
            e.printStackTrace();
        } catch (IOException e) {
            // properties文件加载异常
            e.printStackTrace();
        }

    }
}
