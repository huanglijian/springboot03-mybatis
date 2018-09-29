package cn.ck.controller;

import cn.ck.utils.ConstCofig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("file")
public class FileController {

    /**
     * 加载图片
     * 以字节数组的形式
     * 用response输出到页面
     * @param fileName 要加载的图片文件名
     * @param response
     */
    @RequestMapping("/showImg/{fileName}")
    public void showPicture(@PathVariable("fileName") String fileName, HttpServletResponse response){
        //将文件名分割成 文件名 和 格式
        //“ . " 需要用两次转义
        String [] path = fileName.split("\\.");
        //获取服务器中的文件
        File imgFile = new File(ConstCofig.RootPath + ConstCofig.ImgPath + path[0] + "." + path[1]);
        //输出到页面
        responseFile(response, imgFile);
    }

    /**
     * 响应输出图片文件
     * 文件转换为字节数组
     * @param response
     * @param imgFile
     */
    private void responseFile(HttpServletResponse response, File imgFile) {
        try(InputStream is = new FileInputStream(imgFile); OutputStream os = response.getOutputStream()){
            byte [] buffer = new byte[(int)imgFile.length()]; // 图片文件流缓存池
            while(is.read(buffer) != -1){
                os.write(buffer);
            }
            os.flush();
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
