package com.github.netty.http;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipartWithParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        Map<String, Object> param = new HashMap<>();
        try {
            List<FileItem> items = upload.parseRequest(req);
            for(Object object:items){
                FileItem fileItem = (FileItem) object;
                if (fileItem.isFormField()) {
                    param.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        PrintWriter writer = resp.getWriter();
        writer.write(String.valueOf(param));
        writer.flush();
    }
}
