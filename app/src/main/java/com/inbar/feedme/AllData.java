package com.inbar.feedme;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Inbar on 9/26/2016.
 */

public final class AllData {

    public final static ArrayList<Recipe> recipes = new ArrayList<Recipe>(Arrays.asList(
            new Recipe(
                1000, "Israeli Salad", 5, R.drawable.rec_israeli_salad, true,
                new ArrayList<> (Arrays.asList(
                        new Ingredient("Tomato", 1, Ingredient.Units.UNITS),
                        new Ingredient("Cucumber", 1, Ingredient.Units.UNITS),
                        new Ingredient("Dry onion (optional)", 0.5, Ingredient.Units.UNITS),
                        new Ingredient("Coriander (optional)", 0.5, Ingredient.Units.BUNDLES),
                        new Ingredient("Lemon", 0.5, Ingredient.Units.UNITS),
                        new Ingredient("Extra virgin olive oil", 2, Ingredient.Units.TBSPS),
                        new Ingredient("Table salt or atlantic sea salt", 2, Ingredient.Units.TSPS)
                )),
                new ArrayList<> (Arrays.asList(
                        "Finely chop the tomato, cucumber, onion and coriander",
                        "Put them all in a serving bowl",
                        "Squeeze the lemon on top",
                        "Add the olive oil",
                        "Mix it all together")
                )
            ),
            new Recipe(
                    1001, "Scrambled Eggs", 5, R.drawable.rec_scrambled_eggs, true,
                    new ArrayList<> (Arrays.asList(
                            new Ingredient("Eggs", 2, Ingredient.Units.UNITS),
                            new Ingredient("Milk", 1, Ingredient.Units.TBSPS),
                            new Ingredient("Table salt", 2, Ingredient.Units.PINCHES),
                            new Ingredient("Canola oil", 1, Ingredient.Units.TBSPS)
                    )),
                    new ArrayList<> (Arrays.asList(
                            "Break the eggs into a mixing bowl",
                            "Beat the eggs into an homogeneous mixture",
                            "Add the milk (you may use water for Parve) and salt and beat it some more",
                            "Pour the oil into a pan and heat it up",
                            "Once the oil is warm pour in the egg mixture and start scrambling " +
                                    "until the egg is cooked to your taste")
                    )
            ),
            new Recipe(
                    1002, "Kushkush Pasta", 15, R.drawable.rec_pasta_egg, true,
                    new ArrayList<> (Arrays.asList(
                            new Ingredient("Eggs", 3, Ingredient.Units.UNITS),
                            new Ingredient("Cream cheese", 4, Ingredient.Units.TBSPS),
                            new Ingredient("Grated Feta cheese", 50, Ingredient.Units.GRAMS),
                            new Ingredient("Grated medium-hard cheese", 50, Ingredient.Units.GRAMS),
                            new Ingredient("Table salt", 1.5, Ingredient.Units.TSPS),
                            new Ingredient("Tap water", 1, Ingredient.Units.LITRES),
                            new Ingredient("Fine noodles", 200, Ingredient.Units.GRAMS),
                            new Ingredient("Canola oil", 1, Ingredient.Units.TBSPS)
                    )),
                    new ArrayList<> (Arrays.asList(
                            "Boil the water in a pot",
                            "Add the noodles and 1 tsps of salt",
                            "Let it cook for 5 minutes until the noodles are ready",
                            "Take the noodles off the stove and dry them in a strainer",
                            "Break the eggs into a mixing bowl",
                            "Beat the eggs into an homogeneous mixture",
                            "Add the cheeses and salt and beat it some more",
                            "Add the prepared noodles",
                            "Pour the oil into a pan and heat it up",
                            "Once the oil is warm pour in the egg mixture",
                            "When the bottom is coked toyour taste, flip it over",
                            "Take out when the dish is cooked to taste on both sides")
                    )
            ),
            new Recipe(1003, "Kichri Rice", 30, R.drawable.rec_kichri_rice, true),
            new Recipe(1004, "Kubbeh Hamusta", 60, R.drawable.rec_kubbeh_hamusta, false),
            new Recipe(1005, "Red Kubbeh", 60, R.drawable.rec_red_kubbeh, false),
            new Recipe(1006, "Beet Kubbeh", 60, R.drawable.rec_beet_kubbeh, false),
            new Recipe(1007, "Onion Soup", 180, R.drawable.rec_onion_soup, false),
            new Recipe(1008, "Mashed Fruits", 15, R.drawable.rec_mashed_fruits, false),
            new Recipe(1009, "Cheese Toast", 17, R.drawable.rec_cheese_toast, false),
            new Recipe(1010, "Shakshuka", 45, R.drawable.rec_shakshuka, false)
    ));



