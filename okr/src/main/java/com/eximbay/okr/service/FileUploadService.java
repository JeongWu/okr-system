package com.eximbay.okr.service;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.constant.ErrorMessages;
import com.eximbay.okr.dto.MemberDto;
import com.eximbay.okr.enumeration.EntityType;
import com.eximbay.okr.enumeration.FileContentType;
import com.eximbay.okr.enumeration.FileType;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.service.Interface.IMemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class FileUploadService {
    private final IMemberService memberService;

    @Value("${application.file.imagesPath}")
    private String imagePath;

    @Value("${application.supported-image-extensions}")
    private String supportedImageExtensions;

    @Value("${application.image-max-size}")
    private Integer imageMaxSize;
    
    public String store(FileType fileType, FileContentType fileContentType, EntityType entityType, MultipartFile file){
        if (fileType.equals(FileType.VIDEO)) throw new UserException(new IllegalArgumentException("Video is not supported yet"));
        validateImage(file);
        String uploadFileName = file.getOriginalFilename();
        String extension = uploadFileName.substring(uploadFileName.lastIndexOf("."));

        Optional<MemberDto> memberDto = memberService.getCurrentMember();
        // if (memberDto.isEmpty()) throw new UserException(ErrorMessages.loginRequired);

        try {
            String saveFileName = entityType.name()  + extension;
            Path saveDestination = Paths.get(imagePath)
                    .resolve(getSubPath(fileContentType) + saveFileName)
                    .normalize().toAbsolutePath();
            FileCopyUtils.copy(file.getBytes(), saveDestination.toFile());
            return saveFileName;
        }catch (Exception e){
            e.printStackTrace();
            throw new UserException(ErrorMessages.errorWhileSavingFile);
        }
    }

    private void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty())
            throw new UserException(new IllegalArgumentException(ErrorMessages.fileMustNotBeEmpty));
        String uploadFileName = file.getOriginalFilename();
        if (uploadFileName == null)
            throw new UserException(new IllegalArgumentException(ErrorMessages.fileExtensionIsNotSupported));
        String extension = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
        if (!supportedImageExtensions.contains(extension))
            throw new UserException(new IllegalArgumentException(ErrorMessages.fileExtensionIsNotSupported));
        if (file.getSize() > imageMaxSize)
            throw new UserException(new IllegalArgumentException(ErrorMessages.fileSizeLimitExceeded + " of " + imageMaxSize/1024 + " Kb"));
    }

    private String getSubPath(FileContentType fileContentType){
        switch (fileContentType){
            case AVATAR:
                return AppConst.AVATAR_SUB_PATH;
            case IMAGE:
            default: return "";
        }
    }
}
