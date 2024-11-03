package controller.login;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import google.Constants;
import google.UserGoogleDto;
import model.bean.User;
import model.service.UserService;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginGoogleController", value = "/login_google")
public class LoginGoogleController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        String accessToken = getToken(code);
        UserGoogleDto userGoogleDto = getUserInfo(accessToken);

        String email = userGoogleDto.getEmail();
        UserService userService = UserService.getInstance();
        User user;
        if (userService.containsEmail(email)) {
            user =  userService.getUser(email);

            if (user == null) {
                request.setAttribute("login_error", "Tài khoản của bạn đã bị khóa!");
                request.getRequestDispatcher("dang_nhap.jsp").forward(request, response);
                return;
            }

            request.getSession().setAttribute("user", user);
            if (user.isAdmin()) {
                response.sendRedirect("admin_pages/dashboard.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        }
        else {
            user = new User();
            user.setEmail(email);
            user.setAvatar(userGoogleDto.getPicture());
            request.setAttribute("user", user);
            request.getRequestDispatcher("dang_ky_voi_google.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public static String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static UserGoogleDto getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();

        UserGoogleDto googlePojo = new Gson().fromJson(response, UserGoogleDto.class);

        return googlePojo;
    }
}