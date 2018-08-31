package top.neospot.cloud.cart.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.neospot.cloud.cart.domain.entity.Cart;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@Mapper
public interface CartMapper {
    @Select("select id, cart_line_ids as lines, user_id as userId from cart where id=#{id}")
    Cart findById(Long id);

    @Insert("insert into cart (cart_line_ids, user_id) values (#{lines}, #{userId})")
    void save(Cart cart);

    @Select("select id, cart_line_ids as lines, user_id as userId from cart")
    List<Cart> findAll();
}
