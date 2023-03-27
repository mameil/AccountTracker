create sequence usage_transaction_seq
    start with 1
    increment by 1
    maxvalue 99999
    cycle
    cache 20;


-- 그냥 postman 으로 때려박는 방법 선택 >> swagger 설정 시급..
insert into tag values (1, now(), now(), 'RED', '음식');
insert into tag values (2, now(), now(), 'ORANGE', '디저트');
insert into tag values (3, now(), now(), 'YELLOW', '인터넷쇼핑');
insert into tag values (4, now(), now(), 'GREEN', '편의점');
insert into tag values (5, now(), now(), 'BLUE', '술값');
insert into tag values (6, now(), now(), 'NAVY', '생활용품');
insert into tag values (7, now(), now(), 'PURPLE', '미용');
insert into tag values (8, now(), now(), 'SKYBLUE', '통신비');
insert into tag values (9, now(), now(), 'GRAY', '의류');
insert into tag values (10, now(), now(), 'IVORY', '저축');