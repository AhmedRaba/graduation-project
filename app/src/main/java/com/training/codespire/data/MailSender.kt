package com.training.codespire.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailSender {

    suspend fun sendEmail(from: String, to: String, subject: String, message: String) {
        withContext(Dispatchers.IO) {

            val props = Properties()
            props["mail.smtp.host"] = "smtp.mailersend.net"
            props["mail.smtp.port"] = 587
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(from, "oNaPUkxE9rcLLAE1")
                }
            })

            try {
                val emailMessage = MimeMessage(session)
                emailMessage.setFrom(InternetAddress(from))
                emailMessage.addRecipient(Message.RecipientType.TO, InternetAddress(to))
                emailMessage.subject = subject
                emailMessage.setText(message)

                Transport.send(emailMessage)
            } catch (e: Exception) {
                // Handle exceptions
                Log.e("MailSender", "Failed to send email: ${e.message}", e)
            }
        }
    }
}
