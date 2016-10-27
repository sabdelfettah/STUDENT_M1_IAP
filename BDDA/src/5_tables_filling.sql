\echo '---------+---------+---------+---------+---------'
\echo '----> REMPLISSAGE DES TABLES <----'
\echo '---------+---------+---------+---------+---------'

\echo Remplissage de la table 'stade'
\echo '...'

INSERT INTO stade(nom_stade, capacite) VALUES ('Stamford Bridge', 416); --41629
INSERT INTO stade(nom_stade, capacite) VALUES ('Old Trafford', 757); --75731
INSERT INTO stade(nom_stade, capacite) VALUES ('Etihad Stadium', 600); --60000
INSERT INTO stade(nom_stade, capacite) VALUES ('Emirates Stadium', 602); --60272
INSERT INTO stade(nom_stade, capacite) VALUES ('Anfield', 453); --45362
INSERT INTO stade(nom_stade, capacite) VALUES ('White Hart Lane', 363); --36310
INSERT INTO stade(nom_stade, capacite) VALUES ('St. Mary''s Stadium', 326); --32689
INSERT INTO stade(nom_stade, capacite) VALUES ('The Liberty Stadium', 208); --20828
INSERT INTO stade(nom_stade, capacite) VALUES ('Britannia Stadium', 283); --28384
INSERT INTO stade(nom_stade, capacite) VALUES ('Goodison Park', 401); --40157
INSERT INTO stade(nom_stade, capacite) VALUES ('Upton Park', 355); --35595
INSERT INTO stade(nom_stade, capacite) VALUES ('Selhurst Park', 264); --26400
INSERT INTO stade(nom_stade, capacite) VALUES ('The Hawthorns', 278); --27877
INSERT INTO stade(nom_stade, capacite) VALUES ('St James'' Park', 523); --52389
INSERT INTO stade(nom_stade, capacite) VALUES ('Villa Park', 427); --42719
INSERT INTO stade(nom_stade, capacite) VALUES ('KC Stadium', 254); --25404
INSERT INTO stade(nom_stade, capacite) VALUES ('King Power Stadium', 325); --32500
INSERT INTO stade(nom_stade, capacite) VALUES ('Stadium of Light', 482); --48285
INSERT INTO stade(nom_stade, capacite) VALUES ('Loftus Road', 210); --21000
INSERT INTO stade(nom_stade, capacite) VALUES ('Turf Moor', 226); --22619
INSERT INTO stade(nom_stade, capacite) VALUES ('Allianz-Arena', 750); --75000
INSERT INTO stade(nom_stade, capacite) VALUES ('Camp Nou', 997); --99786

\echo '---------+---------+---------+---------+---------'

\echo Remplissage de la table 'club'
\echo '...'

INSERT INTO club(nom_club, id_stade) VALUES ('Chelsea', 1);
INSERT INTO club(nom_club, id_stade) VALUES ('Manchester United', 2);
INSERT INTO club(nom_club, id_stade) VALUES ('Manchester City', 3);
INSERT INTO club(nom_club, id_stade) VALUES ('Arsenal', 4);
INSERT INTO club(nom_club, id_stade) VALUES ('Liverpool', 5);
INSERT INTO club(nom_club, id_stade) VALUES ('Tottenham', 6);
INSERT INTO club(nom_club, id_stade) VALUES ('Southampton', 7);
INSERT INTO club(nom_club, id_stade) VALUES ('Swansea City', 8);
INSERT INTO club(nom_club, id_stade) VALUES ('Stoke City', 9);
INSERT INTO club(nom_club, id_stade) VALUES ('Everton', 10);
INSERT INTO club(nom_club, id_stade) VALUES ('West Ham', 11);
INSERT INTO club(nom_club, id_stade) VALUES ('Crystal Palace', 12);
INSERT INTO club(nom_club, id_stade) VALUES ('West Bromwich Albion', 13);
INSERT INTO club(nom_club, id_stade) VALUES ('Newcastle', 14);
INSERT INTO club(nom_club, id_stade) VALUES ('Aston Villa', 15);
INSERT INTO club(nom_club, id_stade) VALUES ('Hull City', 16);
INSERT INTO club(nom_club, id_stade) VALUES ('Leicester City', 17);
INSERT INTO club(nom_club, id_stade) VALUES ('Sunderland', 18);
INSERT INTO club(nom_club, id_stade) VALUES ('Queens Park Rangers', 19);
INSERT INTO club(nom_club, id_stade) VALUES ('Burnley', 20);
INSERT INTO club(nom_club, id_stade) VALUES ('F.C. Barcelone', 21);
INSERT INTO club(nom_club, id_stade) VALUES ('Bayern Munich', 22);
INSERT INTO club(nom_club, id_stade) VALUES ('Club A', NULL);
INSERT INTO club(nom_club, id_stade) VALUES ('CLUB B', 65);


\echo '---------+---------+---------+---------+---------'

\echo 'Remplissage de la table 'competitioon''
\echo '...'

