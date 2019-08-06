package kr.or.ksmart.ksmart_layout1.controller;

import java.awt.Component;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ksmart.ksmart_layout1.service.MemberService;
import kr.or.ksmart.ksmart_layout1.vo.Member;

@Controller
public class MemberController {
	
	@Autowired MemberService memberService;
	
	@GetMapping("/login")
	public String login() {
		
		return "/login/login";
	}
	
	@PostMapping("/login")
	public String login(Member member, HttpSession session, Model model) {
		//입력된 아이디 비밀번호
		System.out.println(member.toString() + "<--입력된 정보");
		
		Map<String,Object> map = memberService.getMemberLogin(member);
		String result 		= (String) map.get("result"); 
		Member loginMember 	= (Member) map.get("loginMember");
		
		//로그인 실패 화면 login
		if(!result.equals("로그인 성공")) {
			model.addAttribute("result", result);
			return "/login/login";
		}
		session.setAttribute("SID"		, loginMember.getMemberId());
		session.setAttribute("SLEVEL"	, loginMember.getMemberLevel());
		session.setAttribute("SNAME"	, loginMember.getMemberName());
		
		//로그인 성공 화면 index
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	
	@GetMapping("/memberList")
	public String getMemberList(Model model) {
		List<Member> list = memberService.getMemberList();
		
		model.addAttribute("memberList", list);
		
		return "/member/mlist/memberList";
	}
	
	@PostMapping("/memberList")
	public String getMemberList( @RequestParam(value="sk") String sk
								,@RequestParam(value="sv") String sv
								,Model model) {
		List<Member> list = memberService.getMemberSearchList(sk, sv);
		model.addAttribute("memberList", list);
		
		return "/member/mlist/memberList";
	}
	
	@GetMapping("/addMember")
	public String addMember() {
		return "/member/mInsert/addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member, Model model) {
		System.out.println(member);
		Member memberCheck = memberService.getMemberById(member.getMemberId());
		
		if(memberCheck != null) {
			model.addAttribute("result", "동일한 아이디가 존재합니다.");
			return "/member/mInsert/addMember";
		}
		
		memberService.addMember(member);
		
		return "redirect:/memberList";
	}
	
	@GetMapping("/modifyMember")
	public String modifyMember(@RequestParam(value="memberId")
								String memberId, Model model) {
		System.out.println(memberId + "<--memberId");
		
		model.addAttribute("member", memberService.getMemberById(memberId));
		
		return "/member/mUpdate/modifyMember";
	}
	
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		memberService.modifyMember(member);
		return "redirect:/memberList";
	}
	
	@GetMapping("/delMember")
	public String delMember(@RequestParam(value="memberId")
							String memberId, Model model) {
		model.addAttribute("memberId", memberId);
		
		return "member/mdelete/delMember";
	}
	@PostMapping("/delMember")
	public String delMember(Member member,Model model) {
		
		int result = memberService.deleteMember(member.getMemberId(), 
												member.getMemberPw());
		
		if(result == 0) {
			model.addAttribute("result",("비밀번호가 일치하지 않습니다."));
			model.addAttribute("memberId", member.getMemberId());
			return "member/mdelete/delMember";
		}
		
		return "redirect:/memberList";
	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	
}
