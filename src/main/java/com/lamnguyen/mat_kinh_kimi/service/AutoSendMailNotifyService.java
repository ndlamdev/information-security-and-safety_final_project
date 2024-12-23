package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.config.mail.SendMail;
import com.lamnguyen.mat_kinh_kimi.model.BillStatus;
import com.lamnguyen.mat_kinh_kimi.util.enums.BillStatusEnum;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoSendMailNotifyService extends Thread {
    private final Map<String, CounterSendMail> userSendMail;
    private static AutoSendMailNotifyService instance;
    private final ScheduledExecutorService scheduler;
    private boolean start = false;

    private AutoSendMailNotifyService() {
        userSendMail = new HashMap<>();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(() -> {
            userSendMail.forEach((key, value) -> {
                var keySendMail = KeySendMail.parse(key);
                if (keySendMail == null || !timeOut(value.time)) return;
                sendMail(keySendMail.email, keySendMail.billId);
                addAutoSendMail(key);
            });
        }, 0, 1, TimeUnit.HOURS);
    }

    private boolean timeOut(LocalDateTime time) {
        return time.until(LocalDateTime.now(), ChronoUnit.SECONDS) > 600 * 24;
    }

    public static AutoSendMailNotifyService getInstance() {
        return instance = instance == null ? new AutoSendMailNotifyService() : instance;
    }

    public void addAutoSendMail(String email, int billId) {
        if (!start) {
            start();
            start = true;
        }
        var key = generateKey(email, billId);
        var counter = userSendMail.containsKey(key) ?
                new CounterSendMail(userSendMail.get(key).count + 1, userSendMail.get(key).time.plusDays(1)) :
                new CounterSendMail(0, LocalDateTime.now());

        if (counter.count > 3)
            cancelBill(email, billId);

        userSendMail.put(key, counter);
    }

    private void addAutoSendMail(String key) {
        var keyObj = KeySendMail.parse(key);
        if (keyObj == null) return;
        addAutoSendMail(keyObj.email, keyObj.billId);
    }

    public void removeAutoSendMail(String email, int billId) {
        userSendMail.remove(generateKey(email, billId));
    }

    private String generateKey(String email, int billId) {
        return email + "_" + billId;
    }

    private record KeySendMail(String email, int billId) {
        public static KeySendMail parse(String key) {
            var keys = key.split("_");
            try {
                var billId = Integer.parseInt(keys[1]);
                return new KeySendMail(keys[0], billId);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    private record CounterSendMail(int count, LocalDateTime time) {
    }

    private void sendMail(String email, int billId) {
        SendMail.Send(email, "Yêu cầu ký xác nhận đơn hàng!",
                "<div style=\"max-width: 500px; margin: auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\">"
                + "<h2 style=\"margin-bottom: 20px; color: #333;\">Một đơn hàng chưa được ký xác nhận.</h2>"
                + "<p style=\"margin-bottom: 20px; color: #555;\">Bạn có một đơn hàng chưa được ký xác nhận để hoàn tất quá trình mua hàng. Chúng tôi sẽ tự động hủy đơn hàng này sau 3 ngày nếu như bạn không ký xác nhận đơn hàng. Vui lòng kiểm tra lại thông tin và thực hiện ký lại để xác nhận đơn hàng. Nhấn vào nút bên dưới để được chuyển hướng đến trang ký xác nhận.</p>"
                + "<a href=\"http://localhost:8080/mat_kinh_kimi/bill_history?action=see-detail&bill-id=" + billId + "\" style=\"display:block;width:100%;padding:10px;font-size:16px;color:#fff;background-color:#007bff;border:none;border-radius:5px;text-decoration: none;text-align: center;\">Ký đơn hàng</a>"
                + "</div>"
        );
    }

    private void cancelBill(String email, int billId) {
        BillStatus billStatus = new BillStatus();
        billStatus.setBillId(billId);
        billStatus.setDate(LocalDateTime.now());
        billStatus.setStatus(BillStatusEnum.CANCEL.getStatus());
        billStatus.setDescribe("Đơn hàng của bạn đã bị hủy do không thực hiện ký xác nhận đơn hàng!");
        billStatus.setCanEdit(false);
        BillStatusService.getInstance().insert(billStatus);
        removeAutoSendMail(email, billId);
    }
}
