package com.example.ssa.ssa.service;

import com.example.ssa.ssa.domain.mapper.PhotoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Slf4j
@Service
public class AlbumService {
    @Autowired
    private PhotoMapper photoMapper;

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);
    private static final String BASE_PHOTO_PATH = "/Users/Shared/ssa/data/photo/";

    /**
     * 選択された複数のアルバム写真画像をアップロード
     * ファイルを格納後に、DB登録
     *
     * @param files     選択された画像ファイル
     * @param roomId    ルームID
     * @param accountId 会員ID
     */
    @Transactional(rollbackFor = IOException.class)
    public void postPhoto(MultipartFile[] files, long roomId, long accountId) {
        Arrays.stream(files).forEach(file -> {
            try {
                String imagePath = uploadPhotoImage(file, roomId);
                photoMapper.insert(imagePath, roomId, accountId);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

    /**
     * アルバム写真の画像を１ファイルだけアップロード
     *
     * @param file   画像ファイル
     * @param roomId ルームID
     * @return
     * @throws IOException
     */
    private String uploadPhotoImage(MultipartFile file, long roomId) throws IOException {
        String fileDir = createPath(roomId);
        Path filePath;
        try {
            Files.createDirectory(Paths.get(fileDir));
            filePath = Paths.get(fileDir, file.getOriginalFilename());
            Files.createFile(filePath);
        } catch (IOException e) {
            logger.error(String.format("画像の登録に失敗しました。 roomId: %s, fileName: %s", roomId, file.getOriginalFilename()));
            throw new IOException(e);
        }
        try (OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            byte[] bytes = file.getBytes();
            os.write(bytes);
        } catch (IOException e) {
            logger.error(String.format("画像の登録に失敗しました。 roomId: %s, fileName: %s", roomId, file.getOriginalFilename()));
            throw new IOException(e);
        }
        return filePath.toString();
    }

    /**
     * ランダム値のディレクトリパスを作成
     * 「ID + 現在日時」でsha256変換
     *
     * @param id ID
     * @return パス文字列
     */
    private String createPath(Long id) {
        String now = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        return BASE_PHOTO_PATH + DigestUtils.sha256Hex(id.toString() + now);
    }
}
