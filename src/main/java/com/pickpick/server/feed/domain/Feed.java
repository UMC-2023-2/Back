package com.pickpick.server.feed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pickpick.server.album.domain.Album;
import com.pickpick.server.global.common.BaseTimeEntity;
import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.feed.domain.enums.BookMark;
import com.pickpick.server.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@DynamicInsert
@DynamicUpdate
public class Feed extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feed_id")
	private Long id;

	private String content;

	@Column(nullable = false, length = 3)
	@ColumnDefault("'OFF'")
	@Enumerated(EnumType.STRING)
	private BookMark bookMark;


	private String imgUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "album_id")
	@JsonIgnore
	private Album album;

	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
	private List<Photo> photo = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

}
