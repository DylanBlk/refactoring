# Diagnostic du code historique (étape 1)

## Rôle de chaque classe

- ServiceClient : valide le client, l'enregistre et envoie l'email de bienvenue.
  Dépend de Database (singleton) et EmailService (statique).
- ServiceExpedition : valide la demande, choisit le véhicule, calcule le prix et le délai,
  gère le cycle de vie, envoie les notifications, enregistre.
  Dépend de presque tout : Database, CalculateurPrix, AffectationVehicule, EmailService, SmsService, Utils.
- CalculateurPrix : calcule le prix HT (tarif de base, suppléments, urgence, remise, assurance).
- AffectationVehicule : cherche le premier véhicule libre et compatible (avec des instanceof).
- Flotte : liste statique des véhicules.
- Expedition : les données d'une expédition, et aussi la génération de la facture.
- Client, Colis : les données.
- IVehicule, Vehicule, Camion, CamionFrigorifique, Train, Bateau, Avion : la flotte.
- Database, EmailService, SmsService : stockage et notifications simulés, avec des données statiques.
- Utils : dates, arrondi, email, table des distances.

## Défauts trouvés

| Défaut | Où | Principe |
|---|---|---|
| Classe qui fait tout | ServiceExpedition | S |
| L'entité génère sa facture | Expedition.genererFacture() | S |
| Validation + enregistrement + email | ServiceClient.creerClient | S |
| if/else sur le mode puis sur le type de client | CalculateurPrix | O |
| switch sur le mode | ServiceExpedition.calculerDelai | O |
| if sur le mode pour faire partir le véhicule | ServiceExpedition.demarrerExpedition | O |
| Suite de instanceof | AffectationVehicule | O |
| Un if par trajet | Utils.getDistance | O |
| Le camion frigo refuse les colis non réfrigérés au chargement | CamionFrigorifique.charger | L |
| faireLePlein() lance une exception, cachée par un catch vide | Train + demarrerExpedition | L |
| Interface trop grosse, UnsupportedOperationException partout | IVehicule | I |
| Singleton | Database.getInstance() | D |
| Envoi d'email en méthode statique | EmailService.envoyer | D |
| new de services en dur | new SmsService(), new CalculateurPrix(), new AffectationVehicule() | D |
| Données statiques modifiables | Flotte.vehicules, compteur, Database, journaux | D |

Autres problèmes :
- nombres magiques (1.2, 0.10, 167, 5000, 70, 1440...)
- mode, statut et type de client en String, type de colis en int
- Math.round(x * 100.0) / 100.0 recopié partout
- calcul poids / dangereux / réfrigéré recopié à 3 endroits
- SQL construit par concaténation, clé d'API en dur dans SmsService
- erreurs affichées avec System.out puis return null

## Ce qu'il faudrait changer pour ajouter le mode FLUVIAL

1. CalculateurPrix : nouvelle branche pour le tarif
2. ServiceExpedition.calculerDelai : nouveau case
3. ServiceExpedition.creerExpedition : règle "pas d'urgence"
4. ServiceExpedition.demarrerExpedition : départ du véhicule
5. AffectationVehicule : nouvelle branche avec instanceof Peniche
6. nouvelle classe Peniche, avec des UnsupportedOperationException
7. Flotte : ajouter la péniche
8. Utils.getDistance : ajouter Paris - Le Havre

Total : 7 fichiers existants à modifier + 1 nouveau.

## Ce qu'il faudrait changer pour le supplément hors gabarit

1. CalculateurPrix : un booléen et un if de plus
2. éventuellement Colis : une méthode pour tester les dimensions

Peu de fichiers, mais c'est toujours la même classe qui grossit.

Remarque : l'enum ModeTransport du module contrat n'a pas FLUVIAL et on ne doit pas le modifier,
donc FLUVIAL sera testé avec nos propres tests unitaires.