INSERT INTO competition(nom_competition, nb_equipes_depart, nb_equipes_fin) VALUES ('Premier League', 20, 20);
INSERT INTO competition(nom_competition, nb_equipes_depart, nb_equipes_fin) VALUES ('FA Cup', 32, 1);
INSERT INTO competition(nom_competition, nb_equipes_depart, nb_equipes_fin) VALUES ('Carling Cup', 32, 1);
INSERT INTO competition(nom_competition, nb_equipes_depart, nb_equipes_fin) VALUES ('Champions League', 32, 1);
INSERT INTO competition(nom_competition, nb_equipes_depart, nb_equipes_fin) VALUES ('Championship', 24, 24);

\echo '---------+---------+---------+---------+---------'

\echo Remplissage de la table 'saison'
\echo '...'

INSERT INTO saison VALUES (2014);

\echo '---------+---------+---------+---------+---------'

\echo Remplissage de la table 'participe'
\echo '...'

INSERT INTO participe VALUES (1, 1, 2014);
INSERT INTO participe VALUES (2, 1, 2014);
INSERT INTO participe VALUES (3, 1, 2014);
INSERT INTO participe VALUES (4, 1, 2014);
INSERT INTO participe VALUES (5, 1, 2014);
INSERT INTO participe VALUES (6, 1, 2014);
INSERT INTO participe VALUES (7, 1, 2014);
INSERT INTO participe VALUES (8, 1, 2014);
INSERT INTO participe VALUES (9, 1, 2014);
INSERT INTO participe VALUES (10, 1, 2014);
INSERT INTO participe VALUES (11, 1, 2014);
INSERT INTO participe VALUES (12, 1, 2014);
INSERT INTO participe VALUES (13, 1, 2014);
INSERT INTO participe VALUES (14, 1, 2014);
INSERT INTO participe VALUES (15, 1, 2014);
INSERT INTO participe VALUES (16, 1, 2014);
INSERT INTO participe VALUES (17, 1, 2014);
INSERT INTO participe VALUES (18, 1, 2014);
INSERT INTO participe VALUES (19, 1, 2014);
INSERT INTO participe VALUES (20, 1, 2014);
INSERT INTO participe VALUES (1, 4, 2014);
INSERT INTO participe VALUES (2, 4, 2014);
INSERT INTO participe VALUES (21, 4, 2014);
INSERT INTO participe VALUES (22, 4, 2014);

\echo '---------+---------+---------+---------+---------'

\echo Remplissage de la table 'phase'
\echo '...'

INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('1ere journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('2eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('3eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('4eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('5eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('6eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('7eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('8eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('9eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('10eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('11eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('12eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('13eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('14eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('15eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('16eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('17eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('18eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('19eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('20eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('21eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('22eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('23eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('24eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('25eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('26eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('27eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('28eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('29eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('30eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('31eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('32eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('33eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('34eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('35eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('36eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('37eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('38eme journee', 20, 20);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('1/32 finale', 64, 32);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('1/16 finale', 32, 16);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('1/8 finale', 16, 8);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('1/4 finale', 8, 4);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('1/2 finale', 4, 2);
INSERT INTO phase(nom_phase, nb_equipes_depart, nb_equipes_fin) VALUES ('Finale', 2, 1);

\echo '---------+---------+---------+---------+---------'

\echo Remplissage de la table 'client'
\echo '...'

INSERT INTO client(nom_client, date_naissance, lieu_naissance) VALUES ('Dupont', '1976-02-13', 'Paris');
INSERT INTO client(nom_client, date_naissance, lieu_naissance) VALUES ('Smith', '1980-08-21', 'Londres');
INSERT INTO client(nom_client, date_naissance, lieu_naissance) VALUES ('Alves', '1978-11-10', 'Lisbone');
INSERT INTO client(nom_client, date_naissance, lieu_naissance) VALUES ('Abbas', '1973-12-02', 'Tunis');
INSERT INTO client(nom_client, date_naissance, lieu_naissance) VALUES ('UEFA', '1901-01-01', 'Bruxelles');

\echo '---------+---------+---------+---------+---------'

\echo Remplissage de la table 'matchs'
\echo '...'

INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-22', 1, 1, 2014,  1, 1); -- passe pas
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-22', 1, 1, 2014,  1, 2);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-22', 1, 1, 2014,  3, 1); -- passe pas
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-22', 1, 1, 2014,  3, 4);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-28', 1, 2, 2014,  1, 3);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-28', 1, 2, 2014,  3, 4); -- passe pas
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-28', 1, 1, 2014,  21, 4); -- passe pas
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-22', 1, 1, 2014,  5, 6);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-28', 1, 2, 2014,  2, 5);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-04-28', 1, 2, 2014,  6, 4);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-05-04', 1, 3, 2014,  1, 6);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-05-04', 1, 3, 2014,  4, 2);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-05-05', 1, 3, 2014,  3, 5);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-05-12', 4, 43, 2014,  1, 21);
INSERT INTO matchs (date_match, id_competition, id_phase, annee_debut, id_club_domicile, id_club_exterieur) VALUES ('2015-05-13', 4, 43, 2014,  2, 22);

\echo '---------+---------+---------+---------+---------'
\echo '----> FIN DU REMPLISSAGE DES TABLES <----'
\echo '---------+---------+---------+---------+---------'