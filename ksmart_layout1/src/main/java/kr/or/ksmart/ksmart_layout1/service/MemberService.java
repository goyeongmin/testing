package kr.or.ksmart.ksmart_layout1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ksmart.ksmart_layout1.mapper.MemberMapper;
import kr.or.ksmart.ksmart_layout1.vo.Member;

@Service
@Transactional 
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	public Map<String, Object> getMemberLogin(Member member) {
		
		String result = "";
		
		Map<String, Object> map = new HashMap<String,Object>();
		
		Member memberCheck = memberMapper.getMemberLogin(member);
		
		if(memberCheck == null) {
			Member memberIdCheck = memberMapper.getMemberById(
					member.getMemberId());
			if(memberIdCheck == null) {
				result = "등록된 아이디의 정보가 없습니다.";
			}else {
				result = "비밀번호가 일치하지 않습니다.";
			}

		}else {
			result = "로그인 성공";
			map.put("loginMember", memberCheck);
		}
		
		map.put("result", result);
		
		return map;
	}
	
	public List<Member> getMemberList(){
		List<Member> list = memberMapper.getMemberList();
		return list;
	}
	
	public List<Member> getMemberSearchList(String sk, String sv){
		List<Member> list = memberMapper.getMemberSearchList(sk, sv);
		
		return list;
	}
	
	public int addMember(Member member) {
		int result = memberMapper.addMember(member);
		return result;
	}
	
	public Member getMemberById(String memberId) {
		
		return memberMapper.getMemberById(memberId);
	}
	
	public int modifyMember(Member member) {
		return memberMapper.modifyMember(member);
	}
	
	public int deleteMember(String memberId, String memberPw) {
		return memberMapper.delMember(memberId, memberPw);
	}
	
}
