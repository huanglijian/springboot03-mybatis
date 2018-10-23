package cn.ck.controller.jobs;

import cn.ck.utils.ConstCofig;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("down")
public class jianlidownload {

    @GetMapping("/jianli")
    public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
        System.out.println("in--down---and--filename--is:"+fileName);
        try {
            //存放地址
//            String realPath = "F:\\down";
            String realPath = ConstCofig.RootPath+"resume";
            //获得服务器端某个文件的完整路径
            String fullPath = realPath + File.separator + fileName;
            //设置响应
            response.setContentType("application/force-download");
            //设置响应头信息
            response.setHeader("Content-Disposition", "attachment;fileName="+fileName);// 设置文件名
            //文件名有中文时设置编码
            response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes("GBK"),"ISO-8859-1"));

            File downloadFile = new File(fullPath);
            FileInputStream inputStream = new FileInputStream(downloadFile);
            OutputStream outputStream =  response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            response.flushBuffer();
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
