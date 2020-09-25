set foreign_key_checks = 0;

delete from tbl_pontuacao;
delete from tbl_jogo;
delete from tbl_time;

alter table tbl_time auto_increment = 1;
alter table tbl_jogo auto_increment = 1;
alter table tbl_pontuacao auto_increment = 1;

insert into tbl_time(nome) values ('Time da maria'), ('Visitante 1'), ('Visitante 2'), ('Visitante 3'), ('Visitante 4');

insert into tbl_jogo(time_casa, time_visitante, data) 
values (1, 2, '2020-09-21'), (3, 1, '2020-09-22'), (4, 1, '2020-08-23'), (1, 3, '2020-08-24');;

insert into tbl_pontuacao(jogo_id, placar, minimo_temporada, maximo_temporada, quebra_recorde_minimo, quebra_recorde_maximo) 
values (1, 12, 12, 12, 0, 0), (2, 24, 12, 24, 0, 1), (3, 10, 10, 24, 1, 1), (4, 24, 10, 24, 1, 1); 