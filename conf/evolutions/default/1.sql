# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table hub (
  id                        varchar(255) not null,
  hardware_ver              varchar(255),
  firmware_ver              varchar(255),
  software_ver              varchar(255),
  constraint pk_hub primary key (id))
;

create table phone (
  id                        varchar(255) not null,
  hub_id                    varchar(255),
  model                     varchar(255),
  manufacturer              varchar(255),
  os                        varchar(255),
  os_ver                    varchar(255),
  constraint pk_phone primary key (id))
;

alter table phone add constraint fk_phone_hub_1 foreign key (hub_id) references hub (id) on delete restrict on update restrict;
create index ix_phone_hub_1 on phone (hub_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table hub;

drop table phone;

SET FOREIGN_KEY_CHECKS=1;

