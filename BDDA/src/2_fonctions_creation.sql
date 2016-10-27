\echo ---------+---------+---------+---------+---------
\echo ----> CREATION DES FONCTIONS <----
\echo ---------+---------+---------+---------+---------

\echo Suppression d anciennes versions des fonctions
\echo ...

DROP FUNCTION type_competition(competition.id_competition%TYPE);
DROP FUNCTION est_championnat(competition.id_competition%TYPE);
DROP FUNCTION est_coupe(competition.id_competition%TYPE);
DROP FUNCTION nombre_places_libres_total(matchs.id_match%TYPE);
DROP FUNCTION participe_a_la_competition(club.id_club%TYPE, competition.id_competition%TYPE, saison.annee_debut%TYPE);
DROP FUNCTION deja_present(club.id_club%TYPE, competition.id_competition%TYPE, phase.id_phase%TYPE, saison.annee_debut%TYPE);
DROP FUNCTION surcharge(club.id_club%TYPE, date);
DROP FUNCTION a_deja_un_match(club.id_club%TYPE, date);
DROP FUNCTION max_match(competition.id_competition%TYPE, phase.id_phase%TYPE, saison.annee_debut%TYPE);
DROP FUNCTION recherche_matchs(competition.id_competition%TYPE, phase.id_phase%TYPE, saison.annee_debut%TYPE, club.id_club%TYPE);
DROP FUNCTION reserver_match(matchs.id_match%TYPE, client.id_client%TYPE);
DROP FUNCTION reserver_matchs_competition(competition.id_competition%TYPE, saison.annee_debut%TYPE, club.id_club%TYPE, client.id_client%TYPE);
DROP FUNCTION reporter_match(matchs.id_match%TYPE, matchs.date_match%TYPE);
DROP FUNCTION annuler_reservation(matchs.id_match%TYPE, client.id_client%TYPE);
DROP FUNCTION stats_match(matchs.id_match%TYPE);
DROP FUNCTION stats_club(club.id_club%TYPE, saison.annee_debut%TYPE);
DROP FUNCTION stats_competition(competition.id_competition%TYPE, saison.annee_debut%TYPE);

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'type_competition'
\echo '...'

CREATE OR REPLACE FUNCTION type_competition(competition.id_competition%TYPE) RETURNS TEXT AS $$
DECLARE
	id_compet ALIAS FOR $1;
	nb_d competition.nb_equipes_depart%TYPE;
	nb_f competition.nb_equipes_fin%TYPE;
	resultat TEXT;
BEGIN
	SELECT c.nb_equipes_depart, c.nb_equipes_fin INTO nb_d, nb_f FROM competition c WHERE c.id_competition = id_compet;
	IF nb_d > 0 AND nb_f > 0 THEN
		IF nb_d = nb_f THEN
			--resultat = 'Championnat'::CHAR(30);
			resultat := 'Championnat';
		ELSEIF nb_d > 1 AND nb_f = 1 THEN
			--resultat = 'Coupe'::CHAR(30);
			resultat := 'Coupe';
		ELSE
			--resultat = 'Non roconnu'::CHAR(30);
			resultat := 'Non roconnu';
		END IF;
	ELSE
		RAISE NOTICE 'Nombre d''equipes de depart et/ou de fin de la competition errone';
		--resultat = 'Nombres faux'::CHAR(30);
		resultat := 'Nombres faux';
	END IF;
	RETURN resultat;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'est_championnat'
\echo '...'

CREATE OR REPLACE FUNCTION est_championnat(competition.id_competition%TYPE) RETURNS BOOLEAN AS $$
DECLARE
	resultat TEXT;
BEGIN
	resultat := type_competition($1);
	IF resultat = 'Championnat' THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'est_coupe'
\echo '...'

CREATE OR REPLACE FUNCTION est_coupe(competition.id_competition%TYPE) RETURNS BOOLEAN AS $$
BEGIN
	IF type_competition($1) = 'Coupe' THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'nombre_places_libres_total'
\echo '...'

CREATE OR REPLACE FUNCTION nombre_places_libres_total(matchs.id_match%TYPE) RETURNS INTEGER AS $$
DECLARE
	id_m ALIAS FOR $1;
	nb_p_l INTEGER;
