--utenti 
create table if not exists utente(
idutente serial primary key not null,
nome varchar(200),
cognome varchar(200),
username varchar(40),
password varchar(40),
ruolo varchar(20),
email varchar(40),
attivo boolean,
datacreazione timestamp,
dataultimoaccesso timestamp
);

create table if not exists tracciautente(
idtraccia serial primary key not null,
idutente integer not null,
data timestamp,
tabella varchar(20),
operazione varchar(20),
idstazione integer,
datainzio timestamp,
datafine timestamp,
idprocesso integer
);
--ubicazione
create table if not exists bacino(
idBacino serial primary key not null,
nomeBacino varchar(45) not null unique
);

create table if not exists sottobacino(
idSottobacino serial not null primary key,
idBacino integer not null,
nomesottoBacino varchar(45) not null unique,
FOREIGN KEY (idBacino) references bacino(idBacino) on delete cascade 
);

CREATE TABLE IF NOT EXISTS nazione (
idNazione serial primary key,
nomeNazione varchar(150) not null unique
);

CREATE TABLE IF NOT EXISTS regione (
idRegione serial primary key,
areaGeog varchar(30),
nomeRegione varchar(100) not null unique,
idNazione integer NOT NULL,
FOREIGN KEY (idNazione) REFERENCES nazione (idNazione) on delete cascade 
);

CREATE TABLE IF NOT EXISTS provincia(
idProvincia serial not null primary key,
idRegione integer not null,
nomeProvincia varchar(50) not null unique,
sigla varchar(2) not null,
FOREIGN KEY (idRegione) REFERENCES regione (idRegione) on delete cascade
);


CREATE TABLE IF NOT EXISTS comune(
idComune serial primary key not null,
idProvincia integer not null,
nomeComune varchar(50)not null,
FOREIGN KEY (idProvincia) REFERENCES provincia (idProvincia) on delete cascade 
);

CREATE TABLE IF NOT EXISTS ubicazione(
idUbicazione serial primary key not null,
idComune integer,
idSottobacino integer,
quota double precision,
esposizione varchar(10),
coordinate geography(point),
affidabilita varchar(45),
FOREIGN KEY (idComune) REFERENCES comune (idComune) on delete no action, 
FOREIGN KEY (idSottobacino) REFERENCES sottobacino (idSottobacino) on delete no action
);

--ubicazione


--Processo--
create table if not exists proprieta_termiche(
idProprietaTermiche serial primary key not null,
nome_IT varchar(200) not null unique,
nome_ENG varchar(200) not null unique
);

create table if not exists Stato_Fratturazione(
idStatoFratturazione serial not null primary key,
nome_IT varchar(200) not null unique,
nome_ENG varchar(200) not null unique
);

create table if not exists litologia(
idLitologia serial not null primary key,
nome_IT varchar(200) not null unique,
nome_Eng varchar(200) not null unique
);

CREATE table if not exists sito_processo(
idSitoprocesso serial not null primary key,
caratteristica_IT varchar(200) not null unique,
caratteristica_ENG varchar(200) not null unique
);

create table if not exists tipologia_Processo(
idTipologiaProcesso serial not null primary key,
nome_IT varchar(200) not null unique,
nome_ENG varchar(200) not null unique
);

create table if not exists danno(
idDanno serial not null primary key,
tipo_IT varchar(200) not null unique,
tipo_ENG varchar(200) not null unique
);

create table if not exists effetti_Morfologici(
idEffettiMorfologici serial not null primary key,
tipo_IT varchar(200) not null unique,
tipo_ENG varchar(200) not null unique
);

CREATE table if not exists classi_volume(
idclassevolume serial not null primary key,
intervallo double precision not null
);

CREATE table if not exists processo(
idprocesso serial not null primary key,
nome text,
idubicazione integer references ubicazione(idubicazione) on delete no action,
idsito integer references sito_processo(idsitoprocesso) on delete no action,
data timestamp not null,
formatodata integer not null,
idutentecreatore integer not null references utente(idutente) on delete no action,
idutentemodifica integer references utente(idutente) on delete no action,
descrizione text,
note text,
superficie double precision,
larghezza double precision,
altezza double precision,
volumeSpecifico double precision,
convalidato boolean not null,
idclassevolume integer references classi_volume(idclassevolume) on delete no action,
idlitologia integer references litologia(idlitologia) on delete no action,
idproprietatermiche integer references proprieta_termiche(idproprietatermiche) on delete no action,
idstatofratturazione integer references stato_fratturazione(idstatofratturazione) on delete no action,
unique(nome,data)
); 

