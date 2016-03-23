#Projet 2: modifier un logiciel interactif permettant de jouer de la musique

Modifiez ou créer un logiciel permettant d'écrire des notes de musique et de les jouer. On vous propose SimplePianoRoll.zip comme point de départ, mais vous pouvez aussi créer votre propre logiciel, dans le langage de programmation de votre choix, mais il faut demander notre approbation avant.
Quelques remarques concernant SimplePianoRoll:

Le bouton gauche de souris, seul ou avec les touches Ctrl ou Shift, permet de lancer différentes opérations ou d'ouvrir un menu radial ou un Control Menu.
La case à cocher "Loop when playing" n'a actuellement aucun effet: lorsqu'on appuie "Play", la musique boucle quelque soit l'état de la case à cocher "Loop when playing". Aussi, dans le ControlMenu, les options "Tempo", "Total Duration", et "Transpose" n'ont actuellement aucun effet. Aussi, dans le menu "View", la case à cocher "Auto Frame" n'a aucun effet. Vous pouvez enlever ces éléments d'interface si vous voulez, mais ces éléments d'interface pourraient aussi vous aider à compléter certains changements.
Modifications et fonctionnalités à réaliser:

- ~~**[0.5 point]** Permettez à l'utilisateur de changer la durée totale de la portée de notes en glissant dans le ControlMenu.~~

- ~~**[Variante valant 1 point]** Si la case à cocher "Auto Frame" est cochée, pendant que l'utilisateur glisse pour changer la durée totale, faites un zoom automatique en même temps pour que l'utilisateur voit la portée qui se rallonge ou se raccourcit.~~ indice :
```java
gw.frame( score.getBoundingRectangle(),...)
```
- ~~**[1 point]** Permettez à l'utilisateur de sélectionner des notes sur la portée, et de les déplacer en hauteur ou en temps avec un glissement de souris.~~ 

- **[Variante valant 2 points]** Permettez aussi à l'utilisateur de faire une copie d'une selection de notes et de coller cette copie ailleurs en temps.
- ~~**[1 point]** Permettez à l'utilisateur de modifier le tempo (nombre de "temps" par seconde ou battements/seconde ou pulsations/seconde, ou autrement dit le nombre de millisecondes entre les "temps"). Vous pouvez réaliser ce changement en permettant à l'utilisateur de glisser sur "tempo" dans le ControlMenu. Vous devez aussi afficher le tempo (en temps/seconde, ou bien en millisecondes) actuel dans un champ de texte à quelque part.~~ 

- ~~**[Variante valant 2 points]** Pendant que l'utilisateur glisse dans le ControlMenu pour régler le tempo, jouez en temps réel des battements (comme un Métronome) permettant à l'utilisateur d'entendre le tempo avant qu'il ne relâche le bouton de souris.~~
- >**[1 point]** Actuellement, dans SimplePianoRoll, l'utilisateur peut dessiner des notes sur toutes les hauteurs d'une gamme chromatique (autrement dit, toutes les hauteurs de notes sont permises). Si on dessine un ensemble de notes arbitraires, en on appuie "Play", le résultat est souvent désagréable. On peut limiter l'ensemble de notes disponibles à l'utilisateur pour augmenter les chances que le résultat sonore soit agréable. Par exemple, si on limite les notes accessibles aux notes d'une gamme majeure (exemple: la gamme majeure de do, qui correspond aux touches blanches d'un piano: C, D, E, F, G, A, B) et on dessine des notes au hasard, le résultat sonne mieux. Autre exemple: si on se limite à une gamme pentatonique majeure (comme C, D, E, G, A, ou encore les touches noires C#, D#, F#, G#, A#) cela donne un résultat toujours agréable et plutôt oriental. Autre exemple: des gammes comme C, D, E, F#, G, A, B, ou encore C, D, E, F, G, A, A#, donnent aussi des résultats agréables. Pour aider l'utilisateur à composer des phrases musicales agréables, rajouter une option qui limite les notes accessibles aux notes d'une gamme quelconque (comme une gamme majeure, mineur, pentatonique, ou autre). Pendant votre présentation orale, discutez de quelles gammes vous avez essayées et lesquelles semblaient fonctionner mieux. 

- >**[Variante valant 2 points]** Rajouter une option pour générer de la musique alléatoire sur une gamme quelconque. Pendant votre présentation orale, discutez de quelles gammes vous avez essayées, et des algorithmes de choix de notes que vous avez essayés (exemple: choix hasard indépendent pour chaque note? Choix dépendent de la note précédente?), et lesquels semblaient fonctionner mieux. Pour vous inspirer, regardez peut-être l'algorithme dans la section 2.4 de Cabrol et al. 2013
- **[1 point]** Modifiez le logiciel pour permettre des notes de différentes durées.
- **[1 point]** Remplacer le ControlMenu et le menu radial avec une interface alternative pour accéder aux fonctionnalités. Pendant votre présentation orale, discutez des avantages et des inconvénients que vous avez trouvés avec le ControlMenu + menu radial versus votre nouvelle interface.
- ~~**[1 point]** Rajoutez une option pour sauvegarder et lire une pièce d'un fichier. Inventez votre propre format de fichier pour le faire.~~

- >**[Variante valant 2 points]** Utilisez le format MIDI pour sauvegarder et lire vos fichiers. Quelques liens qui pourraient vous servir: http://www.classicalarchives.com: source de fichiers MIDI gratuits de pièces de musique classique

http://opera.media.mit.edu/rogus/home.html: Rogus McJava, librarie permettant de lire des fichiers MIDI, mais pour laquelle le code source ne semble plus disponible :( 

http://www.jsresources.org/examples/midi_files.html 
http://docs.oracle.com/javase/7/docs/api/javax/sound/midi/spi/MidiFileReader.html 
http://www.automatic-pilot.com/midifile.html 
- **[1 point]** Rajoutez un effet visuel artistique ou abstrait pour visualiser ou représenter l'historique des notes. Par exemple, quand la pièce joue, dessinez des tracées de couleurs pour chaque note, qui disparaissent graduellement. Voir la liste de visualisations de musique plus bas dans cet énoncé, et aussi dans les diapos de cours, pour des exemples.
- **[0.5 points]** Passez 20 minutes à essayer de transcrire une pièce de musique que vous aimez, et notez tous les problèmes que vous rencontrez avec l'interface. Pendant votre présentation orale, discutez des changements qui vous semblent importants pour rendre l'interface plus utilisable, et que vous ferez si vous aviez plus de temps.
Vous pouvez aussi proposer d'autres changements pour approbation par le chargé de labo.