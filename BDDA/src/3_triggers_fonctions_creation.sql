\echo ---------+---------+---------+---------+---------
\echo ----> CREATION DES FONCTIONS DES TRIGGERS <----
\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'ajouter_places'
\echo '...'

CREATE OR REPLACE FUNCTION ajouter_places() RETURNS TRIGGER AS $$ -- AFTER INSERT ON stade
DECLARE
	nb_p stade.capacite%TYPE; -- nombre de places
	id_s stade.id_stade%TYPE; -- id stade
BEGIN
	nb_p := NEW.capacite;
	id_s := NEW.id_stade;
	FOR i IN 1.. nb_p LOOP
		EXECUTE 'INSERT INTO place(numero_place, type_place, id_stade) VALUES ('||i||', ''cat1'', '||id_s||')';
	END LOOP;
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'rajouter_places'
\echo '...'

CREATE OR REPLACE FUNCTION rajouter_places() RETURNS TRIGGER AS $$ -- AFTER UPDATE ON stade
DECLARE
	nb_p_o stade.capacite%TYPE; -- nombre de places avant
	nb_p_n stade.capacite%TYPE; -- nombre de places apres
	id_s stade.id_stade%TYPE; -- id stade
BEGIN
	nb_p_o := OLD.capacite;
	nb_p_n := NEW.capacite;
	id_s := NEW.id_stade;
	FOR i IN nb_p_o + 1 .. nb_p_n LOOP
		INSERT INTO place(numero_place, type_place, id_stade) VALUES (i, 'cat1', id_s);
	END LOOP;
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'annuler_modif_stade'
\echo '...'

CREATE OR REPLACE FUNCTION annuler_modif_stade() RETURNS TRIGGER AS $$ -- BEFORE UPDATE ON stade
BEGIN
	RAISE NOTICE 'Modification de la capacite du stade interdite.';
	RAISE NOTICE 'Veuillez supprimer les places une a une.';
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'annuler_modif_place'
\echo '...'

CREATE OR REPLACE FUNCTION annuler_modif_place() RETURNS TRIGGER AS $$ -- BEFORE UPDATE ON place
BEGIN
	RAISE NOTICE 'Modification du stade de la place interdite !';
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'decrementer_capacite_stade'
\echo '...'

CREATE OR REPLACE FUNCTION decrementer_capacite_stade() RETURNS TRIGGER AS $$ -- AFTER DELETE ON place
DECLARE
	nb_p stade.capacite%TYPE;
BEGIN
	SELECT s.capacite INTO nb_p FROM stade s WHERE s.id_stade = OLD.id_stade;
	nb_p = nbp - 1;
	UPDATE stade SET capacite = nb_p WHERE id_stade = OLD.id_stade;
	RETURN OLD;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'verifier_place'
\echo '...'

CREATE OR REPLACE FUNCTION verifier_place() RETURNS TRIGGER AS $$ -- BEFORE INSERT OR UPDATE ON place
DECLARE
	ok BOOLEAN;
	id_s stade.id_stade%TYPE;
	nb_p_max stade.capacite%TYPE;
	nb_p stade.capacite%TYPE;
	old_num place.numero_place%TYPE;
BEGIN
	ok := TRUE;
	id_s := NEW.id_stade;
	SELECT s.capacite INTO nb_p_max FROM stade s WHERE s.id_stade = id_s;
	-- INSERT
	IF TG_OP = 'INSERT' THEN
		old_num := - 1;
		SELECT count(*) INTO nb_p FROM place p WHERE p.id_stade = id_s;
		IF nb_p >= nb_p_max THEN
			ok := FALSE;
		END IF;
	ELSE
		old_num := OLD.numero_place;
	END IF;
	-- UPDATE
	IF ok AND NEW.numero_place <> old_num THEN
		PERFORM * FROM place p WHERE p.id_stade = id_s AND numero_place = NEW.numero_place;
		ok := NOT FOUND;
	END IF;
	-- RETURN
	IF ok THEN
		RETURN NEW;
	ELSE
		IF TG_OP = 'INSERT' THEN
			RAISE NOTICE 'Erreur lors de l''ajout de la place. Le nombre de place est atteint ou le numero appartient deja a une place.';
		ELSE
			RAISE NOTICE 'Erreur lors de la modification de la place. Le numero appartient deja a une place.';
		END IF;
		RETURN NULL;
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'ajouter_billets'
\echo '...'

CREATE OR REPLACE FUNCTION ajouter_billets() RETURNS TRIGGER AS $$ -- AFTER INSERT ON matchs
DECLARE
	nb_p stade.capacite%TYPE; -- nombre de places
	id_m matchs.id_match%TYPE; -- id match
	id_p place.id_place%TYPE; -- id place
	id_s stade.id_stade%TYPE; -- id stade
	id_c_d club.id_club%TYPE; -- id club domicile
