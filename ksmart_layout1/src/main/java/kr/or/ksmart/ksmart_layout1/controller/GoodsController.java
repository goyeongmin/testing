package kr.or.ksmart.ksmart_layout1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ksmart.ksmart_layout1.service.GoodsService;
import kr.or.ksmart.ksmart_layout1.vo.Goods;
import kr.or.ksmart.ksmart_layout1.vo.Member;

@Controller
public class GoodsController {

	@Autowired private GoodsService goodsService;
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model) {

		model.addAttribute("goodsList", goodsService.getGoodsList());

		return "/goods/glist/goodsList";
	}
	
	@PostMapping("/getGoodsSearch")
	public String getGoodsSearch( @RequestParam(value="sk") String sk
								 ,@RequestParam(value="sv")	String sv
								 ,Model model) {

		model.addAttribute("goodsList", goodsService.getGoodsSearch(sk, sv));
		return "/goods/glist/goodsList";
	}
	
	@GetMapping("/addGoods")
	public String addGoods() {
		
		return "/goods/gInsert/addGoods";
	}
	
	@PostMapping("/addGoods")
	public String addGoods(Goods goods, HttpSession session) {
		
		goodsService.addGoods(goods, session);
		
		return "redirect:/goodsList";
	}
	
	/*
	 * @PostMapping("/addGoods") public String addGoods(Goods goods, HttpSession
	 * session) {
	 * 
	 * goodsService.addGoods1(goods, session);
	 * 
	 * return "redirect:/goodsList"; }
	 */
	
	@GetMapping("/modifyGoods")
	public String modifyGoods(@RequestParam(value="goodsCode")
								String goodsCode, Model model) {
		System.out.println(goodsCode + "<--goodsCode");
		
		model.addAttribute("goods", goodsService.getGoodsByCode(goodsCode));
		
		return "/goods/gUpdate/modifyGoods";
	}
	
	@PostMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {
		goodsService.modifyGoods(goods);
		return "redirect:/goodsList";
	}
	
	@GetMapping("/delGoods")
	public String delGoods(@RequestParam(value="goodsCode")
							String goodsCode, Model model) {
		model.addAttribute("goodsCode", goodsCode);
		
		return "goods/gdelete/delGoods";
	}
	@PostMapping("/delGoods")
	public String delGoods(Goods goods,Model model) {
		
		int result = goodsService.deleteGoods(goods.getGoodsCode(), 
												goods.getMemberPw());
		
	
		if(result == 0) {
			model.addAttribute("result",("비밀번호가 일치하지 않습니다."));
			model.addAttribute("goodsCode", goods.getGoodsCode());
			return "goods/gdelete/delGoods";
		}
		
		return "redirect:/goodsList";
	}
	
}
