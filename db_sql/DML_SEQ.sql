create sequence usage_transaction_seq
    start with 1
    increment by 1
    maxvalue 99999
    cycle
    cache 20;


-- 아 mysql에서 한국어로 insert 박아넣고 있다가 한국어 안들어가는 에러 때문에 docker 다시 깔아야할듯한데
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