BEGIN
	id_m := NEW.id_match;
	id_c_d := NEW.id_club_domicile;
	SELECT s.id_stade INTO id_s FROM club c, stade s WHERE c.id_club = id_c_d AND s.id_stade = c.id_stade;
	SELECT count(*) INTO nb_p FROM place p WHERE p.id_stade = id_s;
	FOR id_p IN 
		SELECT id_place FROM place WHERE id_stade = id_s
	LOOP
		INSERT INTO billet(prix, id_client, id_place, id_match) VALUES (0, NULL, id_p, id_m);
	END LOOP;
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'annuler_modif_billet'
\echo '...'

CREATE OR REPLACE FUNCTION annuler_modification_billet() RETURNS TRIGGER AS $$ -- BEFORE UPDATE ON billet
BEGIN
	RAISE NOTICE 'Modification de la place et du match du billetinterdite !';
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'verifier_match'
\echo '...'

CREATE OR REPLACE FUNCTION verifier_match() RETURNS TRIGGER AS $$ -- BEFORE INSERT ON matchs
DECLARE
	id_c_domicile club.id_club%TYPE; -- id club domicile
	id_c_exterieur club.id_club%TYPE; -- id club exterieur
	id_p phase.id_phase%TYPE; -- id phase du match
	id_compet competition.id_competition%TYPE; -- id competition
	annee_saison saison.annee_debut%TYPE; -- annee debut saison
	date_m matchs.date_match%TYPE;
BEGIN
	id_c_domicile := NEW.id_club_domicile;
	id_c_exterieur := NEW.id_club_exterieur;
	id_p := NEW.id_phase;
	id_compet := NEW.id_competition;
	annee_saison := NEW.annee_debut;
	date_m := NEW.date_match;
	IF id_c_domicile <> id_c_exterieur THEN
		IF participe_a_la_competition(id_c_domicile, id_compet, annee_saison) AND participe_a_la_competition(id_c_exterieur, id_compet, annee_saison) THEN
			IF NOT deja_present(id_c_domicile, id_compet, id_p, annee_saison) AND NOT deja_present(id_c_exterieur, id_compet, id_p, annee_saison) THEN
				IF NOT a_deja_un_match(id_c_domicile, date_m) AND NOT a_deja_un_match(id_c_exterieur, date_m) THEN
					IF NOT max_match(id_compet, id_p, annee_saison) THEN
						RETURN NEW;
					ELSE
						RAISE NOTICE 'Erreur lors de l''ajout du match ; le nombre maximum de matchs pour la phase est atteint.';
						RETURN NULL;
					END IF;
				ELSE
					RAISE NOTICE 'Erreur lors de l''ajout du match ; l''un des deux clubs joue deja un match au alentour de la date mentionnee.';
					RETURN NULL;
				END IF;
			ELSE
				RAISE NOTICE 'Erreur lors de l''ajout du match ; l''un des deux clubs participe deja a la phase de la competition.';
				RETURN NULL;
			END IF;
		ELSE
			RAISE NOTICE 'Erreur lors de l''ajout du match ; l''un des deux clubs ne peut participer a la competition.';		
			RETURN NULL;
		END IF;
	ELSE
		RAISE NOTICE 'Erreur lors de l''ajout du match ; les deux clubs saisis sont identiques.';		
		RETURN NULL;
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction 'ajouter_stade'
\echo '...'

CREATE OR REPLACE FUNCTION ajouter_stade() RETURNS TRIGGER AS $$ -- BEFORE INSERT ON club
DECLARE
	nom_nouveau_stade CHAR(30);
BEGIN
	nom_nouveau_stade := 'Stade_de_'||NEW.nom_club::CHAR(30);
	IF NEW.id_stade IS NULL THEN
		INSERT INTO stade(nom_stade, capacite) VALUES (nom_nouveau_stade, 1);
		SELECT s.id_stade INTO NEW.id_stade FROM stade s WHERE s.nom_stade = nom_nouveau_stade;
	ELSE
		PERFORM * FROM stade s WHERE s.id_stade = NEW.id_stade;
		IF NOT FOUND THEN
			INSERT INTO stade VALUES (NEW.id_stade, nom_nouveau_stade, 1);
		END IF;
	END IF;
	RETURN NEW;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'verifier_billet'
\echo '...'

CREATE OR REPLACE FUNCTION verifier_billet() RETURNS TRIGGER AS $$ -- BEFORE INSERT OR UPDATE ON billet
DECLARE
	ok BOOLEAN;
	old_p place.id_place%TYPE;
BEGIN
	ok := TRUE;
	IF TG_OP = 'INSERT' THEN
		old_p := -1;
	ELSE
		old_p := OLD.id_place;
	END IF;
	IF NEW.id_place <> old_p THEN
		PERFORM * FROM billet b WHERE b.id_match = NEW.id_match AND b.id_place = NEW.id_place;
		ok := NOT FOUND;
	END IF;
	IF ok THEN
		IF NEW.id_client IS NULL THEN
			PERFORM * FROM client WHERE id_client = -1;
			IF NOT FOUND THEN
				INSERT INTO client VALUES (-1, 'NULL', '1901-01-01', 'NULL');
			END IF;
			NEW.id_client = -1;
		END IF;
		RETURN NEW;
	ELSE
		RAISE NOTICE 'Erreur, la place est deja prise.';
		RETURN NULL;
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'annuler_modification_match'
\echo '...'

