\echo ---------+---------+---------+---------+---------
\echo ----> CREATION DES TABLES <----
\echo ---------+---------+---------+---------+---------

\echo Suppression d anciennes versions des tables
\echo ...
DROP TABLE IF EXISTS stade, club, competition, saison, participe, phase, matchs, client, place, billet, messagerie CASCADE;

\echo ---------+---------+---------+---------+---------

\echo Creation des nouvelles versions des tables
\echo ...

CREATE TABLE stade(
	id_stade serial PRIMARY KEY,
	nom_stade char(30) NOT NULL UNIQUE,
	capacite integer CHECK (capacite > 0)
);

CREATE TABLE club(
	id_club SERIAL PRIMARY KEY,
	nom_club char(30) NOT NULL UNIQUE,
	id_stade integer REFERENCES stade(id_stade)
);

CREATE TABLE competition(
	id_competition SERIAL PRIMARY KEY,
	nom_competition char(30) NOT NULL UNIQUE,
	nb_equipes_depart smallint CHECK (nb_equipes_depart > 0),
	nb_equipes_fin smallint CHECK (nb_equipes_fin > 0)
);

CREATE TABLE saison(
	annee_debut smallint PRIMARY KEY
);

CREATE TABLE participe(
	id_club integer REFERENCES club(id_club),
	id_competition integer REFERENCES competition(id_competition),
	annee_debut integer REFERENCES saison(annee_debut),
	PRIMARY KEY (id_club, id_competition, annee_debut)
);

CREATE TABLE phase(
	id_phase SERIAL PRIMARY KEY,
	nom_phase char(30) NOT NULL UNIQUE,
	nb_equipes_depart smallint CHECK (nb_equipes_depart > 0),
	nb_equipes_fin smallint CHECK (nb_equipes_fin > 0)
);

CREATE TABLE matchs(
	id_match SERIAL PRIMARY KEY,
	date_match date NOT NULL,
	id_club_domicile integer REFERENCES club(id_club),
	id_club_exterieur integer REFERENCES club(id_club),
	id_competition integer REFERENCES competition(id_competition),
	annee_debut integer REFERENCES saison(annee_debut),
	id_phase integer REFERENCES phase(id_phase)
);

CREATE TABLE client(
	id_client SERIAL PRIMARY KEY,
	nom_client char(30) NOT NULL,
	date_naissance date NOT NULL,
	lieu_naissance char(30) NOT NULL
);

CREATE TABLE place(
	id_place SERIAL PRIMARY KEY,
	numero_place integer NOT NULL,
	type_place char(30),
	id_stade integer REFERENCES stade(id_stade)
);

CREATE TABLE billet(
	id_billet SERIAL PRIMARY KEY,
	prix integer CHECK (prix >= 0),
	id_client integer REFERENCES client(id_client),
	id_place integer REFERENCES place(id_place),
	id_match integer REFERENCES matchs(id_match)
);

CREATE TABLE messagerie(
	id_message SERIAL PRIMARY KEY,
	id_client integer REFERENCES client(id_client),
	date_message date,
	message char(500)
);

\echo ---------+---------+---------+---------+---------
\echo ----> FIN DE LA CREATION DES TABLES <----
\echo ---------+---------+---------+---------+---------