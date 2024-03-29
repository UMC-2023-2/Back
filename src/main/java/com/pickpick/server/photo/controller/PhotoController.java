package com.pickpick.server.photo.controller;

import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.global.converter.PhotoConverter;
import com.pickpick.server.global.file.FileService;
import com.pickpick.server.photo.dto.PhotoRequest;
import com.pickpick.server.photo.dto.PhotoResponse;
import com.pickpick.server.photo.service.PhotoService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;
    private final FileService fileService;

    @PostMapping("/photo")
    public ApiResponse<PhotoResponse.CreateDTO> create(PhotoRequest.CreatePhotoDTO request) throws IOException, Exception {
        return ApiResponse.onSuccess(PhotoConverter.toCreateDTO(photoService.createPhoto(request)));
    }

    @GetMapping("/photos")
    public ApiResponse<List<PhotoResponse.GetPhotosDTO>> getPhotos(){
        return ApiResponse.onSuccess(PhotoConverter.toGetPhotosDTO(photoService.getPhotos()));
    }

    @PostMapping("/photo/update")
    public ApiResponse<PhotoResponse.UpdatePhotoDTO> updatePhoto(@RequestBody @Valid PhotoRequest.UpdatePhotoDTO request){
        return ApiResponse.onSuccess(PhotoConverter.toUpdatePhotoDTO(photoService.updatePhoto(request)));
    }
}
