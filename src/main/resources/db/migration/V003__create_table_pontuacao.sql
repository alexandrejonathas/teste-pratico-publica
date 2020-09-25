create table tbl_pontuacao (
    id bigint not null auto_increment,
    jogo_id bigint not null,
    placar int not null,
    minimo_temporada int not null,
    maximo_temporada int not null,
    quebra_recorde_minimo int not null,
    quebra_recorde_maximo int not null,
    
    primary key(id)
)engine=InnoDB default charset=utf8;

alter table tbl_pontuacao add constraint fk_pontuacao_jogo_id
foreign key (jogo_id) references tbl_jogo (id);