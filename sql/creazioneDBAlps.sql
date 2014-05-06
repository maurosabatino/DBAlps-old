
--utenti 
create table if not exists utente(
idutente serial primary key not null,
nome varchar(200),
cognome varchar(200),
username varchar(40),
password varchar(40),
ruolo varchar(20),
email varchar(40),
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
FOREIGN KEY (idBacino) references bacino(idBacino)
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
FOREIGN KEY (idNazione) REFERENCES nazione (idNazione)
);

CREATE TABLE IF NOT EXISTS provincia(
idProvincia serial not null primary key,
idRegione integer not null,
nomeProvincia varchar(50) not null unique,
sigla varchar(2) not null,
FOREIGN KEY (idRegione) REFERENCES regione (idRegione)
);


CREATE TABLE IF NOT EXISTS comune(
idComune serial primary key not null,
idProvincia integer not null,
nomeComune varchar(50)not null,
FOREIGN KEY (idProvincia) REFERENCES provincia (idProvincia)
);

CREATE TABLE IF NOT EXISTS ubicazione(
idUbicazione serial primary key not null,
idComune integer not null,
idSottobacino integer not null,
quota double precision,
esposizione varchar(10),
coordinate geometry(point),
FOREIGN KEY (idComune) REFERENCES comune (idComune),
FOREIGN KEY (idSottobacino) REFERENCES sottobacino (idSottobacino)
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
nome text not null,
idubicazione integer not null references ubicazione(idubicazione),
idsito integer references sito_processo(idsitoprocesso),
data timestamp not null,
idutentecreatore integer not null references utente(idutente),
idutentemodifica integer not null references utente(idutente),
descrizione text,
note text,
superficie double precision,
larghezza double precision,
altezza double precision,
volumeSpecifico double precision,
convalidato boolean not null,
idclassevolume integer references classi_volume(idclassevolume),
idlitologia integer references litologia(idlitologia),
idproprietatermiche integer references proprieta_termiche(idproprietatermiche),
idstatofratturazione integer references stato_fratturazione(idstatofratturazione),
unique(nome,data)
); 

create table if not exists effetti_processo(
idprocesso integer references processo(idprocesso),
ideffettimorfologici integer references effetti_morfologici(ideffettimorfologici)
)

create table if not exists danni_processo(
iddanniprocesso serial not null primary key,
idprocesso integer references processo(idprocesso),
iddanno integer references danno(iddanno)
)

create table if not exists caratteristiche_processo(
idcaratteristicheprocesso serial not null primary key,
idprocesso integer references processo(idprocesso),
idtipologiaProcesso integer references tipologia_processo(idtipologiaprocesso)
)

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
idEnte integer REFERENCES ente(idEnte) not null,
idSitostazione integer REFERENCES sito_stazione(idSitostazione) not null,
idUbicazione integer REFERENCES ubicazione(idUbicazione) not null,
idutentecreatore integer not null references utente(idutente),
nome varchar(100) not null,
aggregazioneGiornaliera varchar(100) not null,
oraria boolean not null,
note text,
datainizio timestamp,-- forse not null?
datafine timestamp
);

create table if not exists sensore_stazione(
idSensore integer REFERENCES sensore(idSensore),
idStazionemetereologica integer REFERENCES stazione_metereologica(idStazionemetereologica)
);

create table if not exists neve(
idneve serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
neve double precision,
data timestamp not null
);

create table if not exists precipitazione(
idPrecipitazione serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
quantita double precision,
data timestamp not null
);

create table if not exists temperatura_avg(
idTemperaturaavg serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
temperaturaavg double precision,
data timestamp not null
);

create table if not exists temperatura_max(
idTemperaturamax serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
temperaturamax double precision,
data timestamp not null
);

create table if not exists temperatura_min(
idTemperaturamin serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
temperaturamin double precision,
data timestamp not null
);

create table if not exists vento(
idvento serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
nome varchar(40),
direzione varchar(8),
velocita double precision,
data timestamp not null
);

create table if not exists rain(
idRain serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
quantita double precision,
data timestamp not null
);

create table if not exists radSol(
idRadsol serial primary key not null,
idStazionemetereologica integer not null REFERENCES stazione_metereologica(idStazionemetereologica),
quantita double precision,
data timestamp not null
);

--allegati
create table if not exists allegati(
idAllegati serial not null primary key,
nome text not null,
data timestamp not null,
fonte text not null,
tipoallegato text not null,
url text,
idutente integer not null references utente(idutente),
file bytea--?????
);

create table if not exists allegati_Processo(
idProcesso integer references processo(idprocesso),
idallegati integer references allegati(idallegati)
);

create table if not exists allegati_Stazione(
idStazione integer references stazione_metereologica(idstazionemetereologica),
idallegati integer references allegati(idallegati)
);