BEGIN
	SELECT count(*) INTO nb_p_l FROM billet WHERE id_match = id_m AND id_client = -1;
	RETURN nb_p_l;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'nombre_places_libres_cat'
\echo '...'

CREATE OR REPLACE FUNCTION nombre_places_libres_cat(matchs.id_match%TYPE, place.type_place%TYPE) RETURNS INTEGER AS $$
DECLARE
	id_m ALIAS FOR $1;
	type_p ALIAS FOR $2;
	nb_p_l INTEGER;
BEGIN
	SELECT count(*) INTO nb_p_l FROM billet b, place p WHERE b.id_place = p.id_place AND id_match = id_m AND p.type_place = type_p AND id_client = -1;
	RETURN nb_p_l;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'participe_a_la_competition'
\echo '...'

CREATE OR REPLACE FUNCTION participe_a_la_competition(club.id_club%TYPE, competition.id_competition%TYPE, saison.annee_debut%TYPE) RETURNS BOOLEAN AS $$
DECLARE
	id_c ALIAS FOR $1;
	id_compet ALIAS FOR $2;
	annee ALIAS FOR $3;
BEGIN
	PERFORM * FROM participe WHERE id_club = id_c AND id_competition = id_compet AND annee_debut = annee;
	RETURN FOUND;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'deja_present'
\echo '...'

CREATE OR REPLACE FUNCTION deja_present(club.id_club%TYPE, competition.id_competition%TYPE, phase.id_phase%TYPE, saison.annee_debut%TYPE) RETURNS BOOLEAN AS $$
DECLARE
	id_c ALIAS FOR $1;
	id_compet ALIAS FOR $2;
	id_p ALIAS FOR $3;
	annee ALIAS FOR $4;
BEGIN
	PERFORM * FROM matchs m WHERE m.id_competition = id_compet AND m.id_phase = id_p AND m.annee_debut = annee AND (m.id_club_domicile = id_c OR m.id_club_exterieur = id_c);
	RETURN FOUND;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'surcharge'
\echo '...'

CREATE OR REPLACE FUNCTION surcharge(club.id_club%TYPE, date) RETURNS BOOLEAN AS $$
DECLARE
	max_number integer;
	temp_number integer;
	insert_date ALIAS FOR $2;
	date_min date;
	date_max date;
BEGIN
	max_number := 0;
	FOR I IN REVERSE 7..0 LOOP
		date_min := insert_date - I;
		date_max := date_min + 7;
		SELECT count(*) INTO temp_number FROM matchs m WHERE (m.id_club_domicile = $1 OR m.id_club_exterieur = $1) AND m.date_match BETWEEN date_min AND date_max;
		IF max_number < temp_number THEN
			max_number := temp_number;
		END IF;
	END LOOP;
	IF max_number > 2 THEN
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'a_deja_un_match'
\echo '...'

CREATE OR REPLACE FUNCTION a_deja_un_match(club.id_club%TYPE, date) RETURNS BOOLEAN AS $$
DECLARE
	id_c ALIAS FOR $1;
	insert_date ALIAS FOR $2;
	date_min date;
	date_max date;
BEGIN
	date_min := insert_date - 2;
	date_max := insert_date + 2;
	PERFORM * FROM matchs m WHERE (m.id_club_domicile = id_c OR m.id_club_exterieur = id_c) AND (m.date_match BETWEEN date_min AND date_max);
	RETURN FOUND;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'max_match'
\echo '...'

CREATE OR REPLACE FUNCTION max_match(competition.id_competition%TYPE, phase.id_phase%TYPE, saison.annee_debut%TYPE) RETURNS BOOLEAN AS $$
DECLARE
	id_compet ALIAS FOR $1;
	id_p ALIAS FOR $2;
	annee ALIAS FOR $3;
	number_matchs smallint;
	max_number_matchs smallint;
