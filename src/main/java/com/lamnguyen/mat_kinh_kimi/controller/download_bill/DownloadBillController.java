package com.lamnguyen.mat_kinh_kimi.controller.download_bill;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.service.BillService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DownloadBill", value = "/download-bill")
public class DownloadBillController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        // Set response content type
        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=order_form.pdf");
        HttpSession session = req.getSession();

        String action = req.getParameter("action");

        User user = (User) session.getAttribute("user");
        BillService billService = (BillService) session.getAttribute("bill");
        String userName = req.getParameter("full-name").trim();
        String email = req.getParameter("email").trim();
        String phoneNumber = req.getParameter("phone-number").trim();
        String province = req.getParameter("provinces").trim();
        String district = req.getParameter("districts").trim();
        String ward = req.getParameter("wards").trim();

        String fullAddress = req.getParameter("full-address").trim();
        boolean transfer = req.getParameter("pay-option").equals("transfer") ? true : false;

        // Create PDF writer and document
        PdfWriter writer = new PdfWriter(resp.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        try {
            // Add Title
            Paragraph title = new Paragraph("ORDER FORM")
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Add Date and Order Number fields
            Table dateTable = new Table(new float[]{1, 1});
            dateTable.setWidth(100);
            dateTable.addCell(new Cell().add(new Paragraph("DATE").setBold()).setBorder(null));
            dateTable.addCell(new Cell().add(new Paragraph("ORDER ID").setBold()).setBorder(null));
            dateTable.addCell(new Cell().add(new Paragraph("____________________")).setBorder(null));
            dateTable.addCell(new Cell().add(new Paragraph("____________________")).setBorder(null));
            document.add(dateTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Customer Info Table
            Table customerTable = new Table(new float[]{1, 3});
            customerTable.setWidth(100);
            customerTable.addCell(createLabelCell("NAME"));
            customerTable.addCell(createValueCell("___________________________"));
            customerTable.addCell(createLabelCell("ADDRESS"));
            customerTable.addCell(createValueCell("___________________________"));
            customerTable.addCell(createLabelCell("PHONE"));
            customerTable.addCell(createValueCell("___________________________"));
            customerTable.addCell(createLabelCell("E-MAIL"));
            customerTable.addCell(createValueCell("___________________________"));
            document.add(customerTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Items Table
            Table itemsTable = new Table(new float[]{2, 4, 1, 1, 1});
            itemsTable.setWidth(100);
            itemsTable.addHeaderCell(createHeaderCell("ITEM"));
            itemsTable.addHeaderCell(createHeaderCell("DESCRIPTION"));
            itemsTable.addHeaderCell(createHeaderCell("QTY"));
            itemsTable.addHeaderCell(createHeaderCell("PRICE"));
            itemsTable.addHeaderCell(createHeaderCell("AMOUNT"));

            // Add empty rows
            for (int i = 0; i < 10; i++) {
                itemsTable.addCell(createEmptyCell());
                itemsTable.addCell(createEmptyCell());
                itemsTable.addCell(createEmptyCell());
                itemsTable.addCell(createEmptyCell());
                itemsTable.addCell(createEmptyCell());
            }

            // Add Total row
            itemsTable.addCell(new Cell(1, 4).add(new Paragraph("TOTAL").setBold()).setTextAlignment(TextAlignment.RIGHT));
            itemsTable.addCell(new Cell().add(new Paragraph("_________")));
            document.add(itemsTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Notes Section
            Paragraph notes = new Paragraph("NOTES:").setBold().setFontSize(12);
            document.add(notes);
            document.add(new Paragraph("____________________________________________________________"));
            document.add(new Paragraph("____________________________________________________________"));

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/error.jsp");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        } finally {
            document.close();
        }
    }
    // Helper method to create header cells
    private Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);
    }

    // Helper method to create label cells
    private Cell createLabelCell(String text) {
        return new Cell().add(new Paragraph(text).setBold()).setBorder(null);
    }

    // Helper method to create value cells
    private Cell createValueCell(String text) {
        return new Cell().add(new Paragraph(text)).setBorder(null);
    }

    // Helper method to create empty cells for the items table
    private Cell createEmptyCell() {
        return new Cell().add(new Paragraph("")).setHeight(20);
    }
}
