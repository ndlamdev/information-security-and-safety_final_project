package com.lamnguyen.mat_kinh_kimi.util.helper;

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

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DocumentHelper {
    public static String createBillFilePDF(BillDTO billDTO, HttpServletRequest request, boolean copy) throws IOException {
        String uploadDir = request.getServletContext().getRealPath("doc/bills/");
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);
        String desFile = "bill_" + billDTO.getId() + ".pdf";

        Document document = null;
        try {
            // Create PDF writer and document
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource("DejaVuSans.ttf");
            if (resource == null) {
                return null;
            }
            PdfFont font = PdfFontFactory.createFont(new File(resource.getFile()).getAbsolutePath(), "Identity-H");
            PdfWriter writer = new PdfWriter(copy ? uploadPath.resolve(desFile).toString() : desFile);
            PdfDocument pdfDoc = new PdfDocument(writer);
            document = new Document(pdfDoc, PageSize.A4, true);
            document.setFont(font);
            // Add Title
            Paragraph title = new Paragraph("ĐƠN HÀNG")
                    .setFontSize(20)
                    .setBold()
                    .setMinWidth(525f)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            // Add Date and Order Number fields
            Table dateTable = new Table(new float[]{1, 1});
            dateTable.setWidth(525f);
            dateTable.addCell(createValueCell("Ngày"));
            dateTable.addCell(createValueCell(dateTimeVietnamese(billDTO.getDate())));
            dateTable.addCell(createValueCell("Mã Đơn Hàng"));
            dateTable.addCell(createValueCell(String.valueOf(billDTO.getId())));
            document.add(dateTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Customer Info Table
            Table customerTable = new Table(new float[]{2, 2});
            customerTable.setWidth(525f);
            customerTable.addCell(createLabelCell("Tên"));
            customerTable.addCell(createValueCell(billDTO.getName()));
            customerTable.addCell(createLabelCell("Địa Chỉ"));
            customerTable.addCell(createValueCell(billDTO.getAddress()));
            customerTable.addCell(createLabelCell("Số Điện Thoại"));
            customerTable.addCell(createValueCell(billDTO.getPhone()));
            customerTable.addCell(createLabelCell("E-MAIL"));
            customerTable.addCell(createValueCell(billDTO.getEmail()));
            customerTable.addCell(createLabelCell("THANH TOÁN"));
            customerTable.addCell(createValueCell(billDTO.getPayment()));
            document.add(customerTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Items Table
            Table itemsTable = new Table(new float[]{1, 4, 2, 1, 1, 1});
            itemsTable.setWidth(525f);
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
                var product = billDTO.getProducts().get(i);
                itemsTable.addCell(createValueCell(String.valueOf(i + 1)));
                itemsTable.addCell(createValueCell(product.getName()));
                itemsTable.addCell(createValueCell(product.getModel().getName()));
                itemsTable.addCell(createValueCell(currentVietnamese(product.hasDiscount() ? product.getDiscount() : product.getPrice())));
                itemsTable.addCell(createValueCell(String.valueOf(product.getQuantity())));
                itemsTable.addCell(createValueCell(currentVietnamese(product.totalPrice())));
                total += product.totalPrice();
            }

            // Add FeeShip row
            itemsTable.addCell(new Cell(8, 2).add(new Paragraph("Phí Ship").setBold()).setTextAlignment(TextAlignment.CENTER));
            itemsTable.addCell(new Cell().add(new Paragraph(currentVietnamese(freeShip))));
            // Add Total row
            itemsTable.addCell(new Cell(8, 2).add(new Paragraph("Tổng").setBold()).setTextAlignment(TextAlignment.CENTER));
            itemsTable.addCell(new Cell().add(new Paragraph(currentVietnamese(total + freeShip))));

            document.add(itemsTable);

            document.add(new Paragraph("\n")); // Spacer

            // Add Notes Section
            Paragraph notes = new Paragraph("NOTES:").setBold().setFontSize(12);
            document.add(notes);
            document.add(new Paragraph("____________________________________________________________"));
            document.add(new Paragraph("____________________________________________________________"));

        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            document.close();
        }
        return desFile;
    }

    public static String createBillFilePDF(BillDTO billDTO, HttpServletRequest request) throws IOException {
        return createBillFilePDF(billDTO, request, true);
    }

    public static String createBillTempFilePDF(BillDTO billDTO, HttpServletRequest request) throws IOException {
        return createBillFilePDF(billDTO, request, false);
    }

    public static String createBillFileBinary(BillDTO billDTO, HttpServletRequest request, boolean copy) throws IOException {
        String uploadDir = request.getServletContext().getRealPath("doc/bills/");
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);
        String desFile = "bill_" + billDTO.getId() + ".bills";

        RandomAccessFile writer = new RandomAccessFile(copy ? uploadPath.resolve(desFile).toString() : desFile, "rw");

        writer.writeUTF(dateTimeVietnamese(billDTO.getDate()));
        writer.writeInt(billDTO.getId());
        writer.writeUTF(billDTO.getName());
        writer.writeUTF(billDTO.getAddress());
        writer.writeUTF(billDTO.getPhone());
        writer.writeUTF(billDTO.getEmail());
        writer.writeUTF(billDTO.getPayment());
        double total = 0;
        writer.writeInt(billDTO.getProducts().size());
        for (var product : billDTO.getProducts()) {
            writer.writeInt(product.getProductId());
            writer.writeUTF(product.getName());
            writer.writeUTF(product.getModel().getName());
            writer.writeUTF(currentVietnamese(product.getPrice()));
            writer.writeInt(product.getQuantity());
            writer.writeUTF(currentVietnamese(product.totalPrice()));
            total += product.totalPrice();
        }
        writer.writeInt(20000);
        writer.writeUTF(currentVietnamese(total));
        writer.close();

        return desFile;
    }

    public static String createBillFileBinary(BillDTO billDTO, HttpServletRequest request) throws IOException {
        return createBillFileBinary(billDTO, request, true);
    }

    public static String createBillTempFileBinary(BillDTO billDTO, HttpServletRequest request) throws IOException {
        return createBillFileBinary(billDTO, request, false);
    }

    public static String createBillFileText(BillDTO billDTO, HttpServletRequest request, boolean copy) throws IOException {
        String uploadDir = request.getServletContext().getRealPath("doc/bills/");
        Path uploadPath = Path.of(uploadDir);
        if (!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);
        String desFile = "bill_" + billDTO.getId() + ".txt";

        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(copy ? uploadPath.resolve(desFile).toString() : desFile))), true);

        writer.print("Ngày: ");
        writer.println(dateTimeVietnamese(billDTO.getDate()));
        writer.print("Mã Đơn Hàng: ");
        writer.println(billDTO.getId());
        writer.println("----------------------------------------------------------------");
        writer.print("Tên: ");
        writer.println(billDTO.getName());
        writer.print("Địa Chỉ: ");
        writer.println(billDTO.getAddress());
        writer.print("Số Điện Thoại: ");
        writer.println(billDTO.getPhone());
        writer.print("E-mail: ");
        writer.println(billDTO.getEmail());
        writer.print("Thanh Toán: ");
        writer.println(billDTO.getPayment());
        writer.println("----------------------------------------------------------------");
        double total = 0;
        writer.print("Tổng số đơn sản phẩm: ");
        writer.println(billDTO.getProducts().size());
        for (var product : billDTO.getProducts()) {
            writer.print("|");
            writer.print(product.getProductId());
            writer.print("|");
            writer.print(product.getName());
            writer.print("|");
            writer.print(product.getModel().getName());
            writer.print("|");
            writer.print(currentVietnamese(product.getPrice()));
            writer.print("|");
            writer.print(product.getQuantity());
            writer.print("|");
            writer.print(currentVietnamese(product.totalPrice()));
            writer.print("|");
            total += product.totalPrice();
        }
        writer.println("\n----------------------------------------------------------------");
        writer.print("Phí Ship: ");
        writer.println(20000);
        writer.print("Tổng: ");
        writer.println(currentVietnamese(total));
        writer.close();

        return desFile;
    }

    public static String createBillFileText(BillDTO billDTO, HttpServletRequest request) throws IOException {
        return createBillFileText(billDTO, request, true);
    }

    public static String createBillTempFileText(BillDTO billDTO, HttpServletRequest request) throws IOException {
        return createBillFileText(billDTO, request, false);
    }

    // Helper method to create header cells
    private static Cell createHeaderCell(String text) {
        return new Cell().add(new Paragraph(text).setBold())
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setTextAlignment(TextAlignment.CENTER);
    }

    // Helper method to create label cells
    private static Cell createLabelCell(String text) {
        return new Cell().add(new Paragraph(text).setBold()).setPadding(5);
    }

    // Helper method to create value cells
    private static Cell createValueCell(String text) {
        return new Cell().add(new Paragraph(text)).setPadding(5);
    }

    // Helper method to create empty cells for the items table
    private static Cell createEmptyCell() {
        return new Cell().add(new Paragraph("")).setHeight(20).setPadding(5);
    }

    public static String currentVietnamese(double amount) {
        Locale localeVN = Locale.of("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(amount);
    }

    public static String dateTimeVietnamese(LocalDateTime dateTime) {
        Locale vietnameseLocale = Locale.forLanguageTag("vi");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", vietnameseLocale);
        return dateTime.format(formatter);
    }
}
