package helper;

import com.sun.mail.smtp.SMTPMessage;
import model.bean.Bill;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendMail {

    private static Session session;

    private SendMail() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS Port
        props.put("mail.smtp.auth", "true"); // enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

        String fromEmail = SendMailProperties.getEmail();
        String password = SendMailProperties.getPassword();
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        session = Session.getInstance(props, auth);
    }

    public static void Send(String to, String subject, String body) {
        if (session == null) new SendMail();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            try {
                Message msg = new MimeMessage(session);

                msg.setFrom(new InternetAddress("Shop"));
                InternetAddress[] toAddresses = {new InternetAddress(to)};
                msg.setRecipients(Message.RecipientType.TO, toAddresses);
                msg.setSubject(subject);
                msg.setSentDate(new Date());
                msg.setContent(body, "text/html;  charset=UTF-8");
                Transport.send(msg);
            } catch (MessagingException e) {
            }
        });
        executor.shutdown();
    }

    public static void SendMailWithImage(String to, String subject, String body) {
        if (session == null) new SendMail();
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            try {
                Message msg = new MimeMessage(session);

                msg.setFrom(new InternetAddress("Shop"));
                InternetAddress[] toAddresses = {new InternetAddress(to)};
                msg.setRecipients(Message.RecipientType.TO, toAddresses);
                msg.setSubject(subject);
                msg.setSentDate(new Date());
                // Tạo phần thân (body) HTML của email với hình ảnh đính kèm
                MimeBodyPart htmlPart = new MimeBodyPart();
                htmlPart.setContent(body, "text/html; charset=UTF-8");

                // Tạo MimeMultipart để kết hợp các phần thân của email
                MimeMultipart multipart = new MimeMultipart();
                multipart.addBodyPart(htmlPart);

                // Đính kèm hình ảnh từ file
                MimeBodyPart imagePart = new MimeBodyPart();
                File imageFile = new File("D:\\Tai_lieu_hoc_tap\\LapTrinhWeb\\project_ban_mat_kinh\\src\\main\\webapp\\images\\icon\\icon_tich_xanh.png"); // Thay đổi đường dẫn tới hình ảnh cần gửi đi
                imagePart.attachFile(imageFile);
                imagePart.setContentID("<complete_cid>");
                multipart.addBodyPart(imagePart);

                // Đặt phần thân của email
                msg.setContent(multipart);
                Transport.send(msg);
            } catch (MessagingException e) {
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        executor.shutdown();
    }

    public static String getFormRegister(String url, String fullName, String email, String code) {
        return "<div style=\"width: 500px; background: #2F189A; color: #fff; padding: 15px; border-radius: 10px\">" +
                "    <h3 style=\"margin-top: 0;\">Xin chào " + fullName + "!</h3>" +
                "    <p>Bạn vừa đăng ký thành công thông tin tài khoản của mình." +
                "        Để hoàn tất quá trình đăng ký vui lòng xác thực để hoàn tất quá trình đăng ký." +
                "    </p>" +
                "    <p>Mail xác thực chỉ khả dụng tối đa 10' tính từ thời điểm nhận mail</p>" +
                "    <a href=\"" + url + "&email=" + email + "&code=" + code + "\">" +
                "        <button style=\"background: #ff3300; color: #fff; padding: 10px; border: none; border-radius: 5px; font-size: 15px; font-weight: bold\">Nhấp để xác thực</button>" +
                "    </a>" +
                "</div>";
    }

    public static String getFormForgetPassword(String url, String email, String code) {
        return "<div style=\"width: 500px; background: #2F189A; color: #fff; padding: 15px; border-radius: 10px\">" +
                "    <h3 style=\"margin-top: 0;\">Xin chào " + email + "</h3>" +
                "    <p>Bạn vừa gửi lệnh yêu cầu đổi lại mật khẩu của mình. <br>" +
                "        Vui lòng nhấn nút xác thực để tiếp tục quá trình đổi mật khẩu." +
                "    </p>" +
                "    <p>Mail xác thực chỉ khả dụng tối đa 10' tính từ thời điểm nhận mail.</p>" +
                "    <a href=\"" + url + "&email=" + email + "&code=" + code + "\">" +
                "        <button style=\"background: #ff3300; color: #fff; padding: 10px; border: none; border-radius: 5px; font-size: 15px; font-weight: bold\">" +
                "            Nhấp để xác thực" +
                "        </button>" +
                "    </a>" +
                "</div>";
    }

    /*
    gửi hóa đơn cho khách hàng
     */
    public static String getFormBill(String url, Bill bill, String addressDetails) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.of("vi", "VN"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = bill.getStatuses().get(0).getDate().format(formatter);
        String form = "<div style=\"width: 50%; height: auto; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); margin: auto; font-family: Tahoma, Arial, sans-serif;\">" +
                "    <div class=\"tttc-top\" style=\"text-align: center\">" +
                "        <h1 class=\"\" style=\"color: #2F189A; padding-top: 0.5rem; margin-bottom: 5px\">Thanh toán thành công</h1>" +
                "        <div class=\"icon-tttc\" style=\"margin-bottom: -5px;\">" +
                "            <img src=\"cid:complete_cid\"" +
                "                 alt=\"complete.png\">" +
                "        </div>" +
                "        <div class=\"time-tttc\" style=\"color: #2F189A; margin-top: -5px;\">" +
                "            <p>" +
                "                <span>" + formattedDateTime + "</span>" +
                "            </p>" +
                "        </div>" +
                "    </div>" +
                "    <div class=\"tttc-bot\" style=\"padding: 0 1rem\">" +
                "        <div class=\"ma-giao-dich\"" +
                "             style=\"padding: 10px 0; border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">" +
                "            <span class=\"left\">Mã đơn hàng</span>" +
                "            <span class=\"right\" style=\"font-weight: bold; color: #0a53be; float: right;\">#" + bill.getId() + "</span>" +
                "        </div>" +
                "        <div class=\"username\"" +
                "             style=\"padding: 10px 0; border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">" +
                "            <span class=\"left\">Tên</span>" +
                "            <span class=\"right\"  style=\"float: right;\">" + bill.getUserName() + "</span>" +
                "        </div>" +
                "        <div class=\"phone-number\"" +
                "             style=\"padding: 10px 0; border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">" +
                "            <span class=\"left\">Số điện thoại</span>" +
                "            <span class=\"right\"  style=\"float: right;\">" + bill.getPhoneNumber() + "</span>" +
                "        </div>" +
                "        <div class=\"email\"" +
                "             style=\"padding: 10px 0; border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">" +
                "            <span class=\"left\">Email</span>" +
                "            <span class=\"right\"  style=\"float: right;\">" + bill.getEmail() + "</span>" +
                "        </div>" +
                "        <div class=\"adrress\"" +
                "             style=\"padding:15px 0 30px 0; border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">" +
                "            <span class=\"left\">Địa chỉ</span>" +
                "            <span class=\"right\"  style=\"float: right;\">" + addressDetails + "</span>" +
                "        </div>" +
                "        <div class=\"phuong-thuc-thanh-toan\"" +
                "             style=\"padding: 10px 0; border-bottom: 1px solid rgba(0, 0, 0, 0.5);\">" +
                "            <span class=\"left\">Phương thức thanh toán</span>";
        form += bill.isTransfer() ? "            <span class=\"right\"  style=\"float: right;\">Chuyển khoản</span>" : "            <span class=\"right\"  style=\"float: right;\">Thanh toán trực tiếp</span>";
        form += "        </div>" +
                "        <div class=\"so-tien\" style=\"padding: 10px 0;\">" +
                "            <span class=\"left\">Tổng số tiền</span>" +
                "            <span class=\"right\"  style=\"float: right;\">" + nf.format(bill.totalBill()) + "</span>" +
                "        </div>" +
                "    </div>" +
                "    <div class=\"btn-tttc\" style=\"padding: 3rem 0; text-align: center; \">" +
                "        <a href=\"" + url + "\"" +
                "           style=\"border-radius: 5px;  background-color: #2F189A; color: #FFFFFF; border: 2px solid #2F189A;  padding: 10px 5px; text-decoration: none;\"" +
                "           class=\"check-bill\" type=\"button\">" +
                "            Kiểm tra đơn hàng</a>" +
                "    </div>" +
                "</div>";
        return form;
    }
}
