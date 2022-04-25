package com.filipiakp.warehousespring.controller;

import com.filipiakp.warehousespring.entities.Order;
import com.filipiakp.warehousespring.model.OrderRepository;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Slf4j
@Controller
public class InvoiceController {

  @Autowired private TemplateEngine templateEngine;

  @Autowired private OrderRepository orderRepository;

  @RequestMapping(
      value = "/invoice/{orderId}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_PDF_VALUE)
  public void downloadInvoice(@PathVariable long orderId, HttpServletResponse response) {
    try {
      Optional<Order> orderOptional = orderRepository.findById(orderId);
      Order order = orderOptional.get();
      Context context = getOrderContext(order);
      String html = parseThymeleafTemplate("invoiceTemplate", context);
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/pdf");
      String disposition =
          new StringBuilder()
              .append("inline; filename=\"FV")
              .append(
                  new SimpleDateFormat("yyyy-MM-dd-").format(new Date(System.currentTimeMillis())))
              .append(orderId)
              .append(".pdf\"")
              .toString();
      response.setHeader("content-disposition", disposition);
      writeToStreamPdfFromHtml(response.getOutputStream(), html);
      response.flushBuffer();
    } catch (IOException | DocumentException ex) {
      log.error(
          "Error writing file to output stream. OrderId was %d. %s", orderId, ex.getMessage());
      throw new RuntimeException("IOError writing file to output stream");
    }
  }

  private Context getOrderContext(Order order) {
    Context context = new Context();
    context.setVariable("order", order);
    context.setVariable(
        "summaryNet",
        order.getProductsList().stream()
            .mapToDouble(p -> p.getProduct().getPrice() * p.getQuantity())
            .sum());
    context.setVariable(
        "summaryGross",
        order.getProductsList().stream()
            .mapToDouble(p -> p.getProduct().getPrice() * 1.23 * p.getQuantity())
            .sum());
    context.setVariable(
        "summaryTax",
        order.getProductsList().stream()
            .mapToDouble(p -> p.getProduct().getPrice() * 0.23 * p.getQuantity())
            .sum());
    context.setVariable("generatedDate", new Date(System.currentTimeMillis()));
    context.setLocale(new Locale("pl"));
    return context;
  }

  public String parseThymeleafTemplate(String template, Context context) {
    return templateEngine.process(template, context);
  }

  public void writeToStreamPdfFromHtml(OutputStream outputStream, String html)
      throws IOException, DocumentException {
    ITextRenderer renderer = new ITextRenderer();

    String fontpath =
        this.getClass().getClassLoader().getResource("static/font/Arial.ttf").getPath();
    renderer.getFontResolver().addFont(fontpath, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
    renderer.setDocumentFromString(html);
    renderer.layout();
    renderer.createPDF(outputStream);
  }
}
