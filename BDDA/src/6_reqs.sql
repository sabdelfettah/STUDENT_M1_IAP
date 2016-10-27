\echo '---------+---------+---------+---------+---------'
\echo '----> REQUETES <----'
\echo '---------+---------+---------+---------+---------'

\echo Requetes de recherche
\echo '...'

\echo requete de recherche (affiche tous les matchs)
\echo '...'
SELECT recherche_matchs(NULL, NULL, NULL, NULL) AS RESULTATS_TOUS_LES_MATCHS;

\echo requete de recherche (affiche tous les matchs de la 1ere journee de Premier League - Saison 2014)
\echo '...'
SELECT recherche_matchs(1, 1, 2014::smallint, NULL) AS RESULTATS_MATCHS_PL_J1_2014;

\echo requete de recherche (affiche tous les matchs du club Chelsea - Saison 2014)
\echo '...'
SELECT recherche_matchs(NULL, NULL, 2014::smallint, 1) AS RESULTATS_MATCHS_CHELSEA_2014;

\echo '---------+---------+---------+---------+---------'

\echo Mise a jour des stade
\echo '...'

UPDATE stade SET nom_stade = 'Stade A' WHERE id_stade = 23;
UPDATE stade SET nom_stade = 'Stade B' WHERE id_stade = 65;
UPDATE stade SET capacite = 10 WHERE id_stade = 23;
UPDATE stade SET capacite = 1 WHERE id_stade = 23;
UPDATE stade SET capacite = 10 WHERE id_stade = 23;

\echo '---------+---------+---------+---------+---------'

\echo Mise a jour des types des places du stade numero 1
\echo '...'

UPDATE place SET type_place = 'cat2' WHERE id_stade = 1 AND numero_place BETWEEN 201 AND 300;
UPDATE place SET type_place = 'cat3' WHERE id_stade = 1 AND numero_place BETWEEN 301 AND 400;
UPDATE place SET type_place = 'cat4' WHERE id_stade = 1 AND numero_place BETWEEN 401 AND 416;

\echo '---------+---------+---------+---------+---------'

\echo Mise a jour des prix des places du match numero 2
\echo '...'

SELECT modifier_prix(2, 'cat1', 80);
SELECT modifier_prix(2, 'cat2', 120);
SELECT modifier_prix(2, 'cat3', 250);
SELECT modifier_prix(2, 'cat4', 400);

\echo '---------+---------+---------+---------+---------'

\echo Requetes de reservation
\echo '...'

SELECT reserver_match(2, 1, 'cat1');
SELECT annuler_reservation(2, 1);
SELECT reserver_match(2, 1, 'cat3');
SELECT reporter_match(2, '2015-05-29');
select * from billet where id_client <> -1;
SELECT reserver_matchs_competition (1,2014::smallint, 1, 2);
select * from billet where id_client <> -1;
DELETE FROM matchs WHERE id_match = 5;
UPDATE billet SET prix = 50 WHERE id_client <> -1 AND prix = 0;
select * from billet where id_client <> -1;
select * from billet where id_match = 2 and id_client <> -1;

\echo '---------+---------+---------+---------+---------'

\echo Statistiques
\echo '...'

\echo statistiques du match numero 2
\echo '...'
SELECT (stats_match(2)).nombre_places_prises, (stats_match(2)).nombre_places_total, (stats_match(2)).somme;

\echo statistiques du club numero 1 (Chealsea) pour la saison 2014
\echo '...'
SELECT (stats_club(1, 2014::smallint)).nombre_places_prises, (stats_club(1, 2014::smallint)).nombre_places_total, 
		(stats_club(1, 2014::smallint)).nombre_matchs_joues, (stats_club(1, 2014::smallint)).somme;

\echo statistiques de la competition numero 1 (Premier League) pour la saison 2014
\echo '...'
SELECT (stats_competition(1, 2014::smallint)).nombre_places_prises, (stats_competition(1, 2014::smallint)).nombre_places_total, 
		(stats_competition(1, 2014::smallint)).nombre_matchs_joues, (stats_competition(1, 2014::smallint)).somme;

\echo '---------+---------+---------+---------+---------'

\echo Messagerie
\echo '...'
select * from messagerie;

\echo '---------+---------+---------+---------+---------'
\echo '----> FIN DES REQUETES <----'
\echo '---------+---------+---------+---------+---------'
