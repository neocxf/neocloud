insert into inventory_item(id, quantity, product_code, create_time, last_update_time)
VALUES (1, 2, 'p1', now(), now()),
       (2, 5, 'p2', now(), now()),
       (3, 10, 'p3', now(), now())
on duplicate key update product_code=values(product_code);