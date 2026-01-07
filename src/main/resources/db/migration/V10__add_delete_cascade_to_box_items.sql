alter table if exists items drop constraint FK3ajgioeb0uvkjt5rlbstqxuuw;
alter table if exists items add constraint FK3ajgioeb0uvkjt5rlbstqxuuw foreign key (box_id) references boxes ON DELETE CASCADE;