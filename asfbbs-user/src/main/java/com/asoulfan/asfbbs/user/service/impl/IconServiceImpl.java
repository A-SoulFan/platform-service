package com.asoulfan.asfbbs.user.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asoulfan.asfbbs.exception.Asserts;
import com.asoulfan.asfbbs.user.service.IIconService;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @program: ASFBBS
 * @description: 头像相关
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-09-05
 **/
@Service
public class IconServiceImpl implements IIconService {

    @Value("${imgFile.storage}")
    private String imgPath;

    @Override
    public String upload(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (StrUtil.isNotBlank(filename)) {
            try (InputStream is = file.getInputStream()) {
                File path = new File(imgPath);
                if (!path.exists()) {
                    path.mkdir();
                }
                String ext = FileTypeUtil.getType(is);
                if (!"jpg".equals(ext) && !"jpeg".equals(ext) && !"png".equals(ext)) {
                    Asserts.fail("只支持jpg/jpeg/png格式图片");
                }
                String id = IdUtil.simpleUUID();
                String fileName = id + "." + ext;
                File localFile = new File(imgPath + "/" + fileName);
                file.transferTo(localFile);
                return fileName;
            } catch (IOException ioException) {
                Asserts.fail("保存头像文件失败");
            }
        }
        Asserts.fail("保存头像文件失败");
        return null;
    }

    /**
     * 图片下载
     *
     * @param id
     * @param response
     */
    @Override
    public void get(String id, HttpServletResponse response) {
        File file = new File(imgPath + "/" + id);
        if (!file.exists()) {
            Asserts.fail("文件不存在");
        }
        byte[] bytes;
        response.setHeader("Content-Disposition", "attachment; filename" + id + " ");
        try (InputStream is = new FileInputStream(file); OutputStream outputStream = response.getOutputStream()) {
            int length;
            bytes = new byte[1024];
            while ((length = is.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
        } catch (Exception ex) {
            Asserts.fail("下载文件失败");
        }
        Asserts.fail("下载文件失败");
    }
}
