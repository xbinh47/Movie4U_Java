package finalproject.application.serviceImpl;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import finalproject.application.dto.TicketDTO;
import finalproject.application.entity.Ticket;
import finalproject.application.repository.SeatRepository;
import finalproject.application.repository.TicketRepository;
import finalproject.application.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document;
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
    private final SeatRepository seatRepository;
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
    public void sendTicket(String to, String subject, Ticket ticket) throws URISyntaxException, IOException {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime date = LocalDateTime.parse(ticketDTO.getDate(), formatter);
        String dayOfWeek = date.getDayOfWeek().toString();
        String month = date.getMonth().toString();
        String year = String.valueOf(date.getYear());
        String movie_name = ticketDTO.getMovieName();
        String start_time = ticketDTO.getStartTime();
        String end_time = ticketDTO.getEndTime();
        String seat_names = ticketDTO.getSeatNames();
        String theater_name = ticketDTO.getTheatreName();

        Map<String, String> params = new HashMap<>();
        params.put("ticket_id", ticket_id);
        params.put("dayOfWeek", dayOfWeek);
        params.put("month", month);
        params.put("year", year);
        params.put("movie_name", movie_name);
        params.put("start_time", start_time);
        params.put("end_time", end_time);
        params.put("seat_names", seat_names);
        params.put("theatre_name", theater_name);


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
        Document document = new Document(pdfDocument);
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri(resourcePath);
        HtmlConverter.convertToPdf(modifiedHtml, pdfDocument, converterProperties);

        // pdfHTML specific code


        // Close the output stream
        fileOutputStream.close();

        System.out.println("PDF file created successfully.");
    }
}
