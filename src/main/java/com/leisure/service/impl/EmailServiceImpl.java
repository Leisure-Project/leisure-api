package com.leisure.service.impl;

import com.leisure.config.exception.ResourceNotFoundException;
import com.leisure.entity.Client;
import com.leisure.repository.ClientRepository;
import com.leisure.service.EmailService;
import com.leisure.util.Mail;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Optional;

@Component
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private Mail mail;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(Long clientId, String clientEmail) throws Exception {
        String html = this.getEmailTemplate(clientId);
        Message message1 = new MimeMessage(this.mail.getJavaMailSender());
        message1.setContent(html,"text/html; charset=utf-8");
        message1.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(clientEmail));
        message1.setSubject("Mensaje de prueba");
        emailSender.send((MimeMessage) message1);
    }

    @Override
    public String getEmailTemplate(Long clientId) throws Exception {
        Optional<Client> optionalClient = this.clientRepository.findById(clientId);
        if(optionalClient.isEmpty()){
            throw new ResourceNotFoundException("Cliente", clientId);
        }
        Client client = optionalClient.get();
        String html = "html/new-client.html";
        VelocityContext context = new VelocityContext();
        context.put("firstname", client.getName());
        context.put("lastname", client.getLastName());
        context.put("password", client.getPassword());

        String content = Optional.ofNullable(html)
                .map(url -> url + "")
                .map(template -> {
                    int i = 0;
                    return getContentMail(context, template);
                })
                .orElse("");
        return content;
    }
    String getContentMail(VelocityContext context, String template) {
        VelocityEngine velocity = getVelocityEngine();
        Template t = velocity.getTemplate(template);
        StringWriter w = new StringWriter();
        t.merge(context, w);
        return w.toString();
    }
    VelocityEngine getVelocityEngine() {
        VelocityEngine velocity = new VelocityEngine();
        velocity.setProperty(Velocity.RESOURCE_LOADER, "classpath");
        velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocity.setProperty("input.encoding", "UTF-8");
        velocity.init();
        return velocity;
    }
}
