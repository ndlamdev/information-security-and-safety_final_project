package controller.profile;

import com.google.gson.Gson;
import model.DAO.UserDAO;
import model.bean.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "ProfileController", value = "/api/profile" )
@MultipartConfig
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        response.sendRedirect("/tai_khoan.jsp");
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            User user = (User) request.getSession().getAttribute("user");
            if(user == null){
                response.setStatus(401);
                return;
            }

            // Đường dẫn để lưu trữ hình ảnh avatar


            // Lấy thông tin user từ request
            String fullName = request.getParameter("full_name");
            String sex = request.getParameter("sex");
            String birthday = request.getParameter("birthday");
            if(fullName == null || fullName.trim().isEmpty() || sex == null || sex.trim().isEmpty() || birthday == null || birthday.trim().isEmpty() ){
                response.setStatus(400);
                response.getWriter().println("Vui lòng nhập đầy đủ thông tin");
                return;
            }
            user.setFullName(fullName);
            user.setSex(sex);
            try {
                user.setBirthDay(LocalDate.parse(birthday));
            }catch (Exception e){
                response.setStatus(400);
                response.getWriter().println("Ngày sinh không hợp lệ: yyyy-mm-dd");
                return;
            }

            // Lấy phần hình đại diện từ request
            Part filePart = request.getPart("avatar");
            System.out.println(filePart.getSubmittedFileName());
            if(filePart.getSubmittedFileName() != null){
                if( filePart.getSize() > 3145728){
                    response.setStatus(400);
                    response.getWriter().println("Avatar phải nhỏ hơn 3MB ");
                    return;
                }
                String path = "images/avatar/" + user.getEmail().replace(".","_");
                String uploadDir = getServletContext().getRealPath(path);
                Path uploadPath = Path.of(uploadDir);

                // Tạo thư mục nếu nó không tồn tại
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String fileName = "avatar.jpg";
                // Lưu hình đại diện vào thư mục chỉ định
                Path filePath = uploadPath.resolve(fileName);
                try (InputStream fileContent = filePart.getInputStream()) {
                    Files.copy(fileContent, filePath, StandardCopyOption.REPLACE_EXISTING);
                }
                String pathAvatar = path + "/" + fileName;
                user.setAvatar(pathAvatar);
            }
            ;
            UserDAO.getInstance().updateProdile(user) ;
            request.getSession().setAttribute("user", user);
            response.setStatus(200);
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(400);
            response.getWriter().println("Vui lòng thử lại");
            }
    }
}
