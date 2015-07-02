create or replace function reunion$after_insert_row$100() returns trigger as $$
begin
    perform reunion$actualizar_lista_salas_disponibles(null, new.id);
    return null;
end;
$$ language plpgsql;
