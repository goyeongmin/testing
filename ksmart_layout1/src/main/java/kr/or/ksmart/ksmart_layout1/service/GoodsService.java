package kr.or.ksmart.ksmart_layout1.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ksmart.ksmart_layout1.mapper.GoodsMapper;
import kr.or.ksmart.ksmart_layout1.vo.Goods;
import kr.or.ksmart.ksmart_layout1.vo.Member;

@Service
@Transactional
public class GoodsService {
	@Autowired private GoodsMapper goodsMapper;
	
	public List<Goods> getGoodsList(){
		return goodsMapper.getGoodsList();
	}
	
	public List<Goods> getGoodsSearch(String sk, String sv){
		return goodsMapper.getGoodsSearch(sk, sv);
	}
	
	public int addGoods(Goods goods, HttpSession session) {
		int max = goodsMapper.getGoodsCodeMax()+1;
		String tempCode = "goods_";
		
		goods.setMemberId((String) session.getAttribute("SID"));
		goods.setGoodsCode(tempCode+max);
		
		return goodsMapper.addGoods(goods);
	}
	
	public int addGoods1(Goods goods, HttpSession session) {
		
		goods.setMemberId((String) session.getAttribute("SID"));
		
		return goodsMapper.addGoods1(goods);
	}
	
	public Goods getGoodsByCode(String goodsCode) {
		
		return goodsMapper.getGoodsByCode(goodsCode);
	}
	
	public int modifyGoods(Goods goods) {
		return goodsMapper.modifyGoods(goods);
	}
	public int deleteGoods(String goodsCode, String memberPw) {
		return goodsMapper.delGoods(goodsCode, memberPw);
	}

}
