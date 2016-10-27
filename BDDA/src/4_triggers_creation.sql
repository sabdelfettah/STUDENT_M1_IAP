\echo ---------+---------+---------+---------+---------
\echo ----> CREATION DES TRIGGERS <----
\echo ---------+---------+---------+---------+---------

\echo Suppression d anciennes versions des triggers
\echo ...

DROP TRIGGER IF EXISTS ajout_places ON stade;
DROP TRIGGER IF EXISTS rajout_places ON stade;
DROP TRIGGER IF EXISTS annulation_modification_stade ON stade;
DROP TRIGGER IF EXISTS verification_place ON place;
DROP TRIGGER IF EXISTS annulation_modification_place ON place;
DROP TRIGGER IF EXISTS maj_stade ON place;
DROP TRIGGER IF EXISTS ajout_stade ON club;
DROP TRIGGER IF EXISTS verification_billet ON billet;
DROP TRIGGER IF EXISTS notifier_reservation ON billet;
DROP TRIGGER IF EXISTS annulation_modification_billet ON billet;
DROP TRIGGER IF EXISTS verification_ajout_match ON matchs;
DROP TRIGGER IF EXISTS ajout_billets ON matchs;
DROP TRIGGER IF EXISTS annulation_modification_match ON matchs;
DROP TRIGGER IF EXISTS notifier_report ON matchs;
DROP TRIGGER IF EXISTS annulation_match ON matchs;

\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'ajout_places' apres l ajout d un stade
\echo '...'

CREATE TRIGGER ajout_places AFTER INSERT ON stade
	FOR EACH ROW
	EXECUTE PROCEDURE ajouter_places();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'rajout_places' apres l augmentation du nombre de place d un stade
\echo '...'

CREATE TRIGGER rajout_places AFTER UPDATE ON stade
	FOR EACH ROW
	WHEN (NEW.capacite > OLD.capacite)
	EXECUTE PROCEDURE rajouter_places();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'annulation_modification_stade' apres la reduction du nombre de place d un stade
\echo '...'

CREATE TRIGGER annulation_modification_stade BEFORE UPDATE ON stade
	FOR EACH ROW
	WHEN (NEW.capacite < OLD.capacite)
	EXECUTE PROCEDURE annuler_modif_stade();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'verification_place' apres l ajout/modification d une place
\echo '...'

CREATE TRIGGER verification_place BEFORE INSERT OR UPDATE ON place
	FOR EACH ROW
	EXECUTE PROCEDURE verifier_place();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'annulation_modification_place' apres la modification du stade d une place
\echo '...'

CREATE TRIGGER annulation_modification_place BEFORE UPDATE ON place
	FOR EACH ROW
	WHEN (NEW.id_stade IS DISTINCT FROM OLD.id_stade)
	EXECUTE PROCEDURE annuler_modif_place();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'maj_stade' apres la suppression d une place
\echo '...'

CREATE TRIGGER maj_stade AFTER DELETE ON place
	FOR EACH ROW
	EXECUTE PROCEDURE decrementer_capacite_stade();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'ajout_stade' avant l ajout d un club
\echo '...'

CREATE TRIGGER ajout_stade BEFORE INSERT ON club
	FOR EACH ROW
	EXECUTE PROCEDURE ajouter_stade();

\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'verification_ajout_match' avant l ajout d un match
\echo '...'

CREATE TRIGGER verification_ajout_match BEFORE INSERT ON matchs
	FOR EACH ROW
	EXECUTE PROCEDURE verifier_match();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'ajout_billets' apres l ajout d un match
\echo '...'

CREATE TRIGGER ajout_billets AFTER INSERT ON matchs
	FOR EACH ROW
	EXECUTE PROCEDURE ajouter_billets();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'verification_billet' apres l ajout/modification d un billet
\echo '...'

CREATE TRIGGER verification_billet BEFORE INSERT OR UPDATE ON billet
	FOR EACH ROW
	EXECUTE PROCEDURE verifier_billet();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'annulation_modification_match' apres le report d un match
\echo '...'

CREATE TRIGGER annulation_modification_match BEFORE UPDATE ON matchs
	FOR EACH ROW
	EXECUTE PROCEDURE annuler_modification_match();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'notifier_reservation' apres avant la modification d un billet
\echo '...'

CREATE TRIGGER notifier_reservation AFTER UPDATE ON billet
	FOR EACH ROW
	WHEN (NEW.id_client IS DISTINCT FROM OLD.id_client AND NEW.id_place = OLD.id_place AND NEW.id_match = OLD.id_match AND NEW.prix = OLD.prix)
	EXECUTE PROCEDURE notifier_reservation_match();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'annulation_modification_billet' avant la modification d un billet autre que le client
\echo '...'

CREATE TRIGGER annulation_modification_billet BEFORE UPDATE ON billet
	FOR EACH ROW
	WHEN (NEW.id_place <> OLD.id_place OR NEW.id_match <> OLD.id_match)
	EXECUTE PROCEDURE annuler_modification_billet();

	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'notifier_report' apres le report d un match
\echo '...'

CREATE TRIGGER notifier_report AFTER UPDATE ON matchs
	FOR EACH ROW
	EXECUTE PROCEDURE notifier_report_match();
	
\echo ---------+---------+---------+---------+---------

\echo Ajout du trigger 'annulation_match' apres l annulation d un match
\echo '...'

CREATE TRIGGER annulation_match BEFORE DELETE ON matchs
	FOR EACH ROW
	EXECUTE PROCEDURE annuler_match();

\echo ---------+---------+---------+---------+---------
\echo ----> FIN DE LA CREATION DES TRIGGERS <----
\echo ---------+---------+---------+---------+---------