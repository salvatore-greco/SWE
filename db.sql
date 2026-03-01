create table if not exists book(
code varchar(10) primary key,
ISBN character(13),
title text
);
-- non posso usare if not exists su create type quindi:
DO $$
BEGIN
	if not exists(select 1 from pg_type where typname = 'role') then
		CREATE type role AS ENUM ('librarian', 'libraryUser', 'libraryAdministrator');
	end if;
end;
$$;

-- user è una parola chiave per postgresql, per chiamare la tabella user devo racchiuderla fra virgolette
create table if not exists "user"(
email varchar(254) primary key,
name varchar(50) ,
surname varchar(50) ,
role role,
password varchar(72) not null
);

create table if not exists card(
id integer generated always as identity primary key,
issueDate date,
expirationDate date,
"user" varchar(254) constraint user_fk_card references "user" unique not null
);

create table if not exists loan(
book varchar(10) constraint book_fk_loan references book primary key,
card integer constraint card_fk_loan references card not null,
issueDate timestamp,
expirationDate timestamp,
granted boolean,
ended boolean
);

create table if not exists "library"(
name varchar(50) primary key,
budget smallint 
);

create table if not exists manage(
"user" varchar(254) constraint user_fk_manage references "user" primary key,
"library" varchar(50) constraint library_fk_manage references "library" not null
);

create table if not exists room(
number smallint primary key,
seats smallint
);

create table if not exists "event"(
id integer generated always as identity primary key,
name varchar(50),
description text,
date timestamp,
duration interval,
organizer varchar(254) constraint user_fk_event references "user" not null,
room smallint constraint room_fk references room not null
);

create table if not exists reservation_study_room(
"user" varchar(254) constraint user_fk_reservation references "user" primary key,
room smallint constraint room_fk_reservation references room not null
);

create table if not exists partecipation(
"event" integer constraint event_fk_partecipation references "event" primary key,
"user" varchar(254) constraint user_fk_partecipation references "user" not null
);