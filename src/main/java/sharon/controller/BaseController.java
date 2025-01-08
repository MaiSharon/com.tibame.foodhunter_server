package sharon.controller;

import com.google.gson.Gson;
import sharon.vo.ApiResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class BaseController extends HttpServlet {
    protected final Gson gson = new Gson();  // 單例複用 避免重複創建提升性能

    protected void sendJsonResponse(HttpServletResponse resp, ApiResponse<?> apiResponse) {
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // 創建統一的成功響應格式，狀態碼 200
            writeResponse(resp, apiResponse);
        } catch (IOException e) {
            sendErrorResponse(resp, 500,"內部系統錯誤");
        }
    }

    protected void sendErrorResponse(HttpServletResponse resp, int status, String message) {
        try {
            // 設置 HTTP 狀態碼
            resp.setStatus(status);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            // 創建統一的錯誤響應格式，數據為 null
            ApiResponse<?> errorResponse = new ApiResponse<>(status, message, null);
            writeResponse(resp, errorResponse);  // 嘗試寫入錯誤響應
        } catch (IOException e) {
            // 當 writeResponse 失敗時會進入這裡
            e.printStackTrace();  // 在服務器日誌中打印錯誤
            resp.setStatus(500);  // 告訴客戶端發生了服務器錯誤
        }
    }
    // 私有方法，實際寫入響應數據
    private void writeResponse(HttpServletResponse resp, ApiResponse<?> response)
            throws IOException {
        // 使用 try-with-resources 自動關閉 PrintWriter
        try (PrintWriter writer = resp.getWriter()) {
            // 將響應對象轉換為 JSON 字符串
            writer.write(gson.toJson(response));
            // 立即刷新輸出流，確保數據被發送
            writer.flush();
        }
    }
}