BEGIN
	SELECT p.nb_equipes_fin::smallint INTO max_number_matchs FROM phase p WHERE p.id_phase = id_p;
	IF est_championnat(id_compet) THEN
		max_number_matchs := (max_number_matchs/2)::smallint;
	END IF;
	SELECT count(*) INTO number_matchs FROM matchs m WHERE m.id_competition = id_compet AND m.id_phase = id_p AND m.annee_debut = annee;
	RETURN number_matchs >= max_number_matchs;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'modifier_prix'
\echo '...'

CREATE OR REPLACE FUNCTION modifier_prix(matchs.id_match%TYPE, place.type_place%TYPE, billet.prix%TYPE) RETURNS VOID AS $$
DECLARE
	id_m ALIAS FOR $1;
	type_p ALIAS FOR $2;
	prix_b ALIAS FOR $3;
	id_s stade.id_stade%TYPE;
	la_place place.id_place%TYPE;
BEGIN
	SELECT s.id_stade INTO id_s FROM stade s, club c, matchs m WHERE c.id_club = m.id_club_domicile AND c.id_stade = s.id_stade;
	FOR la_place IN
		SELECT p.id_place FROM place p WHERE p.id_stade = id_s AND p.type_place = type_p
	LOOP
		UPDATE billet SET prix = prix_b WHERE id_place = la_place AND id_match = id_m;
	END LOOP;
	RAISE NOTICE 'Prix mis a jour.';
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'recherche_matchs'
\echo '...'

CREATE OR REPLACE FUNCTION recherche_matchs(competition.id_competition%TYPE, phase.id_phase%TYPE, saison.annee_debut%TYPE, club.id_club%TYPE) 
		RETURNS TABLE (
		id_match matchs.id_match%TYPE, equipe_domicile club.nom_club%TYPE, equipe_exterieur club.nom_club%TYPE,
		competition competition.nom_competition%TYPE, type_competition TEXT, phase phase.nom_phase%TYPE, date_match date,
		nombre_places_restantes stade.capacite%TYPE
		) AS $$
DECLARE
	m RECORD;
	id_compet ALIAS FOR $1;
	id_p ALIAS FOR $2;
	annee ALIAS FOR $3;
	id_c ALIAS FOR $4;
	ok BOOLEAN;
BEGIN
	FOR m IN
		SELECT * FROM matchs
	LOOP
		ok := true;
		IF id_compet IS NOT NULL AND m.id_competition <> id_compet THEN
			ok := FALSE;
		END IF;
		IF ok AND id_p IS NOT NULL AND m.id_phase <> id_p THEN
			ok := FALSE;
		END IF;
		IF ok AND annee IS NOT NULL AND m.annee_debut <> annee THEN
			ok := FALSE;
		END IF;
		IF ok AND id_c IS NOT NULL AND m.id_club_domicile <> id_c AND m.id_club_exterieur <> id_c THEN
			ok := FALSE;
		END IF;
		IF ok THEN
			id_match := m.id_match;
			SELECT nom_club INTO equipe_domicile::TEXT FROM club WHERE id_club = m.id_club_domicile;
			SELECT nom_club INTO equipe_exterieur::TEXT FROM club WHERE id_club = m.id_club_exterieur;
			SELECT nom_competition INTO competition::TEXT FROM competition WHERE id_competition = m.id_competition;
			SELECT type_competition(m.id_competition) INTO type_competition;
			SELECT nom_phase INTO phase::TEXT FROM phase WHERE id_phase = m.id_phase;
			date_match := m.date_match;
			--SELECT count(*) INTO nombre_places_restantes FROM billet b WHERE b.id_match = m.id_match AND b.id_client = -1;
			nombre_places_restantes = nombre_places_libres_total(m.id_match);
			return next;
		END IF;
	END LOOP;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'reserver_match'
\echo '...'

CREATE OR REPLACE FUNCTION reserver_match(matchs.id_match%TYPE, client.id_client%TYPE, place.type_place%TYPE) RETURNS VOID AS $$
DECLARE
	id_m ALIAS FOR $1;
	id_c ALIAS FOR $2;
	type_p ALIAS FOR $3;
	nb_p_l stade.capacite%TYPE;
	id_p place.id_place%TYPE;
