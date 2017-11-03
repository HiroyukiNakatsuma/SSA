package com.example.ssa.ssa.service;

import com.example.ssa.ssa.component.properties.UrlProperties;
import com.example.ssa.ssa.domain.mapper.PhotoMapper;
import com.example.ssa.ssa.domain.model.Photo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
import java.util.List;

@Slf4j
@Service
public class AlbumService {
    @Autowired
    private PhotoMapper photoMapper;
    @Autowired
    private UrlProperties urlProperties;

    private static final int IMAGE_WIDTH = 480;
    private static final int IMAGE_HEIGHT = 320;

    public List<Photo> loadList(long roomId) {
        List<Photo> photos = photoMapper.loadListByRoomId(roomId);
        photos.forEach(photo -> photo.setImageUrl(urlProperties.getPhoto() + photo.getImagePath()));
        return photos;
    }

    /**
     * 選択された複数のアルバム写真画像をアップロード
     * ファイルを格納後に、DB登録
     *
     * @param files     選択された画像ファイル
     * @param roomId    ルームID
     * @param accountId 会員ID
     */
    @Transactional(rollbackFor = UncheckedIOException.class)
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
            Files.createDirectory(Paths.get(urlProperties.getRoot(), urlProperties.getPhoto() + fileDir));
            filePath = Paths.get(urlProperties.getRoot(), urlProperties.getPhoto() + fileDir, file.getOriginalFilename());
            Files.createFile(filePath);
        } catch (IOException e) {
            log.info(String.format("画像の登録に失敗しました。 roomId: %s, fileName: %s", roomId, file.getOriginalFilename()));
            throw new IOException(e);
        }
        try (ByteArrayInputStream is = new ByteArrayInputStream(file.getBytes());
             OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
            BufferedImage destImage = resizeImage(ImageIO.read(is));
            ImageIO.write(destImage, "jpeg", os);
        } catch (IOException e) {
            log.info(String.format("画像の登録に失敗しました。 roomId: %s, fileName: %s", roomId, file.getOriginalFilename()));
            throw new IOException(e);
        }
        return fileDir + "/" + file.getOriginalFilename();
    }

    /**
     * ランダム値のディレクトリパスを作成
     * 「ID + 現在日時」でsha256変換
     *
     * @param id ID
     * @return パス文字列
     */
    private String createPath(Long id) {
        return DigestUtils.sha256Hex(id.toString()
                + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));
    }

    /**
     * 画像リサイズ処理
     * 画像リサイズ(アフィン変換)の変換器を作成し、リサイズ済みの画像を返す
     *
     * @param image アップロード画像
     * @return
     * @throws IOException
     */
    private static BufferedImage resizeImage(BufferedImage image) throws IOException {
        AffineTransform transform = AffineTransform.getScaleInstance(
                (double) IMAGE_WIDTH / image.getWidth(),
                (double) IMAGE_HEIGHT / image.getHeight());
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);

        BufferedImage resizedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, image.getType());
        op.filter(image, resizedImage);

        return resizedImage;
    }
}
