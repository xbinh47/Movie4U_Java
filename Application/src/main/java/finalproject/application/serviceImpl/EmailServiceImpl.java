package finalproject.application.serviceImpl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import finalproject.application.dto.TicketDTO;
import finalproject.application.entity.Ticket;
import finalproject.application.repository.TicketRepository;
import finalproject.application.service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfWriter;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;
    private final TicketRepository ticketRepository;
    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress("movie4U@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);

        message.setContent(text, "text/html; charset=utf-8");

        javaMailSender.send(message);
    }

    @Override
    public void sendTicket(String to, String subject, Ticket ticket) throws URISyntaxException, IOException, MessagingException {
        ClassLoader classLoader = getClass().getClassLoader();
        URI uri = classLoader.getResource("static").toURI();
        Path ticketHtml = Paths.get(uri).resolve("ticket.html");

        var fileInputStream = new FileInputStream(ticketHtml.toFile());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String htmlContent = "";
        String line = bufferedReader.readLine();
        while (line != null) {
            htmlContent += line;
            line = bufferedReader.readLine();
        }
        bufferedReader.close();



        // Define the parameters to replace in the HTML file
        List<Object[]> tickets = ticketRepository.getTicketById(ticket.getId());
        TicketDTO ticketDTO = TicketDTO.getInstance().convertToObject(tickets.get(0));

        String ticket_id = ticketDTO.getTicketId().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");;
        LocalDateTime date = LocalDateTime.parse(ticketDTO.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        String formatDate = date.format(formatter);
        String movie_name = ticketDTO.getMovieName();
        String start_time = ticketDTO.getStartTime().substring(0,5);
        String seat_names = ticketDTO.getSeatNames();
        String theater_name = ticketDTO.getTheatreName();
        String room_name = ticketDTO.getRoomName();

        Map<String, String> params = new HashMap<>();
        params.put("ticket_id", ticket_id);
        params.put("movie_name", movie_name);
        params.put("date", formatDate);
        params.put("start_time", start_time);
        params.put("seat_names", seat_names);
        params.put("theatre_name", theater_name);
        params.put("room_name", room_name);

        // Replace the parameters in the HTML file
        String modifiedHtml = htmlContent;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue();
            modifiedHtml = modifiedHtml.replace(placeholder, value);
        }
        // Convert the modified HTML content to a PDF file
        Resource resource = resourceLoader.getResource("classpath:/static/pdf");
        String resourcePath = resource.getFile().getAbsolutePath();
        String ticketName = "ticket" + ticket.getId() + ".pdf";
        String pdfFilePath = resourcePath + "\\" + ticketName;
        FileOutputStream fileOutputStream = new FileOutputStream(pdfFilePath);
        PdfWriter writer = new PdfWriter(fileOutputStream);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.setDefaultPageSize(PageSize.A4.rotate());
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri(resourcePath);
        HtmlConverter.convertToPdf(modifiedHtml, pdfDocument, converterProperties);

        // Close the output stream
        fileOutputStream.close();

        //Send mail with pdf file
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(new InternetAddress("movie4U@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);

        // create the message body and add it to a multipart message
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<h2>Thank for purchase</h2>", "text/html; charset=utf-8");

        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // add the attachment to the multipart message
        MimeBodyPart attachmentPart = new MimeBodyPart();
        DataSource source = new FileDataSource(pdfFilePath);
        attachmentPart.setDataHandler(new DataHandler(source));
        attachmentPart.setFileName(ticketName);

        multipart.addBodyPart(attachmentPart);

        // set the message content to be the multipart message
        message.setContent(multipart);

        javaMailSender.send(message);

        System.out.println("PDF file created successfully.");
    }
}
