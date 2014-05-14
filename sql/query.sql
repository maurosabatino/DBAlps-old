insert into utente(nome,cognome,username,password,ruolo,attivo) values ('mauro','sabatino','sabawalker','tonino','amministratore',true)

select * from utente

select * from public.allegati  where  id

alter table allegati add linkfile text


sb.append("select *,st_x(coordinate::geometry) as x ,st_y(coordinate::geometry) as y, l.nome_it as lito_it,l.nome_eng as lito_eng,pt.nome_it as pt_it,pt.nome_eng as pt_eng,sf.nome_it as sf_it,sf.nome_eng as sf_eng ");
sb.append(" ");
		sb.append(" litologia l,proprieta_termiche pt, stato_fratturazione sf, sito_processo sp,classi_volume cv");
		sb.append(" where proc.idubicazione = u.idubicazione and proc.idprocesso="+idProcesso+"");
		sb.append(" and(c.idProvincia=p.idProvincia) and ( r.idregione=p.idregione) and(r.idnazione=n.idnazione) and c.idcomune=u.idcomune and");
		sb.append(" b.idbacino=s.idbacino and s.idsottobacino=u.idsottobacino and ");
		sb.append(" proc.idlitologia = l.idlitologia and pt.idproprietatermiche=proc.idproprietatermiche and sp.idsitoprocesso=proc.idsito and proc.idclassevolume=cv.idclassevolume");




select * from processo proc 
left join sito_processo sp on (proc.idsito=sp.idsitoprocesso) 
left join classi_volume cv  on (proc.idclassevolume=cv.idclassevolume)
left join litologia l on(proc.idlitologia = l.idlitologia)
left join proprieta_termiche pt on (pt.idproprietatermiche=proc.idproprietatermiche)
left join stato_fratturazione sf on (proc.idstatofratturazione=sf.idstatofratturazione)
left join ubicazione u on (proc.idubicazione=u.idubicazione)
left join comune c on (c.idcomune=u.idcomune)
left join provincia p on (c.idProvincia=p.idProvincia)
left join regione r on ( r.idregione=p.idregione)
left join nazione n on (r.idnazione=n.idnazione)
left join sottobacino s on (s.idsottobacino=u.idsottobacino) 
left join bacino b on (b.idbacino=s.idbacino)
where proc.idprocesso=6



