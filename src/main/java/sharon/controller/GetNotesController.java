package sharon.controller;

import java.io.IOException;
import java.io.Serial;
import java.nio.file.InvalidPathException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sharon.controller.utils.RestPathValidator;
import sharon.exception.BusinessException;
import sharon.service.NoteService;
import sharon.service.impl.NoteServiceImpl;
import sharon.vo.ApiResponse;
import sharon.vo.Note;

/**
 * 處理會員筆記相關的請求控制器
 * URL路徑: /api/v1/members/{memberId}/notes
 */
@WebServlet("/api/v1/members/*")
public class GetNotesController extends BaseController {

    @Serial  // Java 14 引入的新註解 用於標記序列化相關的字段和方法 幫助編譯器進行檢查
    private static final long serialVersionUID = 1L;
    private NoteService service;

    /**
     * Servlet初始化
     * 建立NoteService和Gson實例
     */
    @Override
    public void init() throws ServletException {
        try {
            service = new NoteServiceImpl();
        } catch (NamingException e) {
            // 記錄具體錯誤信息
            throw new ServletException("初始化 NoteService 失敗", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // 處理路徑中的參數
            String pathInfo = req.getPathInfo();

            int memberId = RestPathValidator.extractMemberIdFromNoteListPath(pathInfo);

            // 調用服務層獲取響應
            List<Note> notes = service.getNotes(memberId);

            ApiResponse<List<Note>> response = notes.isEmpty()
                ? new ApiResponse<>(200, "此會員尚無筆記", notes)
                : new ApiResponse<>(200, "成功取得全部筆記", notes);

            sendJsonResponse(resp, response);

        } catch (InvalidPathException e) {
            sendErrorResponse(resp,400,e.getMessage());

        } catch (BusinessException e) {
          sendErrorResponse(resp,e.getStatusCode(),e.getMessage());

        } catch (Exception e) {
            sendErrorResponse(resp,500,"伺服器錯誤");
        }
    }
}
