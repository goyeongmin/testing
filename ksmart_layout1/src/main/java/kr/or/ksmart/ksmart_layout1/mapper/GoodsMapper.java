package kr.or.ksmart.ksmart_layout1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ksmart.ksmart_layout1.vo.Goods;
import kr.or.ksmart.ksmart_layout1.vo.Member;

@Mapper
public interface GoodsMapper {
	public List<Goods> getGoodsList();
	
	public List<Goods> getGoodsSearch(String sk, String sv);

	public int getGoodsCodeMax();
	
	public int addGoods(Goods goods);
	
	public int addGoods1(Goods goods);
	
	public Goods getGoodsByCode(String goodsCode);
	
	public int modifyGoods (Goods goods);
	
	public int delGoods(String goodsCode, String memberPw);
}
