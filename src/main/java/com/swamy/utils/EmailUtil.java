package com.swamy.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender javaMailSender;

	private String result;

	public String sendEmail(String subject, String receiver, String body) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
			messageHelper.setSubject(subject);
			messageHelper.setTo(receiver);
			messageHelper.setText(body);

			FileSystemResource file = new FileSystemResource(
					new File(AppConstants.FILE_NAME));

			messageHelper.addAttachment(file.getFilename(), file);

			javaMailSender.send(messageHelper.getMimeMessage());

			result = AppConstants.EMAIL_SENT;

		} catch (Exception e) {
			e.printStackTrace();
			result = AppConstants.EMAIL_ERROR;
		}

		return result;
	}
}
