package com.pickpick.server.album.service;


import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.AlbumHandler;
import com.pickpick.server.global.apiPayload.exception.handler.MemberHandler;
import com.pickpick.server.converter.AlbumConverter;
import com.pickpick.server.album.domain.Album;
import com.pickpick.server.album.domain.SharedAlbum;
import com.pickpick.server.member.domain.Member;
import com.pickpick.server.album.dto.AlbumRequest;
import com.pickpick.server.album.repository.AlbumRepository;
import com.pickpick.server.album.repository.SharedAlbumRepository;
import com.pickpick.server.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final MemberRepository memberRepository;
    private final SharedAlbumRepository sharedAlbumRepository;

    public Album create(AlbumRequest.CreateDTO request) {

        //앨범 생성
        Album album = AlbumConverter.toAlbum(request);

        request.getUserId().forEach(userId -> {
            Optional<Member> user = memberRepository.findById(userId);
            if(user.isEmpty()){
                throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
            }

            //sharedAlbum 생성
            SharedAlbum sharedAlbum = SharedAlbum.builder()
                .user(user.get())
                .album(album)
                .build();

            sharedAlbumRepository.save(sharedAlbum);
        });

        return albumRepository.save(album);

    }

    public List<List<Album>> findByEmail(String email) {
        Optional<Member> user = memberRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }

        List<SharedAlbum> sharedAlbumList = user.get().getSharedAlbums();

        if (!sharedAlbumList.isEmpty()) {

            List<Album> shareAlbum = new ArrayList<>();
            List<Album> nonShareAlbum = new ArrayList<>();
            for (SharedAlbum sharedAlbum : sharedAlbumList) {
                if (Objects.equals(sharedAlbum.getAlbum().getShareStatus().toString(),
                    "SHAREABLE")) {
                    shareAlbum.add(sharedAlbum.getAlbum());
                } else {
                    nonShareAlbum.add(sharedAlbum.getAlbum());
                }
            }
            List<List<Album>> albums = new ArrayList<>();
            albums.add(shareAlbum);
            albums.add(nonShareAlbum);
            return albums;

        } else {
            return null;
        }
    }

    public void deleteAlbum(AlbumRequest.DeleteAlbumDTO request) {
        Optional<Album> album = albumRepository.findById(request.getAlbumId());
        if(album.isEmpty()){
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
        albumRepository.delete(album.get());
    }

    public Long updateAlbum(AlbumRequest.UpdateAlbumDTO request) {
        Optional<Album> album = albumRepository.findById(request.getAlbumId());
        if(album.isEmpty()){
            throw new AlbumHandler(ErrorStatus.ALBUM_NOT_FOUND);
        }
        album.get().setName(request.getTitle());
        album.get().setTitleImgUrl(request.getImgUrl());
        List<SharedAlbum> sharedAlbumList = sharedAlbumRepository.findByAlbum(album.get());
        sharedAlbumRepository.deleteAll(sharedAlbumList);
        for (Long userId : request.getUserId()) {
            Optional<Member> user = memberRepository.findById(userId);
            if(user.isEmpty()){
                throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
            }
            SharedAlbum sharedAlbum = SharedAlbum.builder()
                .album(album.get())
                .user(user.get())
                .build();
            sharedAlbumRepository.save(sharedAlbum);
        }
        return request.getAlbumId();
    }
}