BEGIN
	--SELECT count(*) INTO nb_p_l FROM billet b WHERE b.id_match = id_m AND b.id_client = -1;
	nb_p_l := nombre_places_libres_cat(id_m, type_p);
	IF nb_p_l = 0 THEN
		RAISE NOTICE 'Plus de places libres de cette categorie pour ce match.';
	ELSE
		SELECT b.id_place INTO id_p FROM billet b, place p WHERE b.id_place = p.id_place AND p.type_place = type_p AND b.id_match = id_m AND b.id_client = -1;
		UPDATE billet SET id_client = id_c WHERE id_place = id_p AND id_match = id_m;
		RAISE NOTICE 'Reservation effectuee.';
	END IF;
	--RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'reserver_matchs_competition'
\echo '...'

CREATE OR REPLACE FUNCTION reserver_matchs_competition(competition.id_competition%TYPE, saison.annee_debut%TYPE, club.id_club%TYPE, client.id_client%TYPE) RETURNS VOID AS $$
DECLARE
	id_m matchs.id_match%TYPE;
	id_compet ALIAS FOR $1;
	annee ALIAS FOR $2;
	id_cb ALIAS FOR $3;
	id_ct ALIAS FOR $4;
	nb_p_l stade.capacite%TYPE;
	id_p place.id_place%TYPE;
	ok BOOLEAN;
BEGIN
	--SELECT count(*) INTO nb_p_l FROM billet b WHERE b.id_match = id_m AND b.id_client = -1;
	ok := TRUE;
	FOR id_m IN
		SELECT * FROM matchs WHERE id_competition = id_compet AND annee_debut = annee AND (id_club_domicile = id_cb OR id_club_exterieur = id_cb)
	LOOP
		nb_p_l := nombre_places_libres_cat(id_m, 'cat1');
		IF nb_p_l = 0 THEN
			ok := FALSE;
			EXIT;
		END IF;
	END LOOP;
	IF ok THEN
		FOR id_m IN
			SELECT * FROM matchs WHERE id_competition = id_compet AND annee_debut = annee AND (id_club_domicile = id_cb OR id_club_exterieur = id_cb)
		LOOP
			PERFORM reserver_match(id_m, id_ct, 'cat1');
		END LOOP;
		RAISE NOTICE 'Reservation des matchs effectuee.';
	ELSE
		RAISE NOTICE 'Pas de places libres pour tous les matchs.';
	END IF;
	--RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'reporter_match'
\echo '...'

CREATE OR REPLACE FUNCTION reporter_match(matchs.id_match%TYPE, matchs.date_match%TYPE) RETURNS VOID AS $$
DECLARE
	id_m ALIAS FOR $1;
	nouvelle_date ALIAS FOR $2;
BEGIN
	UPDATE matchs SET date_match = nouvelle_date WHERE id_match = id_m;
	RAISE NOTICE 'Match reporte.';
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'annuler_reservation'
\echo '...'

CREATE OR REPLACE FUNCTION annuler_reservation(matchs.id_match%TYPE, client.id_client%TYPE) RETURNS VOID AS $$
DECLARE
	id_m ALIAS FOR $1;
	id_c ALIAS FOR $2;
BEGIN
	PERFORM * FROM billet WHERE id_match = id_m AND id_client = id_c;
	IF FOUND THEN
		UPDATE billet SET id_client = -1 WHERE id_client = id_c AND id_match = id_m;
		RAISE NOTICE 'Reservation annulee.';
	ELSE
		RAISE NOTICE 'La reservation n''existe pas.';
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'stats_match'
\echo '...'

CREATE OR REPLACE FUNCTION stats_match(matchs.id_match%TYPE) RETURNS 
					TABLE(nombre_places_prises stade.capacite%TYPE, nombre_places_total stade.capacite%TYPE, somme billet.prix%TYPE) AS $$
DECLARE
	id_m ALIAS FOR $1;
BEGIN
	nombre_places_total := 0;
	nombre_places_prises := 0;
	somme := 0;
	SELECT s.capacite INTO nombre_places_total FROM matchs m, stade s, club c WHERE m.id_match = id_m AND c.id_club = m.id_club_domicile AND c.id_stade = s.id_stade;
	SELECT count(*), sum(b.prix) INTO nombre_places_prises, somme FROM billet b WHERE b.id_match = id_m AND b.id_client <> -1;
	RETURN NEXT;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'stats_club'
