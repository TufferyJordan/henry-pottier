package com.jordantuffery.henrypottier

import com.google.gson.Gson
import com.jordantuffery.henrypottier.restapi.Book
import com.jordantuffery.henrypottier.restapi.Offers
import com.jordantuffery.henrypottier.restapi.RestInterface
import retrofit2.Call
import retrofit2.mock.Calls

class ConnectedMockApi() : RestInterface {
    override fun getBooks(): Call<List<Book>> {
        return Calls.response(Gson().fromJson(MOCK_BOOKS, Array<Book>::class.java).toList())
    }

    override fun getOffers(isbn: String): Call<Offers> {
        return Calls.response(Gson().fromJson(MOCK_OFFERS, Offers::class.java))
    }

    private val MOCK_BOOKS = "[\n" +
        "  {\n" +
        "    \"isbn\": \"c8fabf68-8374-48fe-a7ea-a00ccd07afff\",\n" +
        "    \"title\": \"Henri Potier à l'école des sorciers\",\n" +
        "    \"price\": 35,\n" +
        "    \"cover\": \"http://henri-potier.xebia.fr/hp0.jpg\",\n" +
        "    \"synopsis\": [\n" +
        "      \"Après la mort de ses parents (Lily et James Potier), Henri est recueilli par sa tante Pétunia (la sœur de Lily) et son oncle Vernon à l'âge d'un an. Ces derniers, animés depuis toujours d'une haine féroce envers les parents du garçon qu'ils qualifient de gens « bizarres », voire de « monstres », traitent froidement leur neveu et demeurent indifférents aux humiliations que leur fils Dudley lui fait subir. Henri ignore tout de l'histoire de ses parents, si ce n'est qu'ils ont été tués dans un accident de voiture\",\n" +
        "      \"Le jour des 11 ans de Henri, un demi-géant du nom de Rubeus Hagrid vient le chercher pour l’emmener à Poudlard, une école de sorcellerie, où il est inscrit depuis sa naissance et attendu pour la prochaine rentrée. Hagrid lui révèle alors qu’il a toujours été un sorcier, tout comme l'étaient ses parents, tués en réalité par le plus puissant mage noir du monde de la sorcellerie, Voldemort (surnommé « Celui-Dont-On-Ne-Doit-Pas-Prononcer-Le-Nom »), après qu'ils ont refusé de se joindre à lui. Ce serait Henri lui-même, alors qu'il n'était encore qu'un bébé, qui aurait fait ricocher le sortilège que Voldemort lui destinait, neutralisant ses pouvoirs et le réduisant à l'état de créature quasi-insignifiante. Le fait d'avoir vécu son enfance chez son oncle et sa tante dépourvus de pouvoirs magiques lui a donc permis de grandir à l'abri de la notoriété qu'il a dans le monde des sorciers.\",\n" +
        "      \"Henri entre donc à l’école de Poudlard, dirigée par le professeur Albus Dumbledore. Il est envoyé dans la maison Gryffondor par le « choixpeau ». Il y fait la connaissance de Ron Weasley et Hermione Granger, qui deviendront ses complices. Par ailleurs, Henri intègre rapidement l'équipe de Quidditch de sa maison, un sport collectif très populaire chez les sorciers se pratiquant sur des balais volants. Henri connaît probablement la plus heureuse année de sa vie, mais également la plus périlleuse, car Voldemort n'a pas totalement disparu et semble bien décidé à reprendre forme humaine.\"\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"isbn\": \"a460afed-e5e7-4e39-a39d-c885c05db861\",\n" +
        "    \"title\": \"Henri Potier et la Chambre des secrets\",\n" +
        "    \"price\": 30,\n" +
        "    \"cover\": \"http://henri-potier.xebia.fr/hp1.jpg\",\n" +
        "    \"synopsis\": [\n" +
        "      \"Henri Potier passe l'été chez les Dursley et reçoit la visite de Dobby, un elfe de maison. Celui-ci vient l'avertir que des évènements étranges vont bientôt se produire à Poudlard et lui conseille donc vivement de ne pas y retourner. Henri choisit d'ignorer cet avertissement. Le jour de son départ pour l'école, il se retrouve bloqué avec Ron Weasley à la gare de King's Cross, sans pouvoir se rendre sur le quai 9 ¾ où les attend le Poudlard Express. En dernier recours, les garçons se rendent donc à Poudlard à l'aide de la voiture volante de Monsieur Weasley et manquent de peu de se faire renvoyer dès leur arrivée à l'école pour avoir été aperçus au cours de leur voyage par plusieurs moldus.\",\n" +
        "      \"Le nouveau professeur de défense contre les forces du mal, Gilderoy Lockhart, se montre particulièrement narcissique et inefficace. Pendant ce temps, Henri entend une voix étrange en parcourant les couloirs du château, systématiquement associée à la pétrification immédiate d'un élève moldu de l'école. Dès la première attaque, un message sanglant apparaît sur l'un des murs, informant que la Chambre des secrets a été ouverte. Dumbledore et les autres professeurs (ainsi que Henri, Ron et Hermione) doivent prendre les mesures nécessaires pour trouver l'identité du coupable et protéger les élèves contre de nouvelles agressions.\"\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"isbn\": \"fcd1e6fa-a63f-4f75-9da4-b560020b6acc\",\n" +
        "    \"title\": \"Henri Potier et le Prisonnier d'Azkaban\",\n" +
        "    \"price\": 30,\n" +
        "    \"cover\": \"http://henri-potier.xebia.fr/hp2.jpg\",\n" +
        "    \"synopsis\": [\n" +
        "      \"Durant l'été, pour son treizième anniversaire, Henri reçoit plusieurs cartes de ses amis, notamment une lettre de Ron qui lui écrit d'Égypte, où il passe ses vacances avec sa famille. Une lettre du professeur McGonagall, directrice adjointe de Poudlard, lui informe que les élèves de troisième année auront la possibilité de visiter le village de Pré-au-Lard.\",\n" +
        "      \"Le lendemain, les informations télévisées moldues annoncent l'évasion d'un très dangereux prisonnier du nom de Sirius Black et l'oncle et la tante de Henri s'inquiètent légèrement pour eux-mêmes. Plus tard, ils annoncent à Henri que la tante Marge séjournera une semaine chez eux, à Privet Drive. La tante Marge provoque Henri en traitant son père défunt d'ancien ivrogne. Henri s'énerve, perd le contrôle de sa magie et fait accidentellement gonfler la tante Marge comme un ballon. Alors que son oncle, furieux, ordonne à Henri de rendre à Marge son apparence normale, le garçon refuse et prend la fuite en pleine nuit, emportant sa valise et sa chouette Edwige.\",\n" +
        "      \"Plus tard, sur le Chemin de traverse, Henri apprend avec surprise que Sirius Black s'est en fait échappé d'Azkaban pour le retrouver, lui. Il semblerait que l'homme veuille tuer Henri afin de permettre à Lord Voldemort, son maître, de retrouver l'étendue de son pouvoir.\"\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"isbn\": \"c30968db-cb1d-442e-ad0f-80e37c077f89\",\n" +
        "    \"title\": \"Henri Potier et la Coupe de feu\",\n" +
        "    \"price\": 29,\n" +
        "    \"cover\": \"http://henri-potier.xebia.fr/hp3.jpg\",\n" +
        "    \"synopsis\": [\n" +
        "      \"Juste avant d'assister à la coupe du Monde de Quidditch opposant les équipes d'Irlande et de Bulgarie, Henri Potier fait un rêve étrange dans lequel il est témoin du meurtre d'un vieux jardinier moldu par Voldemort, alors que le jardinier surprenait une conversation au sujet de Henri.\",\n" +
        "      \"Au camping de la coupe du Monde, juste après le match, des mangemorts font irruption en pleine nuit et provoquent la panique parmi les supporters, en faisant apparaître la Marque des Ténèbres dans le ciel, et annonçant le retour du mage noir.\",\n" +
        "      \"Henri passe le reste des vacances d'été au Terrier sous haute surveillance, et entame une nouvelle année à Poudlard, annoncée comme une véritable année de compétition. En effet, l'école accueille exceptionnellement un grand évènement : le Tournoi des Trois Sorciers. À cette occasion, Poudlard accueille également des délégations de deux autres écoles de magie : celles de Durmstrang et de Beauxbâtons. Contre toute attente, alors que les trois champions sont choisis par la Coupe de Feu (Viktor Krum pour Durmstrang, Fleur Delacour pour Beauxbâtons et Cedric Diggory pour Poudlard), un deuxième champion de Poudlard est désigné, et il s'agit de Henri Potier.\"\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"isbn\": \"78ee5f25-b84f-45f7-bf33-6c7b30f1b502\",\n" +
        "    \"title\": \"Henri Potier et l'Ordre du phénix\",\n" +
        "    \"price\": 28,\n" +
        "    \"cover\": \"http://henri-potier.xebia.fr/hp4.jpg\",\n" +
        "    \"synopsis\": [\n" +
        "      \"Pour faire face au retour de Voldemort, les membres de l'Ordre du phénix, sous la direction d'Albus Dumbledore, sont rassemblés au 12, Square Grimmaurd à Londres, leur quartier général. Le ministre de la magie ne croit pas au retour de Voldemort. La gazette du sorcier contribue à répandre ses idées, notamment en sous-entendant que Henri est un menteur ou que Dumbledore perd la raison.\",\n" +
        "      \"Henri, Ron et Hermione font leur entrée en 5e année à Poudlard, qui est de plus en plus contrôlé par le ministère, notamment par la présence de Dolores Ombrage, sous-secrétaire du ministre et professeur de défense contre les forces du mal, qui instaure une forme de régime dictatorial en devenant grande inquisitrice et en interdisant aux élèves de s'exercer aux sortilèges de défense. Hermione crée alors « l'armée de Dumbledore », une association d'élèves secrète qui permet à ceux qui le souhaitent de pratiquer la défense contre les forces du mal sous l'enseignement de Henri.\"\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"isbn\": \"cef179f2-7cbc-41d6-94ca-ecd23d9f7fd6\",\n" +
        "    \"title\": \"Henri Potier et le Prince de sang-mêlé\",\n" +
        "    \"price\": 30,\n" +
        "    \"cover\": \"http://henri-potier.xebia.fr/hp5.jpg\",\n" +
        "    \"synopsis\": [\n" +
        "      \"Henri rentre en sixième année à l'école de sorcellerie Poudlard. Il entre alors en possession d'un livre de potion portant le mot « propriété du Prince de sang-mêlé » et commence à en savoir plus sur le sombre passé de Voldemort qui était encore connu sous le nom de Tom Jedusor.\"\n" +
        "    ]\n" +
        "  },\n" +
        "  {\n" +
        "    \"isbn\": \"bbcee412-be64-4a0c-bf1e-315977acd924\",\n" +
        "    \"title\": \"Henri Potier et les Reliques de la Mort\",\n" +
        "    \"price\": 35,\n" +
        "    \"cover\": \"http://henri-potier.xebia.fr/hp6.jpg\",\n" +
        "    \"synopsis\": [\n" +
        "      \"Cette année, Henri a 17 ans et ne retourne pas à l'école de Poudlard après la mort de Dumbledore. Avec Ron et Hermione il se consacre à la dernière mission confiée par Dumbledore. Le Seigneur des Ténèbres règne en maître et traque les fidèles amis qui sont réduit à la clandestinité. D'épreuves en révélations, le courage, les choix et les sacrifices de Henri seront déterminants dans la lutte contre les forces du Mal.\"\n" +
        "    ]\n" +
        "  }\n" +
        "]"

    private val MOCK_OFFERS = "{\n" +
        "  \"offers\": [\n" +
        "    {\n" +
        "      \"type\": \"percentage\",\n" +
        "      \"value\": 5\n" +
        "    },\n" +
        "    {\n" +
        "      \"type\": \"minus\",\n" +
        "      \"value\": 15\n" +
        "    },\n" +
        "    {\n" +
        "      \"type\": \"slice\",\n" +
        "      \"sliceValue\": 100,\n" +
        "      \"value\": 12\n" +
        "    }\n" +
        "  ]\n" +
        "}"
}