    /*

    static def recipeData = [
            '1000': israeliSalad,
            '1001': scrambledEggs,
            '1002': kushkushPasta,
            '1003': kichriRice,
            '1004': kubbehHamusta,
            '1005': redKubbeh,
            '1006': beetKubbeh,
            '1007': onionSoup,
            '1008': mashedFruits,
            '1009': cheeseToast,
            '1010': shakshuka
    ];

    static def hanselAndGrethel = [
    id         : '2000',
    name       : 'HANSEL AND GRETHEL',
    author     : 'Jacob Grimm and Wilhelm Grimm',
    thumbnail  : R.drawable.sto_hansel_and_grethel,
    paragraphs : [['Once upon a time there dwelt near a large wood a poor woodcutter, with\n' +
            'his wife and two children by his former marriage, a little boy called\n' +
            'Hansel, and a girl named Grethel. He had little enough to break or bite;\n' +
            'and once, when there was a great famine in the land, he could not\n' +
            'procure even his daily bread; and as he lay thinking in his bed one\n' +
            'evening, rolling about for trouble, he sighed, and said to his wife,\n' +
            '"What will become of us? How can we feed our children, when we have no\n' +
            'more than we can eat ourselves?"\n'],
            ['"Know, then, my husband," answered she, "we will lead them away, quite\n' +
            'early in the morning, into the thickest part of the wood, and there make\n' +
            'them a fire, and give them each a little piece of bread; then we will go\n' +
            'to our work, and leave them alone, so they will not find the way home\n' +
            'again, and we shall be freed from them." "No, wife," replied he, "that I\n' +
            'can never do. How can you bring your heart to leave my children all\n' +
            'alone in the wood, for the wild beasts will soon come and tear them to\n' +
            'pieces?"\n'],
            ['"Oh, you simpleton!" said she, "then we must all four die of hunger; you\n' +
            'had better plane the coffins for us." But she left him no peace till he\n' +
            'consented, saying, "Ah, but I shall regret the poor children."\n'],
            ['The two children, however, had not gone to sleep for very hunger, and so\n' +
            'they overheard what the stepmother said to their father. Grethel wept\n' +
            'bitterly, and said to Hansel, "What will become of us?" "Be quiet,\n' +
            'Grethel," said he; "do not cry--I will soon help you." And as soon as\n' +
            'their parents had fallen asleep, he got up, put on his coat, and,\n' +
            'unbarring the back door, slipped out. The moon shone brilliantly, and\n' +
            'the white pebbles which lay before the door seemed like silver pieces,\n' +
            'they glittered so brightly. Hansel stooped down, and put as many into\n' +
            'his pocket as it would hold; and then going back, he said to Grethel,\n' +
            '"Be comforted, dear sister, and sleep in peace; God will not forsake\n' +
            'us." And so saying, he went to bed again.\n']
            ['The next morning, before the sun arose, the wife went and awoke the two\n' +
            'children. "Get up, you lazy things; we are going into the forest to chop\n' +
            'wood." Then she gave them each a piece of bread, saying, "There is\n' +
            'something for your dinner; do not eat it before the time, for you will\n' +
            'get nothing else." Grethel took the bread in her apron, for Hansel\'s\n' +
            'pocket was full of pebbles; and so they all set out upon their way. When\n' +
            'they had gone a little distance, Hansel stood still, and peeped back at\n' +
            'the house; and this he repeated several times, till his father said,\n' +
            '"Hansel, what are you peeping at, and why do you lag behind? Take care,\n' +
            'and remember your legs."\n'],
            ['"Ah, father," said Hansel, "I am looking at my white cat sitting upon\n' +
            'the roof of the house, and trying to say good-bye." "You simpleton!"\n' +
            'said the wife, "that is not a cat; it is only the sun shining on the\n' +
            'white chimney." But in reality Hansel was not looking at a cat; but\n' +
            'every time he stopped, he dropped a pebble out of his pocket upon the\n' +
            'path.\n'],
            ['When they came to the middle of the forest, the father told the children\n' +
            'to collect wood, and he would make them a fire, so that they should not\n' +
            'be cold. So Hansel and Grethel gathered together quite a little mountain\n' +
            'of twigs. Then they set fire to them; and as the flame burnt up high,\n' +
            'the wife said, "Now, you children, lie down near the fire, and rest\n' +
            'yourselves, while we go into the forest and chop wood; when we are\n' +
            'ready, I will come and call you."\n'],
            ['Hansel and Grethel sat down by the fire, and when it was noon, each ate\n' +
            'the piece of bread; and because they could hear the blows of an axe,\n' +
            'they thought their father was near: but it was not an axe, but a branch\n' +
            'which he had bound to a withered tree, so as to be blown to and fro by\n' +
            'the wind. They waited so long that at last their eyes closed from\n' +
            'weariness, and they fell fast asleep. When they awoke, it was quite\n' +
            'dark, and Grethel began to cry, "How shall we get out of the wood?" But\n' +
            'Hansel tried to comfort her by saying, "Wait a little while till the\n' +
            'moon rises, and then we will quickly find the way." The moon soon shone\n' +
            'forth, and Hansel, taking his sister\'s hand, followed the pebbles, which\n' +
            'glittered like new-coined silver pieces, and showed them the path. All\n' +
            'night long they walked on, and as day broke they came to their father\'s\n' +
            'house. They knocked at the door, and when the wife opened it, and saw\n' +
            'Hansel and Grethel, she exclaimed, "You wicked children! why did you\n' +
            'sleep so long in the wood? We thought you were never coming home again."\n' +
            'But their father was very glad, for it had grieved his heart to leave\n' +
            'them all alone.\n'],
            ['Not long afterward there was again great scarcity in every corner of the\n' +
            'land; and one night the children overheard their stepmother saying to\n' +
            'their father, "Everything is again consumed; we have only half a loaf\n' +
            'left, and then the song is ended: the children must be sent away. We\n' +
            'will take them deeper into the wood, so that they may not find the way\n' +
            'out again; it is the only means of escape for us."\n'],
            ['But her husband felt heavy at heart, and thought, "It were better to\n' +
            'share the last crust with the children." His wife, however, would listen\n' +
            'to nothing that he said, and scolded and reproached him without end.\n'],
            ['He who says A must say B too; and he who consents the first time must\n' +
            'also the second.\n'],
            ['The children, however, had heard the conversation as they lay awake, and\n' +
            'as soon as the old people went to sleep Hansel got up, intending to pick\n' +
            'up some pebbles as before; but the wife had locked the door, so that he\n' +
            'could not get out. Nevertheless, he comforted Grethel, saying, "Do not\n' +
            'cry; sleep in quiet; the good God will not forsake us."\n'],
            ['Early in the morning the stepmother came and pulled them out of bed, and\n' +
            'gave them each a slice of bread, which was still smaller than the former\n' +
            'piece. On the way, Hansel broke his in his pocket, and, stooping every\n' +
            'now and then, dropped a crumb upon the path. "Hansel, why do you stop\n' +
            'and look about?" said the father; "keep in the path." "I am looking at\n' +
            'my little dove," answered Hansel, "nodding a good-bye to me."\n' +
            '"Simpleton!" said the wife, "that is no dove, but only the sun shining\n' +
            'on the chimney." But Hansel still kept dropping crumbs as he went along.\n'],
            ['The mother led the children deep into the wood, where they had never\n' +
            'been before, and there making an immense fire, she said to them, "Sit\n' +
            'down here and rest, and when you feel tired you can sleep for a little\n' +
            'while. We are going into the forest to hew wood, and in the evening,\n' +
            'when we are ready, we will come and fetch you."\n'],
            ['When noon came Grethel shared her bread with Hansel, who had strewn his\n' +
            'on the path. Then they went to sleep; but the evening arrived and no one\n' +
            'came to visit the poor children, and in the dark night they awoke, and\n' +
            'Hansel comforted his sister by saying, "Only wait, Grethel, till the\n' +
            'moon comes out, then we shall see the crumbs of bread which I have\n' +
            'dropped, and they will show us the way home." The moon shone and they\n' +
            'got up, but they could not see any crumbs, for the thousands of birds\n' +
            'which had been flying about in the woods and fields had picked them all\n' +
            'up. Hansel kept saying to Grethel, "We will soon find the way"; but they\n' +
            'did not, and they walked the whole night long and the next day, but\n' +
            'still they did not come out of the wood; and they got so hungry, for\n' +
            'they had nothing to eat but the berries which they found upon the\n' +
            'bushes. Soon they got so tired that they could not drag themselves\n' +
            'along, so they lay down under a tree and went to sleep.\n'],
            ['It was now the third morning since they had left their father\'s house,\n' +
            'and they still walked on; but they only got deeper and deeper into the\n' +
            'wood, and Hansel saw that if help did not come very soon they would die\n' +
            'of hunger. At about noonday they saw a beautiful snow-white bird sitting\n' +
            'upon a bough, which sang so sweetly that they stood still and listened\n' +
            'to it. It soon ceased, and spreading its wings flew off; and they\n' +
            'followed it until it arrived at a cottage, upon the roof of which it\n' +
            'perched; and when they went close up to it they saw that the cottage was\n' +
            'made of bread and cakes, and the window-panes were of clear sugar.\n'],
            ['"We will go in there," said Hansel, "and have a glorious feast. I will\n' +
            'eat a piece of the roof, and you can eat the window. Will they not be\n' +
            'sweet?" So Hansel reached up and broke a piece off the roof, in order to\n' +
            'see how it tasted, while Grethel stepped up to the window and began to\n' +
            'bite it. Then a sweet voice called out in the room, "Tip-tap, tip-tap,\n' +
            'who raps at my door?" and the children answered, "the wind, the wind,\n' +
            'the child of heaven"; and they went on eating without interruption.\n' +
            'Hansel thought the roof tasted very nice, so he tore off a great piece;\n' +
            'while Grethel broke a large round pane out of the window, and sat down\n' +
            'quite contentedly. Just then the door opened, and a very old woman,\n' +
            'walking upon crutches, came out. Hansel and Grethel were so frightened\n' +
            'that they let fall what they had in their hands; but the old woman,\n' +
            'nodding her head, said, "Ah, you dear children, what has brought you\n' +
            'here? Come in and stop with me, and no harm shall befall you"; and so\n' +
            'saying she took them both by the hand, and led them into her cottage. A\n' +
            'good meal of milk and pancakes, with sugar, apples, and nuts, was spread\n' +
            'on the table, and in the back room were two nice little beds, covered\n' +
            'with white, where Hansel and Grethel laid themselves down, and thought\n' +
            'themselves in heaven. The old woman behaved very kindly to them, but in\n' +
            'reality she was a wicked witch who waylaid children, and built the\n' +
            'bread-house in order to entice them in, but as soon as they were in her\n' +
            'power she killed them, cooked and ate them, and made a great festival of\n' +
            'the day. Witches have red eyes, and cannot see very far; but they have a\n' +
            'fine sense of smelling, like wild beasts, so that they know when\n' +
            'children approach them. When Hansel and Grethel came near the witch\'s\n' +
            'house she laughed wickedly, saying, "Here come two who shall not escape\n' +
            'me." And early in the morning, before they awoke, she went up to them,\n' +
            'and saw how lovingly they lay sleeping, with their chubby red cheeks,\n' +
            'and she mumbled to herself, "That will be a good bite." Then she took up\n' +
            'Hansel with her rough hands, and shut him up in a little cage with a\n' +
            'lattice-door; and although he screamed loudly it was of no use. Grethel\n' +
            'came next, and, shaking her till she awoke, the witch said, "Get up, you\n' +
            'lazy thing, and fetch some water to cook something good for your\n' +
            'brother, who must remain in that stall and get fat; when he is fat\n' +
            'enough I shall eat him." Grethel began to cry, but it was all useless,\n' +
            'for the old witch made her do as she wished. So a nice meal was cooked\n' +
            'for Hansel, but Grethel got nothing but a crab\'s claw.\n'],
            ['Every morning the old witch came to the cage and said, "Hansel, stretch\n' +
            'out your finger that I may feel whether you are getting fat." But Hansel\n' +
            'used to stretch out a bone, and the old woman, having very bad sight,\n' +
            'thought it was his finger, and wondered very much that he did not get\n' +
            'fatter. When four weeks had passed, and Hansel still kept quite lean,\n' +
            'she lost all her patience, and would not wait any longer. "Grethel," she\n' +
            'called out in a passion, "get some water quickly; be Hansel fat or lean,\n' +
            'this morning I will kill and cook him." Oh, how the poor little sister\n' +
            'grieved, as she was forced to fetch the water, and fast the tears ran\n' +
            'down her cheeks! "Dear good God, help us now!" she exclaimed. "Had we\n' +
            'only been eaten by the wild beasts in the wood, then we should have died\n' +
            'together." But the old witch called out, "Leave off that noise; it will\n' +
            'not help you a bit."\n'],
            ['So early in the morning Grethel was forced to go out and fill the\n' +
            'kettle, and make a fire. "First, we will bake, however," said the old\n' +
            'woman; "I have already heated the oven and kneaded the dough"; and so\n' +
            'saying, she pushed poor Grethel up to the oven, out of which the flames\n' +
            'were burning fiercely. "Creep in," said the witch, "and see if it is hot\n' +
            'enough, and then we will put in the bread"; but she intended when\n' +
            'Grethel got in to shut up the oven and let her bake, so that she might\n' +
            'eat her as well as Hansel. Grethel perceived what her thoughts were, and\n' +
            'said, "I do not know how to do it; how shall I get in?" "You stupid\n' +
            'goose," said she, "the opening is big enough. See, I could even get in\n' +
            'myself!" and she got up, and put her head into the oven. Then Grethel\n' +
            'gave her a push, so that she fell right in, and then shutting the iron\n' +
            'door she bolted it! Oh! how horribly she howled; but Grethel ran away,\n' +
            'and left the ungodly witch to burn to ashes.\n'],
            ['Now she ran to Hansel, and, opening his door, called out, "Hansel, we\n' +
            'are saved; the old witch is dead!" So he sprang out, like a bird out of\n' +
            'his cage when the door is opened; and they were so glad that they fell\n' +
            'upon each other\'s neck, and kissed each other over and over again. And\n' +
            'now, as there was nothing to fear, they went into the witch\'s house,\n' +
            'where in every corner were caskets full of pearls and precious stones.\n' +
            '"These are better than pebbles," said Hansel, putting as many into his\n' +
            'pocket as it would hold; while Grethel thought, "I will take some too,"\n' +
            'and filled her apron full. "We must be off now," said Hansel, "and get\n' +
            'out of this enchanted forest." But when they had walked for two hours\n' +
            'they came to a large piece of water. "We cannot get over," said Hansel;\n' +
            '"I can see no bridge at all." "And there is no boat, either," said\n' +
            'Grethel; "but there swims a white duck, and I will ask her to help us\n' +
            'over." And she sang:\n'],
            ['  "Little Duck, good little Duck,\n' +
            '    Grethel and Hansel, here we stand;\n' +
            '  There is neither stile nor bridge,\n' +
            '    Take us on your back to land."\n'],
            ['So the duck came to them, and Hansel sat himself on, and bade his sister\n' +
            'sit behind him. "No," answered Grethel, "that will be too much for the\n' +
            'duck; she shall take us over one at a time." This the good little bird\n' +
            'did, and when both were happily arrived on the other side, and had gone\n' +
            'a little way, they came to a well-known wood, which they knew the better\n' +
            'every step they went, and at last they perceived their father\'s house.\n' +
            'Then they began to run, and, bursting into the house, they fell into\n' +
            'their father\'s arms. He had not had one happy hour since he had left the\n' +
            'children in the forest; and his wife was dead. Grethel shook her apron,\n' +
            'and the pearls and precious stones rolled out upon the floor, and Hansel\n' +
            'threw down one handful after the other out of his pocket. Then all their\n' +
            'sorrows were ended, and they lived together in great happiness.\n'],
            ['My tale is done. There runs a mouse; whoever catches her may make a\n' +
            'great, great cap out of her fur.']],
    recipe     : '1000'
            ];

    static def littleRedCap = [
    id         : '2001',
    name       : 'LITTLE RED CAP',
    author     : 'Jacob Grimm and Wilhelm Grimm',
    thumbnail  : R.drawable.sto_little_red_cap,
    paragraphs : [['Many years ago there lived a dear little girl who was beloved by every\n' +
            'one who knew her; but her grand-mother was so very fond of her that she\n' +
            'never felt she could think and do enough to please this dear\n' +
            'grand-daughter, and she presented the little girl with a red silk cap,\n' +
            'which suited her so well, that she would never wear anything else, and\n' +
            'so was called Little Red-Cap.\n'],
            ['One day Red-Cap\'s mother said to her, "Come, Red-Cap, here is a nice\n' +
            'piece of meat, and a bottle of wine: take these to your grandmother; she\n' +
            'is weak and ailing, and they will do her good. Be there before she gets\n' +
            'up; go quietly and carefully."\n'],
            ['The grandmother lived far away in the wood, a long walk from the\n' +
            'village, and as Little Red-Cap came among the trees she met a Wolf; but\n' +
            'she did not know what a wicked animal it was, and so she was not at all\n' +
            'frightened. "Good morning, Little Red-Cap," he said.\n'],
            ['"Thank you, Mr. Wolf," said she.\n'],
            ['"Where are you going so early, Little Red-Cap?"\n'],
            ['"To my grandmother\'s," she answered.\n'],
            ['"And what are you carrying in that basket?"\n'],
            ['"Some wine and meat," she replied. "We baked the meat yesterday, so that\n' +
            'grandmother, who is very weak, might have a nice strengthening meal."\n'],
            ['"And where does your grandmother live?" asked the Wolf.\n'],
            ['"Oh, quite twenty minutes walk further in the forest. The cottage stands\n' +
            'under three great oak trees; and close by are some nut bushes, by which\n' +
            'you will at once know it."\n'],
            ['The Wolf was thinking to himself, "She is a nice tender thing, and will\n' +
            'taste better than the old woman; I must act cleverly, that I may make a\n' +
            'meal of both."\n'],
            ['[Illustration: THE VALIANT LITTLE TAILOR.]\n'],
            ['Presently he came up again to Little Red-Cap, and said, "Just look at\n' +
            'the beautiful flowers which grow around you; why do you not look about\n' +
            'you? I believe you don\'t hear how sweetly the birds are singing. You\n' +
            'walk as if you were going to school; see how cheerful everything is\n' +
            'about you in the forest."\n'],
            ['And Little Red-Cap opened her eyes; and when she saw how the sunbeams\n' +
            'glanced and danced through the trees, and what bright flowers were\n' +
            'blooming in her path, she thought, "If I take my grandmother a fresh\n' +
            'nosegay, she will be very much pleased; and it is so very early that I\n' +
            'can, even then, get there in good time;" and running into the forest,\n' +
            'she looked about for flowers. But when she had once begun she did not\n' +
            'know how to leave off, and kept going deeper and deeper amongst the\n' +
            'trees looking for some still more beautiful flower. The Wolf, however,\n' +
            'ran straight to the house of the old grandmother, and knocked at the\n' +
            'door.\n'],
            ['"Who\'s there?" asked the old lady.\n'],
            ['"Only Little Red-Cap, bringing you some meat and wine; please open the\n' +
            'door," answered the Wolf. "Lift up the latch," cried the grandmother; "I\n' +
            'am much too ill to get up myself."\n'],
            ['So the Wolf lifted the latch, and the door flew open; and without a\n' +
            'word, he jumped on to the bed, and gobbled up the poor old lady. Then he\n' +
            'put on her clothes, and tied her night-cap over his head; got into the\n' +
            'bed, and drew the blankets over him. All this time Red-Cap was gathering\n' +
            'flowers; and when she had picked as many as she could carry, she thought\n' +
            'of her grandmother, and hurried to the cottage. She wondered greatly to\n' +
            'find the door open; and when she got into the room, she began to feel\n' +
            'very ill, and exclaimed, "How sad I feel! I wish I had not come to-day."\n' +
            'Then she said, "Good morning," but received no reply; so she went up to\n' +
            'the bed, and drew back the curtains, and there lay her grandmother, as\n' +
            'she imagined, with the cap drawn half over her eyes, and looking very\n' +
            'fierce.\n'],
            ['"Oh, grandmother, what great ears you have!" she said.\n'],
            ['"All the better to hear you with," was the reply.\n'],
            ['"And what great eyes you have!"\n'],
            ['"All the better to see you with."\n'],
            ['"And what great hands you have!"\n'],
            ['"All the better to touch you with."\n'],
            ['"But, grandmother, what very great teeth you have!"\n'],
            ['"All the better to eat you with;" and hardly were the words spoken when\n' +
            'the Wolf made a jump out of bed, and swallowed up poor Little Red-Cap\n' +
            'also.\n'],
            ['As soon as the Wolf had thus satisfied his hunger, he laid himself down\n' +
            'again on the bed, and went to sleep and snored very loudly. A huntsman\n' +
            'passing by overheard him, and said, "How loudly that old woman snores! I\n' +
            'must see if anything is the matter."\n'],
            ['So he went into the cottage; and when he came to the bed, he saw the\n' +
            'Wolf sleeping in it. "What! are you here, you old rascal? I have been\n' +
            'looking for you," exclaimed he; and taking up his gun, he shot the old\n' +
            'Wolf through the head.\n'],
            ['But it is also said that the story ends in a different manner; for that\n' +
            'one day, when Red-Cap was taking some presents to her grandmother, a\n' +
            'Wolf met her, and wanted to mislead her; but she went straight on, and\n' +
            'told her grandmother that she had met a Wolf, who said good day, and who\n' +
            'looked so hungrily out of his great eyes, as if he would have eaten her\n' +
            'up had she not been on the high-road.\n'],
            ['So her grandmother said, "We will shut the door, and then he cannot get\n' +
            'in." Soon after, up came the Wolf, who tapped, and exclaimed, "I am\n' +
            'Little Red-Cap, grandmother; I have some roast meat for you." But they\n' +
            'kept quite quiet, and did not open the door; so the Wolf, after looking\n' +
            'several times round the house, at last jumped on the roof, thinking to\n' +
            'wait till Red-Cap went home in the evening, and then to creep after her\n' +
            'and eat her in the darkness. The old woman, however, saw what the\n' +
            'villain intended. There stood before the door a large stone trough, and\n' +
            'she said to Little Red-Cap, "Take this bucket, dear: yesterday I boiled\n' +
            'some meat in this water, now pour it into the stone trough." Then the\n' +
            'Wolf sniffed the smell of the meat, and his mouth watered, and he wished\n' +
            'very much to taste. At last he stretched his neck too far over, so that\n' +
            'he lost his balance, and fell down from the roof, right into the great\n' +
            'trough below, and there he was drowned.']]
            ];


    static def cinderella = [
    id         : '2002',
    name       : 'CINDERELLA',
    author     : 'Jacob Grimm and Wilhelm Grimm',
    thumbnail  : R.drawable.sto_cinderella,
    paragraphs : [['The wife of a rich man fell sick: and when she felt that her end drew\n' +
            'nigh, she called her only daughter to her bedside, and said, "Always be\n' +
            'a good girl, and I will look down from heaven and watch over you." Soon\n' +
            'afterwards she shut her eyes and died, and was buried in the garden; and\n' +
            'the little girl went every day to her grave and wept, and was always\n' +
            'good and kind to all about her. And the snow spread a beautiful white\n' +
            'covering over the grave; but by the time the sun had melted it away\n' +
            'again, her father had married another wife. This new wife had two\n' +
            'daughters of her own: they were fair in face but foul at heart, and it\n' +
            'was now a sorry time for the poor little girl. "What does the\n' +
            'good-for-nothing thing want in the parlor?" said they; and they took\n' +
            'away her fine clothes, and gave her an old frock to put on, and laughed\n' +
            'at her and turned her into the kitchen.\n'],
            ['Then she was forced to do hard work; to rise early, before daylight, to\n' +
            'bring the water, to make the fire, to cook and to wash. She had no bed\n' +
            'to lie down on, but was made to lie by the hearth among the ashes, and\n' +
            'they called her Cinderella.\n'],
            ['It happened once that her father was going to the fair, and asked his\n' +
            'wife\'s daughters what he should bring to them. "Fine clothes," said the\n' +
            'first. "Pearls and diamonds," said the second. "Now, child," said he to\n' +
            'his own daughter, "what will you have?" "The first sprig, dear father,\n' +
            'that rubs against your hat on your way home," said she. Then he bought\n' +
            'for the two first the fine clothes and pearls and diamonds they had\n' +
            'asked for: and on his way home, as he rode through a green copse, a\n' +
            'sprig of hazel brushed against him, so he broke it off and when he got\n' +
            'home he gave it to his daughter. Then she took it, and went to her\n' +
            'mother\'s grave and planted it there, and cried so much that it was\n' +
            'watered with her tears; and there it grew and became a fine tree, and\n' +
            'soon a little bird came and built its nest upon the tree, and talked\n' +
            'with her and watched over her, and brought her whatever she wished for.\n'],
            ['Now it happened that the king of the land held a feast which was to last\n' +
            'three days, and out of those who came to it his son was to choose a\n' +
            'bride for himself; and Cinderella\'s two sisters were asked to come. So\n' +
            'they called Cinderella, and said, "Now, comb our hair, brush our shoes,\n' +
            'and tie our sashes for us, for we are going to dance at the king\'s\n' +
            'feast." Then she did as she was told, but when all was done she could\n' +
            'not help crying, for she thought to herself, she would have liked to go\n' +
            'to the dance too, and at last she begged her mother very hard to let her\n' +
            'go, "You! Cinderella?" said she; "you who have nothing to wear, no\n' +
            'clothes at all, and who cannot even dance--you want to go to the ball?"\n' +
            'And when she kept on begging, to get rid of her, she said at last, "I\n' +
            'will throw this basinful of peas into the ash heap, and if you have\n' +
            'picked them all out in two hours\' time you shall go to the feast too."\n' +
            'Then she threw the peas into the ashes; but the little maiden ran out at\n' +
            'the back door into the garden, and cried out--\n'],
            ['  "Hither, thither, through the sky, turtle-doves and linnets, fly!\n' +
            '  Blackbird, thrush, and chaffinch gay, hither, thither, haste away!\n' +
            '  One and all, come, help me quick! haste ye, haste ye--pick, pick,\n' +
            '  pick!"\n'],
            ['Then first came two white doves; and next two turtle-doves; and after\n' +
            'them all the little birds under heaven came, and the little doves\n' +
            'stooped their heads down and set to work, pick, pick, pick; and then the\n' +
            'others began to pick, pick, pick, and picked out all the good grain and\n' +
            'put it into a dish, and left the ashes. At the end of one hour the work\n' +
            'was done, and all flew out again at the windows. Then she brought the\n' +
            'dish to her mother. But the mother said, "No, no! indeed, you have no\n' +
            'clothes and cannot dance; you shall not go." And when Cinderella begged\n' +
            'very hard to go, she said, "If you can in one hour\'s time pick two of\n' +
            'these dishes of pease out of the ashes, you shall go too." So she shook\n' +
            'two dishes of peas into the ashes; but the little maiden went out into\n' +
            'the garden at the back of the house, and called as before and all the\n' +
            'birds came flying, and in half an hour\'s time all was done, and out they\n' +
            'flew again. And then Cinderella took the dishes to her mother, rejoicing\n' +
            'to think that she should now go to the ball. But her mother said, "It is\n' +
            'all of no use, you cannot go; you have no clothes, and cannot dance; and\n' +
            'you would only put us to shame;" and off she went with her two daughters\n' +
            'to the feast.\n'],
            ['Now when all were gone, and nobody left at home, Cinderella went\n' +
            'sorrowfully and sat down under the hazel-tree, and cried out--\n'],
            ['  "Shake, shake, hazel-tree, gold and silver over me!"\n'],
            ['Then her friend the bird flew out of the tree and brought a gold and\n' +
            'silver dress for her, and slippers of spangled silk; and she put them\n' +
            'on, and followed her sisters to the feast. But they did not know her,\n' +
            'she looked so fine and beautiful in her rich clothes.\n'],
            ['The king\'s son soon came up to her, and took her by the hand and danced\n' +
            'with her and no one else; and he never left her hand, but when any one\n' +
            'else came to ask her to dance, he said, "This lady is dancing with me."\n' +
            'Thus they danced till a late hour of the night, and then she wanted to\n' +
            'go home; and the king\'s son said, "I shall go and take care of you to\n' +
            'your home," for he wanted to see where the beautiful maid lived. But she\n' +
            'slipped away from him unawares, and ran off towards home, and the prince\n' +
            'followed her; then she jumped up into the pigeon-house and shut the\n' +
            'door. So he waited till her father came home, and told him that the\n' +
            'unknown maiden who had been at the feast had hidden herself in the\n' +
            'pigeon-house. But when they had broken open the door they found no one\n' +
            'within; and as they came back into the house, Cinderella lay, as she\n' +
            'always did, in her dirty frock by the ashes; for she had run as quickly\n' +
            'as she could through the pigeon-house and on to the hazel-tree, and had\n' +
            'there taken off her beautiful clothes, and laid them beneath the tree,\n' +
            'that the bird might carry them away; and had seated herself amid the\n' +
            'ashes again in her little old frock.\n'],
            ['The next day, when the feast was again held, and her father, mother and\n' +
            'sisters were gone, Cinderella went to the hazel-tree, and all happened\n' +
            'as the evening before.\n'],
            ['The king\'s son, who was waiting for her, took her by the hand and danced\n' +
            'with her; and, when any one asked her to dance, he said as before, "This\n' +
            'lady is dancing with me." When night came she wanted to go home; and the\n' +
            'king\'s son went with her, but she sprang away from him all at once into\n' +
            'the garden behind her father\'s house. In this garden stood a fine large\n' +
            'pear-tree; and Cinderella jumped up into it without being seen. Then the\n' +
            'king\'s son waited till her father came home, and said to him, "The\n' +
            'unknown lady has slipped away, and I think she must have sprung into the\n' +
            'pear-tree." The father ordered an axe to be brought, and they cut down\n' +
            'the tree, but found no one upon it. And when they came back into the\n' +
            'kitchen, there lay Cinderella in the ashes as usual; for she had slipped\n' +
            'down on the other side of the tree, and carried her beautiful clothes\n' +
            'back to the bird at the hazel-tree, and then put on her little old\n' +
            'frock.\n'],
            ['The third day, when her father and mother and sisters were gone, she\n' +
            'went again into the garden, and said--\n'],
            ['  "Shake, shake, hazel-tree, gold and silver over me!"\n'],
            ['Then her kind friend the bird brought a dress still finer than the\n' +
            'former one, and slippers which were all of gold; and the king\'s son\n' +
            'danced with her alone, and when any one else asked her to dance, he\n' +
            'said, "This lady is my partner." Now when night came she wanted to go\n' +
            'home; and the king\'s son would go with her, but she managed to slip away\n' +
            'from him, though in such a hurry that she dropped her left golden\n' +
            'slipper upon the stairs.\n'],
            ['So the prince took the shoe, and went the next day to the king, his\n' +
            'father, and said, "I will take for my wife the lady that this golden\n' +
            'shoe fits."\n'],
            ['Then both the sisters were overjoyed to hear this; for they had\n' +
            'beautiful feet, and had no doubt that they could wear the golden\n' +
            'slipper. The eldest went first into the room where the slipper was, and\n' +
            'wanted to try it on, and the mother stood by. But her big toe could not\n' +
            'go into it, and the shoe was altogether much too small for her. Then the\n' +
            'mother said, "Never mind, cut it off. When you are queen you will not\n' +
            'care about toes; you will not want to go on foot." So the silly girl cut\n' +
            'her big toe off, and squeezed the shoe on, and went to the king\'s son.\n' +
            'Then he took her for his bride, and rode away with her.\n'],
            ['But on their way home they had to pass by the hazel-tree that Cinderella\n' +
            'had planted, and there sat a little dove on the branch, singing--\n'],
            ['  "Back again! back again! look to the shoe!\n' +
            '  The shoe is too small, and not made for you!\n' +
            '  Prince! prince! look again for thy bride,\n' +
            '  For she\'s not the true one that sits by thy side."\n'],
            ['Then the prince looked at her foot, and saw by the blood that streamed\n' +
            'from it what a trick she had played him. So he brought the false bride\n' +
            'back to her home, and said, "This is not the right bride; let the other\n' +
            'sister try and put on the slipper." Then she went into the room and got\n' +
            'her foot into the shoe, all but the heel, which was too large. But her\n' +
            'mother squeezed it in till the blood came, and took her to the king\'s\n' +
            'son; and he rode away with her. But when they came to the hazel-tree,\n' +
            'the little dove sat there still, and sang as before. Then the king\'s son\n' +
            'looked down, and saw that the blood streamed from the shoe. So he\n' +
            'brought her back again also. "This is not the true bride," said he to\n' +
            'the father; "have you no other daughters?"\n'],
            ['Then Cinderella came and she took her clumsy shoe off, and put on the\n' +
            'golden slipper, and it fitted as if it had been made for her. And when\n' +
            'he drew near and looked at her face the prince knew her, and said, "This\n' +
            'is the right bride."\n'],
            ['Then he took Cinderella on his horse and rode away. And when they came\n' +
            'to the hazel-tree the white dove sang--\n'],
            ['  "Prince! prince! take home thy bride,\n' +
            '  For she is the true one that sits by thy side!"']]
            ]

    static def storyData = [
            '2000': hanselAndGrethel,
            '2001': littleRedCap,
            '2002': cinderella
    ]*/


}
