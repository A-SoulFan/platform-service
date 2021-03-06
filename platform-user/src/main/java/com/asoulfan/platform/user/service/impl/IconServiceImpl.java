package com.asoulfan.platform.user.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asoulfan.platform.user.service.IIconService;
import com.asoulfan.platform.common.exception.Asserts;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 头像相关
 *
 * @author fengling
 * @since 2021-09-05
 **/
@Service
@Slf4j
public class IconServiceImpl implements IIconService {

    @Value("${imgFile.storage}")
    private String imgPath;

    @Override
    public String upload(String imgByte) {
        byte[] decode = Base64.decode(imgByte);
        try (InputStream is = new ByteArrayInputStream(decode)) {
            String ext = FileTypeUtil.getType(is);
            if (!"jpg".equals(ext) && !"jpeg".equals(ext) && !"png".equals(ext)) {
                Asserts.fail("只支持jpg/jpeg/png格式图片");
            }
            String id = IdUtil.simpleUUID();
            String fileName = id + "." + ext;
            FileUtil.writeBytes(decode, imgPath + "/" + fileName);
            return fileName;
        } catch (Exception Exception) {
            Asserts.fail("保存头像文件失败");
        }
        Asserts.fail("保存头像文件失败");
        return null;
    }

    /**
     * 图片下载
     *
     * @param id
     */
    @Override
    public String get(String id) {
        File file = new File(imgPath + "/" + id);
        if (!file.exists()) {
            Asserts.fail("文件不存在");
        }
        return Base64.encode(file);
    }
}