\echo '...'

CREATE OR REPLACE FUNCTION stats_club(club.id_club%TYPE, saison.annee_debut%TYPE) RETURNS 
		TABLE(nombre_places_prises stade.capacite%TYPE, nombre_places_total stade.capacite%TYPE, nombre_matchs_joues INTEGER, somme billet.prix%TYPE) AS $$
DECLARE
	r RECORD;
	id_c ALIAS FOR $1;
	annee ALIAS FOR $2;
	nb_p_p stade.capacite%TYPE;
	nb_p_t stade.capacite%TYPE;
	nb_p_p_s stade.capacite%TYPE;
	--nb_p_t_s stade.capacite%TYPE;
	somme_match billet.prix%TYPE;
	somme_matchs billet.prix%TYPE;
	nb_match INTEGER;
	nb_matchs INTEGER;
BEGIN
	nb_p_p_s := 0;
	--nb_p_t_s := 0;
	nb_matchs := 0;
	somme_matchs := 0;
	FOR r IN
		SELECT * FROM matchs WHERE id_club_domicile = id_c AND annee_debut = annee
	LOOP
		somme_match := 0;
		SELECT count(*), sum(b.prix) INTO nb_p_p, somme_match FROM billet b WHERE b.id_match = r.id_match AND b.id_client <> -1;
		IF nb_p_p <> 0 THEN
			nb_p_p_s := nb_p_p_s + nb_p_p;
			somme_matchs := somme_matchs + somme_match;
		END IF;
		nb_matchs := nb_matchs + 1;
	END LOOP;
	SELECT s.capacite INTO nb_p_t FROM stade s, club c WHERE c.id_club = id_c AND c.id_stade = s.id_stade;
	nombre_places_prises := nb_p_p_s;
	nombre_places_total := nb_p_t * nb_matchs;
	nombre_matchs_joues := nb_matchs;
	somme := somme_matchs;
	RETURN NEXT;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'stats_competition'
\echo '...'

CREATE OR REPLACE FUNCTION stats_competition(competition.id_competition%TYPE, saison.annee_debut%TYPE) RETURNS 
		TABLE(nombre_places_prises stade.capacite%TYPE, nombre_places_total stade.capacite%TYPE, nombre_matchs_joues INTEGER, somme billet.prix%TYPE) AS $$
DECLARE
	r RECORD;
	id_compet ALIAS FOR $1;
	annee ALIAS FOR $2;
	nb_p_p stade.capacite%TYPE;
	nb_p_t stade.capacite%TYPE;
	nb_p_p_s stade.capacite%TYPE;
	nb_p_t_s stade.capacite%TYPE;
	somme_match billet.prix%TYPE;
	somme_matchs billet.prix%TYPE;
	nb_match INTEGER;
	nb_matchs INTEGER;
BEGIN
	nb_p_p_s := 0;
	nb_p_t_s := 0;
	nb_matchs := 0;
	somme_matchs := 0;
	FOR r IN
		SELECT * FROM matchs WHERE id_competition = id_compet AND annee_debut = annee
	LOOP
		somme_match := 0;
		SELECT count(*), sum(b.prix) INTO nb_p_p, somme_match FROM billet b WHERE b.id_match = r.id_match AND b.id_client <> -1;
		IF nb_p_p <> 0 THEN
			somme_matchs := somme_matchs + somme_match;
			nb_p_p_s := nb_p_p_s + nb_p_p;
		END IF;
		SELECT s.capacite INTO nb_p_t FROM stade s, club c WHERE c.id_club = r.id_club_domicile AND c.id_stade = s.id_stade;
		nb_p_t_s := nb_p_t_s + nb_p_t;
		nb_matchs := nb_matchs + 1;
	END LOOP;
	nombre_places_prises := nb_p_p_s;
	nombre_places_total := nb_p_t_s;
	nombre_matchs_joues := nb_matchs;
	somme := somme_matchs;
	RETURN NEXT;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------
\echo ----> FIN DE LA CREATION DES FONCTIONS <----
\echo ---------+---------+---------+---------+---------