CREATE OR REPLACE FUNCTION annuler_modification_match() RETURNS TRIGGER AS $$ -- BEFORE UPDATE ON match
DECLARE
	ok BOOLEAN;
	old_p place.id_place%TYPE;
BEGIN
	IF NEW.id_club_domicile = OLD.id_club_domicile AND NEW.id_club_exterieur = OLD.id_club_exterieur AND
	NEW.id_competition = OLD.id_competition AND NEW.annee_debut = OLD.annee_debut AND NEW.id_phase = OLD.id_phase THEN
		IF NOT a_deja_un_match(NEW.id_club_domicile, NEW.date_match) AND NOT a_deja_un_match(NEW.id_club_exterieur, NEW.date_match) THEN
			RETURN NEW;
		ELSE
			RAISE NOTICE 'L''un des deux clubs joue deja un match autour de la date specifiee';
			RETURN NULL;
		END IF;
	ELSE
		RAISE NOTICE 'Vous ne pouvez que reporter ou annuler le match.';
		RETURN NULL;
	END IF;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'notifier_reservation_match'
\echo '...'

CREATE OR REPLACE FUNCTION notifier_reservation_match() RETURNS TRIGGER AS $$ -- AFTER UPDATE ON billet
DECLARE
	id_m matchs.id_match%TYPE;
	nom_club_domicile club.nom_club%TYPE;
	nom_club_exterieur club.nom_club%TYPE;
	type_p place.type_place%TYPE;
BEGIN
	id_m := NEW.id_match;
	SELECT c.nom_club INTO nom_club_domicile  FROM club c, matchs m WHERE m.id_match = id_m AND c.id_club = m.id_club_domicile;
	SELECT c.nom_club INTO nom_club_exterieur FROM club c, matchs m WHERE m.id_match = id_m AND c.id_club = m.id_club_exterieur;
	IF NEW.id_client = -1 THEN
		INSERT INTO messagerie(id_client, date_message, message)
		VALUES (OLD.id_client, current_date, 'Votre reservation du match entre '||nom_club_domicile||' et '||nom_club_exterieur||' a ete annule avec success.');
		INSERT INTO messagerie(id_client, date_message, message)
		VALUES (OLD.id_client, current_date, 'Vous allez etre rembourse de la somme de '||OLD.prix||' euros.');
	ELSE
		SELECT p.type_place INTO type_p FROM place p WHERE p.id_place = OLD.id_place;
		INSERT INTO messagerie(id_client, date_message, message)
		VALUES (NEW.id_client, current_date, 'Votre reservation ('||type_p||') pour le match entre '||nom_club_domicile||' et '||nom_club_exterieur||' a ete reservee avec success.');
	END IF;
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'notifier_report_match'
\echo '...'

CREATE OR REPLACE FUNCTION notifier_report_match() RETURNS TRIGGER AS $$ -- AFTER UPDATE ON match
DECLARE
	id_c client.id_client%TYPE;
	nom_club_domicile club.nom_club%TYPE;
	nom_club_exterieur club.nom_club%TYPE;
BEGIN
	SELECT nom_club INTO nom_club_domicile FROM club WHERE id_club = NEW.id_club_domicile;
	SELECT nom_club INTO nom_club_exterieur FROM club WHERE id_club = NEW.id_club_exterieur;
	FOR id_c IN
		SELECT id_client FROM billet WHERE id_match = OLD.id_match AND id_client <> -1
	LOOP
		INSERT INTO messagerie(id_client, date_message, message)
		VALUES (id_c, current_date, 'Le match entre '||nom_club_domicile||' et '||nom_club_exterieur||' a ete reporte au '||NEW.date_match);
	END LOOP;
	RETURN NULL;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------

\echo Ajout de la fonction de trigger 'annuler_match'
\echo '...'

CREATE OR REPLACE FUNCTION annuler_match() RETURNS TRIGGER AS $$ -- BEFORE DELETE ON match
DECLARE
	id_c client.id_client%TYPE;
	nom_club_domicile club.nom_club%TYPE;
	nom_club_exterieur club.nom_club%TYPE;
BEGIN
	SELECT nom_club INTO nom_club_domicile FROM club WHERE id_club = OLD.id_club_domicile;
	SELECT nom_club INTO nom_club_exterieur FROM club WHERE id_club = OLD.id_club_exterieur;
	FOR id_c IN
		SELECT id_client FROM billet WHERE id_match = OLD.id_match AND id_client <> -1
	LOOP
		INSERT INTO messagerie(id_client, date_message, message)
		VALUES (id_c, current_date, 'Le match entre '||nom_club_domicile||' et '||nom_club_exterieur||' a ete annule.');
	END LOOP;
	DELETE FROM billet WHERE id_match = OLD.id_match;
	RETURN OLD;
END
$$ LANGUAGE 'plpgsql';

\echo ---------+---------+---------+---------+---------
\echo ----> FIN DE LA CREATION DES FONCTIONS DE TRIGGERS <----
\echo ---------+---------+---------+---------+---------