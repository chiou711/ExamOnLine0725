package examDo;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(
    urlPatterns={"/examDo/mail.do"},
    initParams={
        @WebInitParam(name = "mailHost", value = "smtp.gmail.com"),
        @WebInitParam(name = "mailPort", value = "465"),
        @WebInitParam(name = "username", value = "chiou711"),
        @WebInitParam(name = "password", value = "password?")
    }
)
public class MailServlet extends HttpServlet {
    private String mailHost;
    private String mailPort;
    private String username;
    private String password;
    private Properties props;

	@Override
    public void init() throws ServletException {
	    mailHost = getServletConfig().getInitParameter("mailHost");
	    mailPort = getServletConfig().getInitParameter("mailPort");
	    username = getServletConfig().getInitParameter("username");
	    password = getServletConfig().getInitParameter("password");
	    
        props = new Properties();
        props.put("mail.smtp.host", mailHost);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", mailPort);
        props.setProperty("mail.smtp.socketFactory.port", mailPort);
        props.setProperty("mail.smtp.auth", "true");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String text = request.getParameter("text");
        
        try {
            Message message = getMessage(from, to, subject, text);
            Transport.send(message);
            response.getWriter().println("郵件傳送成功!");
        } catch (Exception e) {
            throw new ServletException(e);
        } 
	}

    private Message getMessage(String from, String to, String subject,
            String text) throws MessagingException, AddressException {
        Session session = Session.getDefaultInstance(props, new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }}
        );
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setSentDate(new Date());
        message.setText(text);
        return message;
    }
}
