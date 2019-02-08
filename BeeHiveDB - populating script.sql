INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO privileges(name, readable_name, description) VALUES('OWNER_PRIVILEGE', 'Apiary Owner', 'Right to grant privileges for owned apiary');
INSERT INTO privileges(name, readable_name, description) VALUES('HIVE_EDITING', 'Hive actions', 'Right to perform actions like honey collectiong or feeding');
INSERT INTO privileges(name, readable_name, description) VALUES('APIARY_EDITING', 'Apiary actions', 'Right to remove/add/modify hives in apiary');
INSERT INTO privileges(name, readable_name, description) VALUES('HIVE_STATS_READING', 'Hive statistics', 'Right to read statistics of each hive in apiary');
INSERT INTO privileges(name, readable_name, description) VALUES('APIARY_STATS_READING', 'Apiary statistics', 'Right to read general statistics of whole apairy.');

INSERT INTO bee_race (agression, description, name) VALUES(0, "Italian bees are the most popular bees to order in North America. They are known for being gentle and good honey producers. They are typically reared in the south and have difficulty in colder climates, as they need to consume extra food.", "Italian");
INSERT INTO bee_race (agression, description, name) VALUES(1, "Russian bees are highly resistant to mites and accustomed to cold climates. As such, they overwinter well. Unfortunately, they also tend to swarm, so it’s important for the beekeeper to provide extra space in the hive to prevent unwanted swarming.", "Russian");
INSERT INTO bee_race (agression, description, name) VALUES(1, "Other than being somewhat gentler and more prone to robbing, these bees behave just like the Italians from which they evolved. They are rarely available in a package of bees, unlike Italians, so are often considered wilder.", "Cordovan");
INSERT INTO bee_race (agression, description, name) VALUES(1, "Caucasian bees are known for high propolis production. The propolis they produce is soft and sticky, which can make it hard for beekeepers to inspect the hive. They stop producing brood in the fall and tend to overwinter quite well.", "Caucasian");
INSERT INTO bee_race (agression, description, name) VALUES(0, "Carniolan bees are incredibly gentle and easy to work with. Due to their region of origin, they are more likely to forage on cold, wet days than other types of bees and rank among the best for overwintering.", "Carniolan");
INSERT INTO bee_race (agression, description, name) VALUES(2, "Buckfast bees are resistant to Tracheal mites and do well in cool climates. They are very gentle, easy to work with and are excellent honey producers. They have a low tendency to swarm and are economical in the use of winter stores.", "Buckfast");
INSERT INTO bee_race (agression, description, name) VALUES(2, "Africanized honey bees are known for being highly aggressive and, unlike their more docile cousins, will chase a person up to a quarter of a mile if they perceive a threat.", "Africanized");
INSERT INTO bee_race (agression, description, name) VALUES(1, "Blank description", "Other");

INSERT INTO hive_type (description, frame_capacity, name) VALUES( "The stacked white boxes that most people probably envision when they think of beekeeping are called Langstroth hives. These boxes stack together to form the hive. The bees build a brood nest at the bottom and fill the top boxes with honey.", 10, "Ten-Frame Langstroth");
INSERT INTO hive_type (description, frame_capacity, name) VALUES( "Eight frame hives work just like the ten-frame Langstroth hives in terms of structure but each box is slightly smaller, holding only eight frames instead of ten. What does this mean? When you lift a medium super full of honey.", 8, "Eight Frame Langstroth");
INSERT INTO hive_type (description, frame_capacity, name) VALUES( "Top-bar hives are becoming more popular with backyard enthusiasts and sustainable farmers. Instead of setting up the hive vertically, they are set up horizontally, so that the honey is at the front and the brood nest is at the back.", 20, "Top-Bar Hive ");
INSERT INTO hive_type (description, frame_capacity, name) VALUES( "A Warré hive uses small, square hive bodies and top bars with no frames or foundation. It also uses a unique style of hive cover: a quilt and a vented, angled roof. This is supposed to provide superior moisture management.", 8, "Warré Hive");
INSERT INTO hive_type (description, frame_capacity, name) VALUES( "Blank description", 10, "Other");

INSERT INTO honey_type (description, name, price) VALUES( "Acacia is very sweet with a clean, pure, classic honey flavor.", "Acacia", 50);
INSERT INTO honey_type (description, name, price) VALUES( "Wildflower honey is light and fruity yet richly flavored at the same time.", "Wildflower", 40);
INSERT INTO honey_type (description, name, price) VALUES( "Blackberry honey is deep and rich while still being fruity.", "Blackberry", 60);
INSERT INTO honey_type (description, name, price) VALUES( "Linden honey is quite delicate and has a fresh, woodsy aroma that goes perfectly with tea.", "Linden", 40);
INSERT INTO honey_type (description, name, price) VALUES( "Heather is pungent and almost bitter, in a good way. It's great with smoky things.", "Heather", 80);
INSERT INTO honey_type (description, name, price) VALUES( "Clover is a classic honey that's light, sweet, and floral.", "Clover", 35);
INSERT INTO honey_type (description, name, price) VALUES( "Blank description", "Other", 30);

