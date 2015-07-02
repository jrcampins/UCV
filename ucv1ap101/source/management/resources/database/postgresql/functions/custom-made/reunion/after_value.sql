create or replace function reunion$after_value(_new$ reunion)
returns reunion as $$
begin
    _new$.fecha_hora_inicio_deseada := _new$.fecha_inicio_deseada + _new$.hora_inicio_deseada;
    _new$.fecha_hora_fin_deseada := _new$.fecha_fin_deseada + _new$.hora_fin_deseada;
    if _new$.fecha_inicio_pautada is not null and _new$.hora_inicio_pautada is not null then
        _new$.fecha_hora_inicio_pautada := _new$.fecha_inicio_pautada + _new$.hora_inicio_pautada;
    else
        _new$.fecha_hora_inicio_pautada := null;
    end if;
    if _new$.fecha_fin_pautada is not null and _new$.hora_fin_pautada is not null then
        _new$.fecha_hora_fin_pautada := _new$.fecha_fin_pautada + _new$.hora_fin_pautada;
    else
        _new$.fecha_hora_fin_pautada := null;
    end if;
 return _new$;
end;
$$ language plpgsql;
