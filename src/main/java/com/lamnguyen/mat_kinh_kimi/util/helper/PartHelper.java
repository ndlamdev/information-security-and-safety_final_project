/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 9:06 AM - 11/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.util.helper;

import javax.servlet.http.Part;

public class PartHelper {
    public static String getFileName(Part part) {
        var contentDisposition = part.getHeader("content-disposition");
        String[] items = contentDisposition.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                return item.substring(item.indexOf("=") + 2, item.length() - 1);
            }
        }

        return "unknown";
    }
}