create table if not exists effetti_processo(
idprocesso integer references processo(idprocesso) on delete cascade ,
ideffettimorfologici integer references effetti_morfologici(ideffettimorfologici) on delete no action
);

create table if not exists danni_processo(
iddanniprocesso serial not null primary key,
idprocesso integer references processo(idprocesso) on delete cascade,
iddanno integer references danno(iddanno) on delete no action
);

create table if not exists caratteristiche_processo(
idcaratteristicheprocesso serial not null primary key,
idprocesso integer references processo(idprocesso) on delete cascade,
idtipologiaProcesso integer references tipologia_processo(idtipologiaprocesso) on delete no action
);

--fine processo--

--dati climatici
create table if not exists ente(
idEnte serial primary key not null,
nome varchar(100) not null unique
);

create table if not exists sito_stazione(
idSitostazione serial primary key not null,
caratteristiche_IT varchar(100) not null unique,
caratteristiche_ENG varchar(100) not null unique
);

create table if not exists sensore(
idSensore serial primary key not null,
tipo_IT varchar(100) not null unique,
tipo_ENG varchar(100) not null unique
);

create table if not exists stazione_metereologica(
idStazionemetereologica serial primary key not null,
nome varchar(100) not null,
idEnte integer REFERENCES ente(idEnte),
idSitostazione integer REFERENCES sito_stazione(idSitostazione) on delete no action,
idUbicazione integer not null REFERENCES ubicazione(idUbicazione)  on delete no action,
idutentecreatore integer references utente(idutente) on delete no action,
aggregazioneGiornaliera varchar(100),
note text,
datainizio timestamp,-- forse not null?
datafine timestamp
);

create table if not exists sensore_stazione(
idSensore integer REFERENCES sensore(idSensore) on delete cascade,
idStazionemetereologica integer REFERENCES stazione_metereologica(idStazionemetereologica) on delete no action
);

create table if not exists neve(
idneve serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
neve double precision,
data timestamp not null,
oraria boolean not null
);

create table if not exists precipitazione(
idPrecipitazione serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
quantita double precision,
data timestamp not null,
oraria boolean not null
);

create table if not exists temperatura_avg(
idTemperaturaavg serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
temperaturaavg double precision,
data timestamp not null,
oraria boolean not null
);

create table if not exists temperatura_max(
idTemperaturamax serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
temperaturamax double precision,
data timestamp not null,
oraria boolean not null
);

create table if not exists temperatura_min(
idTemperaturamin serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
temperaturamin double precision,
data timestamp not null,
oraria boolean not null
);

create table if not exists vento(
idvento serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
nome varchar(40),
direzione varchar(8),
velocita double precision,
data timestamp not null,
oraria boolean not null
);

create table if not exists rain(
idRain serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
quantita double precision,
data timestamp not null,
oraria boolean not null
);

create table if not exists radSol(
idRadsol serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica) on delete cascade,
quantita double precision,
data timestamp not null,
oraria boolean not null
);

--allegati
create table if not exists allegati(
idAllegati serial not null primary key,
autore text not null,
titolo text not null,
anno text not null,
fonte text not null,
tipoallegato text,
urlweb text,
nella text,
idutente integer not null references utente(idutente),
linkfile text,
data timestamp
);

create table if not exists allegati_Processo(
idProcesso integer references processo(idprocesso),
idallegati integer references allegati(idallegati)
);

create table if not exists allegati_Stazione(
idStazione integer references stazione_metereologica(idstazionemetereologica),
idallegati integer references allegati(idallegati)
);

--indici
create index t_avg_index on temperatura_avg(idStazionemetereologica);
create index t_min_index on temperatura_min(idStazionemetereologica);
create index t_max_index on temperatura_max(idStazionemetereologica);
create index rain_index on rain(idStazionemetereologica);
create index vento_index on vento(idStazionemetereologica);
create index neve_index on neve(idStazionemetereologica);
create index precipitazione_index on precipitazione(idStazionemetereologica);
create index radSol_index on radsol(idStazionemetereologica);

