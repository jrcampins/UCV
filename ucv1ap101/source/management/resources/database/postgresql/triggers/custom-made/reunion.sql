drop trigger if exists reunion$after_insert_row$100 on reunion cascade;
create trigger reunion$after_insert_row$100 after insert on reunion
    for each row execute procedure reunion$after_insert_row$100();

drop trigger if exists reunion$after_update_row$100 on reunion cascade;
create trigger reunion$after_update_row$100 after update on reunion
    for each row execute procedure reunion$after_update_row$100();
