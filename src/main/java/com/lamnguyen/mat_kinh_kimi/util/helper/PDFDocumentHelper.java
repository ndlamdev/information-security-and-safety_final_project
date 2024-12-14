package com.lamnguyen.mat_kinh_kimi.util.helper;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.lamnguyen.mat_kinh_kimi.domain.dto.BillDTO;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PDFDocumentHelper {
    public static void createBillFile(BillDTO billDTO, HttpServletResponse resp) throws IOException {
        System.out.println("Creating PDF");
        Document document = null;
        try {
            // Create PDF writer and document
            String path = "/home/lamhongphong/Downloads/file.pdf";
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource("DejaVuSans.ttf");
            String fontPath = "path/to/DejaVuSans.ttf"; // Replace with your font path
            if (resource == null) resp.sendRedirect("error.jsp");
            PdfFont font = null;
            if (resource != null) {
                font = PdfFontFactory.createFont(new File(resource.getFile()).getAbsolutePath(), "Identity-H");
            }
            PdfWriter writer = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(writer);
            document = new Document(pdfDoc, PageSize.A4, true);
            document.setFont(font);
            // Add Title
            Paragraph title = new Paragraph("ĐƠN HÀNG")
                    .setFontSize(20)
                    .setBold()
                    .setMinWidth(PageSize.A5.getWidth())
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            System.out.println(title.getHeight() + " " + title.getWidth());
            System.out.println("width A4" + PageSize.A4.getWidth());
            System.out.println("width A5" + PageSize.A5.getWidth());

            // Add Date and Order Number fields
            Table dateTable = new Table(new float[]{1, 1});
            dateTable.setWidth(PageSize.A5.getWidth());
            dateTable.addCell(createValueCell("Ngày"));
            dateTable.addCell(createValueCell(billDTO.getDate().toString()).setBorder(null));
            dateTable.addCell(createValueCell("Mã Đơn Hàng"));
            dateTable.addCell(createValueCell("____________________"));
            document.add(dateTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Customer Info Table
            Table customerTable = new Table(new float[]{2, 2});
            customerTable.setWidth(PageSize.A5.getWidth());
            customerTable.addCell(createLabelCell("Tên"));
            customerTable.addCell(createValueCell(billDTO.getName()));
            customerTable.addCell(createLabelCell("Địa Chỉ"));
            customerTable.addCell(createValueCell(billDTO.getAddress()));
            customerTable.addCell(createLabelCell("Số Điện Thoại"));
            customerTable.addCell(createValueCell(billDTO.getPhone()));
            customerTable.addCell(createLabelCell("E-MAIL"));
            customerTable.addCell(createValueCell(billDTO.getEmail()));
            document.add(customerTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Items Table
            Table itemsTable = new Table(new float[]{1, 4, 2, 1, 1, 1});
            itemsTable.setWidth(PageSize.A5.getWidth());
            itemsTable.addHeaderCell(createHeaderCell("STT"));
            itemsTable.addHeaderCell(createHeaderCell("TÊN SẢN PHẨM"));
            itemsTable.addHeaderCell(createHeaderCell("MẪU"));
            itemsTable.addHeaderCell(createHeaderCell("GIÁ"));
            itemsTable.addHeaderCell(createHeaderCell("SỐ LƯỢNG"));
            itemsTable.addHeaderCell(createHeaderCell("THÀNH TIỀN"));

            double total = 0;
            int freeShip = 20000;
            // Add empty rows
            for (int i = 0; i < billDTO.getProducts().size(); i++) {
                itemsTable.addCell(createValueCell(String.valueOf(i + 1)));
                itemsTable.addCell(createValueCell(billDTO.getProducts().get(i).getName()));
                itemsTable.addCell(createValueCell(billDTO.getProducts().get(i).getModel().getName()));
                itemsTable.addCell(createValueCell(String.valueOf(billDTO.getProducts().get(i).getPrice())));
                itemsTable.addCell(createValueCell(String.valueOf(billDTO.getProducts().get(i).getQuantity())));
                itemsTable.addCell(createValueCell(String.valueOf(billDTO.getProducts().get(i).getPrice() * billDTO.getProducts().get(i).getQuantity())));
                total += billDTO.getProducts().get(i).totalPrice();
                System.out.println(i);
            }

            // Add FeeShip row
            itemsTable.addCell(new Cell(8, 2).add(new Paragraph("Phí Ship").setBold()).setTextAlignment(TextAlignment.RIGHT));
            itemsTable.addCell(new Cell().add(new Paragraph(freeShip + " VND")));
            // Add Total row
            itemsTable.addCell(new Cell(8, 2).add(new Paragraph("Tổng").setBold()).setTextAlignment(TextAlignment.RIGHT));
            itemsTable.addCell(new Cell().add(new Paragraph((total + freeShip) + " VND")));

            document.add(itemsTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Notes Section
            Paragraph notes = new Paragraph("NOTES:").setBold().setFontSize(12);
            document.add(notes);
            document.add(new Paragraph("____________________________________________________________"));
            document.add(new Paragraph("____________________________________________________________"));

            System.out.println("PDF generated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        } finally {
            document.close();
        }
    }

    // Helper method to create header cells
    private static Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);
    }

    // Helper method to create label cells
    private static Cell createLabelCell(String text) {
        return new Cell().add(new Paragraph(text).setBold()).setBorder(null).setPadding(5);
    }

    // Helper method to create value cells
    private static Cell createValueCell(String text) {
        return new Cell().add(new Paragraph(text)).setBorder(null).setPadding(5);
    }

    // Helper method to create empty cells for the items table
    private static Cell createEmptyCell() {
        return new Cell().add(new Paragraph("")).setHeight(20).setPadding(5);
    }
}
