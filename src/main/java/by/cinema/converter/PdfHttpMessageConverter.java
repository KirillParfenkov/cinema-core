package by.cinema.converter;

import by.cinema.bean.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 5/2/2017.
 */
public class PdfHttpMessageConverter implements HttpMessageConverter<List<Ticket>> {

    private static int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;

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

        outputMessage.getHeaders().set("Content-Type", MediaType.APPLICATION_PDF_VALUE);

        Document document = new Document(PageSize.A4);
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE);

            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);

            document.open();
            document.newPage();
            document.add(new Paragraph("PDF Output"));
            for(Ticket ticket: tickets) {
                document.add(new Paragraph(ticket.toString()));
            }
            document.close();

            outputMessage.getHeaders().set("Content-Length", String.valueOf(baos.size()));
            OutputStream out = outputMessage.getBody();
            baos.writeTo(out);
            out.flush();

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
