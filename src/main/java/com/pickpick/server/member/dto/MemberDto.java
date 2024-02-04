package com.pickpick.server.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDto {

	private String email;
	private String name;
	private String phoneNum;
	private String imgUrl;
}
