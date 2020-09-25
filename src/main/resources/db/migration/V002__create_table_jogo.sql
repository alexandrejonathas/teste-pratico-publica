create table tbl_jogo (
    id bigint not null auto_increment,
    time_casa bigint not null,
    time_visitante bigint not null,
    data date not null,
    
    primary key(id)
)engine=InnoDB default charset=utf8;

alter table tbl_jogo add constraint fk_jogo_time_casa
foreign key (time_casa) references tbl_time (id);

alter table tbl_jogo add constraint fk_jogo_time_visitante
foreign key (time_visitante) references tbl_time (id);