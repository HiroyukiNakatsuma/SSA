package com.example.ssa.ssa.service;

import com.example.ssa.ssa.domain.mapper.PhotoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Service
public class AlbumService {
    @Autowired
    private PhotoMapper photoMapper;

    private static final String BASE_PHOTO_PATH = "/Users/Shared/ssa/data/photo";

    @Transactional(rollbackFor = IOException.class)
    public void postPhoto(MultipartFile[] files, long roomId, long accountId) {
        Arrays.stream(files).forEach(file -> {
            // ファイルを格納
            String imagePath = uploadPhotoImage(file, roomId);
            // DB登録
            photoMapper.insert(imagePath, roomId, accountId);
        });
    }

    private String uploadPhotoImage(InputStreamSource file, long roomId) {
        return "";
    }
}
