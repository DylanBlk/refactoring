# Bilan

## Étape 5 : qui choisit la stratégie ?

Deux possibilités :
- l'enum porte la stratégie (ModeTransport.ROUTE connaît TarifRoute)
- une Map<ModeTransport, TarifTransport> est injectée

On a choisi l'enum : c'est plus simple (mode.getTarif()) et on ne peut pas oublier un mode,
le compilateur oblige à donner un tarif à chaque valeur. La Map serait utile si on voulait changer
de tarif sans toucher à l'enum, par exemple pour des tests.
Même chose pour la remise (TypeClient porte sa PolitiqueRemise) et pour les délais.

## Étape 8 : ajout de FLUVIAL et du supplément hors gabarit

Nouvelles classes : TarifFluvial, DelaiFluvial, Peniche, SupplementHorsGabarit
(+ tests : FluvialTest, SupplementHorsGabaritTest).

Fichiers existants modifiés :
- ModeTransport : une ligne pour FLUVIAL
- Flotte : une ligne pour la péniche PEN-0001
- TableDistances : une ligne pour Paris - Le Havre
- CalculateurPrix : une ligne dans l'assemblage des décorateurs
- Application et Main : choix des tarifs (avec ou sans hors gabarit)

Aucune classe métier n'a été touchée (ServiceExpedition, les autres tarifs, les autres décorateurs,
AffectationVehicule, ValidateurExpedition). Avec l'ancien code il fallait modifier 7 fichiers
(voir diagnostic.md).

Les 68 tests passent toujours. Par contre les colis des tests de contrat font plus de 2 m
(fûts de 240 cm, conteneur de 12 m), donc le supplément hors gabarit change leur prix.
C'est une nouvelle règle métier, pas un refactoring. On a donc deux versions :
- CalculateurPrix.tarifsHistoriques() : par défaut et pour les tests de contrat
- CalculateurPrix.avecHorsGabarit() : les nouveaux tarifs, utilisés dans Main

## Grille d'auto-évaluation

| Critère | OK ? |
|---|---|
| 68 tests de contrat verts | oui |
| Aucun nombre magique | oui |
| Aucun if/switch sur un mode, un type de client ou un type de marchandise | oui (*) |
| Strategy pour tarifs et remises, Decorator pour les suppléments, avec tests unitaires | oui |
| Aucun instanceof, aucune UnsupportedOperationException | oui |
| Aucun static modifiable, aucun singleton | oui |
| L'adaptateur ne fait que traduire | oui |
| FLUVIAL et hors gabarit = nouvelles classes + assemblage | oui |

(*) les décorateurs testent encore "y a-t-il un colis fragile ?", mais c'est justement leur rôle.

## Différence de comportement

Avant, une expédition route avec des colis réfrigérés et non réfrigérés mélangés partait dans le
camion frigo, qui plantait au chargement. Maintenant le camion frigo refuse dès le départ
et l'expédition est refusée. Aucun test de contrat ne couvre ce cas.
