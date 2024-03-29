package com.pickpick.server.global.converter;


import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.member.domain.enums.PublicStatus;
import com.pickpick.server.member.domain.enums.ShareStatus;
import com.pickpick.server.member.dto.MemberRequestDto;
import com.pickpick.server.member.dto.MemberRequestDto.CreateMemberRequestDto;
import com.pickpick.server.member.dto.MemberRequestDto.MemberSignupDto;
import com.pickpick.server.member.dto.MemberResponseDto;
import com.pickpick.server.member.dto.MemberResponseDto.IsDuplicateDTO;

public class MemberDtoConverter {

	public static MemberSignupDto convertToUserSignupDto(CreateMemberRequestDto request) {
		MemberSignupDto memberSignupDto = MemberSignupDto.builder()
				.name(request.getName())
				.email(request.getEmail())
				.phoneNum(request.getPhoneNum())
				.password(request.getPassword())
//				.imgUrl(request.getImgUrl())
				.uploadImg(request.getUploadImg())
				.publicStatus(PublicStatus.PUBLIC)
				.shareStatus(ShareStatus.NON_SHAREABLE)
				.build();
		if (request.getPublicStatus().equals("HIDDEN")) {
			memberSignupDto.setPublicStatus(PublicStatus.HIDDEN);
		}
		if (request.getShareStatus().equals("NON_SHAREABLE")) {
			memberSignupDto.setShareStatus(ShareStatus.SHAREABLE);
		}
		return memberSignupDto;
	}
	public static MemberResponseDto.IsDuplicateDTO toIsDuplicateDTO(String isDuplicate){
		if (isDuplicate.equals("true")) {
			return IsDuplicateDTO.builder().isDuplicate(true).build();
		}
		return IsDuplicateDTO.builder().isDuplicate(false).build();
	}
	public static MemberResponseDto.DeleteDTO toDeleteDTOConverter(Long userId){
		return MemberResponseDto.DeleteDTO.builder()
			.id(userId)
			.build();
	}
}
