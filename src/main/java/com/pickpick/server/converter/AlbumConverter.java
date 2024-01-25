package com.pickpick.server.converter;

import com.pickpick.server.domain.Album;
import com.pickpick.server.domain.SharedAlbum;
import com.pickpick.server.domain.Users;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.dto.AlbumRequest;
import com.pickpick.server.dto.AlbumResponse;
import java.time.LocalDate;
import java.util.List;

public class AlbumConverter {

    public static Album toAlbum(AlbumRequest.CreateDTO request) {
        return Album.builder()
            .name(request.getName())
            .titleImgUrl(request.getTitleImgUrl())
            .shareStatus(request.getShareStatus())
            .createdAt(LocalDate.now())
            .build();
    }
    public static AlbumResponse.CreateDTO toCreateDTO(Album album){
        return AlbumResponse.CreateDTO.builder()
            .albumId(album.getId())
            .createdAt(album.getCreatedAt())
            .build();
    }



}