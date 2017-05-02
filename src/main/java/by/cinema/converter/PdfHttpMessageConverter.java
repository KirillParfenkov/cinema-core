package by.cinema.converter;

import by.cinema.bean.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 5/2/2017.
 */
public class PdfHttpMessageConverter implements HttpMessageConverter<List<Ticket>> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return true;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.APPLICATION_PDF);
    }

    @Override
    public List<Ticket> read(Class<? extends List<Ticket>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    public void write(List<Ticket> tickets, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, outputMessage.getBody());
            document.open();

            for(Ticket ticket: tickets) {
                document.add(new Paragraph(ticket.toString()));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

    }
}
