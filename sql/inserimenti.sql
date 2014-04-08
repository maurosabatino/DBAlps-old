INSERT INTO nazione (nomenazione) VALUES
( 'Austria'),
( 'Svizzera'),
( 'Slovenia'),
('Francia'),
( 'Italia');

INSERT INTO regione (nomeRegione, idNazione) VALUES
(  'Piemonte',5);

INSERT INTO provincia (idregione,nomeprovincia,sigla) VALUES
(1, 'Torino', 'TO'),
(1, 'Vercelli', 'VC'),
(1,  'Novara', 'NO'),
(1,  'Cuneo', 'CN');

INSERT INTO comune(idprovincia, nomecomune) VALUES
(1, 'Cantagallo'),
(2, 'Carmignano'),
(1, 'Montemurlo'),
(2, 'Poggio a Caiano');

insert into bacino(nomebacino) values
('po');

insert into sottobacino(idbacino,nomesottobacino) values
(1,'sottopo');


INSERT INTO sito_stazione (caratteristiche_it,caratteristiche_eng) VALUES
( 'spartiacque','0f'),
( 'versante','f');

insert into ente(nome) values 
('AEM Azienda Energetica Metropolitana'),
('AIPO Agenzia Interregionale per il fiume Po'),
('AM Aeronautica Militare'),
('ARPA Abruzzo'),
('ARPA Basilicata'),
('ARPA Calabria'),
('ARPA Campania'),
('ARPA Emilia-Romagna'),
('ARPA Friuli-Venezia Giulia'),
('ARPA Lazio'),
('ARPA Liguria'),
('ARPA Lombardia'),
('ARPA Marche');

insert into sensore(tipo_it,tipo_eng) values
('pioggia','r'),
('neve','n');

INSERT INTO sito_processo (caratteristica_it,caratteristica_eng) VALUES
( 'spartiacque','0f'),
( 'versante','f');