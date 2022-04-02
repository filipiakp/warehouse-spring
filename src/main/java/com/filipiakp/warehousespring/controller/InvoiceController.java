package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Order;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Controller
public class InvoiceController {

    @RequestMapping(value = "/dinvoice/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadInvoice(@PathVariable long orderId, HttpServletResponse response) {
        try {
            // get your file as InputStream
            File file = createPDFInvoice(new Order());
            InputStream is = new FileInputStream(file);
            // copy it to response's OutputStream
            FileCopyUtils.copy(is, response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.flushBuffer();
            file.delete();
        } catch (IOException ex) {
            System.out.printf("Error writing file to output stream. OrderId was '{0}'. {1}", orderId, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @RequestMapping(value = "/invoice/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadStuff(@PathVariable long orderId)
            throws IOException {
        File file = createPDFInvoice(new Order());
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        respHeaders.setContentDispositionFormData("attachment", file.getName());

        return new ResponseEntity<>(
                new FileSystemResource(file), respHeaders, HttpStatus.OK
        );
    }

    private File createPDFInvoice(Order order) {
        final String FILENAME = "invoices/"+UUID.randomUUID()+"invoice.pdf";
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream;
        try {
            contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.beginText();
            contentStream.newLine();
            contentStream.showText("Hello World");
            contentStream.endText();
            contentStream.close();

            document.save(FILENAME);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(FILENAME);
    }
}
